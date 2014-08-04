/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.model.internal.inspect.handlers;

import org.gradle.model.Finalize;
import org.gradle.model.internal.inspect.MethodRuleDefinition;
import org.gradle.model.internal.registry.ModelRegistry;

import java.lang.annotation.Annotation;

public class FinalizeRuleDefinitionHandler extends AbstractMutationRuleDefinitionHandler {
    @Override
    protected Class<? extends Annotation> getMarkerAnnotation() {
        return Finalize.class;
    }

    public void register(MethodRuleDefinition ruleDefinition, ModelRegistry modelRegistry, Object target) {
        mutationMethod(modelRegistry, ruleDefinition, true);
    }
}