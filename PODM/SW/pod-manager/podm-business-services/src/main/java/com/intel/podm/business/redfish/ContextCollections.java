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

package com.intel.podm.business.redfish;

import com.intel.podm.business.entities.redfish.Chassis;
import com.intel.podm.business.entities.redfish.ComposedNode;
import com.intel.podm.business.entities.redfish.ComputerSystem;
import com.intel.podm.business.entities.redfish.Drive;
import com.intel.podm.business.entities.redfish.Endpoint;
import com.intel.podm.business.entities.redfish.EthernetInterface;
import com.intel.podm.business.entities.redfish.EthernetSwitch;
import com.intel.podm.business.entities.redfish.EthernetSwitchPort;
import com.intel.podm.business.entities.redfish.LogicalDrive;
import com.intel.podm.business.entities.redfish.Manager;
import com.intel.podm.business.entities.redfish.Memory;
import com.intel.podm.business.entities.redfish.PcieDevice;
import com.intel.podm.business.entities.redfish.PcieDeviceFunction;
import com.intel.podm.business.entities.redfish.PhysicalDrive;
import com.intel.podm.business.entities.redfish.Port;
import com.intel.podm.business.entities.redfish.Processor;
import com.intel.podm.business.entities.redfish.RemoteTarget;
import com.intel.podm.business.entities.redfish.SimpleStorage;
import com.intel.podm.business.entities.redfish.Storage;
import com.intel.podm.business.entities.redfish.StorageService;
import com.intel.podm.business.entities.redfish.Switch;
import com.intel.podm.business.entities.redfish.base.DiscoverableEntity;
import com.intel.podm.business.services.context.Context;
import com.intel.podm.common.types.Id;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@SuppressWarnings({"checkstyle:MethodCount"})
public final class ContextCollections {
    private ContextCollections() {
    }

    public static Set<Id> getAsIdSet(Collection<? extends DiscoverableEntity> discoverableEntities) {
        return transform(discoverableEntities, DiscoverableEntity::getId);
    }

    public static Set<Id> getComposedNodesAsIdSet(Collection<ComposedNode> composedNodes) {
        return transform(composedNodes, ComposedNode::getId);
    }

    public static Set<Context> asLogicalDriveContexts(Set<LogicalDrive> logicalDrives) {
        return transform(logicalDrives, Contexts::toContext);
    }

    public static Set<Context> asSimpleStorageContexts(Set<SimpleStorage> simpleStorages) {
        return transform(simpleStorages, Contexts::toContext);
    }

    public static Set<Context> asPhysicalDriveContexts(Set<PhysicalDrive> physicalDrives) {
        return transform(physicalDrives, Contexts::toContext);
    }

    public static Set<Context> asStorageServicesContexts(Set<StorageService> storageServices) {
        return transform(storageServices, Contexts::toContext);
    }

    public static Set<Context> asRemoteTargetContexts(Set<RemoteTarget> remoteTargets) {
        return transform(remoteTargets, Contexts::toContext);
    }

    public static Set<Context> asEthernetSwitchContexts(Set<EthernetSwitch> ethernetSwitches) {
        return transform(ethernetSwitches, Contexts::toContext);
    }

    public static Set<Context> asChassisContexts(Set<Chassis> chassis) {
        return transform(chassis, Contexts::toContext);
    }

    public static Set<Context> asComputerSystemContexts(Set<ComputerSystem> computerSystems) {
        return transform(computerSystems, Contexts::toContext);
    }

    public static Set<Context> asMemoryContexts(Set<Memory> memoryModules) {
        return transform(memoryModules, Contexts::toContext);
    }

    public static Set<Context> asProcessorContexts(Set<Processor> processors) {
        return transform(processors, Contexts::toContext);
    }

    public static Set<Context> asEthernetInterfaceContexts(Set<EthernetInterface> ethernetInterfaces) {
        return transform(ethernetInterfaces, Contexts::toContext);
    }

    public static Set<Context> asEthernetSwitchPortContexts(Set<EthernetSwitchPort> ports) {
        return transform(ports, Contexts::toContext);
    }

    public static Set<Context> asManagerContexts(Set<Manager> managers) {
        return transform(managers, Contexts::toContext);
    }

    public static Set<Context> asFabricSwitchContexts(Set<Switch> switches) {
        return transform(switches, Contexts::toContext);
    }

    public static Set<Context> asEndpointContexts(Set<Endpoint> endpoints) {
        return transform(endpoints, Contexts::toContext);
    }

    public static Set<Context> asDriveContexts(Set<Drive> drives) {
        return transform(drives, Contexts::toContext);
    }

    public static Set<Context> asPortContexts(Set<Port> ports) {
        return transform(ports, Contexts::toContext);
    }

    public static Set<Context> asStorageContexts(Set<Storage> storages) {
        return transform(storages, Contexts::toContext);
    }

    public static Set<Context> asPcieDeviceContexts(Set<PcieDevice> pcieDevices) {
        return transform(pcieDevices, Contexts::toContext);
    }

    public static Set<Context> asPcieDeviceFunctionContexts(Set<PcieDeviceFunction> deviceFunctions) {
        return transform(deviceFunctions, Contexts::toContext);
    }

    private static <T, R> Set<R> transform(Iterable<T> items, Function<T, R> transformFunction) {
        if (items == null) {
            return emptySet();
        }
        return stream(items.spliterator(), false)
            .map(transformFunction)
            .filter(Objects::nonNull)
            .collect(toSet());
    }
}
