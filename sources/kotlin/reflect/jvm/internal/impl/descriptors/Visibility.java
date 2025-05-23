package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
/* compiled from: Visibility.kt */
/* loaded from: classes2.dex */
public abstract class Visibility {
    private final boolean isPublicAPI;
    private final String name;

    public abstract boolean isVisible(ReceiverValue receiverValue, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, DeclarationDescriptor declarationDescriptor);

    public Visibility normalize() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Visibility(String name, boolean z) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.name = name;
        this.isPublicAPI = z;
    }

    public final boolean isPublicAPI() {
        return this.isPublicAPI;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Integer compareTo(Visibility visibility) {
        Intrinsics.checkParameterIsNotNull(visibility, "visibility");
        return Visibilities.compareLocal(this, visibility);
    }

    public String getInternalDisplayName() {
        return this.name;
    }

    public final String toString() {
        return getInternalDisplayName();
    }
}
