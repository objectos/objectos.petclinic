/*
 * Copyright (C) 2023-2024 Objectos Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package objectos.petclinic.site;

import java.util.List;
import objectos.way.Http;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;

final class Owners implements Http.Handler {

  private final SiteInjector injector;

  public Owners(SiteInjector injector) {
    this.injector = injector;
  }

  @Override
  public final void handle(Http.Exchange http) {
    switch (http.method()) {
      case GET, HEAD -> get(http);

      case POST -> post(http);

      default -> http.methodNotAllowed();
    }
  }

  private void get(Http.Exchange http) {
    // renders the view
    OwnersView view;
    view = renderView(http);

    // respond 200 OK with our view
    http.ok(view);
  }

  private void post(Http.Exchange http) {
    // parse the form data
    Web.FormData formData;
    formData = Web.FormData.parse(http);

    // TODO validation

    // fetch the SQL session from the HTTP exchange
    // see interceptor(injector::transactional) in the SiteModule.java file
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    trx.sql("""
    insert into owners (first_name, last_name, address, city, telephone)
    values (?, ?, ?, ?, ?)
    """);

    trx.add(formData.getOrDefault("firstName", ""));
    trx.add(formData.getOrDefault("lastName", ""));
    trx.add(formData.getOrDefault("address", ""));
    trx.add(formData.getOrDefault("city", ""));
    trx.add(formData.getOrDefault("telephone", ""));

    trx.update();

    // re-renders the listing with the new record
    OwnersView view;
    view = renderView(http);

    // checks if this is a request from the Objectos Way JS library
    String wayRequest;
    wayRequest = http.header(Http.HeaderName.WAY_REQUEST);

    if ("true".equals(wayRequest)) {

      // Yes, this is a request from the Objectos Way JS library
      // respond with JSON

      Script.Action action;
      action = view.createAction();

      http.ok(action);

    } else {

      // No, this is not a request from the Objectos Way JS library
      // respond with HTML

      http.ok(view);

    }
  }

  private OwnersView renderView(Http.Exchange http) {
    // fetch the SQL session from the HTTP exchange
    // see interceptor(injector::transactional) in the SiteModule.java file
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    // the owners SQL query
    // the where clause is enclosed in a SQL fragment
    trx.sql("""
    select concat_ws(' ', owners.first_name, owners.last_name) as name
         , owners.address
         , owners.city
         , owners.telephone
         , concat('/owners/', owners.id)
         , coalesce(listagg(pets.name, ', ') within group (order by pets.name), '')
      from owners
      left join pets
        on pets.owner_id = owners.id
    --
     where owners.last_name like ?
    --
     group by owners.id
     order by owners.last_name, owners.id
    """);

    // the search query parameter
    String q;
    q = http.queryParam("q");

    // our SQL fragment will be enabled if the expression on the right evaluates to true
    trx.addIf(q + "%", q != null && !q.isBlank());

    // paginate the SQL query
    Web.Paginator paginator;
    paginator = Web.Paginator.create(config -> {
      config.requestTarget(http);

      config.parameterName("page");

      config.pageSize(5);

      config.rowCount(trx.count());
    });

    trx.paginate(paginator);

    // execute the SQL query
    List<OwnersRow> rows;
    rows = trx.query(OwnersRow.MAPPER);

    // create a new view instance
    return OwnersView.create(config -> {
      config.injector = injector;

      config.paginator = paginator;

      config.rows = rows;
    });
  }

}