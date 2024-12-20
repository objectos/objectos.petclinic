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
package objectos.petclinic;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import objectos.petclinic.boot.PetClinicH2;
import objectos.petclinic.site.SiteInjector;
import objectos.way.App;
import objectos.way.Http;
import objectos.way.Note;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;
import org.h2.jdbcx.JdbcConnectionPool;

abstract class Start extends App.Bootstrap {

  public static final int DEVELOPMENT_HTTP_PORT = 8004;

  public static final int PRODUCTION_HTTP_PORT = 4004;

  Start() {
  }

  @Override
  protected final void bootstrap() {
    long startTime;
    startTime = System.currentTimeMillis();

    // NoteSink
    App.NoteSink noteSink;
    noteSink = noteSink();

    Note.Ref0 startNote;
    startNote = Note.Ref0.create(getClass(), "Start", Note.INFO);

    noteSink.send(startNote);

    // ShutdownHook
    App.ShutdownHook shutdownHook;
    shutdownHook = App.ShutdownHook.create(config -> config.noteSink(noteSink));

    shutdownHook.registerIfPossible(noteSink);

    // Sql.Database
    Sql.Database db;
    db = db(noteSink, shutdownHook);

    // Web.Resources
    Web.Resources webResources;
    webResources = webResources(noteSink);

    shutdownHook.register(webResources);

    // Injector
    SiteInjector injector;
    injector = new SiteInjector(
        db,
        noteSink,
        webResources,

        stylesHandler(),
        stylesScanDirectory(),
        SiteInjector.defaultTemplateHeadPlugin()
    );

    // HandlerFactory
    Http.HandlerFactory handlerFactory;
    handlerFactory = handlerFactory(shutdownHook, injector);

    // WebServer
    try {
      Http.Server httpServer;
      httpServer = Http.Server.create(config -> {
        config.handlerFactory(handlerFactory);

        config.bufferSize(1024, 4096);

        config.noteSink(noteSink);

        config.port(serverPort());
      });

      shutdownHook.register(httpServer);

      httpServer.start();
    } catch (IOException e) {
      throw App.serviceFailed("Http.Server", e);
    }

    Note.Long1 totalTimeNote;
    totalTimeNote = Note.Long1.create(getClass(), "Total time [ms]", Note.INFO);

    long totalTime;
    totalTime = System.currentTimeMillis() - startTime;

    noteSink.send(totalTimeNote, totalTime);
  }

  abstract App.NoteSink noteSink();

  private Sql.Database db(Note.Sink noteSink, App.ShutdownHook shutdownHook) {
    try {
      JdbcConnectionPool dataSource;
      dataSource = PetClinicH2.create();

      shutdownHook.register(dataSource::dispose);

      return Sql.Database.create(config -> {
        config.dataSource(dataSource);

        config.noteSink(noteSink);
      });
    } catch (SQLException e) {
      throw App.serviceFailed("Sql.Database", e);
    }
  }

  private Web.Resources webResources(Note.Sink noteSink) {
    try {
      return Web.Resources.create(config -> {
        config.noteSink(noteSink);

        config.contentTypes("""
        .js: text/javascript; charset=utf-8
        """);

        config.serveFile("/ui/script.js", Script.getBytes());
      });
    } catch (IOException e) {
      throw App.serviceFailed("Web.Resources", e);
    }
  }

  abstract Http.Handler stylesHandler();

  abstract Path stylesScanDirectory();

  abstract Http.HandlerFactory handlerFactory(App.ShutdownHook shutdownHook, SiteInjector injector);

  abstract int serverPort();

}