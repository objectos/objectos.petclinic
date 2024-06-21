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
package objectox.petclinic;

import java.sql.SQLException;
import objectos.way.Sql;

public final class TestingSqlDataSource {

  public static final Sql.Source INSTANCE;

  static {
    try {
      Sql.Source dataSource;
      dataSource = Sql.createSource(TestingDataSource.INSTANCE);

      INSTANCE = dataSource;
    } catch (SQLException e) {
      throw new AssertionError(e);
    }
  }

  private TestingSqlDataSource() {}

  public static Sql.Transaction beginTransaction(Sql.Transaction.IsolationLevel level) throws SQLException {
    return INSTANCE.beginTransaction(level);
  }

}