package com.squareup.kotlinpoet;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: NameAllocator.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002¨\u0006\u0003"}, d2 = {"toJavaIdentifier", "", "suggestion", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class NameAllocatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String toJavaIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            if (str != null) {
                int codePointAt = str.codePointAt(i);
                if (i == 0 && !Character.isJavaIdentifierStart(codePointAt) && Character.isJavaIdentifierPart(codePointAt)) {
                    sb.append("_");
                }
                sb.appendCodePoint(Character.isJavaIdentifierPart(codePointAt) ? codePointAt : 95);
                i += Character.charCount(codePointAt);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
