package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.repackaged.com.google.common.base.Function;
import com.bumptech.glide.repackaged.com.google.common.base.Joiner;
import com.bumptech.glide.repackaged.com.google.common.base.Predicate;
import com.bumptech.glide.repackaged.com.google.common.collect.FluentIterable;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RequestOptionsOverrideGenerator {
    private final TypeElement baseRequestOptionsType;
    private ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOptionsOverrideGenerator(ProcessingEnvironment processingEnvironment, ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
        this.baseRequestOptionsType = processingEnvironment.getElementUtils().getTypeElement("com.bumptech.glide.request.BaseRequestOptions");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<MethodSpec> generateInstanceMethodOverridesForRequestOptions(TypeName typeName) {
        return generateInstanceMethodOverridesForRequestOptions(typeName, Collections.emptySet());
    }

    List<MethodSpec> generateInstanceMethodOverridesForRequestOptions(final TypeName typeName, final Set<String> set) {
        ProcessorUtil processorUtil = this.processorUtil;
        TypeElement typeElement = this.baseRequestOptionsType;
        return FluentIterable.from(processorUtil.findInstanceMethodsReturning(typeElement, typeElement)).filter(new Predicate<ExecutableElement>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsOverrideGenerator.2
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Predicate
            public boolean apply(ExecutableElement executableElement) {
                return !set.contains(executableElement.getSimpleName().toString());
            }
        }).transform(new Function<ExecutableElement, MethodSpec>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsOverrideGenerator.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public MethodSpec apply(ExecutableElement executableElement) {
                return RequestOptionsOverrideGenerator.this.generateRequestOptionOverride(typeName, executableElement);
            }
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MethodSpec generateRequestOptionOverride(TypeName typeName, ExecutableElement executableElement) {
        MethodSpec.Builder returns = ProcessorUtil.overriding(executableElement).returns(typeName);
        returns.addCode(CodeBlock.builder().add("return ($T) super.$N(", typeName, executableElement.getSimpleName()).add(FluentIterable.from(returns.build().parameters).transform(new Function<ParameterSpec, String>() { // from class: com.bumptech.glide.annotation.compiler.RequestOptionsOverrideGenerator.3
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Function
            public String apply(ParameterSpec parameterSpec) {
                return parameterSpec.name;
            }
        }).join(Joiner.on(", ")), new Object[0]).add(");\n", new Object[0]).build());
        if (executableElement.getSimpleName().toString().contains("transform") && executableElement.isVarArgs()) {
            returns.addModifiers(Modifier.FINAL).addAnnotation(SafeVarargs.class).addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "varargs").build());
        }
        for (AnnotationMirror annotationMirror : executableElement.getAnnotationMirrors()) {
            returns.addAnnotation(AnnotationSpec.get(annotationMirror));
        }
        return returns.build();
    }
}
