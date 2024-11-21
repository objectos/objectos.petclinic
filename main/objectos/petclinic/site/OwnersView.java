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
import objectos.way.Html;
import objectos.way.Script;
import objectos.way.Web;

@Css.Source
final class OwnersView extends UiTemplate {

  static final class Config extends UiTemplate.Config {

    Web.Paginator paginator;

    List<OwnersRow> rows;

  }

  private final Web.Paginator paginator;

  private final List<OwnersRow> rows;

  private OwnersView(Config config) {
    super(config);

    paginator = Objects.requireNonNull(config.paginator, "paginator == null");

    rows = Objects.requireNonNull(config.rows, "rows == null");
  }

  public static OwnersView create(Consumer<Config> config) {
    Config cfg;
    cfg = new Config();

    config.accept(cfg);

    return new OwnersView(cfg);
  }

  @Override
  protected final void preRender() {
    pageSidebar = UiSidebar.OWNERS;

    pageTitle = "Owners | Objectos PetClinic";
  }

  @Override
  final void renderContents() {
    h1("Owners");

    // tearsheet

    final Html.Id overlay;
    overlay = Html.Id.of("overlay");

    final Html.Id tearsheet;
    tearsheet = Html.Id.of("tearsheet");

    final Script.Action openTearsheet;
    openTearsheet = Script.actions(
        Script.replaceClass(overlay, "invisible", "visible"),
        Script.replaceClass(overlay, "opacity-0", "opacity-100"),
        Script.replaceClass(tearsheet, "translate-y-3/4", "translate-y-0")
    );

    final Script.Action closeTearsheet;
    closeTearsheet = Script.actions(
        Script.replaceClass(overlay, "opacity-0", "opacity-100", true),
        Script.replaceClass(tearsheet, "translate-y-3/4", "translate-y-0", true),
        Script.delay(350, Script.replaceClass(overlay, "invisible", "visible", true))
    );

    div(
        overlay,

        dataOnClick(closeTearsheet),

        className("invisible fixed inset-0px z-tearsheet flex justify-center bg-overlay opacity-0 transition-opacity duration-300"),

        div(
            tearsheet,

            className("""
            flex max-w-screen-sm w-full h-full flex-col
            bg-layer
            outline outline-3 -outline-offset-3 outline-transparent
            transition-transform duration-300
            translate-y-3/4

            sm:mt-48px
            """)
        )
    );

    button(
        className("cursor-pointer"),

        dataOnClick(openTearsheet),

        text("Add owner")
    );

    // search form

    final Html.Id searchForm;
    searchForm = Html.Id.of("search-form");

    form(
        searchForm,

        action("/owners"), method("get"),

        input(
            name("q"), type("text"), autocomplete("off"),
            placeholder("Last name"), tabindex("0"),

            dataOnInput(
                Script.delay(500, Script.submit(searchForm))
            )
        )
    );

    // owners data table

    div(
        dataFrame("owners-data"),

        !rows.isEmpty() ? renderFragment(this::data) : p("No data found")
    );
  }

  private void data() {
    pagination("owners-pagination", paginator);

    dataTable(
        this::tableHead,

        this::tableBody
    );
  }

  private void tableHead() {
    tr(
        className("th:text-start"),

        th(
            text("Name")
        ),

        th(
            text("Address")
        ),

        th(
            text("City")
        ),

        th(
            text("Telephone")
        ),

        th(
            text("Pets")
        )
    );
  }

  private void tableBody() {
    for (OwnersRow row : rows) {
      tr(
          className("td:text-start"),

          td(
              testable("owner.name", row.name())
          ),

          td(
              testable("owner.address", row.address())
          ),

          td(
              testable("owner.city", row.city())
          ),

          td(
              testable("owner.telephone", row.telephone())
          ),

          td(
              testable("owner.pets", row.pets())
          )
      );
    }
  }

}