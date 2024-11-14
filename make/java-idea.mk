#
# Copyright (C) 2023-2024 Objectos Software LTDA.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#
# Allow importing a Java project in IntelliJ IDEA
#

ifndef COMPILE_MARKER
$(error Required java-compile.mk was not included)
endif

ifndef TEST_COMPILE_MARKER
$(error Required java-test-compile.mk was not included)
endif

## let's generate at the root of the project
## - always generate the file
IDEA := $(MODULE).iml
.PHONY: $(IDEA)

## compile deps
ifdef COMPILE_RESOLUTION_FILES
IDEA_COMPILE_PATHS := $(sort $(foreach f,$(COMPILE_RESOLUTION_FILES),$(file < $(f))))
endif

## test-compile deps
ifdef TEST_COMPILE_RESOLUTION_FILES
IDEA_TEST_COMPILE_PATHS := $(sort $(foreach f,$(TEST_COMPILE_RESOLUTION_FILES),$(file < $(f))))

ifdef IDEA_COMPILE_PATHS
IDEA_TEST_COMPILE_PATHS := $(filter-out $(IDEA_COMPILE_PATHS),$(IDEA_TEST_COMPILE_PATHS))
endif
endif

## generate deps contents
define IDEA_DEPENDENCY =
    <orderEntry type="module-library"$(2)>
      <library>
        <CLASSES>
          <root url="jar://$(1)!/" />
        </CLASSES>
        <JAVADOC />
        <SOURCES />
      </library>
    </orderEntry>

endef

IDEA_DEPS :=

ifdef IDEA_COMPILE_PATHS
IDEA_DEPS += $(foreach jar,$(IDEA_COMPILE_PATHS),$(call IDEA_DEPENDENCY,$(jar),))
endif

ifdef IDEA_TEST_COMPILE_PATHS
IDEA_DEPS += $(foreach jar,$(IDEA_TEST_COMPILE_PATHS),$(call IDEA_DEPENDENCY,$(jar), scope="TEST"))
endif

## generate module XML file contents
define IDEA_CONTENTS :=
<?xml version="1.0" encoding="UTF-8"?>
<module type="JAVA_MODULE" version="4">
  <component name="NewModuleRootManager">
    <output url="file://$$MODULE_DIR$$/work/main" />
    <output-test url="file://$$MODULE_DIR$$/work/test" />
    <exclude-output />
    <content url="file://$$MODULE_DIR$$">
      <sourceFolder url="file://$$MODULE_DIR$$/main" isTestSource="false" />
      <sourceFolder url="file://$$MODULE_DIR$$/test" isTestSource="true" />
    </content>
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
$(IDEA_DEPS)  </component>
</module>
endef

## where to store IDEA config files
IDEA_DIR := .idea

## IDEA modules.xml
IDEA_MODULES := $(IDEA_DIR)/modules.xml
.PHONY: $(IDEA_MODULES)

## IDEA modules.xml contents
define IDEA_MODULES_CONTENTS :=
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
      <module fileurl="file://$$PROJECT_DIR$$/$(IDEA)" filepath="$$PROJECT_DIR$$/$(IDEA)" />
    </modules>
  </component>
</project>
endef 

## force dep resolving
IDEA_REQS := $(COMPILE_RESOLUTION_FILES)
IDEA_REQS += $(TEST_COMPILE_RESOLUTION_FILES)

#
# Targets
#

.PHONY: idea
idea: $(IDEA) $(IDEA_MODULES)

$(IDEA): $(IDEA_REQS)
	$(file > $@,$(IDEA_CONTENTS))

$(IDEA_MODULES): | $(IDEA_DIR)
	$(file > $@,$(IDEA_MODULES_CONTENTS))

$(IDEA_DIR):
	mkdir --parents $@