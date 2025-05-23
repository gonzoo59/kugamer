package com.squareup.kotlinpoet;

import com.squareup.kotlinpoet.AnnotationSpec;
import com.squareup.kotlinpoet.CodeBlock;
import com.squareup.kotlinpoet.TypeSpec;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.Strictfp;
import kotlin.jvm.Synchronized;
import kotlin.jvm.Throws;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
/* compiled from: FunSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 @2\u00020\u0001:\u0002?@B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J-\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\u000f2\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0000¢\u0006\u0002\b5J\u001a\u00106\u001a\u0002002\u0006\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\u000fH\u0002J\u0013\u00107\u001a\u00020\u00182\b\u00108\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00109\u001a\u00020:H\u0016J\u0017\u0010;\u001a\u0004\u0018\u00010%2\u0006\u0010\"\u001a\u00020\u000fH\u0000¢\u0006\u0002\b<J\u0006\u0010=\u001a\u00020\u0003J\b\u0010>\u001a\u00020\u000fH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\tR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u001b\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\rR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0011R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0006¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\tR\u0013\u0010'\u001a\u0004\u0018\u00010\u0015¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0013\u0010*\u001a\u0004\u0018\u00010\u0015¢\u0006\b\n\u0000\u001a\u0004\b+\u0010)R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u0006¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\t¨\u0006A"}, d2 = {"Lcom/squareup/kotlinpoet/FunSpec;", "", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "(Lcom/squareup/kotlinpoet/FunSpec$Builder;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations", "()Ljava/util/List;", "body", "Lcom/squareup/kotlinpoet/CodeBlock;", "getBody", "()Lcom/squareup/kotlinpoet/CodeBlock;", "delegateConstructor", "", "getDelegateConstructor", "()Ljava/lang/String;", "delegateConstructorArguments", "getDelegateConstructorArguments", "exceptions", "Lcom/squareup/kotlinpoet/TypeName;", "getExceptions", "isAccessor", "", "()Z", "isConstructor", "kdoc", "getKdoc", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers", "()Ljava/util/Set;", "name", "getName", "parameters", "Lcom/squareup/kotlinpoet/ParameterSpec;", "getParameters", "receiverType", "getReceiverType", "()Lcom/squareup/kotlinpoet/TypeName;", "returnType", "getReturnType", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "enclosingName", "implicitModifiers", "emit$kotlinpoet", "emitSignature", "equals", "other", "hashCode", "", "parameter", "parameter$kotlinpoet", "toBuilder", "toString", "Builder", "Companion", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class FunSpec {
    private static final String CONSTRUCTOR = "constructor()";
    public static final Companion Companion = new Companion(null);
    private static final CodeBlock EXPRESSION_BODY_PREFIX = CodeBlock.Companion.of("return ", new Object[0]);
    public static final String GETTER = "get()";
    public static final String SETTER = "set()";
    private final List<AnnotationSpec> annotations;
    private final CodeBlock body;
    private final String delegateConstructor;
    private final List<CodeBlock> delegateConstructorArguments;
    private final List<TypeName> exceptions;
    private final CodeBlock kdoc;
    private final Set<KModifier> modifiers;
    private final String name;
    private final List<ParameterSpec> parameters;
    private final TypeName receiverType;
    private final TypeName returnType;
    private final List<TypeVariableName> typeVariables;

    @JvmStatic
    public static final Builder builder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.builder(name);
    }

    @JvmStatic
    public static final Builder constructorBuilder() {
        return Companion.constructorBuilder();
    }

    @JvmStatic
    public static final Builder getterBuilder() {
        return Companion.getterBuilder();
    }

    @JvmStatic
    public static final Builder overriding(ExecutableElement method) {
        Intrinsics.checkParameterIsNotNull(method, "method");
        return Companion.overriding(method);
    }

    @JvmStatic
    public static final Builder overriding(ExecutableElement method, DeclaredType enclosing, Types types) {
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(enclosing, "enclosing");
        Intrinsics.checkParameterIsNotNull(types, "types");
        return Companion.overriding(method, enclosing, types);
    }

    @JvmStatic
    public static final Builder setterBuilder() {
        return Companion.setterBuilder();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d6, code lost:
        if (r9 != false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private FunSpec(com.squareup.kotlinpoet.FunSpec.Builder r9) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.kotlinpoet.FunSpec.<init>(com.squareup.kotlinpoet.FunSpec$Builder):void");
    }

    public /* synthetic */ FunSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final String getName() {
        return this.name;
    }

    public final CodeBlock getKdoc() {
        return this.kdoc;
    }

    public final List<AnnotationSpec> getAnnotations() {
        return this.annotations;
    }

    public final Set<KModifier> getModifiers() {
        return this.modifiers;
    }

    public final List<TypeVariableName> getTypeVariables() {
        return this.typeVariables;
    }

    public final TypeName getReceiverType() {
        return this.receiverType;
    }

    public final TypeName getReturnType() {
        return this.returnType;
    }

    public final List<ParameterSpec> getParameters() {
        return this.parameters;
    }

    public final String getDelegateConstructor() {
        return this.delegateConstructor;
    }

    public final List<CodeBlock> getDelegateConstructorArguments() {
        return this.delegateConstructorArguments;
    }

    public final List<TypeName> getExceptions() {
        return this.exceptions;
    }

    public final CodeBlock getBody() {
        return this.body;
    }

    public final ParameterSpec parameter$kotlinpoet(String name) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(name, "name");
        Iterator<T> it = this.parameters.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ParameterSpec) obj).getName(), name)) {
                break;
            }
        }
        return (ParameterSpec) obj;
    }

    public final void emit$kotlinpoet(CodeWriter codeWriter, String str, Set<? extends KModifier> implicitModifiers) {
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        Intrinsics.checkParameterIsNotNull(implicitModifiers, "implicitModifiers");
        codeWriter.emitKdoc(this.kdoc);
        codeWriter.emitAnnotations(this.annotations, false);
        codeWriter.emitModifiers(this.modifiers, implicitModifiers);
        if (!isConstructor() && !Companion.isAccessor(this.name)) {
            codeWriter.emit("fun ");
        }
        if (!this.typeVariables.isEmpty()) {
            codeWriter.emitTypeVariables(this.typeVariables);
            codeWriter.emit(" ");
        }
        emitSignature(codeWriter, str);
        codeWriter.emitWhereBlock(this.typeVariables);
        boolean z = isConstructor() && this.body.isEmpty();
        if (UtilKt.containsAnyOf(this.modifiers, KModifier.ABSTRACT, KModifier.EXTERNAL, KModifier.EXPECT) || implicitModifiers.contains(KModifier.EXPECT) || z) {
            codeWriter.emit("\n");
            return;
        }
        CodeBlock withoutPrefix$kotlinpoet = this.body.trim$kotlinpoet().withoutPrefix$kotlinpoet(Companion.getEXPRESSION_BODY_PREFIX());
        if (withoutPrefix$kotlinpoet != null) {
            codeWriter.emitCode(" = %L", withoutPrefix$kotlinpoet);
            return;
        }
        codeWriter.emit(" {\n");
        CodeWriter.indent$default(codeWriter, 0, 1, null);
        codeWriter.emitCode(this.body);
        CodeWriter.unindent$default(codeWriter, 0, 1, null);
        codeWriter.emit("}\n");
    }

    private final void emitSignature(final CodeWriter codeWriter, String str) {
        if (isConstructor()) {
            codeWriter.emitCode("constructor", str);
        } else if (Intrinsics.areEqual(this.name, GETTER)) {
            codeWriter.emitCode("get");
        } else if (Intrinsics.areEqual(this.name, SETTER)) {
            codeWriter.emitCode("set");
        } else {
            TypeName typeName = this.receiverType;
            if (typeName != null) {
                if (typeName instanceof LambdaTypeName) {
                    codeWriter.emitCode("(%T).", typeName);
                } else {
                    codeWriter.emitCode("%T.", typeName);
                }
            }
            codeWriter.emitCode("%L", UtilKt.escapeIfKeyword(this.name));
        }
        ParameterSpecKt.emit(this.parameters, codeWriter, new Function1<ParameterSpec, Unit>() { // from class: com.squareup.kotlinpoet.FunSpec$emitSignature$1
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ParameterSpec parameterSpec) {
                invoke2(parameterSpec);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2(ParameterSpec param) {
                Intrinsics.checkParameterIsNotNull(param, "param");
                param.emit$kotlinpoet(codeWriter, !Intrinsics.areEqual(FunSpec.this.getName(), FunSpec.SETTER));
            }
        });
        TypeName typeName2 = this.returnType;
        if (typeName2 != null) {
            codeWriter.emitCode(": %T", typeName2);
        }
        if (this.delegateConstructor != null) {
            List<CodeBlock> list = this.delegateConstructorArguments;
            codeWriter.emitCode(CodeBlocks.joinToCode$default(list, null, " : " + this.delegateConstructor + '(', ")", 1, null));
        }
        if (!this.exceptions.isEmpty()) {
            codeWriter.emitWrappingSpace().emit("throws");
            int i = 0;
            for (TypeName typeName3 : this.exceptions) {
                int i2 = i + 1;
                if (i > 0) {
                    codeWriter.emit(",");
                }
                codeWriter.emitWrappingSpace().emitCode("%T", typeName3);
                i = i2;
            }
        }
    }

    public final boolean isConstructor() {
        return Companion.isConstructor(this.name);
    }

    public final boolean isAccessor() {
        return Companion.isAccessor(this.name);
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
        emit$kotlinpoet(new CodeWriter(sb, null, null, null, 14, null), "Constructor", TypeSpec.Kind.CLASS.getImplicitFunctionModifiers$kotlinpoet());
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.name);
        builder.getKdoc$kotlinpoet().add(this.kdoc);
        CollectionsKt.addAll(builder.getAnnotations$kotlinpoet(), this.annotations);
        CollectionsKt.addAll(builder.getModifiers$kotlinpoet(), this.modifiers);
        CollectionsKt.addAll(builder.getTypeVariables$kotlinpoet(), this.typeVariables);
        builder.setReturnType$kotlinpoet(this.returnType);
        CollectionsKt.addAll(builder.getParameters$kotlinpoet(), this.parameters);
        builder.setDelegateConstructor$kotlinpoet(this.delegateConstructor);
        CollectionsKt.addAll(builder.getDelegateConstructorArguments$kotlinpoet(), this.delegateConstructorArguments);
        CollectionsKt.addAll(builder.getExceptions$kotlinpoet(), this.exceptions);
        builder.getBody$kotlinpoet().add(this.body);
        return builder;
    }

    /* compiled from: FunSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\u0007J\u000e\u0010.\u001a\u00020\u00002\u0006\u00100\u001a\u000201J\u0012\u0010.\u001a\u00020\u00002\n\u00100\u001a\u0006\u0012\u0002\b\u000302J\u0012\u0010.\u001a\u00020\u00002\n\u00100\u001a\u0006\u0012\u0002\b\u000303J\u0014\u00104\u001a\u00020\u00002\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000706J\u000e\u00107\u001a\u00020\u00002\u0006\u00108\u001a\u00020\u0013J'\u00107\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J'\u0010=\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010?\u001a\u00020\u0013J'\u0010>\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J\u001f\u0010@\u001a\u00020\u00002\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0;\"\u00020\u001d¢\u0006\u0002\u0010AJ\u0014\u0010@\u001a\u00020\u00002\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d06J \u0010B\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00032\u0010\u0010:\u001a\f\u0012\u0004\u0012\u00020\u0003\u0012\u0002\b\u00030CJ\u000e\u0010D\u001a\u00020\u00002\u0006\u0010E\u001a\u00020!J/\u0010D\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010F\u001a\u00020\u00172\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0;\"\u00020\u001d¢\u0006\u0002\u0010GJ/\u0010D\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010F\u001a\u00020H2\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0;\"\u00020\u001d¢\u0006\u0002\u0010IJ3\u0010D\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\n\u0010F\u001a\u0006\u0012\u0002\b\u0003032\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0;\"\u00020\u001d¢\u0006\u0002\u0010JJ\u0014\u0010K\u001a\u00020\u00002\f\u0010L\u001a\b\u0012\u0004\u0012\u00020!06J'\u0010M\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J\u000e\u0010N\u001a\u00020\u00002\u0006\u0010O\u001a\u00020,J\u0014\u0010P\u001a\u00020\u00002\f\u0010+\u001a\b\u0012\u0004\u0012\u00020,06J'\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J\u0006\u0010S\u001a\u00020TJ)\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130;\"\u00020\u0013H\u0002¢\u0006\u0002\u0010XJ!\u0010Y\u001a\u00020\u00002\u0014\b\u0002\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130;\"\u00020\u0013¢\u0006\u0002\u0010ZJ\u001f\u0010Y\u001a\u00020\u00002\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030;\"\u00020\u0003¢\u0006\u0002\u0010[J!\u0010\\\u001a\u00020\u00002\u0014\b\u0002\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130;\"\u00020\u0013¢\u0006\u0002\u0010ZJ\u001f\u0010\\\u001a\u00020\u00002\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030;\"\u00020\u0003¢\u0006\u0002\u0010[J\u0006\u0010]\u001a\u00020\u0000J\u0014\u0010^\u001a\u00020V2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020_06J'\u0010`\u001a\u00020\u00002\u0006\u0010R\u001a\u00020\u00032\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010;\"\u00020\u0001¢\u0006\u0002\u0010<J\u000e\u0010a\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0017J\u000e\u0010a\u001a\u00020\u00002\u0006\u0010#\u001a\u00020HJ\u0012\u0010a\u001a\u00020\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u000303J\u000e\u0010b\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\u0017J\u000e\u0010b\u001a\u00020\u00002\u0006\u0010(\u001a\u00020HJ\u0012\u0010b\u001a\u00020\u00002\n\u0010(\u001a\u0006\u0012\u0002\b\u000303R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0004R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\rR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0010R\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\tR\u001c\u0010#\u001a\u0004\u0018\u00010\u0017X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001c\u0010(\u001a\u0004\u0018\u00010\u0017X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010%\"\u0004\b*\u0010'R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\t¨\u0006c"}, d2 = {"Lcom/squareup/kotlinpoet/FunSpec$Builder;", "", "name", "", "(Ljava/lang/String;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations$kotlinpoet", "()Ljava/util/List;", "body", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "getBody$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "delegateConstructor", "getDelegateConstructor$kotlinpoet", "()Ljava/lang/String;", "setDelegateConstructor$kotlinpoet", "delegateConstructorArguments", "Lcom/squareup/kotlinpoet/CodeBlock;", "getDelegateConstructorArguments$kotlinpoet", "exceptions", "", "Lcom/squareup/kotlinpoet/TypeName;", "getExceptions$kotlinpoet", "()Ljava/util/Set;", "kdoc", "getKdoc$kotlinpoet", "modifiers", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers$kotlinpoet", "getName$kotlinpoet", "parameters", "Lcom/squareup/kotlinpoet/ParameterSpec;", "getParameters$kotlinpoet", "receiverType", "getReceiverType$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "setReceiverType$kotlinpoet", "(Lcom/squareup/kotlinpoet/TypeName;)V", "returnType", "getReturnType$kotlinpoet", "setReturnType$kotlinpoet", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables$kotlinpoet", "addAnnotation", "annotationSpec", "annotation", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "addAnnotations", "annotationSpecs", "", "addCode", "codeBlock", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "addComment", "addKdoc", "block", "addModifiers", "([Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "addNamedCode", "", "addParameter", "parameterSpec", "type", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "Ljava/lang/reflect/Type;", "(Ljava/lang/String;Ljava/lang/reflect/Type;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "(Ljava/lang/String;Lkotlin/reflect/KClass;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "addParameters", "parameterSpecs", "addStatement", "addTypeVariable", "typeVariable", "addTypeVariables", "beginControlFlow", "controlFlow", "build", "Lcom/squareup/kotlinpoet/FunSpec;", "callConstructor", "", "constructor", "(Ljava/lang/String;[Lcom/squareup/kotlinpoet/CodeBlock;)V", "callSuperConstructor", "([Lcom/squareup/kotlinpoet/CodeBlock;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "([Ljava/lang/String;)Lcom/squareup/kotlinpoet/FunSpec$Builder;", "callThisConstructor", "endControlFlow", "jvmModifiers", "Ljavax/lang/model/element/Modifier;", "nextControlFlow", "receiver", "returns", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private final CodeBlock.Builder body;
        private String delegateConstructor;
        private final List<CodeBlock> delegateConstructorArguments;
        private final Set<TypeName> exceptions;
        private final CodeBlock.Builder kdoc;
        private final List<KModifier> modifiers;
        private final String name;
        private final List<ParameterSpec> parameters;
        private TypeName receiverType;
        private TypeName returnType;
        private final List<TypeVariableName> typeVariables;

        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Modifier.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[Modifier.PUBLIC.ordinal()] = 1;
                iArr[Modifier.PROTECTED.ordinal()] = 2;
                iArr[Modifier.PRIVATE.ordinal()] = 3;
                iArr[Modifier.ABSTRACT.ordinal()] = 4;
                iArr[Modifier.FINAL.ordinal()] = 5;
                iArr[Modifier.NATIVE.ordinal()] = 6;
                iArr[Modifier.DEFAULT.ordinal()] = 7;
                iArr[Modifier.STATIC.ordinal()] = 8;
                iArr[Modifier.SYNCHRONIZED.ordinal()] = 9;
                iArr[Modifier.STRICTFP.ordinal()] = 10;
            }
        }

        public Builder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.name = name;
            this.kdoc = CodeBlock.Companion.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.typeVariables = new ArrayList();
            this.parameters = new ArrayList();
            this.delegateConstructorArguments = new ArrayList();
            this.exceptions = new LinkedHashSet();
            this.body = CodeBlock.Companion.builder();
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final CodeBlock.Builder getKdoc$kotlinpoet() {
            return this.kdoc;
        }

        public final List<AnnotationSpec> getAnnotations$kotlinpoet() {
            return this.annotations;
        }

        public final List<KModifier> getModifiers$kotlinpoet() {
            return this.modifiers;
        }

        public final List<TypeVariableName> getTypeVariables$kotlinpoet() {
            return this.typeVariables;
        }

        public final TypeName getReceiverType$kotlinpoet() {
            return this.receiverType;
        }

        public final void setReceiverType$kotlinpoet(TypeName typeName) {
            this.receiverType = typeName;
        }

        public final TypeName getReturnType$kotlinpoet() {
            return this.returnType;
        }

        public final void setReturnType$kotlinpoet(TypeName typeName) {
            this.returnType = typeName;
        }

        public final List<ParameterSpec> getParameters$kotlinpoet() {
            return this.parameters;
        }

        public final String getDelegateConstructor$kotlinpoet() {
            return this.delegateConstructor;
        }

        public final void setDelegateConstructor$kotlinpoet(String str) {
            this.delegateConstructor = str;
        }

        public final List<CodeBlock> getDelegateConstructorArguments$kotlinpoet() {
            return this.delegateConstructorArguments;
        }

        public final Set<TypeName> getExceptions$kotlinpoet() {
            return this.exceptions;
        }

        public final CodeBlock.Builder getBody$kotlinpoet() {
            return this.body;
        }

        public final Builder addKdoc(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.kdoc.add(format, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder addKdoc(CodeBlock block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            this.kdoc.add(block);
            return this;
        }

        public final Builder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
            Intrinsics.checkParameterIsNotNull(annotationSpecs, "annotationSpecs");
            CollectionsKt.addAll(this.annotations, annotationSpecs);
            return this;
        }

        public final Builder addAnnotation(AnnotationSpec annotationSpec) {
            Intrinsics.checkParameterIsNotNull(annotationSpec, "annotationSpec");
            this.annotations.add(annotationSpec);
            return this;
        }

        public final Builder addAnnotation(ClassName annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            this.annotations.add(AnnotationSpec.Companion.builder(annotation).build());
            return this;
        }

        public final Builder addAnnotation(Class<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addAnnotation(KClass<?> annotation) {
            Intrinsics.checkParameterIsNotNull(annotation, "annotation");
            return addAnnotation(ClassNames.get(annotation));
        }

        public final Builder addModifiers(KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final Builder addModifiers(Iterable<? extends KModifier> modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final void jvmModifiers(Iterable<? extends Modifier> modifiers) {
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            KModifier kModifier = KModifier.INTERNAL;
            for (Modifier modifier : modifiers) {
                switch (WhenMappings.$EnumSwitchMapping$0[modifier.ordinal()]) {
                    case 1:
                        kModifier = KModifier.PUBLIC;
                        break;
                    case 2:
                        kModifier = KModifier.PROTECTED;
                        break;
                    case 3:
                        kModifier = KModifier.PRIVATE;
                        break;
                    case 4:
                        this.modifiers.add(KModifier.ABSTRACT);
                        break;
                    case 5:
                        this.modifiers.add(KModifier.FINAL);
                        break;
                    case 6:
                        this.modifiers.add(KModifier.EXTERNAL);
                        break;
                    case 7:
                        break;
                    case 8:
                        addAnnotation(Reflection.getOrCreateKotlinClass(JvmStatic.class));
                        break;
                    case 9:
                        addAnnotation(Reflection.getOrCreateKotlinClass(Synchronized.class));
                        break;
                    case 10:
                        addAnnotation(Reflection.getOrCreateKotlinClass(Strictfp.class));
                        break;
                    default:
                        throw new IllegalArgumentException("unexpected fun modifier " + modifier);
                }
            }
            this.modifiers.add(kModifier);
        }

        public final Builder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            if (!(!FunSpec.Companion.isAccessor(this.name))) {
                throw new IllegalStateException(("" + this.name + " cannot have type variables").toString());
            }
            CollectionsKt.addAll(this.typeVariables, typeVariables);
            return this;
        }

        public final Builder addTypeVariable(TypeVariableName typeVariable) {
            Intrinsics.checkParameterIsNotNull(typeVariable, "typeVariable");
            if (!(!FunSpec.Companion.isAccessor(this.name))) {
                throw new IllegalStateException(("" + this.name + " cannot have type variables").toString());
            }
            this.typeVariables.add(typeVariable);
            return this;
        }

        public final Builder receiver(TypeName receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            if (!(!FunSpec.Companion.isConstructor(this.name))) {
                throw new IllegalStateException(("" + this.name + " cannot have receiver type").toString());
            }
            this.receiverType = receiverType;
            return this;
        }

        public final Builder receiver(Type receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            return receiver(TypeNames.get(receiverType));
        }

        public final Builder receiver(KClass<?> receiverType) {
            Intrinsics.checkParameterIsNotNull(receiverType, "receiverType");
            return receiver(TypeNames.get(receiverType));
        }

        public final Builder returns(TypeName returnType) {
            Intrinsics.checkParameterIsNotNull(returnType, "returnType");
            if (!((FunSpec.Companion.isConstructor(this.name) || FunSpec.Companion.isAccessor(this.name)) ? false : true)) {
                throw new IllegalStateException(("" + this.name + " cannot have a return type").toString());
            }
            this.returnType = returnType;
            return this;
        }

        public final Builder returns(Type returnType) {
            Intrinsics.checkParameterIsNotNull(returnType, "returnType");
            return returns(TypeNames.get(returnType));
        }

        public final Builder returns(KClass<?> returnType) {
            Intrinsics.checkParameterIsNotNull(returnType, "returnType");
            return returns(TypeNames.get(returnType));
        }

        public final Builder addParameters(Iterable<ParameterSpec> parameterSpecs) {
            Intrinsics.checkParameterIsNotNull(parameterSpecs, "parameterSpecs");
            for (ParameterSpec parameterSpec : parameterSpecs) {
                addParameter(parameterSpec);
            }
            return this;
        }

        public final Builder addParameter(ParameterSpec parameterSpec) {
            Intrinsics.checkParameterIsNotNull(parameterSpec, "parameterSpec");
            boolean z = true;
            if (!(!Intrinsics.areEqual(this.name, FunSpec.GETTER))) {
                throw new IllegalStateException(("" + this.name + " cannot have parameters").toString());
            }
            if (!(!Intrinsics.areEqual(this.name, FunSpec.SETTER)) && this.parameters.size() != 0) {
                z = false;
            }
            if (!z) {
                throw new IllegalStateException(("" + this.name + " can have only one parameter").toString());
            }
            this.parameters.add(parameterSpec);
            return this;
        }

        public final Builder callThisConstructor(String... args) {
            Intrinsics.checkParameterIsNotNull(args, "args");
            String[] strArr = args;
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str : strArr) {
                arrayList.add(CodeBlock.Companion.of(str, new Object[0]));
            }
            ArrayList arrayList2 = arrayList;
            Object[] array = arrayList2.toArray(new CodeBlock[arrayList2.size()]);
            if (array != null) {
                CodeBlock[] codeBlockArr = (CodeBlock[]) array;
                callConstructor("this", (CodeBlock[]) Arrays.copyOf(codeBlockArr, codeBlockArr.length));
                return this;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }

        public final Builder callThisConstructor(CodeBlock... args) {
            Intrinsics.checkParameterIsNotNull(args, "args");
            callConstructor("this", (CodeBlock[]) Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder callSuperConstructor(String... args) {
            Intrinsics.checkParameterIsNotNull(args, "args");
            String[] strArr = args;
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str : strArr) {
                arrayList.add(CodeBlock.Companion.of(str, new Object[0]));
            }
            ArrayList arrayList2 = arrayList;
            Object[] array = arrayList2.toArray(new CodeBlock[arrayList2.size()]);
            if (array != null) {
                CodeBlock[] codeBlockArr = (CodeBlock[]) array;
                callConstructor("super", (CodeBlock[]) Arrays.copyOf(codeBlockArr, codeBlockArr.length));
                return this;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }

        public final Builder callSuperConstructor(CodeBlock... args) {
            Intrinsics.checkParameterIsNotNull(args, "args");
            callConstructor("super", (CodeBlock[]) Arrays.copyOf(args, args.length));
            return this;
        }

        private final void callConstructor(String str, CodeBlock... codeBlockArr) {
            if (!FunSpec.Companion.isConstructor(this.name)) {
                throw new IllegalStateException("only constructors can delegate to other constructors!".toString());
            }
            this.delegateConstructor = str;
            CollectionsKt.addAll(this.delegateConstructorArguments, codeBlockArr);
        }

        public final Builder addParameter(String name, TypeName type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addParameter(ParameterSpec.Companion.builder(name, type, (KModifier[]) Arrays.copyOf(modifiers, modifiers.length)).build());
        }

        public final Builder addParameter(String name, Type type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addParameter(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        public final Builder addParameter(String name, KClass<?> type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addParameter(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        public final Builder addCode(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.body.add(format, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder addNamedCode(String format, Map<String, ?> args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.body.addNamed(format, args);
            return this;
        }

        public final Builder addCode(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            this.body.add(codeBlock);
            return this;
        }

        public final Builder addComment(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            CodeBlock.Builder builder = this.body;
            builder.add("// " + format + "\n", Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder beginControlFlow(String controlFlow, Object... args) {
            Intrinsics.checkParameterIsNotNull(controlFlow, "controlFlow");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.body.beginControlFlow(controlFlow, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder nextControlFlow(String controlFlow, Object... args) {
            Intrinsics.checkParameterIsNotNull(controlFlow, "controlFlow");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.body.nextControlFlow(controlFlow, Arrays.copyOf(args, args.length));
            return this;
        }

        public final Builder endControlFlow() {
            this.body.endControlFlow();
            return this;
        }

        public final Builder addStatement(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            this.body.addStatement(format, Arrays.copyOf(args, args.length));
            return this;
        }

        public final FunSpec build() {
            return new FunSpec(this, null);
        }

        public static /* bridge */ /* synthetic */ Builder callThisConstructor$default(Builder builder, CodeBlock[] codeBlockArr, int i, Object obj) {
            if ((i & 1) != 0) {
                codeBlockArr = new CodeBlock[0];
            }
            return builder.callThisConstructor(codeBlockArr);
        }

        public static /* bridge */ /* synthetic */ Builder callSuperConstructor$default(Builder builder, CodeBlock[] codeBlockArr, int i, Object obj) {
            if ((i & 1) != 0) {
                codeBlockArr = new CodeBlock[0];
            }
            return builder.callSuperConstructor(codeBlockArr);
        }
    }

    /* compiled from: FunSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0007J\b\u0010\u0012\u001a\u00020\u0010H\u0007J\b\u0010\u0013\u001a\u00020\u0010H\u0007J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J \u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\b\u0010\u001b\u001a\u00020\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\u00020\f*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0018\u0010\u000e\u001a\u00020\f*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\r¨\u0006\u001c"}, d2 = {"Lcom/squareup/kotlinpoet/FunSpec$Companion;", "", "()V", "CONSTRUCTOR", "", "EXPRESSION_BODY_PREFIX", "Lcom/squareup/kotlinpoet/CodeBlock;", "getEXPRESSION_BODY_PREFIX", "()Lcom/squareup/kotlinpoet/CodeBlock;", "GETTER", "SETTER", "isAccessor", "", "(Ljava/lang/String;)Z", "isConstructor", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "name", "constructorBuilder", "getterBuilder", "overriding", "method", "Ljavax/lang/model/element/ExecutableElement;", "enclosing", "Ljavax/lang/model/type/DeclaredType;", "types", "Ljavax/lang/model/util/Types;", "setterBuilder", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isConstructor(String str) {
            return Intrinsics.areEqual(str, FunSpec.CONSTRUCTOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isAccessor(String str) {
            boolean isOneOf;
            isOneOf = UtilKt.isOneOf(str, FunSpec.GETTER, FunSpec.SETTER, (r16 & 4) != 0 ? null : null, (r16 & 8) != 0 ? null : null, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
            return isOneOf;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CodeBlock getEXPRESSION_BODY_PREFIX() {
            return FunSpec.EXPRESSION_BODY_PREFIX;
        }

        @JvmStatic
        public final Builder builder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(name);
        }

        @JvmStatic
        public final Builder constructorBuilder() {
            return new Builder(FunSpec.CONSTRUCTOR);
        }

        @JvmStatic
        public final Builder getterBuilder() {
            return new Builder(FunSpec.GETTER);
        }

        @JvmStatic
        public final Builder setterBuilder() {
            return new Builder(FunSpec.SETTER);
        }

        @JvmStatic
        public final Builder overriding(ExecutableElement method) {
            Intrinsics.checkParameterIsNotNull(method, "method");
            Set modifiers = method.getModifiers();
            Intrinsics.checkExpressionValueIsNotNull(modifiers, "method.modifiers");
            if (!((modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.FINAL) || modifiers.contains(Modifier.STATIC)) ? false : true)) {
                throw new IllegalArgumentException(("cannot override method with modifiers: " + modifiers).toString());
            }
            Builder builder = FunSpec.Companion.builder(method.getSimpleName().toString());
            builder.addModifiers(KModifier.OVERRIDE);
            Set mutableSet = CollectionsKt.toMutableSet(modifiers);
            mutableSet.remove(Modifier.ABSTRACT);
            builder.jvmModifiers(mutableSet);
            List<TypeParameterElement> typeParameters = method.getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            for (TypeParameterElement typeParameterElement : typeParameters) {
                TypeVariable asType = typeParameterElement.asType();
                if (asType == null) {
                    throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.type.TypeVariable");
                }
                arrayList.add(asType);
            }
            ArrayList<TypeVariable> arrayList2 = arrayList;
            ArrayList<TypeVariableName> arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (TypeVariable typeVariable : arrayList2) {
                arrayList3.add(TypeVariableNames.get(typeVariable));
            }
            for (TypeVariableName typeVariableName : arrayList3) {
                builder.addTypeVariable(typeVariableName);
            }
            builder.returns(TypeNames.get(method.getReturnType()));
            builder.addParameters(ParameterSpec.Companion.parametersOf(method));
            if (method.isVarArgs()) {
                builder.getParameters$kotlinpoet().set(CollectionsKt.getLastIndex(builder.getParameters$kotlinpoet()), ParameterSpec.toBuilder$default((ParameterSpec) CollectionsKt.last((List<? extends Object>) builder.getParameters$kotlinpoet()), null, null, 3, null).addModifiers(KModifier.VARARG).build());
            }
            if (!method.getThrownTypes().isEmpty()) {
                String joinToString$default = CollectionsKt.joinToString$default(method.getThrownTypes(), null, null, null, 0, null, new Function1<TypeMirror, String>() { // from class: com.squareup.kotlinpoet.FunSpec$Companion$overriding$throwsValueString$1
                    @Override // kotlin.jvm.functions.Function1
                    public final String invoke(TypeMirror typeMirror) {
                        return "%T::class";
                    }
                }, 31, null);
                AnnotationSpec.Builder builder2 = AnnotationSpec.Companion.builder(Reflection.getOrCreateKotlinClass(Throws.class));
                List thrownTypes = method.getThrownTypes();
                if (thrownTypes == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
                }
                Object[] array = thrownTypes.toArray(new TypeMirror[thrownTypes.size()]);
                if (array != null) {
                    builder.addAnnotation(builder2.addMember(joinToString$default, Arrays.copyOf(array, array.length)).build());
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            return builder;
        }

        @JvmStatic
        public final Builder overriding(ExecutableElement method, DeclaredType enclosing, Types types) {
            Intrinsics.checkParameterIsNotNull(method, "method");
            Intrinsics.checkParameterIsNotNull(enclosing, "enclosing");
            Intrinsics.checkParameterIsNotNull(types, "types");
            ExecutableType asMemberOf = types.asMemberOf(enclosing, (Element) method);
            if (asMemberOf == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.type.ExecutableType");
            }
            ExecutableType executableType = asMemberOf;
            List parameterTypes = executableType.getParameterTypes();
            TypeMirror returnType = executableType.getReturnType();
            Builder overriding = overriding(method);
            overriding.returns(TypeNames.get(returnType));
            int size = overriding.getParameters$kotlinpoet().size();
            for (int i = 0; i < size; i++) {
                ParameterSpec parameterSpec = overriding.getParameters$kotlinpoet().get(i);
                overriding.getParameters$kotlinpoet().set(i, parameterSpec.toBuilder(parameterSpec.getName(), TypeNames.get((TypeMirror) parameterTypes.get(i))).build());
            }
            return overriding;
        }
    }
}
