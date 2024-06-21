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
import objectos.html.Html;
import objectos.html.HtmlTemplate;
import objectos.html.pseudom.HtmlDocument;
import objectos.html.pseudom.HtmlElement;
import objectos.html.pseudom.HtmlNode;
import objectos.html.pseudom.HtmlText;

public class DataTable {

  private List<List<String>> rows;

  private DataTable(List<List<String>> rows) {
    this.rows = rows;
  }

  public static DataTable of(HtmlTemplate template) {
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

    private final HtmlTemplate template;

    private List<List<String>> data = new ArrayList<>();

    private List<String> row;

    private boolean tbody;

    public Builder(HtmlTemplate template) {
      this.template = template;
    }

    public final DataTable build() {
      Html html;
      html = new Html();

      template.accept(html);

      HtmlDocument document;
      document = html.compile();

      for (HtmlNode node : document.nodes()) {
        node(node);
      }

      return new DataTable(
          List.copyOf(data)
      );
    }

    private void node(HtmlNode node) {
      switch (node) {
        case HtmlElement element -> element(element);

        case HtmlText text -> {
          if (row != null) {
            String value;
            value = text.value();

            row.add(value);
          }
        }

        default -> {}
      }
    }

    private void element(HtmlElement element) {
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

    private void children(HtmlElement element) {
      for (HtmlNode node : element.nodes()) {
        node(node);
      }
    }

  }

}