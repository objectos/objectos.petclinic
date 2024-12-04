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

import java.util.Objects;
import java.util.function.Consumer;
import objectos.way.Css;
import objectos.way.Html;
import objectos.way.Script;
import objectos.way.Sql;
import objectos.way.Web;

@Css.Source
abstract class UiTemplate extends Html.Template {

  static abstract class Config {

    SiteInjector injector;

  }

  final SiteInjector injector;

  UiTemplate(Config config) {
    injector = Objects.requireNonNull(config.injector, "injector == null");
  }

  public static Consumer<Html.Markup> defaultHeadPlugin() {
    return html -> {
      html.link(html.rel("stylesheet"), html.type("text/css"), html.href("/ui/styles.css"));
      html.script(html.src("/ui/script.js"));
    };
  }

  //
  // UI: shell
  //

  final void shell(
      UiSidebar sidebar,

      String title,

      Html.Fragment.Of0 contents
  ) {
    Objects.requireNonNull(sidebar, "sidebar == null");
    Objects.requireNonNull(title, "title == null");
    Objects.requireNonNull(contents, "contents == null");

    doctype();

    html(
        className("theme-light"),
        className("size-full bg-background text-text"),

        renderFragment(this::shellHead, title),

        renderFragment(this::shellBody, sidebar, contents)
    );
  }

  private void shellHead(String title) {
    Consumer<Html.Markup> templateHeadPlugin;
    templateHeadPlugin = injector.templateHeadPlugin();

    head(
        meta(charset("utf-8")),
        meta(httpEquiv("content-type"), content("text/html; charset=utf-8")),
        meta(name("viewport"), content("width=device-width, initial-scale=1")),
        link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png")),
        renderPlugin(templateHeadPlugin),
        title(title)
    );
  }

  private void shellBody(UiSidebar sidebar, Html.Fragment.Of0 contents) {
    body(
        className("size-full"),

        div(
            className("mx-auto w-full max-w-screen-xl flex items-start"),
            className("body-compact-01"),

            renderFragment(this::shellSidebar, sidebar),

            main(
                className("grow p-16px"),
                dataFrame("main", classSimpleName()),

                renderFragment(contents)
            )
        )
    );
  }

  private String classSimpleName() {
    Class<? extends UiTemplate> thisClass;
    thisClass = getClass();

    return thisClass.getSimpleName();
  }

  private void shellSidebar(UiSidebar pageSidebar) {
    div(
        className("sticky top-0px w-240px h-screen shrink-0 border-r border-r-border px-16px"),
        className("body-compact-01"),

        div(
            className("flex items-center py-28px px-16px"),

            raw(UiIcon.LOGO.value),

            span(
                className("pl-8px"),

                text("Objectos PetClinic")
            )
        ),

        nav(
            className("pt-16px"),
            dataFrame("sidebar", pageSidebar.name()),

            renderFragment(this::shellSidebarItems, pageSidebar)
        )
    );
  }

  private void shellSidebarItems(UiSidebar pageSidebar) {
    for (UiSidebar item : UiSidebar.VALUES) {
      a(
          className("flex items-center px-16px py-8px hover:bg-background-hover"),

          item == pageSidebar ? className("bg-background-selected") : noop(),

          dataOnClick(Script.navigate()),

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

  private static final Html.ClassName __BUTTON_BASE = Html.ClassName.of(
      Html.ClassName.of("text-14px leading-18px font-400 tracking-0.16px"),

      Html.ClassName.of("""
      relative m-0px inline-flex shrink-0
      cursor-pointer appearance-none
      text-start align-top
      outline-0
      transition-all duration-100
      focus:border-focus
      focus:shadow-[inset_0_0_0_1px_var(--ui-focus),inset_0_0_0_2px_var(--ui-background)]
      """)
  );

  private static final Html.ClassName __BUTTON_DISABLED_STANDARD = Html.ClassName.of("""
      disabled:cursor-not-allowed
      disabled:border-button-disabled
      disabled:bg-button-disabled
      disabled:text-text-on-color-disabled
      disabled:shadow-none
      """);

  private static final Html.ClassName __BUTTON_JUSTIFY_STANDARD = Html.ClassName.of("""
      justify-between
      """);

  private static final Html.ClassName __BUTTON_PADDING_STANDARD = Html.ClassName.of("""
      pr-[63px] pl-[15px]
      """);

  private static final Html.ClassName __BUTTON_SIZE_LG = Html.ClassName.of("""
      w-max max-w-320px min-h-48px py-14px
      """);

  private static final Html.ClassName __BUTTON_PRIMARY = Html.ClassName.of("""
      bg-button-primary
      border border-transparent
      text-text-on-color
      active:bg-button-primary-active
      hover:bg-button-primary-hover
      """);

  private static final Html.ClassName BUTTON_PRIMARY = Html.ClassName.of(__BUTTON_BASE, __BUTTON_DISABLED_STANDARD, __BUTTON_JUSTIFY_STANDARD, __BUTTON_PADDING_STANDARD, __BUTTON_PRIMARY);

  //
  // Ui: Button
  //

  final Html.Instruction.OfElement renderButton(Html.Instruction... attributes) {
    return button(BUTTON_PRIMARY, __BUTTON_SIZE_LG, flatten(attributes));
  }

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
        dataOnClick(Script.navigate()),

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