package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideExtension;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
/* loaded from: classes.dex */
final class ExtensionProcessor {
    private final GlideExtensionValidator extensionValidator;
    private final IndexerGenerator indexerGenerator;
    private final ProcessorUtil processorUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExtensionProcessor(ProcessingEnvironment processingEnvironment, ProcessorUtil processorUtil, IndexerGenerator indexerGenerator) {
        this.processorUtil = processorUtil;
        this.indexerGenerator = indexerGenerator;
        this.extensionValidator = new GlideExtensionValidator(processingEnvironment, processorUtil);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean processExtensions(RoundEnvironment roundEnvironment) {
        List<TypeElement> elementsFor = this.processorUtil.getElementsFor(GlideExtension.class, roundEnvironment);
        ProcessorUtil processorUtil = this.processorUtil;
        processorUtil.debugLog("Processing types : " + elementsFor);
        for (TypeElement typeElement : elementsFor) {
            this.extensionValidator.validateExtension(typeElement);
            ProcessorUtil processorUtil2 = this.processorUtil;
            processorUtil2.debugLog("Processing elements: " + typeElement.getEnclosedElements());
        }
        if (elementsFor.isEmpty()) {
            return false;
        }
        this.processorUtil.writeIndexer(this.indexerGenerator.generate(elementsFor));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(GlideExtension.class.getName());
    }
}
