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

  static final Script.Action SHOW = Script.actions(
      Script.replaceClass(BACKDROP, "hidden", "block"),
      UiLayout.BODY_HIDE_OVERFLOW
  );

  private static final Script.Action HIDE = Script.actions(
      Script.replaceClass(BACKDROP, "block", "hidden"),
      UiLayout.BODY_AUTO_OVERFLOW
  );

  @Override
  protected final void render() {
    $backdrop();
  }

  private void $backdrop() {
    div(
        BACKDROP,
        Ui.BACKDROP_FULL,

        dataOnClick(HIDE)
    );
  }

}