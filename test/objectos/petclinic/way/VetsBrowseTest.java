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

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;
import objectos.way.Http;
import objectos.way.Sql;
import objectox.petclinic.DataTable;
import objectox.petclinic.TestingSqlDataSource;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VetsBrowseTest {

  private Sql.Transaction trx;

  @BeforeClass
  public void setUp() throws SQLException {
    trx = TestingSqlDataSource.beginTransaction(Sql.SERIALIZABLE);
  }

  @AfterClass(alwaysRun = true)
  public void cleanUp() throws SQLException {
    if (trx != null) {
      try {
        trx.rollback();
      } finally {
        trx.close();
      }
    }
  }

  @Test
  public void query() {
    Http.TestingExchange http;
    http = Http.TestingExchange.create(config -> {
      config.path("/vets");

      config.set(Sql.Transaction.class, trx);
    });

    VetsBrowse vets;
    vets = new VetsBrowse();

    vets.handle(http);

    DataTable t;
    t = DataTable.of(vets);

    assertEquals(t.size(), 5);
    assertEquals(t.row(0), List.of("James Carter", "none"));
    assertEquals(t.row(1), List.of("Linda Douglas", "dentistry surgery"));
    assertEquals(t.row(2), List.of("Sharon Jenkins", "none"));
    assertEquals(t.row(3), List.of("Helen Leary", "radiology"));
    assertEquals(t.row(4), List.of("Rafael Ortega", "surgery"));
  }

}