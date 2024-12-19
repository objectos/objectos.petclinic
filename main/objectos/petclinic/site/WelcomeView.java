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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import objectos.way.Css;

@Css.Source
final class WelcomeView extends UiTemplate {

  static final class Config extends UiTemplate.Config {

    List<WelcomeVisit> visits;

  }

  private final List<WelcomeVisit> visits;

  WelcomeView(Config config) {
    super(config);

    visits = Objects.requireNonNull(config.visits, "visits == null");
  }

  public static WelcomeView create(Consumer<Config> config) {
    Config cfg;
    cfg = new Config();

    config.accept(cfg);

    return new WelcomeView(cfg);
  }

  @Override
  protected final void render() {
    shell(
        SiteSidebar.HOME,

        "Objectos PetClinic",

        this::contents
    );
  }

  private void contents() {
    dataTable(
        this::tableHead,

        this::tableBody
    );
  }

  private void tableHead() {
    tr(
        th(
            className("width:144rx text-align:center"),

            text("Date")
        ),

        th(
            className("width:224rx text-align:start"),

            text("Pet")
        ),

        th(
            className("text-align:start"),

            text("Description")
        )
    );
  }

  private void tableBody() {
    for (WelcomeVisit visit : visits) {
      tr(
          td(
              className("width:144rx text-align:center"),

              testable("visit.date", visit.dateText())
          ),

          td(
              className("width:224rx text-align:start"),

              testable("visit.name", visit.name())
          ),

          td(
              className("text-align:start"),

              testable("visit.description", visit.description())
          )
      );
    }
  }

}