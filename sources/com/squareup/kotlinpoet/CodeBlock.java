package com.squareup.kotlinpoet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KClass;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
/* compiled from: CodeBlock.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001d\u001eB%\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0006\u0010\u000f\u001a\u00020\u000bJ\u0006\u0010\u0010\u001a\u00020\u000bJ\u001d\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0014J\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0017\u001a\u00020\u0004H\u0016J\r\u0010\u0018\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u0019J\u0017\u0010\u001a\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u001b\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u001cR\u001c\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u001f"}, d2 = {"Lcom/squareup/kotlinpoet/CodeBlock;", "", "formatParts", "", "", "args", "(Ljava/util/List;Ljava/util/List;)V", "getArgs$kotlinpoet", "()Ljava/util/List;", "getFormatParts$kotlinpoet", "equals", "", "other", "hashCode", "", "isEmpty", "isNotEmpty", "replaceAll", "oldValue", "newValue", "replaceAll$kotlinpoet", "toBuilder", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "toString", "trim", "trim$kotlinpoet", "withoutPrefix", "prefix", "withoutPrefix$kotlinpoet", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class CodeBlock {
    private static final int ARG_NAME = 1;
    private static final int TYPE_NAME = 2;
    private final List<Object> args;
    private final List<String> formatParts;
    public static final Companion Companion = new Companion(null);
    private static final Regex NAMED_ARGUMENT = new Regex("%([\\w_]+):([\\w]).*");
    private static final Regex LOWERCASE = new Regex("[a-z]+[\\w_]*");
    private static final Set<String> NO_ARG_PLACEHOLDERS = SetsKt.setOf((Object[]) new String[]{"%W", "%>", "%<", "%[", "%]"});

    @JvmStatic
    public static final Builder builder() {
        return Companion.builder();
    }

    @JvmStatic
    public static final CodeBlock of(String format, Object... args) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        Intrinsics.checkParameterIsNotNull(args, "args");
        return Companion.of(format, args);
    }

    private CodeBlock(List<String> list, List<? extends Object> list2) {
        this.formatParts = list;
        this.args = list2;
    }

    public /* synthetic */ CodeBlock(List list, List list2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2);
    }

    public final List<String> getFormatParts$kotlinpoet() {
        return this.formatParts;
    }

    public final List<Object> getArgs$kotlinpoet() {
        return this.args;
    }

    public final boolean isEmpty() {
        return this.formatParts.isEmpty();
    }

    public final boolean isNotEmpty() {
        return !isEmpty();
    }

    public final CodeBlock withoutPrefix$kotlinpoet(CodeBlock prefix) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        if (this.formatParts.size() >= prefix.formatParts.size() && this.args.size() >= prefix.args.size()) {
            String str = null;
            int i = 0;
            int i2 = 0;
            for (String str2 : prefix.formatParts) {
                int i3 = i + 1;
                if (!Intrinsics.areEqual(this.formatParts.get(i), str2)) {
                    if (i != prefix.formatParts.size() - 1 || !StringsKt.startsWith$default(this.formatParts.get(i), str2, false, 2, (Object) null)) {
                        return null;
                    }
                    String str3 = this.formatParts.get(i);
                    int length = str2.length();
                    if (str3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    str = str3.substring(length);
                    Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).substring(startIndex)");
                }
                if (StringsKt.startsWith$default(str2, "%", false, 2, (Object) null) && !Companion.isNoArgPlaceholder$kotlinpoet(str2.charAt(1))) {
                    if (!Intrinsics.areEqual(this.args.get(i2), prefix.args.get(i2))) {
                        return null;
                    }
                    i2++;
                }
                i = i3;
            }
            ArrayList arrayList = new ArrayList();
            if (str != null) {
                arrayList.add(str);
            }
            int size = this.formatParts.size();
            for (int size2 = prefix.formatParts.size(); size2 < size; size2++) {
                arrayList.add(this.formatParts.get(size2));
            }
            ArrayList arrayList2 = new ArrayList();
            int size3 = this.args.size();
            for (int size4 = prefix.args.size(); size4 < size3; size4++) {
                arrayList2.add(this.args.get(size4));
            }
            return new CodeBlock(arrayList, arrayList2);
        }
        return null;
    }

    public final CodeBlock trim$kotlinpoet() {
        int size = this.formatParts.size();
        int i = 0;
        while (i < size && Companion.getNO_ARG_PLACEHOLDERS().contains(this.formatParts.get(i))) {
            i++;
        }
        while (i < size && Companion.getNO_ARG_PLACEHOLDERS().contains(this.formatParts.get(size - 1))) {
            size--;
        }
        return (i > 0 || size < this.formatParts.size()) ? new CodeBlock(this.formatParts.subList(i, size), this.args) : this;
    }

    public final CodeBlock replaceAll$kotlinpoet(String oldValue, String newValue) {
        Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        List<String> list = this.formatParts;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (String str : list) {
            arrayList.add(StringsKt.replace$default(str, oldValue, newValue, false, 4, (Object) null));
        }
        return new CodeBlock(arrayList, this.args);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || (true ^ Intrinsics.areEqual(getClass(), obj.getClass()))) {
            return false;
        }
        return Intrinsics.areEqual(toString(), obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        new CodeWriter(sb, null, null, null, 14, null).emitCode(this);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder();
        CollectionsKt.addAll(builder.getFormatParts$kotlinpoet(), this.formatParts);
        builder.getArgs$kotlinpoet().addAll(this.args);
        return builder;
    }

    /* compiled from: CodeBlock.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\fJ+\u0010\n\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\b2\u0016\u0010\u0003\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000fJ\"\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0002J \u0010\u0015\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\b2\u0010\u0010\u0016\u001a\f\u0012\u0004\u0012\u00020\b\u0012\u0002\b\u00030\u0017J+\u0010\u0018\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\b2\u0016\u0010\u0003\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000fJ\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u0010\u001b\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0002J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0002J+\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\b2\u0016\u0010\u0003\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000fJ\u0006\u0010!\u001a\u00020\fJ\u0006\u0010\"\u001a\u00020\u0000J\u0006\u0010#\u001a\u00020\u0000J\u0006\u0010$\u001a\u00020%J\u0006\u0010&\u001a\u00020%J+\u0010'\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\b2\u0016\u0010\u0003\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000fJ\u0006\u0010(\u001a\u00020\u0000R\u001c\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006¨\u0006)"}, d2 = {"Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "", "()V", "args", "", "getArgs$kotlinpoet", "()Ljava/util/List;", "formatParts", "", "getFormatParts$kotlinpoet", "add", "codeBlock", "Lcom/squareup/kotlinpoet/CodeBlock;", "format", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "addArgument", "", "c", "", "arg", "addNamed", "arguments", "", "addStatement", "argToLiteral", "o", "argToName", "argToString", "argToType", "Lcom/squareup/kotlinpoet/TypeName;", "beginControlFlow", "controlFlow", "build", "endControlFlow", "indent", "isEmpty", "", "isNotEmpty", "nextControlFlow", "unindent", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<String> formatParts = new ArrayList();
        private final List<Object> args = new ArrayList();

        private final Object argToLiteral(Object obj) {
            return obj;
        }

        public final List<String> getFormatParts$kotlinpoet() {
            return this.formatParts;
        }

        public final List<Object> getArgs$kotlinpoet() {
            return this.args;
        }

        public final boolean isEmpty() {
            return this.formatParts.isEmpty();
        }

        public final boolean isNotEmpty() {
            return !isEmpty();
        }

        /* JADX WARN: Code restructure failed: missing block: B:40:0x01a5, code lost:
            return r17;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final com.squareup.kotlinpoet.CodeBlock.Builder addNamed(java.lang.String r18, java.util.Map<java.lang.String, ?> r19) {
            /*
                Method dump skipped, instructions count: 422
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.CodeBlock.Builder.addNamed(java.lang.String, java.util.Map):com.squareup.kotlinpoet.CodeBlock$Builder");
        }

        public final Builder add(String format, Object... args) {
            int i;
            char charAt;
            boolean z;
            int i2;
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            int[] iArr = new int[args.length];
            int i3 = 0;
            boolean z2 = false;
            int i4 = 0;
            boolean z3 = false;
            while (true) {
                if (i3 >= format.length()) {
                    if (z2) {
                        if (!(i4 >= args.length)) {
                            throw new IllegalArgumentException(("unused arguments: expected " + i4 + ", received " + args.length).toString());
                        }
                    }
                    if (z3) {
                        ArrayList arrayList = new ArrayList();
                        int length = args.length;
                        for (int i5 = 0; i5 < length; i5++) {
                            if (iArr[i5] == 0) {
                                arrayList.add("%" + (i5 + 1));
                            }
                        }
                        String str = arrayList.size() == 1 ? "" : "s";
                        if (!arrayList.isEmpty()) {
                            throw new IllegalArgumentException(("unused argument" + str + ": " + CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null)).toString());
                        }
                    }
                    return this;
                } else if (format.charAt(i3) != '%') {
                    int indexOf$default = StringsKt.indexOf$default((CharSequence) format, '%', i3 + 1, false, 4, (Object) null);
                    if (indexOf$default == -1) {
                        indexOf$default = format.length();
                    }
                    String substring = format.substring(i3, indexOf$default);
                    Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    this.formatParts.add(substring);
                    i3 = indexOf$default;
                } else {
                    int i6 = i3 + 1;
                    int i7 = i6;
                    while (true) {
                        if (!(i7 < format.length())) {
                            throw new IllegalArgumentException(("dangling format characters in '" + format + '\'').toString());
                        }
                        i = i7 + 1;
                        charAt = format.charAt(i7);
                        if ('0' > charAt || '9' < charAt) {
                            break;
                        }
                        i7 = i;
                    }
                    int i8 = i - 1;
                    if (CodeBlock.Companion.isNoArgPlaceholder$kotlinpoet(charAt)) {
                        if (!(i6 == i8)) {
                            throw new IllegalArgumentException("%%, %>, %<, %[, %], and %W may not have an index".toString());
                        }
                        this.formatParts.add("%" + charAt);
                        i3 = i;
                    } else {
                        if (i6 < i8) {
                            String substring2 = format.substring(i6, i8);
                            Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                            int parseInt = Integer.parseInt(substring2) - 1;
                            if (!(args.length == 0)) {
                                int length2 = parseInt % args.length;
                                iArr[length2] = iArr[length2] + 1;
                            }
                            z = true;
                            i2 = i4;
                            i4 = parseInt;
                        } else {
                            z = z3;
                            i2 = i4 + 1;
                            z2 = true;
                        }
                        if (!(i4 >= 0 && i4 < args.length)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("index ");
                            sb.append(i4 + 1);
                            sb.append(" for '");
                            String substring3 = format.substring(i6 - 1, i8 + 1);
                            Intrinsics.checkExpressionValueIsNotNull(substring3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                            sb.append(substring3);
                            sb.append("' not in range (received ");
                            sb.append(args.length);
                            sb.append(" arguments)");
                            throw new IllegalArgumentException(sb.toString().toString());
                        }
                        if (z && z2) {
                            r11 = false;
                        }
                        if (!r11) {
                            throw new IllegalArgumentException("cannot mix indexed and positional parameters".toString());
                        }
                        addArgument(format, charAt, args[i4]);
                        this.formatParts.add("%" + charAt);
                        i4 = i2;
                        i3 = i;
                        z3 = z;
                    }
                }
            }
        }

        private final void addArgument(String str, char c, Object obj) {
            if (c == 'L') {
                this.args.add(argToLiteral(obj));
            } else if (c == 'N') {
                this.args.add(argToName(obj));
            } else if (c == 'S') {
                this.args.add(argToString(obj));
            } else if (c == 'T') {
                this.args.add(argToType(obj));
            } else {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format("invalid format string: '%s'", Arrays.copyOf(new Object[]{str}, 1));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                throw new IllegalArgumentException(format);
            }
        }

        private final String argToName(Object obj) {
            if (obj instanceof CharSequence) {
                return obj.toString();
            }
            if (obj instanceof ParameterSpec) {
                return ((ParameterSpec) obj).getName();
            }
            if (obj instanceof PropertySpec) {
                return ((PropertySpec) obj).getName();
            }
            if (obj instanceof FunSpec) {
                return ((FunSpec) obj).getName();
            }
            if (!(obj instanceof TypeSpec)) {
                throw new IllegalArgumentException("expected name but was " + obj);
            }
            String name = ((TypeSpec) obj).getName();
            if (name == null) {
                Intrinsics.throwNpe();
                return name;
            }
            return name;
        }

        private final String argToString(Object obj) {
            if (obj != null) {
                return obj.toString();
            }
            return null;
        }

        private final TypeName argToType(Object obj) {
            if (obj instanceof TypeName) {
                return (TypeName) obj;
            }
            if (obj instanceof TypeMirror) {
                return TypeNames.get((TypeMirror) obj);
            }
            if (obj instanceof Element) {
                return TypeNames.get(((Element) obj).asType());
            }
            if (obj instanceof Type) {
                return TypeNames.get((Type) obj);
            }
            if (obj instanceof KClass) {
                return TypeNames.get((KClass) obj);
            }
            throw new IllegalArgumentException("expected type but was " + obj);
        }

        public final Builder beginControlFlow(String controlFlow, Object... args) {
            Intrinsics.checkParameterIsNotNull(controlFlow, "controlFlow");
            Intrinsics.checkParameterIsNotNull(args, "args");
            add(controlFlow + " {\n", Arrays.copyOf(args, args.length));
            indent();
            return this;
        }

        public final Builder nextControlFlow(String controlFlow, Object... args) {
            Intrinsics.checkParameterIsNotNull(controlFlow, "controlFlow");
            Intrinsics.checkParameterIsNotNull(args, "args");
            unindent();
            add("} " + controlFlow + " {\n", Arrays.copyOf(args, args.length));
            indent();
            return this;
        }

        public final Builder endControlFlow() {
            unindent();
            add("}\n", new Object[0]);
            return this;
        }

        public final Builder addStatement(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            add("%[", new Object[0]);
            add(format, Arrays.copyOf(args, args.length));
            add("\n%]", new Object[0]);
            return this;
        }

        public final Builder add(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            CollectionsKt.addAll(this.formatParts, codeBlock.getFormatParts$kotlinpoet());
            this.args.addAll(codeBlock.getArgs$kotlinpoet());
            return this;
        }

        public final Builder indent() {
            this.formatParts.add("%>");
            return this;
        }

        public final Builder unindent() {
            this.formatParts.add("%<");
            return this;
        }

        public final CodeBlock build() {
            return new CodeBlock(UtilKt.toImmutableList(this.formatParts), UtilKt.toImmutableList(this.args), null);
        }
    }

    /* compiled from: CodeBlock.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0007J\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0000¢\u0006\u0002\b\u0017J-\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\r2\u0016\u0010\u001b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u001c\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/squareup/kotlinpoet/CodeBlock$Companion;", "", "()V", "ARG_NAME", "", "LOWERCASE", "Lkotlin/text/Regex;", "getLOWERCASE", "()Lkotlin/text/Regex;", "NAMED_ARGUMENT", "getNAMED_ARGUMENT", "NO_ARG_PLACEHOLDERS", "", "", "getNO_ARG_PLACEHOLDERS", "()Ljava/util/Set;", "TYPE_NAME", "builder", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "isNoArgPlaceholder", "", "c", "", "isNoArgPlaceholder$kotlinpoet", "of", "Lcom/squareup/kotlinpoet/CodeBlock;", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/CodeBlock;", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Regex getNAMED_ARGUMENT() {
            return CodeBlock.NAMED_ARGUMENT;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Regex getLOWERCASE() {
            return CodeBlock.LOWERCASE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<String> getNO_ARG_PLACEHOLDERS() {
            return CodeBlock.NO_ARG_PLACEHOLDERS;
        }

        @JvmStatic
        public final CodeBlock of(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return new Builder().add(format, Arrays.copyOf(args, args.length)).build();
        }

        @JvmStatic
        public final Builder builder() {
            return new Builder();
        }

        public final boolean isNoArgPlaceholder$kotlinpoet(char c) {
            return UtilKt.isOneOf(Character.valueOf(c), '%', Character.valueOf(Typography.greater), Character.valueOf(Typography.less), '[', ']', 'W');
        }
    }
}
