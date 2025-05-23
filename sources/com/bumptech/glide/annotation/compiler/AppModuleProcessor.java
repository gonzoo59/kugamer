package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
/* loaded from: classes.dex */
final class AppModuleProcessor {
    private static final String COMPILER_PACKAGE_NAME = GlideAnnotationProcessor.class.getPackage().getName();
    private final List<TypeElement> appGlideModules = new ArrayList();
    private final AppModuleGenerator appModuleGenerator;
    private final GlideGenerator glideGenerator;
    private final ProcessingEnvironment processingEnv;
    private final ProcessorUtil processorUtil;
    private final RequestBuilderGenerator requestBuilderGenerator;
    private final RequestManagerFactoryGenerator requestManagerFactoryGenerator;
    private final RequestManagerGenerator requestManagerGenerator;
    private final RequestOptionsGenerator requestOptionsGenerator;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppModuleProcessor(ProcessingEnvironment processingEnvironment, ProcessorUtil processorUtil) {
        this.processingEnv = processingEnvironment;
        this.processorUtil = processorUtil;
        this.appModuleGenerator = new AppModuleGenerator(processingEnvironment, processorUtil);
        this.requestOptionsGenerator = new RequestOptionsGenerator(processingEnvironment, processorUtil);
        this.requestManagerGenerator = new RequestManagerGenerator(processingEnvironment, processorUtil);
        this.requestManagerFactoryGenerator = new RequestManagerFactoryGenerator(processingEnvironment, processorUtil);
        this.glideGenerator = new GlideGenerator(processingEnvironment, processorUtil);
        this.requestBuilderGenerator = new RequestBuilderGenerator(processingEnvironment, processorUtil);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void processModules(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement typeElement : this.processorUtil.getElementsFor(GlideModule.class, roundEnvironment)) {
            if (this.processorUtil.isAppGlideModule(typeElement)) {
                this.appGlideModules.add(typeElement);
            }
        }
        ProcessorUtil processorUtil = this.processorUtil;
        processorUtil.debugLog("got app modules: " + this.appGlideModules);
        if (this.appGlideModules.size() <= 1) {
            return;
        }
        throw new IllegalStateException("You cannot have more than one AppGlideModule, found: " + this.appGlideModules);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean maybeWriteAppModule() {
        if (this.appGlideModules.isEmpty()) {
            return false;
        }
        TypeElement typeElement = this.appGlideModules.get(0);
        ProcessorUtil processorUtil = this.processorUtil;
        processorUtil.debugLog("Processing app module: " + typeElement);
        FoundIndexedClassNames indexedClassNames = getIndexedClassNames(this.processingEnv.getElementUtils().getPackageElement(COMPILER_PACKAGE_NAME));
        String obj = typeElement.getEnclosingElement().toString();
        TypeSpec generate = this.requestOptionsGenerator.generate(obj, indexedClassNames.extensions);
        writeRequestOptions(obj, generate);
        TypeSpec generate2 = this.requestBuilderGenerator.generate(obj, indexedClassNames.extensions, generate);
        writeRequestBuilder(obj, generate2);
        TypeSpec generate3 = this.requestManagerGenerator.generate(obj, generate, generate2, indexedClassNames.extensions);
        writeRequestManager(obj, generate3);
        writeRequestManagerFactory(this.requestManagerFactoryGenerator.generate(obj, generate3));
        writeGlide(obj, this.glideGenerator.generate(obj, getGlideName(typeElement), generate3));
        writeAppModule(this.appModuleGenerator.generate(typeElement, indexedClassNames.glideModules));
        ProcessorUtil processorUtil2 = this.processorUtil;
        processorUtil2.infoLog("Wrote GeneratedAppGlideModule with: " + indexedClassNames.glideModules);
        return true;
    }

    private String getGlideName(TypeElement typeElement) {
        return ((GlideModule) typeElement.getAnnotation(GlideModule.class)).glideName();
    }

    private FoundIndexedClassNames getIndexedClassNames(PackageElement packageElement) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (Element element : packageElement.getEnclosedElements()) {
            Index index = (Index) element.getAnnotation(Index.class);
            if (index != null) {
                Collections.addAll(hashSet, index.modules());
                Collections.addAll(hashSet2, index.extensions());
            }
        }
        ProcessorUtil processorUtil = this.processorUtil;
        processorUtil.debugLog("Found GlideModules: " + hashSet);
        return new FoundIndexedClassNames(hashSet, hashSet2);
    }

    private void writeGlide(String str, TypeSpec typeSpec) {
        this.processorUtil.writeClass(str, typeSpec);
    }

    private void writeRequestManager(String str, TypeSpec typeSpec) {
        this.processorUtil.writeClass(str, typeSpec);
    }

    private void writeRequestManagerFactory(TypeSpec typeSpec) {
        this.processorUtil.writeClass("com.bumptech.glide", typeSpec);
    }

    private void writeAppModule(TypeSpec typeSpec) {
        this.processorUtil.writeClass("com.bumptech.glide", typeSpec);
    }

    private void writeRequestOptions(String str, TypeSpec typeSpec) {
        this.processorUtil.writeClass(str, typeSpec);
    }

    private void writeRequestBuilder(String str, TypeSpec typeSpec) {
        this.processorUtil.writeClass(str, typeSpec);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class FoundIndexedClassNames {
        private final Set<String> extensions;
        private final Set<String> glideModules;

        private FoundIndexedClassNames(Set<String> set, Set<String> set2) {
            this.glideModules = set;
            this.extensions = set2;
        }
    }
}
