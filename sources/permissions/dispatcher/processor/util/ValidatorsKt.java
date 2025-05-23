package permissions.dispatcher.processor.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.PermissionsProcessorKt;
import permissions.dispatcher.processor.ProcessorUnit;
import permissions.dispatcher.processor.RuntimePermissionsElement;
import permissions.dispatcher.processor.exception.DuplicatedMethodNameException;
import permissions.dispatcher.processor.exception.DuplicatedValueException;
import permissions.dispatcher.processor.exception.MixPermissionTypeException;
import permissions.dispatcher.processor.exception.NoAnnotatedMethodsException;
import permissions.dispatcher.processor.exception.NoParametersAllowedException;
import permissions.dispatcher.processor.exception.NoThrowsAllowedException;
import permissions.dispatcher.processor.exception.PrivateMethodException;
import permissions.dispatcher.processor.exception.WrongClassException;
import permissions.dispatcher.processor.exception.WrongParametersException;
import permissions.dispatcher.processor.exception.WrongReturnTypeException;
/* compiled from: Validators.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u001a,\u0010\b\u001a\u00020\u0004\"\b\b\u0000\u0010\t*\u00020\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f\u001a5\u0010\r\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u0011\"\u00020\u0012¢\u0006\u0002\u0010\u0013\u001a\u0014\u0010\u0014\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u001a,\u0010\u0015\u001a\u00020\u0004\"\b\b\u0000\u0010\t*\u00020\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f\u001a4\u0010\u0016\u001a\u00020\u0004\"\b\b\u0000\u0010\t*\u00020\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f\u001a,\u0010\u0019\u001a\u00020\u0004\"\b\b\u0000\u0010\t*\u00020\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\f\u001a.\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001b\"\u0004\b\u0000\u0010\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001c0\u001b0\u00062\u0006\u0010\u001e\u001a\u00020\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"SYSTEM_ALERT_WINDOW", "", "WRITE_SETTINGS", "checkDuplicatedMethodName", "", "items", "", "Ljavax/lang/model/element/ExecutableElement;", "checkDuplicatedValue", "A", "", "annotationClass", "Ljava/lang/Class;", "checkMethodParameters", "numParams", "", "requiredTypes", "", "Ljavax/lang/model/type/TypeMirror;", "(Ljava/util/List;I[Ljavax/lang/model/type/TypeMirror;)V", "checkMethodSignature", "checkMixPermissionType", "checkNotEmpty", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "checkPrivateMethods", "findAndValidateProcessorUnit", "Lpermissions/dispatcher/processor/ProcessorUnit;", "K", "units", "element", "Ljavax/lang/model/element/Element;", "processor"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class ValidatorsKt {
    private static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
    private static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";

    public static final <K> ProcessorUnit<K> findAndValidateProcessorUnit(List<? extends ProcessorUnit<? extends K>> units, Element element) {
        Intrinsics.checkParameterIsNotNull(units, "units");
        Intrinsics.checkParameterIsNotNull(element, "element");
        TypeMirror type = element.asType();
        try {
            for (Object obj : units) {
                Intrinsics.checkExpressionValueIsNotNull(type, "type");
                if (ExtensionsKt.isSubtypeOf(type, ((ProcessorUnit) obj).getTargetType())) {
                    return (ProcessorUnit) obj;
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        } catch (NoSuchElementException unused) {
            Intrinsics.checkExpressionValueIsNotNull(type, "type");
            throw new WrongClassException(type);
        }
    }

    public static final <A extends Annotation> void checkDuplicatedValue(List<? extends ExecutableElement> items, Class<A> annotationClass) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        ArrayList<List> arrayList = new ArrayList();
        for (ExecutableElement executableElement : items) {
            Annotation annotation = executableElement.getAnnotation(annotationClass);
            Intrinsics.checkExpressionValueIsNotNull(annotation, "it.getAnnotation(annotationClass)");
            List sorted = CollectionsKt.sorted(ExtensionsKt.permissionValue(annotation));
            for (List list : arrayList) {
                if (Intrinsics.areEqual(list, sorted)) {
                    throw new DuplicatedValueException(sorted, executableElement, annotationClass);
                }
            }
            arrayList.add(sorted);
        }
    }

    public static final <A extends Annotation> void checkNotEmpty(List<? extends ExecutableElement> items, RuntimePermissionsElement rpe, Class<A> annotationClass) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        if (items.isEmpty()) {
            throw new NoAnnotatedMethodsException(rpe, annotationClass);
        }
    }

    public static final <A extends Annotation> void checkPrivateMethods(List<? extends ExecutableElement> items, Class<A> annotationClass) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        for (ExecutableElement executableElement : items) {
            if (executableElement.getModifiers().contains(Modifier.PRIVATE)) {
                throw new PrivateMethodException(executableElement, annotationClass);
            }
        }
    }

    public static final void checkMethodSignature(List<? extends ExecutableElement> items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        for (ExecutableElement executableElement : items) {
            TypeMirror returnType = executableElement.getReturnType();
            Intrinsics.checkExpressionValueIsNotNull(returnType, "it.returnType");
            if (returnType.getKind() != TypeKind.VOID) {
                throw new WrongReturnTypeException(executableElement);
            }
            List thrownTypes = executableElement.getThrownTypes();
            Intrinsics.checkExpressionValueIsNotNull(thrownTypes, "it.thrownTypes");
            if (!thrownTypes.isEmpty()) {
                throw new NoThrowsAllowedException(executableElement);
            }
        }
    }

    public static final void checkMethodParameters(List<? extends ExecutableElement> items, int i, TypeMirror... requiredTypes) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Intrinsics.checkParameterIsNotNull(requiredTypes, "requiredTypes");
        for (ExecutableElement executableElement : items) {
            List<VariableElement> params = executableElement.getParameters();
            if (i == 0) {
                Intrinsics.checkExpressionValueIsNotNull(params, "params");
                if (!params.isEmpty()) {
                    throw new NoParametersAllowedException(executableElement);
                }
            }
            if (i != params.size()) {
                throw new WrongParametersException(executableElement, requiredTypes);
            }
            Intrinsics.checkExpressionValueIsNotNull(params, "params");
            int i2 = 0;
            for (VariableElement variableElement : params) {
                int i3 = i2 + 1;
                if (!PermissionsProcessorKt.getTYPE_UTILS().isSameType(variableElement.asType(), requiredTypes[i2])) {
                    throw new WrongParametersException(executableElement, requiredTypes);
                }
                i2 = i3;
            }
        }
    }

    public static final <A extends Annotation> void checkMixPermissionType(List<? extends ExecutableElement> items, Class<A> annotationClass) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Intrinsics.checkParameterIsNotNull(annotationClass, "annotationClass");
        for (ExecutableElement executableElement : items) {
            Annotation annotation = executableElement.getAnnotation(annotationClass);
            Intrinsics.checkExpressionValueIsNotNull(annotation, "it.getAnnotation(annotationClass)");
            List<String> permissionValue = ExtensionsKt.permissionValue(annotation);
            if (permissionValue.size() > 1) {
                if (permissionValue.contains(WRITE_SETTINGS)) {
                    throw new MixPermissionTypeException(executableElement, WRITE_SETTINGS);
                }
                if (permissionValue.contains(SYSTEM_ALERT_WINDOW)) {
                    throw new MixPermissionTypeException(executableElement, SYSTEM_ALERT_WINDOW);
                }
            }
        }
    }

    public static final void checkDuplicatedMethodName(List<? extends ExecutableElement> items) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(items, "items");
        List<? extends ExecutableElement> list = items;
        for (ExecutableElement executableElement : list) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                ExecutableElement executableElement2 = (ExecutableElement) obj;
                boolean z = true;
                if (!(!Intrinsics.areEqual(executableElement2, executableElement)) || !Intrinsics.areEqual(executableElement2.getSimpleName(), executableElement.getSimpleName())) {
                    z = false;
                    continue;
                }
                if (z) {
                    break;
                }
            }
            if (((ExecutableElement) obj) != null) {
                throw new DuplicatedMethodNameException(executableElement);
            }
        }
    }
}
