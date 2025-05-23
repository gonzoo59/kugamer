package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterizedTypeName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.WildcardTypeName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AppModuleGenerator {
    private final ProcessingEnvironment processingEnv;
    private final ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppModuleGenerator(ProcessingEnvironment processingEnvironment, ProcessorUtil processorUtil) {
        this.processingEnv = processingEnvironment;
        this.processorUtil = processorUtil;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypeSpec generate(TypeElement typeElement, Set<String> set) {
        ClassName className = ClassName.get(typeElement);
        List<String> excludedGlideModuleClassNames = getExcludedGlideModuleClassNames(typeElement);
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList);
        MethodSpec generateConstructor = generateConstructor(className, arrayList, excludedGlideModuleClassNames);
        MethodSpec generateRegisterComponents = generateRegisterComponents(arrayList, excludedGlideModuleClassNames);
        TypeSpec.Builder addMethod = TypeSpec.classBuilder("GeneratedAppGlideModuleImpl").addModifiers(Modifier.FINAL).addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "deprecation").build()).superclass(ClassName.get("com.bumptech.glide", "GeneratedAppGlideModule", new String[0])).addField(className, "appGlideModule", Modifier.PRIVATE, Modifier.FINAL).addMethod(generateConstructor).addMethod(MethodSpec.methodBuilder("applyOptions").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).addParameter(ParameterSpec.builder(ClassName.get("android.content", "Context", new String[0]), "context", new Modifier[0]).addAnnotation(this.processorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(ClassName.get("com.bumptech.glide", "GlideBuilder", new String[0]), "builder", new Modifier[0]).addAnnotation(this.processorUtil.nonNull()).build()).addStatement("appGlideModule.applyOptions(context, builder)", typeElement).build()).addMethod(generateRegisterComponents).addMethod(MethodSpec.methodBuilder("isManifestParsingEnabled").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).returns(Boolean.TYPE).addStatement("return appGlideModule.isManifestParsingEnabled()", typeElement).build()).addMethod(generateGetExcludedModuleClasses(excludedGlideModuleClassNames));
        ClassName className2 = ClassName.get("com.bumptech.glide", "GeneratedRequestManagerFactory", new String[0]);
        addMethod.addMethod(MethodSpec.methodBuilder("getRequestManagerFactory").addAnnotation(Override.class).addAnnotation(this.processorUtil.nonNull()).returns(className2).addStatement("return new $T()", className2).build());
        return addMethod.build();
    }

    private MethodSpec generateGetExcludedModuleClasses(Collection<String> collection) {
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get((Class<?>) Class.class), WildcardTypeName.subtypeOf(Object.class));
        ParameterizedTypeName parameterizedTypeName2 = ParameterizedTypeName.get(ClassName.get((Class<?>) Set.class), parameterizedTypeName);
        ParameterizedTypeName parameterizedTypeName3 = ParameterizedTypeName.get(ClassName.get((Class<?>) HashSet.class), parameterizedTypeName);
        MethodSpec.Builder returns = MethodSpec.methodBuilder("getExcludedModuleClasses").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).addAnnotation(this.processorUtil.nonNull()).returns(parameterizedTypeName2);
        if (collection.isEmpty()) {
            returns.addStatement("return $T.emptySet()", Collections.class);
        } else {
            returns.addStatement("$T excludedClasses = new $T()", parameterizedTypeName2, parameterizedTypeName3);
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                returns.addStatement("excludedClasses.add($L.class)", it.next());
            }
            returns.addStatement("return excludedClasses", new Object[0]);
        }
        return returns.build();
    }

    private MethodSpec generateRegisterComponents(Collection<String> collection, Collection<String> collection2) {
        MethodSpec.Builder addParameter = MethodSpec.methodBuilder("registerComponents").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).addParameter(ParameterSpec.builder(ClassName.get("android.content", "Context", new String[0]), "context", new Modifier[0]).addAnnotation(this.processorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(ClassName.get("com.bumptech.glide", "Glide", new String[0]), "glide", new Modifier[0]).addAnnotation(this.processorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(ClassName.get("com.bumptech.glide", "Registry", new String[0]), "registry", new Modifier[0]).addAnnotation(this.processorUtil.nonNull()).build());
        for (String str : collection) {
            if (!collection2.contains(str)) {
                addParameter.addStatement("new $T().registerComponents(context, glide, registry)", ClassName.bestGuess(str));
            }
        }
        addParameter.addStatement("appGlideModule.registerComponents(context, glide, registry)", new Object[0]);
        return addParameter.build();
    }

    private boolean doesAppGlideModuleConstructorAcceptContext(ClassName className) {
        for (ExecutableElement executableElement : this.processingEnv.getElementUtils().getTypeElement(className.reflectionName()).getEnclosedElements()) {
            if (executableElement.getKind() == ElementKind.CONSTRUCTOR) {
                List parameters = executableElement.getParameters();
                if (parameters.isEmpty()) {
                    return false;
                }
                if (parameters.size() > 1) {
                    throw new IllegalStateException("Constructor for " + className + " accepts too many parameters, it should accept no parameters, or a single Context");
                }
                TypeMirror asType = ((VariableElement) parameters.get(0)).asType();
                if (this.processingEnv.getTypeUtils().isSameType(asType, this.processingEnv.getElementUtils().getTypeElement("android.content.Context").asType())) {
                    return true;
                }
                throw new IllegalStateException("Unrecognized type: " + asType);
            }
        }
        return false;
    }

    private MethodSpec generateConstructor(ClassName className, Collection<String> collection, Collection<String> collection2) {
        MethodSpec.Builder addParameter = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(ParameterSpec.builder(ClassName.get("android.content", "Context", new String[0]), "context", new Modifier[0]).build());
        if (doesAppGlideModuleConstructorAcceptContext(className)) {
            addParameter.addStatement("appGlideModule = new $T(context)", className);
        } else {
            addParameter.addStatement("appGlideModule = new $T()", className);
        }
        ClassName className2 = ClassName.get("android.util", "Log", new String[0]);
        addParameter.beginControlFlow("if ($T.isLoggable($S, $T.DEBUG))", className2, "Glide", className2);
        addParameter.addStatement("$T.d($S, $S)", className2, "Glide", "Discovered AppGlideModule from annotation: " + className);
        for (String str : collection) {
            if (collection2.contains(str)) {
                addParameter.addStatement("$T.d($S, $S)", className2, "Glide", "AppGlideModule excludes LibraryGlideModule from annotation: " + str);
            } else {
                addParameter.addStatement("$T.d($S, $S)", className2, "Glide", "Discovered LibraryGlideModule from annotation: " + str);
            }
        }
        addParameter.endControlFlow();
        return addParameter.build();
    }

    private List<String> getExcludedGlideModuleClassNames(TypeElement typeElement) {
        ArrayList arrayList = new ArrayList(this.processorUtil.findClassValuesFromAnnotationOnClassAsNames(typeElement, Excludes.class));
        Collections.sort(arrayList);
        return arrayList;
    }
}
