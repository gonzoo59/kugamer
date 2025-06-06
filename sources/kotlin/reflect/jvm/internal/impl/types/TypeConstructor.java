package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
/* loaded from: classes2.dex */
public interface TypeConstructor extends TypeConstructorMarker {
    KotlinBuiltIns getBuiltIns();

    /* renamed from: getDeclarationDescriptor */
    ClassifierDescriptor mo1092getDeclarationDescriptor();

    List<TypeParameterDescriptor> getParameters();

    /* renamed from: getSupertypes */
    Collection<KotlinType> mo1093getSupertypes();

    boolean isDenotable();

    TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner);
}
