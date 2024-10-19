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

public class OwnersBrowseTest {

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
    http = Http.testingExchange(
        Http.requestTarget("/owners"),

        Http.set(Sql.Transaction.class, trx)
    );

    OwnersBrowse owners;
    owners = new OwnersBrowse();

    owners.handle(http);

    DataTable t;
    t = DataTable.of(owners);

    assertEquals(t.size(), 5);
    assertEquals(t.row(0), List.of("Jeff Black", "1450 Oak Blvd.", "Monona", "6085555387", "Lucky"));
    assertEquals(t.row(1), List.of("Jean Coleman", "105 N. Lake St.", "Monona", "6085552654", "Max, Samantha"));
    assertEquals(t.row(2), List.of("Betty Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749", "Basil"));
    assertEquals(t.row(3), List.of("Harold Davis", "563 Friendly St.", "Windsor", "6085553198", "Iggy"));
    assertEquals(t.row(4), List.of("Maria Escobito", "345 Maple St.", "Madison", "6085557683", "Mulligan"));
  }

  @Test
  public void filtering() {
    Http.TestingExchange http;
    http = Http.testingExchange(
        Http.requestTarget("/owners?lastName=dav"),

        Http.set(Sql.Transaction.class, trx)
    );

    OwnersBrowse owners;
    owners = new OwnersBrowse();

    owners.handle(http);

    DataTable t;
    t = DataTable.of(owners);

    assertEquals(t.size(), 2);
    assertEquals(t.row(0), List.of("Betty Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749", "Basil"));
    assertEquals(t.row(1), List.of("Harold Davis", "563 Friendly St.", "Windsor", "6085553198", "Iggy"));
  }

}
