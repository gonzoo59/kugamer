package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Joiner;
import java.util.Collection;
/* loaded from: classes.dex */
public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.on(", ").useForNull("null");

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection) iterable;
    }
}
