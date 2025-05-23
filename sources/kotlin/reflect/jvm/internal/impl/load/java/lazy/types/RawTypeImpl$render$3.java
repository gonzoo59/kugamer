package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
/* compiled from: RawType.kt */
/* loaded from: classes2.dex */
final class RawTypeImpl$render$3 extends Lambda implements Function2<String, String, String> {
    public static final RawTypeImpl$render$3 INSTANCE = new RawTypeImpl$render$3();

    RawTypeImpl$render$3() {
        super(2);
    }

    @Override // kotlin.jvm.functions.Function2
    public final String invoke(String replaceArgs, String newArgs) {
        Intrinsics.checkParameterIsNotNull(replaceArgs, "$this$replaceArgs");
        Intrinsics.checkParameterIsNotNull(newArgs, "newArgs");
        if (StringsKt.contains$default((CharSequence) replaceArgs, (char) Typography.less, false, 2, (Object) null)) {
            return StringsKt.substringBefore$default(replaceArgs, (char) Typography.less, (String) null, 2, (Object) null) + Typography.less + newArgs + Typography.greater + StringsKt.substringAfterLast$default(replaceArgs, (char) Typography.greater, (String) null, 2, (Object) null);
        }
        return replaceArgs;
    }
}
