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
import objectos.way.Http;
import objectos.way.Sql;
import objectos.way.Web;

final class VetsBrowse extends UiLayout {

  private static final String QUERY = """
  select    concat_ws(' ', v.first_name, v.last_name) as vet_name,
            coalesce(listagg(s.name, ' ') within group (order by s.name), 'none') as spec_names
  from      vets as v
  left join vet_specialties as vs
  on        v.id = vs.vet_id
  left join specialties as s
  on        vs.specialty_id = s.id
  group by  vet_name
  order by  v.last_name, v.id
  """;

  VetsBrowse(Http.Exchange http) {
    super(http);

    section = UiSection.VETS;

    title = "Veterinarians :: Objectos PetClinic";
  }

  @Override
  protected final void mainContent() {
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    int count;
    count = trx.count(QUERY);

    Web.Paginator paginator;
    paginator = Way.paginator(http, count);

    ui(trx, paginator);
  }

  private void ui(Sql.Transaction trx, Web.Paginator paginator) {
    dataFrame("main", "vets");

    header(Ui.PAGE_HEADER,
        h1("Veterinarians")
    );

    div(Ui.PAGE_MAIN, dataFrame("vets-table"),

        pagination(paginator),

        div(Ui.PAGE_TABLE,

            table(
                thead(
                    tr(
                        th(Ui.W_144PX, t("Name")),
                        th("Specialties")
                    )
                ),
                tbody(
                    f(this::tbody, trx, paginator)
                )
            )

        )

    );
  }

  private void tbody(Sql.Transaction trx, Web.Paginator paginator) {
    trx.processQuery(this::rows, paginator, QUERY);
  }

  private void rows(ResultSet rs) throws SQLException {
    while (rs.next()) {
      String name;
      name = rs.getString("vet_name");

      String specialties;
      specialties = rs.getString("spec_names");

      tr(
          td(name),
          td(specialties)
      );
    }
  }

}
