/*!
 * @section LICENSE
 *
 * @copyright
 * Copyright (c) 2015-2017 Intel Corporation
 *
 * @copyright
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * @copyright
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * @copyright
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @section DESCRIPTION
 *
 * @file request/compute/get_interface_info.cpp
 *
 * @brief Compute request get network interface information implementation
 * */

#include "agent-framework/module/requests/compute/get_network_interface_info.hpp"
#include "agent-framework/module/constants/compute.hpp"
#include <json/json.h>

using namespace agent_framework::model::requests;
using namespace agent_framework::model::literals;

GetNetworkInterfaceInfo::GetNetworkInterfaceInfo(const std::string& interface): m_interface{interface} {}

Json::Value GetNetworkInterfaceInfo::to_json() const {
    Json::Value value;
    value[NetworkInterface::INTERFACE] = m_interface;
    return value;
}

GetNetworkInterfaceInfo GetNetworkInterfaceInfo::from_json(const Json::Value& json) {
    return GetNetworkInterfaceInfo{
        json[NetworkInterface::INTERFACE].asString()};
}

