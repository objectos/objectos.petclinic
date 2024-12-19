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
import java.util.Set;
import java.util.function.Consumer;
import objectos.way.Html;
import objectos.way.Script;

public final class UiSidebar extends Html.Template {

  public static final class Config {

    public Html.Component logo;

    public Set<? extends Item> items;

    public Item current;

  }

  public interface Item {

    String name();

    String icon();

    String title();

    String href();

    UiSidebar toUi();

  }

  private final Html.Component logo;

  private final Set<? extends Item> items;

  private final Item current;

  private UiSidebar(Config config) {
    logo = Objects.requireNonNull(config.logo, "logo == null");

    items = Objects.requireNonNull(config.items, "items == null");

    current = Objects.requireNonNull(config.current, "current == null");
  }

  public static UiSidebar create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiSidebar(c);
  }

  @Override
  protected final void render() {
    div(
        className("sticky top-0px w-240px h-screen shrink-0 border-r border-r-border px-16px"),
        className("body-compact-01"),

        div(
            className("flex items-center py-28px px-16px"),

            renderComponent(logo),

            span(
                className("pl-8px"),

                text("Objectos PetClinic")
            )
        ),

        nav(
            className("pt-16px"),
            dataFrame("sidebar", current.name()),

            renderFragment(this::shellSidebarItems)
        )
    );
  }

  private void shellSidebarItems() {
    for (Item item : items) {
      a(
          className("flex items-center px-16px py-8px hover:bg-background-hover"),

          item == current ? className("bg-background-selected") : noop(),

          dataOnClick(Script.navigate()),

          href(item.href()),

          div(
              className("pl-2px"),

              raw(item.icon())
          ),

          div(
              className("pl-10px"),

              text(item.title())
          )
      );
    }
  }

}