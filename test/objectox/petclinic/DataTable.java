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
package objectox.petclinic;

import java.util.ArrayList;
import java.util.List;
import objectos.way.Html;

public class DataTable {

  private final List<List<String>> rows;

  private DataTable(List<List<String>> rows) {
    this.rows = rows;
  }

  public static DataTable of(Html.Template template) {
    Builder builder;
    builder = new Builder(template);

    return builder.build();
  }

  public final List<String> row(int index) {
    return rows.get(index);
  }

  public final int size() {
    return rows.size();
  }

  private static class Builder {

    private final Html.Template template;

    private final List<List<String>> data = new ArrayList<>();

    private List<String> row;

    private boolean tbody;

    public Builder(Html.Template template) {
      this.template = template;
    }

    public final DataTable build() {
      Html.Dom document;
      document = Html.Dom.create(template);

      for (Html.Dom.Node node : document.nodes()) {
        node(node);
      }

      return new DataTable(
          List.copyOf(data)
      );
    }

    private void node(Html.Dom.Node node) {
      switch (node) {
        case Html.Dom.Element element -> element(element);

        case Html.Dom.Text text -> {
          if (row != null) {
            String value;
            value = text.value();

            row.add(value);
          }
        }

        default -> {}
      }
    }

    private void element(Html.Dom.Element element) {
      String name;
      name = element.name();

      switch (name) {
        case "tbody" -> {
          tbody = true;

          children(element);

          tbody = false;
        }

        case "tr" -> {
          if (tbody) {
            row = new ArrayList<>();

            children(element);

            List<String> immutableRow;
            immutableRow = List.copyOf(row);

            data.add(immutableRow);

            row = null;
          } else {
            children(element);
          }
        }

        default -> children(element);
      }
    }

    private void children(Html.Dom.Element element) {
      for (Html.Dom.Node node : element.nodes()) {
        node(node);
      }
    }

  }

}