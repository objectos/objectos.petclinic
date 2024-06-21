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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import objectos.way.Http;
import objectos.way.Sql;
import objectos.way.Web;

final class Welcome extends UiLayout {

  static final String QUERY = """
  select p.name, v.visit_date, v.description
  from   visits as v
  join   pets as p 
  on     v.pet_id = p.id
  order by v.visit_date desc
  """;

  private final Web.Paginator paginator;

  private final Sql.Transaction trx;

  public Welcome(Web.Paginator paginator, Sql.Transaction trx) {
    super(UiSection.HOME, "PetClinic :: an Objectos Way demonstration");

    this.paginator = paginator;
    this.trx = trx;
  }

  public static void get(Http.Exchange http) {
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    int count = trx.count(QUERY);

    Web.Paginator paginator;
    paginator = Way.paginator(http, count);

    Welcome template;
    template = new Welcome(paginator, trx);

    http.ok(template);
  }

  @Override
  protected final void mainContent() {
    dataFrame("main", "welcome");

    header(Ui.PAGE_HEADER,
        h1("Welcome")
    );

    div(Ui.PAGE_MAIN, dataFrame("visits-table"),

        pagination(paginator),

        div(Ui.PAGE_TABLE,

            table(
                thead(
                    tr(
                        th(Ui.W_112PX, t("Date")),
                        th(Ui.W_144PX, t("Pet")),
                        th("Description")
                    )
                ),
                tbody(
                    f(this::rows)
                )
            )

        )

    );
  }

  private void rows() {
    Sql.Page page;
    page = paginator.current();

    trx.queryPage(QUERY, this::row, page);
  }

  private void row(ResultSet rs) throws SQLException {
    Date visitDate;
    visitDate = rs.getDate("visit_date");

    String name;
    name = rs.getString("name");

    String description;
    description = rs.getString("description");

    tr(
        td(visitDate.toString()),
        td(name),
        td(description)
    );
  }

}