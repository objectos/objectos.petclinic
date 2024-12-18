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

import java.nio.file.Path;
import objectos.way.App;
import objectos.way.Html;
import objectos.way.Http;
import objectos.way.Sql;
import objectos.way.Web;

@App.DoNotReload
public record SiteInjector(
    Sql.Database db,
    App.NoteSink noteSink,
    Web.Resources webResources,

    Http.Handler stylesHandler,
    Path stylesScanDirectory,
    Html.Component headComponent
) {

  public static Html.Component defaultTemplateHeadPlugin() {
    return UiTemplate.defaultHeadPlugin();
  }

  public final Http.Handler transactional(Http.Handler handler) {
    return http -> {

      Sql.Transaction trx;
      trx = db.beginTransaction(Sql.SERIALIZABLE);

      try {
        http.set(Sql.Transaction.class, trx);

        handler.handle(http);

        trx.commit();
      } catch (Throwable t) {
        throw trx.rollbackAndWrap(t);
      } finally {
        trx.close();
      }

    };
  }

}