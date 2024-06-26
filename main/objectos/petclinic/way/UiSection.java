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

import java.util.List;
import java.util.function.BiFunction;
import objectos.way.Html;
import objectos.way.Icons;

enum UiSection {

  HOME(Icons.Tabler::home2, "Home", "/"),

  OWNERS(Icons.Tabler::users, "Owners", "/owners"),

  VETS(Icons.Tabler::healthRecognition, "Veterinarians", "/vets"),

  ERROR(Icons.Tabler::exclamationCircle, "Error", "/oups");

  public static final List<UiSection> ALL = List.of(UiSection.values());

  public final BiFunction<Icons.Tabler, Html.AttributeInstruction, Html.ElementInstruction> icon;

  public final String title;

  public final String href;

  private UiSection(BiFunction<Icons.Tabler, Html.AttributeInstruction, Html.ElementInstruction> icon, String title, String href) {
    this.icon = icon;

    this.title = title;

    this.href = href;
  }

}