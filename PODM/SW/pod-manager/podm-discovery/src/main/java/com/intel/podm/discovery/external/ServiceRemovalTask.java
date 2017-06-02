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

package com.intel.podm.discovery.external;

import com.intel.podm.business.entities.dao.ExternalServiceDao;
import com.intel.podm.business.entities.redfish.ExternalService;
import com.intel.podm.common.enterprise.utils.logger.ServiceLifecycle;
import com.intel.podm.common.logger.ServiceLifecycleLogger;
import com.intel.podm.config.base.Config;
import com.intel.podm.config.base.Holder;
import com.intel.podm.config.base.dto.ExternalServiceConfig;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Dependent
public class ServiceRemovalTask implements Runnable {
    @Inject @ServiceLifecycle
    private ServiceLifecycleLogger logger;

    @Inject
    private DiscoveryScheduler discoveryScheduler;

    @Inject
    private ExternalServiceDao services;

    @Inject
    @Config(refreshable = true)
    private Holder<ExternalServiceConfig> config;

    @Override
    @Transactional(REQUIRES_NEW)
    public void run() {
        logger.t("checking whether unreachable services should be evicted");

        Duration howLongCanBeRetained = config.get().getServiceRemovalDelay();

        List<ExternalService> unreachableServices = services.getAllUnreachableLongerThan(howLongCanBeRetained);

        for (ExternalService service : unreachableServices) {
            logger.lifecycleInfo(
                    "{} is unreachable longer than {} - will be evicted.", service, howLongCanBeRetained
                );
            discoveryScheduler.cancel(service.getUuid());
            services.remove(service);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
