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
import objectos.petclinic.site.SiteInjector;
import objectos.petclinic.site.UiStyles;
import objectos.way.App;
import objectos.way.Css.StyleSheet;
import objectos.way.Html;
import objectos.way.Script;
import objectos.way.Sql;
import org.h2.jdbcx.JdbcConnectionPool;

public final class Testing {

  public static final SiteInjector SITE_INJECTOR = createSiteInjector();

  private static SiteInjector createSiteInjector() {
    App.NoteSink noteSink;
    noteSink = App.NoteSink.OfConsole.create();

    App.ShutdownHook shutdownHook;
    shutdownHook = App.ShutdownHook.create(noteSink);

    JdbcConnectionPool dataSource;

    try {
      dataSource = PetClinicH2.createSchema();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    shutdownHook.register(dataSource::dispose);

    Sql.Database database;
    database = Sql.Database.create(config -> config.dataSource(dataSource));

    return new SiteInjector(
        database,
        noteSink,
        null,

        null,
        null,
        createTemplateHeadPlugin(noteSink)
    );
  }

  private static Consumer<Html.Markup> createTemplateHeadPlugin(App.NoteSink noteSink) {
    record TemplateHeadPlugin(String styleSheet, String script) implements Consumer<Html.Markup> {
      @Override
      public final void accept(Html.Markup html) {
        html.style(styleSheet);

        html.script(script);
      }
    }

    Path classOutput;
    classOutput = Path.of("work", "main");

    Path absolutePath;
    absolutePath = classOutput.toAbsolutePath();

    UiStyles styles;
    styles = new UiStyles(noteSink, absolutePath);

    StyleSheet sheet;
    sheet = styles.generateStyleSheet();

    byte[] scriptBytes;

    try {
      scriptBytes = Script.getBytes();
    } catch (
      IOException e) {
      throw new RuntimeException(e);
    }

    return new TemplateHeadPlugin(
        sheet.css(),

        new String(scriptBytes, StandardCharsets.UTF_8)
    );
  }

  public static Sql.Transaction beginTransaction(Sql.Transaction.Isolation level) {
    Sql.Database db;
    db = SITE_INJECTOR.db();

    return db.beginTransaction(level);
  }

  private Testing() {}

}