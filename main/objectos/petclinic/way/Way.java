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

import objectos.way.Http;
import objectos.way.Web;
import objectox.petclinic.Injector;

public class Way extends Web.Module {

  private final Injector injector;

  public Way(Injector injector) {
    this.injector = injector;
  }

  static Web.Paginator paginator(Http.Exchange http, int count) {
    Http.Request.Target target;
    target = http.target();

    String pageAttrName;
    pageAttrName = "page";

    int pageSize;
    pageSize = 5;

    return Web.createPaginator(target, pageAttrName, pageSize, count);
  }

  @Override
  protected final void configure() {
    route(segments(eq("ui"), oneOrMore()), new Ui(injector));

    source(injector.dataSource());

    interceptMatched(this::transactional);

    route(path("/"), GET(action(Welcome::new)));
    route(segments(eq("owners"), zeroOrMore()), new Owners());
    route(segments(eq("vets"), zeroOrMore()), new Vets());
  }

}