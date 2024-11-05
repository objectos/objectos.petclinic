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
package objectos.petclinic.boot;

import java.sql.SQLException;
import javax.sql.DataSource;
import objectos.way.App;
import objectos.way.Sql;
import org.h2.jdbcx.JdbcConnectionPool;

public final class Testing {

  public static final class DatabaseSupplier {

    private static final Sql.Database INSTANCE = create();

    private static Sql.Database create() {
      return Sql.createDatabase(DataSourceSupplier.get());
    }

    private DatabaseSupplier() {}

    public static Sql.Transaction beginTransaction(Sql.Transaction.Isolation level) {
      return INSTANCE.beginTransaction(level);
    }

    public static Sql.Database get() {
      return INSTANCE;
    }

  }

  private static final class DataSourceSupplier {

    private static final DataSource INSTANCE = create();

    private static DataSource create() {
      try {
        JdbcConnectionPool ds;
        ds = PetClinicH2.createSchema();

        App.ShutdownHook shutdownHook;
        shutdownHook = ShutdownHookSupplier.get();

        shutdownHook.register(ds::dispose);

        return ds;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    private DataSourceSupplier() {}

    public static DataSource get() {
      return INSTANCE;
    }

  }

  public static final class NoteSinkSupplier {

    private static final App.NoteSink INSTANCE = create();

    private static App.NoteSink create() {
      return App.NoteSink.OfConsole.create();
    }

    private NoteSinkSupplier() {}

    public static App.NoteSink get() {
      return INSTANCE;
    }

  }

  public static final class ShutdownHookSupplier {

    private static final App.ShutdownHook INSTANCE = create();

    private static App.ShutdownHook create() {
      return App.ShutdownHook.create(NoteSinkSupplier.get());
    }

    private ShutdownHookSupplier() {}

    public static App.ShutdownHook get() {
      return INSTANCE;
    }

  }

  private Testing() {}

}