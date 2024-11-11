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
# Objectos PetClinic
#

## Coordinates
GROUP_ID := br.com.objectos
ARTIFACT_ID := objectos.petclinic
VERSION := 001-SNAPSHOT

## javac --release option
JAVA_RELEASE := 21

## Dependencies
H2_SRC := com.h2database/h2/2.3.232
H2_LOCAL := br.com.objectos/h2-petclinic/2.2.232
WAY := br.com.objectos/objectos.way/0.1.7-SNAPSHOT

SLF4J_API := org.slf4j/slf4j-api/1.7.36
SLF4J_NOP := org.slf4j/slf4j-nop/1.7.36
TESTNG := org.testng/testng/7.9.0

# Delete the default suffixes
.SUFFIXES:

#
# petclinic
#

.PHONY: all
all: test

include make/java-core.mk

#
# petclinic@clean
#

include make/common-clean.mk

#
# petclinic@h2
#
# creates alternate h2.jar containing module-info.class
#

include make/java-module-info.mk

com.h2database_MULTI_RELEASE := 21
com.h2database_IGNORE_MISSING_DEPS := 1
$(eval $(call module-info,com.h2database,$(H2_LOCAL),$(H2_SRC),$(SLF4J_API)))

#
# petclinic@compile
#

## compile deps
COMPILE_DEPS := $(WAY)
COMPILE_DEPS += $(H2_LOCAL)

include make/java-compile.mk

#
# petclinic@test-compile
#

## test compile deps
TEST_COMPILE_DEPS := $(TESTNG)

include make/java-test-compile.mk

#
# petclinic@test
#

## test main class
TEST_MAIN := objectos.petclinic.RunTests

## www test runtime dependencies
TEST_RUNTIME_DEPS := $(SLF4J_NOP)

## test --add-modules
TEST_ADD_MODULES := org.testng

## test --add-exports
TEST_ADD_EXPORTS := objectos.petclinic/objectos.petclinic.site=org.testng

## test --add-reads
TEST_ADD_READS := objectos.petclinic=org.testng

include make/java-test.mk

#
# petclinic@dev
#

## dev main class
DEV_MAIN := objectos.petclinic.StartDev

## dev jvm opts
DEV_JVM_OPTS := -Xmx64m
DEV_JVM_OPTS += -XX:+UseSerialGC
ifeq ($(ENABLE_DEBUG),1)
DEV_JVM_OPTS += -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=localhost:7000
endif

## dev --add-exports
DEV_ADD_EXPORTS := objectos.petclinic/objectos.petclinic.site=ALL-UNNAMED

## dev app args
DEV_APP_ARGS := --class-output $(CLASS_OUTPUT)

include make/java-dev.mk
