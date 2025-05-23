package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnnotationTypeQualifierResolver.kt */
/* loaded from: classes2.dex */
public final /* synthetic */ class AnnotationTypeQualifierResolver$resolvedNicknames$1 extends FunctionReference implements Function1<ClassDescriptor, AnnotationDescriptor> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationTypeQualifierResolver$resolvedNicknames$1(AnnotationTypeQualifierResolver annotationTypeQualifierResolver) {
        super(1, annotationTypeQualifierResolver);
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "computeTypeQualifierNickname";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AnnotationTypeQualifierResolver.class);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "computeTypeQualifierNickname(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;";
    }

    @Override // kotlin.jvm.functions.Function1
    public final AnnotationDescriptor invoke(ClassDescriptor p1) {
        AnnotationDescriptor computeTypeQualifierNickname;
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        computeTypeQualifierNickname = ((AnnotationTypeQualifierResolver) this.receiver).computeTypeQualifierNickname(p1);
        return computeTypeQualifierNickname;
    }
}
