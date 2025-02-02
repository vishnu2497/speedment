/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.tool.config.trait;


import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.runtime.config.trait.HasOrderType;
import com.speedment.runtime.config.trait.HasOrderTypeUtil;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

/**
 *
 * @author Emil Forslund
 * @since 2.3.0
 */

public interface HasOrderTypeProperty extends DocumentProperty, HasOrderType {

    default ObjectProperty<OrderType> orderTypeProperty() {
        final StringProperty orderType = stringPropertyOf(HasOrderTypeUtil.ORDER_TYPE, () -> null);
        final StringConverter<OrderType> converter = new StringConverter<OrderType>() {
            @Override
            public String toString(OrderType object) {
                return object == null ? null : object.name();
            }

            @Override
            public OrderType fromString(String string) {
                return string == null ? null : OrderType.valueOf(string);
            }
        };
        final ObjectProperty<OrderType> object = new SimpleObjectProperty<>(converter.fromString(orderType.get()));       
        Bindings.bindBidirectional(orderType, object, converter);
        
        return object;
    }

    @Override
    default OrderType getOrderType() {
        return orderTypeProperty().get();
    }
}
