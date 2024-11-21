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
import objectos.way.Sql;
import objectos.way.Web;

@Css.Source
abstract class UiTemplate extends Html.Template implements Http.Handler {

  Http.Exchange http;

  UiSidebar pageSidebar = UiSidebar.HOME;

  String pageTitle = "Objectos PetClinic";

  public static Consumer<Html.Markup> defaultHeadPlugin() {
    return html -> {
      html.link(html.rel("stylesheet"), html.type("text/css"), html.href("/ui/styles.css"));
      html.script(html.src("/ui/script.js"));
    };
  }

  @Override
  public final void handle(Http.Exchange http) {
    this.http = http;

    handle();
  }

  void handle() {
    switch (http.method()) {
      case GET, HEAD -> http.ok(this);

      default -> http.methodNotAllowed();
    }
  }

  @Override
  protected final void render() {
    doctype();

    html(
        className("theme-light"),
        className("size-full bg-background text-text"),

        head0(),

        body0()
    );
  }

  private Html.Instruction.OfElement head0() {
    SiteInjector injector;
    injector = http.get(SiteInjector.class);

    Consumer<Html.Markup> templateHeadPlugin;
    templateHeadPlugin = injector.templateHeadPlugin();

    return head(
        meta(charset("utf-8")),
        meta(httpEquiv("content-type"), content("text/html; charset=utf-8")),
        meta(name("viewport"), content("width=device-width, initial-scale=1")),
        link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png")),
        renderPlugin(templateHeadPlugin),
        title(pageTitle)
    );
  }

  private Html.Instruction.OfElement body0() {
    return body(
        className("size-full"),

        shell()
    );
  }

  private Html.Instruction.OfElement shell() {
    return div(
        className("mx-auto w-full max-w-screen-xl flex items-start"),
        className("body-compact-01"),

        sidebar(),

        main(
            className("grow p-16px"),
            dataFrame("main", mainFrameName()),

            renderFragment(this::renderContents)
        )
    );
  }

  private Html.Instruction.OfElement sidebar() {
    return div(
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
            dataFrame("sidebar", pageSidebar.name()),

            renderFragment(this::sidebarItems)
        )
    );
  }

  private void sidebarItems() {
    for (UiSidebar item : UiSidebar.VALUES) {
      a(
          className("flex items-center px-16px py-8px hover:bg-background-hover"),

          item == pageSidebar ? className("bg-background-selected") : noop(),

          href(item.href),

          div(
              className("pl-2px"),

              raw(item.icon)
          ),

          div(
              className("pl-10px"),

              text(item.title)
          )
      );
    }
  }

  private String mainFrameName() {
    Class<? extends UiTemplate> thisClass;
    thisClass = getClass();

    String simpleName;
    simpleName = thisClass.getSimpleName();

    return simpleName.toLowerCase(Locale.US);
  }

  abstract void renderContents();

  //
  // UI: Data Table
  //

  final Html.Instruction.OfElement dataTable(Html.Fragment.Of0 tableHead, Html.Fragment.Of0 tableBody) {
    return div(
        table(
            className("w-full tr:h-40px"),

            thead(
                className("th:px-16px th:align-middle th:font-600"),

                renderFragment(tableHead)
            ),

            tbody(
                className(
                    "tr:transition-colors tr:duration-75 tr:hover:bg-background-hover",
                    "td:border-t td:border-t-border td:px-16px td:align-middle td:text-text-secondary"
                ),

                renderFragment(tableBody)
            )
        )
    );
  }

  //
  // UI: Pagination
  //

  final Html.Instruction.OfElement pagination(String frameName, Web.Paginator paginator) {
    Sql.Page page;
    page = paginator.page();

    String frameValue;
    frameValue = Integer.toString(page.number());

    return div(
        className("flex justify-end"),

        dataFrame(frameName, frameValue),

        paginator.hasPrevious()
            ? paginationLink(UiIcon.CHEVRON_LEFT, "Previous page", paginator.previousHref())
            : paginationDisabled(UiIcon.CHEVRON_LEFT, "Previous page"),

        paginator.hasNext()
            ? paginationLink(UiIcon.CHEVRON_RIGHT, "Next page", paginator.nextHref())
            : paginationDisabled(UiIcon.CHEVRON_RIGHT, "Next page")
    );
  }

  private Html.Instruction paginationLink(UiIcon icon, String label, String href) {
    return a(
        href(href),

        raw(icon.value)
    );
  }

  private Html.Instruction paginationDisabled(UiIcon icon, String label) {
    return button(
        className("cursor-not-allowed"),

        ariaLabel(label),

        disabled(),

        raw(icon.value)
    );
  }

}