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
import objectos.way.Script;

public final class UiSearch extends Html.Template {

  public static final class Config {

    public String id = "search";

    public String action;

    public String parameterName = "q";

    public String placeholder;

  }

  private final Html.Id id;

  private final String action;

  private final String parameterName;

  private final String placeholder;

  private UiSearch(Config config) {
    id = Html.Id.of(config.id);

    action = Objects.requireNonNull(config.action, "action == null");

    parameterName = Objects.requireNonNull(config.parameterName, "parameterName == null");

    placeholder = Objects.requireNonNull(config.placeholder, "placeholder == null");
  }

  public static UiSearch create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiSearch(c);
  }

  @Override
  protected final void render() {
    form(
        id,

        className("relative flex-1"),

        action(action),

        method("get"),

        span(
            className("""
            position-absolute left-14px top-14px
            svg:size-20px svg:stroke-icon-secondary
            """),

            raw("""
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
            </svg>
            """)
        ),

        input(
            className("""
            width-100%
            height-48px
            background-color-field
            border-0px
            border-b-1px_solid_gray-500
            padding-x-48px
            outline-none
            -outline-offset-2px
            focus:outline-focus
            """),

            name(parameterName),

            type("text"),

            autocomplete("off"),

            placeholder(placeholder),

            tabindex("0"),

            dataOnInput(
                Script.delay(500, Script.submit(id))
            )
        )
    );
  }

}