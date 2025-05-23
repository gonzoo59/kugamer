package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
/* loaded from: classes.dex */
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    boolean isHashCodeFast() {
        return false;
    }

    @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, java.util.NavigableSet
    public abstract UnmodifiableIterator<E> iterator();

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return construct(5, e, e2, e3, e4, e5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> ImmutableSet<E> construct(int i, Object... objArr) {
        if (i != 0) {
            if (i == 1) {
                return of(objArr[0]);
            }
            int chooseTableSize = chooseTableSize(i);
            Object[] objArr2 = new Object[chooseTableSize];
            int i2 = chooseTableSize - 1;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                Object checkElementNotNull = ObjectArrays.checkElementNotNull(objArr[i5], i5);
                int hashCode = checkElementNotNull.hashCode();
                int smear = Hashing.smear(hashCode);
                while (true) {
                    int i6 = smear & i2;
                    Object obj = objArr2[i6];
                    if (obj == null) {
                        objArr[i3] = checkElementNotNull;
                        objArr2[i6] = checkElementNotNull;
                        i4 += hashCode;
                        i3++;
                        break;
                    } else if (obj.equals(checkElementNotNull)) {
                        break;
                    } else {
                        smear++;
                    }
                }
            }
            Arrays.fill(objArr, i3, i, (Object) null);
            if (i3 == 1) {
                return new SingletonImmutableSet(objArr[0], i4);
            }
            if (chooseTableSize != chooseTableSize(i3)) {
                return construct(i3, objArr);
            }
            if (i3 < objArr.length) {
                objArr = ObjectArrays.arraysCopyOf(objArr, i3);
            }
            return new RegularImmutableSet(objArr, i4, objArr2, i2);
        }
        return of();
    }

    static int chooseTableSize(int i) {
        if (i < 751619276) {
            int highestOneBit = Integer.highestOneBit(i - 1) << 1;
            while (highestOneBit * 0.7d < i) {
                highestOneBit <<= 1;
            }
            return highestOneBit;
        }
        Preconditions.checkArgument(i < 1073741824, "collection too large");
        return 1073741824;
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof ImmutableSortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.isPartialView()) {
                return immutableSet;
            }
        } else if (collection instanceof EnumSet) {
            return copyOfEnumSet((EnumSet) collection);
        }
        Object[] array = collection.toArray();
        return construct(array.length, array);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? copyOf((Collection) iterable) : copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        E next = it.next();
        if (!it.hasNext()) {
            return of((Object) next);
        }
        return new Builder().add((Builder) next).addAll((Iterator) it).build();
    }

    private static ImmutableSet copyOfEnumSet(EnumSet enumSet) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof ImmutableSet) && isHashCodeFast() && ((ImmutableSet) obj).isHashCodeFast() && hashCode() != obj.hashCode()) {
            return false;
        }
        return Sets.equalsImpl(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    /* loaded from: classes.dex */
    public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.ArrayBasedBuilder add(Object obj) {
            return add((Builder<E>) obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj);
        }

        public Builder() {
            this(4);
        }

        Builder(int i) {
            super(i);
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e) {
            super.add((Builder<E>) e);
            return this;
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        public ImmutableSet<E> build() {
            ImmutableSet<E> construct = ImmutableSet.construct(this.size, this.contents);
            this.size = construct.size();
            return construct;
        }
    }
}
