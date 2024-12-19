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

  public static Html.Component defaultHeadPlugin() {
    return m -> {
      m.link(m.rel("stylesheet"), m.type("text/css"), m.href("/ui/styles.css"));
      m.script(m.src("/ui/script.js"));
    };
  }

  //
  // UI: shell
  //

  final void shell(
      SiteSidebar sidebar,

      String title,

      Html.Fragment.Of0 contents
  ) {
    Objects.requireNonNull(sidebar, "sidebar == null");
    Objects.requireNonNull(title, "title == null");
    Objects.requireNonNull(contents, "contents == null");

    doctype();

    html(
        className("""
        width:100%
        height:100%
        background-color:background
        color:foreground
        """),

        renderFragment(this::shellHead, title),

        renderFragment(this::shellBody, sidebar, contents)
    );
  }

  private void shellHead(String title) {
    Html.Component templateHeadPlugin;
    templateHeadPlugin = injector.headComponent();

    head(
        meta(charset("utf-8")),
        meta(httpEquiv("content-type"), content("text/html; charset=utf-8")),
        meta(name("viewport"), content("width=device-width, initial-scale=1")),
        link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png")),
        renderComponent(templateHeadPlugin),
        title(title)
    );
  }

  private static final Html.ClassName BODY_COMPACT_01 = Html.ClassName.ofText("""
  font-size:14rx
  line-height:18rx
  font-weight:400
  letter-spacing:0.16px
  """);

  private void shellBody(SiteSidebar sidebar, Html.Fragment.Of0 contents) {
    body(
        classNameText("""
        width:100%
        height:100%
        """),

        div(
            classNameText("""
            width:100%
            max-width:screen-xl
            margin:0_auto
            display:flex
            align-items:flex-start
            """),
            BODY_COMPACT_01,

            renderFragment(this::shellSidebar, sidebar),

            main(
                classNameText("""
                flex-grow:1
                padding:16rx
                """),
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

  private void shellSidebar(SiteSidebar pageSidebar) {
    div(
        classNameText("""
        position:sticky
        top:0px
        width:240rx
        height:100vh
        border-right:1px_solid_border
        padding:0px_16rx
        """),
        BODY_COMPACT_01,

        div(
            classNameText("""
            display:flex
            align-items:center
            padding:28rx_16rx
            """),

            raw(UiIcon.LOGO.value),

            span(
                className("padding-left:8rx"),

                text("Objectos PetClinic")
            )
        ),

        nav(
            className("padding-top:16rx"),
            dataFrame("sidebar", pageSidebar.name()),

            renderFragment(this::shellSidebarItems, pageSidebar)
        )
    );
  }

  private void shellSidebarItems(SiteSidebar pageSidebar) {
    for (SiteSidebar item : SiteSidebar.values()) {
      a(
          classNameText("""
          display:flex
          align-items:center
          padding:8rx_16px
          hover:background-color:accent/10
          """),

          item == pageSidebar ? className("background-color:accent/20") : noop(),

          dataOnClick(Script.navigate()),

          href(item.href),

          div(
              className("padding-left:2rx"),

              raw(item.icon)
          ),

          div(
              className("padding-left:10rx"),

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
            classNameText("""
            width:100%

            tr:height:40rx
            """),

            thead(
                classNameText("""
                th:padding:0_16rx
                th:text-align:middle
                th:font-weight:600
                """),

                renderFragment(tableHead)
            ),

            tbody(
                classNameText("""
                tr.transition-duration:75ms
                tr:transition-property:background-color
                tr:hover:background-color:accent/10

                td:border-top:1px_solid_border
                td:padding:0_16rx
                td:vertical-align:middle
                """),

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
        className("flex justify-end py-8px"),

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
        className("size-40px flex items-center justify-center"),
        className("hover:bg-layer-hover"),

        dataOnClick(Script.navigate()),

        href(href),

        raw(icon.value)
    );
  }

  private Html.Instruction paginationDisabled(UiIcon icon, String label) {
    return button(
        className("size-40px flex items-center justify-center"),
        className("hover:bg-layer-hover"),
        className("cursor-not-allowed"),

        ariaLabel(label),

        disabled(),

        raw(icon.value)
    );
  }

}