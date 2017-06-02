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

package com.intel.podm.client.resources.redfish.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intel.podm.client.api.resources.redfish.IdentifierObject;
import com.intel.podm.common.types.DurableNameFormat;

public class IdentifierObjectImpl implements IdentifierObject {
    @JsonProperty("DurableName")
    private String durableName;

    @JsonProperty("DurableNameFormat")
    private DurableNameFormat durableNameFormat;

    @Override
    public String getDurableName() {
        return durableName;
    }

    @Override
    public DurableNameFormat getDurableNameFormat() {
        return durableNameFormat;
    }
}
