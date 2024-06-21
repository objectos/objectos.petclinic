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
package objectos.petclinic.way;

import objectos.html.Action;
import objectos.html.ElementId;
import objectos.html.HtmlTemplate;
import objectos.html.Api.Fragment;
import objectos.html.Api.GlobalAttribute;
import objectos.html.icon.TablerIcons;
import objectos.way.Http;
import objectos.way.Web;

/**
 * Main template.
 */
abstract class UiLayout extends HtmlTemplate implements Web.Action {

  private static final ElementId _BODY = ElementId.of("body");
  private static final ElementId _BACKDROP = ElementId.of("backdrop");
  private static final ElementId _BTN_OPEN = ElementId.of("btn-open-menu");
  private static final ElementId _BTN_CLOSE = ElementId.of("btn-close-menu");
  private static final ElementId _NAV = ElementId.of("app-nav");

  protected final TablerIcons icons = new TablerIcons(this);

  protected UiSection section;

  protected String title;

  protected Http.Exchange http;

  UiLayout(Http.Exchange http) {
    this.http = http;
  }

  UiLayout(UiSection section, String title) {
    this.section = section;
    this.title = title;
  }

  @Override
  public final void execute() {
    http.ok(this);
  }

  @Override
  protected final void definition() {
    doctype();
    html(Ui.HTML,
        head(
            f(this::$head)
        ),
        body(_BODY, Ui.BODY,
            f(this::$body)
        )
    );
  }

  private void $head() {
    meta(charset("utf-8"));
    meta(httpEquiv("content-type"), content("text/html; charset=utf-8"));
    meta(name("viewport"), content("width=device-width, initial-scale=1"));
    script(src("/ui/script.js"));
    link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png"));
    link(rel("stylesheet"), type("text/css"), href("/ui/styles.css"));

    if (title != null) {
      title(title);
    }
  }

  private void $body() {
    $header();

    $backdrop();

    $nav();

    main(Ui.MAIN, Ui.CONTAINER,
        f(this::mainContent)
    );
  }

  private void $header() {
    header(Ui.HEADER,
        div(Ui.HEADER_CONTENTS, Ui.CONTAINER,

            div(Ui.HEADER_LEFT,
                button(_BTN_OPEN, Ui.HEADER_BTN, Ui.FLEX,
                    title("Open menu"), $clickOpenMenu(),
                    icons.menu2(Ui.HEADER_BTN_ICON)
                ),

                button(_BTN_CLOSE, Ui.HEADER_BTN, Ui.HIDDEN,
                    title("Close menu"), $clickCloseMenu(),
                    icons.x(Ui.HEADER_BTN_ICON)
                )
            ),

            div(Ui.HEADER_TITLE,
                span("PetClinic")
            ),

            div(Ui.HEADER_NAV_WRAPPER,
                nav(Ui.HEADER_NAV, dataFrame("desktop-nav", section.name()),
                    f(this::$navigationItems)
                )
            )

        )
    );
  }

  private GlobalAttribute $clickOpenMenu() {
    return dataOnClick(
        Action.replaceClass(_BACKDROP, "hidden", "block"),
        Action.replaceClass(_BTN_OPEN, "flex", "hidden"),
        Action.replaceClass(_BTN_CLOSE, "hidden", "flex"),
        Action.replaceClass(_NAV, "hidden", "block"),
        Action.replaceClass(_BODY, "overflow-auto", "overflow-hidden")
    );
  }

  private GlobalAttribute $clickCloseMenu() {
    return dataOnClick(
        Action.replaceClass(_BACKDROP, "block", "hidden"),
        Action.replaceClass(_BTN_OPEN, "hidden", "flex"),
        Action.replaceClass(_BTN_CLOSE, "flex", "hidden"),
        Action.replaceClass(_NAV, "block", "hidden"),
        Action.replaceClass(_BODY, "overflow-hidden", "overflow-auto")
    );
  }

  private void $backdrop() {
    div(
        _BACKDROP,
        Ui.BACKDROP,
        $clickCloseMenu()
    );
  }

  private void $nav() {
    nav(_NAV, Ui.NAV_FRAME, dataFrame("mobile-nav", section.name()),
        div(Ui.NAV_GROUP,
            f(this::$navigationItems)
        )
    );
  }

  private void $navigationItems() {
    for (UiSection item : UiSection.ALL) {
      a(Ui.NAV_LINK,
          item == section ? Ui.NAV_LINK_SELECTED : noop(),
          $clickCloseMenu(),
          dataOnClick(Action.location(item.href)),
          href(item.href), span(item.title)
      );
    }
  }

  protected abstract void mainContent();

  final Fragment pagination(Web.Paginator paginator) {
    UiPagination pagination;
    pagination = new UiPagination(paginator);

    return include(pagination);
  }

}