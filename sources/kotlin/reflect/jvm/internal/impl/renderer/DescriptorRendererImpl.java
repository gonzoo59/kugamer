package kotlin.reflect.jvm.internal.impl.renderer;

import com.baidu.kwgames.GameSettingFloat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnresolvedType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
/* compiled from: DescriptorRendererImpl.kt */
/* loaded from: classes2.dex */
public final class DescriptorRendererImpl extends DescriptorRenderer implements DescriptorRendererOptions {
    private final Lazy functionTypeAnnotationsRenderer$delegate;
    private final Lazy functionTypeParameterTypesRenderer$delegate;
    private final DescriptorRendererOptionsImpl options;

    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;

        static {
            int[] iArr = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[RenderingFormat.PLAIN.ordinal()] = 1;
            iArr[RenderingFormat.HTML.ordinal()] = 2;
            int[] iArr2 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[RenderingFormat.PLAIN.ordinal()] = 1;
            iArr2[RenderingFormat.HTML.ordinal()] = 2;
            int[] iArr3 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$2 = iArr3;
            iArr3[RenderingFormat.PLAIN.ordinal()] = 1;
            iArr3[RenderingFormat.HTML.ordinal()] = 2;
            int[] iArr4 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$3 = iArr4;
            iArr4[RenderingFormat.PLAIN.ordinal()] = 1;
            iArr4[RenderingFormat.HTML.ordinal()] = 2;
            int[] iArr5 = new int[ParameterNameRenderingPolicy.values().length];
            $EnumSwitchMapping$4 = iArr5;
            iArr5[ParameterNameRenderingPolicy.ALL.ordinal()] = 1;
            iArr5[ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
            iArr5[ParameterNameRenderingPolicy.NONE.ordinal()] = 3;
        }
    }

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        return (DescriptorRendererImpl) this.functionTypeAnnotationsRenderer$delegate.getValue();
    }

    private final DescriptorRenderer getFunctionTypeParameterTypesRenderer() {
        return (DescriptorRenderer) this.functionTypeParameterTypesRenderer$delegate.getValue();
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    public boolean getInformativeErrorType() {
        return this.options.getInformativeErrorType();
    }

    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkParameterIsNotNull(annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkParameterIsNotNull(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z) {
        this.options.setDebugMode(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkParameterIsNotNull(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setModifiers(Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkParameterIsNotNull(set, "<set-?>");
        this.options.setModifiers(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkParameterIsNotNull(parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z) {
        this.options.setReceiverAfterName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z) {
        this.options.setRenderCompanionObjectName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z) {
        this.options.setStartFromName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setTextFormat(RenderingFormat renderingFormat) {
        Intrinsics.checkParameterIsNotNull(renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z) {
        this.options.setVerbose(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z) {
        this.options.setWithDefinedIn(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z) {
        this.options.setWithoutSuperTypes(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z) {
        this.options.setWithoutTypeParameters(z);
    }

    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    public DescriptorRendererImpl(DescriptorRendererOptionsImpl options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        this.options = options;
        options.isLocked();
        this.functionTypeAnnotationsRenderer$delegate = LazyKt.lazy(new Function0<DescriptorRendererImpl>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DescriptorRendererImpl invoke() {
                DescriptorRenderer withOptions = DescriptorRendererImpl.this.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void invoke2(DescriptorRendererOptions receiver) {
                        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
                        receiver.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) receiver.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf(KotlinBuiltIns.FQ_NAMES.extensionFunctionType)));
                        receiver.setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy.ALWAYS_PARENTHESIZED);
                    }
                });
                if (withOptions != null) {
                    return (DescriptorRendererImpl) withOptions;
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
            }
        });
        this.functionTypeParameterTypesRenderer$delegate = LazyKt.lazy(new Function0<DescriptorRenderer>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeParameterTypesRenderer$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DescriptorRenderer invoke() {
                return DescriptorRendererImpl.this.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeParameterTypesRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void invoke2(DescriptorRendererOptions receiver) {
                        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
                        receiver.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) receiver.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf(KotlinBuiltIns.FQ_NAMES.parameterName)));
                    }
                });
            }
        });
    }

    private final String renderKeyword(String str) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i != 1) {
            if (i == 2) {
                if (getBoldOnlyForNamesInHtml()) {
                    return str;
                }
                return "<b>" + str + "</b>";
            }
            throw new NoWhenBranchMatchedException();
        }
        return str;
    }

    private final String renderError(String str) {
        int i = WhenMappings.$EnumSwitchMapping$1[getTextFormat().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return "<font color=red><b>" + str + "</b></font>";
            }
            throw new NoWhenBranchMatchedException();
        }
        return str;
    }

    private final String escape(String str) {
        return getTextFormat().escape(str);
    }

    private final String lt() {
        return escape("<");
    }

    private final String gt() {
        return escape(">");
    }

    private final String arrow() {
        int i = WhenMappings.$EnumSwitchMapping$2[getTextFormat().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return "&rarr;";
            }
            throw new NoWhenBranchMatchedException();
        }
        return escape("->");
    }

    public String renderMessage(String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        int i = WhenMappings.$EnumSwitchMapping$3[getTextFormat().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return "<i>" + message + "</i>";
            }
            throw new NoWhenBranchMatchedException();
        }
        return message;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderName(Name name, boolean z) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String escape = escape(RenderingUtilsKt.render(name));
        if (getBoldOnlyForNamesInHtml() && getTextFormat() == RenderingFormat.HTML && z) {
            return "<b>" + escape + "</b>";
        }
        return escape;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderName(DeclarationDescriptor declarationDescriptor, StringBuilder sb, boolean z) {
        Name name = declarationDescriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "descriptor.name");
        sb.append(renderName(name, z));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor declarationDescriptor, StringBuilder sb) {
        if (getRenderCompanionObjectName()) {
            if (getStartFromName()) {
                sb.append("companion object");
            }
            renderSpaceIfNeeded(sb);
            DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
            if (containingDeclaration != null) {
                sb.append("of ");
                Name name = containingDeclaration.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "containingDeclaration.name");
                sb.append(renderName(name, false));
            }
        }
        if (getVerbose() || (!Intrinsics.areEqual(declarationDescriptor.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT))) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            Name name2 = declarationDescriptor.getName();
            Intrinsics.checkExpressionValueIsNotNull(name2, "descriptor.name");
            sb.append(renderName(name2, true));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderFqName(FqNameUnsafe fqName) {
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        List<Name> pathSegments = fqName.pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(pathSegments, "fqName.pathSegments()");
        return renderFqName(pathSegments);
    }

    private final String renderFqName(List<Name> list) {
        return escape(RenderingUtilsKt.renderFqName(list));
    }

    public String renderClassifierName(ClassifierDescriptor klass) {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        if (ErrorUtils.isError(klass)) {
            return klass.getTypeConstructor().toString();
        }
        return getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderType(KotlinType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        StringBuilder sb = new StringBuilder();
        renderNormalizedType(sb, getTypeNormalizer().invoke(type));
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final void renderNormalizedType(StringBuilder sb, KotlinType kotlinType) {
        UnwrappedType unwrap = kotlinType.unwrap();
        if (!(unwrap instanceof AbbreviatedType)) {
            unwrap = null;
        }
        AbbreviatedType abbreviatedType = (AbbreviatedType) unwrap;
        if (abbreviatedType != null) {
            if (getRenderTypeExpansions()) {
                renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
                return;
            }
            renderNormalizedTypeAsIs(sb, abbreviatedType.getAbbreviation());
            if (getRenderUnabbreviatedType()) {
                renderAbbreviatedTypeExpansion(sb, abbreviatedType);
                return;
            }
            return;
        }
        renderNormalizedTypeAsIs(sb, kotlinType);
    }

    private final void renderAbbreviatedTypeExpansion(StringBuilder sb, AbbreviatedType abbreviatedType) {
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("<font color=\"808080\"><i>");
        }
        sb.append(" /* = ");
        renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
        sb.append(" */");
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("</i></font>");
        }
    }

    private final void renderNormalizedTypeAsIs(StringBuilder sb, KotlinType kotlinType) {
        if ((kotlinType instanceof WrappedType) && getDebugMode() && !((WrappedType) kotlinType).isComputed()) {
            sb.append("<Not computed yet>");
            return;
        }
        UnwrappedType unwrap = kotlinType.unwrap();
        if (unwrap instanceof FlexibleType) {
            sb.append(((FlexibleType) unwrap).render(this, this));
        } else if (unwrap instanceof SimpleType) {
            renderSimpleType(sb, (SimpleType) unwrap);
        }
    }

    private final void renderSimpleType(StringBuilder sb, SimpleType simpleType) {
        if (!Intrinsics.areEqual(simpleType, TypeUtils.CANT_INFER_FUNCTION_PARAM_TYPE)) {
            SimpleType simpleType2 = simpleType;
            if (!TypeUtils.isDontCarePlaceholder(simpleType2)) {
                if (ErrorUtils.isUninferredParameter(simpleType2)) {
                    if (getUninferredTypeParameterAsName()) {
                        TypeConstructor constructor = simpleType.getConstructor();
                        if (constructor == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.ErrorUtils.UninferredParameterTypeConstructor");
                        }
                        TypeParameterDescriptor typeParameterDescriptor = ((ErrorUtils.UninferredParameterTypeConstructor) constructor).getTypeParameterDescriptor();
                        Intrinsics.checkExpressionValueIsNotNull(typeParameterDescriptor, "(type.constructor as Uni…).typeParameterDescriptor");
                        String name = typeParameterDescriptor.getName().toString();
                        Intrinsics.checkExpressionValueIsNotNull(name, "(type.constructor as Uni…escriptor.name.toString()");
                        sb.append(renderError(name));
                        return;
                    }
                    sb.append("???");
                    return;
                } else if (KotlinTypeKt.isError(simpleType2)) {
                    renderDefaultType(sb, simpleType2);
                    return;
                } else if (shouldRenderAsPrettyFunctionType(simpleType2)) {
                    renderFunctionType(sb, simpleType2);
                    return;
                } else {
                    renderDefaultType(sb, simpleType2);
                    return;
                }
            }
        }
        sb.append("???");
    }

    private final boolean shouldRenderAsPrettyFunctionType(KotlinType kotlinType) {
        boolean z;
        if (FunctionTypesKt.isBuiltinFunctionalType(kotlinType)) {
            List<TypeProjection> arguments = kotlinType.getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                for (TypeProjection typeProjection : arguments) {
                    if (typeProjection.isStarProjection()) {
                        z = false;
                        break;
                    }
                }
            }
            z = true;
            return z;
        }
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderFlexibleType(String lowerRendered, String upperRendered, KotlinBuiltIns builtIns) {
        Intrinsics.checkParameterIsNotNull(lowerRendered, "lowerRendered");
        Intrinsics.checkParameterIsNotNull(upperRendered, "upperRendered");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        if (differsOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, (Object) null)) {
                return '(' + lowerRendered + ")!";
            }
            return lowerRendered + '!';
        }
        ClassifierNamePolicy classifierNamePolicy = getClassifierNamePolicy();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkExpressionValueIsNotNull(collection, "builtIns.collection");
        DescriptorRendererImpl descriptorRendererImpl = this;
        String substringBefore$default = StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(collection, descriptorRendererImpl), "Collection", (String) null, 2, (Object) null);
        String replacePrefixes = replacePrefixes(lowerRendered, substringBefore$default + "Mutable", upperRendered, substringBefore$default, substringBefore$default + "(Mutable)");
        if (replacePrefixes != null) {
            return replacePrefixes;
        }
        String replacePrefixes2 = replacePrefixes(lowerRendered, substringBefore$default + "MutableMap.MutableEntry", upperRendered, substringBefore$default + "Map.Entry", substringBefore$default + "(Mutable)Map.(Mutable)Entry");
        if (replacePrefixes2 != null) {
            return replacePrefixes2;
        }
        ClassifierNamePolicy classifierNamePolicy2 = getClassifierNamePolicy();
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkExpressionValueIsNotNull(array, "builtIns.array");
        String substringBefore$default2 = StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(array, descriptorRendererImpl), "Array", (String) null, 2, (Object) null);
        String replacePrefixes3 = replacePrefixes(lowerRendered, substringBefore$default2 + escape("Array<"), upperRendered, substringBefore$default2 + escape("Array<out "), substringBefore$default2 + escape("Array<(out) "));
        if (replacePrefixes3 != null) {
            return replacePrefixes3;
        }
        return '(' + lowerRendered + ".." + upperRendered + ')';
    }

    public String renderTypeArguments(List<? extends TypeProjection> typeArguments) {
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lt());
        appendTypeProjections(sb, typeArguments);
        sb.append(gt());
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final void renderDefaultType(StringBuilder sb, KotlinType kotlinType) {
        renderAnnotations$default(this, sb, kotlinType, null, 2, null);
        if (KotlinTypeKt.isError(kotlinType)) {
            if ((kotlinType instanceof UnresolvedType) && getPresentableUnresolvedTypes()) {
                sb.append(((UnresolvedType) kotlinType).getPresentableName());
            } else if ((kotlinType instanceof ErrorType) && !getInformativeErrorType()) {
                sb.append(((ErrorType) kotlinType).getPresentableName());
            } else {
                sb.append(kotlinType.getConstructor().toString());
            }
            sb.append(renderTypeArguments(kotlinType.getArguments()));
        } else {
            renderTypeConstructorAndArguments$default(this, sb, kotlinType, null, 2, null);
        }
        if (kotlinType.isMarkedNullable()) {
            sb.append("?");
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType)) {
            sb.append("!!");
        }
    }

    static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor, int i, Object obj) {
        if ((i & 2) != 0) {
            typeConstructor = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(sb, kotlinType, typeConstructor);
    }

    private final void renderTypeConstructorAndArguments(StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor) {
        PossiblyInnerType buildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(kotlinType);
        if (buildPossiblyInnerType == null) {
            sb.append(renderTypeConstructor(typeConstructor));
            sb.append(renderTypeArguments(kotlinType.getArguments()));
            return;
        }
        renderPossiblyInnerType(sb, buildPossiblyInnerType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0023, code lost:
        if (r3 != null) goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void renderPossiblyInnerType(java.lang.StringBuilder r3, kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType r4) {
        /*
            r2 = this;
            kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType r0 = r4.getOuterType()
            if (r0 == 0) goto L26
            r2.renderPossiblyInnerType(r3, r0)
            r0 = 46
            r3.append(r0)
            kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters r0 = r4.getClassifierDescriptor()
            kotlin.reflect.jvm.internal.impl.name.Name r0 = r0.getName()
            java.lang.String r1 = "possiblyInnerType.classifierDescriptor.name"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            r1 = 0
            java.lang.String r0 = r2.renderName(r0, r1)
            r3.append(r0)
            if (r3 == 0) goto L26
            goto L3a
        L26:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters r0 = r4.getClassifierDescriptor()
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r0 = r0.getTypeConstructor()
            java.lang.String r1 = "possiblyInnerType.classi…escriptor.typeConstructor"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            java.lang.String r0 = r2.renderTypeConstructor(r0)
            r3.append(r0)
        L3a:
            java.util.List r4 = r4.getArguments()
            java.lang.String r4 = r2.renderTypeArguments(r4)
            r3.append(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderPossiblyInnerType(java.lang.StringBuilder, kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType):void");
    }

    public String renderTypeConstructor(TypeConstructor typeConstructor) {
        Intrinsics.checkParameterIsNotNull(typeConstructor, "typeConstructor");
        ClassifierDescriptor mo1092getDeclarationDescriptor = typeConstructor.mo1092getDeclarationDescriptor();
        if ((mo1092getDeclarationDescriptor instanceof TypeParameterDescriptor) || (mo1092getDeclarationDescriptor instanceof ClassDescriptor) || (mo1092getDeclarationDescriptor instanceof TypeAliasDescriptor)) {
            return renderClassifierName(mo1092getDeclarationDescriptor);
        }
        if (mo1092getDeclarationDescriptor == null) {
            return typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + mo1092getDeclarationDescriptor.getClass()).toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderTypeProjection(TypeProjection typeProjection) {
        Intrinsics.checkParameterIsNotNull(typeProjection, "typeProjection");
        StringBuilder sb = new StringBuilder();
        appendTypeProjections(sb, CollectionsKt.listOf(typeProjection));
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final void appendTypeProjections(StringBuilder sb, List<? extends TypeProjection> list) {
        CollectionsKt.joinTo(list, sb, (r15 & 2) != 0 ? ", " : ", ", (r15 & 4) != 0 ? "" : null, (r15 & 8) != 0 ? "" : null, (r15 & 16) != 0 ? -1 : 0, (r15 & 32) != 0 ? "..." : null, (r15 & 64) != 0 ? null : new Function1<TypeProjection, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$appendTypeProjections$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(TypeProjection it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (it.isStarProjection()) {
                    return "*";
                }
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                KotlinType type = it.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                String renderType = descriptorRendererImpl.renderType(type);
                if (it.getProjectionKind() != Variance.INVARIANT) {
                    renderType = it.getProjectionKind() + ' ' + renderType;
                }
                return renderType;
            }
        });
    }

    private final void renderFunctionType(StringBuilder sb, KotlinType kotlinType) {
        Name name;
        int length = sb.length();
        renderAnnotations$default(getFunctionTypeAnnotationsRenderer(), sb, kotlinType, null, 2, null);
        boolean z = true;
        boolean z2 = sb.length() != length;
        boolean isSuspendFunctionType = FunctionTypesKt.isSuspendFunctionType(kotlinType);
        boolean isMarkedNullable = kotlinType.isMarkedNullable();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        boolean z3 = isMarkedNullable || (z2 && receiverTypeFromFunctionType != null);
        if (z3) {
            if (isSuspendFunctionType) {
                sb.insert(length, '(');
            } else {
                if (z2) {
                    StringBuilder sb2 = sb;
                    StringsKt.last(sb2);
                    if (sb.charAt(StringsKt.getLastIndex(sb2) - 1) != ')') {
                        sb.insert(StringsKt.getLastIndex(sb2), "()");
                    }
                }
                sb.append("(");
            }
        }
        renderModifier(sb, isSuspendFunctionType, "suspend");
        if (receiverTypeFromFunctionType != null) {
            if ((!shouldRenderAsPrettyFunctionType(receiverTypeFromFunctionType) || receiverTypeFromFunctionType.isMarkedNullable()) && !hasModifiersOrAnnotations(receiverTypeFromFunctionType)) {
                z = false;
            }
            if (z) {
                sb.append("(");
            }
            renderNormalizedType(sb, receiverTypeFromFunctionType);
            if (z) {
                sb.append(")");
            }
            sb.append(".");
        }
        sb.append("(");
        int i = 0;
        for (TypeProjection typeProjection : FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType)) {
            if (i > 0) {
                sb.append(", ");
            }
            if (getParameterNamesInFunctionalTypes()) {
                KotlinType type = typeProjection.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "typeProjection.type");
                name = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type);
            } else {
                name = null;
            }
            if (name != null) {
                sb.append(renderName(name, false));
                sb.append(": ");
            }
            sb.append(getFunctionTypeParameterTypesRenderer().renderTypeProjection(typeProjection));
            i++;
        }
        sb.append(") ");
        sb.append(arrow());
        sb.append(" ");
        renderNormalizedType(sb, FunctionTypesKt.getReturnTypeFromFunctionType(kotlinType));
        if (z3) {
            sb.append(")");
        }
        if (isMarkedNullable) {
            sb.append("?");
        }
    }

    private final boolean hasModifiersOrAnnotations(KotlinType kotlinType) {
        return FunctionTypesKt.isSuspendFunctionType(kotlinType) || !kotlinType.getAnnotations().isEmpty();
    }

    private final void appendDefinedIn(StringBuilder sb, DeclarationDescriptor declarationDescriptor) {
        if ((declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof PackageViewDescriptor)) {
            return;
        }
        if (declarationDescriptor instanceof ModuleDescriptor) {
            sb.append(" is a module");
            return;
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        if (containingDeclaration == null || (containingDeclaration instanceof ModuleDescriptor)) {
            return;
        }
        sb.append(" ");
        sb.append(renderMessage("defined in"));
        sb.append(" ");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(containingDeclaration);
        Intrinsics.checkExpressionValueIsNotNull(fqName, "DescriptorUtils.getFqName(containingDeclaration)");
        sb.append(fqName.isRoot() ? "root package" : renderFqName(fqName));
        if (getWithSourceFileForTopLevel() && (containingDeclaration instanceof PackageFragmentDescriptor) && (declarationDescriptor instanceof DeclarationDescriptorWithSource)) {
            SourceElement source = ((DeclarationDescriptorWithSource) declarationDescriptor).getSource();
            Intrinsics.checkExpressionValueIsNotNull(source, "descriptor.source");
            SourceFile containingFile = source.getContainingFile();
            Intrinsics.checkExpressionValueIsNotNull(containingFile, "descriptor.source.containingFile");
            String name = containingFile.getName();
            if (name != null) {
                sb.append(" ");
                sb.append(renderMessage("in file"));
                sb.append(" ");
                sb.append(name);
            }
        }
    }

    static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        descriptorRendererImpl.renderAnnotations(sb, annotated, annotationUseSiteTarget);
    }

    private final void renderAnnotations(StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            Set<FqName> excludedTypeAnnotationClasses = annotated instanceof KotlinType ? getExcludedTypeAnnotationClasses() : getExcludedAnnotationClasses();
            Function1<AnnotationDescriptor, Boolean> annotationFilter = getAnnotationFilter();
            for (AnnotationDescriptor annotationDescriptor : annotated.getAnnotations()) {
                if (!CollectionsKt.contains(excludedTypeAnnotationClasses, annotationDescriptor.getFqName()) && (annotationFilter == null || annotationFilter.invoke(annotationDescriptor).booleanValue())) {
                    sb.append(renderAnnotation(annotationDescriptor, annotationUseSiteTarget));
                    if (getEachAnnotationOnNewLine()) {
                        StringsKt.appendln(sb);
                    } else {
                        sb.append(" ");
                    }
                }
            }
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String renderAnnotation(AnnotationDescriptor annotation, AnnotationUseSiteTarget annotationUseSiteTarget) {
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        if (annotationUseSiteTarget != null) {
            sb.append(annotationUseSiteTarget.getRenderName() + ":");
        }
        KotlinType type = annotation.getType();
        sb.append(renderType(type));
        if (getIncludeAnnotationArguments()) {
            List<String> renderAndSortAnnotationArguments = renderAndSortAnnotationArguments(annotation);
            if (getIncludeEmptyAnnotationArguments() || (!renderAndSortAnnotationArguments.isEmpty())) {
                CollectionsKt.joinTo(renderAndSortAnnotationArguments, sb, (r15 & 2) != 0 ? ", " : ", ", (r15 & 4) != 0 ? "" : "(", (r15 & 8) != 0 ? "" : ")", (r15 & 16) != 0 ? -1 : 0, (r15 & 32) != 0 ? "..." : null, (r15 & 64) != 0 ? null : null);
            }
        }
        if (getVerbose() && (KotlinTypeKt.isError(type) || (type.getConstructor().mo1092getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor))) {
            sb.append(" /* annotation class not found */");
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor annotationDescriptor) {
        ClassConstructorDescriptor mo1086getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
        ArrayList arrayList = null;
        ClassDescriptor annotationClass = getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(annotationDescriptor) : null;
        if (annotationClass != null && (mo1086getUnsubstitutedPrimaryConstructor = annotationClass.mo1086getUnsubstitutedPrimaryConstructor()) != null && (valueParameters = mo1086getUnsubstitutedPrimaryConstructor.getValueParameters()) != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : valueParameters) {
                if (((ValueParameterDescriptor) obj).declaresDefaultValue()) {
                    arrayList2.add(obj);
                }
            }
            ArrayList<ValueParameterDescriptor> arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            for (ValueParameterDescriptor it : arrayList3) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                arrayList4.add(it.getName());
            }
            arrayList = arrayList4;
        }
        if (arrayList == null) {
            arrayList = CollectionsKt.emptyList();
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (!allValueArguments.containsKey((Name) obj2)) {
                arrayList5.add(obj2);
            }
        }
        ArrayList arrayList6 = arrayList5;
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
        Iterator it2 = arrayList6.iterator();
        while (it2.hasNext()) {
            arrayList7.add(((Name) it2.next()).asString() + " = ...");
        }
        ArrayList arrayList8 = arrayList7;
        Set<Map.Entry<Name, ConstantValue<?>>> entrySet = allValueArguments.entrySet();
        ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(entrySet, 10));
        Iterator<T> it3 = entrySet.iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            Name name = (Name) entry.getKey();
            ConstantValue<?> constantValue = (ConstantValue) entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(name.asString());
            sb.append(" = ");
            sb.append(!arrayList.contains(name) ? renderConstant(constantValue) : "...");
            arrayList9.add(sb.toString());
        }
        return CollectionsKt.sorted(CollectionsKt.plus((Collection) arrayList8, (Iterable) arrayList9));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderConstant(ConstantValue<?> constantValue) {
        if (constantValue instanceof ArrayValue) {
            return CollectionsKt.joinToString$default(((ArrayValue) constantValue).getValue(), ", ", "{", "}", 0, null, new Function1<ConstantValue<?>, String>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$renderConstant$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(ConstantValue<?> it) {
                    String renderConstant;
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    renderConstant = DescriptorRendererImpl.this.renderConstant(it);
                    return renderConstant;
                }
            }, 24, null);
        }
        if (constantValue instanceof AnnotationValue) {
            return StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, ((AnnotationValue) constantValue).getValue(), null, 2, null), (CharSequence) "@");
        }
        if (constantValue instanceof KClassValue) {
            KClassValue.Value value = ((KClassValue) constantValue).getValue();
            if (value instanceof KClassValue.Value.LocalClass) {
                return ((KClassValue.Value.LocalClass) value).getType() + "::class";
            } else if (value instanceof KClassValue.Value.NormalClass) {
                KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value;
                String asString = normalClass.getClassId().asSingleFqName().asString();
                Intrinsics.checkExpressionValueIsNotNull(asString, "classValue.classId.asSingleFqName().asString()");
                for (int i = 0; i < normalClass.getArrayDimensions(); i++) {
                    asString = "kotlin.Array<" + asString + Typography.greater;
                }
                return asString + "::class";
            } else {
                throw new NoWhenBranchMatchedException();
            }
        }
        return constantValue.toString();
    }

    private final boolean renderVisibility(Visibility visibility, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.VISIBILITY)) {
            if (getNormalizedVisibilities()) {
                visibility = visibility.normalize();
            }
            if (getRenderDefaultVisibility() || !Intrinsics.areEqual(visibility, Visibilities.DEFAULT_VISIBILITY)) {
                sb.append(renderKeyword(visibility.getInternalDisplayName()));
                sb.append(" ");
                return true;
            }
            return false;
        }
        return false;
    }

    private final void renderModality(Modality modality, StringBuilder sb, Modality modality2) {
        if (getRenderDefaultModality() || modality != modality2) {
            boolean contains = getModifiers().contains(DescriptorRendererModifier.MODALITY);
            String name = modality.name();
            if (name == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = name.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            renderModifier(sb, contains, lowerCase);
        }
    }

    private final Modality implicitModalityWithoutExtensions(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) memberDescriptor).getKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        if (classDescriptor == null) {
            return Modality.FINAL;
        }
        if (memberDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "this.overriddenDescriptors");
            if (!(!overriddenDescriptors.isEmpty()) || classDescriptor.getModality() == Modality.FINAL) {
                if (classDescriptor.getKind() == ClassKind.INTERFACE && (!Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), Visibilities.PRIVATE))) {
                    return callableMemberDescriptor.getModality() == Modality.ABSTRACT ? Modality.ABSTRACT : Modality.OPEN;
                }
                return Modality.FINAL;
            }
            return Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (DescriptorUtils.isTopLevelDeclaration(callableMemberDescriptor) && callableMemberDescriptor.getModality() == Modality.FINAL) {
            return;
        }
        if (getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callableMemberDescriptor.getModality() == Modality.OPEN && overridesSomething(callableMemberDescriptor)) {
            return;
        }
        Modality modality = callableMemberDescriptor.getModality();
        Intrinsics.checkExpressionValueIsNotNull(modality, "callable.modality");
        renderModality(modality, sb, implicitModalityWithoutExtensions(callableMemberDescriptor));
    }

    private final void renderOverride(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.OVERRIDE) && overridesSomething(callableMemberDescriptor) && getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            renderModifier(sb, true, "override");
            if (getVerbose()) {
                sb.append("/*");
                sb.append(callableMemberDescriptor.getOverriddenDescriptors().size());
                sb.append("*/ ");
            }
        }
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.MEMBER_KIND) && getVerbose() && callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            sb.append("/*");
            String name = callableMemberDescriptor.getKind().name();
            if (name == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = name.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            sb.append(lowerCase);
            sb.append("*/ ");
        }
    }

    private final void renderModifier(StringBuilder sb, boolean z, String str) {
        if (z) {
            sb.append(renderKeyword(str));
            sb.append(" ");
        }
    }

    private final void renderMemberModifiers(MemberDescriptor memberDescriptor, StringBuilder sb) {
        renderModifier(sb, memberDescriptor.isExternal(), "external");
        boolean z = true;
        renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.EXPECT) && memberDescriptor.isExpect(), "expect");
        renderModifier(sb, (getModifiers().contains(DescriptorRendererModifier.ACTUAL) && memberDescriptor.isActual()) ? false : false, "actual");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void renderAdditionalModifiers(kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r7, java.lang.StringBuilder r8) {
        /*
            r6 = this;
            boolean r0 = r7.isOperator()
            java.lang.String r1 = "it"
            java.lang.String r2 = "functionDescriptor.overriddenDescriptors"
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L48
            java.util.Collection r0 = r7.getOverriddenDescriptors()
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r2)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            boolean r5 = r0 instanceof java.util.Collection
            if (r5 == 0) goto L24
            r5 = r0
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L24
        L22:
            r0 = 1
            goto L3e
        L24:
            java.util.Iterator r0 = r0.iterator()
        L28:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L22
            java.lang.Object r5 = r0.next()
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r5 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r5
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r5, r1)
            boolean r5 = r5.isOperator()
            if (r5 == 0) goto L28
            r0 = 0
        L3e:
            if (r0 != 0) goto L46
            boolean r0 = r6.getAlwaysRenderModifiers()
            if (r0 == 0) goto L48
        L46:
            r0 = 1
            goto L49
        L48:
            r0 = 0
        L49:
            boolean r5 = r7.isInfix()
            if (r5 == 0) goto L8a
            java.util.Collection r5 = r7.getOverriddenDescriptors()
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r5, r2)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            boolean r2 = r5 instanceof java.util.Collection
            if (r2 == 0) goto L67
            r2 = r5
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L67
        L65:
            r1 = 1
            goto L81
        L67:
            java.util.Iterator r2 = r5.iterator()
        L6b:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L65
            java.lang.Object r5 = r2.next()
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r5 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r5
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r5, r1)
            boolean r5 = r5.isInfix()
            if (r5 == 0) goto L6b
            r1 = 0
        L81:
            if (r1 != 0) goto L89
            boolean r1 = r6.getAlwaysRenderModifiers()
            if (r1 == 0) goto L8a
        L89:
            r3 = 1
        L8a:
            boolean r1 = r7.isTailrec()
            java.lang.String r2 = "tailrec"
            r6.renderModifier(r8, r1, r2)
            r6.renderSuspendModifier(r7, r8)
            boolean r7 = r7.isInline()
            java.lang.String r1 = "inline"
            r6.renderModifier(r8, r7, r1)
            java.lang.String r7 = "infix"
            r6.renderModifier(r8, r3, r7)
            java.lang.String r7 = "operator"
            r6.renderModifier(r8, r0, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderAdditionalModifiers(kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, java.lang.StringBuilder):void");
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        renderModifier(sb, functionDescriptor.isSuspend(), "suspend");
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    public String render(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkParameterIsNotNull(declarationDescriptor, "declarationDescriptor");
        StringBuilder sb = new StringBuilder();
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), sb);
        if (getWithDefinedIn()) {
            appendDefinedIn(sb, declarationDescriptor);
        }
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameter(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb, boolean z) {
        if (z) {
            sb.append(lt());
        }
        if (getVerbose()) {
            sb.append("/*");
            sb.append(typeParameterDescriptor.getIndex());
            sb.append("*/ ");
        }
        renderModifier(sb, typeParameterDescriptor.isReified(), "reified");
        String label = typeParameterDescriptor.getVariance().getLabel();
        boolean z2 = true;
        renderModifier(sb, label.length() > 0, label);
        renderAnnotations$default(this, sb, typeParameterDescriptor, null, 2, null);
        renderName(typeParameterDescriptor, sb, z);
        int size = typeParameterDescriptor.getUpperBounds().size();
        if ((size > 1 && !z) || size == 1) {
            KotlinType upperBound = typeParameterDescriptor.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(upperBound)) {
                sb.append(" : ");
                Intrinsics.checkExpressionValueIsNotNull(upperBound, "upperBound");
                sb.append(renderType(upperBound));
            }
        } else if (z) {
            for (KotlinType upperBound2 : typeParameterDescriptor.getUpperBounds()) {
                if (!KotlinBuiltIns.isDefaultBound(upperBound2)) {
                    if (z2) {
                        sb.append(" : ");
                    } else {
                        sb.append(" & ");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(upperBound2, "upperBound");
                    sb.append(renderType(upperBound2));
                    z2 = false;
                }
            }
        }
        if (z) {
            sb.append(gt());
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> list, StringBuilder sb, boolean z) {
        if (!getWithoutTypeParameters() && (!list.isEmpty())) {
            sb.append(lt());
            renderTypeParameterList(sb, list);
            sb.append(gt());
            if (z) {
                sb.append(" ");
            }
        }
    }

    private final void renderTypeParameterList(StringBuilder sb, List<? extends TypeParameterDescriptor> list) {
        Iterator<? extends TypeParameterDescriptor> it = list.iterator();
        while (it.hasNext()) {
            renderTypeParameter(it.next(), sb, false);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderFunction(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderAnnotations$default(this, sb, functionDescriptor, null, 2, null);
                Visibility visibility = functionDescriptor.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "function.visibility");
                renderVisibility(visibility, sb);
                FunctionDescriptor functionDescriptor2 = functionDescriptor;
                renderModalityForCallable(functionDescriptor2, sb);
                if (getIncludeAdditionalModifiers()) {
                    renderMemberModifiers(functionDescriptor, sb);
                }
                renderOverride(functionDescriptor2, sb);
                if (getIncludeAdditionalModifiers()) {
                    renderAdditionalModifiers(functionDescriptor, sb);
                } else {
                    renderSuspendModifier(functionDescriptor, sb);
                }
                renderMemberKind(functionDescriptor2, sb);
                if (getVerbose()) {
                    if (functionDescriptor.isHiddenToOvercomeSignatureClash()) {
                        sb.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (functionDescriptor.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        sb.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            sb.append(renderKeyword("fun"));
            sb.append(" ");
            List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "function.typeParameters");
            renderTypeParameters(typeParameters, sb, true);
            renderReceiver(functionDescriptor, sb);
        }
        renderName(functionDescriptor, sb, true);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "function.valueParameters");
        renderValueParameters(valueParameters, functionDescriptor.hasSynthesizedParameterNames(), sb);
        renderReceiverAfterName(functionDescriptor, sb);
        KotlinType returnType = functionDescriptor.getReturnType();
        if (!getWithoutReturnType() && (getUnitReturnType() || returnType == null || !KotlinBuiltIns.isUnit(returnType))) {
            sb.append(": ");
            sb.append(returnType == null ? "[NULL]" : renderType(returnType));
        }
        List<TypeParameterDescriptor> typeParameters2 = functionDescriptor.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "function.typeParameters");
        renderWhereSuffix(typeParameters2, sb);
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (getReceiverAfterName() && (extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter()) != null) {
            sb.append(" on ");
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "receiver.type");
            sb.append(renderType(type));
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            renderAnnotations(sb, extensionReceiverParameter, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "receiver.type");
            String renderType = renderType(type);
            if (shouldRenderAsPrettyFunctionType(type) && !TypeUtils.isNullableType(type)) {
                renderType = '(' + renderType + ')';
            }
            sb.append(renderType);
            sb.append(".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x002b, code lost:
        if (r0.getModality() != kotlin.reflect.jvm.internal.impl.descriptors.Modality.SEALED) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void renderConstructor(kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor r18, java.lang.StringBuilder r19) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderConstructor(kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor, java.lang.StringBuilder):void");
    }

    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> list, StringBuilder sb) {
        if (getWithoutTypeParameters()) {
            return;
        }
        ArrayList arrayList = new ArrayList(0);
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkExpressionValueIsNotNull(upperBounds, "typeParameter.upperBounds");
            for (KotlinType it : CollectionsKt.drop(upperBounds, 1)) {
                StringBuilder sb2 = new StringBuilder();
                Name name = typeParameterDescriptor.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "typeParameter.name");
                sb2.append(renderName(name, false));
                sb2.append(" : ");
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                sb2.append(renderType(it));
                arrayList.add(sb2.toString());
            }
        }
        if (!arrayList.isEmpty()) {
            sb.append(" ");
            sb.append(renderKeyword("where"));
            sb.append(" ");
            CollectionsKt.joinTo(arrayList, sb, (r15 & 2) != 0 ? ", " : ", ", (r15 & 4) != 0 ? "" : null, (r15 & 8) != 0 ? "" : null, (r15 & 16) != 0 ? -1 : 0, (r15 & 32) != 0 ? "..." : null, (r15 & 64) != 0 ? null : null);
        }
    }

    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> collection, boolean z, StringBuilder sb) {
        boolean shouldRenderParameterNames = shouldRenderParameterNames(z);
        int size = collection.size();
        getValueParametersHandler().appendBeforeValueParameters(size, sb);
        int i = 0;
        for (ValueParameterDescriptor valueParameterDescriptor : collection) {
            getValueParametersHandler().appendBeforeValueParameter(valueParameterDescriptor, i, size, sb);
            renderValueParameter(valueParameterDescriptor, shouldRenderParameterNames, sb, false);
            getValueParametersHandler().appendAfterValueParameter(valueParameterDescriptor, i, size, sb);
            i++;
        }
        getValueParametersHandler().appendAfterValueParameters(size, sb);
    }

    private final boolean shouldRenderParameterNames(boolean z) {
        int i = WhenMappings.$EnumSwitchMapping$4[getParameterNameRenderingPolicy().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return false;
                }
                throw new NoWhenBranchMatchedException();
            } else if (z) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void renderValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r10, boolean r11, java.lang.StringBuilder r12, boolean r13) {
        /*
            r9 = this;
            if (r13 == 0) goto L10
            java.lang.String r0 = "value-parameter"
            java.lang.String r0 = r9.renderKeyword(r0)
            r12.append(r0)
            java.lang.String r0 = " "
            r12.append(r0)
        L10:
            boolean r0 = r9.getVerbose()
            if (r0 == 0) goto L27
            java.lang.String r0 = "/*"
            r12.append(r0)
            int r0 = r10.getIndex()
            r12.append(r0)
        */
        //  java.lang.String r0 = "*/ "
        /*
            r12.append(r0)
        L27:
            r3 = r10
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated r3 = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated) r3
            r4 = 0
            r5 = 2
            r6 = 0
            r1 = r9
            r2 = r12
            renderAnnotations$default(r1, r2, r3, r4, r5, r6)
            boolean r0 = r10.isCrossinline()
            java.lang.String r1 = "crossinline"
            r9.renderModifier(r12, r0, r1)
            boolean r0 = r10.isNoinline()
            java.lang.String r1 = "noinline"
            r9.renderModifier(r12, r0, r1)
            boolean r0 = r9.getRenderPrimaryConstructorParametersAsProperties()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L61
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor r0 = r10.getContainingDeclaration()
            boolean r3 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor
            if (r3 != 0) goto L55
            r0 = 0
        L55:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor) r0
            if (r0 == 0) goto L61
            boolean r0 = r0.isPrimary()
            if (r0 != r2) goto L61
            r8 = 1
            goto L62
        L61:
            r8 = 0
        L62:
            if (r8 == 0) goto L6d
            boolean r0 = r9.getActualPropertiesInPrimaryConstructor()
            java.lang.String r3 = "actual"
            r9.renderModifier(r12, r0, r3)
        L6d:
            r4 = r10
            kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor r4 = (kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor) r4
            r3 = r9
            r5 = r11
            r6 = r12
            r7 = r13
            r3.renderVariable(r4, r5, r6, r7, r8)
            kotlin.jvm.functions.Function1 r11 = r9.getDefaultParameterValueRenderer()
            if (r11 == 0) goto L8f
            boolean r11 = r9.getDebugMode()
            if (r11 == 0) goto L88
            boolean r11 = r10.declaresDefaultValue()
            goto L8c
        L88:
            boolean r11 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.declaresOrInheritsDefaultValue(r10)
        L8c:
            if (r11 == 0) goto L8f
            r1 = 1
        L8f:
            if (r1 == 0) goto Lb4
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r13 = " = "
            r11.append(r13)
            kotlin.jvm.functions.Function1 r13 = r9.getDefaultParameterValueRenderer()
            if (r13 != 0) goto La4
            kotlin.jvm.internal.Intrinsics.throwNpe()
        La4:
            java.lang.Object r10 = r13.invoke(r10)
            java.lang.String r10 = (java.lang.String) r10
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            r12.append(r10)
        Lb4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor, boolean, java.lang.StringBuilder, boolean):void");
    }

    static /* synthetic */ void renderValVarPrefix$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, StringBuilder sb, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        descriptorRendererImpl.renderValVarPrefix(variableDescriptor, sb, z);
    }

    private final void renderValVarPrefix(VariableDescriptor variableDescriptor, StringBuilder sb, boolean z) {
        if (z || !(variableDescriptor instanceof ValueParameterDescriptor)) {
            sb.append(renderKeyword(variableDescriptor.isVar() ? "var" : "val"));
            sb.append(" ");
        }
    }

    private final void renderVariable(VariableDescriptor variableDescriptor, boolean z, StringBuilder sb, boolean z2, boolean z3) {
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "variable.type");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) (!(variableDescriptor instanceof ValueParameterDescriptor) ? null : variableDescriptor);
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType kotlinType = varargElementType != null ? varargElementType : type;
        renderModifier(sb, varargElementType != null, "vararg");
        if (z3 || (z2 && !getStartFromName())) {
            renderValVarPrefix(variableDescriptor, sb, z3);
        }
        if (z) {
            renderName(variableDescriptor, sb, z2);
            sb.append(": ");
        }
        sb.append(renderType(kotlinType));
        renderInitializer(variableDescriptor, sb);
        if (!getVerbose() || varargElementType == null) {
            return;
        }
        sb.append(" /*");
        sb.append(renderType(type));
        sb.append("*/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderProperty(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderPropertyAnnotations(propertyDescriptor, sb);
                Visibility visibility = propertyDescriptor.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "property.visibility");
                renderVisibility(visibility, sb);
                boolean z = false;
                renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.CONST) && propertyDescriptor.isConst(), "const");
                renderMemberModifiers(propertyDescriptor, sb);
                PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
                renderModalityForCallable(propertyDescriptor2, sb);
                renderOverride(propertyDescriptor2, sb);
                if (getModifiers().contains(DescriptorRendererModifier.LATEINIT) && propertyDescriptor.isLateInit()) {
                    z = true;
                }
                renderModifier(sb, z, "lateinit");
                renderMemberKind(propertyDescriptor2, sb);
            }
            renderValVarPrefix$default(this, propertyDescriptor, sb, false, 4, null);
            List<TypeParameterDescriptor> typeParameters = propertyDescriptor.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "property.typeParameters");
            renderTypeParameters(typeParameters, sb, true);
            renderReceiver(propertyDescriptor, sb);
        }
        renderName(propertyDescriptor, sb, true);
        sb.append(": ");
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "property.type");
        sb.append(renderType(type));
        renderReceiverAfterName(propertyDescriptor, sb);
        renderInitializer(propertyDescriptor, sb);
        List<TypeParameterDescriptor> typeParameters2 = propertyDescriptor.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "property.typeParameters");
        renderWhereSuffix(typeParameters2, sb);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            renderAnnotations$default(this, sb, propertyDescriptor, null, 2, null);
            FieldDescriptor it = propertyDescriptor.getBackingField();
            if (it != null) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                renderAnnotations(sb, it, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor it2 = propertyDescriptor.getDelegateField();
            if (it2 != null) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                renderAnnotations(sb, it2, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (getPropertyAccessorRenderingPolicy() == PropertyAccessorRenderingPolicy.NONE) {
                PropertyGetterDescriptor it3 = propertyDescriptor.getGetter();
                if (it3 != null) {
                    Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                    renderAnnotations(sb, it3, AnnotationUseSiteTarget.PROPERTY_GETTER);
                }
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter != null) {
                    Intrinsics.checkExpressionValueIsNotNull(setter, "it");
                    renderAnnotations(sb, setter, AnnotationUseSiteTarget.PROPERTY_SETTER);
                    Intrinsics.checkExpressionValueIsNotNull(setter, "setter");
                    List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
                    Intrinsics.checkExpressionValueIsNotNull(valueParameters, "setter.valueParameters");
                    ValueParameterDescriptor it4 = (ValueParameterDescriptor) CollectionsKt.single((List<? extends Object>) valueParameters);
                    Intrinsics.checkExpressionValueIsNotNull(it4, "it");
                    renderAnnotations(sb, it4, AnnotationUseSiteTarget.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void renderInitializer(VariableDescriptor variableDescriptor, StringBuilder sb) {
        ConstantValue<?> constant;
        if (!getIncludePropertyConstant() || (constant = variableDescriptor.mo1088getCompileTimeInitializer()) == null) {
            return;
        }
        sb.append(" = ");
        Intrinsics.checkExpressionValueIsNotNull(constant, "constant");
        sb.append(escape(renderConstant(constant)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeAlias(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
        renderAnnotations$default(this, sb, typeAliasDescriptor, null, 2, null);
        Visibility visibility = typeAliasDescriptor.getVisibility();
        Intrinsics.checkExpressionValueIsNotNull(visibility, "typeAlias.visibility");
        renderVisibility(visibility, sb);
        renderMemberModifiers(typeAliasDescriptor, sb);
        sb.append(renderKeyword("typealias"));
        sb.append(" ");
        renderName(typeAliasDescriptor, sb, true);
        List<TypeParameterDescriptor> declaredTypeParameters = typeAliasDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "typeAlias.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(typeAliasDescriptor, sb);
        sb.append(" = ");
        sb.append(renderType(typeAliasDescriptor.getUnderlyingType()));
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters, StringBuilder sb) {
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "classifier.declaredTypeParameters");
        TypeConstructor typeConstructor = classifierDescriptorWithTypeParameters.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "classifier.typeConstructor");
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "classifier.typeConstructor.parameters");
        if (getVerbose() && classifierDescriptorWithTypeParameters.isInner() && parameters.size() > declaredTypeParameters.size()) {
            sb.append(" /*captured type parameters: ");
            renderTypeParameterList(sb, parameters.subList(declaredTypeParameters.size(), parameters.size()));
            sb.append("*/");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderClass(ClassDescriptor classDescriptor, StringBuilder sb) {
        ClassConstructorDescriptor mo1086getUnsubstitutedPrimaryConstructor;
        boolean z = classDescriptor.getKind() == ClassKind.ENUM_ENTRY;
        if (!getStartFromName()) {
            renderAnnotations$default(this, sb, classDescriptor, null, 2, null);
            if (!z) {
                Visibility visibility = classDescriptor.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "klass.visibility");
                renderVisibility(visibility, sb);
            }
            if (classDescriptor.getKind() != ClassKind.INTERFACE || classDescriptor.getModality() != Modality.ABSTRACT) {
                ClassKind kind = classDescriptor.getKind();
                Intrinsics.checkExpressionValueIsNotNull(kind, "klass.kind");
                if (!kind.isSingleton() || classDescriptor.getModality() != Modality.FINAL) {
                    Modality modality = classDescriptor.getModality();
                    Intrinsics.checkExpressionValueIsNotNull(modality, "klass.modality");
                    renderModality(modality, sb, implicitModalityWithoutExtensions(classDescriptor));
                }
            }
            renderMemberModifiers(classDescriptor, sb);
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INNER) && classDescriptor.isInner(), "inner");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.DATA) && classDescriptor.isData(), GameSettingFloat.TAG_ATTR_DATA);
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INLINE) && classDescriptor.isInline(), "inline");
            renderClassKindPrefix(classDescriptor, sb);
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (!DescriptorUtils.isCompanionObject(classDescriptor2)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            renderName(classDescriptor2, sb, true);
        } else {
            renderCompanionObjectName(classDescriptor2, sb);
        }
        if (z) {
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "klass.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(classDescriptor, sb);
        ClassKind kind2 = classDescriptor.getKind();
        Intrinsics.checkExpressionValueIsNotNull(kind2, "klass.kind");
        if (!kind2.isSingleton() && getClassWithPrimaryConstructor() && (mo1086getUnsubstitutedPrimaryConstructor = classDescriptor.mo1086getUnsubstitutedPrimaryConstructor()) != null) {
            sb.append(" ");
            renderAnnotations$default(this, sb, mo1086getUnsubstitutedPrimaryConstructor, null, 2, null);
            Visibility visibility2 = mo1086getUnsubstitutedPrimaryConstructor.getVisibility();
            Intrinsics.checkExpressionValueIsNotNull(visibility2, "primaryConstructor.visibility");
            renderVisibility(visibility2, sb);
            sb.append(renderKeyword("constructor"));
            List<ValueParameterDescriptor> valueParameters = mo1086getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "primaryConstructor.valueParameters");
            renderValueParameters(valueParameters, mo1086getUnsubstitutedPrimaryConstructor.hasSynthesizedParameterNames(), sb);
        }
        renderSuperTypes(classDescriptor, sb);
        renderWhereSuffix(declaredTypeParameters, sb);
    }

    private final void renderSuperTypes(ClassDescriptor classDescriptor, StringBuilder sb) {
        if (getWithoutSuperTypes() || KotlinBuiltIns.isNothing(classDescriptor.getDefaultType())) {
            return;
        }
        TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "klass.typeConstructor");
        Collection<KotlinType> mo1093getSupertypes = typeConstructor.mo1093getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(mo1093getSupertypes, "klass.typeConstructor.supertypes");
        if (mo1093getSupertypes.isEmpty()) {
            return;
        }
        if (mo1093getSupertypes.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(mo1093getSupertypes.iterator().next())) {
            return;
        }
        renderSpaceIfNeeded(sb);
        sb.append(": ");
        CollectionsKt.joinTo(mo1093getSupertypes, sb, (r15 & 2) != 0 ? ", " : ", ", (r15 & 4) != 0 ? "" : null, (r15 & 8) != 0 ? "" : null, (r15 & 16) != 0 ? -1 : 0, (r15 & 32) != 0 ? "..." : null, (r15 & 64) != 0 ? null : new Function1<KotlinType, String>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$renderSuperTypes$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KotlinType it) {
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                return descriptorRendererImpl.renderType(it);
            }
        });
    }

    private final void renderClassKindPrefix(ClassDescriptor classDescriptor, StringBuilder sb) {
        sb.append(renderKeyword(DescriptorRenderer.Companion.getClassifierKindPrefix(classDescriptor)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageView(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
        renderPackageHeader(packageViewDescriptor.getFqName(), "package", sb);
        if (getDebugMode()) {
            sb.append(" in context of ");
            renderName(packageViewDescriptor.getModule(), sb, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
        renderPackageHeader(packageFragmentDescriptor.getFqName(), "package-fragment", sb);
        if (getDebugMode()) {
            sb.append(" in ");
            renderName(packageFragmentDescriptor.getContainingDeclaration(), sb, false);
        }
    }

    private final void renderPackageHeader(FqName fqName, String str, StringBuilder sb) {
        sb.append(renderKeyword(str));
        FqNameUnsafe unsafe = fqName.toUnsafe();
        Intrinsics.checkExpressionValueIsNotNull(unsafe, "fqName.toUnsafe()");
        String renderFqName = renderFqName(unsafe);
        if (renderFqName.length() > 0) {
            sb.append(" ");
            sb.append(renderFqName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAccessorModifiers(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb) {
        renderMemberModifiers(propertyAccessorDescriptor, sb);
    }

    /* compiled from: DescriptorRendererImpl.kt */
    /* loaded from: classes2.dex */
    private final class RenderDeclarationDescriptorVisitor implements DeclarationDescriptorVisitor<Unit, StringBuilder> {

        /* loaded from: classes2.dex */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PropertyAccessorRenderingPolicy.values().length];
                $EnumSwitchMapping$0 = iArr;
                iArr[PropertyAccessorRenderingPolicy.PRETTY.ordinal()] = 1;
                iArr[PropertyAccessorRenderingPolicy.DEBUG.ordinal()] = 2;
                iArr[PropertyAccessorRenderingPolicy.NONE.ordinal()] = 3;
            }
        }

        public RenderDeclarationDescriptorVisitor() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, StringBuilder sb) {
            visitClassDescriptor2(classDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) {
            visitConstructorDescriptor2(constructorDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, StringBuilder sb) {
            visitFunctionDescriptor2(functionDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, StringBuilder sb) {
            visitModuleDeclaration2(moduleDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
            visitPackageFragmentDescriptor2(packageFragmentDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
            visitPackageViewDescriptor2(packageViewDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
            visitPropertyDescriptor2(propertyDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, StringBuilder sb) {
            visitPropertyGetterDescriptor2(propertyGetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, StringBuilder sb) {
            visitPropertySetterDescriptor2(propertySetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, StringBuilder sb) {
            visitReceiverParameterDescriptor2(receiverParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
            visitTypeAliasDescriptor2(typeAliasDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb) {
            visitTypeParameterDescriptor2(typeParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, StringBuilder sb) {
            visitValueParameterDescriptor2(valueParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        /* renamed from: visitValueParameterDescriptor  reason: avoid collision after fix types in other method */
        public void visitValueParameterDescriptor2(ValueParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor, true, builder, true);
        }

        /* renamed from: visitPropertyDescriptor  reason: avoid collision after fix types in other method */
        public void visitPropertyDescriptor2(PropertyDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor, builder);
        }

        /* renamed from: visitPropertyGetterDescriptor  reason: avoid collision after fix types in other method */
        public void visitPropertyGetterDescriptor2(PropertyGetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "getter");
        }

        /* renamed from: visitPropertySetterDescriptor  reason: avoid collision after fix types in other method */
        public void visitPropertySetterDescriptor2(PropertySetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "setter");
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb, String str) {
            int i = WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                visitFunctionDescriptor2((FunctionDescriptor) propertyAccessorDescriptor, sb);
                return;
            }
            DescriptorRendererImpl.this.renderAccessorModifiers(propertyAccessorDescriptor, sb);
            sb.append(str + " for ");
            DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
            PropertyDescriptor correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty();
            Intrinsics.checkExpressionValueIsNotNull(correspondingProperty, "descriptor.correspondingProperty");
            descriptorRendererImpl.renderProperty(correspondingProperty, sb);
        }

        /* renamed from: visitFunctionDescriptor  reason: avoid collision after fix types in other method */
        public void visitFunctionDescriptor2(FunctionDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor, builder);
        }

        /* renamed from: visitReceiverParameterDescriptor  reason: avoid collision after fix types in other method */
        public void visitReceiverParameterDescriptor2(ReceiverParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            builder.append(descriptor.getName());
        }

        /* renamed from: visitConstructorDescriptor  reason: avoid collision after fix types in other method */
        public void visitConstructorDescriptor2(ConstructorDescriptor constructorDescriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        /* renamed from: visitTypeParameterDescriptor  reason: avoid collision after fix types in other method */
        public void visitTypeParameterDescriptor2(TypeParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor, builder, true);
        }

        /* renamed from: visitPackageFragmentDescriptor  reason: avoid collision after fix types in other method */
        public void visitPackageFragmentDescriptor2(PackageFragmentDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor, builder);
        }

        /* renamed from: visitPackageViewDescriptor  reason: avoid collision after fix types in other method */
        public void visitPackageViewDescriptor2(PackageViewDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor, builder);
        }

        /* renamed from: visitModuleDeclaration  reason: avoid collision after fix types in other method */
        public void visitModuleDeclaration2(ModuleDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor, builder, true);
        }

        /* renamed from: visitClassDescriptor  reason: avoid collision after fix types in other method */
        public void visitClassDescriptor2(ClassDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor, builder);
        }

        /* renamed from: visitTypeAliasDescriptor  reason: avoid collision after fix types in other method */
        public void visitTypeAliasDescriptor2(TypeAliasDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor, builder);
        }
    }

    private final void renderSpaceIfNeeded(StringBuilder sb) {
        int length = sb.length();
        if (length == 0 || sb.charAt(length - 1) != ' ') {
            sb.append(' ');
        }
    }

    private final String replacePrefixes(String str, String str2, String str3, String str4, String str5) {
        if (StringsKt.startsWith$default(str, str2, false, 2, (Object) null) && StringsKt.startsWith$default(str3, str4, false, 2, (Object) null)) {
            int length = str2.length();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = str.substring(length);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            int length2 = str4.length();
            if (str3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring2 = str3.substring(length2);
            Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.String).substring(startIndex)");
            String str6 = str5 + substring;
            if (Intrinsics.areEqual(substring, substring2)) {
                return str6;
            }
            if (differsOnlyInNullability(substring, substring2)) {
                return str6 + '!';
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0032, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r7 + '?', r8) == false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean differsOnlyInNullability(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r1 = "?"
            java.lang.String r2 = ""
            r3 = 0
            r4 = 4
            r5 = 0
            r0 = r8
            java.lang.String r0 = kotlin.text.StringsKt.replace$default(r0, r1, r2, r3, r4, r5)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            r1 = 0
            if (r0 != 0) goto L50
            r0 = 2
            r2 = 0
            java.lang.String r3 = "?"
            boolean r0 = kotlin.text.StringsKt.endsWith$default(r8, r3, r1, r0, r2)
            if (r0 == 0) goto L34
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r7)
            r2 = 63
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r8)
            if (r0 != 0) goto L50
        L34:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r2 = 40
            r0.append(r2)
            r0.append(r7)
            java.lang.String r7 = ")?"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            if (r7 == 0) goto L51
        L50:
            r1 = 1
        L51:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.differsOnlyInNullability(java.lang.String, java.lang.String):boolean");
    }

    private final boolean overridesSomething(CallableMemberDescriptor callableMemberDescriptor) {
        return !callableMemberDescriptor.getOverriddenDescriptors().isEmpty();
    }
}
