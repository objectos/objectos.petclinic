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

import java.util.EnumSet;
import java.util.Set;
import objectos.petclinic.ui.UiSidebar;

enum SiteSidebar implements UiSidebar.Item {

  HOME(UiIcon.HOME, "Home", "/"),

  OWNERS(UiIcon.OWNERS, "Owners", "/owners");

  private static final Set<? extends UiSidebar.Item> ITEMS = EnumSet.allOf(SiteSidebar.class);

  final String icon;

  final String title;

  final String href;

  private SiteSidebar(UiIcon icon, String title, String href) {
    this.icon = icon.value;

    this.title = title;

    this.href = href;
  }

  @Override
  public final UiSidebar toUi() {
    return UiSidebar.create(config -> {
      config.logo = html -> html.raw(UiIcon.LOGO.value);

      config.items = ITEMS;

      config.current = this;
    });
  }

  @Override
  public String icon() { return icon; }

  @Override
  public String title() { return title; }

  @Override
  public String href() { return href; }

}