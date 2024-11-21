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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import objectos.way.Css;
import objectos.way.Html;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;

@Css.Source
final class Owners extends UiTemplate {

  private record Owner(
      String name,
      String address,
      String city,
      String telephone,
      String href,
      String pets
  ) {

    Owner(ResultSet rs, int idx) throws SQLException {
      this(
          rs.getString(idx++),
          rs.getString(idx++),
          rs.getString(idx++),
          rs.getString(idx++),
          rs.getString(idx++),
          rs.getString(idx++)
      );
    }

  }

  private List<Owner> owners;

  private Web.Paginator paginator;

  @Override
  protected final void preRender() {
    pageSidebar = UiSidebar.OWNERS;

    pageTitle = "Owners | Objectos PetClinic";

    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

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
     group by name
     order by owners.last_name, owners.id
    """);

    String q;
    q = http.queryParam("q");

    trx.addIf(q + "%", q != null && !q.isBlank());

    paginator = Web.Paginator.create(config -> {
      config.requestTarget(http);

      config.parameterName("page");

      config.pageSize(5);

      config.rowCount(trx.count());
    });

    trx.paginate(paginator);

    owners = trx.query(Owner::new);
  }

  private static final Html.Id FORM_ID = Html.Id.of("search-form");

  @Override
  final void renderContents() {
    h1("Owners");

    form(FORM_ID,
        action(http.path()), method("get"),

        input(name("q"), type("text"), autocomplete("off"), placeholder("Last name"), tabindex("0"),
            dataOnInput(
                Script.delay(500, Script.submit(FORM_ID))
            )
        )
    );

    div(
        dataFrame("owners-data"),

        !owners.isEmpty() ? renderFragment(this::data) : p("No data found")
    );
  }

  private void data() {
    pagination("owners-pagination", paginator);

    dataTable(
        this::tableHead,

        this::tableBody
    );
  }

  private void tableHead() {
    tr(
        className("th:text-start"),

        th(
            text("Name")
        ),

        th(
            text("Address")
        ),

        th(
            text("City")
        ),

        th(
            text("Telephone")
        ),

        th(
            text("Pets")
        )
    );
  }

  private void tableBody() {
    for (Owner owner : owners) {
      tr(
          className("td:text-start"),

          td(
              testable("owner.name", owner.name)
          ),

          td(
              testable("owner.address", owner.address)
          ),

          td(
              testable("owner.city", owner.city)
          ),

          td(
              testable("owner.telephone", owner.telephone)
          ),

          td(
              testable("owner.pets", owner.pets)
          )
      );
    }
  }

}