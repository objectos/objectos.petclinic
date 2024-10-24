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
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchService;
import objectos.notes.NoteSink;
import objectos.way.App;
import objectos.way.Carbon;
import objectos.way.Http;
import objectox.petclinic.Injector;

public final class PetClinicDev extends PetClinic {

  private final App.Option<Path> classOutputOption = option(
      "--class-output", ofPath(),
      //description("Where to look for class files in dev mode")
      required(),
      withValidator(Files::isDirectory, "Path must be a directory")
  );

  private PetClinicDev() {}

  public static void main(String[] args) {
    PetClinicDev petClinic;
    petClinic = new PetClinicDev();

    petClinic.start(args);
  }

  @Override
  final App.NoteSink noteSink() {
    return App.NoteSink.OfConsole.create(config -> {});
  }

  @Override
  final Http.Handler carbonHandler(NoteSink noteSink) {
    return Carbon.generateOnGetHandler(noteSink, classOutputOption.get());
  }

  @Override
  final Http.HandlerFactory handlerFactory(App.ShutdownHook shutdownHook, Injector injector) {
    // WatchService
    FileSystem fileSystem;
    fileSystem = FileSystems.getDefault();

    WatchService watchService;

    try {
      watchService = fileSystem.newWatchService();
    } catch (IOException e) {
      throw App.serviceFailed("WatchService", e);
    }

    shutdownHook.register(watchService);

    // App.Reloader
    App.Reloader reloader;

    try {
      reloader = App.createReloader(
          "objectos.petclinic.way.Way",

          watchService,

          App.noteSink(injector.noteSink()),

          App.watchDirectory(classOutputOption.get())
      );

      shutdownHook.register(reloader);
    } catch (IOException e) {
      throw App.serviceFailed("ClassReloader", e);
    }

    return App.createHandlerFactory(reloader, Injector.class, injector);
  }

  @Override
  final int serverPort() {
    return DEVELOPMENT_HTTP_PORT;
  }

}
