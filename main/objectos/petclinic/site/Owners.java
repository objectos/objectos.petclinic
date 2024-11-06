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
import objectos.way.Sql;

@Css.Source
final class Owners extends UiTemplate {

  private List<Owner> owners;

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

  @Override
  protected final void preRender() {
    pageTitle = "Owners | Objectos PetClinic";

    Sql.Transaction trx;
    trx = http.get(Sql.Transaction.class);

    trx.sql("""
    SELECT CONCAT_WS(' ', o.first_name, o.last_name) AS name,
           o.address,
           o.city,
           o.telephone,
           CONCAT('/owners/', o.id) as href,
           LISTAGG(p.name, ', ') WITHIN GROUP (ORDER BY p.name) AS pets

      FROM owners AS o
      LEFT
      JOIN pets AS p
        ON p.owner_id = o.id

     GROUP
        BY name

     ORDER
        BY o.last_name,
           o.id
    """);

    owners = trx.query(Owner::new);
  }

  @Override
  final void renderContents() {
    breadcrumb(
        breadcrumbItem("Owners")
    );

    contents(
        dataTable(
            this::tableHead,

            this::tableBody
        )
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