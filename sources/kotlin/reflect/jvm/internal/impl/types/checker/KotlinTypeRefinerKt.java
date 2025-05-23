package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
/* compiled from: KotlinTypeRefiner.kt */
/* loaded from: classes2.dex */
public final class KotlinTypeRefinerKt {
    private static final ModuleDescriptor.Capability<Ref<KotlinTypeRefiner>> REFINER_CAPABILITY = new ModuleDescriptor.Capability<>("KotlinTypeRefiner");

    public static final ModuleDescriptor.Capability<Ref<KotlinTypeRefiner>> getREFINER_CAPABILITY() {
        return REFINER_CAPABILITY;
    }

    public static final List<KotlinType> refineTypes(KotlinTypeRefiner refineTypes, Iterable<? extends KotlinType> types) {
        Intrinsics.checkParameterIsNotNull(refineTypes, "$this$refineTypes");
        Intrinsics.checkParameterIsNotNull(types, "types");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(types, 10));
        for (KotlinType kotlinType : types) {
            arrayList.add(refineTypes.refineType(kotlinType));
        }
        return arrayList;
    }
}
