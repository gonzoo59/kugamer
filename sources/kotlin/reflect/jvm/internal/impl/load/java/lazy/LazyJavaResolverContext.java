package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.Lazy;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
/* compiled from: context.kt */
/* loaded from: classes2.dex */
public final class LazyJavaResolverContext {
    private final JavaResolverComponents components;
    private final Lazy defaultTypeQualifiers$delegate;
    private final Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers;
    private final TypeParameterResolver typeParameterResolver;
    private final JavaTypeResolver typeResolver;

    public final JavaTypeQualifiersByElementType getDefaultTypeQualifiers() {
        return (JavaTypeQualifiersByElementType) this.defaultTypeQualifiers$delegate.getValue();
    }

    public LazyJavaResolverContext(JavaResolverComponents components, TypeParameterResolver typeParameterResolver, Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers) {
        Intrinsics.checkParameterIsNotNull(components, "components");
        Intrinsics.checkParameterIsNotNull(typeParameterResolver, "typeParameterResolver");
        Intrinsics.checkParameterIsNotNull(delegateForDefaultTypeQualifiers, "delegateForDefaultTypeQualifiers");
        this.components = components;
        this.typeParameterResolver = typeParameterResolver;
        this.delegateForDefaultTypeQualifiers = delegateForDefaultTypeQualifiers;
        this.defaultTypeQualifiers$delegate = delegateForDefaultTypeQualifiers;
        this.typeResolver = new JavaTypeResolver(this, typeParameterResolver);
    }

    public final JavaResolverComponents getComponents() {
        return this.components;
    }

    public final TypeParameterResolver getTypeParameterResolver() {
        return this.typeParameterResolver;
    }

    public final Lazy<JavaTypeQualifiersByElementType> getDelegateForDefaultTypeQualifiers$descriptors_jvm() {
        return this.delegateForDefaultTypeQualifiers;
    }

    public final JavaTypeResolver getTypeResolver() {
        return this.typeResolver;
    }

    public final StorageManager getStorageManager() {
        return this.components.getStorageManager();
    }

    public final ModuleDescriptor getModule() {
        return this.components.getModule();
    }
}
