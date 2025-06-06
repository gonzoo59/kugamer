package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
/* compiled from: CompanionObjectMapping.kt */
/* loaded from: classes2.dex */
public final class CompanionObjectMapping {
    public static final CompanionObjectMapping INSTANCE = new CompanionObjectMapping();
    private static final LinkedHashSet<ClassId> classIds;

    static {
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        Intrinsics.checkExpressionValueIsNotNull(set, "PrimitiveType.NUMBER_TYPES");
        Set<PrimitiveType> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        for (PrimitiveType primitiveType : set2) {
            arrayList.add(KotlinBuiltIns.getPrimitiveFqName(primitiveType));
        }
        LinkedHashSet<ClassId> linkedHashSet = new LinkedHashSet<>();
        for (FqName fqName : CollectionsKt.plus((Collection<? extends FqName>) CollectionsKt.plus((Collection<? extends FqName>) CollectionsKt.plus((Collection<? extends FqName>) arrayList, KotlinBuiltIns.FQ_NAMES.string.toSafe()), KotlinBuiltIns.FQ_NAMES._boolean.toSafe()), KotlinBuiltIns.FQ_NAMES._enum.toSafe())) {
            linkedHashSet.add(ClassId.topLevel(fqName));
        }
        classIds = linkedHashSet;
    }

    private CompanionObjectMapping() {
    }

    public final Set<ClassId> allClassesWithIntrinsicCompanions() {
        Set<ClassId> unmodifiableSet = Collections.unmodifiableSet(classIds);
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableSet, "Collections.unmodifiableSet(classIds)");
        return unmodifiableSet;
    }

    public final boolean isMappedIntrinsicCompanionObject(ClassDescriptor classDescriptor) {
        Intrinsics.checkParameterIsNotNull(classDescriptor, "classDescriptor");
        if (DescriptorUtils.isCompanionObject(classDescriptor)) {
            LinkedHashSet<ClassId> linkedHashSet = classIds;
            ClassId classId = DescriptorUtilsKt.getClassId(classDescriptor);
            if (CollectionsKt.contains(linkedHashSet, classId != null ? classId.getOuterClassId() : null)) {
                return true;
            }
        }
        return false;
    }
}
