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

    Web.Form form;

    Web.Paginator paginator;

    List<OwnersRow> rows;

    private UiForm createForm() {
      Objects.requireNonNull(form, "form == null");

      return new UiForm("owners", form);
    }

  }

  private final UiForm createForm;

  private final Web.Paginator paginator;

  private final List<OwnersRow> rows;

  private OwnersView(Config config) {
    super(config);

    createForm = config.createForm();

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
  protected final void render() {
    shell(
        UiSidebar.OWNERS,

        "Owners | Objectos PetClinic",

        this::contents
    );
  }

  final Script.Action onCreateAction() {
    return createForm.onCreateAction(this);
  }

  private final Html.Id searchForm = Html.Id.of("search-form");

  private void contents() {
    // page header

    header(

        className("px-16px"),

        h1(
            className("text-28px leading-36px font-400 tracking-0px"),

            text("Owners")
        ),

        p(
            className("body-compact-01"),

            text("Find a pet owner or add a new one.")
        )

    );

    // page action bar

    div(

        className("mt-28px flex"),

        form(
            searchForm,

            className("relative flex-1"),

            action("/owners"), method("get"),

            span(
                className("""
                absolute left-14px top-14px svg:size-20px svg:stroke-icon-secondary
                """),

                raw(UiIcon.MAGNIFYING_GLASS.value)
            ),

            input(
                className("""
                w-full h-48px border-0 border-b border-b-border bg-field px-48px outline-none -outline-offset-2
                focus:outline-focus
                """),

                name("q"), type("text"), autocomplete("off"),
                placeholder("Find by last name"), tabindex("0"),

                dataOnInput(
                    Script.delay(500, Script.submit(searchForm))
                )
            )
        ),

        // create button

        renderButton(
            dataOnClick(createForm.showAction()),

            text("Add owner")
        )

    );

    // create form

    renderTemplate(createForm);

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