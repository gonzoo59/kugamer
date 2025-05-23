package permissions.dispatcher.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.kotlinpoet.FileSpec;
import java.io.File;
import java.util.Comparator;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import permissions.dispatcher.RuntimePermissions;
import permissions.dispatcher.processor.impl.ProcessorUnitsKt;
import permissions.dispatcher.processor.util.HelpersKt;
import permissions.dispatcher.processor.util.ValidatorsKt;
/* compiled from: PermissionsProcessor.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 *2\u00020\u0001:\u0001*B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001e\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00142\u0006\u0010 \u001a\u00020!H\u0016J \u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J \u0010)\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR+\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006+"}, d2 = {"Lpermissions/dispatcher/processor/PermissionsProcessor;", "Ljavax/annotation/processing/AbstractProcessor;", "()V", "<set-?>", "Ljavax/annotation/processing/Filer;", "filer", "getFiler", "()Ljavax/annotation/processing/Filer;", "setFiler", "(Ljavax/annotation/processing/Filer;)V", "filer$delegate", "Lkotlin/properties/ReadWriteProperty;", "Ljavax/annotation/processing/Messager;", "messager", "getMessager", "()Ljavax/annotation/processing/Messager;", "setMessager", "(Ljavax/annotation/processing/Messager;)V", "messager$delegate", "getSupportedAnnotationTypes", "", "", "getSupportedSourceVersion", "Ljavax/lang/model/SourceVersion;", "init", "", "processingEnv", "Ljavax/annotation/processing/ProcessingEnvironment;", "process", "", "annotations", "Ljavax/lang/model/element/TypeElement;", "roundEnv", "Ljavax/annotation/processing/RoundEnvironment;", "processJava", "element", "Ljavax/lang/model/element/Element;", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "requestCodeProvider", "Lpermissions/dispatcher/processor/RequestCodeProvider;", "processKotlin", "Companion", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class PermissionsProcessor extends AbstractProcessor {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(PermissionsProcessor.class), "filer", "getFiler()Ljavax/annotation/processing/Filer;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(PermissionsProcessor.class), "messager", "getMessager()Ljavax/annotation/processing/Messager;"))};
    public static final Companion Companion = new Companion(null);
    public static final String KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated";
    private final ReadWriteProperty filer$delegate = Delegates.INSTANCE.notNull();
    private final ReadWriteProperty messager$delegate = Delegates.INSTANCE.notNull();

    private final Filer getFiler() {
        return (Filer) this.filer$delegate.getValue(this, $$delegatedProperties[0]);
    }

    private final void setFiler(Filer filer) {
        this.filer$delegate.setValue(this, $$delegatedProperties[0], filer);
    }

    public final Messager getMessager() {
        return (Messager) this.messager$delegate.getValue(this, $$delegatedProperties[1]);
    }

    public final void setMessager(Messager messager) {
        Intrinsics.checkParameterIsNotNull(messager, "<set-?>");
        this.messager$delegate.setValue(this, $$delegatedProperties[1], messager);
    }

    /* compiled from: PermissionsProcessor.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lpermissions/dispatcher/processor/PermissionsProcessor$Companion;", "", "()V", "KAPT_KOTLIN_GENERATED_OPTION_NAME", "", "processor"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public void init(ProcessingEnvironment processingEnv) {
        Intrinsics.checkParameterIsNotNull(processingEnv, "processingEnv");
        super.init(processingEnv);
        Filer filer = processingEnv.getFiler();
        Intrinsics.checkExpressionValueIsNotNull(filer, "processingEnv.filer");
        setFiler(filer);
        Messager messager = processingEnv.getMessager();
        Intrinsics.checkExpressionValueIsNotNull(messager, "processingEnv.messager");
        setMessager(messager);
        Elements elementUtils = processingEnv.getElementUtils();
        Intrinsics.checkExpressionValueIsNotNull(elementUtils, "processingEnv.elementUtils");
        PermissionsProcessorKt.setELEMENT_UTILS(elementUtils);
        Types typeUtils = processingEnv.getTypeUtils();
        Intrinsics.checkExpressionValueIsNotNull(typeUtils, "processingEnv.typeUtils");
        PermissionsProcessorKt.setTYPE_UTILS(typeUtils);
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public Set<String> getSupportedAnnotationTypes() {
        return SetsKt.hashSetOf(RuntimePermissions.class.getCanonicalName());
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(roundEnv, "roundEnv");
        RequestCodeProvider requestCodeProvider = new RequestCodeProvider();
        Set elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(RuntimePermissions.class);
        Intrinsics.checkExpressionValueIsNotNull(elementsAnnotatedWith, "roundEnv.getElementsAnno…ePermissions::class.java)");
        for (TypeElement typeElement : CollectionsKt.sortedWith(elementsAnnotatedWith, new Comparator<T>() { // from class: permissions.dispatcher.processor.PermissionsProcessor$process$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                Element it = (Element) t;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                Element it2 = (Element) t2;
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                return ComparisonsKt.compareValues(it.getSimpleName().toString(), it2.getSimpleName().toString());
            }
        })) {
            if (typeElement != null) {
                RuntimePermissionsElement runtimePermissionsElement = new RuntimePermissionsElement(typeElement);
                if (typeElement.getAnnotation(HelpersKt.getKotlinMetadataClass()) != null) {
                    processKotlin(typeElement, runtimePermissionsElement, requestCodeProvider);
                } else {
                    processJava(typeElement, runtimePermissionsElement, requestCodeProvider);
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.TypeElement");
            }
        }
        return true;
    }

    private final void processKotlin(Element element, RuntimePermissionsElement runtimePermissionsElement, RequestCodeProvider requestCodeProvider) {
        String replace$default;
        ProcessingEnvironment processingEnv = this.processingEnv;
        Intrinsics.checkExpressionValueIsNotNull(processingEnv, "processingEnv");
        String str = (String) processingEnv.getOptions().get(KAPT_KOTLIN_GENERATED_OPTION_NAME);
        if (str == null || (replace$default = StringsKt.replace$default(str, "kaptKotlin", "kapt", false, 4, (Object) null)) == null) {
            ProcessingEnvironment processingEnv2 = this.processingEnv;
            Intrinsics.checkExpressionValueIsNotNull(processingEnv2, "processingEnv");
            processingEnv2.getMessager().printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.");
            return;
        }
        File file = new File(replace$default);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ((FileSpec) ValidatorsKt.findAndValidateProcessorUnit(ProcessorUnitsKt.kotlinProcessorUnits(getMessager()), element).createFile(runtimePermissionsElement, requestCodeProvider)).writeTo(file);
    }

    private final void processJava(Element element, RuntimePermissionsElement runtimePermissionsElement, RequestCodeProvider requestCodeProvider) {
        ((JavaFile) ValidatorsKt.findAndValidateProcessorUnit(ProcessorUnitsKt.javaProcessorUnits(getMessager()), element).createFile(runtimePermissionsElement, requestCodeProvider)).writeTo(getFiler());
    }
}
