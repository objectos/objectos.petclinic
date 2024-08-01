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
package objectos.petclinic.way;

import java.sql.ResultSet;
import java.sql.SQLException;
import objectos.way.Html;
import objectos.way.Http;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;

final class OwnersBrowse extends UiLayout {

  static final String QUERY = """
  SELECT    CONCAT_WS(' ', o.first_name, o.last_name) AS name,
            o.address,
            o.city,
            o.telephone,
            CONCAT('/owners/', o.id) as href,
            LISTAGG(p.name, ', ') WITHIN GROUP (ORDER BY p.name) AS pets
  FROM      owners AS o
  LEFT JOIN pets AS p
  ON        p.owner_id = o.id
  --
  WHERE     o.last_name LIKE CONCAT(?, '%')
  --
  GROUP BY  name
  ORDER BY  o.last_name, o.id
  """;

  OwnersBrowse(Http.Exchange http) {
    super(http);
  }

  @Override
  protected final void renderHead() {
    title("Owners :: PetClinic");
  }

  @Override
  protected final void renderContent() throws Exception {
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    String lastName;
    lastName = http.queryParam("lastName");

    int count;
    count = trx.count(QUERY, lastName);

    Web.Paginator paginator;
    paginator = Way.paginator(http, count);

    String searchAction;
    searchAction = http.path();

    ui(trx, lastName, paginator, searchAction);
  }

  private void ui(Sql.Transaction trx, String lastName, Web.Paginator paginator, String searchAction) {
    dataFrame("main", "owners");

    Html.Id formId;
    formId = Html.id("search-form");

    header(
        h1("Owners"),

        form(formId, action(searchAction), method("get"),
            input(
                name("lastName"),
                type("text"), autocomplete("off"), placeholder("Last name"), tabindex("0"),
                dataOnInput(
                    Script.delay(500, Script.submit(formId))
                ),
                lastName != null ? value(lastName) : noop()
            )
        )
    );

    div(dataFrame("owners-table"),

        pagination(paginator),

        div(
            table(
                thead(
                    tr(
                        th(t("Name")),
                        th(t("Address")),
                        th(t("City")),
                        th(t("Telephone")),
                        th("Pets")
                    )
                ),
                tbody(
                    f(this::tbody, trx, paginator, lastName)
                )
            )
        )

    );
  }

  private void tbody(Sql.Transaction trx, Web.Paginator paginator, String lastName) {
    trx.processQuery(this::rows, paginator, QUERY, lastName);
  }

  private void rows(ResultSet rs) throws SQLException {
    while (rs.next()) {
      String name;
      name = rs.getString("name");

      String address;
      address = rs.getString("address");

      String city;
      city = rs.getString("city");

      String telephone;
      telephone = rs.getString("telephone");

      String pets;
      pets = rs.getString("pets");

      String href;
      href = rs.getString("href");

      tr(
          td(
              a(href(href), t(name))
          ),
          td(address),
          td(city),
          td(telephone),
          td(pets)
      );
    }
  }

}