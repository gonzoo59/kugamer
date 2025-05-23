package permissions.dispatcher.processor;

import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeNames;
import com.squareup.kotlinpoet.TypeVariableName;
import com.squareup.kotlinpoet.TypeVariableNames;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.processor.util.ConstantsKt;
import permissions.dispatcher.processor.util.ExtensionsKt;
import permissions.dispatcher.processor.util.HelpersKt;
import permissions.dispatcher.processor.util.ValidatorsKt;
/* compiled from: RuntimePermissionsElement.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010$\u001a\u0004\u0018\u00010\u00152\u0006\u0010%\u001a\u00020\u0015J\u0010\u0010&\u001a\u0004\u0018\u00010\u00152\u0006\u0010%\u001a\u00020\u0015J\u0010\u0010'\u001a\u0004\u0018\u00010\u00152\u0006\u0010%\u001a\u00020\u0015J\b\u0010(\u001a\u00020)H\u0002J\b\u0010*\u001a\u00020)H\u0002J\b\u0010+\u001a\u00020)H\u0002J\b\u0010,\u001a\u00020)H\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\bR\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001f\u0010 \u001a\u0010\u0012\f\u0012\n \"*\u0004\u0018\u00010!0!0\u0010¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0013¨\u0006-"}, d2 = {"Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "", "e", "Ljavax/lang/model/element/TypeElement;", "(Ljavax/lang/model/element/TypeElement;)V", "generatedClassName", "", "getGeneratedClassName", "()Ljava/lang/String;", "inputClassName", "getInputClassName", "ktTypeName", "Lcom/squareup/kotlinpoet/TypeName;", "getKtTypeName", "()Lcom/squareup/kotlinpoet/TypeName;", "ktTypeVariables", "", "Lcom/squareup/kotlinpoet/TypeVariableName;", "getKtTypeVariables", "()Ljava/util/List;", "needsElements", "Ljavax/lang/model/element/ExecutableElement;", "getNeedsElements", "onDeniedElements", "onNeverAskElements", "onRationaleElements", "packageName", "getPackageName", "typeName", "Lcom/squareup/javapoet/TypeName;", "getTypeName", "()Lcom/squareup/javapoet/TypeName;", "typeVariables", "Lcom/squareup/javapoet/TypeVariableName;", "kotlin.jvm.PlatformType", "getTypeVariables", "findOnDeniedForNeeds", "needsElement", "findOnNeverAskForNeeds", "findOnRationaleForNeeds", "validateDeniedMethods", "", "validateNeedsMethods", "validateNeverAskMethods", "validateRationaleMethods", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class RuntimePermissionsElement {
    private final String generatedClassName;
    private final String inputClassName;
    private final TypeName ktTypeName;
    private final List<TypeVariableName> ktTypeVariables;
    private final List<ExecutableElement> needsElements;
    private final List<ExecutableElement> onDeniedElements;
    private final List<ExecutableElement> onNeverAskElements;
    private final List<ExecutableElement> onRationaleElements;
    private final String packageName;
    private final com.squareup.javapoet.TypeName typeName;
    private final List<com.squareup.javapoet.TypeVariableName> typeVariables;

    public RuntimePermissionsElement(TypeElement e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        com.squareup.javapoet.TypeName typeName = com.squareup.javapoet.TypeName.get(e.asType());
        Intrinsics.checkExpressionValueIsNotNull(typeName, "TypeName.get(e.asType())");
        this.typeName = typeName;
        TypeMirror asType = e.asType();
        Intrinsics.checkExpressionValueIsNotNull(asType, "e.asType()");
        this.ktTypeName = TypeNames.get(asType);
        List typeParameters = e.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters, "e.typeParameters");
        List<TypeParameterElement> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterElement typeParameterElement : list) {
            arrayList.add(com.squareup.javapoet.TypeVariableName.get(typeParameterElement));
        }
        this.typeVariables = arrayList;
        List typeParameters2 = e.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "e.typeParameters");
        List<TypeParameterElement> list2 = typeParameters2;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (TypeParameterElement it : list2) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            arrayList2.add(TypeVariableNames.get(it));
        }
        this.ktTypeVariables = arrayList2;
        this.packageName = ExtensionsKt.packageName(e);
        Element element = (Element) e;
        String simpleString = ExtensionsKt.simpleString(element);
        this.inputClassName = simpleString;
        this.generatedClassName = simpleString + ConstantsKt.GEN_CLASS_SUFFIX;
        this.needsElements = ExtensionsKt.childElementsAnnotatedWith(element, NeedsPermission.class);
        this.onRationaleElements = ExtensionsKt.childElementsAnnotatedWith(element, OnShowRationale.class);
        this.onDeniedElements = ExtensionsKt.childElementsAnnotatedWith(element, OnPermissionDenied.class);
        this.onNeverAskElements = ExtensionsKt.childElementsAnnotatedWith(element, OnNeverAskAgain.class);
        validateNeedsMethods();
        validateRationaleMethods();
        validateDeniedMethods();
        validateNeverAskMethods();
    }

    public final com.squareup.javapoet.TypeName getTypeName() {
        return this.typeName;
    }

    public final TypeName getKtTypeName() {
        return this.ktTypeName;
    }

    public final List<com.squareup.javapoet.TypeVariableName> getTypeVariables() {
        return this.typeVariables;
    }

    public final List<TypeVariableName> getKtTypeVariables() {
        return this.ktTypeVariables;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getInputClassName() {
        return this.inputClassName;
    }

    public final String getGeneratedClassName() {
        return this.generatedClassName;
    }

    public final List<ExecutableElement> getNeedsElements() {
        return this.needsElements;
    }

    private final void validateNeedsMethods() {
        ValidatorsKt.checkNotEmpty(this.needsElements, this, NeedsPermission.class);
        ValidatorsKt.checkPrivateMethods(this.needsElements, NeedsPermission.class);
        ValidatorsKt.checkMethodSignature(this.needsElements);
        ValidatorsKt.checkMixPermissionType(this.needsElements, NeedsPermission.class);
        ValidatorsKt.checkDuplicatedMethodName(this.needsElements);
    }

    private final void validateRationaleMethods() {
        ValidatorsKt.checkDuplicatedValue(this.onRationaleElements, OnShowRationale.class);
        ValidatorsKt.checkPrivateMethods(this.onRationaleElements, OnShowRationale.class);
        ValidatorsKt.checkMethodSignature(this.onRationaleElements);
        ValidatorsKt.checkMethodParameters(this.onRationaleElements, 1, HelpersKt.typeMirrorOf("permissions.dispatcher.PermissionRequest"));
    }

    private final void validateDeniedMethods() {
        ValidatorsKt.checkDuplicatedValue(this.onDeniedElements, OnPermissionDenied.class);
        ValidatorsKt.checkPrivateMethods(this.onDeniedElements, OnPermissionDenied.class);
        ValidatorsKt.checkMethodSignature(this.onDeniedElements);
        ValidatorsKt.checkMethodParameters(this.onDeniedElements, 0, new TypeMirror[0]);
    }

    private final void validateNeverAskMethods() {
        ValidatorsKt.checkDuplicatedValue(this.onNeverAskElements, OnNeverAskAgain.class);
        ValidatorsKt.checkPrivateMethods(this.onNeverAskElements, OnNeverAskAgain.class);
        ValidatorsKt.checkMethodSignature(this.onNeverAskElements);
        ValidatorsKt.checkMethodParameters(this.onNeverAskElements, 0, new TypeMirror[0]);
    }

    public final ExecutableElement findOnRationaleForNeeds(ExecutableElement needsElement) {
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        return HelpersKt.findMatchingMethodForNeeds(needsElement, this.onRationaleElements, OnShowRationale.class);
    }

    public final ExecutableElement findOnDeniedForNeeds(ExecutableElement needsElement) {
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        return HelpersKt.findMatchingMethodForNeeds(needsElement, this.onDeniedElements, OnPermissionDenied.class);
    }

    public final ExecutableElement findOnNeverAskForNeeds(ExecutableElement needsElement) {
        Intrinsics.checkParameterIsNotNull(needsElement, "needsElement");
        return HelpersKt.findMatchingMethodForNeeds(needsElement, this.onNeverAskElements, OnNeverAskAgain.class);
    }
}
