/*
 * Copyright (c) 2016-2017 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.podm.assembly.tasks;

import com.intel.podm.common.types.Id;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

class NodeAssemblyTaskChainBuilder implements TaskRequester, IdRequester, TaskExecutor {

    private LinkedList<NodeAssemblyTask> tasks;
    private Consumer<RuntimeException> exceptionHandler;

    public static TaskRequester instance() {
        return new NodeAssemblyTaskChainBuilder();
    }

    @Override
    public IdRequester prepareTaskChain(List<NodeAssemblyTask> tasksToRun) {
        this.tasks = new LinkedList<>(tasksToRun);
        return this;
    }

    @Override
    public TaskExecutor forComposedNode(Id composedNodeId) {
        this.tasks.stream().forEach(t -> t.setNodeId(composedNodeId));
        return this;
    }

    @Override
    public void executeWith(Consumer<ChainElement<NodeAssemblyTask>> nodeAssemblyTaskRunner) {
        ChainElement<NodeAssemblyTask> chain = new ChainElement<>(nodeAssemblyTaskRunner, tasks, exceptionHandler);
        chain.execute();
    }

    @Override
    public TaskExecutor useExceptionHandler(Consumer<RuntimeException> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }
}

interface TaskExecutor {
    void executeWith(Consumer<ChainElement<NodeAssemblyTask>> runner);

    TaskExecutor useExceptionHandler(Consumer<RuntimeException> consumer);
}

interface IdRequester {
    TaskExecutor forComposedNode(Id composedNodeId);
}

interface TaskRequester {
    IdRequester prepareTaskChain(List<NodeAssemblyTask> tasksToRun);
}
