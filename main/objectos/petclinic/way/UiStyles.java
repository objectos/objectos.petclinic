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

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import objectos.css.WayStyleGen;
import objectos.notes.NoteSink;
import objectos.way.Http;
import objectox.petclinic.Injector;

final class UiStyles implements Http.Handler {

  private final NoteSink noteSink;

  public UiStyles(Injector injector) {
    noteSink = injector.noteSink();
  }

  @Override
  public final void handle(Http.Exchange http) {
    WayStyleGen styleGen;
    styleGen = new WayStyleGen();

    styleGen.noteSink(noteSink);

    styleGen.addRule(":root", """
    --color-border-subtle: var(--color-border-subtle-00, #e0e0e0);
    --color-layer: var(--cds-layer-01, #f4f4f4);
    """);

    styleGen.addRule(".theme-white", """
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
    --color-focus: #0f62fe;
    --color-icon-disabled: rgba(22, 22, 22, 0.25);
    --color-icon-primary: #161616;
    --color-layer-01: #f4f4f4;
    --color-layer-02: #ffffff;
    --color-layer-03: #f4f4f4;
    --color-overlay: rgba(22, 22, 22, 0.5);
    """);

    styleGen.addVariant("h1", "& h1");
    styleGen.addVariant("svg", "& svg");
    styleGen.addVariant("table", "& table");
    styleGen.addVariant("tbody", "& tbody");
    styleGen.addVariant("tr", "& tr");

    styleGen.overrideColors(
        Map.entry("transparent", "transparent"),

        Map.entry("background", "var(--color-background)"),
        Map.entry("background-active", "var(--color-background-active)"),
        Map.entry("background-hover", "var(--color-background-hover)"),
        Map.entry("background-selected", "var(--color-background-selected)"),
        Map.entry("background-selected-hover", "var(--color-background-selected-hover)"),
        Map.entry("border-subtle", "var(--color-border-subtle)"),
        Map.entry("focus", "var(--color-focus)"),
        Map.entry("icon-disabled", "var(--color-icon-disabled)"),
        Map.entry("icon-primary", "var(--color-icon-primary)"),
        Map.entry("layer", "var(--color-layer)"),
        Map.entry("overlay", "var(--color-overlay)")
    );

    styleGen.overrideSpacing(
        px(0),
        px(1), px(2), px(4), px(6), px(8),
        px(10), px(12), px(14), px(16),
        px(20), px(24), px(28),
        px(32), px(36),
        px(40), px(44), px(48),
        px(56),
        px(60), px(64),
        px(96),
        px(112), px(128), px(144), px(160),
        px(256),

        Map.entry("header", "3rem")
    );

    Set<Class<?>> classes;
    classes = Set.of(Ui.class, UiPagination.class);

    String s;
    s = styleGen.generate(classes);

    byte[] bytes;
    bytes = s.getBytes(StandardCharsets.UTF_8);

    http.status(Http.OK);

    http.dateNow();

    http.header(Http.CONTENT_TYPE, "text/css; charset=utf-8");

    http.header(Http.CONTENT_LENGTH, bytes.length);

    http.send(bytes);
  }

  private Entry<String, String> px(int value) {
    String px;
    px = Integer.toString(value) + "px";

    double remValue;
    remValue = ((double) value) / 16;

    String rem;

    if (remValue == Math.rint(remValue)) {
      rem = Integer.toString((int) remValue);
    } else {
      rem = Double.toString(remValue);
    }

    return Map.entry(px, rem + "rem");
  }

}