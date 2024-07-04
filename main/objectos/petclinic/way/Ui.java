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

import objectos.way.Html;
import objectos.way.Http;
import objectos.web.WebResources;
import objectox.petclinic.Injector;

final class Ui extends Http.Module {

  @SuppressWarnings("unused")
  private static final String GENERATE_ME = "overflow-hidden";

  // layout

  static final Html.ClassName HTML = Html.className(
      "theme-white bg-background"
  );

  static final Html.ClassName BODY = Html.className(
      "overflow-auto"
  );

  static final Html.ClassName MAIN = Html.className(
      "px-16px sm:mt-32px"
  );

  static final Html.ClassName CONTAINER = Html.className(
      "w-full max-w-screen-lg mx-auto"
  );

  // $header

  static final Html.ClassName HEADER = Html.className(
      "border-b border-b-border-subtle",
      "bg-background"
  );

  static final Html.ClassName HEADER_CONTENTS = Html.className(
      "grid grid-cols-3 h-header px-16px",
      "sm:flex"
  );

  static final Html.ClassName HEADER_LEFT = Html.className(
      "flex items-center -ml-8px",
      "sm:hidden"
  );

  static final Html.ClassName HEADER_BTN = Html.className(
      "size-32px cursor-pointer flex items-center justify-center outline-1",
      "active:bg-background-active focus:outline-focus hover:bg-background-hover",
      "lg:hidden"
  );

  static final Html.ClassName HEADER_BTN_ICON = Html.className(
      "size-16px"
  );

  static final Html.ClassName HEADER_TITLE = Html.className(
      "flex items-center justify-center text-sm"
  );

  static final Html.ClassName HEADER_NAV_WRAPPER = Html.className(
      "hidden",
      "sm:flex sm:grow sm:justify-end"
  );

  static final Html.ClassName HEADER_NAV = Html.className(
      "flex"
  );

  // $backdrop

  static final Html.ClassName BACKDROP = Html.className(
      "absolute inset-0px z-10 mt-header",
      "hidden",
      "bg-overlay",
      "transition-opacity"
  );

  // $nav

  static final Html.ClassName NAV_FRAME = Html.className(
      "absolute top-header bottom-0px left-0px z-20 w-256px",
      "hidden bg-background pt-20px",
      "border-r border-r-border-subtle"
  );

  static final Html.ClassName NAV_GROUP = Html.className(
      "flex flex-col",

      "sm:h-full sm:flex-row sm:justify-end"
  );

  static final Html.ClassName NAV_LINK = Html.className(
      "flex gap-14px items-center",
      "px-16px py-8px",
      "text-sm",

      "hover:bg-background-hover"
  );

  static final Html.ClassName NAV_LINK_SELECTED = Html.className(
      "bg-background-selected hover:bg-background-selected-hover"
  );

  // page stuff

  static final Html.ClassName PAGE_HEADER = Html.className(
      "py-32px",

      "h1:text-lg"
  );

  static final Html.ClassName PAGE_TABLE = Html.className(
      "w-full overflow-x-auto",

      "table:w-full table:table-fixed table:text-sm table:text-left",
      "tbody:tr:border-t tbody:tr:border-t-border-subtle tbody:tr:hover:bg-background-hover",
      "tr:h-48px"
  );

  static final Html.ClassName FLEX = Html.className("flex");
  static final Html.ClassName HIDDEN = Html.className("hidden");

  static final Html.ClassName W_96PX = Html.className("w-96px");
  static final Html.ClassName W_112PX = Html.className("w-112px");
  static final Html.ClassName W_128PX = Html.className("w-128px");
  static final Html.ClassName W_144PX = Html.className("w-144px");
  static final Html.ClassName W_160PX = Html.className("w-160px");

  private final Injector injector;

  private final WebResources webResources;

  public Ui(Injector injector) {
    this.injector = injector;

    webResources = injector.webResources();
  }

  @Override
  protected final void configure() {
    route("/ui/script.js", webResources::handle);

    route("/ui/styles.css", GET(new UiStyles(injector)));
  }

}