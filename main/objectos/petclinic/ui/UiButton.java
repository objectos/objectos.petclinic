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

import java.util.Objects;
import java.util.function.Consumer;
import objectos.way.Html;
import objectos.way.Script;

public final class UiButton extends Html.Template {

  public static final class Config {

    public String id;

    public Script.Action dataOnClick;

    public String text;

  }

  private final Script.Action dataOnClick;

  private final String text;

  private UiButton(Config config) {
    dataOnClick = config.dataOnClick;

    text = Objects.requireNonNull(config.text, "text == null");
  }

  public static UiButton create(Consumer<Config> config) {
    Config c;
    c = new Config();

    config.accept(c);

    return new UiButton(c);
  }

  private static final Html.ClassName __BUTTON_BASE = Html.ClassName.of(
      Html.ClassName.of("text-14px leading-18px font-400 tracking-0.16px"),

      Html.ClassName.of("""
      relative m-0px inline-flex shrink-0
      cursor-pointer appearance-none
      text-start align-top
      outline-0
      transition-all duration-100
      focus:border-focus
      focus:shadow-[inset_0_0_0_1px_var(--ui-focus),inset_0_0_0_2px_var(--ui-background)]
      """)
  );

  private static final Html.ClassName __BUTTON_DISABLED_STANDARD = Html.ClassName.of("""
      disabled:cursor-not-allowed
      disabled:border-button-disabled
      disabled:bg-button-disabled
      disabled:text-text-on-color-disabled
      disabled:shadow-none
      """);

  private static final Html.ClassName __BUTTON_JUSTIFY_STANDARD = Html.ClassName.of("""
      justify-between
      """);

  private static final Html.ClassName __BUTTON_PADDING_STANDARD = Html.ClassName.of("""
      pr-[63px] pl-[15px]
      """);

  private static final Html.ClassName __BUTTON_SIZE_LG = Html.ClassName.of("""
      w-max max-w-320px min-h-48px py-14px
      """);

  private static final Html.ClassName __BUTTON_PRIMARY = Html.ClassName.of("""
      bg-button-primary
      border border-transparent
      text-text-on-color
      active:bg-button-primary-active
      hover:bg-button-primary-hover
      """);

  private static final Html.ClassName BUTTON_PRIMARY = Html.ClassName.of(__BUTTON_BASE, __BUTTON_DISABLED_STANDARD, __BUTTON_JUSTIFY_STANDARD, __BUTTON_PADDING_STANDARD, __BUTTON_PRIMARY);

  @Override
  protected final void render() {
    button(
        BUTTON_PRIMARY,

        __BUTTON_SIZE_LG,

        dataOnClick != null ? dataOnClick(dataOnClick) : noop(),

        text(text)
    );
  }

}