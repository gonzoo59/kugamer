package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: inlineClassesUtils.kt */
/* loaded from: classes2.dex */
public final class InlineClassesUtilsKt {
    public static final ValueParameterDescriptor underlyingRepresentation(ClassDescriptor underlyingRepresentation) {
        ClassConstructorDescriptor mo1086getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Intrinsics.checkParameterIsNotNull(underlyingRepresentation, "$this$underlyingRepresentation");
        if (!underlyingRepresentation.isInline() || (mo1086getUnsubstitutedPrimaryConstructor = underlyingRepresentation.mo1086getUnsubstitutedPrimaryConstructor()) == null || (valueParameters = mo1086getUnsubstitutedPrimaryConstructor.getValueParameters()) == null) {
            return null;
        }
        return (ValueParameterDescriptor) CollectionsKt.singleOrNull((List<? extends Object>) valueParameters);
    }

    public static final boolean isInlineClass(DeclarationDescriptor isInlineClass) {
        Intrinsics.checkParameterIsNotNull(isInlineClass, "$this$isInlineClass");
        return (isInlineClass instanceof ClassDescriptor) && ((ClassDescriptor) isInlineClass).isInline();
    }

    public static final ValueParameterDescriptor unsubstitutedUnderlyingParameter(KotlinType unsubstitutedUnderlyingParameter) {
        Intrinsics.checkParameterIsNotNull(unsubstitutedUnderlyingParameter, "$this$unsubstitutedUnderlyingParameter");
        ClassifierDescriptor mo1092getDeclarationDescriptor = unsubstitutedUnderlyingParameter.getConstructor().mo1092getDeclarationDescriptor();
        if (!(mo1092getDeclarationDescriptor instanceof ClassDescriptor)) {
            mo1092getDeclarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) mo1092getDeclarationDescriptor;
        if (classDescriptor != null) {
            return underlyingRepresentation(classDescriptor);
        }
        return null;
    }

    public static final boolean isInlineClassType(KotlinType isInlineClassType) {
        Intrinsics.checkParameterIsNotNull(isInlineClassType, "$this$isInlineClassType");
        ClassifierDescriptor mo1092getDeclarationDescriptor = isInlineClassType.getConstructor().mo1092getDeclarationDescriptor();
        if (mo1092getDeclarationDescriptor != null) {
            return isInlineClass(mo1092getDeclarationDescriptor);
        }
        return false;
    }

    public static final KotlinType substitutedUnderlyingType(KotlinType substitutedUnderlyingType) {
        Intrinsics.checkParameterIsNotNull(substitutedUnderlyingType, "$this$substitutedUnderlyingType");
        ValueParameterDescriptor unsubstitutedUnderlyingParameter = unsubstitutedUnderlyingParameter(substitutedUnderlyingType);
        if (unsubstitutedUnderlyingParameter != null) {
            MemberScope memberScope = substitutedUnderlyingType.getMemberScope();
            Name name = unsubstitutedUnderlyingParameter.getName();
            Intrinsics.checkExpressionValueIsNotNull(name, "parameter.name");
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) CollectionsKt.singleOrNull(memberScope.getContributedVariables(name, NoLookupLocation.FOR_ALREADY_TRACKED));
            if (propertyDescriptor != null) {
                return propertyDescriptor.getType();
            }
            return null;
        }
        return null;
    }

    public static final boolean isGetterOfUnderlyingPropertyOfInlineClass(CallableDescriptor isGetterOfUnderlyingPropertyOfInlineClass) {
        Intrinsics.checkParameterIsNotNull(isGetterOfUnderlyingPropertyOfInlineClass, "$this$isGetterOfUnderlyingPropertyOfInlineClass");
        if (isGetterOfUnderlyingPropertyOfInlineClass instanceof PropertyGetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) isGetterOfUnderlyingPropertyOfInlineClass).getCorrespondingProperty();
            Intrinsics.checkExpressionValueIsNotNull(correspondingProperty, "correspondingProperty");
            if (isUnderlyingPropertyOfInlineClass(correspondingProperty)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnderlyingPropertyOfInlineClass(VariableDescriptor isUnderlyingPropertyOfInlineClass) {
        Intrinsics.checkParameterIsNotNull(isUnderlyingPropertyOfInlineClass, "$this$isUnderlyingPropertyOfInlineClass");
        DeclarationDescriptor containingDeclaration = isUnderlyingPropertyOfInlineClass.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "this.containingDeclaration");
        if (isInlineClass(containingDeclaration)) {
            if (containingDeclaration != null) {
                ValueParameterDescriptor underlyingRepresentation = underlyingRepresentation((ClassDescriptor) containingDeclaration);
                return Intrinsics.areEqual(underlyingRepresentation != null ? underlyingRepresentation.getName() : null, isUnderlyingPropertyOfInlineClass.getName());
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        return false;
    }
}
