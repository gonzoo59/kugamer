package permissions.dispatcher.processor;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
/* compiled from: PermissionsProcessor.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\"+\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006\"+\u0010\n\u001a\u00020\t2\u0006\u0010\u0000\u001a\u00020\t8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\b\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"<set-?>", "Ljavax/lang/model/util/Elements;", "ELEMENT_UTILS", "getELEMENT_UTILS", "()Ljavax/lang/model/util/Elements;", "setELEMENT_UTILS", "(Ljavax/lang/model/util/Elements;)V", "ELEMENT_UTILS$delegate", "Lkotlin/properties/ReadWriteProperty;", "Ljavax/lang/model/util/Types;", "TYPE_UTILS", "getTYPE_UTILS", "()Ljavax/lang/model/util/Types;", "setTYPE_UTILS", "(Ljavax/lang/model/util/Types;)V", "TYPE_UTILS$delegate", "processor"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class PermissionsProcessorKt {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.mutableProperty0(new MutablePropertyReference0Impl(Reflection.getOrCreateKotlinPackage(PermissionsProcessorKt.class, "processor"), "ELEMENT_UTILS", "getELEMENT_UTILS()Ljavax/lang/model/util/Elements;")), Reflection.mutableProperty0(new MutablePropertyReference0Impl(Reflection.getOrCreateKotlinPackage(PermissionsProcessorKt.class, "processor"), "TYPE_UTILS", "getTYPE_UTILS()Ljavax/lang/model/util/Types;"))};
    private static final ReadWriteProperty ELEMENT_UTILS$delegate = Delegates.INSTANCE.notNull();
    private static final ReadWriteProperty TYPE_UTILS$delegate = Delegates.INSTANCE.notNull();

    public static final Elements getELEMENT_UTILS() {
        return (Elements) ELEMENT_UTILS$delegate.getValue(null, $$delegatedProperties[0]);
    }

    public static final Types getTYPE_UTILS() {
        return (Types) TYPE_UTILS$delegate.getValue(null, $$delegatedProperties[1]);
    }

    public static final void setELEMENT_UTILS(Elements elements) {
        Intrinsics.checkParameterIsNotNull(elements, "<set-?>");
        ELEMENT_UTILS$delegate.setValue(null, $$delegatedProperties[0], elements);
    }

    public static final void setTYPE_UTILS(Types types) {
        Intrinsics.checkParameterIsNotNull(types, "<set-?>");
        TYPE_UTILS$delegate.setValue(null, $$delegatedProperties[1], types);
    }
}
