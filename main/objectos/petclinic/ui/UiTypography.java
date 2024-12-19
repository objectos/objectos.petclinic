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

import objectos.way.Html;

final class UiTypography {

  public static final Html.ClassName BODY_COMPACT_01 = Html.ClassName.of("""
      font-size:14rx
      line-height:18rx
      font-weight:400
      letter-spacing:0.16px
      """);

  private UiTypography() {}

}