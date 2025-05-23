package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.repackaged.com.squareup.javapoet.AnnotationSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
/* loaded from: classes.dex */
final class IndexerGenerator {
    private final ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IndexerGenerator(ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypeSpec generate(List<TypeElement> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (TypeElement typeElement : list) {
            if (this.processorUtil.isExtension(typeElement)) {
                arrayList2.add(typeElement);
            } else if (this.processorUtil.isLibraryGlideModule(typeElement)) {
                arrayList.add(typeElement);
            } else {
                throw new IllegalArgumentException("Unrecognized type: " + typeElement);
            }
        }
        if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
            throw new IllegalArgumentException("Given both modules and extensions, expected one or the other. Modules: " + arrayList + " Extensions: " + arrayList2);
        } else if (!arrayList.isEmpty()) {
            return generate(list, GlideModule.class);
        } else {
            return generate(list, GlideExtension.class);
        }
    }

    private static TypeSpec generate(List<TypeElement> list, Class<? extends Annotation> cls) {
        AnnotationSpec.Builder builder = AnnotationSpec.builder(Index.class);
        String annotationValue = getAnnotationValue(cls);
        Iterator<TypeElement> it = list.iterator();
        while (it.hasNext()) {
            builder.addMember(annotationValue, "$S", ClassName.get(it.next()).toString());
        }
        StringBuilder sb = new StringBuilder("GlideIndexer_" + cls.getSimpleName() + "_");
        for (TypeElement typeElement : list) {
            sb.append(typeElement.getQualifiedName().toString().replace(".", "_"));
            sb.append("_");
        }
        return TypeSpec.classBuilder(new StringBuilder(sb.substring(0, sb.length() - 1)).toString()).addAnnotation(builder.build()).addModifiers(Modifier.PUBLIC).build();
    }

    private static String getAnnotationValue(Class<? extends Annotation> cls) {
        if (cls == GlideModule.class) {
            return "modules";
        }
        if (cls == GlideExtension.class) {
            return "extensions";
        }
        throw new IllegalArgumentException("Unrecognized annotation: " + cls);
    }
}
