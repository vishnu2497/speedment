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
package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function7;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder7;
import com.speedment.runtime.join.builder.JoinBuilder8;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder7Impl<T0, T1, T2, T3, T4, T5, T6>
    extends AbstractJoinBuilder<T6, JoinBuilder7<T0, T1, T2, T3, T4, T5, T6>>
    implements JoinBuilder7<T0, T1, T2, T3, T4, T5, T6> {

    JoinBuilder7Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T6> current) {
        super(previousStage, current);
    }

    @Override
    public <T7> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7> innerJoinOn(HasComparableOperators<T7, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T7> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7> leftJoinOn(HasComparableOperators<T7, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T7> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7> rightJoinOn(HasComparableOperators<T7, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

//    @Override
//    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> fullOuterJoinOn(HasComparableOperators<T6, ?> joinedField) {
//        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
//    }

    @Override
    public <T7> JoinBuilder8<T0, T1, T2, T3, T4, T5, T6, T7> crossJoin(TableIdentifier<T7> joinedTable) {
        return new JoinBuilder8Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T7>
        extends BaseAfterJoin<T7, JoinBuilder8<T0, T1, T2, T3, T4, T5, T6, T7>>
        implements AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7> {

        private AfterJoinImpl(StageBean<T7> stageBean) {
            super(JoinBuilder7Impl.this, stageBean, JoinBuilder8Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function7<T0, T1, T2, T3, T4, T5, T6, T> constructor) {
        requireNonNull(constructor);
/*        assertFieldsAreInJoinTables();*/
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier(),
            (TableIdentifier<T2>) stages.get(2).identifier(),
            (TableIdentifier<T3>) stages.get(3).identifier(),
            (TableIdentifier<T4>) stages.get(4).identifier(),
            (TableIdentifier<T5>) stages.get(5).identifier(),
            (TableIdentifier<T6>) stages.get(6).identifier()
        );
    }

}
