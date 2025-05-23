package permissions.dispatcher.processor;

import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
/* compiled from: ProcessorUnit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lpermissions/dispatcher/processor/ProcessorUnit;", "K", "", "createFile", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "requestCodeProvider", "Lpermissions/dispatcher/processor/RequestCodeProvider;", "(Lpermissions/dispatcher/processor/RuntimePermissionsElement;Lpermissions/dispatcher/processor/RequestCodeProvider;)Ljava/lang/Object;", "getTargetType", "Ljavax/lang/model/type/TypeMirror;", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public interface ProcessorUnit<K> {
    K createFile(RuntimePermissionsElement runtimePermissionsElement, RequestCodeProvider requestCodeProvider);

    TypeMirror getTargetType();
}
