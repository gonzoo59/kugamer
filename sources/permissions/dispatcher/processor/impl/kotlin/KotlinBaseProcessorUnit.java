package permissions.dispatcher.processor.impl.kotlin;

import com.squareup.kotlinpoet.AnnotationSpec;
import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.CodeBlock;
import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.FunSpec;
import com.squareup.kotlinpoet.KModifier;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.PropertySpec;
import com.squareup.kotlinpoet.TypeNames;
import com.squareup.kotlinpoet.TypeSpec;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
import permissions.dispatcher.BuildConfig;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.processor.KtProcessorUnit;
import permissions.dispatcher.processor.RequestCodeProvider;
import permissions.dispatcher.processor.RuntimePermissionsElement;
import permissions.dispatcher.processor.impl.kotlin.SensitivePermissionInterface;
import permissions.dispatcher.processor.util.ConstantsKt;
import permissions.dispatcher.processor.util.ExtensionsKt;
import permissions.dispatcher.processor.util.HelpersKt;
/* compiled from: KotlinBaseProcessorUnit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u000b\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0007H&J(\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0007H\u0002J\"\u0010!\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020#H&J \u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010%\u001a\u00020&H\u0002J\u0018\u0010'\u001a\u00020(2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020\u0007H\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u001dH\u0002J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020.032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u00104\u001a\u0002002\u0006\u00101\u001a\u00020\u001dH\u0002J\u0018\u00105\u001a\u0002062\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u00107\u001a\b\u0012\u0004\u0012\u000206032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u00108\u001a\u00020.2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J$\u00109\u001a\b\u0012\u0004\u0012\u000200032\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001d032\u0006\u0010)\u001a\u00020*H\u0002J\u0018\u0010;\u001a\u0002002\u0006\u00101\u001a\u00020\u001d2\u0006\u0010<\u001a\u00020=H\u0002J\u0018\u0010>\u001a\u00020.2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u001dH\u0002J\u0016\u0010@\u001a\b\u0012\u0004\u0012\u00020.032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0012\u0010A\u001a\u00020\u00072\b\b\u0002\u0010\u0018\u001a\u00020\u0007H&J\u0010\u0010B\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010C\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010D\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010E\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010F\u001a\u00020\u0007H\u0002J\b\u0010G\u001a\u00020#H&R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006H"}, d2 = {"Lpermissions/dispatcher/processor/impl/kotlin/KotlinBaseProcessorUnit;", "Lpermissions/dispatcher/processor/KtProcessorUnit;", "messager", "Ljavax/annotation/processing/Messager;", "(Ljavax/annotation/processing/Messager;)V", "ADD_WITH_CHECK_BODY_MAP", "Ljava/util/HashMap;", "", "Lpermissions/dispatcher/processor/impl/kotlin/SensitivePermissionInterface;", "Lkotlin/collections/HashMap;", "BUILD", "Lcom/squareup/kotlinpoet/ClassName;", "INT_ARRAY", "MANIFEST_SYSTEM_ALERT_WINDOW", "MANIFEST_WRITE_SETTING", "PERMISSION_UTILS", "getPERMISSION_UTILS", "()Lcom/squareup/kotlinpoet/ClassName;", "getMessager", "()Ljavax/annotation/processing/Messager;", "addRequestPermissionsStatement", "", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "targetParam", "permissionField", "requestCodeField", "addResultCaseBody", "needsMethod", "Ljavax/lang/model/element/ExecutableElement;", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "grantResultsParam", "addShouldShowRequestPermissionRationaleCondition", "isPositiveCondition", "", "addWithPermissionCheckBody", "createDeprecatedAnnotation", "Lcom/squareup/kotlinpoet/AnnotationSpec;", "createFile", "Lcom/squareup/kotlinpoet/FileSpec;", "requestCodeProvider", "Lpermissions/dispatcher/processor/RequestCodeProvider;", "createJvmNameAnnotation", "generatedClassName", "createOnActivityResultFun", "Lcom/squareup/kotlinpoet/FunSpec;", "createPendingRequestProperty", "Lcom/squareup/kotlinpoet/PropertySpec;", "e", "createPermissionHandlingFuns", "", "createPermissionProperty", "createPermissionRequestClass", "Lcom/squareup/kotlinpoet/TypeSpec;", "createPermissionRequestClasses", "createPermissionResultFun", "createProperties", "needsElements", "createRequestCodeProperty", "index", "", "createWithPermissionCheckFun", "method", "createWithPermissionCheckFuns", "getActivityName", "hasNormalPermission", "hasSystemAlertWindowPermission", "hasWriteSettingPermission", "isDefinePermission", "permissionName", "isDeprecated", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public abstract class KotlinBaseProcessorUnit implements KtProcessorUnit {
    private final HashMap<String, SensitivePermissionInterface> ADD_WITH_CHECK_BODY_MAP;
    private final ClassName BUILD;
    private final ClassName INT_ARRAY;
    private final String MANIFEST_SYSTEM_ALERT_WINDOW;
    private final String MANIFEST_WRITE_SETTING;
    private final ClassName PERMISSION_UTILS;
    private final Messager messager;

    public abstract void addRequestPermissionsStatement(FunSpec.Builder builder, String str, String str2, String str3);

    public abstract void addShouldShowRequestPermissionRationaleCondition(FunSpec.Builder builder, String str, boolean z);

    public abstract String getActivityName(String str);

    public abstract boolean isDeprecated();

    public KotlinBaseProcessorUnit(Messager messager) {
        Intrinsics.checkParameterIsNotNull(messager, "messager");
        this.messager = messager;
        this.PERMISSION_UTILS = new ClassName(BuildConfig.APPLICATION_ID, "PermissionUtils", new String[0]);
        this.BUILD = new ClassName("android.os", "Build", new String[0]);
        this.MANIFEST_WRITE_SETTING = "android.permission.WRITE_SETTINGS";
        this.MANIFEST_SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
        this.INT_ARRAY = new ClassName("kotlin", "IntArray", new String[0]);
        this.ADD_WITH_CHECK_BODY_MAP = MapsKt.hashMapOf(TuplesKt.to("android.permission.SYSTEM_ALERT_WINDOW", new SystemAlertWindowHelper()), TuplesKt.to("android.permission.WRITE_SETTINGS", new WriteSettingsHelper()));
    }

    public final Messager getMessager() {
        return this.messager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ClassName getPERMISSION_UTILS() {
        return this.PERMISSION_UTILS;
    }

    public static /* bridge */ /* synthetic */ void addRequestPermissionsStatement$default(KotlinBaseProcessorUnit kotlinBaseProcessorUnit, FunSpec.Builder builder, String str, String str2, String str3, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addRequestPermissionsStatement");
        }
        if ((i & 2) != 0) {
            str = "this";
        }
        kotlinBaseProcessorUnit.addRequestPermissionsStatement(builder, str, str2, str3);
    }

    public static /* bridge */ /* synthetic */ void addShouldShowRequestPermissionRationaleCondition$default(KotlinBaseProcessorUnit kotlinBaseProcessorUnit, FunSpec.Builder builder, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addShouldShowRequestPermissionRationaleCondition");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        kotlinBaseProcessorUnit.addShouldShowRequestPermissionRationaleCondition(builder, str, z);
    }

    public static /* bridge */ /* synthetic */ String getActivityName$default(KotlinBaseProcessorUnit kotlinBaseProcessorUnit, String str, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = "this";
            }
            return kotlinBaseProcessorUnit.getActivityName(str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getActivityName");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // permissions.dispatcher.processor.ProcessorUnit
    public FileSpec createFile(RuntimePermissionsElement rpe, RequestCodeProvider requestCodeProvider) {
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(requestCodeProvider, "requestCodeProvider");
        return ExtensionsKt.addTypes(ExtensionsKt.addFunctions(ExtensionsKt.addFunctions(ExtensionsKt.addProperties(FileSpec.Companion.builder(rpe.getPackageName(), rpe.getGeneratedClassName()).addComment(ConstantsKt.FILE_COMMENT, new Object[0]).addAnnotation(createJvmNameAnnotation(rpe.getGeneratedClassName())), createProperties(rpe.getNeedsElements(), requestCodeProvider)), createWithPermissionCheckFuns(rpe)), createPermissionHandlingFuns(rpe)), createPermissionRequestClasses(rpe)).build();
    }

    private final AnnotationSpec createJvmNameAnnotation(String str) {
        return AnnotationSpec.Companion.builder(new ClassName("", "JvmName", new String[0])).addMember("%S", str).build();
    }

    private final AnnotationSpec createDeprecatedAnnotation() {
        return AnnotationSpec.Companion.builder(Deprecated.class).addMember("%L = %S", "message", ConstantsKt.DEPRECATED_MESSAGE).build();
    }

    private final List<PropertySpec> createProperties(List<? extends ExecutableElement> list, RequestCodeProvider requestCodeProvider) {
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : CollectionsKt.sortedWith(list, new Comparator<T>() { // from class: permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit$createProperties$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(ExtensionsKt.simpleString((ExecutableElement) t), ExtensionsKt.simpleString((ExecutableElement) t2));
            }
        })) {
            arrayList.add(createRequestCodeProperty(executableElement, requestCodeProvider.nextRequestCode()));
            arrayList.add(createPermissionProperty(executableElement));
            List parameters = executableElement.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "it.parameters");
            if (!parameters.isEmpty()) {
                arrayList.add(createPendingRequestProperty(executableElement));
            }
        }
        return arrayList;
    }

    private final PropertySpec createRequestCodeProperty(ExecutableElement executableElement, int i) {
        return PropertySpec.Companion.builder(HelpersKt.requestCodeFieldName(executableElement), Integer.TYPE, KModifier.PRIVATE).initializer("%L", Integer.valueOf(i)).build();
    }

    private final PropertySpec createPermissionProperty(ExecutableElement executableElement) {
        Annotation annotation = executableElement.getAnnotation(NeedsPermission.class);
        Intrinsics.checkExpressionValueIsNotNull(annotation, "e.getAnnotation(NeedsPermission::class.java)");
        String joinToString$default = CollectionsKt.joinToString$default(ExtensionsKt.permissionValue(annotation), ", ", null, null, 0, null, new Function1<String, String>() { // from class: permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit$createPermissionProperty$formattedValue$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return Typography.quote + it + Typography.quote;
            }
        }, 30, null);
        PropertySpec.Builder builder = PropertySpec.Companion.builder(HelpersKt.permissionFieldName(executableElement), ParameterizedTypeName.Companion.get(TypeNames.ARRAY, new ClassName("kotlin", "String", new String[0])), KModifier.PRIVATE);
        return builder.initializer("%N", "arrayOf(" + joinToString$default + ')').build();
    }

    private final PropertySpec createPendingRequestProperty(ExecutableElement executableElement) {
        return PropertySpec.Companion.varBuilder(HelpersKt.pendingRequestFieldName(executableElement), new ClassName(BuildConfig.APPLICATION_ID, "GrantableRequest", new String[0]).asNullable(), KModifier.PRIVATE).initializer(CodeBlock.Companion.of("null", new Object[0])).build();
    }

    private final List<FunSpec> createWithPermissionCheckFuns(RuntimePermissionsElement runtimePermissionsElement) {
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            arrayList.add(createWithPermissionCheckFun(runtimePermissionsElement, executableElement));
        }
        return arrayList;
    }

    private final FunSpec createWithPermissionCheckFun(RuntimePermissionsElement runtimePermissionsElement, ExecutableElement executableElement) {
        FunSpec.Builder receiver = FunSpec.Companion.builder(HelpersKt.withPermissionCheckMethodName(executableElement)).addTypeVariables(runtimePermissionsElement.getKtTypeVariables()).receiver(runtimePermissionsElement.getKtTypeName());
        List<Element> parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "method.parameters");
        for (Element param : parameters) {
            Intrinsics.checkExpressionValueIsNotNull(param, "param");
            receiver.addParameter(ExtensionsKt.simpleString(param), ExtensionsKt.asPreparedType(param), new KModifier[0]);
        }
        addWithPermissionCheckBody(receiver, executableElement, runtimePermissionsElement);
        if (isDeprecated()) {
            receiver.addAnnotation(createDeprecatedAnnotation());
        }
        return receiver.build();
    }

    private final void addWithPermissionCheckBody(FunSpec.Builder builder, ExecutableElement executableElement, RuntimePermissionsElement runtimePermissionsElement) {
        String requestCodeFieldName = HelpersKt.requestCodeFieldName(executableElement);
        String permissionFieldName = HelpersKt.permissionFieldName(executableElement);
        int maxSdkVersion = ((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).maxSdkVersion();
        if (maxSdkVersion > 0) {
            builder.beginControlFlow("if (%T.VERSION.SDK_INT > %L)", this.BUILD, Integer.valueOf(maxSdkVersion)).addCode(CodeBlock.Companion.builder().add("%N(", ExtensionsKt.simpleString((Element) executableElement)).add(HelpersKt.varargsKtParametersCodeBlock(executableElement)).addStatement(")", new Object[0]).addStatement("return", new Object[0]).build()).endControlFlow();
        }
        String str = ((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0];
        String activityName$default = getActivityName$default(this, null, 1, null);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(str);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addHasSelfPermissionsCondition(builder, activityName$default, permissionFieldName);
        } else {
            builder.beginControlFlow("if (%T.hasSelfPermissions(%N, *%N))", this.PERMISSION_UTILS, activityName$default, permissionFieldName);
        }
        builder.addCode(CodeBlock.Companion.builder().add("%N(", ExtensionsKt.simpleString((Element) executableElement)).add(HelpersKt.varargsKtParametersCodeBlock(executableElement)).addStatement(")", new Object[0]).build());
        builder.nextControlFlow("else", new Object[0]);
        Element findOnRationaleForNeeds = runtimePermissionsElement.findOnRationaleForNeeds(executableElement);
        List parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsMethod.parameters");
        boolean z = !parameters.isEmpty();
        if (z) {
            builder.addCode(CodeBlock.Companion.builder().add("%N = %N(this, ", HelpersKt.pendingRequestFieldName(executableElement), HelpersKt.permissionRequestTypeName(runtimePermissionsElement, executableElement)).add(HelpersKt.varargsKtParametersCodeBlock(executableElement)).addStatement(")", new Object[0]).build());
        }
        if (findOnRationaleForNeeds != null) {
            addShouldShowRequestPermissionRationaleCondition$default(this, builder, permissionFieldName, false, 4, null);
            if (z) {
                builder.addStatement("%N?.let { %N(it) }", HelpersKt.pendingRequestFieldName(executableElement), ExtensionsKt.simpleString(findOnRationaleForNeeds));
            } else {
                builder.addStatement("%N(%N(this))", ExtensionsKt.simpleString(findOnRationaleForNeeds), HelpersKt.permissionRequestTypeName(runtimePermissionsElement, executableElement));
            }
            builder.nextControlFlow("else", new Object[0]);
        }
        SensitivePermissionInterface sensitivePermissionInterface2 = this.ADD_WITH_CHECK_BODY_MAP.get(str);
        if (sensitivePermissionInterface2 != null) {
            SensitivePermissionInterface.DefaultImpls.addRequestPermissionsStatement$default(sensitivePermissionInterface2, builder, null, getActivityName$default(this, null, 1, null), requestCodeFieldName, 2, null);
        } else {
            addRequestPermissionsStatement$default(this, builder, null, permissionFieldName, requestCodeFieldName, 2, null);
        }
        if (findOnRationaleForNeeds != null) {
            builder.endControlFlow();
        }
        builder.endControlFlow();
    }

    private final List<FunSpec> createPermissionHandlingFuns(RuntimePermissionsElement runtimePermissionsElement) {
        ArrayList arrayList = new ArrayList();
        if (hasNormalPermission(runtimePermissionsElement)) {
            arrayList.add(createPermissionResultFun(runtimePermissionsElement));
        }
        if (hasSystemAlertWindowPermission(runtimePermissionsElement) || hasWriteSettingPermission(runtimePermissionsElement)) {
            arrayList.add(createOnActivityResultFun(runtimePermissionsElement));
        }
        return arrayList;
    }

    private final FunSpec createOnActivityResultFun(RuntimePermissionsElement runtimePermissionsElement) {
        FunSpec.Builder addParameter = FunSpec.Companion.builder("onActivityResult").addTypeVariables(runtimePermissionsElement.getKtTypeVariables()).receiver(runtimePermissionsElement.getKtTypeName()).addParameter("requestCode", TypeNames.INT, new KModifier[0]);
        addParameter.beginControlFlow("when (%N)", "requestCode");
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            if (this.ADD_WITH_CHECK_BODY_MAP.containsKey(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0])) {
                addParameter.beginControlFlow("%N ->", HelpersKt.requestCodeFieldName(executableElement));
                addResultCaseBody(addParameter, executableElement, runtimePermissionsElement, "grantResults");
                addParameter.endControlFlow();
            }
        }
        addParameter.endControlFlow();
        return addParameter.build();
    }

    private final FunSpec createPermissionResultFun(RuntimePermissionsElement runtimePermissionsElement) {
        FunSpec.Builder addParameter = FunSpec.Companion.builder("onRequestPermissionsResult").addTypeVariables(runtimePermissionsElement.getKtTypeVariables()).receiver(runtimePermissionsElement.getKtTypeName()).addParameter("requestCode", TypeNames.INT, new KModifier[0]).addParameter("grantResults", this.INT_ARRAY, new KModifier[0]);
        addParameter.beginControlFlow("when (%N)", "requestCode");
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            if (!this.ADD_WITH_CHECK_BODY_MAP.containsKey(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0])) {
                addParameter.beginControlFlow("%N ->\n", HelpersKt.requestCodeFieldName(executableElement));
                addResultCaseBody(addParameter, executableElement, runtimePermissionsElement, "grantResults");
                addParameter.endControlFlow();
            }
        }
        addParameter.endControlFlow();
        return addParameter.build();
    }

    private final void addResultCaseBody(FunSpec.Builder builder, ExecutableElement executableElement, RuntimePermissionsElement runtimePermissionsElement, String str) {
        Element findOnDeniedForNeeds = runtimePermissionsElement.findOnDeniedForNeeds(executableElement);
        boolean z = findOnDeniedForNeeds != null;
        String str2 = ((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0];
        String permissionFieldName = HelpersKt.permissionFieldName(executableElement);
        String activityName$default = getActivityName$default(this, null, 1, null);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(str2);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addHasSelfPermissionsCondition(builder, activityName$default, permissionFieldName);
        } else {
            builder.beginControlFlow("if (%T.verifyPermissions(*%N))", this.PERMISSION_UTILS, str);
        }
        List parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsMethod.parameters");
        boolean z2 = !parameters.isEmpty();
        if (z2) {
            builder.addStatement("%N?.grant()", HelpersKt.pendingRequestFieldName(executableElement));
        } else {
            builder.addStatement("%N()", ExtensionsKt.simpleString((Element) executableElement));
        }
        Element findOnNeverAskForNeeds = runtimePermissionsElement.findOnNeverAskForNeeds(executableElement);
        boolean z3 = findOnNeverAskForNeeds != null;
        if (z || z3) {
            builder.nextControlFlow("else", new Object[0]);
        }
        if (findOnNeverAskForNeeds != null) {
            addShouldShowRequestPermissionRationaleCondition(builder, HelpersKt.permissionFieldName(executableElement), false);
            builder.addStatement("%N()", ExtensionsKt.simpleString(findOnNeverAskForNeeds));
            if (z) {
                builder.nextControlFlow("else", new Object[0]);
            } else {
                builder.endControlFlow();
            }
        }
        if (z) {
            Object[] objArr = new Object[1];
            if (findOnDeniedForNeeds == null) {
                Intrinsics.throwNpe();
            }
            objArr[0] = ExtensionsKt.simpleString(findOnDeniedForNeeds);
            builder.addStatement("%N()", objArr);
            if (z3) {
                builder.endControlFlow();
            }
        }
        builder.endControlFlow();
        if (z2) {
            builder.addStatement("%N = null", HelpersKt.pendingRequestFieldName(executableElement));
        }
    }

    private final boolean hasNormalPermission(RuntimePermissionsElement runtimePermissionsElement) {
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            Annotation annotation = executableElement.getAnnotation(NeedsPermission.class);
            Intrinsics.checkExpressionValueIsNotNull(annotation, "it.getAnnotation(NeedsPermission::class.java)");
            List<String> permissionValue = ExtensionsKt.permissionValue(annotation);
            if (!permissionValue.contains(this.MANIFEST_SYSTEM_ALERT_WINDOW) && !permissionValue.contains(this.MANIFEST_WRITE_SETTING)) {
                return true;
            }
        }
        return false;
    }

    private final boolean hasSystemAlertWindowPermission(RuntimePermissionsElement runtimePermissionsElement) {
        return isDefinePermission(runtimePermissionsElement, this.MANIFEST_SYSTEM_ALERT_WINDOW);
    }

    private final boolean hasWriteSettingPermission(RuntimePermissionsElement runtimePermissionsElement) {
        return isDefinePermission(runtimePermissionsElement, this.MANIFEST_WRITE_SETTING);
    }

    private final boolean isDefinePermission(RuntimePermissionsElement runtimePermissionsElement, String str) {
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            Annotation annotation = executableElement.getAnnotation(NeedsPermission.class);
            Intrinsics.checkExpressionValueIsNotNull(annotation, "it.getAnnotation(NeedsPermission::class.java)");
            if (ExtensionsKt.permissionValue(annotation).contains(str)) {
                return true;
            }
        }
        return false;
    }

    private final List<TypeSpec> createPermissionRequestClasses(RuntimePermissionsElement runtimePermissionsElement) {
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            if (runtimePermissionsElement.findOnRationaleForNeeds(executableElement) == null) {
                List parameters = executableElement.getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters, "it.parameters");
                if (!parameters.isEmpty()) {
                }
            }
            arrayList.add(createPermissionRequestClass(runtimePermissionsElement, executableElement));
        }
        return arrayList;
    }

    private final TypeSpec createPermissionRequestClass(RuntimePermissionsElement runtimePermissionsElement, ExecutableElement executableElement) {
        List parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsMethod.parameters");
        boolean z = !parameters.isEmpty();
        TypeSpec.Builder addModifiers = TypeSpec.Companion.classBuilder(HelpersKt.permissionRequestTypeName(runtimePermissionsElement, executableElement)).addTypeVariables(runtimePermissionsElement.getKtTypeVariables()).addSuperinterface(new ClassName(BuildConfig.APPLICATION_ID, z ? "GrantableRequest" : ConstantsKt.GEN_PERMISSION_REQUEST_SUFFIX, new String[0])).addModifiers(KModifier.PRIVATE);
        PropertySpec.Builder builder = PropertySpec.Companion.builder("weakTarget", ParameterizedTypeName.Companion.get(new ClassName("java.lang.ref", "WeakReference", new String[0]), runtimePermissionsElement.getKtTypeName()), KModifier.PRIVATE);
        builder.initializer("%N", "WeakReference(target)");
        addModifiers.addProperty(builder.build());
        List<Element> parameters2 = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters2, "needsMethod.parameters");
        for (Element it : parameters2) {
            PropertySpec.Companion companion = PropertySpec.Companion;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Element element = it;
            addModifiers.addProperty(companion.builder(ExtensionsKt.simpleString(element), ExtensionsKt.asPreparedType(it), KModifier.PRIVATE).initializer(CodeBlock.Companion.of(ExtensionsKt.simpleString(element), new Object[0])).build());
        }
        FunSpec.Builder addParameter = FunSpec.Companion.constructorBuilder().addParameter("target", runtimePermissionsElement.getKtTypeName(), new KModifier[0]);
        List<Element> parameters3 = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters3, "needsMethod.parameters");
        for (Element it2 : parameters3) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            addParameter.addParameter(ExtensionsKt.simpleString(it2), ExtensionsKt.asPreparedType(it2), KModifier.PRIVATE);
        }
        addModifiers.primaryConstructor(addParameter.build());
        FunSpec.Builder addStatement = FunSpec.Companion.builder("proceed").addModifiers(KModifier.OVERRIDE).addStatement("val target = %N.get() ?: return", "weakTarget");
        String requestCodeFieldName = HelpersKt.requestCodeFieldName(executableElement);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0]);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addRequestPermissionsStatement(addStatement, "target", getActivityName("target"), requestCodeFieldName);
        } else {
            addRequestPermissionsStatement(addStatement, "target", HelpersKt.permissionFieldName(executableElement), requestCodeFieldName);
        }
        addModifiers.addFunction(addStatement.build());
        FunSpec.Builder addModifiers2 = FunSpec.Companion.builder("cancel").addModifiers(KModifier.OVERRIDE);
        Element findOnDeniedForNeeds = runtimePermissionsElement.findOnDeniedForNeeds(executableElement);
        if (findOnDeniedForNeeds != null) {
            addModifiers2.addStatement("val target = %N.get() ?: return", "weakTarget").addStatement("target.%N()", ExtensionsKt.simpleString(findOnDeniedForNeeds));
        }
        addModifiers.addFunction(addModifiers2.build());
        if (z) {
            FunSpec.Builder addStatement2 = FunSpec.Companion.builder("grant").addModifiers(KModifier.OVERRIDE).addStatement("val target = %N.get() ?: return", "weakTarget");
            addStatement2.addCode(CodeBlock.Companion.builder().add("target.%N(", ExtensionsKt.simpleString((Element) executableElement)).add(HelpersKt.varargsKtParametersCodeBlock(executableElement)).addStatement(")", new Object[0]).build());
            addModifiers.addFunction(addStatement2.build());
        }
        return addModifiers.build();
    }
}
