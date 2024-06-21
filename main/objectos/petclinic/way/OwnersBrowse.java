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
import objectos.html.Action;
import objectos.html.ElementId;
import objectos.way.Http;
import objectos.way.Sql;
import objectos.way.Web;

final class OwnersBrowse extends UiLayout implements Web.Action {

  static final String QUERY = """
  select    concat_ws(' ', o.first_name, o.last_name) as name,
            o.address,
            o.city,
            o.telephone,
            listagg(p.name, ', ') within group (order by p.name) as pets
  from      owners as o
  left join pets as p
  on        p.owner_id = o.id
  --
  where     o.last_name like ?
  --
  group by  name
  order by  o.last_name, o.id
  """;

  private final String lastName;

  private final Web.Paginator paginator;

  private final String path;

  private final Sql.Transaction trx;

  OwnersBrowse(Http.Exchange http) {
    super(http);

    section = UiSection.OWNERS;

    title = "Owners :: PetClinic";

    trx = http.get(Sql.Transaction.class);

    path = http.path().value();

    String q = http.query().get("q");

    String lastName = null;

    if (q != null && !q.isBlank()) {
      lastName = q + "%";
    }

    this.lastName = lastName;

    int count;
    count = trx.count(QUERY, lastName);

    paginator = Way.paginator(http, count);
  }

  @Override
  protected final void mainContent() {
    dataFrame("main", "owners");

    ElementId formId;
    formId = ElementId.of("search-form");

    header(Ui.PAGE_HEADER,
        h1("Owners"),

        form(formId, action(path), method("get"),
            input(name("q"), type("text"), autocomplete("off"), placeholder("Last name"), tabindex("0"),
                dataOnInput(
                    Action.delay(500, Action.submit(formId))
                )
            )
        )
    );

    div(Ui.PAGE_MAIN, dataFrame("owners-table"),

        pagination(paginator),

        div(Ui.PAGE_TABLE,
            table(
                thead(
                    tr(
                        th(Ui.W_160PX, t("Name")),
                        th(Ui.W_160PX, t("Address")),
                        th(Ui.W_128PX, t("City")),
                        th(Ui.W_128PX, t("Telephone")),
                        th("Pets")
                    )
                ),
                tbody(
                    include(this::rows)
                )
            )
        )

    );
  }

  private void rows() {
    trx.queryPage(QUERY, this::row, paginator.current(), lastName);
  }

  private void row(ResultSet rs) throws SQLException {
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

    tr(
        td(name),
        td(address),
        td(city),
        td(telephone),
        td(pets)
    );
  }

}