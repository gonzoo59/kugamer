package permissions.dispatcher.processor.util;

import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.FunSpec;
import com.squareup.kotlinpoet.PropertySpec;
import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeNames;
import com.squareup.kotlinpoet.TypeSpec;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.processor.PermissionsProcessorKt;
/* compiled from: Extensions.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000`\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u001a\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003\u001a\u0018\u0010\b\u001a\u00020\u0001*\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\r\u001a\n\u0010\u000e\u001a\u00020\f*\u00020\f\u001a(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003\"\b\b\u0000\u0010\u0011*\u00020\u0012*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0015\u001a\"\u0010\u0016\u001a\u00020\u0017\"\b\b\u0000\u0010\u0011*\u00020\u0012*\u00020\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0015\u001a\n\u0010\u0019\u001a\u00020\u0017*\u00020\r\u001a\u0012\u0010\u001a\u001a\u00020\u0017*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b\u001a\u0012\u0010\u001d\u001a\u00020\f*\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0017\u001a\u000e\u0010\u001f\u001a\u00020 *\u0004\u0018\u00010\u0013H\u0002\u001a\n\u0010\u001f\u001a\u00020 *\u00020!\u001a\u0010\u0010\"\u001a\b\u0012\u0004\u0012\u00020 0\u0003*\u00020\u0012\u001a\n\u0010#\u001a\u00020 *\u00020\u0013\u001a\n\u0010#\u001a\u00020 *\u00020\u001b\u001a\f\u0010$\u001a\u00020 *\u00020 H\u0000¨\u0006%"}, d2 = {"addFunctions", "Lcom/squareup/kotlinpoet/FileSpec$Builder;", "functions", "", "Lcom/squareup/kotlinpoet/FunSpec;", "addProperties", "properties", "Lcom/squareup/kotlinpoet/PropertySpec;", "addTypes", "types", "Lcom/squareup/kotlinpoet/TypeSpec;", "asPreparedType", "Lcom/squareup/kotlinpoet/TypeName;", "Ljavax/lang/model/element/VariableElement;", "checkStringType", "childElementsAnnotatedWith", "Ljavax/lang/model/element/ExecutableElement;", "A", "", "Ljavax/lang/model/element/Element;", "annotationClass", "Ljava/lang/Class;", "hasAnnotation", "", "annotationType", "isNullable", "isSubtypeOf", "Ljavax/lang/model/type/TypeMirror;", "ofType", "mapToNullableTypeIf", "nullable", "packageName", "", "Ljavax/lang/model/element/TypeElement;", "permissionValue", "simpleString", "trimDollarIfNeeded", "processor"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class ExtensionsKt {
    public static final String packageName(TypeElement receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return packageName(receiver.getEnclosingElement());
    }

    private static final String packageName(Element element) {
        Element enclosingElement;
        String packageName;
        return element instanceof TypeElement ? packageName((TypeElement) element) : element instanceof PackageElement ? ((PackageElement) element).getQualifiedName().toString() : (element == null || (enclosingElement = element.getEnclosingElement()) == null || (packageName = packageName(enclosingElement)) == null) ? "" : packageName;
    }

    public static final String trimDollarIfNeeded(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        int indexOf$default = StringsKt.indexOf$default((CharSequence) receiver, "$", 0, false, 6, (Object) null);
        if (indexOf$default == -1) {
            return receiver;
        }
        String substring = receiver.substring(0, indexOf$default);
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static final String simpleString(Element receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return trimDollarIfNeeded(receiver.getSimpleName().toString());
    }

    public static final String simpleString(TypeMirror receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        String obj = receiver.toString();
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) obj, '.', 0, false, 6, (Object) null);
        if (lastIndexOf$default == -1) {
            return obj;
        }
        int i = lastIndexOf$default + 1;
        if (obj != null) {
            String substring = obj.substring(i);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            return substring;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final <A extends Annotation> boolean hasAnnotation(Element receiver, Class<A> annotationType) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(annotationType, "annotationType");
        return receiver.getAnnotation(annotationType) != null;
    }

    public static final boolean isNullable(VariableElement receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        List annotationMirrors = receiver.getAnnotationMirrors();
        Intrinsics.checkExpressionValueIsNotNull(annotationMirrors, "this.annotationMirrors");
        List<AnnotationMirror> list = annotationMirrors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (AnnotationMirror it : list) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            TypeMirror annotationType = it.getAnnotationType();
            Intrinsics.checkExpressionValueIsNotNull(annotationType, "it.annotationType");
            arrayList.add(simpleString(annotationType));
        }
        return CollectionsKt.toList(arrayList).contains("Nullable");
    }

    public static final TypeName asPreparedType(VariableElement receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        TypeMirror asType = receiver.asType();
        Intrinsics.checkExpressionValueIsNotNull(asType, "this.asType()");
        return mapToNullableTypeIf(checkStringType(TypeNames.get(asType)), isNullable(receiver));
    }

    public static final List<String> permissionValue(Annotation receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return receiver instanceof NeedsPermission ? ArraysKt.asList(((NeedsPermission) receiver).value()) : receiver instanceof OnShowRationale ? ArraysKt.asList(((OnShowRationale) receiver).value()) : receiver instanceof OnPermissionDenied ? ArraysKt.asList(((OnPermissionDenied) receiver).value()) : receiver instanceof OnNeverAskAgain ? ArraysKt.asList(((OnNeverAskAgain) receiver).value()) : CollectionsKt.emptyList();
    }

    public static final <A extends Annotation> List<ExecutableElement> childElementsAnnotatedWith(Element receiver, Class<A> annotationClass) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        List enclosedElements = receiver.getEnclosedElements();
        Intrinsics.checkExpressionValueIsNotNull(enclosedElements, "this.enclosedElements");
        ArrayList arrayList = new ArrayList();
        for (Object obj : enclosedElements) {
            Element it = (Element) obj;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            if (hasAnnotation(it, annotationClass)) {
                arrayList.add(obj);
            }
        }
        ArrayList<ExecutableElement> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ExecutableElement executableElement : arrayList2) {
            if (executableElement == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.lang.model.element.ExecutableElement");
            }
            arrayList3.add(executableElement);
        }
        return arrayList3;
    }

    public static final boolean isSubtypeOf(TypeMirror receiver, TypeMirror ofType) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(ofType, "ofType");
        return PermissionsProcessorKt.getTYPE_UTILS().isSubtype(receiver, ofType);
    }

    public static final FileSpec.Builder addProperties(FileSpec.Builder receiver, List<PropertySpec> properties) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(properties, "properties");
        for (PropertySpec propertySpec : properties) {
            receiver.addProperty(propertySpec);
        }
        return receiver;
    }

    public static final FileSpec.Builder addFunctions(FileSpec.Builder receiver, List<FunSpec> functions) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
        for (FunSpec funSpec : functions) {
            receiver.addFunction(funSpec);
        }
        return receiver;
    }

    public static final FileSpec.Builder addTypes(FileSpec.Builder receiver, List<TypeSpec> types) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(types, "types");
        for (TypeSpec typeSpec : types) {
            receiver.addType(typeSpec);
        }
        return receiver;
    }

    public static final TypeName checkStringType(TypeName receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return Intrinsics.areEqual(receiver.toString(), "java.lang.String") ? new ClassName("kotlin", "String", new String[0]) : receiver;
    }

    public static final TypeName mapToNullableTypeIf(TypeName receiver, boolean z) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return z ? receiver.asNullable() : receiver.asNonNullable();
    }
}
