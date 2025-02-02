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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.runtime.core.internal.stream.builder.action.reference.*;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import java.util.*;
import static java.util.Objects.requireNonNull;
import java.util.function.*;
import java.util.stream.*;
import com.speedment.runtime.core.stream.java9.Java9StreamAdditions;

/**
 *
 * @author pemi
 * @param <T> steam type
 */
public final class ReferenceStreamBuilder<T> extends AbstractStreamBuilder<ReferenceStreamBuilder<T>, ReferencePipeline<T>>
    implements Stream<T>, Java9StreamAdditions<T> {

    ReferenceStreamBuilder(PipelineImpl<?> pipeline, final StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        super(pipeline, streamTerminator, streamSet);
        streamSet.add(this); // Add this new stream to the streamSet so it may be closed later
    }

    public ReferenceStreamBuilder(PipelineImpl<?> pipeline, final StreamTerminator streamTerminator) {
        this(pipeline, streamTerminator, newStreamSet());
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return append(new FilterAction<>(predicate));
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<R>(pipeline, streamTerminator, streamSet).append(new MapAction<>(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new MapToIntAction<>(mapper));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new MapToLongAction<>(mapper));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new MapToDoubleAction<>(mapper));
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new ReferenceStreamBuilder<R>(pipeline, streamTerminator, streamSet).append(new FlatMapAction<>(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new IntStreamBuilder(pipeline, streamTerminator, streamSet).append(new FlatMapToIntAction<>(mapper));
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new LongStreamBuilder(pipeline, streamTerminator, streamSet).append(new FlatMapToLongAction<>(mapper));
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        assertNotLinkedOrConsumedAndSet();
        return new DoubleStreamBuilder(pipeline, streamTerminator, streamSet).append(new FlatMapToDoubleAction<>(mapper));
    }

    @Override
    public Stream<T> distinct() {
        return append(new DistinctAction<>());
    }

    @Override
    public Stream<T> sorted() {
        return append(new SortedAction<>());
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return append(new SortedComparatorAction<>(comparator));
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        requireNonNull(action);
        return append(new PeekAction<>(action));
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return append(new LimitAction<>(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        return append(new SkipAction<>(n));
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return append(new TakeWhileAction<>(predicate));
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return append(new DropWhileAction<>(predicate));
    }

    // Terminal operations
    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        requireNonNull(action);
        assertNotLinkedOrConsumedAndSet();
        finallyClose(() -> streamTerminator.forEach(pipeline(), action));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        requireNonNull(action);
        assertNotLinkedOrConsumedAndSet();
        finallyClose(() -> streamTerminator.forEachOrdered(pipeline(), action));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Object[] toArray() {
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.toArray(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        requireNonNull(generator);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.toArray(pipeline(), generator));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.reduce(pipeline(), identity, accumulator));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.reduce(pipeline(), accumulator));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.reduce(pipeline(), identity, accumulator, combiner));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.collect(pipeline(), supplier, accumulator, combiner));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.collect(pipeline(), collector));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        assertNotLinkedOrConsumedAndSet();
        return finallyCloseReference(() -> streamTerminator.min(pipeline(), comparator));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseReference(() -> streamTerminator.max(pipeline(), comparator));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public long count() {
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseLong(() -> streamTerminator.count(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseBoolean(() -> streamTerminator.anyMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseBoolean(() -> streamTerminator.allMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseBoolean(() -> streamTerminator.noneMatch(pipeline(), predicate));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Optional<T> findFirst() {
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseReference(() -> streamTerminator.findFirst(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline and
     * closes the stream automatically when a terminal operation is performed.
     *
     */
    @Override
    public Optional<T> findAny() {
        assertNotLinkedOrConsumedAndSet();        
        return finallyCloseReference(() -> streamTerminator.findAny(pipeline()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     * <p>
     * If you call this method, you <em>must</em> ensure to call the stream's 
     * {@link #close() } method or else resources may not be released properly.
     *
     */
    @Override
    public Iterator<T> iterator() {
        assertNotLinkedOrConsumedAndSet();
        //throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
        return streamTerminator.iterator(pipeline());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * N.B. This method may short-circuit operations in the Stream pipeline.
     * <p>
     * If you call this method, you <em>must</em> ensure to call the stream's 
     * {@link #close() } method or else resources may not be released properly.
     *
     */
    @Override
    public Spliterator<T> spliterator() {
        assertNotLinkedOrConsumedAndSet();
        //throw new UnsupportedOperationException(UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED);
        return streamTerminator.spliterator(pipeline());
    }
}
