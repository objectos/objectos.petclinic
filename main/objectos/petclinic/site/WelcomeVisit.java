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

import java.time.LocalDate;
import objectos.way.Sql;

/**
 * Represents a row of the visits data table of the welcome page.
 */
record WelcomeVisit(
    String name,
    LocalDate date,
    String description
) {

  static final Sql.Mapper<WelcomeVisit> MAPPER = Sql.createRecordMapper(WelcomeVisit.class);

  final String dateText() {
    return date.toString();
  }

}