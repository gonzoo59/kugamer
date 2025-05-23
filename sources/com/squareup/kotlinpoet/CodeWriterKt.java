package com.squareup.kotlinpoet;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CodeWriter.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u0010\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0004"}, d2 = {"NO_PACKAGE", "", "extractMemberName", "part", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class CodeWriterKt {
    private static final String NO_PACKAGE = new String();

    /* JADX INFO: Access modifiers changed from: private */
    public static final String extractMemberName(String str) {
        if (!Character.isJavaIdentifierStart(str.charAt(0))) {
            throw new IllegalArgumentException(("not an identifier: " + str).toString());
        }
        int length = str.length();
        if (1 <= length) {
            int i = 1;
            while (str != null) {
                String substring = str.substring(0, i);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                if (!UtilKt.isIdentifier(substring)) {
                    int i2 = i - 1;
                    if (str != null) {
                        String substring2 = str.substring(0, i2);
                        Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        return substring2;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else if (i != length) {
                    i++;
                }
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str;
    }
}
