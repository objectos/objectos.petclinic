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
import objectos.petclinic.boot.Testing;
import objectos.way.Http;
import objectos.way.Sql;
import org.testng.annotations.Test;

public class OwnersTest extends AbstractTransactionalTest {

  private final SiteInjector siteInjector = Testing.SiteInjectorSupplier.get();

  @Test(description = """
  GET /owners should list the owners
  - ordered by last name (asc)
  - owners with the same last name are order by id (asc)
  - pets are ordered by name
  """)
  public void testCase01() {
    Http.TestingExchange http;
    http = Http.TestingExchange.create(config -> {
      config.method(Http.Method.GET);

      config.set(SiteInjector.class, siteInjector);

      config.set(Sql.Transaction.class, trx);
    });

    Owners page;
    page = new Owners();

    page.handle(http);

    assertEquals(http.responseStatus(), Http.Status.OK);

    assertEquals(
        writeResponseBody(http, "testCase01"),

        """
        owner.name: OW12 AAA
        owner.address: Add 12
        owner.city: City 12
        owner.telephone: 12
        owner.pets: BBB, BBB, CCC
        owner.name: OW14 BBB
        owner.address: Add 14
        owner.city: City 14
        owner.telephone: 14
        owner.pets:
        owner.name: OW11 DDD
        owner.address: Add 11
        owner.city: City 11
        owner.telephone: 11
        owner.pets: LLL
        owner.name: OW13 DDD
        owner.address: Add 13
        owner.city: City 13
        owner.telephone: 13
        owner.pets: TTT
        """
    );
  }

  @Override
  protected final String testData() {
    return """
    INSERT INTO owners (id, first_name, last_name, address, city, telephone)
    VALUES (11, 'OW11', 'DDD', 'Add 11', 'City 11', '11')
    ,      (12, 'OW12', 'AAA', 'Add 12', 'City 12', '12')
    ,      (13, 'OW13', 'DDD', 'Add 13', 'City 13', '13')
    ,      (14, 'OW14', 'BBB', 'Add 14', 'City 14', '14')

    INSERT INTO types (id, name)
    VALUES (91, 'Type 1')
    ,      (92, 'Type 2')
    ,      (93, 'Type 3')

    INSERT INTO pets (id, owner_id, type_id, name, birth_date)
    VALUES (11091, 11, 91, 'LLL', '2010-09-07')
    ,      (12092, 12, 92, 'BBB', '2012-08-06')
    ,      (12192, 12, 92, 'CCC', '2012-08-06')
    ,      (12292, 12, 92, 'BBB', '2012-08-06')
    ,      (13093, 13, 93, 'TTT', '2011-04-17')
    """;
  }

}