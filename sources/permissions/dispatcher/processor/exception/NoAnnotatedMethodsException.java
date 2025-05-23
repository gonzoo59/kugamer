package permissions.dispatcher.processor.exception;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.RuntimePermissionsElement;
/* compiled from: NoAnnotatedMethodsException.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lpermissions/dispatcher/processor/exception/NoAnnotatedMethodsException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "type", "Ljava/lang/Class;", "(Lpermissions/dispatcher/processor/RuntimePermissionsElement;Ljava/lang/Class;)V", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class NoAnnotatedMethodsException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoAnnotatedMethodsException(RuntimePermissionsElement rpe, Class<?> type) {
        super("Annotated class '" + rpe.getInputClassName() + "' doesn't have any method annotated with '@" + type.getSimpleName() + '\'');
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(type, "type");
    }
}
