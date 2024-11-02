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

import objectos.way.Carbon;
import objectos.way.Css;
import objectos.way.Html;
import objectos.way.Web;

/**
 * Main template.
 */
@Css.Source
abstract class UiLayout extends Carbon.Template {

  @Override
  protected final void render() {
    doctype();

    html(
        className("theme-white"),

        head(
            renderFragment(this::renderStandardHead),
            renderFragment(this::renderHead)
        ),

        body(
            renderFragment(this::renderBody)
        )
    );
  }

  protected abstract void renderHead();

  private void renderBody() {
    header(
        className("theme-g100 header"),

        ariaLabel("Objectos PetClinic"),

        div(
            className("mx-auto flex size-full max-w-screen-max lg:pl-16px max:pl-24px"),

            a(
                className("header-name"),

                href("/"),

                span("Objectos"), nbsp(), text("PetClinic")
            )
        )
    );

    main(
        dataFrame("main"),

        className("header-offset"),

        renderFragment(this::renderContent)
    );
  }

  protected abstract void renderContent() throws Exception;

  final Html.Instruction.OfFragment pagination(Web.Paginator paginator) {
    UiPagination pagination;
    pagination = new UiPagination(paginator);

    return renderTemplate(pagination);
  }

  final String msg(String msg) {
    return msg;
  }

}