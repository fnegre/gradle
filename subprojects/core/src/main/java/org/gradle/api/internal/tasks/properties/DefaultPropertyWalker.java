/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.api.internal.tasks.properties;

import org.gradle.api.NonNullApi;
import org.gradle.api.internal.tasks.PropertySpecFactory;
import org.gradle.api.internal.tasks.properties.bean.RootRuntimeBeanNode;
import org.gradle.api.internal.tasks.properties.bean.RuntimeBeanNode;

import java.util.ArrayDeque;
import java.util.Queue;

@NonNullApi
public class DefaultPropertyWalker implements PropertyWalker {
    private final PropertyMetadataStore propertyMetadataStore;

    public DefaultPropertyWalker(PropertyMetadataStore propertyMetadataStore) {
        this.propertyMetadataStore = propertyMetadataStore;
    }

    @Override
    public void visitProperties(PropertySpecFactory specFactory, PropertyVisitor visitor, Object bean) {
        Queue<RuntimeBeanNode> queue = new ArrayDeque<RuntimeBeanNode>();
        queue.add(new RootRuntimeBeanNode(bean, propertyMetadataStore.getTypeMetadata(bean.getClass())));
        while (!queue.isEmpty()) {
            RuntimeBeanNode node = queue.remove();
            node.visitNode(visitor, specFactory, queue, propertyMetadataStore);
        }
    }
}
