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

import objectos.way.Html;
import objectos.way.Script;

final class UiOwnerEdit extends Html.Template {

  private static final Html.Id BACKDROP = Html.id("owner-edit-backdrop");
  private static final Html.Id MODAL = Html.id("owner-edit-modal");

  private static final Script.Action HIDE = Script.actions(
      Script.replaceClass(BACKDROP, "opacity-100", "opacity-0"),
      Script.delay(500, Script.replaceClass(BACKDROP, "visible", "invisible")),
      UiLayout.BODY_AUTO_OVERFLOW
  );

  public static final Script.Action SHOW = Script.actions(
      Script.replaceClass(BACKDROP, "invisible", "visible"),
      Script.replaceClass(BACKDROP, "opacity-0", "opacity-100"),
      UiLayout.BODY_HIDE_OVERFLOW
  );

  @Override
  protected final void render() {
    $backdrop();

    $modal();
  }

  private void $backdrop() {
    div(
        BACKDROP,
        className("invisible fixed inset-0px bg-overlay opacity-0"),
        className("transition-opacity duration-500"),

        dataOnClick(HIDE)
    );
  }

  private void $modal() {
    div(
        MODAL,
        className("invisible fixed inset-0px grid bg-layer")
    );
  }

}