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

import objectos.way.Css;
import objectos.way.Http;

@Css.Source
final class Owners extends UiTemplate {

  Owners(SiteInjector injector) {
    super(injector);
  }

  @Override
  public final void handle(Http.Exchange http) {
    switch (http.method()) {
      case GET, HEAD -> http.ok(this);

      default -> http.methodNotAllowed();
    }
  }

  @Override
  final void renderHead() {
    title("Owners | Objectos PetClinic");
  }

  @Override
  final void renderMain() {
    h1("Owners");
  }

}