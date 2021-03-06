/*!
 * @copyright
 * Copyright (c) 2016-2017 Intel Corporation
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
 *
 * @file fabric.hpp
 * @brief Definition of Fabric class
 * */
#pragma once
#include "agent-framework/module/model/resource.hpp"
#include "agent-framework/module/enum/pnc.hpp"

namespace Json {
    class Value;
}

namespace agent_framework {
namespace model {

/*! @brief Fabric class */
class Fabric : public Resource {
public:


    explicit Fabric(const std::string& parent_uuid = {}, enums::Component parent_type = enums::Component::Manager);
    ~Fabric();

    /*! Enable copy */
    Fabric(const Fabric&) = default;
    Fabric& operator=(const Fabric&) = default;
    /*! Enable move */
    Fabric(Fabric&&) = default;
    Fabric& operator=(Fabric&&) = default;

    /*!
     * @brief Get collection name
     * @return collection name
     */
    static enums::CollectionName get_collection_name() {
        return Fabric::collection_name;
    }

    /*!
     * @brief Get component name
     * @return component name
     * */
    static enums::Component get_component() {
        return Fabric::component;
    }

    /*!
     * @brief Returns JSON representation of the object.
     * @return Json representation.
     * */
    Json::Value to_json() const;

    /*!
     * @brief Constructs object from JSON
     * @param[in] json the Json::Value deserialized to object
     * @return the newly constructed object
     */
    static Fabric from_json(const Json::Value& json);

    /*!
     * @brief Get protocol
     * @return protocol
     * */
    const OptionalField<enums::StorageProtocol>& get_protocol() const {
        return m_protocol;
    }

    /*!
     * @brief Set protocol
     * @param[in] protocol protocol
     * */
    void set_protocol(const OptionalField<enums::StorageProtocol>& protocol) {
        m_protocol = protocol;
    }

private:

    OptionalField<enums::StorageProtocol> m_protocol{};

    static const enums::CollectionName collection_name;
    static const enums::Component component;
};

}
}
