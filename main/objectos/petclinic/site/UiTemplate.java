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

  @Override
  protected final void render() {
    doctype();

    html(
        className("theme-light"),
        className("size-full bg-background text-text"),

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
            className("size-full"),

            renderFragment(this::renderShell)
        )
    );
  }

  abstract void renderHead();

  private void renderShell() {
    div(
        className("mx-auto w-full max-w-screen-2xl flex items-start px-16px 2xl:px-32px"),

        div(
            className("sticky top-0px w-256px shrink-0"),

            text("sidenav"),

            renderFragment(this::contents, 1)
        ),

        main(
            className("grow"),

            text("main"),

            renderFragment(this::contents, 10)
        )
    );
  }

  private void contents(int count) {
    for (int i = 0; i < count; i++) {
      p("""
      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ac enim vel ante tincidunt tincidunt ut venenatis massa. Integer euismod, nibh ac bibendum facilisis, orci felis blandit urna, at convallis ligula tortor non magna. Nam purus odio, scelerisque at lorem vel, dapibus maximus nunc. Morbi ut libero sed diam sodales posuere non a purus. In vitae magna nec sem varius interdum eget quis libero. Cras eget felis volutpat, condimentum orci id, venenatis felis. Morbi rutrum, velit id placerat facilisis, lorem dui posuere leo, vitae feugiat nibh lacus ac dui. Suspendisse sit amet porta leo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam et tempus odio. Duis ullamcorper ante at nisl scelerisque eleifend.
      """);
    }
  }

}