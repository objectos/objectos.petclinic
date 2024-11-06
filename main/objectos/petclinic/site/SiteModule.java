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

import objectos.way.Http;

public class SiteModule extends Http.Module {

  private final SiteInjector injector;

  public SiteModule(SiteInjector injector) {
    this.injector = injector;
  }

  @Override
  protected final void configure() {
    filter(this::setInjector);

    route("/",
        interceptor(injector::transactional),
        handlerFactory(SiteWelcome::new));

    route("/owners",
        interceptor(injector::transactional),
        handlerFactory(Owners::new));

    route("/ui/styles.css",
        handler(injector.stylesHandler()), // in prod, we serve the file from the filesystem
        handlerFactory(UiStyles::new, injector)); // in dev, we generate the file on each request
    route("/ui/script.js",
        handler(injector.webResources()));
  }

  private void setInjector(Http.Exchange http) {
    // sets the injector on every request
    // (even non-matched requests, which we probably should restrict to only matched requests, BUT it not supported as of yet)
    http.set(SiteInjector.class, injector);
  }

}