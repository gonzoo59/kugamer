package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
/* compiled from: ModuleDescriptor.kt */
/* loaded from: classes2.dex */
public interface ModuleDescriptor extends DeclarationDescriptor {
    KotlinBuiltIns getBuiltIns();

    <T> T getCapability(Capability<T> capability);

    List<ModuleDescriptor> getExpectedByModules();

    PackageViewDescriptor getPackage(FqName fqName);

    Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> function1);

    boolean shouldSeeInternalsOf(ModuleDescriptor moduleDescriptor);

    /* compiled from: ModuleDescriptor.kt */
    /* loaded from: classes2.dex */
    public static final class DefaultImpls {
        public static DeclarationDescriptor getContainingDeclaration(ModuleDescriptor moduleDescriptor) {
            return null;
        }

        public static <R, D> R accept(ModuleDescriptor moduleDescriptor, DeclarationDescriptorVisitor<R, D> visitor, D d) {
            Intrinsics.checkParameterIsNotNull(visitor, "visitor");
            return visitor.visitModuleDeclaration(moduleDescriptor, d);
        }
    }

    /* compiled from: ModuleDescriptor.kt */
    /* loaded from: classes2.dex */
    public static final class Capability<T> {
        private final String name;

        public Capability(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}
