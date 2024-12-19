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
package objectos.petclinic.ui;

import java.util.Objects;
import java.util.function.Consumer;
import objectos.way.Html;

public final class UiShell extends Html.Template {

  public static final class Config {

    public String pageTitle;

    public UiSidebar.Item sidebarItem;

    public UiContents contents;

  }

  private static final Html.ClassName HTML = Html.ClassName.of("""
      width:100%
      height:100%
      background-color:neutral-100
      color:neutral-900
      """);

  private static final Html.ClassName BODY = Html.ClassName.of("""
      width:100%
      height:100%
      """);

  private static final Html.ClassName WRAPPER = Html.ClassName.of("""
      width:100%
      max-width:screen-xl
      margin:0_auto
      display:flex
      align-items:flex-start
      """);

  private final String pageTitle;

  private final UiSidebar.Item sidebarItem;

  private final UiContents contents;

  UiShell(Config config) {
    pageTitle = Objects.requireNonNull(config.pageTitle, "pageTitle == null");

    sidebarItem = Objects.requireNonNull(config.sidebarItem, "sidebarItem == null");

    contents = Objects.requireNonNull(config.contents, "contents == null");
  }

  public static UiShell create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiShell(c);
  }

  @Override
  protected final void render() {
    doctype();

    html(
        HTML,

        renderFragment(this::head0),

        renderFragment(this::body0)
    );
  }

  private void head0() {
    head(
        meta(charset("utf-8")),
        meta(httpEquiv("content-type"), content("text/html; charset=utf-8")),
        meta(name("viewport"), content("width=device-width, initial-scale=1")),
        link(rel("shortcut icon"), type("image/x-icon"), href("/favicon.png")),
        link(rel("stylesheet"), type("text/css"), href("/ui/styles.css")),
        script(src("/ui/script.js")),
        title(pageTitle)
    );
  }

  private void body0() {
    body(
        BODY,

        div(
            WRAPPER, UiTypography.BODY_COMPACT_01,

            renderComponent(sidebarItem.toUi()),

            renderComponent(contents)
        )
    );
  }

}