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

import java.nio.file.Path;
import objectos.way.App;
import objectos.way.Css;
import objectos.way.Http;

public final class UiStyles implements Http.Handler {

  private final App.NoteSink noteSink;

  private final Path stylesScanDirectory;

  public UiStyles(SiteInjector injector) {
    noteSink = injector.noteSink();

    stylesScanDirectory = injector.stylesScanDirectory();
  }

  public UiStyles(App.NoteSink noteSink, Path stylesScanDirectory) {
    this.noteSink = noteSink;

    this.stylesScanDirectory = stylesScanDirectory;
  }

  @Override
  public final void handle(Http.Exchange http) {
    switch (http.method()) {
      case GET, HEAD -> get(http);

      default -> http.methodNotAllowed();
    }
  }

  private void get(Http.Exchange http) {
    Css.StyleSheet s;
    s = generateStyleSheet();

    http.ok(s);
  }

  public final Css.StyleSheet generateStyleSheet() {
    return Css.generateStyleSheet(
        Css.noteSink(noteSink),

        Css.scanDirectory(stylesScanDirectory),

        Css.baseLayer("""
        .theme-light {
          --ui-background: #f4f4f4;
          --ui-background-hover: rgba(141, 141, 141, 0.12);
          --ui-background-selected: rgba(141, 141, 141, 0.2);
          --ui-border: #c6c6c6;
          --ui-button-primary: #0f62fe;
          --ui-button-primary-active: #002d9c;
          --ui-button-primary-hover: #0050e6;
          --ui-field: #ffffff;
          --ui-focus: #0f62fe;
          --ui-icon-secondary: #525252;
          --ui-layer: #ffffff;
          --ui-layer-hover: #e8e8e8;
          --ui-overlay: rgba(22, 22, 22, 0.5);
          --ui-text: #161616;
          --ui-text-on-color: #ffffff;
          --ui-text-secondary: #525252;
        }
        """),

        Css.overrideFontSize("""
        14px: 0.875rem
        20px: 1.25rem
        28px: 1.75rem
        32px: 2rem
        """),

        Css.overrideLineHeight("""
        18px: 1.125rem
        28px: 1.75rem
        36px: 2.25rem
        40px: 2.5rem
        """),

        Css.overrideFontWeight("""
        400: 400
        600: 600
        """),

        Css.overrideLetterSpacing("""
        0px: 0px
        0.16px: 0.16px
        """),

        componentsTypography(),

        extendColors(),

        Css.variants("""
        svg: & svg
        tbody: & tbody
        td: & td
        th: & th
        thead: & thead
        tr: & tr
        """)
    );
  }

  private Css.Option componentsTypography() {
    return Css.components("""
    # body-compact-01
    text-14px leading-18px font-400 tracking-0.16px
    """);
  }

  private Css.Option extendColors() {
    return Css.extendColors("""
    background: var(--ui-background)
    background-hover: var(--ui-background-hover)
    background-selected: var(--ui-background-selected)
    border: var(--ui-border)
    button-primary: var(--ui-button-primary)
    button-primary-active: var(--ui-button-primary-active)
    button-primary-hover: var(--ui-button-primary-hover)
    field: var(--ui-field)
    focus: var(--ui-focus)
    icon-secondary: var(--ui-icon-secondary)
    layer: var(--ui-layer)
    layer-hover: var(--ui-layer-hover)
    overlay: var(--ui-overlay)
    text: var(--ui-text)
    text-on-color: var(--ui-text-on-color)
    text-secondary: var(--ui-text-secondary)
    """);
  }

}