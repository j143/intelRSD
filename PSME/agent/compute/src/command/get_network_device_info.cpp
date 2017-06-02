/*!
 * @brief Definition of getNetworkDeviceInfo command for Compute agent
 *
 * @header{License}
 * @copyright Copyright (c) 2017 Intel Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @header{Filesystem}
 * @file get_network_device_info.cpp
 */

#include "agent-framework/module/compute_components.hpp"
#include "agent-framework/command-ref/registry.hpp"
#include "agent-framework/command-ref/compute_commands.hpp"

using namespace agent_framework::command_ref;
using namespace agent_framework::module;

REGISTER_COMMAND(GetNetworkDeviceInfo,
                 [] (const GetNetworkDeviceInfo::Request& req, GetNetworkDeviceInfo::Response& rsp) {
                     log_debug(GET_LOGGER("agent"), "Getting network function info.");
                     rsp = ComputeComponents::get_instance()->get_network_device_manager().get_entry(req.get_uuid());
                 }
);
