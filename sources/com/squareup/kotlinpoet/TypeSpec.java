package com.squareup.kotlinpoet;

import com.squareup.kotlinpoet.CodeBlock;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
/* compiled from: TypeSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 N2\u00020\u0001:\u0003MNOB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002010\u0012H\u0002J\u001f\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0013H\u0000¢\u0006\u0002\bFJ\u0013\u0010G\u001a\u00020\u001a2\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010I\u001a\u00020JH\u0016J\u0006\u0010K\u001a\u00020\u0003J\b\u0010L\u001a\u00020\u0013H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00000\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u0014\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\rR\u0011\u0010\u001f\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b \u0010\rR\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0013\u0010*\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0013\u0010-\u001a\u0004\u0018\u00010\u0017¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\b\u0012\u0004\u0012\u0002010\u0006¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\tR\u0011\u00103\u001a\u000204¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0017\u00107\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\tR\u0017\u00109\u001a\b\u0012\u0004\u0012\u0002040\u0006¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\tR\u0017\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\tR\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u0006¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\t¨\u0006P"}, d2 = {"Lcom/squareup/kotlinpoet/TypeSpec;", "", "builder", "Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "(Lcom/squareup/kotlinpoet/TypeSpec$Builder;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations", "()Ljava/util/List;", "anonymousTypeArguments", "Lcom/squareup/kotlinpoet/CodeBlock;", "getAnonymousTypeArguments", "()Lcom/squareup/kotlinpoet/CodeBlock;", "companionObject", "getCompanionObject", "()Lcom/squareup/kotlinpoet/TypeSpec;", "enumConstants", "", "", "getEnumConstants", "()Ljava/util/Map;", "funSpecs", "Lcom/squareup/kotlinpoet/FunSpec;", "getFunSpecs", "hasNoBody", "", "getHasNoBody", "()Z", "initializerBlock", "getInitializerBlock", "kdoc", "getKdoc", "kind", "Lcom/squareup/kotlinpoet/TypeSpec$Kind;", "getKind", "()Lcom/squareup/kotlinpoet/TypeSpec$Kind;", "modifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers", "()Ljava/util/Set;", "name", "getName", "()Ljava/lang/String;", "primaryConstructor", "getPrimaryConstructor", "()Lcom/squareup/kotlinpoet/FunSpec;", "propertySpecs", "Lcom/squareup/kotlinpoet/PropertySpec;", "getPropertySpecs", "superclass", "Lcom/squareup/kotlinpoet/TypeName;", "getSuperclass", "()Lcom/squareup/kotlinpoet/TypeName;", "superclassConstructorParameters", "getSuperclassConstructorParameters", "superinterfaces", "getSuperinterfaces", "typeSpecs", "getTypeSpecs", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables", "constructorProperties", "emit", "", "codeWriter", "Lcom/squareup/kotlinpoet/CodeWriter;", "enumName", "emit$kotlinpoet", "equals", "other", "hashCode", "", "toBuilder", "toString", "Builder", "Companion", "Kind", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class TypeSpec {
    public static final Companion Companion = new Companion(null);
    private final List<AnnotationSpec> annotations;
    private final CodeBlock anonymousTypeArguments;
    private final TypeSpec companionObject;
    private final Map<String, TypeSpec> enumConstants;
    private final List<FunSpec> funSpecs;
    private final CodeBlock initializerBlock;
    private final CodeBlock kdoc;
    private final Kind kind;
    private final Set<KModifier> modifiers;
    private final String name;
    private final FunSpec primaryConstructor;
    private final List<PropertySpec> propertySpecs;
    private final TypeName superclass;
    private final List<CodeBlock> superclassConstructorParameters;
    private final List<TypeName> superinterfaces;
    private final List<TypeSpec> typeSpecs;
    private final List<TypeVariableName> typeVariables;

    @JvmStatic
    public static final Builder annotationBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.annotationBuilder(className);
    }

    @JvmStatic
    public static final Builder annotationBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.annotationBuilder(name);
    }

    @JvmStatic
    public static final Builder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
        Intrinsics.checkParameterIsNotNull(typeArgumentsFormat, "typeArgumentsFormat");
        Intrinsics.checkParameterIsNotNull(args, "args");
        return Companion.anonymousClassBuilder(typeArgumentsFormat, args);
    }

    @JvmStatic
    public static final Builder classBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.classBuilder(className);
    }

    @JvmStatic
    public static final Builder classBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.classBuilder(name);
    }

    @JvmStatic
    public static final Builder companionObjectBuilder() {
        return Companion.companionObjectBuilder$default(Companion, null, 1, null);
    }

    @JvmStatic
    public static final Builder companionObjectBuilder(String str) {
        return Companion.companionObjectBuilder(str);
    }

    @JvmStatic
    public static final Builder enumBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.enumBuilder(className);
    }

    @JvmStatic
    public static final Builder enumBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.enumBuilder(name);
    }

    @JvmStatic
    public static final Builder expectClassBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.expectClassBuilder(className);
    }

    @JvmStatic
    public static final Builder expectClassBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.expectClassBuilder(name);
    }

    @JvmStatic
    public static final Builder interfaceBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.interfaceBuilder(className);
    }

    @JvmStatic
    public static final Builder interfaceBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.interfaceBuilder(name);
    }

    @JvmStatic
    public static final Builder objectBuilder(ClassName className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        return Companion.objectBuilder(className);
    }

    @JvmStatic
    public static final Builder objectBuilder(String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return Companion.objectBuilder(name);
    }

    private TypeSpec(Builder builder) {
        this.kind = builder.getKind$kotlinpoet();
        this.name = builder.getName$kotlinpoet();
        this.anonymousTypeArguments = builder.getAnonymousTypeArguments$kotlinpoet();
        this.kdoc = builder.getKdoc$kotlinpoet().build();
        this.annotations = UtilKt.toImmutableList(builder.getAnnotations$kotlinpoet());
        this.modifiers = UtilKt.toImmutableSet(builder.getModifiers$kotlinpoet());
        this.typeVariables = UtilKt.toImmutableList(builder.getTypeVariables$kotlinpoet());
        this.companionObject = builder.getCompanionObject$kotlinpoet();
        this.primaryConstructor = builder.getPrimaryConstructor$kotlinpoet();
        this.superclass = builder.getSuperclass$kotlinpoet();
        this.superclassConstructorParameters = UtilKt.toImmutableList(builder.getSuperclassConstructorParameters$kotlinpoet());
        this.superinterfaces = UtilKt.toImmutableList(builder.getSuperinterfaces$kotlinpoet());
        this.enumConstants = UtilKt.toImmutableMap(builder.getEnumConstants$kotlinpoet());
        this.propertySpecs = UtilKt.toImmutableList(builder.getPropertySpecs$kotlinpoet());
        this.initializerBlock = builder.getInitializerBlock$kotlinpoet().build();
        this.funSpecs = UtilKt.toImmutableList(builder.getFunSpecs$kotlinpoet());
        this.typeSpecs = UtilKt.toImmutableList(builder.getTypeSpecs$kotlinpoet());
    }

    public /* synthetic */ TypeSpec(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final String getName() {
        return this.name;
    }

    public final CodeBlock getAnonymousTypeArguments() {
        return this.anonymousTypeArguments;
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

    public final TypeSpec getCompanionObject() {
        return this.companionObject;
    }

    public final FunSpec getPrimaryConstructor() {
        return this.primaryConstructor;
    }

    public final TypeName getSuperclass() {
        return this.superclass;
    }

    public final List<CodeBlock> getSuperclassConstructorParameters() {
        return this.superclassConstructorParameters;
    }

    public final List<TypeName> getSuperinterfaces() {
        return this.superinterfaces;
    }

    public final Map<String, TypeSpec> getEnumConstants() {
        return this.enumConstants;
    }

    public final List<PropertySpec> getPropertySpecs() {
        return this.propertySpecs;
    }

    public final CodeBlock getInitializerBlock() {
        return this.initializerBlock;
    }

    public final List<FunSpec> getFunSpecs() {
        return this.funSpecs;
    }

    public final List<TypeSpec> getTypeSpecs() {
        return this.typeSpecs;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder(this.kind, this.name, this.anonymousTypeArguments);
        builder.getKdoc$kotlinpoet().add(this.kdoc);
        CollectionsKt.addAll(builder.getAnnotations$kotlinpoet(), this.annotations);
        CollectionsKt.addAll(builder.getModifiers$kotlinpoet(), this.modifiers);
        CollectionsKt.addAll(builder.getTypeVariables$kotlinpoet(), this.typeVariables);
        builder.setSuperclass$kotlinpoet(this.superclass);
        CollectionsKt.addAll(builder.getSuperclassConstructorParameters$kotlinpoet(), this.superclassConstructorParameters);
        CollectionsKt.addAll(builder.getSuperinterfaces$kotlinpoet(), this.superinterfaces);
        builder.getEnumConstants$kotlinpoet().putAll(this.enumConstants);
        CollectionsKt.addAll(builder.getPropertySpecs$kotlinpoet(), this.propertySpecs);
        CollectionsKt.addAll(builder.getFunSpecs$kotlinpoet(), this.funSpecs);
        CollectionsKt.addAll(builder.getTypeSpecs$kotlinpoet(), this.typeSpecs);
        builder.getInitializerBlock$kotlinpoet().add(this.initializerBlock);
        return builder;
    }

    public final void emit$kotlinpoet(final CodeWriter codeWriter, String str) {
        CodeBlock of;
        boolean z;
        boolean z2;
        boolean z3;
        Object obj;
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        int statementLine = codeWriter.getStatementLine();
        codeWriter.setStatementLine(-1);
        final Map<String, PropertySpec> constructorProperties = constructorProperties();
        try {
            if (str != null) {
                codeWriter.emitKdoc(this.kdoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emitCode("%L", str);
                CodeBlock codeBlock = this.anonymousTypeArguments;
                if (codeBlock == null) {
                    Intrinsics.throwNpe();
                }
                if (!codeBlock.getFormatParts$kotlinpoet().isEmpty()) {
                    codeWriter.emit("(");
                    codeWriter.emitCode(this.anonymousTypeArguments);
                    codeWriter.emit(")");
                }
                if (getHasNoBody()) {
                    return;
                }
                codeWriter.emit(" {\n");
            } else if (this.anonymousTypeArguments != null) {
                codeWriter.emitCode("object : %T(", this.superinterfaces.isEmpty() ^ true ? this.superinterfaces.get(0) : this.superclass);
                codeWriter.emitCode(this.anonymousTypeArguments);
                codeWriter.emit(") {\n");
            } else {
                codeWriter.emitKdoc(this.kdoc);
                codeWriter.emitAnnotations(this.annotations, false);
                codeWriter.emitModifiers(this.modifiers, SetsKt.setOf(KModifier.PUBLIC));
                codeWriter.emit(this.kind.getDeclarationKeyword$kotlinpoet());
                String str2 = this.name;
                if (str2 != null) {
                    codeWriter.emitCode(" %L", str2);
                }
                codeWriter.emitTypeVariables(this.typeVariables);
                codeWriter.emitWhereBlock(this.typeVariables);
                FunSpec funSpec = this.primaryConstructor;
                if (funSpec != null) {
                    if (!funSpec.getAnnotations().isEmpty()) {
                        codeWriter.emit(" ");
                        codeWriter.emitAnnotations(funSpec.getAnnotations(), true);
                        z2 = true;
                        z3 = true;
                    } else {
                        z2 = false;
                        z3 = false;
                    }
                    if (!funSpec.getModifiers().isEmpty()) {
                        if (!z2) {
                            codeWriter.emit(" ");
                        }
                        CodeWriter.emitModifiers$default(codeWriter, funSpec.getModifiers(), null, 2, null);
                        z3 = true;
                    }
                    if (z3) {
                        codeWriter.emit("constructor");
                    }
                    ParameterSpecKt.emit(funSpec.getParameters(), codeWriter, new Function1<ParameterSpec, Unit>() { // from class: com.squareup.kotlinpoet.TypeSpec$emit$$inlined$let$lambda$1
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
                            PropertySpec propertySpec = (PropertySpec) constructorProperties.get(param.getName());
                            if (propertySpec != null) {
                                propertySpec.emit$kotlinpoet(CodeWriter.this, SetsKt.setOf(KModifier.PUBLIC), false, true);
                                param.emitDefaultValue$kotlinpoet(CodeWriter.this);
                                return;
                            }
                            ParameterSpec.emit$kotlinpoet$default(param, CodeWriter.this, false, 2, null);
                        }
                    });
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : CollectionsKt.listOf(this.superclass)) {
                    if (!Intrinsics.areEqual((TypeName) obj2, TypeNames.ANY)) {
                        arrayList.add(obj2);
                    }
                }
                ArrayList<TypeName> arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                for (TypeName typeName : arrayList2) {
                    if (this.primaryConstructor == null) {
                        List<FunSpec> list = this.funSpecs;
                        if (!(list instanceof Collection) || !list.isEmpty()) {
                            for (FunSpec funSpec2 : list) {
                                if (funSpec2.isConstructor()) {
                                    z = false;
                                    break;
                                }
                            }
                        }
                        z = true;
                        if (!z) {
                            of = CodeBlock.Companion.of("%T", typeName);
                            arrayList3.add(of);
                        }
                    }
                    of = CodeBlock.Companion.of("%T(%L)", typeName, CodeBlocks.joinToCode$default(this.superclassConstructorParameters, null, null, null, 7, null));
                    arrayList3.add(of);
                }
                ArrayList arrayList4 = arrayList3;
                List<TypeName> list2 = this.superinterfaces;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList5.add(CodeBlock.Companion.of("%T", (TypeName) it.next()));
                }
                List plus = CollectionsKt.plus((Collection) arrayList4, (Iterable) arrayList5);
                if (!plus.isEmpty()) {
                    codeWriter.emitCode(CodeBlocks.joinToCode$default(plus, null, " : ", null, 5, null));
                }
                if (getHasNoBody()) {
                    codeWriter.emit("\n");
                    return;
                } else if (!Intrinsics.areEqual(this.kind, Kind.ANNOTATION)) {
                    codeWriter.emit(" {\n");
                }
            }
            codeWriter.pushType(this);
            CodeWriter.indent$default(codeWriter, 0, 1, null);
            Iterator<Map.Entry<String, TypeSpec>> it2 = this.enumConstants.entrySet().iterator();
            boolean z4 = true;
            while (it2.hasNext()) {
                Map.Entry<String, TypeSpec> next = it2.next();
                if (!z4) {
                    codeWriter.emit("\n");
                }
                next.getValue().emit$kotlinpoet(codeWriter, next.getKey());
                if (it2.hasNext()) {
                    codeWriter.emit(",\n");
                } else {
                    if (!(!this.propertySpecs.isEmpty()) && !(!this.funSpecs.isEmpty()) && !(!this.typeSpecs.isEmpty())) {
                        codeWriter.emit("\n");
                    }
                    codeWriter.emit(";\n");
                }
                z4 = false;
            }
            for (PropertySpec propertySpec : this.propertySpecs) {
                if (!constructorProperties.containsKey(propertySpec.getName())) {
                    if (!z4) {
                        codeWriter.emit("\n");
                    }
                    PropertySpec.emit$kotlinpoet$default(propertySpec, codeWriter, this.kind.getImplicitPropertyModifiers$kotlinpoet(), false, false, 12, null);
                    z4 = false;
                }
            }
            FunSpec funSpec3 = this.primaryConstructor;
            if (funSpec3 != null && funSpec3.getBody().isNotEmpty()) {
                codeWriter.emit("init {\n");
                CodeWriter.indent$default(codeWriter, 0, 1, null);
                codeWriter.emitCode(this.primaryConstructor.getBody());
                CodeWriter.unindent$default(codeWriter, 0, 1, null);
                codeWriter.emit("}\n");
            }
            if (this.initializerBlock.isNotEmpty()) {
                if (!z4) {
                    codeWriter.emit("\n");
                }
                codeWriter.emitCode(this.initializerBlock);
                z4 = false;
            }
            for (FunSpec funSpec4 : this.funSpecs) {
                if (funSpec4.isConstructor()) {
                    if (!z4) {
                        codeWriter.emit("\n");
                    }
                    String str3 = this.name;
                    if (str3 == null) {
                        Intrinsics.throwNpe();
                    }
                    funSpec4.emit$kotlinpoet(codeWriter, str3, this.kind.getImplicitFunctionModifiers$kotlinpoet());
                    z4 = false;
                }
            }
            for (FunSpec funSpec5 : this.funSpecs) {
                if (!funSpec5.isConstructor()) {
                    if (!z4) {
                        codeWriter.emit("\n");
                    }
                    funSpec5.emit$kotlinpoet(codeWriter, this.name, this.kind.getImplicitFunctionModifiers$kotlinpoet());
                    z4 = false;
                }
            }
            for (TypeSpec typeSpec : this.typeSpecs) {
                if (!z4) {
                    codeWriter.emit("\n");
                }
                typeSpec.emit$kotlinpoet(codeWriter, null);
                z4 = false;
            }
            TypeSpec typeSpec2 = this.companionObject;
            if (typeSpec2 != null) {
                obj = null;
                typeSpec2.emit$kotlinpoet(codeWriter, null);
                Unit unit = Unit.INSTANCE;
            } else {
                obj = null;
            }
            CodeWriter.unindent$default(codeWriter, 0, 1, obj);
            codeWriter.popType();
            if (!Intrinsics.areEqual(this.kind, Kind.ANNOTATION)) {
                codeWriter.emit("}");
            }
            if (str == null && this.anonymousTypeArguments == null) {
                codeWriter.emit("\n");
            }
        } finally {
            codeWriter.setStatementLine(statementLine);
        }
    }

    private final Map<String, PropertySpec> constructorProperties() {
        if (this.primaryConstructor == null) {
            return MapsKt.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PropertySpec propertySpec : this.propertySpecs) {
            ParameterSpec parameter$kotlinpoet = this.primaryConstructor.parameter$kotlinpoet(propertySpec.getName());
            if (parameter$kotlinpoet != null && !(!Intrinsics.areEqual(parameter$kotlinpoet.getType(), propertySpec.getType())) && !(!Intrinsics.areEqual(CodeBlock.Companion.of("%N", parameter$kotlinpoet), propertySpec.getInitializer()))) {
                linkedHashMap.put(propertySpec.getName(), propertySpec);
            }
        }
        return linkedHashMap;
    }

    private final boolean getHasNoBody() {
        CodeBlock body;
        if (Intrinsics.areEqual(this.kind, Kind.ANNOTATION)) {
            return true;
        }
        if (!this.propertySpecs.isEmpty()) {
            Map<String, PropertySpec> constructorProperties = constructorProperties();
            for (PropertySpec propertySpec : this.propertySpecs) {
                if (!constructorProperties.containsKey(propertySpec.getName())) {
                    return false;
                }
            }
        }
        if (this.companionObject == null && this.enumConstants.isEmpty() && this.initializerBlock.isEmpty()) {
            FunSpec funSpec = this.primaryConstructor;
            if (((funSpec == null || (body = funSpec.getBody()) == null) ? true : body.isEmpty()) && this.funSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                return true;
            }
        }
        return false;
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
        emit$kotlinpoet(new CodeWriter(sb, null, null, null, 14, null), null);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* compiled from: TypeSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B+\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0015"}, d2 = {"Lcom/squareup/kotlinpoet/TypeSpec$Kind;", "", "declarationKeyword", "", "implicitPropertyModifiers", "", "Lcom/squareup/kotlinpoet/KModifier;", "implicitFunctionModifiers", "(Ljava/lang/String;ILjava/lang/String;Ljava/util/Set;Ljava/util/Set;)V", "getDeclarationKeyword$kotlinpoet", "()Ljava/lang/String;", "getImplicitFunctionModifiers$kotlinpoet", "()Ljava/util/Set;", "getImplicitPropertyModifiers$kotlinpoet", "CLASS", "EXPECT_CLASS", "OBJECT", "COMPANION", "INTERFACE", "ENUM", "ANNOTATION", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public enum Kind {
        CLASS("class", SetsKt.setOf(KModifier.PUBLIC), SetsKt.setOf(KModifier.PUBLIC)),
        EXPECT_CLASS("class", SetsKt.setOf((Object[]) new KModifier[]{KModifier.PUBLIC, KModifier.EXPECT}), SetsKt.setOf((Object[]) new KModifier[]{KModifier.PUBLIC, KModifier.EXPECT})),
        OBJECT("object", SetsKt.setOf(KModifier.PUBLIC), SetsKt.setOf(KModifier.PUBLIC)),
        COMPANION("companion object", SetsKt.setOf(KModifier.PUBLIC), SetsKt.setOf(KModifier.PUBLIC)),
        INTERFACE("interface", SetsKt.setOf(KModifier.PUBLIC), SetsKt.setOf((Object[]) new KModifier[]{KModifier.PUBLIC, KModifier.ABSTRACT})),
        ENUM("enum class", SetsKt.setOf(KModifier.PUBLIC), SetsKt.setOf(KModifier.PUBLIC)),
        ANNOTATION("annotation class", SetsKt.emptySet(), SetsKt.setOf((Object[]) new KModifier[]{KModifier.PUBLIC, KModifier.ABSTRACT}));
        
        private final String declarationKeyword;
        private final Set<KModifier> implicitFunctionModifiers;
        private final Set<KModifier> implicitPropertyModifiers;

        Kind(String declarationKeyword, Set implicitPropertyModifiers, Set implicitFunctionModifiers) {
            Intrinsics.checkParameterIsNotNull(declarationKeyword, "declarationKeyword");
            Intrinsics.checkParameterIsNotNull(implicitPropertyModifiers, "implicitPropertyModifiers");
            Intrinsics.checkParameterIsNotNull(implicitFunctionModifiers, "implicitFunctionModifiers");
            this.declarationKeyword = declarationKeyword;
            this.implicitPropertyModifiers = implicitPropertyModifiers;
            this.implicitFunctionModifiers = implicitFunctionModifiers;
        }

        public final String getDeclarationKeyword$kotlinpoet() {
            return this.declarationKeyword;
        }

        public final Set<KModifier> getImplicitPropertyModifiers$kotlinpoet() {
            return this.implicitPropertyModifiers;
        }

        public final Set<KModifier> getImplicitFunctionModifiers$kotlinpoet() {
            return this.implicitFunctionModifiers;
        }
    }

    /* compiled from: TypeSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B#\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010A\u001a\u00020\u00002\u0006\u0010B\u001a\u00020\u000bJ\u000e\u0010A\u001a\u00020\u00002\u0006\u0010C\u001a\u00020DJ\u0012\u0010A\u001a\u00020\u00002\n\u0010C\u001a\u0006\u0012\u0002\b\u00030EJ\u0012\u0010A\u001a\u00020\u00002\n\u0010C\u001a\u0006\u0012\u0002\b\u00030FJ\u0014\u0010G\u001a\u00020\u00002\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u000b0IJ\u001a\u0010J\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010K\u001a\u00020\u0011H\u0007J\u000e\u0010L\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u001bJ\u0014\u0010N\u001a\u00020\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0IJ\u000e\u0010O\u001a\u00020\u00002\u0006\u0010P\u001a\u00020\u0007J\u000e\u0010Q\u001a\u00020\u00002\u0006\u0010P\u001a\u00020\u0007J'\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020\u00052\u0012\u0010S\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010T\"\u00020\u0001¢\u0006\u0002\u0010UJ\u001f\u0010V\u001a\u00020\u00002\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020&0T\"\u00020&¢\u0006\u0002\u0010WJ\u0014\u0010X\u001a\u00020\u00002\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000IJ\u000e\u0010Y\u001a\u00020\u00002\u0006\u0010Z\u001a\u000200J/\u0010Y\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010[\u001a\u0002032\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020&0T\"\u00020&¢\u0006\u0002\u0010\\J/\u0010Y\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010[\u001a\u00020]2\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020&0T\"\u00020&¢\u0006\u0002\u0010^J3\u0010Y\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010[\u001a\u0006\u0012\u0002\b\u00030F2\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020&0T\"\u00020&¢\u0006\u0002\u0010_J\u000e\u0010`\u001a\u00020\u00002\u0006\u0010a\u001a\u00020\u0007J'\u0010`\u001a\u00020\u00002\u0006\u0010R\u001a\u00020\u00052\u0012\u0010S\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010T\"\u00020\u0001¢\u0006\u0002\u0010UJ\u000e\u0010b\u001a\u00020\u00002\u0006\u0010c\u001a\u000203J\u000e\u0010b\u001a\u00020\u00002\u0006\u0010c\u001a\u00020]J\u0012\u0010b\u001a\u00020\u00002\n\u0010c\u001a\u0006\u0012\u0002\b\u00030FJ\u0014\u0010d\u001a\u00020\u00002\f\u0010:\u001a\b\u0012\u0004\u0012\u0002030IJ\u000e\u0010e\u001a\u00020\u00002\u0006\u0010K\u001a\u00020\u0011J\u000e\u0010f\u001a\u00020\u00002\u0006\u0010g\u001a\u00020?J\u0014\u0010h\u001a\u00020\u00002\f\u0010>\u001a\b\u0012\u0004\u0012\u00020?0IJ\u0014\u0010i\u001a\u00020\u00002\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00110IJ\u0006\u0010j\u001a\u00020\u0011J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010k\u001a\u00020lH\u0002J\u0010\u0010*\u001a\u00020\u00002\b\u0010*\u001a\u0004\u0018\u00010\u001bJ\u000e\u00102\u001a\u00020\u00002\u0006\u00102\u001a\u000203J\u000e\u00102\u001a\u00020\u00002\u0006\u00102\u001a\u00020]J\u0012\u00102\u001a\u00020\u00002\n\u00102\u001a\u0006\u0012\u0002\b\u00030FR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0017X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\rR\u0014\u0010\u001d\u001a\u00020\u001eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u001eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\rR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010\u001bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\b\u0012\u0004\u0012\u0002000\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\rR\u001a\u00102\u001a\u000203X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u00108\u001a\b\u0012\u0004\u0012\u00020\u00070\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\rR\u001a\u0010:\u001a\b\u0012\u0004\u0012\u0002030\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\rR\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\rR\u001a\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\r¨\u0006m"}, d2 = {"Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "", "kind", "Lcom/squareup/kotlinpoet/TypeSpec$Kind;", "name", "", "anonymousTypeArguments", "Lcom/squareup/kotlinpoet/CodeBlock;", "(Lcom/squareup/kotlinpoet/TypeSpec$Kind;Ljava/lang/String;Lcom/squareup/kotlinpoet/CodeBlock;)V", "annotations", "", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "getAnnotations$kotlinpoet", "()Ljava/util/List;", "getAnonymousTypeArguments$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock;", "companionObject", "Lcom/squareup/kotlinpoet/TypeSpec;", "getCompanionObject$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeSpec;", "setCompanionObject$kotlinpoet", "(Lcom/squareup/kotlinpoet/TypeSpec;)V", "enumConstants", "", "getEnumConstants$kotlinpoet", "()Ljava/util/Map;", "funSpecs", "Lcom/squareup/kotlinpoet/FunSpec;", "getFunSpecs$kotlinpoet", "initializerBlock", "Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "getInitializerBlock$kotlinpoet", "()Lcom/squareup/kotlinpoet/CodeBlock$Builder;", "kdoc", "getKdoc$kotlinpoet", "getKind$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeSpec$Kind;", "modifiers", "Lcom/squareup/kotlinpoet/KModifier;", "getModifiers$kotlinpoet", "getName$kotlinpoet", "()Ljava/lang/String;", "primaryConstructor", "getPrimaryConstructor$kotlinpoet", "()Lcom/squareup/kotlinpoet/FunSpec;", "setPrimaryConstructor$kotlinpoet", "(Lcom/squareup/kotlinpoet/FunSpec;)V", "propertySpecs", "Lcom/squareup/kotlinpoet/PropertySpec;", "getPropertySpecs$kotlinpoet", "superclass", "Lcom/squareup/kotlinpoet/TypeName;", "getSuperclass$kotlinpoet", "()Lcom/squareup/kotlinpoet/TypeName;", "setSuperclass$kotlinpoet", "(Lcom/squareup/kotlinpoet/TypeName;)V", "superclassConstructorParameters", "getSuperclassConstructorParameters$kotlinpoet", "superinterfaces", "getSuperinterfaces$kotlinpoet", "typeSpecs", "getTypeSpecs$kotlinpoet", "typeVariables", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getTypeVariables$kotlinpoet", "addAnnotation", "annotationSpec", "annotation", "Lcom/squareup/kotlinpoet/ClassName;", "Ljava/lang/Class;", "Lkotlin/reflect/KClass;", "addAnnotations", "annotationSpecs", "", "addEnumConstant", "typeSpec", "addFunction", "funSpec", "addFunctions", "addInitializerBlock", "block", "addKdoc", "format", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "addModifiers", "([Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "addProperties", "addProperty", "propertySpec", "type", "(Ljava/lang/String;Lcom/squareup/kotlinpoet/TypeName;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "Ljava/lang/reflect/Type;", "(Ljava/lang/String;Ljava/lang/reflect/Type;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "(Ljava/lang/String;Lkotlin/reflect/KClass;[Lcom/squareup/kotlinpoet/KModifier;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "addSuperclassConstructorParameter", "codeBlock", "addSuperinterface", "superinterface", "addSuperinterfaces", "addType", "addTypeVariable", "typeVariable", "addTypeVariables", "addTypes", "build", "ensureCanHaveSuperclass", "", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<AnnotationSpec> annotations;
        private final CodeBlock anonymousTypeArguments;
        private TypeSpec companionObject;
        private final Map<String, TypeSpec> enumConstants;
        private final List<FunSpec> funSpecs;
        private final CodeBlock.Builder initializerBlock;
        private final CodeBlock.Builder kdoc;
        private final Kind kind;
        private final List<KModifier> modifiers;
        private final String name;
        private FunSpec primaryConstructor;
        private final List<PropertySpec> propertySpecs;
        private TypeName superclass;
        private final List<CodeBlock> superclassConstructorParameters;
        private final List<TypeName> superinterfaces;
        private final List<TypeSpec> typeSpecs;
        private final List<TypeVariableName> typeVariables;

        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 7})
        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Kind.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[Kind.INTERFACE.ordinal()] = 1;
                iArr[Kind.ANNOTATION.ordinal()] = 2;
                iArr[Kind.EXPECT_CLASS.ordinal()] = 3;
            }
        }

        public final Builder addEnumConstant(String str) {
            return addEnumConstant$default(this, str, null, 2, null);
        }

        public Builder(Kind kind, String str, CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(kind, "kind");
            this.kind = kind;
            this.name = str;
            this.anonymousTypeArguments = codeBlock;
            this.kdoc = CodeBlock.Companion.builder();
            this.annotations = new ArrayList();
            this.modifiers = new ArrayList();
            this.typeVariables = new ArrayList();
            this.superclass = TypeNames.ANY;
            this.superclassConstructorParameters = new ArrayList();
            this.superinterfaces = new ArrayList();
            this.enumConstants = new LinkedHashMap();
            this.propertySpecs = new ArrayList();
            this.initializerBlock = CodeBlock.Companion.builder();
            this.funSpecs = new ArrayList();
            this.typeSpecs = new ArrayList();
            if (str == null || UtilKt.isName(str)) {
                return;
            }
            throw new IllegalArgumentException(("not a valid name: " + str).toString());
        }

        public final Kind getKind$kotlinpoet() {
            return this.kind;
        }

        public final String getName$kotlinpoet() {
            return this.name;
        }

        public final CodeBlock getAnonymousTypeArguments$kotlinpoet() {
            return this.anonymousTypeArguments;
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

        public final FunSpec getPrimaryConstructor$kotlinpoet() {
            return this.primaryConstructor;
        }

        public final void setPrimaryConstructor$kotlinpoet(FunSpec funSpec) {
            this.primaryConstructor = funSpec;
        }

        public final TypeSpec getCompanionObject$kotlinpoet() {
            return this.companionObject;
        }

        public final void setCompanionObject$kotlinpoet(TypeSpec typeSpec) {
            this.companionObject = typeSpec;
        }

        public final TypeName getSuperclass$kotlinpoet() {
            return this.superclass;
        }

        public final void setSuperclass$kotlinpoet(TypeName typeName) {
            Intrinsics.checkParameterIsNotNull(typeName, "<set-?>");
            this.superclass = typeName;
        }

        public final List<CodeBlock> getSuperclassConstructorParameters$kotlinpoet() {
            return this.superclassConstructorParameters;
        }

        public final List<TypeName> getSuperinterfaces$kotlinpoet() {
            return this.superinterfaces;
        }

        public final Map<String, TypeSpec> getEnumConstants$kotlinpoet() {
            return this.enumConstants;
        }

        public final List<PropertySpec> getPropertySpecs$kotlinpoet() {
            return this.propertySpecs;
        }

        public final CodeBlock.Builder getInitializerBlock$kotlinpoet() {
            return this.initializerBlock;
        }

        public final List<FunSpec> getFunSpecs$kotlinpoet() {
            return this.funSpecs;
        }

        public final List<TypeSpec> getTypeSpecs$kotlinpoet() {
            return this.typeSpecs;
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
            return addAnnotation(AnnotationSpec.Companion.builder(annotation).build());
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
            if (!(this.anonymousTypeArguments == null)) {
                throw new IllegalStateException("forbidden on anonymous types.".toString());
            }
            CollectionsKt.addAll(this.modifiers, modifiers);
            return this;
        }

        public final Builder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
            Intrinsics.checkParameterIsNotNull(typeVariables, "typeVariables");
            if (!(this.anonymousTypeArguments == null)) {
                throw new IllegalStateException("forbidden on anonymous types.".toString());
            }
            CollectionsKt.addAll(this.typeVariables, typeVariables);
            return this;
        }

        public final Builder addTypeVariable(TypeVariableName typeVariable) {
            Intrinsics.checkParameterIsNotNull(typeVariable, "typeVariable");
            if (!(this.anonymousTypeArguments == null)) {
                throw new IllegalStateException("forbidden on anonymous types.".toString());
            }
            this.typeVariables.add(typeVariable);
            return this;
        }

        public final Builder companionObject(TypeSpec companionObject) {
            boolean isOneOf;
            Intrinsics.checkParameterIsNotNull(companionObject, "companionObject");
            isOneOf = UtilKt.isOneOf(this.kind, Kind.CLASS, Kind.INTERFACE, (r16 & 4) != 0 ? null : null, (r16 & 8) != 0 ? null : null, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
            if (!isOneOf) {
                throw new IllegalStateException(("" + this.kind + " can't have a companion object").toString());
            } else if (!Intrinsics.areEqual(companionObject.getKind(), Kind.COMPANION)) {
                throw new IllegalArgumentException(("expected a companion object class but was " + this.kind + ' ').toString());
            } else {
                this.companionObject = companionObject;
                return this;
            }
        }

        public final Builder primaryConstructor(FunSpec funSpec) {
            boolean isOneOf;
            isOneOf = UtilKt.isOneOf(this.kind, Kind.CLASS, Kind.EXPECT_CLASS, (r16 & 4) != 0 ? null : Kind.ENUM, (r16 & 8) != 0 ? null : Kind.ANNOTATION, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
            if (!isOneOf) {
                throw new IllegalStateException(("" + this.kind + " can't have initializer blocks").toString());
            } else if (funSpec != null && !funSpec.isConstructor()) {
                throw new IllegalArgumentException(("expected a constructor but was " + funSpec.getName()).toString());
            } else {
                this.primaryConstructor = funSpec;
                return this;
            }
        }

        public final Builder superclass(TypeName superclass) {
            Intrinsics.checkParameterIsNotNull(superclass, "superclass");
            ensureCanHaveSuperclass();
            if (!(this.superclass == TypeNames.ANY)) {
                throw new IllegalStateException(("superclass already set to " + this.superclass).toString());
            }
            this.superclass = superclass;
            return this;
        }

        private final void ensureCanHaveSuperclass() {
            boolean isOneOf;
            isOneOf = UtilKt.isOneOf(this.kind, Kind.CLASS, Kind.EXPECT_CLASS, (r16 & 4) != 0 ? null : Kind.OBJECT, (r16 & 8) != 0 ? null : Kind.COMPANION, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
            if (isOneOf) {
                return;
            }
            throw new IllegalStateException(("only classes can have super classes, not " + this.kind).toString());
        }

        public final Builder superclass(Type superclass) {
            Intrinsics.checkParameterIsNotNull(superclass, "superclass");
            return superclass(TypeNames.get(superclass));
        }

        public final Builder superclass(KClass<?> superclass) {
            Intrinsics.checkParameterIsNotNull(superclass, "superclass");
            return superclass(TypeNames.get(superclass));
        }

        public final Builder addSuperclassConstructorParameter(String format, Object... args) {
            Intrinsics.checkParameterIsNotNull(format, "format");
            Intrinsics.checkParameterIsNotNull(args, "args");
            addSuperclassConstructorParameter(CodeBlock.Companion.of(format, Arrays.copyOf(args, args.length)));
            return this;
        }

        public final Builder addSuperclassConstructorParameter(CodeBlock codeBlock) {
            Intrinsics.checkParameterIsNotNull(codeBlock, "codeBlock");
            ensureCanHaveSuperclass();
            this.superclassConstructorParameters.add(codeBlock);
            return this;
        }

        public final Builder addSuperinterfaces(Iterable<? extends TypeName> superinterfaces) {
            Intrinsics.checkParameterIsNotNull(superinterfaces, "superinterfaces");
            CollectionsKt.addAll(this.superinterfaces, superinterfaces);
            return this;
        }

        public final Builder addSuperinterface(TypeName superinterface) {
            Intrinsics.checkParameterIsNotNull(superinterface, "superinterface");
            this.superinterfaces.add(superinterface);
            return this;
        }

        public final Builder addSuperinterface(Type superinterface) {
            Intrinsics.checkParameterIsNotNull(superinterface, "superinterface");
            return addSuperinterface(TypeNames.get(superinterface));
        }

        public final Builder addSuperinterface(KClass<?> superinterface) {
            Intrinsics.checkParameterIsNotNull(superinterface, "superinterface");
            return addSuperinterface(TypeNames.get(superinterface));
        }

        public static /* bridge */ /* synthetic */ Builder addEnumConstant$default(Builder builder, String str, TypeSpec typeSpec, int i, Object obj) {
            if ((i & 2) != 0) {
                typeSpec = TypeSpec.Companion.anonymousClassBuilder("", new Object[0]).build();
            }
            return builder.addEnumConstant(str, typeSpec);
        }

        public final Builder addEnumConstant(String name, TypeSpec typeSpec) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(typeSpec, "typeSpec");
            if (!Intrinsics.areEqual(this.kind, Kind.ENUM)) {
                throw new IllegalStateException(("" + this.name + " is not enum").toString());
            }
            if (!(typeSpec.getAnonymousTypeArguments() != null)) {
                throw new IllegalArgumentException("enum constants must have anonymous type arguments".toString());
            }
            if (!UtilKt.isName(name)) {
                throw new IllegalArgumentException(("not a valid enum constant: " + name).toString());
            }
            this.enumConstants.put(name, typeSpec);
            return this;
        }

        public final Builder addProperties(Iterable<PropertySpec> propertySpecs) {
            Intrinsics.checkParameterIsNotNull(propertySpecs, "propertySpecs");
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(propertySpecs, 10));
            for (PropertySpec propertySpec : propertySpecs) {
                arrayList.add(addProperty(propertySpec));
            }
            return this;
        }

        public final Builder addProperty(PropertySpec propertySpec) {
            Intrinsics.checkParameterIsNotNull(propertySpec, "propertySpec");
            if (Intrinsics.areEqual(this.kind, Kind.EXPECT_CLASS)) {
                boolean z = true;
                if (!(propertySpec.getInitializer() == null)) {
                    throw new IllegalArgumentException("properties in expect classes can't have initializers".toString());
                }
                if (!((propertySpec.getGetter() == null && propertySpec.getSetter() == null) ? false : false)) {
                    throw new IllegalArgumentException("properties in expect classes can't have getters and setters".toString());
                }
            }
            this.propertySpecs.add(propertySpec);
            return this;
        }

        public final Builder addProperty(String name, TypeName type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addProperty(PropertySpec.Companion.builder(name, type, (KModifier[]) Arrays.copyOf(modifiers, modifiers.length)).build());
        }

        public final Builder addProperty(String name, Type type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addProperty(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        public final Builder addProperty(String name, KClass<?> type, KModifier... modifiers) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(modifiers, "modifiers");
            return addProperty(name, TypeNames.get(type), (KModifier[]) Arrays.copyOf(modifiers, modifiers.length));
        }

        public final Builder addInitializerBlock(CodeBlock block) {
            boolean isOneOf;
            Intrinsics.checkParameterIsNotNull(block, "block");
            isOneOf = UtilKt.isOneOf(this.kind, Kind.CLASS, Kind.OBJECT, (r16 & 4) != 0 ? null : Kind.ENUM, (r16 & 8) != 0 ? null : null, (r16 & 16) != 0 ? null : null, (r16 & 32) != 0 ? null : null);
            if (!isOneOf) {
                throw new IllegalStateException(("" + this.kind + " can't have initializer blocks").toString());
            }
            this.initializerBlock.add("init {\n", new Object[0]).indent().add(block).unindent().add("}\n", new Object[0]);
            return this;
        }

        public final Builder addFunctions(Iterable<FunSpec> funSpecs) {
            Intrinsics.checkParameterIsNotNull(funSpecs, "funSpecs");
            for (FunSpec funSpec : funSpecs) {
                addFunction(funSpec);
            }
            return this;
        }

        public final Builder addFunction(FunSpec funSpec) {
            Intrinsics.checkParameterIsNotNull(funSpec, "funSpec");
            int i = WhenMappings.$EnumSwitchMapping$0[this.kind.ordinal()];
            if (i == 1) {
                UtilKt.requireNoneOf(funSpec.getModifiers(), KModifier.INTERNAL, KModifier.PROTECTED);
                UtilKt.requireNoneOrOneOf(funSpec.getModifiers(), KModifier.ABSTRACT, KModifier.PRIVATE);
            } else if (i == 2) {
                if (!Intrinsics.areEqual(funSpec.getModifiers(), this.kind.getImplicitFunctionModifiers$kotlinpoet())) {
                    throw new IllegalArgumentException(("" + this.kind + ' ' + this.name + '.' + funSpec.getName() + " requires modifiers " + this.kind.getImplicitFunctionModifiers$kotlinpoet()).toString());
                }
            } else if (i == 3 && !funSpec.getBody().isEmpty()) {
                throw new IllegalArgumentException("functions in expect classes can't have bodies".toString());
            }
            this.funSpecs.add(funSpec);
            return this;
        }

        public final Builder addTypes(Iterable<TypeSpec> typeSpecs) {
            Intrinsics.checkParameterIsNotNull(typeSpecs, "typeSpecs");
            CollectionsKt.addAll(this.typeSpecs, typeSpecs);
            return this;
        }

        public final Builder addType(TypeSpec typeSpec) {
            Intrinsics.checkParameterIsNotNull(typeSpec, "typeSpec");
            this.typeSpecs.add(typeSpec);
            return this;
        }

        public final TypeSpec build() {
            boolean z;
            boolean z2;
            boolean z3 = true;
            if (!((Intrinsics.areEqual(this.kind, Kind.ENUM) ^ true) || (this.enumConstants.isEmpty() ^ true))) {
                throw new IllegalArgumentException(("at least one enum constant is required for " + this.name).toString());
            }
            boolean z4 = this.modifiers.contains(KModifier.ABSTRACT) || (Intrinsics.areEqual(this.kind, Kind.CLASS) ^ true);
            for (FunSpec funSpec : this.funSpecs) {
                if (z4 || !funSpec.getModifiers().contains(KModifier.ABSTRACT)) {
                    z2 = true;
                    continue;
                } else {
                    z2 = false;
                    continue;
                }
                if (!z2) {
                    throw new IllegalArgumentException(("non-abstract type " + this.name + " cannot declare abstract function " + funSpec.getName()).toString());
                }
            }
            if (!(this.anonymousTypeArguments == null || (!Intrinsics.areEqual(this.superclass, TypeNames.ANY) ? 1 : 0) + this.superinterfaces.size() <= 1)) {
                throw new IllegalArgumentException("anonymous type has too many supertypes".toString());
            }
            if (this.primaryConstructor == null) {
                List<FunSpec> list = this.funSpecs;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    for (FunSpec funSpec2 : list) {
                        if (funSpec2.isConstructor()) {
                            z = false;
                            break;
                        }
                    }
                }
                z = true;
                if (!z && !this.superclassConstructorParameters.isEmpty()) {
                    z3 = false;
                }
                if (!z3) {
                    throw new IllegalArgumentException("types without a primary constructor cannot specify secondary constructors and superclass constructor parameters".toString());
                }
            }
            return new TypeSpec(this, null);
        }
    }

    /* compiled from: TypeSpec.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J)\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\b2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\f\"\u00020\u0001H\u0007¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0014\u0010\u000f\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\u0014"}, d2 = {"Lcom/squareup/kotlinpoet/TypeSpec$Companion;", "", "()V", "annotationBuilder", "Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "className", "Lcom/squareup/kotlinpoet/ClassName;", "name", "", "anonymousClassBuilder", "typeArgumentsFormat", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "classBuilder", "companionObjectBuilder", "enumBuilder", "expectClassBuilder", "interfaceBuilder", "objectBuilder", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
    /* loaded from: classes2.dex */
    public static final class Companion {
        @JvmStatic
        public final Builder companionObjectBuilder() {
            return companionObjectBuilder$default(this, null, 1, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final Builder classBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(Kind.CLASS, name, null);
        }

        @JvmStatic
        public final Builder classBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return classBuilder(className.simpleName());
        }

        @JvmStatic
        public final Builder expectClassBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Builder builder = new Builder(Kind.EXPECT_CLASS, name, null);
            builder.addModifiers(KModifier.EXPECT);
            return builder;
        }

        @JvmStatic
        public final Builder expectClassBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return expectClassBuilder(className.simpleName());
        }

        @JvmStatic
        public final Builder objectBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(Kind.OBJECT, name, null);
        }

        @JvmStatic
        public final Builder objectBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return objectBuilder(className.simpleName());
        }

        @JvmStatic
        public static /* bridge */ /* synthetic */ Builder companionObjectBuilder$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.companionObjectBuilder(str);
        }

        @JvmStatic
        public final Builder companionObjectBuilder(String str) {
            return new Builder(Kind.COMPANION, str, null);
        }

        @JvmStatic
        public final Builder interfaceBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(Kind.INTERFACE, name, null);
        }

        @JvmStatic
        public final Builder interfaceBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return interfaceBuilder(className.simpleName());
        }

        @JvmStatic
        public final Builder enumBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(Kind.ENUM, name, null);
        }

        @JvmStatic
        public final Builder enumBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return enumBuilder(className.simpleName());
        }

        @JvmStatic
        public final Builder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
            Intrinsics.checkParameterIsNotNull(typeArgumentsFormat, "typeArgumentsFormat");
            Intrinsics.checkParameterIsNotNull(args, "args");
            return new Builder(Kind.CLASS, null, CodeBlock.Companion.builder().add(typeArgumentsFormat, Arrays.copyOf(args, args.length)).build());
        }

        @JvmStatic
        public final Builder annotationBuilder(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Builder(Kind.ANNOTATION, name, null);
        }

        @JvmStatic
        public final Builder annotationBuilder(ClassName className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            return annotationBuilder(className.simpleName());
        }
    }
}
