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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import objectos.way.Html;
import objectos.way.Icons;

final class UiOwnerVCard extends Html.Template {

  private final Icons.Tabler icons = Icons.tabler(this);

  private final ResultSet rs;

  public UiOwnerVCard(ResultSet rs) {
    this.rs = rs;
  }

  @Override
  protected final void render() throws SQLException {
    String fullName;
    fullName = rs.getString("fullName");

    div(
        className("grid grid-cols-2 gap-8px py-32px"),

        div(
            className("flex justify-center"),

            div(
                className("size-144px flex justify-center items-center border border-border-subtle"),

                icons.user(
                    className("size-64px")
                )
            )
        ),

        div(
            h1(
                className("text-2xl"),
                t(fullName)
            ),

            dl(
                className("pt-12px text-sm"),

                f(this::renderItems)
            )
        )
    );
  }

  private void renderItems() throws SQLException {
    item(icons::mapPin, "Address", "fullAddress");

    item(icons::phone, "Telephone", "telephone");
  }

  private void item(
      Function<Html.AttributeInstruction, Html.ElementInstruction> icon,
      String description, String columnName) throws SQLException {
    div(
        className("flex items-center pt-12px"),
        dt(
            icon.apply(
                className("size-16px")
            ),
            span(
                className("sr-only"),
                t(description)
            )
        ),

        dd(
            className("pl-12px"),
            t(rs.getString(columnName))
        )
    );
  }

}