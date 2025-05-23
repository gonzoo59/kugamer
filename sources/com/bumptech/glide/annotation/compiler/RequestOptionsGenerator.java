package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.repackaged.com.google.common.base.Function;
import com.bumptech.glide.repackaged.com.google.common.base.Objects;
import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;
import com.bumptech.glide.repackaged.com.google.common.base.Predicate;
import com.bumptech.glide.repackaged.com.google.common.base.Strings;
import com.bumptech.glide.repackaged.com.google.common.collect.FluentIterable;
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList;
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableSet;
import com.bumptech.glide.repackaged.com.google.common.collect.Iterables;
import com.bumptech.glide.repackaged.com.google.common.collect.Lists;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import com.bumptech.glide.repackaged.com.squareup.javapoet.FieldSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RequestOptionsGenerator {
    private ClassName glideOptionsName;
    private int nextFieldId;
    private final ProcessorUtil processorUtil;
    private final ClassName requestOptionsName = ClassName.get("com.bumptech.glide.request", "RequestOptions", new String[0]);
    private final RequestOptionsOverrideGenerator requestOptionsOverrideGenerator;
    private final TypeElement requestOptionsType;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOptionsGenerator(ProcessingEnvironment processingEnvironment, ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
        this.requestOptionsType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.request.RequestOptions");
        this.requestOptionsOverrideGenerator = new RequestOptionsOverrideGenerator(processingEnvironment, processorUtil);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypeSpec generate(String str, Set<String> set) {
        ClassName className = ClassName.get(str, "GlideOptions", new String[0]);
        this.glideOptionsName = className;
        RequestOptionsExtensionGenerator requestOptionsExtensionGenerator = new RequestOptionsExtensionGenerator(className, this.processorUtil);
        ImmutableList list = FluentIterable.from(requestOptionsExtensionGenerator.generateInstanceMethodsForExtensions(set)).transform(new Function<MethodSpec, MethodAndStaticVar>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsGenerator.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public MethodAndStaticVar apply(MethodSpec methodSpec) {
                return new MethodAndStaticVar(methodSpec);
            }
        }).toList();
        ImmutableList list2 = FluentIterable.from(requestOptionsExtensionGenerator.getRequestOptionExtensionMethods(set)).filter(new Predicate<ExecutableElement>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsGenerator.3
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(ExecutableElement executableElement) {
                return !RequestOptionsGenerator.skipStaticMethod(executableElement);
            }
        }).transform(new Function<ExecutableElement, MethodAndStaticVar>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsGenerator.2
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public MethodAndStaticVar apply(ExecutableElement executableElement) {
                return RequestOptionsGenerator.this.generateStaticMethodEquivalentForExtensionMethod(executableElement);
            }
        }).toList();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        arrayList.addAll(list2);
        ImmutableSet copyOf = ImmutableSet.copyOf(Iterables.transform(arrayList, new Function<MethodAndStaticVar, MethodSignature>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsGenerator.4
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public MethodSignature apply(MethodAndStaticVar methodAndStaticVar) {
                return new MethodSignature(methodAndStaticVar.method);
            }
        }));
        List<MethodAndStaticVar> generateStaticMethodOverridesForRequestOptions = generateStaticMethodOverridesForRequestOptions();
        List<MethodSpec> generateInstanceMethodOverridesForRequestOptions = this.requestOptionsOverrideGenerator.generateInstanceMethodOverridesForRequestOptions(this.glideOptionsName);
        ArrayList<MethodAndStaticVar> arrayList2 = new ArrayList();
        for (MethodAndStaticVar methodAndStaticVar : generateStaticMethodOverridesForRequestOptions) {
            if (!copyOf.contains(new MethodSignature(methodAndStaticVar.method))) {
                arrayList2.add(methodAndStaticVar);
            }
        }
        for (MethodSpec methodSpec : generateInstanceMethodOverridesForRequestOptions) {
            if (!copyOf.contains(new MethodSignature(methodSpec))) {
                arrayList2.add(new MethodAndStaticVar(methodSpec));
            }
        }
        arrayList2.addAll(arrayList);
        TypeSpec.Builder superclass = TypeSpec.classBuilder("GlideOptions").addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "deprecation").build()).addJavadoc(generateClassJavadoc(set)).addModifiers(Modifier.FINAL).addModifiers(Modifier.PUBLIC).addSuperinterface(Cloneable.class).superclass(this.requestOptionsName);
        for (MethodAndStaticVar methodAndStaticVar2 : arrayList2) {
            if (methodAndStaticVar2.method != null) {
                superclass.addMethod(methodAndStaticVar2.method);
            }
            if (methodAndStaticVar2.staticField != null) {
                superclass.addField(methodAndStaticVar2.staticField);
            }
        }
        return superclass.build();
    }

    private CodeBlock generateClassJavadoc(Set<String> set) {
        CodeBlock.Builder add = CodeBlock.builder().add("Automatically generated from {@link $T} annotated classes.\n", GlideExtension.class).add("\n", new Object[0]).add("@see $T\n", this.requestOptionsName);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            add.add("@see $T\n", ClassName.bestGuess(it.next()));
        }
        return add.build();
    }

    private List<MethodAndStaticVar> generateStaticMethodOverridesForRequestOptions() {
        ProcessorUtil processorUtil = this.processorUtil;
        TypeElement typeElement = this.requestOptionsType;
        List<ExecutableElement> findStaticMethodsReturning = processorUtil.findStaticMethodsReturning(typeElement, typeElement);
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : findStaticMethodsReturning) {
            if (executableElement.getAnnotation(Deprecated.class) == null) {
                arrayList.add(generateStaticMethodEquivalentForRequestOptionsStaticMethod(executableElement));
            }
        }
        return arrayList;
    }

    private static String getInstanceMethodNameFromStaticMethodName(String str) {
        if ("bitmapTransform".equals(str)) {
            return "transform";
        }
        if ("decodeTypeOf".equals(str)) {
            return "decode";
        }
        if (str.endsWith("Transform")) {
            return str.substring(0, str.length() - 9);
        }
        if (str.endsWith("Of")) {
            return str.substring(0, str.length() - 2);
        }
        if ("noTransformation".equals(str)) {
            return "dontTransform";
        }
        if ("noAnimation".equals(str)) {
            return "dontAnimate";
        }
        if (str.equals("option")) {
            return "set";
        }
        throw new IllegalArgumentException("Unrecognized static method name: " + str);
    }

    private MethodAndStaticVar generateStaticMethodEquivalentForRequestOptionsStaticMethod(ExecutableElement executableElement) {
        FieldSpec fieldSpec;
        boolean memoizeStaticMethodFromArguments = memoizeStaticMethodFromArguments(executableElement);
        String obj = executableElement.getSimpleName().toString();
        Object instanceMethodNameFromStaticMethodName = getInstanceMethodNameFromStaticMethodName(obj);
        MethodSpec.Builder returns = MethodSpec.methodBuilder(obj).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addJavadoc(this.processorUtil.generateSeeMethodJavadoc(executableElement)).returns(this.glideOptionsName);
        StringBuilder createNewOptionAndCall = createNewOptionAndCall(memoizeStaticMethodFromArguments, returns, "new $T().$N(", ProcessorUtil.getParameters(executableElement));
        if (memoizeStaticMethodFromArguments) {
            StringBuilder sb = new StringBuilder();
            sb.append(obj);
            int i = this.nextFieldId;
            this.nextFieldId = i + 1;
            sb.append(i);
            String sb2 = sb.toString();
            fieldSpec = FieldSpec.builder(this.glideOptionsName, sb2, new Modifier[0]).addModifiers(Modifier.PRIVATE, Modifier.STATIC).build();
            Object obj2 = this.glideOptionsName;
            returns.beginControlFlow("if ($T.$N == null)", this.glideOptionsName, sb2).addStatement("$T.$N =\n" + ((Object) createNewOptionAndCall) + ".$N", obj2, sb2, obj2, instanceMethodNameFromStaticMethodName, "autoClone()").endControlFlow().addStatement("return $T.$N", this.glideOptionsName, sb2);
        } else {
            returns.addStatement("return " + ((Object) createNewOptionAndCall), this.glideOptionsName, instanceMethodNameFromStaticMethodName);
            fieldSpec = null;
        }
        for (TypeParameterElement typeParameterElement : executableElement.getTypeParameters()) {
            returns.addTypeVariable(TypeVariableName.get(typeParameterElement.getSimpleName().toString()));
        }
        returns.addAnnotation(this.processorUtil.checkResult()).addAnnotation(this.processorUtil.nonNull());
        return new MethodAndStaticVar(returns.build(), fieldSpec);
    }

    private static boolean memoizeStaticMethodFromArguments(ExecutableElement executableElement) {
        return executableElement.getParameters().isEmpty() || (executableElement.getParameters().size() == 1 && ((VariableElement) executableElement.getParameters().get(0)).getSimpleName().toString().equals("android.content.Context"));
    }

    private StringBuilder createNewOptionAndCall(boolean z, MethodSpec.Builder builder, String str, List<ParameterSpec> list) {
        StringBuilder sb = new StringBuilder(str);
        if (!list.isEmpty()) {
            builder.addParameters(list);
            for (ParameterSpec parameterSpec : list) {
                sb.append(parameterSpec.name);
                if (z && isAndroidContext(parameterSpec)) {
                    sb.append(".getApplicationContext()");
                }
                sb.append(", ");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        sb.append(")");
        return sb;
    }

    private boolean isAndroidContext(ParameterSpec parameterSpec) {
        return parameterSpec.type.toString().equals("android.content.Context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MethodAndStaticVar generateStaticMethodEquivalentForExtensionMethod(ExecutableElement executableElement) {
        String staticMethodName = getStaticMethodName(executableElement);
        String obj = executableElement.getSimpleName().toString();
        if (Strings.isNullOrEmpty(staticMethodName)) {
            staticMethodName = obj.startsWith("dont") ? "no" + obj.replace("dont", "") : obj + "Of";
        }
        boolean memoizeStaticMethodFromAnnotation = memoizeStaticMethodFromAnnotation(executableElement);
        Preconditions.checkNotNull(staticMethodName);
        MethodSpec.Builder returns = MethodSpec.methodBuilder(staticMethodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addJavadoc(this.processorUtil.generateSeeMethodJavadoc(executableElement)).varargs(executableElement.isVarArgs()).returns(this.glideOptionsName);
        List parameters = executableElement.getParameters();
        if (parameters.isEmpty()) {
            throw new IllegalArgumentException("Expected non-empty parameters for: " + executableElement);
        }
        StringBuilder createNewOptionAndCall = createNewOptionAndCall(memoizeStaticMethodFromAnnotation, returns, "new $T().$L(", ProcessorUtil.getParameters(parameters.subList(1, parameters.size())));
        FieldSpec fieldSpec = null;
        if (memoizeStaticMethodFromAnnotation) {
            StringBuilder sb = new StringBuilder();
            sb.append(staticMethodName);
            int i = this.nextFieldId;
            this.nextFieldId = i + 1;
            sb.append(i);
            String sb2 = sb.toString();
            fieldSpec = FieldSpec.builder(this.glideOptionsName, sb2, new Modifier[0]).addModifiers(Modifier.PRIVATE, Modifier.STATIC).build();
            Object obj2 = this.glideOptionsName;
            returns.beginControlFlow("if ($T.$N == null)", this.glideOptionsName, sb2).addStatement("$T.$N =\n" + ((Object) createNewOptionAndCall) + ".$N", obj2, sb2, obj2, obj, "autoClone()").endControlFlow().addStatement("return $T.$N", this.glideOptionsName, sb2);
        } else {
            returns.addStatement("return " + ((Object) createNewOptionAndCall), this.glideOptionsName, obj);
        }
        for (TypeParameterElement typeParameterElement : executableElement.getTypeParameters()) {
            returns.addTypeVariable(TypeVariableName.get(typeParameterElement.getSimpleName().toString()));
        }
        returns.addAnnotation(this.processorUtil.checkResult());
        return new MethodAndStaticVar(returns.build(), fieldSpec);
    }

    private static String getStaticMethodName(ExecutableElement executableElement) {
        GlideOption glideOption = (GlideOption) executableElement.getAnnotation(GlideOption.class);
        return Strings.emptyToNull(glideOption != null ? glideOption.staticMethodName() : null);
    }

    private static boolean memoizeStaticMethodFromAnnotation(ExecutableElement executableElement) {
        GlideOption glideOption = (GlideOption) executableElement.getAnnotation(GlideOption.class);
        return glideOption != null && glideOption.memoizeStaticMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean skipStaticMethod(ExecutableElement executableElement) {
        GlideOption glideOption = (GlideOption) executableElement.getAnnotation(GlideOption.class);
        return glideOption != null && glideOption.skipStaticMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class MethodAndStaticVar {
        final MethodSpec method;
        final FieldSpec staticField;

        MethodAndStaticVar(MethodSpec methodSpec) {
            this(methodSpec, null);
        }

        MethodAndStaticVar(MethodSpec methodSpec, FieldSpec fieldSpec) {
            this.method = methodSpec;
            this.staticField = fieldSpec;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class MethodSignature {
        private final boolean isStatic;
        private final String name;
        private final List<TypeName> parameterTypes;
        private final TypeName returnType;

        MethodSignature(MethodSpec methodSpec) {
            this.name = methodSpec.name;
            this.isStatic = methodSpec.modifiers.contains(Modifier.STATIC);
            this.returnType = methodSpec.returnType;
            this.parameterTypes = Lists.transform(methodSpec.parameters, new Function<ParameterSpec, TypeName>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsGenerator.MethodSignature.1
                @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
                public TypeName apply(ParameterSpec parameterSpec) {
                    return parameterSpec.type;
                }
            });
        }

        public boolean equals(Object obj) {
            if (obj instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature) obj;
                return this.name.equals(methodSignature.name) && this.returnType.equals(methodSignature.returnType) && this.parameterTypes.equals(methodSignature.parameterTypes) && this.isStatic == methodSignature.isStatic;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.name, this.returnType, this.parameterTypes, Boolean.valueOf(this.isStatic));
        }
    }
}
