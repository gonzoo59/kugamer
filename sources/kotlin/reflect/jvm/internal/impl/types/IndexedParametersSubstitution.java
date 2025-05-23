package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
/* compiled from: TypeSubstitution.kt */
/* loaded from: classes2.dex */
public final class IndexedParametersSubstitution extends TypeSubstitution {
    private final boolean approximateCapturedTypes;
    private final TypeProjection[] arguments;
    private final TypeParameterDescriptor[] parameters;

    public final TypeParameterDescriptor[] getParameters() {
        return this.parameters;
    }

    public final TypeProjection[] getArguments() {
        return this.arguments;
    }

    public /* synthetic */ IndexedParametersSubstitution(TypeParameterDescriptor[] typeParameterDescriptorArr, TypeProjection[] typeProjectionArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeParameterDescriptorArr, typeProjectionArr, (i & 4) != 0 ? false : z);
    }

    public IndexedParametersSubstitution(TypeParameterDescriptor[] parameters, TypeProjection[] arguments, boolean z) {
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        this.parameters = parameters;
        this.arguments = arguments;
        this.approximateCapturedTypes = z;
        int length = parameters.length;
        int length2 = arguments.length;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public IndexedParametersSubstitution(java.util.List<? extends kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor> r9, java.util.List<? extends kotlin.reflect.jvm.internal.impl.types.TypeProjection> r10) {
        /*
            r8 = this;
            java.lang.String r0 = "parameters"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r9, r0)
            java.lang.String r0 = "argumentsList"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r10, r0)
            java.util.Collection r9 = (java.util.Collection) r9
            r0 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor[] r1 = new kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor[r0]
            java.lang.Object[] r9 = r9.toArray(r1)
            java.lang.String r1 = "null cannot be cast to non-null type kotlin.Array<T>"
            if (r9 == 0) goto L35
            r3 = r9
            kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor[] r3 = (kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor[]) r3
            java.util.Collection r10 = (java.util.Collection) r10
            kotlin.reflect.jvm.internal.impl.types.TypeProjection[] r9 = new kotlin.reflect.jvm.internal.impl.types.TypeProjection[r0]
            java.lang.Object[] r9 = r10.toArray(r9)
            if (r9 == 0) goto L2f
            r4 = r9
            kotlin.reflect.jvm.internal.impl.types.TypeProjection[] r4 = (kotlin.reflect.jvm.internal.impl.types.TypeProjection[]) r4
            r5 = 0
            r6 = 4
            r7 = 0
            r2 = r8
            r2.<init>(r3, r4, r5, r6, r7)
            return
        L2f:
            kotlin.TypeCastException r9 = new kotlin.TypeCastException
            r9.<init>(r1)
            throw r9
        L35:
            kotlin.TypeCastException r9 = new kotlin.TypeCastException
            r9.<init>(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution.<init>(java.util.List, java.util.List):void");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    public boolean isEmpty() {
        return this.arguments.length == 0;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    public boolean approximateContravariantCapturedTypes() {
        return this.approximateCapturedTypes;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    /* renamed from: get */
    public TypeProjection mo1097get(KotlinType key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        ClassifierDescriptor mo1092getDeclarationDescriptor = key.getConstructor().mo1092getDeclarationDescriptor();
        if (!(mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
            mo1092getDeclarationDescriptor = null;
        }
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) mo1092getDeclarationDescriptor;
        if (typeParameterDescriptor != null) {
            int index = typeParameterDescriptor.getIndex();
            TypeParameterDescriptor[] typeParameterDescriptorArr = this.parameters;
            if (index < typeParameterDescriptorArr.length && Intrinsics.areEqual(typeParameterDescriptorArr[index].getTypeConstructor(), typeParameterDescriptor.getTypeConstructor())) {
                return this.arguments[index];
            }
        }
        return null;
    }
}
