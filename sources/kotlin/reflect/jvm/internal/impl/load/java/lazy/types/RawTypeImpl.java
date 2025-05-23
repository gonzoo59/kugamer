package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
/* compiled from: RawType.kt */
/* loaded from: classes2.dex */
public final class RawTypeImpl extends FlexibleType implements RawType {
    private RawTypeImpl(SimpleType simpleType, SimpleType simpleType2, boolean z) {
        super(simpleType, simpleType2);
        if (z) {
            return;
        }
        KotlinTypeChecker.DEFAULT.isSubtypeOf(simpleType, simpleType2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RawTypeImpl(SimpleType lowerBound, SimpleType upperBound) {
        this(lowerBound, upperBound, false);
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    public SimpleType getDelegate() {
        return getLowerBound();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public MemberScope getMemberScope() {
        ClassifierDescriptor mo1092getDeclarationDescriptor = getConstructor().mo1092getDeclarationDescriptor();
        if (!(mo1092getDeclarationDescriptor instanceof ClassDescriptor)) {
            mo1092getDeclarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) mo1092getDeclarationDescriptor;
        if (classDescriptor == null) {
            throw new IllegalStateException(("Incorrect classifier: " + getConstructor().mo1092getDeclarationDescriptor()).toString());
        }
        MemberScope memberScope = classDescriptor.getMemberScope(RawSubstitution.INSTANCE);
        Intrinsics.checkExpressionValueIsNotNull(memberScope, "classDescriptor.getMemberScope(RawSubstitution)");
        return memberScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public RawTypeImpl replaceAnnotations(Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        return new RawTypeImpl(getLowerBound().replaceAnnotations(newAnnotations), getUpperBound().replaceAnnotations(newAnnotations));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    public RawTypeImpl makeNullableAsSpecified(boolean z) {
        return new RawTypeImpl(getLowerBound().makeNullableAsSpecified(z), getUpperBound().makeNullableAsSpecified(z));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    public String render(final DescriptorRenderer renderer, DescriptorRendererOptions options) {
        Intrinsics.checkParameterIsNotNull(renderer, "renderer");
        Intrinsics.checkParameterIsNotNull(options, "options");
        RawTypeImpl$render$1 rawTypeImpl$render$1 = RawTypeImpl$render$1.INSTANCE;
        Function1<KotlinType, List<? extends String>> function1 = new Function1<KotlinType, List<? extends String>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<String> invoke(KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                List<TypeProjection> arguments = type.getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                for (TypeProjection typeProjection : arguments) {
                    arrayList.add(DescriptorRenderer.this.renderTypeProjection(typeProjection));
                }
                return arrayList;
            }
        };
        RawTypeImpl$render$3 rawTypeImpl$render$3 = RawTypeImpl$render$3.INSTANCE;
        String renderType = renderer.renderType(getLowerBound());
        String renderType2 = renderer.renderType(getUpperBound());
        if (options.getDebugMode()) {
            return "raw (" + renderType + ".." + renderType2 + ')';
        } else if (getUpperBound().getArguments().isEmpty()) {
            return renderer.renderFlexibleType(renderType, renderType2, TypeUtilsKt.getBuiltIns(this));
        } else {
            List<String> invoke = function1.invoke((KotlinType) getLowerBound());
            List<String> invoke2 = function1.invoke((KotlinType) getUpperBound());
            List<String> list = invoke;
            String joinToString$default = CollectionsKt.joinToString$default(list, ", ", null, null, 0, null, new Function1<String, String>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$newArgs$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(String it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return "(raw) " + it;
                }
            }, 30, null);
            List zip = CollectionsKt.zip(list, invoke2);
            boolean z = true;
            if (!(zip instanceof Collection) || !zip.isEmpty()) {
                Iterator it = zip.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Pair pair = (Pair) it.next();
                    if (!RawTypeImpl$render$1.INSTANCE.invoke2((String) pair.getFirst(), (String) pair.getSecond())) {
                        z = false;
                        break;
                    }
                }
            }
            if (z) {
                renderType2 = rawTypeImpl$render$3.invoke(renderType2, joinToString$default);
            }
            String invoke3 = rawTypeImpl$render$3.invoke(renderType, joinToString$default);
            return Intrinsics.areEqual(invoke3, renderType2) ? invoke3 : renderer.renderFlexibleType(invoke3, renderType2, TypeUtilsKt.getBuiltIns(this));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public FlexibleType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkParameterIsNotNull(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType refineType = kotlinTypeRefiner.refineType(getLowerBound());
        if (refineType == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        SimpleType simpleType = (SimpleType) refineType;
        KotlinType refineType2 = kotlinTypeRefiner.refineType(getUpperBound());
        if (refineType2 != null) {
            return new RawTypeImpl(simpleType, (SimpleType) refineType2, true);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
}
