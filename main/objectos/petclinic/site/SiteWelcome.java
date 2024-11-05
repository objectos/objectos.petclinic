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
import java.time.LocalDate;
import java.util.List;
import objectos.way.Css;
import objectos.way.Http;
import objectos.way.Sql;

@Css.Source
final class SiteWelcome extends UiTemplate {

  private List<Visit> visits;

  SiteWelcome(SiteInjector injector) {
    super(injector);
  }

  @Override
  public final void handle(Http.Exchange http) {
    switch (http.method()) {
      case GET, HEAD -> handleGet(http);

      default -> http.methodNotAllowed();
    }
  }

  private record Visit(
      String name,
      LocalDate date,
      String description
  ) {

    Visit(ResultSet rs, int idx) throws SQLException {
      this(
          rs.getString(idx++),
          rs.getObject(idx++, LocalDate.class),
          rs.getString(idx++)
      );
    }

    final String dateText() {
      return date.toString();
    }

  }

  private void handleGet(Http.Exchange http) {
    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    trx.sql("""
    SELECT pets.name,
           visits.visit_date,
           visits.description

      FROM visits
      JOIN pets
        ON visits.pet_id = pets.id

     ORDER
        BY visits.visit_date desc
    """);

    visits = trx.query(Visit::new);

    http.ok(this);
  }

  @Override
  protected final void renderHead() {
    title("Objectos PetClinic");
  }

  @Override
  final void renderMain() {
    h1("Welcome");

    table(
        className("w-full"),

        thead(
            tr(
                th("Date"),
                th("Pet"),
                th("Description")
            )
        ),

        tbody(
            renderFragment(this::renderTableBody)
        )
    );
  }

  private void renderTableBody() {
    for (Visit visit : visits) {
      tr(
          td(testable("visit.date", visit.dateText())),
          td(testable("visit.name", visit.name)),
          td(testable("visit.description", visit.description))
      );
    }
  }

}