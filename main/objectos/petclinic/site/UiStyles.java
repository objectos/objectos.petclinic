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

    http.status(Http.Status.OK);

    http.dateNow();

    http.header(Http.HeaderName.CONTENT_TYPE, s.contentType());

    byte[] bytes;
    bytes = s.toByteArray();

    http.header(Http.HeaderName.CONTENT_LENGTH, bytes.length);

    http.send(bytes);
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
          --ui-text: #161616;
          --ui-text-secondary: #525252;
        }
        """),

        Css.overrideFontSize("""
        14px: 0.875rem
        """),

        Css.overrideLineHeight("""
        18px: 1.125rem
        """),

        Css.overrideFontWeight("""
        400: 400
        600: 600
        """),

        Css.overrideLetterSpacing("""
        0.16px: 0.16px
        """),

        componentsTypography(),

        extendColors(),

        extendSpacing(),

        Css.variants("""
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
    text: var(--ui-text)
    text-secondary: var(--ui-text-secondary)
    """);
  }

  private Css.Option extendSpacing() {
    return Css.extendSpacing("""
    0px: 0px
    0.5px: 0.5px
    1px: 0.0625rem
    2px: 0.125rem
    4px: 0.25rem
    6px: 0.375rem
    8px: 0.5rem

    10px: 0.625rem
    12px: 0.75rem
    14px: 0.875rem
    16px: 1rem

    20px: 1.25rem
    24px: 1.5rem
    28px: 1.75rem

    32px: 2rem
    36px: 2.25rem

    40px: 2.5rem
    44px: 2.75rem
    48px: 3rem

    56px: 3.5rem

    64px: 4rem

    80px: 5rem

    96px: 6rem

    112px: 7rem
    128px: 8rem
    144px: 9rem
    160px: 10rem
    176px: 11rem
    192px: 12rem

    208px: 13rem
    224px: 14rem
    240px: 15rem
    256px: 16rem
    288px: 18rem
    320px: 20rem
    384px: 24rem
    """);
  }

}