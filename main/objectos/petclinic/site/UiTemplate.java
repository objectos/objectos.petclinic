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
abstract class UiTemplate extends Html.Template implements Http.Handler {

  private final Consumer<Html.Markup> templateHeadPlugin;

  UiTemplate(SiteInjector injector) {
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
        className("body-compact-01"),

        renderFragment(this::renderSidebar),

        div(
            className("grow"),
            dataFrame("main", mainFrameName()),

            renderFragment(this::renderMain)
        )
    );
  }

  private void renderSidebar() {
    div(
        className("sticky top-0px w-240px h-screen shrink-0 border-r border-r-border px-16px"),
        className("body-compact-01"),

        div(
            className("flex items-center py-20px px-16px"),

            raw(UiIcon.LOGO.value),

            span(
                className("pl-8px"),

                text("Objectos PetClinic")
            )
        ),

        nav(
            className("pt-16px"),

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

  private String mainFrameName() {
    Class<? extends UiTemplate> thisClass;
    thisClass = getClass();

    String simpleName;
    simpleName = thisClass.getSimpleName();

    return simpleName.toLowerCase(Locale.US);
  }

  abstract void renderMain();

  //
  // UI Components
  //

  //
  // UI: Breadcrumb
  //

  record BreadcrumbItem(String name, String href) {}

  final Html.Instruction.OfElement breadcrumb(BreadcrumbItem... items) {
    return nav(
        ariaLabel("breadcrumb"),

        className("h-64px flex"),

        renderFragment(this::renderBreadcrumbItems, items)
    );
  }

  @SuppressWarnings("unused")
  private void renderBreadcrumbItems(BreadcrumbItem... items) {
    for (BreadcrumbItem item : items) {

    }
  }

  final BreadcrumbItem breadcrumbItem(String name) {
    return new BreadcrumbItem(name, null);
  }

  final Html.Instruction.OfElement contents(Html.Instruction.OfElement... elements) {
    return main(
        className("p-16px"),

        flatten(elements)
    );
  }

  //
  // UI: Data Table
  //

  final Html.Instruction.OfElement dataTable(Html.Fragment.Of0 tableHead, Html.Fragment.Of0 tableBody) {
    return div(
        table(
            className("w-full tr:h-40px"),

            thead(
                className(
                    "th:px-16px",
                    "th:align-middle th:text-text-primary"
                ),

                renderFragment(tableHead)
            ),

            tbody(
                className(
                    "tr:transition-colors tr:duration-75",
                    "tr:hover:bg-background-hover",

                    "td:border-t td:border-t-border",
                    "td:px-16px",
                    "td:align-middle td:text-text-secondary"
                ),

                renderFragment(tableBody)
            )
        )
    );
  }

}