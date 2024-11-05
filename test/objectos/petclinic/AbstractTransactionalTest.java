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
package objectos.petclinic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import objectos.petclinic.boot.Testing;
import objectos.way.Html;
import objectos.way.Http;
import objectos.way.Sql;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class AbstractTransactionalTest {

  protected Sql.Transaction trx;

  @BeforeClass
  public void setUp() {
    trx = Testing.DatabaseSupplier.beginTransaction(Sql.SERIALIZABLE);

    trx.sql(testData());

    trx.scriptUpdate();
  }

  @AfterClass(alwaysRun = true)
  public void cleanUp() {
    if (trx != null) {
      Sql.rollbackAndClose(trx);
    }
  }

  protected abstract String testData();

  protected final String writeResponseBody(Http.TestingExchange http, String methodName) {
    Object body;
    body = http.responseBody();

    return switch (body) {
      case Html.Template html -> writeTemplate(html, methodName);

      default -> throw new UnsupportedOperationException("Unsupported type: " + body.getClass());
    };
  }

  protected final String writeTemplate(Html.Template html, String methodName) {
    String simpleName;
    simpleName = getClass().getSimpleName();

    String fileName;
    fileName = simpleName + "." + methodName + ".html";

    String tmpdir;
    tmpdir = System.getProperty("java.io.tmpdir");

    Path target;
    target = Path.of(tmpdir, fileName);

    try (BufferedWriter w = Files.newBufferedWriter(target, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
      html.writeTo(w);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

    return html.testableText();
  }

}
