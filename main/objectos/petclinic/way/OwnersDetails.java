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

final class OwnersDetails extends UiLayout {

  private static final String QUERY = """
  SELECT CONCAT_WS(' ', first_name, last_name) AS fullName,
         CONCAT(address, ', ', city) AS fullAddress,
         telephone
  FROM   owners
  WHERE  id = ?
  """;

  private Sql.Transaction trx;

  private int id;

  OwnersDetails(Http.Exchange http) {
    super(http);
  }

  @Override
  protected final void preRender() {
    section = UiSection.OWNERS;

    trx = http.get(Sql.Transaction.class);

    String idValue;
    idValue = http.pathParam("id");

    id = Integer.parseInt(idValue);
  }

  @Override
  protected final void mainContent() {
    dataFrame("main", "owners:" + id);

    trx.processQuery(this::renderMain, QUERY, id);
  }

  private void renderMain(ResultSet rs) throws SQLException {
    if (!rs.next()) {
      throw new UnsupportedOperationException("Implement me :: not found");
    }

    include(
        new UiOwnerVCard(rs)
    );
  }

}