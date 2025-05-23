package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.Comparator;
/* loaded from: classes.dex */
public abstract class Ordering<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public abstract int compare(T t, T t2);

    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering) comparator : new ComparatorOrdering(comparator);
    }

    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }
}
