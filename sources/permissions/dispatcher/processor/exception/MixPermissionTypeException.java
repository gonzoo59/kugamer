package permissions.dispatcher.processor.exception;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.util.ExtensionsKt;
/* compiled from: MixPermissionTypeException.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lpermissions/dispatcher/processor/exception/MixPermissionTypeException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "e", "Ljavax/lang/model/element/ExecutableElement;", "permissionName", "", "(Ljavax/lang/model/element/ExecutableElement;Ljava/lang/String;)V", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class MixPermissionTypeException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MixPermissionTypeException(ExecutableElement e, String permissionName) {
        super("Method '" + ExtensionsKt.simpleString((Element) e) + "()' defines '" + permissionName + "' with other permissions at the same time.");
        Intrinsics.checkParameterIsNotNull(e, "e");
        Intrinsics.checkParameterIsNotNull(permissionName, "permissionName");
    }
}
