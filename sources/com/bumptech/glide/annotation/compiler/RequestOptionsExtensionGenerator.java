package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.CodeBlock;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RequestOptionsExtensionGenerator {
    private TypeName containingClassName;
    private ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RequestOptionsExtensionGenerator(TypeName typeName, ProcessorUtil processorUtil) {
        this.containingClassName = typeName;
        this.processorUtil = processorUtil;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExecutableElement> getRequestOptionExtensionMethods(Set<String> set) {
        return this.processorUtil.findAnnotatedElementsInClasses(set, GlideOption.class);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<MethodSpec> generateInstanceMethodsForExtensions(Set<String> set) {
        List<ExecutableElement> requestOptionExtensionMethods = getRequestOptionExtensionMethods(set);
        ArrayList arrayList = new ArrayList(requestOptionExtensionMethods.size());
        for (ExecutableElement executableElement : requestOptionExtensionMethods) {
            arrayList.add(generateMethodsForRequestOptionsExtension(executableElement));
        }
        return arrayList;
    }

    private MethodSpec generateMethodsForRequestOptionsExtension(ExecutableElement executableElement) {
        String str;
        if (executableElement.getReturnType().getKind() == TypeKind.VOID) {
            throw new IllegalArgumentException("The " + executableElement.getSimpleName() + " method annotated with @GlideOption in the " + executableElement.getEnclosingElement().getSimpleName() + " @GlideExtension is using a legacy format that is no longer supported. Please change your method definition so that your @GlideModule annotated methods return BaseRequestOptions<?> objects instead of null.");
        }
        int overrideType = this.processorUtil.getOverrideType(executableElement);
        String obj = executableElement.getSimpleName().toString();
        MethodSpec.Builder addAnnotation = MethodSpec.methodBuilder(obj).addModifiers(Modifier.PUBLIC).addJavadoc(this.processorUtil.generateSeeMethodJavadoc(executableElement)).varargs(executableElement.isVarArgs()).returns(this.containingClassName).addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unchecked").build());
        List<? extends VariableElement> subList = executableElement.getParameters().subList(1, executableElement.getParameters().size());
        List<ParameterSpec> parameters = ProcessorUtil.getParameters(subList);
        addAnnotation.addParameters(parameters);
        if (overrideType == 1) {
            addAnnotation.addJavadoc(this.processorUtil.generateSeeMethodJavadoc(this.containingClassName, obj, subList)).addAnnotation(Override.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add(executableElement.getSimpleName().toString());
            StringBuilder sb = new StringBuilder();
            if (!parameters.isEmpty()) {
                for (ParameterSpec parameterSpec : parameters) {
                    sb.append("$L, ");
                    arrayList.add(parameterSpec.name);
                }
                sb = new StringBuilder(sb.substring(0, sb.length() - 2));
            }
            CodeBlock.Builder builder = CodeBlock.builder();
            str = builder.add("super.$N(" + ((Object) sb) + ")", arrayList.toArray(new Object[0])).build().toString();
        } else {
            str = "this";
        }
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb2 = new StringBuilder("return ($T) $T.$L($L, ");
        arrayList2.add(this.containingClassName);
        arrayList2.add(ClassName.get(executableElement.getEnclosingElement().asType()));
        arrayList2.add(executableElement.getSimpleName().toString());
        arrayList2.add(str);
        if (!parameters.isEmpty()) {
            for (ParameterSpec parameterSpec2 : parameters) {
                sb2.append("$L, ");
                arrayList2.add(parameterSpec2.name);
            }
        }
        addAnnotation.addStatement(sb2.substring(0, sb2.length() - 2) + ")", arrayList2.toArray(new Object[0]));
        addAnnotation.addAnnotation(this.processorUtil.checkResult()).addAnnotation(this.processorUtil.nonNull());
        return addAnnotation.build();
    }
}
