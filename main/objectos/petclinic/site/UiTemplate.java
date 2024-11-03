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
import objectos.way.Html;
import objectos.way.Http;

@Css.Source
abstract class UiTemplate extends Html.Template implements Http.Handler {

  final Ui ui = new Ui(this);

  @Override
  protected final void render() {
    doctype();

    html(
        ui.themeWhite,

        head(
            meta(charset("utf-8")),
            meta(httpEquiv("content-type"), content("text/html; charset=utf-8")),
            meta(name("viewport"), content("width=device-width, initial-scale=1")),
            link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png")),
            link(rel("stylesheet"), type("text/css"), href("/ui/styles.css")),
            script(src("/ui/script.js")),
            renderFragment(this::renderHead)
        ),

        body(
            renderFragment(this::renderBody)
        )
    );
  }

  abstract void renderHead();

  private void renderBody() {
    div(
        div(
            text("sidebar")
        ),

        main(
            text("main")
        )
    );
  }

}