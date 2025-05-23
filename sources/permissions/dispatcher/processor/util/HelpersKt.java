package permissions.dispatcher.processor.util;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import com.squareup.kotlinpoet.CodeBlock;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.processor.PermissionsProcessorKt;
import permissions.dispatcher.processor.RuntimePermissionsElement;
/* compiled from: Helpers.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a6\u0010\u0007\u001a\u0004\u0018\u00010\b\"\b\b\u0000\u0010\t*\u00020\u00022\u0006\u0010\n\u001a\u00020\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\t0\u0001\u001a\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\u001a\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\u001a\u0016\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\b\u001a\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\u001a\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000f\u001a\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c\u001a\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\b\u001a\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010\n\u001a\u00020\b\u001a\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\"#\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\""}, d2 = {"kotlinMetadataClass", "Ljava/lang/Class;", "", "getKotlinMetadataClass", "()Ljava/lang/Class;", "kotlinMetadataClass$delegate", "Lkotlin/Lazy;", "findMatchingMethodForNeeds", "Ljavax/lang/model/element/ExecutableElement;", "A", "needsElement", "otherElements", "", "annotationType", "pendingRequestFieldName", "", "e", "permissionFieldName", "permissionRequestTypeName", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "requestCodeFieldName", "typeMirrorOf", "Ljavax/lang/model/type/TypeMirror;", "className", "typeNameOf", "Lcom/squareup/javapoet/TypeName;", "it", "Ljavax/lang/model/element/Element;", "varargsKtParametersCodeBlock", "Lcom/squareup/kotlinpoet/CodeBlock;", "varargsParametersCodeBlock", "Lcom/squareup/javapoet/CodeBlock;", "withPermissionCheckMethodName", "processor"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class HelpersKt {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(HelpersKt.class, "processor"), "kotlinMetadataClass", "getKotlinMetadataClass()Ljava/lang/Class;"))};
    private static final Lazy kotlinMetadataClass$delegate = LazyKt.lazy(new Function0<Class<Annotation>>() { // from class: permissions.dispatcher.processor.util.HelpersKt$kotlinMetadataClass$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Class<Annotation> invoke() {
            try {
                Class cls = Class.forName("kotlin.Metadata");
                if (cls != null) {
                    return cls;
                }
                throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.Annotation>");
            } catch (Throwable unused) {
                return null;
            }
        }
    });

    public static final Class<Annotation> getKotlinMetadataClass() {
        Lazy lazy = kotlinMetadataClass$delegate;
        KProperty kProperty = $$delegatedProperties[0];
        return (Class) lazy.getValue();
    }

    public static final TypeMirror typeMirrorOf(String className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        TypeMirror asType = PermissionsProcessorKt.getELEMENT_UTILS().getTypeElement(className).asType();
        Intrinsics.checkExpressionValueIsNotNull(asType, "ELEMENT_UTILS.getTypeElement(className).asType()");
        return asType;
    }

    public static final TypeName typeNameOf(Element it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        TypeName typeName = TypeName.get(it.asType());
        Intrinsics.checkExpressionValueIsNotNull(typeName, "TypeName.get(it.asType())");
        return typeName;
    }

    public static final String requestCodeFieldName(ExecutableElement e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantsKt.GEN_REQUEST_CODE_PREFIX);
        String trimDollarIfNeeded = ExtensionsKt.trimDollarIfNeeded(ExtensionsKt.simpleString((Element) e));
        if (trimDollarIfNeeded != null) {
            String upperCase = trimDollarIfNeeded.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            sb.append(upperCase);
            return sb.toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final String permissionFieldName(ExecutableElement e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantsKt.GEN_PERMISSION_PREFIX);
        String trimDollarIfNeeded = ExtensionsKt.trimDollarIfNeeded(ExtensionsKt.simpleString((Element) e));
        if (trimDollarIfNeeded != null) {
            String upperCase = trimDollarIfNeeded.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            sb.append(upperCase);
            return sb.toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final String pendingRequestFieldName(ExecutableElement e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantsKt.GEN_PENDING_PREFIX);
        String trimDollarIfNeeded = ExtensionsKt.trimDollarIfNeeded(ExtensionsKt.simpleString((Element) e));
        if (trimDollarIfNeeded != null) {
            String upperCase = trimDollarIfNeeded.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            sb.append(upperCase);
            return sb.toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final String withPermissionCheckMethodName(ExecutableElement e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        return ExtensionsKt.trimDollarIfNeeded(ExtensionsKt.simpleString((Element) e)) + ConstantsKt.GEN_WITH_PERMISSION_CHECK_SUFFIX;
    }

    public static final String permissionRequestTypeName(RuntimePermissionsElement rpe, ExecutableElement e) {
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(e, "e");
        return rpe.getInputClassName() + StringsKt.capitalize(ExtensionsKt.trimDollarIfNeeded(ExtensionsKt.simpleString((Element) e))) + ConstantsKt.GEN_PERMISSION_REQUEST_SUFFIX;
    }

    public static final <A extends Annotation> ExecutableElement findMatchingMethodForNeeds(ExecutableElement needsElement, List<? extends ExecutableElement> otherElements, Class<A> annotationType) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        Intrinsics.checkParameterIsNotNull(otherElements, "otherElements");
        Intrinsics.checkParameterIsNotNull(annotationType, "annotationType");
        Annotation annotation = needsElement.getAnnotation(NeedsPermission.class);
        Intrinsics.checkExpressionValueIsNotNull(annotation, "needsElement.getAnnotati…dsPermission::class.java)");
        List<String> permissionValue = ExtensionsKt.permissionValue(annotation);
        Iterator<T> it = otherElements.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            Annotation annotation2 = ((ExecutableElement) obj).getAnnotation(annotationType);
            Intrinsics.checkExpressionValueIsNotNull(annotation2, "it.getAnnotation(annotationType)");
            if (Intrinsics.areEqual(ExtensionsKt.permissionValue(annotation2), permissionValue)) {
                break;
            }
        }
        return (ExecutableElement) obj;
    }

    public static final CodeBlock varargsParametersCodeBlock(ExecutableElement needsElement) {
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        CodeBlock.Builder builder = CodeBlock.builder();
        List<Element> parameters = needsElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsElement.parameters");
        int i = 0;
        for (Element it : parameters) {
            int i2 = i + 1;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            builder.add("$L", ExtensionsKt.simpleString(it));
            if (i < needsElement.getParameters().size() - 1) {
                builder.add(", ", new Object[0]);
            }
            i = i2;
        }
        CodeBlock build = builder.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "varargsCall.build()");
        return build;
    }

    public static final com.squareup.kotlinpoet.CodeBlock varargsKtParametersCodeBlock(ExecutableElement needsElement) {
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        CodeBlock.Builder builder = com.squareup.kotlinpoet.CodeBlock.Companion.builder();
        List<Element> parameters = needsElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsElement.parameters");
        int i = 0;
        for (Element it : parameters) {
            int i2 = i + 1;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            builder.add("%L", ExtensionsKt.simpleString(it));
            if (i < needsElement.getParameters().size() - 1) {
                builder.add(", ", new Object[0]);
            }
            i = i2;
        }
        return builder.build();
    }
}
