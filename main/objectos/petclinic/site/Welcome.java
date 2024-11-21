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
import objectos.way.Sql;

final class Welcome implements Http.Handler {

  private final SiteInjector injector;

  public Welcome(SiteInjector injector) {
    this.injector = injector;
  }

  @Override
  public final void handle(Http.Exchange http) {
    switch (http.method()) {
      case GET, HEAD -> get(http);

      default -> http.methodNotAllowed();
    }
  }

  private void get(Http.Exchange http) {
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    trx.sql("""
    select pets.name
         , visits.visit_date
         , visits.description
      from visits
      join pets
        on visits.pet_id = pets.id
     order by visits.visit_date desc
    """);

    List<WelcomeVisit> visits;
    visits = trx.query(WelcomeVisit.MAPPER);

    WelcomeView view;
    view = WelcomeView.create(config -> {
      config.injector = injector;

      config.visits = visits;
    });

    http.ok(view);
  }

}