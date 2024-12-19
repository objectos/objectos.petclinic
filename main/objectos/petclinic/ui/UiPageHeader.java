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

public final class UiPageHeader extends Html.Template {

  public static final class Config {

    public String title;

    public String subtitle;

  }

  private final String title;

  private final String subtitle;

  private UiPageHeader(Config config) {
    title = Objects.requireNonNull(config.title, "title == null");

    subtitle = Objects.requireNonNull(config.subtitle, "subtitle == null");
  }

  public static UiPageHeader create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiPageHeader(c);
  }

  @Override
  protected final void render() {
    header(

        className("padding-x-16px"),

        h1(
            className("font-size-28px font-weight-400 letter-spacing-0px line-height-36px"),

            text(title)
        ),

        p(
            className("body-compact-01"),

            text(subtitle)
        )

    );
  }

}