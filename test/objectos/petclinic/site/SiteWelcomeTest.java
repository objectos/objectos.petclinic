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

import static org.testng.Assert.assertEquals;

import objectos.petclinic.AbstractTransactionalTest;
import objectos.way.Http;
import objectos.way.Sql;
import org.testng.annotations.Test;

public class SiteWelcomeTest extends AbstractTransactionalTest {

  @Test
  public void testCase01() {
    Http.TestingExchange http;
    http = Http.TestingExchange.create(config -> {
      config.method(Http.Method.GET);

      config.set(Sql.Transaction.class, trx);
    });

    SiteWelcome page;
    page = new SiteWelcome();

    page.handle(http);

    assertEquals(http.responseStatus(), Http.Status.OK);

    assertEquals(
        writeResponseBody(http, "testCase01"),

        """
        visit.date: 2024-11-04
        visit.name: TTT
        visit.description: visit d
        visit.date: 2024-11-03
        visit.name: BBB
        visit.description: visit c
        visit.date: 2024-11-02
        visit.name: BBB
        visit.description: visit b
        visit.date: 2024-11-01
        visit.name: LLL
        visit.description: visit a
        """
    );
  }

  @Override
  protected final String testData() {
    return """
    INSERT INTO types (id, name)
    VALUES (91, 'Type 1')
    ,      (92, 'Type 2')
    ,      (93, 'Type 3')

    INSERT INTO owners (id, first_name, last_name, address, city, telephone)
    VALUES (11, 'GGG', 'FFF', '', '', '')
    ,      (12, 'BBB', 'DDD', '', '', '')
    ,      (13, 'EEE', 'RRR', '', '', '')

    INSERT INTO pets (id, owner_id, type_id, name, birth_date)
    VALUES (11091, 11, 91, 'LLL', '2010-09-07')
    ,      (12092, 12, 92, 'BBB', '2012-08-06')
    ,      (12192, 12, 92, 'CCC', '2012-08-06')
    ,      (13093, 13, 93, 'TTT', '2011-04-17')

    INSERT INTO visits (id, pet_id, visit_date, description)
    VALUES (110910, 11091, '2024-11-01', 'visit a')
    ,      (120920, 12092, '2024-11-02', 'visit b')
    ,      (120921, 12092, '2024-11-03', 'visit c')
    ,      (130930, 13093, '2024-11-04', 'visit d')
    """;
  }

}