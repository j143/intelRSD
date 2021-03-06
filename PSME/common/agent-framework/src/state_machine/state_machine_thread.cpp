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

#include "agent-framework/state_machine/state_machine_thread.hpp"
#include "agent-framework/state_machine/state_machine_exception.hpp"

#include <chrono>
#include <algorithm>

using namespace agent_framework;
using namespace agent_framework::state_machine;

namespace {
/* @TODO: This variable has to be moved to configuration class. */
/*! State Machine`s iterating interval. */
constexpr const std::size_t STATE_MACHINE_THREAD_INTERVAL_SECONDS = 1;
}

StateMachineThread::StateMachineThread() {

}

StateMachineThread::~StateMachineThread() {
    m_is_running = false;
    m_condition.notify_one();
    m_thread.join();
}

void StateMachineThread::add_module_thread(const StateMachineModuleThreadSharedPtr& module_thread) {
    std::lock_guard<std::mutex> lock{m_module_thread_mutex};
    m_module_threads.push_back(module_thread);
}


void StateMachineThread::task() {
    log_info(GET_LOGGER("state-machine"),"Starting State Machine thread...");
    while(m_is_running) {
        std::unique_lock<std::mutex> lk(m_mutex);
        if (m_condition.wait_for(lk, std::chrono::seconds(STATE_MACHINE_THREAD_INTERVAL_SECONDS)) == std::cv_status::timeout) {
            std::lock_guard<std::mutex> lock{m_module_thread_mutex};
            for (auto& module_thread : m_module_threads) {
                if (!module_thread->is_running()) {
                    module_thread->start();
                }
            }
        }
    }
    log_debug(GET_LOGGER("state-machine"), "State Machine thread stopped.");
}

void StateMachineThread::start() {
    m_is_running = true;
    m_thread = std::thread(&StateMachineThread::task, this);
}
