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
*/

#include "agent-framework/state_machine/state_machine_thread_action.hpp"
#include "agent-framework/state_machine/state_thread_entry.hpp"

using namespace agent_framework;
using namespace agent_framework::state_machine;

StateMachineThreadAction::~StateMachineThreadAction() { }

void StateMachineThreadAction::execute(StateThreadEntrySharedPtr) {}


