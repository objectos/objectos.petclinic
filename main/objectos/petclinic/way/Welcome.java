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
import objectos.way.Carbon;
import objectos.way.Sql;
import objectos.way.Web;

final class Welcome extends UiLayout {

  static final String QUERY = """
  select   p.name, v.visit_date, v.description
  from     visits as v
  join     pets as p
  on       v.pet_id = p.id
  order by v.visit_date desc
  """;

  private Web.Paginator paginator;

  private Sql.Transaction trx;

  @Override
  protected final void preRender() {
    trx = http.get(Sql.Transaction.class);

    int count;
    count = trx.count(QUERY);

    paginator = Way.paginator(http, count);
  }

  @Override
  protected final void renderHead() {
    title("Objectos PetClinic");
  }

  @Override
  protected final void renderContent() {
    section(
        className("page-header page-header-title-only"),

        div(
            className("page-header-title-row"),

            h1(
                className("page-header-title"),

                t(msg("Welcome"))
            ),

            div(
                className("page-header-actions"),

                button(
                    className("button-md button-primary"),
                    type("button"),

                    t(msg("Create visit")),

                    icon16(Carbon.Icon.ADD)
                )
            )
        )
    );

    div(
        className("grid-narrow grid-cols-4 mt-07"),

        f(this::lastVisits),

        div(
            className("tile min-h-screen col-span-1"),

            t("Column 2")
        )
    );
  }

  private void lastVisits() {
    div(
        className("data-table-contents col-span-3"), tabindex("0"),

        table(
            className("data-table"),

            thead(
                tr(
                    th(msg("Date")),
                    th(msg("Pet")),
                    th(msg("Description"))
                )
            ),

            tbody(
                f(this::tbody)
            )
        )
    );
  }

  private void tbody() {
    trx.processQuery(this::rows, paginator, QUERY);
  }

  private void rows(ResultSet rs) throws SQLException {
    while (rs.next()) {
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
}