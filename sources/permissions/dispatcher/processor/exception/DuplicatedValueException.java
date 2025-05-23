package permissions.dispatcher.processor.exception;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.util.ExtensionsKt;
/* compiled from: DuplicatedValueException.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lpermissions/dispatcher/processor/exception/DuplicatedValueException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "value", "", "", "e", "Ljavax/lang/model/element/ExecutableElement;", "annotation", "Ljava/lang/Class;", "(Ljava/util/List;Ljavax/lang/model/element/ExecutableElement;Ljava/lang/Class;)V", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class DuplicatedValueException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DuplicatedValueException(List<String> value, ExecutableElement e, Class<?> annotation) {
        super(value + " is duplicated in '" + ExtensionsKt.simpleString((Element) e) + "()' annotated with '@" + annotation.getSimpleName() + '\'');
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(e, "e");
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
    }
}
