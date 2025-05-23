package com.squareup.kotlinpoet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\b\u000b\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0004\u001a\u0018\u0010\r\u001a\n \u000e*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u000f\u001a\u00020\tH\u0000\u001a\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0000\u001a/\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0017\"\u00020\u0015H\u0000¢\u0006\u0002\u0010\u0018\u001a/\u0010\u0019\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0017\"\u00020\u0015H\u0000¢\u0006\u0002\u0010\u0018\u001a/\u0010\u001b\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0017\"\u00020\u0015H\u0000¢\u0006\u0002\u0010\u0018\u001a\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0000\u001a1\u0010\u001d\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u001e*\b\u0012\u0004\u0012\u0002H\u001e0\u001f2\u0012\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u001e0\u0017\"\u0002H\u001eH\u0000¢\u0006\u0002\u0010!\u001aW\u0010\"\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u001e*\u0002H\u001e2\u0006\u0010#\u001a\u0002H\u001e2\u0006\u0010$\u001a\u0002H\u001e2\n\b\u0002\u0010%\u001a\u0004\u0018\u0001H\u001e2\n\b\u0002\u0010&\u001a\u0004\u0018\u0001H\u001e2\n\b\u0002\u0010'\u001a\u0004\u0018\u0001H\u001e2\n\b\u0002\u0010(\u001a\u0004\u0018\u0001H\u001eH\u0000¢\u0006\u0002\u0010)\u001a\u001e\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u001e0+\"\u0004\b\u0000\u0010\u001e*\b\u0012\u0004\u0012\u0002H\u001e0\u001fH\u0000\u001a0\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H/0-\"\u0004\b\u0000\u0010.\"\u0004\b\u0001\u0010/*\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H/0-H\u0000\u001a\u001e\u00100\u001a\b\u0012\u0004\u0012\u0002H\u001e0\u0003\"\u0004\b\u0000\u0010\u001e*\b\u0012\u0004\u0012\u0002H\u001e0\u001fH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00048@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0018\u0010\b\u001a\u00020\u0006*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n\"\u0018\u0010\u000b\u001a\u00020\u0006*\u00020\u00048@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007\"\u0018\u0010\f\u001a\u00020\u0006*\u00020\u00048@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0007¨\u00061"}, d2 = {"IDENTIFIER_REGEX", "Lkotlin/text/Regex;", "KEYWORDS", "", "", "isIdentifier", "", "(Ljava/lang/String;)Z", "isIsoControl", "", "(C)Z", "isKeyword", "isName", "characterLiteralWithoutSingleQuotes", "kotlin.jvm.PlatformType", "c", "escapeIfKeyword", "value", "requireExactlyOneOf", "", "modifiers", "Lcom/squareup/kotlinpoet/KModifier;", "mutuallyExclusive", "", "(Ljava/util/Set;[Lcom/squareup/kotlinpoet/KModifier;)V", "requireNoneOf", "forbidden", "requireNoneOrOneOf", "stringLiteralWithQuotes", "containsAnyOf", "T", "", "t", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "isOneOf", "t1", "t2", "t3", "t4", "t5", "t6", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z", "toImmutableList", "", "toImmutableMap", "", "K", "V", "toImmutableSet", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class UtilKt {
    private static final Regex IDENTIFIER_REGEX = new Regex("((\\p{gc=Lu}+|\\p{gc=Ll}+|\\p{gc=Lt}+|\\p{gc=Lm}+|\\p{gc=Lo}+|\\p{gc=Nl}+)+\\d*\\p{gc=Lu}*\\p{gc=Ll}*\\p{gc=Lt}*\\p{gc=Lm}*\\p{gc=Lo}*\\p{gc=Nl}*)|(`[^\n\r`]+`)");
    private static final Set<String> KEYWORDS = SetsKt.setOf((Object[]) new String[]{"package", "as", "typealias", "class", "this", "super", "val", "var", "fun", "for", "null", "true", "false", "is", "in", "throw", "return", "break", "continue", "object", "if", "try", "else", "while", "do", "when", "interface", "typeof"});

    private static final boolean isIsoControl(char c) {
        return (c >= 0 && 31 >= c) || (127 <= c && 159 >= c);
    }

    public static final <K, V> Map<K, V> toImmutableMap(Map<K, ? extends V> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Map<K, V> unmodifiableMap = Collections.unmodifiableMap(new LinkedHashMap(receiver));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableMap, "Collections.unmodifiableMap(LinkedHashMap(this))");
        return unmodifiableMap;
    }

    public static final <T> List<T> toImmutableList(Collection<? extends T> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        List<T> unmodifiableList = Collections.unmodifiableList(new ArrayList(receiver));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableList, "Collections.unmodifiableList(ArrayList(this))");
        return unmodifiableList;
    }

    public static final <T> Set<T> toImmutableSet(Collection<? extends T> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Set<T> unmodifiableSet = Collections.unmodifiableSet(new LinkedHashSet(receiver));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableSet, "Collections.unmodifiableSet(LinkedHashSet(this))");
        return unmodifiableSet;
    }

    public static final void requireExactlyOneOf(Set<? extends KModifier> modifiers, KModifier... mutuallyExclusive) {
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        Intrinsics.checkParameterIsNotNull(mutuallyExclusive, "mutuallyExclusive");
        KModifier[] kModifierArr = mutuallyExclusive;
        int i = 0;
        for (KModifier kModifier : kModifierArr) {
            if (modifiers.contains(kModifier)) {
                i++;
            }
        }
        if (i == 1) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("modifiers ");
        sb.append(modifiers);
        sb.append(" must contain one of ");
        String arrays = Arrays.toString(kModifierArr);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        sb.append(arrays);
        throw new IllegalArgumentException(sb.toString().toString());
    }

    public static final void requireNoneOrOneOf(Set<? extends KModifier> modifiers, KModifier... mutuallyExclusive) {
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        Intrinsics.checkParameterIsNotNull(mutuallyExclusive, "mutuallyExclusive");
        KModifier[] kModifierArr = mutuallyExclusive;
        int i = 0;
        for (KModifier kModifier : kModifierArr) {
            if (modifiers.contains(kModifier)) {
                i++;
            }
        }
        if (i <= 1) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("modifiers ");
        sb.append(modifiers);
        sb.append(" must contain none or only one of ");
        String arrays = Arrays.toString(kModifierArr);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        sb.append(arrays);
        throw new IllegalArgumentException(sb.toString().toString());
    }

    public static final void requireNoneOf(Set<? extends KModifier> modifiers, KModifier... forbidden) {
        Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
        Intrinsics.checkParameterIsNotNull(forbidden, "forbidden");
        KModifier[] kModifierArr = forbidden;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= kModifierArr.length) {
                z = true;
                break;
            } else if (modifiers.contains(kModifierArr[i])) {
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("modifiers ");
        sb.append(modifiers);
        sb.append(" must contain none of ");
        String arrays = Arrays.toString(kModifierArr);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        sb.append(arrays);
        throw new IllegalArgumentException(sb.toString().toString());
    }

    public static final <T> boolean isOneOf(T t, T t2, T t3, T t4, T t5, T t6, T t7) {
        return Intrinsics.areEqual(t, t2) || Intrinsics.areEqual(t, t3) || Intrinsics.areEqual(t, t4) || Intrinsics.areEqual(t, t5) || Intrinsics.areEqual(t, t6) || Intrinsics.areEqual(t, t7);
    }

    public static final String characterLiteralWithoutSingleQuotes(char c) {
        if (c == '\b') {
            return "\\b";
        }
        if (c == '\t') {
            return "\\t";
        }
        if (c == '\n') {
            return "\\n";
        }
        if (c == '\r') {
            return "\\r";
        }
        if (c == '\"') {
            return "\"";
        }
        if (c == '\'') {
            return "\\'";
        }
        if (c == '\\') {
            return "\\\\";
        }
        if (isIsoControl(c)) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format("\\u%04x", Arrays.copyOf(new Object[]{Integer.valueOf(c)}, 1));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            return format;
        }
        return Character.toString(c);
    }

    public static final String stringLiteralWithQuotes(String value) {
        boolean regionMatches;
        Intrinsics.checkParameterIsNotNull(value, "value");
        if (StringsKt.contains$default((CharSequence) value, (CharSequence) "\n", false, 2, (Object) null)) {
            StringBuilder sb = new StringBuilder(value.length() + 32);
            sb.append("\"\"\"\n|");
            int i = 0;
            while (i < value.length()) {
                char charAt = value.charAt(i);
                regionMatches = StringsKt.regionMatches(value, i, "\"\"\"", 0, 3, (r12 & 16) != 0 ? false : false);
                if (regionMatches) {
                    sb.append("\"\"${'\"'}");
                    i += 2;
                } else if (charAt == '\n') {
                    sb.append("\n|");
                } else {
                    sb.append(charAt);
                }
                i++;
            }
            if (!StringsKt.endsWith$default(value, "\n", false, 2, (Object) null)) {
                sb.append("\n");
            }
            sb.append("\"\"\".trimMargin()");
            String sb2 = sb.toString();
            Intrinsics.checkExpressionValueIsNotNull(sb2, "result.toString()");
            return sb2;
        }
        StringBuilder sb3 = new StringBuilder(value.length() + 32);
        sb3.append(Typography.quote);
        int length = value.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt2 = value.charAt(i2);
            if (charAt2 == '\'') {
                sb3.append("'");
            } else if (charAt2 == '\"') {
                sb3.append("\\\"");
            } else {
                sb3.append(characterLiteralWithoutSingleQuotes(charAt2));
            }
        }
        sb3.append(Typography.quote);
        String sb4 = sb3.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb4, "result.toString()");
        return sb4;
    }

    public static final String escapeIfKeyword(String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        if (isKeyword(value)) {
            return '`' + value + '`';
        }
        return value;
    }

    public static final boolean isIdentifier(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return IDENTIFIER_REGEX.matches(receiver);
    }

    public static final boolean isKeyword(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return KEYWORDS.contains(receiver);
    }

    public static final boolean isName(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        List<String> split$default = StringsKt.split$default((CharSequence) receiver, new String[]{"\\."}, false, 0, 6, (Object) null);
        if ((split$default instanceof Collection) && split$default.isEmpty()) {
            return true;
        }
        for (String str : split$default) {
            if (isKeyword(str)) {
                return false;
            }
        }
        return true;
    }

    public static final <T> boolean containsAnyOf(Collection<? extends T> receiver, T... t) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(t, "t");
        for (T t2 : t) {
            if (receiver.contains(t2)) {
                return true;
            }
        }
        return false;
    }
}
