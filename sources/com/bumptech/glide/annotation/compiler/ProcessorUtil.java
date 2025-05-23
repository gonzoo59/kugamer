package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.repackaged.com.google.common.base.Function;
import com.bumptech.glide.repackaged.com.google.common.base.Joiner;
import com.bumptech.glide.repackaged.com.google.common.base.Predicate;
import com.bumptech.glide.repackaged.com.google.common.collect.FluentIterable;
import com.bumptech.glide.repackaged.com.google.common.collect.ImmutableList;
import com.bumptech.glide.repackaged.com.google.common.collect.Lists;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import com.bumptech.glide.repackaged.com.squareup.javapoet.JavaFile;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeVariableName;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Type;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import okhttp3.HttpUrl;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ProcessorUtil {
    private final TypeElement appGlideModuleType;
    private final TypeElement libraryGlideModuleType;
    private final ProcessingEnvironment processingEnv;
    private int round;
    private static final String COMPILER_PACKAGE_NAME = GlideAnnotationProcessor.class.getPackage().getName();
    private static final ClassName SUPPORT_NONNULL_ANNOTATION = ClassName.get("android.support.annotation", "NonNull", new String[0]);
    private static final ClassName JETBRAINS_NOTNULL_ANNOTATION = ClassName.get("org.jetbrains.annotations", "NotNull", new String[0]);
    private static final ClassName ANDROIDX_NONNULL_ANNOTATION = ClassName.get("androidx.annotation", "NonNull", new String[0]);
    private static final ClassName SUPPORT_CHECK_RESULT_ANNOTATION = ClassName.get("android.support.annotation", "CheckResult", new String[0]);
    private static final ClassName ANDROIDX_CHECK_RESULT_ANNOTATION = ClassName.get("androidx.annotation", "CheckResult", new String[0]);
    private static final ClassName SUPPORT_VISIBLE_FOR_TESTING = ClassName.get("android.support.annotation", "VisibleForTesting", new String[0]);
    private static final ClassName ANDROIDX_VISIBLE_FOR_TESTING = ClassName.get("androidx.annotation", "VisibleForTesting", new String[0]);

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum MethodType {
        STATIC,
        INSTANCE
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void debugLog(String str) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ProcessorUtil(ProcessingEnvironment processingEnvironment) {
        this.processingEnv = processingEnvironment;
        this.appGlideModuleType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.module.AppGlideModule");
        this.libraryGlideModuleType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.module.LibraryGlideModule");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void process() {
        this.round++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAppGlideModule(TypeElement typeElement) {
        return this.processingEnv.getTypeUtils().isAssignable(typeElement.asType(), this.appGlideModuleType.asType());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isLibraryGlideModule(TypeElement typeElement) {
        return this.processingEnv.getTypeUtils().isAssignable(typeElement.asType(), this.libraryGlideModuleType.asType());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isExtension(TypeElement typeElement) {
        return typeElement.getAnnotation(GlideExtension.class) != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getOverrideType(ExecutableElement executableElement) {
        return ((GlideOption) executableElement.getAnnotation(GlideOption.class)).override();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeIndexer(TypeSpec typeSpec) {
        writeClass(COMPILER_PACKAGE_NAME, typeSpec);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeClass(String str, TypeSpec typeSpec) {
        try {
            debugLog("Writing class:\n" + typeSpec);
            JavaFile.builder(str, typeSpec).skipJavaLangImports(true).build().writeTo(this.processingEnv.getFiler());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> findAnnotatedElementsInClasses(Set<String> set, Class<? extends Annotation> cls) {
        ArrayList arrayList = new ArrayList();
        for (String str : set) {
            for (ExecutableElement executableElement : this.processingEnv.getElementUtils().getTypeElement(str).getEnclosedElements()) {
                if (executableElement.getAnnotation(cls) != null) {
                    arrayList.add(executableElement);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<TypeElement> getElementsFor(Class<? extends Annotation> cls, RoundEnvironment roundEnvironment) {
        return ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(cls));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeBlock generateSeeMethodJavadoc(ExecutableElement executableElement) {
        return generateSeeMethodJavadoc(getJavadocSafeName(executableElement.getEnclosingElement()), executableElement.getSimpleName().toString(), executableElement.getParameters());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeBlock generateSeeMethodJavadoc(TypeName typeName, String str, List<? extends VariableElement> list) {
        return generateSeeMethodJavadocInternal(typeName, str, Lists.transform(list, new Function<VariableElement, Object>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public Object apply(VariableElement variableElement) {
                return ProcessorUtil.this.getJavadocSafeName(variableElement);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeBlock generateSeeMethodJavadoc(TypeName typeName, MethodSpec methodSpec) {
        return generateSeeMethodJavadocInternal(typeName, methodSpec.name, Lists.transform(methodSpec.parameters, new Function<ParameterSpec, Object>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.2
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public Object apply(ParameterSpec parameterSpec) {
                return parameterSpec.type;
            }
        }));
    }

    private CodeBlock generateSeeMethodJavadocInternal(TypeName typeName, String str, List<Object> list) {
        StringBuilder sb = new StringBuilder("@see $T#$L(");
        ArrayList arrayList = new ArrayList();
        arrayList.add(typeName);
        arrayList.add(str);
        for (Object obj : list) {
            sb.append("$T, ");
            arrayList.add(obj);
        }
        if (arrayList.size() > 2) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        sb.append(")\n");
        return CodeBlock.of(sb.toString(), arrayList.toArray(new Object[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TypeName getJavadocSafeName(Element element) {
        Types typeUtils = this.processingEnv.getTypeUtils();
        TypeMirror asType = element.asType();
        if (typeUtils.asElement(asType) == null) {
            return ClassName.get(element.asType());
        }
        return ClassName.bestGuess(typeUtils.asElement(asType).getSimpleName().toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void infoLog(String str) {
        Messager messager = this.processingEnv.getMessager();
        Diagnostic.Kind kind = Diagnostic.Kind.NOTE;
        messager.printMessage(kind, "[" + this.round + "] " + str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CodeBlock generateCastingSuperCall(TypeName typeName, MethodSpec methodSpec) {
        return CodeBlock.builder().add("return ($T) super.$N(", typeName, methodSpec.name).add(FluentIterable.from(methodSpec.parameters).transform(new Function<ParameterSpec, String>() { // from class: com.bumptech.glide.annotation.compiler.ProcessorUtil.3
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public String apply(ParameterSpec parameterSpec) {
                return parameterSpec.name;
            }
        }).join(Joiner.on(",")), new Object[0]).add(");\n", new Object[0]).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MethodSpec.Builder overriding(ExecutableElement executableElement) {
        Modifier modifier;
        MethodSpec.Builder addAnnotation = MethodSpec.methodBuilder(executableElement.getSimpleName().toString()).addAnnotation(Override.class);
        LinkedHashSet linkedHashSet = new LinkedHashSet(executableElement.getModifiers());
        linkedHashSet.remove(Modifier.ABSTRACT);
        try {
            modifier = Modifier.valueOf("DEFAULT");
        } catch (IllegalArgumentException unused) {
            modifier = null;
        }
        linkedHashSet.remove(modifier);
        MethodSpec.Builder addModifiers = addAnnotation.addModifiers(linkedHashSet);
        for (TypeParameterElement typeParameterElement : executableElement.getTypeParameters()) {
            addModifiers = addModifiers.addTypeVariable(TypeVariableName.get(typeParameterElement.asType()));
        }
        MethodSpec.Builder varargs = addModifiers.returns(TypeName.get(executableElement.getReturnType())).addParameters(getParameters(executableElement)).varargs(executableElement.isVarArgs());
        for (TypeMirror typeMirror : executableElement.getThrownTypes()) {
            varargs = varargs.addException(TypeName.get(typeMirror));
        }
        return varargs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ParameterSpec> getParameters(ExecutableElement executableElement) {
        return getParameters(executableElement.getParameters());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ParameterSpec> getParameters(List<? extends VariableElement> list) {
        ArrayList arrayList = new ArrayList();
        for (VariableElement variableElement : list) {
            arrayList.add(getParameter(variableElement));
        }
        return dedupedParameters(arrayList);
    }

    private static List<ParameterSpec> dedupedParameters(List<ParameterSpec> list) {
        HashSet hashSet = new HashSet();
        boolean z = false;
        for (ParameterSpec parameterSpec : list) {
            String str = parameterSpec.name;
            if (hashSet.contains(str)) {
                z = true;
            } else {
                hashSet.add(str);
            }
        }
        if (z) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                ParameterSpec parameterSpec2 = list.get(i);
                TypeName typeName = parameterSpec2.type;
                arrayList.add(ParameterSpec.builder(typeName, parameterSpec2.name + i, new Modifier[0]).addModifiers(parameterSpec2.modifiers).addAnnotations(parameterSpec2.annotations).build());
            }
            return arrayList;
        }
        return list;
    }

    private static ParameterSpec getParameter(VariableElement variableElement) {
        TypeName typeName = TypeName.get(variableElement.asType());
        return ParameterSpec.builder(typeName, computeParameterName(variableElement, typeName), new Modifier[0]).addModifiers(variableElement.getModifiers()).addAnnotations(getAnnotations(variableElement)).build();
    }

    private static String computeParameterName(VariableElement variableElement, TypeName typeName) {
        boolean z;
        String typeName2 = typeName.withoutAnnotations().toString();
        if (typeName.isPrimitive() || typeName.isBoxedPrimitive()) {
            return getSmartPrimitiveParameterName(variableElement);
        }
        if (typeName2.contains("<") && typeName2.contains(">")) {
            String str = typeName2.split("<")[0];
            String[] split = typeName2.split(">");
            typeName2 = split.length > 1 ? str + split[split.length - 1] : str;
        }
        String[] split2 = typeName2.split("\\.");
        String applySmartParameterNameReplacements = applySmartParameterNameReplacements(split2[split2.length - 1]);
        char[] charArray = applySmartParameterNameReplacements.toCharArray();
        int length = charArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            } else if (Character.isLowerCase(charArray[i])) {
                z = false;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return applySmartParameterNameReplacements.toLowerCase();
        }
        char[] charArray2 = applySmartParameterNameReplacements.toCharArray();
        int length2 = charArray2.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length2; i3++) {
            if (Character.isUpperCase(charArray2[i3])) {
                i2 = i3;
            }
        }
        String substring = applySmartParameterNameReplacements.substring(i2, applySmartParameterNameReplacements.length());
        return Character.toLowerCase(substring.charAt(0)) + substring.substring(1, substring.length());
    }

    private static String getSmartPrimitiveParameterName(VariableElement variableElement) {
        for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
            String upperCase = annotationMirror.getAnnotationType().toString().toUpperCase();
            if (upperCase.endsWith("RES")) {
                return "id";
            }
            if (upperCase.endsWith("RANGE")) {
                return "value";
            }
        }
        return variableElement.getSimpleName().toString();
    }

    private static String applySmartParameterNameReplacements(String str) {
        return str.replace(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, "s").replace("Class", "clazz").replace("Object", "o");
    }

    private static List<AnnotationSpec> getAnnotations(VariableElement variableElement) {
        ArrayList arrayList = new ArrayList();
        for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
            arrayList.add(AnnotationSpec.get(annotationMirror));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassName visibleForTesting() {
        return findAnnotationClassName(ANDROIDX_VISIBLE_FOR_TESTING, SUPPORT_VISIBLE_FOR_TESTING);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassName nonNull() {
        return findAnnotationClassName(ANDROIDX_NONNULL_ANNOTATION, SUPPORT_NONNULL_ANNOTATION);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ClassName checkResult() {
        return findAnnotationClassName(ANDROIDX_CHECK_RESULT_ANNOTATION, SUPPORT_CHECK_RESULT_ANNOTATION);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ClassName> nonNulls() {
        return ImmutableList.of(SUPPORT_NONNULL_ANNOTATION, JETBRAINS_NOTNULL_ANNOTATION, ANDROIDX_NONNULL_ANNOTATION);
    }

    private ClassName findAnnotationClassName(ClassName className, ClassName className2) {
        return this.processingEnv.getElementUtils().getTypeElement(className.reflectionName()) != null ? className : className2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> findInstanceMethodsReturning(TypeElement typeElement, TypeMirror typeMirror) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(typeMirror, MethodType.INSTANCE)).transform(new ToMethod()).toList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> findInstanceMethodsReturning(TypeElement typeElement, TypeElement typeElement2) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(this, typeElement2, MethodType.INSTANCE)).transform(new ToMethod()).toList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> findStaticMethodsReturning(TypeElement typeElement, TypeElement typeElement2) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods(this, typeElement2, MethodType.STATIC)).transform(new ToMethod()).toList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> findStaticMethods(TypeElement typeElement) {
        return FluentIterable.from(typeElement.getEnclosedElements()).filter(new FilterPublicMethods((TypeMirror) null, MethodType.STATIC)).transform(new ToMethod()).toList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Set<String> findClassValuesFromAnnotationOnClassAsNames(Element element, Class<? extends Annotation> cls) {
        String name = cls.getName();
        AnnotationValue annotationValue = null;
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (name.equals(annotationMirror.getAnnotationType().toString())) {
                Set entrySet = annotationMirror.getElementValues().entrySet();
                if (entrySet.size() != 1) {
                    throw new IllegalArgumentException("Expected single value, but found: " + entrySet);
                }
                annotationValue = (AnnotationValue) ((Map.Entry) entrySet.iterator().next()).getValue();
                if (annotationValue == null || (annotationValue instanceof Attribute.UnresolvedClass)) {
                    throw new IllegalArgumentException("Failed to find value for: " + cls + " from mirrors: " + element.getAnnotationMirrors());
                }
            }
        }
        if (annotationValue == null) {
            return Collections.emptySet();
        }
        Object value = annotationValue.getValue();
        if (value instanceof List) {
            List<Object> list = (List) value;
            HashSet hashSet = new HashSet(list.size());
            for (Object obj : list) {
                hashSet.add(getExcludedModuleClassFromAnnotationAttribute(element, obj));
            }
            return hashSet;
        }
        return Collections.singleton(((Type.ClassType) value).toString());
    }

    private static String getExcludedModuleClassFromAnnotationAttribute(Element element, Object obj) {
        if (obj.getClass().getSimpleName().equals("UnresolvedClass")) {
            throw new IllegalArgumentException("Failed to parse @Excludes for: " + element + ", one or more excluded Modules could not be found at compile time. Ensure that allexcluded Modules are included in your classpath.");
        }
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();
        if (declaredMethods == null || declaredMethods.length == 0) {
            throw new IllegalArgumentException("Failed to parse @Excludes for: " + element + ", invalid exclude: " + obj);
        }
        for (Method method : declaredMethods) {
            if (method.getName().equals("getValue")) {
                try {
                    return method.invoke(obj, new Object[0]).toString();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Failed to parse @Excludes for: " + element, e);
                }
            }
        }
        throw new IllegalArgumentException("Failed to parse @Excludes for: " + element);
    }

    /* loaded from: classes.dex */
    private final class FilterPublicMethods implements Predicate<Element> {
        private final MethodType methodType;
        private final TypeMirror returnType;

        FilterPublicMethods(TypeMirror typeMirror, MethodType methodType) {
            this.returnType = typeMirror;
            this.methodType = methodType;
        }

        FilterPublicMethods(ProcessorUtil processorUtil, TypeElement typeElement, MethodType methodType) {
            this(typeElement != null ? typeElement.asType() : null, methodType);
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
        public boolean apply(Element element) {
            if (element != null && element.getKind() == ElementKind.METHOD && element.getModifiers().contains(Modifier.PUBLIC)) {
                boolean contains = element.getModifiers().contains(Modifier.STATIC);
                if (this.methodType != MethodType.STATIC || contains) {
                    if (this.methodType == MethodType.INSTANCE && contains) {
                        return false;
                    }
                    ExecutableElement executableElement = (ExecutableElement) element;
                    TypeMirror typeMirror = this.returnType;
                    return typeMirror == null || ProcessorUtil.this.isReturnValueTypeMatching(executableElement, typeMirror);
                }
                return false;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isReturnValueTypeMatching(ExecutableElement executableElement, TypeElement typeElement) {
        return isReturnValueTypeMatching(executableElement, typeElement.asType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReturnValueTypeMatching(ExecutableElement executableElement, TypeMirror typeMirror) {
        return this.processingEnv.getTypeUtils().isAssignable(executableElement.getReturnType(), typeMirror);
    }

    /* loaded from: classes.dex */
    private static final class ToMethod implements Function<Element, ExecutableElement> {
        private ToMethod() {
        }

        @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
        public ExecutableElement apply(Element element) {
            return (ExecutableElement) element;
        }
    }
}
