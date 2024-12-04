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

import objectos.way.Css;
import objectos.way.Html;
import objectos.way.Script;
import objectos.way.Web;

@Css.Source
final class UiForm extends Html.Template {

  private final Web.Form form;

  private final Html.Id overlay;

  private final Html.Id tearsheet;

  private final Script.Action showAction;

  private final Script.Action hideAction;

  UiForm(String prefix, Web.Form form) {
    overlay = Html.Id.of(prefix + "-overlay");

    tearsheet = Html.Id.of(prefix + "-tearsheet");

    showAction = Script.actions(
        Script.toggleClass(overlay, "invisible", "visible"),
        Script.toggleClass(overlay, "opacity-0", "opacity-100"),
        Script.toggleClass(tearsheet, "translate-y-3/4", "translate-y-0")
    );

    hideAction = Script.actions(
        Script.toggleClass(overlay, "opacity-0", "opacity-100"),
        Script.toggleClass(tearsheet, "translate-y-3/4", "translate-y-0"),
        Script.delay(350, Script.toggleClass(overlay, "invisible", "visible"))
    );

    this.form = form;
  }

  @Override
  protected final void render() {
    div(
        overlay,

        className("""
        invisible fixed inset-0px z-tearsheet
        flex justify-center
        bg-overlay
        opacity-0 transition-opacity duration-300
        """),

        dataOnClick(hideAction),

        renderTearsheet()
    );
  }

  final Script.Action onCreateAction(Html.Template html) {
    return Script.actions(
        Script.toggleClass(overlay, "opacity-0", "opacity-100"),
        Script.toggleClass(tearsheet, "translate-y-3/4", "translate-y-0"),
        Script.delay(
            350,
            Script.toggleClass(overlay, "invisible", "visible"),
            Script.html(html)
        )
    );
  }

  final Script.Action showAction() {
    return showAction;
  }

  private Html.Instruction.OfElement renderTearsheet() {
    return div(
        tearsheet,

        className("""
        flex max-w-screen-sm w-full h-full flex-col
        bg-layer
        outline outline-3 -outline-offset-3 outline-transparent
        transition-transform duration-300
        translate-y-3/4

        sm:mt-48px
        """),

        // clicking on the tearsheet should not hide the overlay
        dataOnClick(Script.stopPropagation()),

        renderForm()
    );
  }

  private Html.Instruction.OfElement renderForm() {
    return form(
        action(form.action()),

        method("post"),

        fieldset(
            renderFragment(this::renderFields)
        ),

        button(
            className("cursor-pointer"),

            dataOnClick(hideAction),

            text("Cancel")
        ),

        button(
            className("cursor-pointer"),

            type("submit"),

            text("Create")
        )
    );
  }

  private void renderFields() {
    for (Web.Form.Field field : form.fields()) {
      switch (field) {
        case Web.Form.TextInput input -> {

          div(
              label(
                  forAttr(input.id()),

                  text(input.label())
              ),

              input(
                  id(input.id()),

                  name(input.name()),

                  type(input.type())
              )
          );

        }
      }
    }
  }

}