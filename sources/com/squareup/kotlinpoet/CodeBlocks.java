package com.squareup.kotlinpoet;

import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CodeBlock.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0003\u001a0\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0004H\u0007¨\u0006\u0007"}, d2 = {"joinToCode", "Lcom/squareup/kotlinpoet/CodeBlock;", "", "separator", "", "prefix", "suffix", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class CodeBlocks {
    public static final CodeBlock joinToCode(Collection<CodeBlock> collection) {
        return joinToCode$default(collection, null, null, null, 7, null);
    }

    public static final CodeBlock joinToCode(Collection<CodeBlock> collection, CharSequence charSequence) {
        return joinToCode$default(collection, charSequence, null, null, 6, null);
    }

    public static final CodeBlock joinToCode(Collection<CodeBlock> collection, CharSequence charSequence, CharSequence charSequence2) {
        return joinToCode$default(collection, charSequence, charSequence2, null, 4, null);
    }

    public static /* bridge */ /* synthetic */ CodeBlock joinToCode$default(Collection collection, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Object obj) {
        if ((i & 1) != 0) {
        }
        if ((i & 2) != 0) {
        }
        if ((i & 4) != 0) {
        }
        return joinToCode(collection, charSequence, charSequence2, charSequence3);
    }

    public static final CodeBlock joinToCode(Collection<CodeBlock> receiver, CharSequence separator, CharSequence prefix, CharSequence suffix) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(separator, "separator");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        Object[] array = receiver.toArray(new CodeBlock[receiver.size()]);
        if (array != null) {
            CodeBlock[] codeBlockArr = (CodeBlock[]) array;
            int length = codeBlockArr.length;
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = "%L";
            }
            return CodeBlock.Companion.of(ArraysKt.joinToString$default(strArr, separator, prefix, suffix, 0, (CharSequence) null, (Function1) null, 56, (Object) null), Arrays.copyOf(codeBlockArr, codeBlockArr.length));
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
}
