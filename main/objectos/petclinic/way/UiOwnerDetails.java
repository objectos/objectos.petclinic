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
import objectos.way.Html;

final class UiOwnerDetails extends Html.Template {

  private final ResultSet rs;

  public UiOwnerDetails(ResultSet rs) {
    this.rs = rs;
  }

  @Override
  protected final void render() {
    dl(
        className("grid grid-cols-2 text-sm"),

        f(this::renderItems)
    );
  }

  private void renderItems() throws SQLException {
    item("Name");

    item("Address");

    item("City");

    item("Telephone");
  }

  private void item(String description) throws SQLException {
    dt(
        className("border-b border-b-border-subtle py-12px"),
        t(description)
    );

    dd(
        className("border-b border-b-border-subtle py-12px"),
        t(rs.getString(description))
    );
  }

}