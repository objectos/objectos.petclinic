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

  /*
  
  for reference during migration...
  
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

   */

  public final Css.StyleSheet generateStyleSheet() {
    return Css.StyleSheet.generate(config -> {
      config.noteSink(noteSink);

      config.scanDirectory(stylesScanDirectory);

      config.theme("""
      --color-background: var(--color-neutral-100);
      --color-foreground: var(--color-neutral-900);

      --color-accent: var(--color-neutral-500);

      --color-border: var(--color-neutral-300);
      """);
    });
  }

}