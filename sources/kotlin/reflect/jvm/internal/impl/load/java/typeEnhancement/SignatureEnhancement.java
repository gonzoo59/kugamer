package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
/* compiled from: signatureEnhancement.kt */
/* loaded from: classes2.dex */
public final class SignatureEnhancement {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final Jsr305State jsr305State;

    public SignatureEnhancement(AnnotationTypeQualifierResolver annotationTypeQualifierResolver, Jsr305State jsr305State) {
        Intrinsics.checkParameterIsNotNull(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkParameterIsNotNull(jsr305State, "jsr305State");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.jsr305State = jsr305State;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
        ConstantValue<?> firstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptor);
        if (!(firstArgument instanceof EnumValue)) {
            firstArgument = null;
        }
        EnumValue enumValue = (EnumValue) firstArgument;
        if (enumValue == null) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
        }
        String asString = enumValue.getEnumEntryName().asString();
        switch (asString.hashCode()) {
            case 73135176:
                if (!asString.equals("MAYBE")) {
                    return null;
                }
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
                break;
            case 74175084:
                if (!asString.equals("NEVER")) {
                    return null;
                }
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
                break;
            case 433141802:
                if (asString.equals("UNKNOWN")) {
                    nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
                    break;
                } else {
                    return null;
                }
            case 1933739535:
                if (asString.equals("ALWAYS")) {
                    nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
                    break;
                } else {
                    return null;
                }
            default:
                return null;
        }
        return nullabilityQualifierWithMigrationStatus;
    }

    public final NullabilityQualifierWithMigrationStatus extractNullability(AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations;
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations2 = extractNullabilityFromKnownAnnotations(annotationDescriptor);
        if (extractNullabilityFromKnownAnnotations2 != null) {
            return extractNullabilityFromKnownAnnotations2;
        }
        AnnotationDescriptor resolveTypeQualifierAnnotation = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (resolveTypeQualifierAnnotation != null) {
            ReportLevel resolveJsr305AnnotationState = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
            if (resolveJsr305AnnotationState.isIgnore() || (extractNullabilityFromKnownAnnotations = extractNullabilityFromKnownAnnotations(resolveTypeQualifierAnnotation)) == null) {
                return null;
            }
            return NullabilityQualifierWithMigrationStatus.copy$default(extractNullabilityFromKnownAnnotations, null, resolveJsr305AnnotationState.isWarning(), 1, null);
        }
        return null;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
        FqName fqName = annotationDescriptor.getFqName();
        if (fqName != null) {
            if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(fqName)) {
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
            } else if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(fqName)) {
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
            } else if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
                nullabilityQualifierWithMigrationStatus = extractNullabilityTypeFromArgument(annotationDescriptor);
            } else if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations()) {
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
            } else if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations()) {
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
            } else if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
                nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
            } else {
                nullabilityQualifierWithMigrationStatus = Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION()) ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true) : null;
            }
            if (nullabilityQualifierWithMigrationStatus != null) {
                return (!nullabilityQualifierWithMigrationStatus.isForWarningOnly() && (annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) && ((PossiblyExternalAnnotationDescriptor) annotationDescriptor).isIdeExternalAnnotation()) ? NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatus, null, true, 1, null) : nullabilityQualifierWithMigrationStatus;
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(LazyJavaResolverContext c, Collection<? extends D> platformSignatures) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(platformSignatures, "platformSignatures");
        Collection<? extends D> collection = platformSignatures;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(enhanceSignature((CallableMemberDescriptor) it.next(), c));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0205 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x023c A[LOOP:1: B:131:0x0236->B:133:0x023c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final <D extends kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor> D enhanceSignature(D r17, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r18) {
        /*
            Method dump skipped, instructions count: 618
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.enhanceSignature(kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext):kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor");
    }

    private final boolean hasDefaultValueInAnnotation(ValueParameterDescriptor valueParameterDescriptor, KotlinType kotlinType) {
        boolean declaresDefaultValue;
        AnnotationDefaultValue defaultValueFromAnnotation = UtilKt.getDefaultValueFromAnnotation(valueParameterDescriptor);
        if (defaultValueFromAnnotation instanceof StringDefaultValue) {
            declaresDefaultValue = UtilsKt.lexicalCastFrom(kotlinType, ((StringDefaultValue) defaultValueFromAnnotation).getValue()) != null;
        } else if (Intrinsics.areEqual(defaultValueFromAnnotation, NullDefaultValue.INSTANCE)) {
            declaresDefaultValue = TypeUtils.acceptsNullable(kotlinType);
        } else if (defaultValueFromAnnotation == null) {
            declaresDefaultValue = valueParameterDescriptor.declaresDefaultValue();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return declaresDefaultValue && valueParameterDescriptor.getOverriddenDescriptors().isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: signatureEnhancement.kt */
    /* loaded from: classes2.dex */
    public final class SignatureParts {
        private final AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType;
        private final LazyJavaResolverContext containerContext;
        private final Collection<KotlinType> fromOverridden;
        private final KotlinType fromOverride;
        private final boolean isCovariant;
        final /* synthetic */ SignatureEnhancement this$0;
        private final Annotated typeContainer;

        /* JADX WARN: Multi-variable type inference failed */
        public SignatureParts(SignatureEnhancement signatureEnhancement, Annotated annotated, KotlinType fromOverride, Collection<? extends KotlinType> fromOverridden, boolean z, LazyJavaResolverContext containerContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType) {
            Intrinsics.checkParameterIsNotNull(fromOverride, "fromOverride");
            Intrinsics.checkParameterIsNotNull(fromOverridden, "fromOverridden");
            Intrinsics.checkParameterIsNotNull(containerContext, "containerContext");
            Intrinsics.checkParameterIsNotNull(containerApplicabilityType, "containerApplicabilityType");
            this.this$0 = signatureEnhancement;
            this.typeContainer = annotated;
            this.fromOverride = fromOverride;
            this.fromOverridden = fromOverridden;
            this.isCovariant = z;
            this.containerContext = containerContext;
            this.containerApplicabilityType = containerApplicabilityType;
        }

        private final boolean isForVarargParameter() {
            Annotated annotated = this.typeContainer;
            if (!(annotated instanceof ValueParameterDescriptor)) {
                annotated = null;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) annotated;
            return (valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null;
        }

        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                typeEnhancementInfo = null;
            }
            return signatureParts.enhance(typeEnhancementInfo);
        }

        public final PartEnhancementResult enhance(final TypeEnhancementInfo typeEnhancementInfo) {
            final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride = computeIndexedQualifiersForOverride();
            Function1<Integer, JavaTypeQualifiers> function1 = typeEnhancementInfo != null ? new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$$inlined$let$lambda$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final JavaTypeQualifiers invoke(int i) {
                    JavaTypeQualifiers javaTypeQualifiers = TypeEnhancementInfo.this.getMap().get(Integer.valueOf(i));
                    return javaTypeQualifiers != null ? javaTypeQualifiers : (JavaTypeQualifiers) computeIndexedQualifiersForOverride.invoke(Integer.valueOf(i));
                }
            } : null;
            boolean contains = TypeUtils.contains(this.fromOverride, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$containsFunctionN$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                    return Boolean.valueOf(invoke2(unwrappedType));
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final boolean invoke2(UnwrappedType unwrappedType) {
                    ClassifierDescriptor mo1092getDeclarationDescriptor = unwrappedType.getConstructor().mo1092getDeclarationDescriptor();
                    if (mo1092getDeclarationDescriptor != null) {
                        Intrinsics.checkExpressionValueIsNotNull(mo1092getDeclarationDescriptor, "it.constructor.declarati… ?: return@contains false");
                        return Intrinsics.areEqual(mo1092getDeclarationDescriptor.getName(), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME().shortName()) && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(mo1092getDeclarationDescriptor), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME());
                    }
                    return false;
                }
            });
            KotlinType kotlinType = this.fromOverride;
            if (function1 != null) {
                computeIndexedQualifiersForOverride = function1;
            }
            KotlinType enhance = TypeEnhancementKt.enhance(kotlinType, computeIndexedQualifiersForOverride);
            if (enhance != null) {
                return new PartEnhancementResult(enhance, true, contains);
            }
            return new PartEnhancementResult(this.fromOverride, false, contains);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType r12) {
            /*
                r11 = this;
                boolean r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.isFlexible(r12)
                if (r0 == 0) goto L18
                kotlin.reflect.jvm.internal.impl.types.FlexibleType r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.asFlexibleType(r12)
                kotlin.Pair r1 = new kotlin.Pair
                kotlin.reflect.jvm.internal.impl.types.SimpleType r2 = r0.getLowerBound()
                kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r0.getUpperBound()
                r1.<init>(r2, r0)
                goto L1d
            L18:
                kotlin.Pair r1 = new kotlin.Pair
                r1.<init>(r12, r12)
            L1d:
                java.lang.Object r0 = r1.component1()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r0
                java.lang.Object r1 = r1.component2()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r1
                kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap r2 = kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap.INSTANCE
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers
                boolean r3 = r0.isMarkedNullable()
                r4 = 0
                if (r3 == 0) goto L38
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NULLABLE
            L36:
                r5 = r3
                goto L42
            L38:
                boolean r3 = r1.isMarkedNullable()
                if (r3 != 0) goto L41
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL
                goto L36
            L41:
                r5 = r4
            L42:
                boolean r0 = r2.isReadOnly(r0)
                if (r0 == 0) goto L4b
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
                goto L55
            L4b:
                boolean r0 = r2.isMutable(r1)
                if (r0 == 0) goto L54
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
                goto L55
            L54:
                r0 = r4
            L55:
                kotlin.reflect.jvm.internal.impl.types.UnwrappedType r12 = r12.unwrap()
                boolean r6 = r12 instanceof kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NotNullTypeParameter
                r7 = 0
                r8 = 8
                r9 = 0
                r3 = r10
                r4 = r5
                r5 = r0
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }

        private final JavaTypeQualifiers extractQualifiersFromAnnotations(KotlinType kotlinType, boolean z, JavaTypeQualifiers javaTypeQualifiers) {
            final Annotations annotations;
            Annotated annotated;
            if (z && (annotated = this.typeContainer) != null) {
                annotations = AnnotationsKt.composeAnnotations(annotated.getAnnotations(), kotlinType.getAnnotations());
            } else {
                annotations = kotlinType.getAnnotations();
            }
            Function2<List<? extends FqName>, T, T> function2 = new Function2<List<? extends FqName>, T, T>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(2);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(List<? extends FqName> list, Object obj) {
                    return invoke2((List<FqName>) list, (List<? extends FqName>) obj);
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final <T> T invoke2(List<FqName> ifPresent, T qualifier) {
                    boolean z2;
                    Intrinsics.checkParameterIsNotNull(ifPresent, "$this$ifPresent");
                    Intrinsics.checkParameterIsNotNull(qualifier, "qualifier");
                    List<FqName> list = ifPresent;
                    boolean z3 = true;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        for (FqName fqName : list) {
                            if (Annotations.this.mo1087findAnnotation(fqName) != null) {
                                z2 = true;
                                continue;
                            } else {
                                z2 = false;
                                continue;
                            }
                            if (z2) {
                                break;
                            }
                        }
                    }
                    z3 = false;
                    if (z3) {
                        return qualifier;
                    }
                    return null;
                }
            };
            SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2 signatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2 = SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2.INSTANCE;
            if (z) {
                JavaTypeQualifiersByElementType defaultTypeQualifiers = this.containerContext.getDefaultTypeQualifiers();
                javaTypeQualifiers = defaultTypeQualifiers != null ? defaultTypeQualifiers.get(this.containerApplicabilityType) : null;
            }
            NullabilityQualifierWithMigrationStatus extractNullability = extractNullability(annotations);
            if (extractNullability == null) {
                extractNullability = (javaTypeQualifiers == null || javaTypeQualifiers.getNullability() == null) ? null : new NullabilityQualifierWithMigrationStatus(javaTypeQualifiers.getNullability(), javaTypeQualifiers.isNullabilityQualifierForWarning());
            }
            NullabilityQualifier qualifier = extractNullability != null ? extractNullability.getQualifier() : null;
            MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) signatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2.invoke(function2.invoke2(JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS(), (List<FqName>) MutabilityQualifier.READ_ONLY), function2.invoke2(JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS(), (List<FqName>) MutabilityQualifier.MUTABLE));
            boolean z2 = false;
            boolean z3 = (extractNullability != null ? extractNullability.getQualifier() : null) == NullabilityQualifier.NOT_NULL && TypeUtilsKt.isTypeParameter(kotlinType);
            if (extractNullability != null && extractNullability.isForWarningOnly()) {
                z2 = true;
            }
            return new JavaTypeQualifiers(qualifier, mutabilityQualifier, z3, z2);
        }

        private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations annotations) {
            SignatureEnhancement signatureEnhancement = this.this$0;
            Iterator<AnnotationDescriptor> it = annotations.iterator();
            while (it.hasNext()) {
                NullabilityQualifierWithMigrationStatus extractNullability = signatureEnhancement.extractNullability(it.next());
                if (extractNullability != null) {
                    return extractNullability;
                }
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x006f  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0078  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers> computeIndexedQualifiersForOverride() {
            /*
                r14 = this;
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.KotlinType> r0 = r14.fromOverridden
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                java.util.ArrayList r1 = new java.util.ArrayList
                r2 = 10
                int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r0, r2)
                r1.<init>(r2)
                java.util.Collection r1 = (java.util.Collection) r1
                java.util.Iterator r0 = r0.iterator()
            L15:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L29
                java.lang.Object r2 = r0.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r2 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r2
                java.util.List r2 = r14.toIndexed(r2)
                r1.add(r2)
                goto L15
            L29:
                java.util.List r1 = (java.util.List) r1
                kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = r14.fromOverride
                java.util.List r0 = r14.toIndexed(r0)
                boolean r2 = r14.isCovariant
                r3 = 0
                r4 = 1
                if (r2 == 0) goto L6a
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.KotlinType> r2 = r14.fromOverridden
                java.lang.Iterable r2 = (java.lang.Iterable) r2
                boolean r5 = r2 instanceof java.util.Collection
                if (r5 == 0) goto L4a
                r5 = r2
                java.util.Collection r5 = (java.util.Collection) r5
                boolean r5 = r5.isEmpty()
                if (r5 == 0) goto L4a
            L48:
                r2 = 0
                goto L66
            L4a:
                java.util.Iterator r2 = r2.iterator()
            L4e:
                boolean r5 = r2.hasNext()
                if (r5 == 0) goto L48
                java.lang.Object r5 = r2.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r5 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r5
                kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker r6 = kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.DEFAULT
                kotlin.reflect.jvm.internal.impl.types.KotlinType r7 = r14.fromOverride
                boolean r5 = r6.equalTypes(r5, r7)
                r5 = r5 ^ r4
                if (r5 == 0) goto L4e
                r2 = 1
            L66:
                if (r2 == 0) goto L6a
                r2 = 1
                goto L6b
            L6a:
                r2 = 0
            L6b:
                if (r2 == 0) goto L6f
                r5 = 1
                goto L73
            L6f:
                int r5 = r0.size()
            L73:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers[] r6 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers[r5]
                r7 = 0
            L76:
                if (r7 >= r5) goto Lc6
                if (r7 != 0) goto L7c
                r8 = 1
                goto L7d
            L7c:
                r8 = 0
            L7d:
                java.lang.Object r9 = r0.get(r7)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers r9 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers) r9
                kotlin.reflect.jvm.internal.impl.types.KotlinType r10 = r9.component1()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r9 = r9.component2()
                r11 = r1
                java.lang.Iterable r11 = (java.lang.Iterable) r11
                java.util.ArrayList r12 = new java.util.ArrayList
                r12.<init>()
                java.util.Collection r12 = (java.util.Collection) r12
                java.util.Iterator r11 = r11.iterator()
            L99:
                boolean r13 = r11.hasNext()
                if (r13 == 0) goto Lb9
                java.lang.Object r13 = r11.next()
                java.util.List r13 = (java.util.List) r13
                java.lang.Object r13 = kotlin.collections.CollectionsKt.getOrNull(r13, r7)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers r13 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers) r13
                if (r13 == 0) goto Lb2
                kotlin.reflect.jvm.internal.impl.types.KotlinType r13 = r13.getType()
                goto Lb3
            Lb2:
                r13 = 0
            Lb3:
                if (r13 == 0) goto L99
                r12.add(r13)
                goto L99
            Lb9:
                java.util.List r12 = (java.util.List) r12
                java.util.Collection r12 = (java.util.Collection) r12
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r8 = r14.computeQualifiersForOverride(r10, r12, r9, r8)
                r6[r7] = r8
                int r7 = r7 + 1
                goto L76
            Lc6:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1 r0 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1
                r0.<init>()
                kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeIndexedQualifiersForOverride():kotlin.jvm.functions.Function1");
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType kotlinType) {
            final ArrayList arrayList = new ArrayList(1);
            new Function2<KotlinType, LazyJavaResolverContext, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$toIndexed$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType2, LazyJavaResolverContext lazyJavaResolverContext) {
                    invoke2(kotlinType2, lazyJavaResolverContext);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(KotlinType type, LazyJavaResolverContext ownerContext) {
                    Intrinsics.checkParameterIsNotNull(type, "type");
                    Intrinsics.checkParameterIsNotNull(ownerContext, "ownerContext");
                    LazyJavaResolverContext copyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(ownerContext, type.getAnnotations());
                    ArrayList arrayList2 = arrayList;
                    JavaTypeQualifiersByElementType defaultTypeQualifiers = copyWithNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
                    arrayList2.add(new TypeAndDefaultQualifiers(type, defaultTypeQualifiers != null ? defaultTypeQualifiers.get(AnnotationTypeQualifierResolver.QualifierApplicabilityType.TYPE_USE) : null));
                    for (TypeProjection typeProjection : type.getArguments()) {
                        if (typeProjection.isStarProjection()) {
                            ArrayList arrayList3 = arrayList;
                            KotlinType type2 = typeProjection.getType();
                            Intrinsics.checkExpressionValueIsNotNull(type2, "arg.type");
                            arrayList3.add(new TypeAndDefaultQualifiers(type2, null));
                        } else {
                            KotlinType type3 = typeProjection.getType();
                            Intrinsics.checkExpressionValueIsNotNull(type3, "arg.type");
                            invoke2(type3, copyWithNewDefaultTypeQualifiers);
                        }
                    }
                }
            }.invoke2(kotlinType, this.containerContext);
            return arrayList;
        }

        /* JADX WARN: Removed duplicated region for block: B:77:0x0149  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType r11, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.types.KotlinType> r12, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r13, boolean r14) {
            /*
                Method dump skipped, instructions count: 335
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType, java.util.Collection, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: signatureEnhancement.kt */
    /* loaded from: classes2.dex */
    public static class PartEnhancementResult {
        private final boolean containsFunctionN;
        private final KotlinType type;
        private final boolean wereChanges;

        public PartEnhancementResult(KotlinType type, boolean z, boolean z2) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.type = type;
            this.wereChanges = z;
            this.containsFunctionN = z2;
        }

        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: signatureEnhancement.kt */
    /* loaded from: classes2.dex */
    public static final class ValueParameterEnhancementResult extends PartEnhancementResult {
        private final boolean hasDefaultValue;

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueParameterEnhancementResult(KotlinType type, boolean z, boolean z2, boolean z3) {
            super(type, z2, z3);
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.hasDefaultValue = z;
        }
    }

    private final SignatureParts partsForValueParameter(CallableMemberDescriptor callableMemberDescriptor, ValueParameterDescriptor valueParameterDescriptor, LazyJavaResolverContext lazyJavaResolverContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        LazyJavaResolverContext copyWithNewDefaultTypeQualifiers;
        return parts(callableMemberDescriptor, valueParameterDescriptor, false, (valueParameterDescriptor == null || (copyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, valueParameterDescriptor.getAnnotations())) == null) ? lazyJavaResolverContext : copyWithNewDefaultTypeQualifiers, AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER, function1);
    }

    private final SignatureParts parts(CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType qualifierApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        KotlinType invoke = function1.invoke(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "this.overriddenDescriptors");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (CallableMemberDescriptor it : collection) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            arrayList.add(function1.invoke(it));
        }
        return new SignatureParts(this, annotated, invoke, arrayList, z, ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, function1.invoke(callableMemberDescriptor).getAnnotations()), qualifierApplicabilityType);
    }
}
