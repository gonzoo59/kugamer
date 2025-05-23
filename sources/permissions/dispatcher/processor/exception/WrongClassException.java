package permissions.dispatcher.processor.exception;

import com.squareup.javapoet.TypeName;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: WrongClassException.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lpermissions/dispatcher/processor/exception/WrongClassException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "type", "Ljavax/lang/model/type/TypeMirror;", "(Ljavax/lang/model/type/TypeMirror;)V", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class WrongClassException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrongClassException(TypeMirror type) {
        super("Class '" + TypeName.get(type).toString() + "' can't be annotated with '@RuntimePermissions'");
        Intrinsics.checkParameterIsNotNull(type, "type");
    }
}
