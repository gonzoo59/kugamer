package permissions.dispatcher.processor.exception;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.util.ExtensionsKt;
/* compiled from: WrongParametersException.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lpermissions/dispatcher/processor/exception/WrongParametersException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "e", "Ljavax/lang/model/element/ExecutableElement;", "requiredTypes", "", "Ljavax/lang/model/type/TypeMirror;", "(Ljavax/lang/model/element/ExecutableElement;[Ljavax/lang/model/type/TypeMirror;)V", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class WrongParametersException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrongParametersException(ExecutableElement e, TypeMirror[] requiredTypes) {
        super("Method '" + ExtensionsKt.simpleString((Element) e) + "()' must declare parameters of type " + ArraysKt.joinToString$default(requiredTypes, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<TypeMirror, String>() { // from class: permissions.dispatcher.processor.exception.WrongParametersException.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(TypeMirror it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return '\'' + ExtensionsKt.simpleString(it) + '\'';
            }
        }, 30, (Object) null));
        Intrinsics.checkParameterIsNotNull(e, "e");
        Intrinsics.checkParameterIsNotNull(requiredTypes, "requiredTypes");
    }
}
