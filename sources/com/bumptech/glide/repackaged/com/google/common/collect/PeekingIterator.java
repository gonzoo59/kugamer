package com.bumptech.glide.repackaged.com.google.common.collect;

import java.util.Iterator;
/* loaded from: classes.dex */
public interface PeekingIterator<E> extends Iterator<E> {
    @Override // java.util.Iterator
    E next();

    E peek();
}
