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
package objectos.petclinic.way;

import java.util.function.Function;
import objectos.html.Api;
import objectos.html.HtmlTemplate;
import objectos.html.icon.TablerIcons;
import objectos.way.Web;

final class UiPagination extends HtmlTemplate {

  private final Web.Paginator paginator;

  public UiPagination(Web.Paginator paginator) {
    this.paginator = paginator;
  }

  @Override
  protected final void definition() {
    div(
        className("grid grid-cols-2 items-center py-32px text-sm"),

        div(
            t(paginator.firstItem() + " - " + paginator.lastItem() + " of " + paginator.totalCount())
        ),

        div(
            className("flex gap-x-16px justify-end"),

            f(this::paginationControl)
        )
    );
  }

  private void paginationControl() {
    TablerIcons icons;
    icons = new TablerIcons(this);

    if (paginator.hasPrevious()) {
      active(paginator.previousHref(), icons::chevronLeft);
    } else {
      inactive(icons::chevronLeft);
    }

    if (paginator.hasNext()) {
      active(paginator.nextHref(), icons::chevronRight);
    } else {
      inactive(icons::chevronRight);
    }
  }

  private void active(String href, Function<Api.GlobalAttribute, Api.Element> icon) {
    a(
        className("size-40px flex items-center justify-center rounded-full outline-1"),
        className("active:bg-background-active focus:outline-focus hover:bg-background-hover"),
        href(href),
        icon.apply(className("size-20px stroke-icon-primary"))
    );
  }

  private void inactive(Function<Api.GlobalAttribute, Api.Element> icon) {
    span(className("cursor-not-allowed"),
        button(
            className("size-40px flex items-center justify-center rounded-full"),
            className("pointer-events-none"),
            disabled(),
            icon.apply(className("size-20px stroke-icon-disabled"))
        )
    );
  }

}