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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.function.Consumer;
import javax.sql.DataSource;
import objectos.petclinic.site.SiteInjector;
import objectos.petclinic.site.UiStyles;
import objectos.way.App;
import objectos.way.Css.StyleSheet;
import objectos.way.Html;
import objectos.way.Script;
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

  public static final class SiteInjectorSupplier {

    private static final SiteInjector INSTANCE = create();

    private static SiteInjector create() {
      return new SiteInjector(
          DatabaseSupplier.get(),
          NoteSinkSupplier.get(),
          null,

          null,
          null,
          TemplateHeadPlugin.create()
      );
    }

    private SiteInjectorSupplier() {}

    public static SiteInjector get() {
      return INSTANCE;
    }

  }

  private record TemplateHeadPlugin(String styleSheet, String script) implements Consumer<Html.Markup> {

    public static TemplateHeadPlugin create() {
      return new TemplateHeadPlugin(
          createStyleSheet(),

          createScript()
      );
    }

    private static String createStyleSheet() {
      final App.NoteSink noteSink;
      noteSink = NoteSinkSupplier.get();

      Path classOutput;
      classOutput = Path.of("work", "main");

      Path absolutePath;
      absolutePath = classOutput.toAbsolutePath();

      UiStyles styles;
      styles = new UiStyles(noteSink, absolutePath);

      StyleSheet sheet;
      sheet = styles.generateStyleSheet();

      return sheet.css();
    }

    private static String createScript() {
      try {
        byte[] bytes;
        bytes = Script.getBytes();

        return new String(bytes, StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public final void accept(Html.Markup html) {
      html.style(styleSheet);

      html.script(script);
    }

  }

  private Testing() {}

}