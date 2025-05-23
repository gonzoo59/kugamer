package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
/* loaded from: classes.dex */
final class LibraryModuleProcessor {
    private final IndexerGenerator indexerGenerator;
    private final ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LibraryModuleProcessor(ProcessorUtil processorUtil, IndexerGenerator indexerGenerator) {
        this.processorUtil = processorUtil;
        this.indexerGenerator = indexerGenerator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean processModules(RoundEnvironment roundEnvironment) {
        ArrayList arrayList = new ArrayList();
        for (TypeElement typeElement : this.processorUtil.getElementsFor(GlideModule.class, roundEnvironment)) {
            if (!this.processorUtil.isAppGlideModule(typeElement)) {
                if (!this.processorUtil.isLibraryGlideModule(typeElement)) {
                    throw new IllegalStateException("@GlideModule can only be applied to LibraryGlideModule and AppGlideModule implementations, not: " + typeElement);
                }
                arrayList.add(typeElement);
            }
        }
        ProcessorUtil processorUtil = this.processorUtil;
        processorUtil.debugLog("got child modules: " + arrayList);
        if (arrayList.isEmpty()) {
            return false;
        }
        this.processorUtil.writeIndexer(this.indexerGenerator.generate(arrayList));
        this.processorUtil.debugLog("Wrote an Indexer this round, skipping the app module to ensure all indexers are found");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GlideModule.class.getName());
    }
}
