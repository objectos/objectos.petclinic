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
import objectos.html.Api.Element;
import objectos.html.Api.GlobalAttribute;
import objectos.html.icon.TablerIcons;

enum UiSection {

  HOME(TablerIcons::home2, "Home", "/"),

  OWNERS(TablerIcons::users, "Owners", "/owners"),

  VETS(TablerIcons::healthRecognition, "Veterinarians", "/vets"),

  ERROR(TablerIcons::exclamationCircle, "Error", "/oups");

  public static final List<UiSection> ALL = List.of(UiSection.values());

  public final BiFunction<TablerIcons, GlobalAttribute, Element> icon;

  public final String title;

  public final String href;

  private UiSection(BiFunction<TablerIcons, GlobalAttribute, Element> icon, String title, String href) {
    this.icon = icon;

    this.title = title;

    this.href = href;
  }

}