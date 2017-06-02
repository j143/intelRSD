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

import com.intel.podm.actions.ActionException;
import com.intel.podm.actions.PcieZoneActionsInvoker;
import com.intel.podm.business.entities.dao.GenericDao;
import com.intel.podm.business.entities.redfish.Endpoint;
import com.intel.podm.business.entities.redfish.Zone;
import com.intel.podm.common.enterprise.utils.logger.TimeMeasured;
import com.intel.podm.common.types.Id;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

import static com.intel.podm.common.utils.Contracts.requires;
import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Dependent
public class PcieLocalStorageAssemblyTask extends NodeAssemblyTask {
    private Id zoneId;
    private Id endpointId;

    @Inject
    private PcieZoneActionsInvoker pcieZoneActionsInvoker;

    @Inject
    private GenericDao genericDao;

    @Override
    @Transactional(REQUIRES_NEW)
    @TimeMeasured(tag = "[AssemblyTask]")
    public void run() {
        final String exceptionMessage = "Null value is not allowed for this method.";
        requires(zoneId != null, exceptionMessage);
        requires(endpointId != null, exceptionMessage);

        Zone zone = genericDao.find(Zone.class, zoneId);
        Endpoint endpoint = genericDao.find(Endpoint.class, endpointId);

        try {
            pcieZoneActionsInvoker.attachEndpoint(zone, endpoint);
        } catch (ActionException e) {
            throw new RuntimeException("Updating Zone failed", e);
        }
    }

    public void setZoneId(Id zoneId) {
        this.zoneId = zoneId;
    }

    public void setEndpointId(Id endpointId) {
        this.endpointId = endpointId;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public UUID getServiceUuid() {
        return genericDao.find(Zone.class, zoneId).getService().getUuid();
    }
}
