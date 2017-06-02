/*!
 * @brief Provides base class for tree stability managing classes
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
 * @file tree_stabilizer.hpp
 * */



#pragma once


#include "key_generator.hpp"
#include "agent-framework/module/managers/generic_manager.hpp"

#include <string>
#include <memory>



namespace agent_framework {

/*!
 * @brief Base class for all tree stability managing classes.
 * */
class TreeStabilizer {
public:
    /*! Destructor */
    virtual ~TreeStabilizer();


    /*!
     * Manages persistent UUID generation. Starting from the component tree root
     * it utilizes the data acquired during discovery to create persistent UUIDs
     * of all resources in the component tree.
     *
     * @param[in] resource_tree_root_uuid UUID of resource tree root
     *
     * @return Resource tree root persistent UUID
     * */
    virtual const std::string stabilize(const std::string& resource_tree_root_uuid) = 0;


protected:
    /*!
     * Generate persistent UUID for a single resource of type given by template parameter.
     * This function provides only a wrapper for Resource class interface without
     * any additional actions like resolving dependencies etc.
     *
     * @param[in] resource_temporary_uuid UUID of resource to stabilize
     * @param[in] manager Manager helding the resource
     * @param[in] unique_key Value of the resource unique key to set
     *
     * @return Generated persistent UUID
     * */
    template<typename T>
    const std::string stabilize_single_resource(const std::string& resource_temporary_uuid,
                                                agent_framework::module::GenericManager<T>& manager,
                                                const std::string& unique_key) const {
        std::string resource_persistent_uuid{};
        {
            auto resource = manager.get_entry_reference(resource_temporary_uuid);
            resource->set_unique_key(unique_key);
            resource->make_persistent_uuid();
            resource_persistent_uuid = resource->get_uuid();
        }
        log_persistent_uuid_generated(T::get_component().to_string(), resource_temporary_uuid,
                                      resource_persistent_uuid);
        return resource_persistent_uuid;
    }


    /*!
     * Logs that persisitent UUID was successfully generated.
     *
     * @param[in] resource_name Name of the resource
     * @param[in] temporary_uuid Resource temporary UUID
     * @param[in] persistent_uuid Resource persistent UUID
     * */
    virtual void log_persistent_uuid_generated(const std::string& resource_name,
                                               const std::string& temporary_uuid,
                                               const std::string persistent_uuid) const;


    /*!
     * Logs that persistent UUID cannot be generated.
     *
     * @param[in] resource_name Name of the resource
     * @param[in] temporary_uuid Resource temporary UUID
     * */
    void log_key_value_missing(const std::string& resource_name,
                               const std::string& temporary_uuid) const;

};

}
