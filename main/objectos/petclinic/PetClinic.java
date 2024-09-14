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
import java.sql.SQLException;
import objectos.notes.LongNote;
import objectos.notes.Note0;
import objectos.notes.NoteSink;
import objectos.way.App;
import objectos.way.Http;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;
import objectox.petclinic.Injector;
import objectox.petclinic.PetClinicH2;
import org.h2.jdbcx.JdbcConnectionPool;

abstract class PetClinic extends App.Bootstrap {

  public static final int DEVELOPMENT_HTTP_PORT = 8004;

  public static final int PRODUCTION_HTTP_PORT = 4004;

  PetClinic() {
  }

  @Override
  protected final void bootstrap() {
    long startTime;
    startTime = System.currentTimeMillis();

    // NoteSink
    NoteSink noteSink;
    noteSink = noteSink();

    Note0 startNote;
    startNote = Note0.info(getClass(), "Start");

    noteSink.send(startNote);

    // ShutdownHook
    App.ShutdownHook shutdownHook;
    shutdownHook = App.createShutdownHook(noteSink);

    shutdownHook.registerIfPossible(noteSink);

    // Sql.Source
    Sql.Source dataSource;
    dataSource = dataSource(noteSink, shutdownHook);

    // Web.Resources
    Web.Resources webResources;
    webResources = webResources(noteSink);

    shutdownHook.register(webResources);

    // Carbon
    Http.Handler carbonHandler;
    carbonHandler = carbonHandler(noteSink);

    // Injector
    Injector injector;
    injector = new Injector(dataSource, noteSink, webResources, carbonHandler);

    // HandlerFactory
    Http.HandlerFactory handlerFactory;
    handlerFactory = handlerFactory(shutdownHook, injector);

    // WebServer
    try {
      Http.Server httpServer;
      httpServer = Http.createServer(
          handlerFactory,

          Http.bufferSize(1024, 4096),

          Http.noteSink(noteSink),

          Http.port(serverPort())
      );

      shutdownHook.register(httpServer);

      httpServer.start();
    } catch (IOException e) {
      throw App.serviceFailed("WebServer", e);
    }

    LongNote totalTimeNote;
    totalTimeNote = LongNote.info(getClass(), "Total time [ms]");

    long totalTime;
    totalTime = System.currentTimeMillis() - startTime;

    noteSink.send(totalTimeNote, totalTime);
  }

  abstract NoteSink noteSink();

  private Sql.Source dataSource(NoteSink noteSink, App.ShutdownHook shutdownHook) {
    try {
      JdbcConnectionPool dataSource;
      dataSource = PetClinicH2.create();

      shutdownHook.register(dataSource::dispose);

      return Sql.createSource(
          dataSource,

          Sql.noteSink(noteSink)
      );
    } catch (SQLException e) {
      throw App.serviceFailed("SqlDataSource", e);
    }
  }

  private Web.Resources webResources(NoteSink noteSink) {
    try {
      return Web.createResources(
          Web.noteSink(noteSink),

          Web.contentTypes("""
          .js: text/javascript; charset=utf-8
          """),

          Web.serveFile("/ui/script.js", Script.getBytes())
      );
    } catch (IOException e) {
      throw App.serviceFailed("WebResources", e);
    }
  }

  abstract Http.Handler carbonHandler(NoteSink noteSink);

  abstract Http.HandlerFactory handlerFactory(App.ShutdownHook shutdownHook, Injector injector);

  abstract int serverPort();

}