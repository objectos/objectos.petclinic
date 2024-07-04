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

import objectos.notes.NoteSink;
import objectos.way.Css;
import objectos.way.Http;
import objectox.petclinic.Injector;

final class UiStyles implements Http.Handler {

  private final NoteSink noteSink;

  public UiStyles(Injector injector) {
    noteSink = injector.noteSink();
  }

  private Css.StyleSheet generateStyleSheet() {
    return Css.generateStyleSheet(
        Css.classes(Ui.class, UiOwnerEdit.class, UiOwnerVCard.class, UiPagination.class),

        Css.noteSink(noteSink),

        Css.rule(":root", """
        --color-border-subtle: var(--color-border-subtle-00, #e0e0e0);
        --color-layer: var(--color-layer-01, #f4f4f4);
        """),

        Css.rule(".theme-white", """
        --color-background: #ffffff;
        --color-background-active: rgba(141, 141, 141, 0.5);
        --color-background-brand: #0f62fe;
        --color-background-hover: rgba(141, 141, 141, 0.12);
        --color-background-selected: rgba(141, 141, 141, 0.2);
        --color-background-selected-hover: rgba(141, 141, 141, 0.32);
        --color-border-subtle-00: #e0e0e0;
        --color-border-subtle-01: #c6c6c6;
        --color-border-subtle-02: #e0e0e0;
        --color-border-subtle-03: #c6c6c6;
        --color-button-primary: #0f62fe;
        --color-button-primary-active: #002d9c;
        --color-button-primary-hover: #0050e6;
        --color-focus: #0f62fe;
        --color-icon-disabled: rgba(22, 22, 22, 0.25);
        --color-icon-primary: #161616;
        --color-layer-01: #f4f4f4;
        --color-layer-02: #ffffff;
        --color-layer-03: #f4f4f4;
        --color-overlay: rgba(22, 22, 22, 0.5);
        --color-text-on-color: #ffffff;
        """),

        Css.overrideColors("""
        transparent: transparent

        background: var(--color-background)
        background-active: var(--color-background-active)
        background-hover: var(--color-background-hover)
        background-selected: var(--color-background-selected)
        background-selected-hover: var(--color-background-selected-hover)
        border-subtle: var(--color-border-subtle)
        button-primary: var(--color-button-primary)
        button-primary-active: var(--color-button-primary-active)
        button-primary-hover: var(--color-button-primary-hover)
        focus: var(--color-focus)
        icon-disabled: var(--color-icon-disabled)
        icon-primary: var(--color-icon-primary)
        layer: var(--color-layer)
        overlay: var(--color-overlay)
        text-on-color: var(--color-text-on-color)
        """),

        Css.overrideSpacing("""
        header: 3rem

        0px: 0px
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

        60px: 3.75rem
        64px: 4rem

        80px: 5rem

        96px: 6rem

        112px: 7rem
        128px: 8rem
        144px: 9rem
        160px: 10rem

        208px: 13rem
        224px: 14rem
        240px: 15rem
        256px: 16rem
        288px: 18rem
        """),

        Css.variants("""
        h1: & h1
        svg: & svg
        table: & table
        tbody: & tbody
        tr: & tr
        """)
    );
  }

  @Override
  public final void handle(Http.Exchange http) {
    Css.StyleSheet s;
    s = generateStyleSheet();

    byte[] bytes;
    bytes = s.toByteArray();

    http.status(Http.OK);

    http.dateNow();

    http.header(Http.CONTENT_TYPE, s.contentType());

    http.header(Http.CONTENT_LENGTH, bytes.length);

    http.send(bytes);
  }

}