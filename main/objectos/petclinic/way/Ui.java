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

import objectos.html.ElementClass;
import objectos.way.Http;
import objectos.web.WebResources;
import objectox.petclinic.Injector;

final class Ui extends Http.Module {

  @SuppressWarnings("unused")
  private static final String GENERATE_ME = "overflow-hidden";

  // layout

  static final ElementClass HTML = ElementClass.of(
      "theme-white bg-background"
  );

  static final ElementClass BODY = ElementClass.of(
      "overflow-auto"
  );

  static final ElementClass MAIN = ElementClass.of(
      "sm:mt-32px"
  );

  static final ElementClass CONTAINER = ElementClass.of(
      "w-full max-w-screen-lg mx-auto"
  );

  // $header

  static final ElementClass HEADER = ElementClass.of(
      "border-b border-b-border-subtle",
      "bg-background"
  );

  static final ElementClass HEADER_CONTENTS = ElementClass.of(
      "grid grid-cols-3 h-header px-16px",
      "sm:flex"
  );

  static final ElementClass HEADER_LEFT = ElementClass.of(
      "flex items-center -ml-8px",
      "sm:hidden"
  );

  static final ElementClass HEADER_BTN = ElementClass.of(
      "size-32px cursor-pointer flex items-center justify-center outline-1",
      "active:bg-background-active focus:outline-focus hover:bg-background-hover",
      "lg:hidden"
  );

  static final ElementClass HEADER_BTN_ICON = ElementClass.of(
      "size-16px"
  );

  static final ElementClass HEADER_TITLE = ElementClass.of(
      "flex items-center justify-center text-sm"
  );

  static final ElementClass HEADER_NAV_WRAPPER = ElementClass.of(
      "hidden",
      "sm:flex sm:grow sm:justify-end"
  );

  static final ElementClass HEADER_NAV = ElementClass.of(
      "flex"
  );

  // $backdrop

  static final ElementClass BACKDROP = ElementClass.of(
      "absolute inset-0px z-10 mt-header",
      "hidden",
      "bg-overlay"
  );

  // $nav

  static final ElementClass NAV_FRAME = ElementClass.of(
      "absolute top-header bottom-0px left-0px z-20 w-256px",
      "hidden bg-background pt-20px",
      "border-r border-r-border-subtle"
  );

  static final ElementClass NAV_GROUP = ElementClass.of(
      "flex flex-col",

      "sm:h-full sm:flex-row sm:justify-end"
  );

  static final ElementClass NAV_LINK = ElementClass.of(
      "flex gap-14px items-center",
      "px-16px py-8px",
      "text-sm",

      "hover:bg-background-hover"
  );

  static final ElementClass NAV_LINK_SELECTED = ElementClass.of(
      "bg-background-selected hover:bg-background-selected-hover"
  );

  // page stuff

  static final ElementClass PAGE_HEADER = ElementClass.of(
      "px-16px py-32px",

      "h1:text-lg"
  );

  static final ElementClass PAGE_MAIN = ElementClass.of(
      "px-16px"
  );

  static final ElementClass PAGE_TABLE = ElementClass.of(
      "w-full overflow-x-auto",

      "table:w-full table:table-fixed table:text-sm table:text-left",
      "tbody:tr:border-t tbody:tr:border-t-border-subtle tbody:tr:hover:bg-background-hover",
      "tr:h-48px"
  );

  // utilities

  static final ElementClass FLEX = ElementClass.of("flex");
  static final ElementClass HIDDEN = ElementClass.of("hidden");

  static final ElementClass W_96PX = ElementClass.of("w-96px");
  static final ElementClass W_112PX = ElementClass.of("w-112px");
  static final ElementClass W_128PX = ElementClass.of("w-128px");
  static final ElementClass W_144PX = ElementClass.of("w-144px");
  static final ElementClass W_160PX = ElementClass.of("w-160px");

  private final Injector injector;

  private final WebResources webResources;

  public Ui(Injector injector) {
    this.injector = injector;

    webResources = injector.webResources();
  }

  @Override
  protected final void configure() {
    route(path("/ui/script.js"), webResources::handle);

    route(path("/ui/styles.css"), GET(new UiStyles(injector)));
  }

}