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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import objectos.way.Html;
import objectos.way.Html.Component;

public final class UiContents extends Html.Template {

  public static final class Config {

    public String frameValue;

    public List<Html.Component> components;

    final List<Component> components() {
      Objects.requireNonNull(components, "components == null");

      return List.copyOf(components);
    }

  }

  private final String frameValue;

  private final List<Html.Component> components;

  private UiContents(Config config) {
    frameValue = Objects.requireNonNull(config.frameValue, "frameValue == null");

    components = config.components();
  }

  public static UiContents create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiContents(c);
  }

  @Override
  public final void render() {
    main(
        className("grow p-16px"),
        dataFrame("main", frameValue),

        renderFragment(this::components)
    );
  }

  private void components() {
    for (Html.Component component : components) {
      renderComponent(component);
    }
  }

}