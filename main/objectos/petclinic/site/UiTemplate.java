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

import java.util.Locale;
import java.util.function.Consumer;
import objectos.way.Css;
import objectos.way.Html;
import objectos.way.Http;

@Css.Source
public abstract class UiTemplate extends Html.Template implements Http.Handler {

  static final Html.ClassName BODY_COMPACT_01 = Html.ClassName.of("""
      text-14px leading-18px font-400 tracking-0.16px
      """);

  private final Consumer<Html.Markup> templateHeadPlugin;

  protected UiTemplate(SiteInjector injector) {
    templateHeadPlugin = injector.templateHeadPlugin();
  }

  public static Consumer<Html.Markup> defaultHeadPlugin() {
    return html -> {
      html.link(html.rel("stylesheet"), html.type("text/css"), html.href("/ui/styles.css"));
      html.script(html.src("/ui/script.js"));
    };
  }

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
            renderPlugin(templateHeadPlugin),
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
        className("mx-auto w-full max-w-screen-xl flex items-start"),
        BODY_COMPACT_01,

        renderFragment(this::renderSidebar),

        main(
            className("grow px-16px"),
            dataFrame("main", mainFrameName()),

            renderFragment(this::renderMain)
        )
    );
  }

  private String mainFrameName() {
    Class<? extends UiTemplate> thisClass;
    thisClass = getClass();

    String simpleName;
    simpleName = thisClass.getSimpleName();

    return simpleName.toLowerCase(Locale.US);
  }

  private void renderSidebar() {
    div(
        className("sticky top-0px w-240px h-screen shrink-0 border-r border-r-border px-16px"),
        BODY_COMPACT_01,

        div(
            className("flex items-center py-24px px-16px"),

            raw(UiIcon.LOGO.value),

            span(
                className("pl-8px"),

                text("Objectos PetClinic")
            )
        ),

        nav(
            renderSidebarItem(UiIcon.HOME, "Home", "/"),

            renderSidebarItem(UiIcon.OWNERS, "Owners", "/owners")
        )
    );
  }

  private Html.Instruction.OfElement renderSidebarItem(UiIcon icon, String title, String href) {
    return a(
        className("flex items-center px-16px py-8px hover:bg-background-hover"),

        href(href),

        div(
            className("pl-2px"),

            raw(icon.value)
        ),

        div(
            className("pl-10px"),

            text(title)
        )
    );
  }

  abstract void renderMain();

}