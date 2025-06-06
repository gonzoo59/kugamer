package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/* loaded from: classes2.dex */
public final class ParallelFilterTry<T> extends ParallelFlowable<T> {
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Predicate<? super T> predicate;
    final ParallelFlowable<T> source;

    public ParallelFilterTry(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.source = parallelFlowable;
        this.predicate = predicate;
        this.errorHandler = biFunction;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super T> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelFilterConditionalSubscriber((ConditionalSubscriber) subscriber, this.predicate, this.errorHandler);
                } else {
                    subscriberArr2[i] = new ParallelFilterSubscriber(subscriber, this.predicate, this.errorHandler);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
        final Predicate<? super T> predicate;
        Subscription upstream;

        BaseFilterSubscriber(Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.predicate = predicate;
            this.errorHandler = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            this.upstream.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (tryOnNext(t) || this.done) {
                return;
            }
            this.upstream.request(1L);
        }
    }

    /* loaded from: classes2.dex */
    static final class ParallelFilterSubscriber<T> extends BaseFilterSubscriber<T> {
        final Subscriber<? super T> downstream;

        ParallelFilterSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x003d  */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 != 0) goto L63
                r2 = 0
            L7:
                r0 = 1
                io.reactivex.functions.Predicate<? super T> r4 = r9.predicate     // Catch: java.lang.Throwable -> L17
                boolean r2 = r4.test(r10)     // Catch: java.lang.Throwable -> L17
                if (r2 == 0) goto L16
                org.reactivestreams.Subscriber<? super T> r1 = r9.downstream
                r1.onNext(r10)
                return r0
            L16:
                return r1
            L17:
                r4 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch: java.lang.Throwable -> L4e
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L4e
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch: java.lang.Throwable -> L4e
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch: java.lang.Throwable -> L4e
                io.reactivex.parallel.ParallelFailureHandling r6 = (io.reactivex.parallel.ParallelFailureHandling) r6     // Catch: java.lang.Throwable -> L4e
                int[] r7 = io.reactivex.internal.operators.parallel.ParallelFilterTry.AnonymousClass1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L7
                if (r6 == r5) goto L4d
                r10 = 3
                if (r6 == r10) goto L47
                r9.cancel()
                r9.onError(r4)
                return r1
            L47:
                r9.cancel()
                r9.onComplete()
            L4d:
                return r1
            L4e:
                r10 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r10)
                r9.cancel()
                io.reactivex.exceptions.CompositeException r2 = new io.reactivex.exceptions.CompositeException
                java.lang.Throwable[] r3 = new java.lang.Throwable[r5]
                r3[r1] = r4
                r3[r0] = r10
                r2.<init>(r3)
                r9.onError(r2)
            L63:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelFilterTry.ParallelFilterSubscriber.tryOnNext(java.lang.Object):boolean");
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilterTry$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$parallel$ParallelFailureHandling;

        static {
            int[] iArr = new int[ParallelFailureHandling.values().length];
            $SwitchMap$io$reactivex$parallel$ParallelFailureHandling = iArr;
            try {
                iArr[ParallelFailureHandling.RETRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$parallel$ParallelFailureHandling[ParallelFailureHandling.SKIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$reactivex$parallel$ParallelFailureHandling[ParallelFailureHandling.STOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* loaded from: classes2.dex */
    static final class ParallelFilterConditionalSubscriber<T> extends BaseFilterSubscriber<T> {
        final ConditionalSubscriber<? super T> downstream;

        ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            super(predicate, biFunction);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0040  */
        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean tryOnNext(T r10) {
            /*
                r9 = this;
                boolean r0 = r9.done
                r1 = 0
                if (r0 != 0) goto L66
                r2 = 0
            L7:
                r0 = 1
                io.reactivex.functions.Predicate<? super T> r4 = r9.predicate     // Catch: java.lang.Throwable -> L1a
                boolean r2 = r4.test(r10)     // Catch: java.lang.Throwable -> L1a
                if (r2 == 0) goto L19
                io.reactivex.internal.fuseable.ConditionalSubscriber<? super T> r2 = r9.downstream
                boolean r10 = r2.tryOnNext(r10)
                if (r10 == 0) goto L19
                r1 = 1
            L19:
                return r1
            L1a:
                r4 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                r5 = 2
                io.reactivex.functions.BiFunction<? super java.lang.Long, ? super java.lang.Throwable, io.reactivex.parallel.ParallelFailureHandling> r6 = r9.errorHandler     // Catch: java.lang.Throwable -> L51
                r7 = 1
                long r2 = r2 + r7
                java.lang.Long r7 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L51
                java.lang.Object r6 = r6.apply(r7, r4)     // Catch: java.lang.Throwable -> L51
                java.lang.String r7 = "The errorHandler returned a null item"
                java.lang.Object r6 = io.reactivex.internal.functions.ObjectHelper.requireNonNull(r6, r7)     // Catch: java.lang.Throwable -> L51
                io.reactivex.parallel.ParallelFailureHandling r6 = (io.reactivex.parallel.ParallelFailureHandling) r6     // Catch: java.lang.Throwable -> L51
                int[] r7 = io.reactivex.internal.operators.parallel.ParallelFilterTry.AnonymousClass1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling
                int r6 = r6.ordinal()
                r6 = r7[r6]
                if (r6 == r0) goto L7
                if (r6 == r5) goto L50
                r10 = 3
                if (r6 == r10) goto L4a
                r9.cancel()
                r9.onError(r4)
                return r1
            L4a:
                r9.cancel()
                r9.onComplete()
            L50:
                return r1
            L51:
                r10 = move-exception
                io.reactivex.exceptions.Exceptions.throwIfFatal(r10)
                r9.cancel()
                io.reactivex.exceptions.CompositeException r2 = new io.reactivex.exceptions.CompositeException
                java.lang.Throwable[] r3 = new java.lang.Throwable[r5]
                r3[r1] = r4
                r3[r0] = r10
                r2.<init>(r3)
                r9.onError(r2)
            L66:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.parallel.ParallelFilterTry.ParallelFilterConditionalSubscriber.tryOnNext(java.lang.Object):boolean");
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }
    }
}
