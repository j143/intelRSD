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

package com.intel.podm.allocation;

import com.intel.podm.common.logger.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@DependsOn({"DiscoveryStartup"})
public class AllocationStartup {
    @Inject
    private Logger logger;

    @Inject
    private ComposedNodeSanitizer composedNodeSanitizer;

    @PostConstruct
    public void initialize() {
        logger.d("Failing all composed nodes that were not assembled before restart");
        composedNodeSanitizer.sanitizeComputerSystemsUsedInComposedNodes();
    }
}
