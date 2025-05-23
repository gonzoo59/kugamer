package permissions.dispatcher.processor.impl.java;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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
import permissions.dispatcher.processor.JavaProcessorUnit;
import permissions.dispatcher.processor.RequestCodeProvider;
import permissions.dispatcher.processor.RuntimePermissionsElement;
import permissions.dispatcher.processor.util.ConstantsKt;
import permissions.dispatcher.processor.util.ExtensionsKt;
import permissions.dispatcher.processor.util.HelpersKt;
/* compiled from: JavaBaseProcessorUnit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0007H&J0\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0007H\u0002J*\u0010!\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020#H&J&\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0007J\b\u0010%\u001a\u00020&H\u0002J\b\u0010'\u001a\u00020(H\u0002J$\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001d0*2\u0006\u0010-\u001a\u00020.H\u0002J\u0016\u0010/\u001a\u0002002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.J\u0010\u00101\u001a\u00020&2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u00102\u001a\u00020+2\u0006\u00103\u001a\u00020\u001dH\u0002J\u0010\u00104\u001a\u00020+2\u0006\u00103\u001a\u00020\u001dH\u0002J\u0016\u00105\u001a\b\u0012\u0004\u0012\u00020&0*2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u00106\u001a\u0002072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u00108\u001a\b\u0012\u0004\u0012\u0002070*2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u00109\u001a\u00020&2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010:\u001a\u00020+2\u0006\u00103\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020<H\u0002J\u0018\u0010=\u001a\u0002072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010>\u001a\u00020&2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020\u001dH\u0002J\u0016\u0010@\u001a\b\u0012\u0004\u0012\u00020&0*2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010A\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H&J\u0010\u0010B\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010C\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010D\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010E\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010F\u001a\u00020\u0007H\u0002J\b\u0010G\u001a\u00020#H&R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006H"}, d2 = {"Lpermissions/dispatcher/processor/impl/java/JavaBaseProcessorUnit;", "Lpermissions/dispatcher/processor/JavaProcessorUnit;", "messager", "Ljavax/annotation/processing/Messager;", "(Ljavax/annotation/processing/Messager;)V", "ADD_WITH_CHECK_BODY_MAP", "Ljava/util/HashMap;", "", "Lpermissions/dispatcher/processor/impl/java/SensitivePermissionInterface;", "Lkotlin/collections/HashMap;", "BUILD", "Lcom/squareup/javapoet/ClassName;", "kotlin.jvm.PlatformType", "MANIFEST_SYSTEM_ALERT_WINDOW", "MANIFEST_WRITE_SETTING", "PERMISSION_UTILS", "getPERMISSION_UTILS", "()Lcom/squareup/javapoet/ClassName;", "getMessager", "()Ljavax/annotation/processing/Messager;", "addRequestPermissionsStatement", "", "builder", "Lcom/squareup/javapoet/MethodSpec$Builder;", "targetParam", "permissionField", "requestCodeField", "addResultCaseBody", "needsMethod", "Ljavax/lang/model/element/ExecutableElement;", "rpe", "Lpermissions/dispatcher/processor/RuntimePermissionsElement;", "grantResultsParam", "addShouldShowRequestPermissionRationaleCondition", "isPositiveCondition", "", "addWithPermissionCheckBody", "createConstructor", "Lcom/squareup/javapoet/MethodSpec;", "createDeprecatedAnnotation", "Lcom/squareup/javapoet/AnnotationSpec;", "createFields", "", "Lcom/squareup/javapoet/FieldSpec;", "needsElements", "requestCodeProvider", "Lpermissions/dispatcher/processor/RequestCodeProvider;", "createFile", "Lcom/squareup/javapoet/JavaFile;", "createOnActivityResultMethod", "createPendingRequestField", "e", "createPermissionField", "createPermissionHandlingMethods", "createPermissionRequestClass", "Lcom/squareup/javapoet/TypeSpec;", "createPermissionRequestClasses", "createPermissionResultMethod", "createRequestCodeField", "index", "", "createTypeSpec", "createWithPermissionCheckMethod", "method", "createWithPermissionCheckMethods", "getActivityName", "hasNormalPermission", "hasSystemAlertWindowPermission", "hasWriteSettingPermission", "isDefinePermission", "permissionName", "isDeprecated", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public abstract class JavaBaseProcessorUnit implements JavaProcessorUnit {
    private final HashMap<String, SensitivePermissionInterface> ADD_WITH_CHECK_BODY_MAP;
    private final ClassName BUILD;
    private final String MANIFEST_SYSTEM_ALERT_WINDOW;
    private final String MANIFEST_WRITE_SETTING;
    private final ClassName PERMISSION_UTILS;
    private final Messager messager;

    public abstract void addRequestPermissionsStatement(MethodSpec.Builder builder, String str, String str2, String str3);

    public abstract void addShouldShowRequestPermissionRationaleCondition(MethodSpec.Builder builder, String str, String str2, boolean z);

    public abstract String getActivityName(String str);

    public abstract boolean isDeprecated();

    public JavaBaseProcessorUnit(Messager messager) {
        Intrinsics.checkParameterIsNotNull(messager, "messager");
        this.messager = messager;
        ClassName className = ClassName.get(BuildConfig.APPLICATION_ID, "PermissionUtils", new String[0]);
        Intrinsics.checkExpressionValueIsNotNull(className, "ClassName.get(\"permissio…cher\", \"PermissionUtils\")");
        this.PERMISSION_UTILS = className;
        this.BUILD = ClassName.get("android.os", "Build", new String[0]);
        this.MANIFEST_WRITE_SETTING = "android.permission.WRITE_SETTINGS";
        this.MANIFEST_SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
        this.ADD_WITH_CHECK_BODY_MAP = MapsKt.hashMapOf(TuplesKt.to("android.permission.SYSTEM_ALERT_WINDOW", new SystemAlertWindowHelper()), TuplesKt.to("android.permission.WRITE_SETTINGS", new WriteSettingsHelper()));
    }

    public final Messager getMessager() {
        return this.messager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ClassName getPERMISSION_UTILS() {
        return this.PERMISSION_UTILS;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // permissions.dispatcher.processor.ProcessorUnit
    public final JavaFile createFile(RuntimePermissionsElement rpe, RequestCodeProvider requestCodeProvider) {
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(requestCodeProvider, "requestCodeProvider");
        JavaFile build = JavaFile.builder(rpe.getPackageName(), createTypeSpec(rpe, requestCodeProvider)).addFileComment(ConstantsKt.FILE_COMMENT, new Object[0]).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "JavaFile.builder(rpe.pac…\n                .build()");
        return build;
    }

    public static /* bridge */ /* synthetic */ void addShouldShowRequestPermissionRationaleCondition$default(JavaBaseProcessorUnit javaBaseProcessorUnit, MethodSpec.Builder builder, String str, String str2, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addShouldShowRequestPermissionRationaleCondition");
        }
        if ((i & 8) != 0) {
            z = true;
        }
        javaBaseProcessorUnit.addShouldShowRequestPermissionRationaleCondition(builder, str, str2, z);
    }

    private final TypeSpec createTypeSpec(RuntimePermissionsElement runtimePermissionsElement, RequestCodeProvider requestCodeProvider) {
        TypeSpec.Builder addTypes = TypeSpec.classBuilder(runtimePermissionsElement.getGeneratedClassName()).addModifiers(Modifier.FINAL).addFields(createFields(runtimePermissionsElement.getNeedsElements(), requestCodeProvider)).addMethod(createConstructor()).addMethods(createWithPermissionCheckMethods(runtimePermissionsElement)).addMethods(createPermissionHandlingMethods(runtimePermissionsElement)).addTypes(createPermissionRequestClasses(runtimePermissionsElement));
        if (isDeprecated()) {
            addTypes.addAnnotation(createDeprecatedAnnotation());
        }
        TypeSpec build = addTypes.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "TypeSpec.classBuilder(rp…\n                .build()");
        return build;
    }

    private final AnnotationSpec createDeprecatedAnnotation() {
        AnnotationSpec build = AnnotationSpec.builder(Deprecated.class).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "AnnotationSpec.builder(j…ated::class.java).build()");
        return build;
    }

    private final List<FieldSpec> createFields(List<? extends ExecutableElement> list, RequestCodeProvider requestCodeProvider) {
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : CollectionsKt.sortedWith(list, new Comparator<T>() { // from class: permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit$createFields$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(ExtensionsKt.simpleString((ExecutableElement) t), ExtensionsKt.simpleString((ExecutableElement) t2));
            }
        })) {
            arrayList.add(createRequestCodeField(executableElement, requestCodeProvider.nextRequestCode()));
            arrayList.add(createPermissionField(executableElement));
            List parameters = executableElement.getParameters();
            Intrinsics.checkExpressionValueIsNotNull(parameters, "it.parameters");
            if (!parameters.isEmpty()) {
                arrayList.add(createPendingRequestField(executableElement));
            }
        }
        return arrayList;
    }

    private final FieldSpec createRequestCodeField(ExecutableElement executableElement, int i) {
        FieldSpec build = FieldSpec.builder(Integer.TYPE, HelpersKt.requestCodeFieldName(executableElement), new Modifier[0]).addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$L", Integer.valueOf(i)).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "FieldSpec.builder(Int::c…\n                .build()");
        return build;
    }

    private final FieldSpec createPermissionField(ExecutableElement executableElement) {
        Annotation annotation = executableElement.getAnnotation(NeedsPermission.class);
        Intrinsics.checkExpressionValueIsNotNull(annotation, "e.getAnnotation(NeedsPermission::class.java)");
        String joinToString$default = CollectionsKt.joinToString$default(ExtensionsKt.permissionValue(annotation), ",", "{", "}", 0, null, new Function1<String, String>() { // from class: permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit$createPermissionField$formattedValue$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return Typography.quote + it + Typography.quote;
            }
        }, 24, null);
        FieldSpec.Builder addModifiers = FieldSpec.builder(ArrayTypeName.of(String.class), HelpersKt.permissionFieldName(executableElement), new Modifier[0]).addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        FieldSpec build = addModifiers.initializer("$N", "new String[] " + joinToString$default).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "FieldSpec.builder(ArrayT…\n                .build()");
        return build;
    }

    private final FieldSpec createPendingRequestField(ExecutableElement executableElement) {
        FieldSpec build = FieldSpec.builder(ClassName.get(BuildConfig.APPLICATION_ID, "GrantableRequest", new String[0]), HelpersKt.pendingRequestFieldName(executableElement), new Modifier[0]).addModifiers(Modifier.PRIVATE, Modifier.STATIC).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "FieldSpec.builder(ClassN…\n                .build()");
        return build;
    }

    private final MethodSpec createConstructor() {
        MethodSpec build = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build();
        Intrinsics.checkExpressionValueIsNotNull(build, "MethodSpec.constructorBu…\n                .build()");
        return build;
    }

    private final List<MethodSpec> createWithPermissionCheckMethods(RuntimePermissionsElement runtimePermissionsElement) {
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            arrayList.add(createWithPermissionCheckMethod(runtimePermissionsElement, executableElement));
        }
        return arrayList;
    }

    private final MethodSpec createWithPermissionCheckMethod(RuntimePermissionsElement runtimePermissionsElement, ExecutableElement executableElement) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(HelpersKt.withPermissionCheckMethodName(executableElement)).addTypeVariables(runtimePermissionsElement.getTypeVariables()).addModifiers(Modifier.STATIC).returns(TypeName.VOID).addParameter(runtimePermissionsElement.getTypeName(), "target", new Modifier[0]);
        List<Element> parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "method.parameters");
        for (Element it : parameters) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Element element = it;
            builder.addParameter(HelpersKt.typeNameOf(element), ExtensionsKt.simpleString(element), new Modifier[0]);
        }
        Intrinsics.checkExpressionValueIsNotNull(builder, "builder");
        addWithPermissionCheckBody(builder, executableElement, runtimePermissionsElement, "target");
        MethodSpec build = builder.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "builder.build()");
        return build;
    }

    public final void addWithPermissionCheckBody(MethodSpec.Builder builder, ExecutableElement needsMethod, RuntimePermissionsElement rpe, String targetParam) {
        String str;
        String str2;
        int i;
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(needsMethod, "needsMethod");
        Intrinsics.checkParameterIsNotNull(rpe, "rpe");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        String requestCodeFieldName = HelpersKt.requestCodeFieldName(needsMethod);
        String permissionFieldName = HelpersKt.permissionFieldName(needsMethod);
        int maxSdkVersion = ((NeedsPermission) needsMethod.getAnnotation(NeedsPermission.class)).maxSdkVersion();
        if (maxSdkVersion > 0) {
            builder.beginControlFlow("if ($T.VERSION.SDK_INT > $L)", this.BUILD, Integer.valueOf(maxSdkVersion)).addCode(CodeBlock.builder().add("$N.$N(", targetParam, ExtensionsKt.simpleString((Element) needsMethod)).add(HelpersKt.varargsParametersCodeBlock(needsMethod)).addStatement(")", new Object[0]).addStatement("return", new Object[0]).build()).endControlFlow();
        }
        String str3 = ((NeedsPermission) needsMethod.getAnnotation(NeedsPermission.class)).value()[0];
        String activityName = getActivityName(targetParam);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(str3);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addHasSelfPermissionsCondition(builder, activityName, permissionFieldName);
        } else {
            builder.beginControlFlow("if ($T.hasSelfPermissions($N, $N))", this.PERMISSION_UTILS, activityName, permissionFieldName);
        }
        builder.addCode(CodeBlock.builder().add("$N.$N(", targetParam, ExtensionsKt.simpleString((Element) needsMethod)).add(HelpersKt.varargsParametersCodeBlock(needsMethod)).addStatement(")", new Object[0]).build());
        builder.nextControlFlow("else", new Object[0]);
        Element findOnRationaleForNeeds = rpe.findOnRationaleForNeeds(needsMethod);
        List parameters = needsMethod.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsMethod.parameters");
        boolean z = !parameters.isEmpty();
        if (z) {
            builder.addCode(CodeBlock.builder().add("$N = new $N($N, ", HelpersKt.pendingRequestFieldName(needsMethod), HelpersKt.permissionRequestTypeName(rpe, needsMethod), targetParam).add(HelpersKt.varargsParametersCodeBlock(needsMethod)).addStatement(")", new Object[0]).build());
        }
        if (findOnRationaleForNeeds != null) {
            str = activityName;
            str2 = str3;
            addShouldShowRequestPermissionRationaleCondition$default(this, builder, targetParam, permissionFieldName, false, 8, null);
            if (z) {
                i = 0;
                builder.addStatement("$N.$N($N)", targetParam, ExtensionsKt.simpleString(findOnRationaleForNeeds), HelpersKt.pendingRequestFieldName(needsMethod));
            } else {
                i = 0;
                builder.addStatement("$N.$N(new $N($N))", targetParam, ExtensionsKt.simpleString(findOnRationaleForNeeds), HelpersKt.permissionRequestTypeName(rpe, needsMethod), targetParam);
            }
            builder.nextControlFlow("else", new Object[i]);
        } else {
            str = activityName;
            str2 = str3;
        }
        SensitivePermissionInterface sensitivePermissionInterface2 = this.ADD_WITH_CHECK_BODY_MAP.get(str2);
        if (sensitivePermissionInterface2 != null) {
            sensitivePermissionInterface2.addRequestPermissionsStatement(builder, targetParam, str, requestCodeFieldName);
        } else {
            addRequestPermissionsStatement(builder, targetParam, permissionFieldName, requestCodeFieldName);
        }
        if (findOnRationaleForNeeds != null) {
            builder.endControlFlow();
        }
        builder.endControlFlow();
    }

    private final List<MethodSpec> createPermissionHandlingMethods(RuntimePermissionsElement runtimePermissionsElement) {
        ArrayList arrayList = new ArrayList();
        if (hasNormalPermission(runtimePermissionsElement)) {
            arrayList.add(createPermissionResultMethod(runtimePermissionsElement));
        }
        if (hasSystemAlertWindowPermission(runtimePermissionsElement) || hasWriteSettingPermission(runtimePermissionsElement)) {
            arrayList.add(createOnActivityResultMethod(runtimePermissionsElement));
        }
        return arrayList;
    }

    private final MethodSpec createOnActivityResultMethod(RuntimePermissionsElement runtimePermissionsElement) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("onActivityResult").addTypeVariables(runtimePermissionsElement.getTypeVariables()).addModifiers(Modifier.STATIC).returns(TypeName.VOID).addParameter(runtimePermissionsElement.getTypeName(), "target", new Modifier[0]).addParameter(TypeName.INT, "requestCode", new Modifier[0]);
        builder.beginControlFlow("switch ($N)", "requestCode");
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            if (this.ADD_WITH_CHECK_BODY_MAP.containsKey(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0])) {
                builder.addCode("case $N:\n", HelpersKt.requestCodeFieldName(executableElement));
                Intrinsics.checkExpressionValueIsNotNull(builder, "builder");
                addResultCaseBody(builder, executableElement, runtimePermissionsElement, "target", "grantResults");
            }
        }
        builder.addCode("default:\n", new Object[0]).addStatement("break", new Object[0]).endControlFlow();
        MethodSpec build = builder.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "builder.build()");
        return build;
    }

    private final MethodSpec createPermissionResultMethod(RuntimePermissionsElement runtimePermissionsElement) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("onRequestPermissionsResult").addTypeVariables(runtimePermissionsElement.getTypeVariables()).addModifiers(Modifier.STATIC).returns(TypeName.VOID).addParameter(runtimePermissionsElement.getTypeName(), "target", new Modifier[0]).addParameter(TypeName.INT, "requestCode", new Modifier[0]).addParameter(ArrayTypeName.of(TypeName.INT), "grantResults", new Modifier[0]);
        builder.beginControlFlow("switch ($N)", "requestCode");
        for (ExecutableElement executableElement : runtimePermissionsElement.getNeedsElements()) {
            if (!this.ADD_WITH_CHECK_BODY_MAP.containsKey(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0])) {
                builder.addCode("case $N:\n", HelpersKt.requestCodeFieldName(executableElement));
                Intrinsics.checkExpressionValueIsNotNull(builder, "builder");
                addResultCaseBody(builder, executableElement, runtimePermissionsElement, "target", "grantResults");
            }
        }
        builder.addCode("default:\n", new Object[0]).addStatement("break", new Object[0]).endControlFlow();
        MethodSpec build = builder.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "builder.build()");
        return build;
    }

    private final void addResultCaseBody(MethodSpec.Builder builder, ExecutableElement executableElement, RuntimePermissionsElement runtimePermissionsElement, String str, String str2) {
        Element findOnDeniedForNeeds = runtimePermissionsElement.findOnDeniedForNeeds(executableElement);
        boolean z = findOnDeniedForNeeds != null;
        String str3 = ((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0];
        String permissionFieldName = HelpersKt.permissionFieldName(executableElement);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(str3);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addHasSelfPermissionsCondition(builder, getActivityName(str), permissionFieldName);
        } else {
            builder.beginControlFlow("if ($T.verifyPermissions($N))", this.PERMISSION_UTILS, str2);
        }
        List parameters = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "needsMethod.parameters");
        boolean z2 = !parameters.isEmpty();
        if (z2) {
            String pendingRequestFieldName = HelpersKt.pendingRequestFieldName(executableElement);
            builder.beginControlFlow("if ($N != null)", pendingRequestFieldName);
            builder.addStatement("$N.grant()", pendingRequestFieldName);
            builder.endControlFlow();
        } else {
            builder.addStatement("target.$N()", ExtensionsKt.simpleString((Element) executableElement));
        }
        Element findOnNeverAskForNeeds = runtimePermissionsElement.findOnNeverAskForNeeds(executableElement);
        boolean z3 = findOnNeverAskForNeeds != null;
        if (z || z3) {
            builder.nextControlFlow("else", new Object[0]);
        }
        if (z3) {
            addShouldShowRequestPermissionRationaleCondition(builder, str, HelpersKt.permissionFieldName(executableElement), false);
            Object[] objArr = new Object[1];
            if (findOnNeverAskForNeeds == null) {
                Intrinsics.throwNpe();
            }
            objArr[0] = ExtensionsKt.simpleString(findOnNeverAskForNeeds);
            builder.addStatement("target.$N()", objArr);
            if (z) {
                builder.nextControlFlow("else", new Object[0]);
            } else {
                builder.endControlFlow();
            }
        }
        if (z) {
            Object[] objArr2 = new Object[2];
            objArr2[0] = str;
            if (findOnDeniedForNeeds == null) {
                Intrinsics.throwNpe();
            }
            objArr2[1] = ExtensionsKt.simpleString(findOnDeniedForNeeds);
            builder.addStatement("$N.$N()", objArr2);
            if (z3) {
                builder.endControlFlow();
            }
        }
        builder.endControlFlow();
        if (z2) {
            builder.addStatement("$N = null", HelpersKt.pendingRequestFieldName(executableElement));
        }
        builder.addStatement("break", new Object[0]);
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
        String str = z ? "GrantableRequest" : ConstantsKt.GEN_PERMISSION_REQUEST_SUFFIX;
        TypeName typeName = runtimePermissionsElement.getTypeName();
        TypeSpec.Builder addModifiers = TypeSpec.classBuilder(HelpersKt.permissionRequestTypeName(runtimePermissionsElement, executableElement)).addTypeVariables(runtimePermissionsElement.getTypeVariables()).addSuperinterface(ClassName.get(BuildConfig.APPLICATION_ID, str, new String[0])).addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        addModifiers.addField(ParameterizedTypeName.get(ClassName.get("java.lang.ref", "WeakReference", new String[0]), typeName), "weakTarget", Modifier.PRIVATE, Modifier.FINAL);
        List<Element> parameters2 = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters2, "needsMethod.parameters");
        for (Element it : parameters2) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Element element = it;
            addModifiers.addField(HelpersKt.typeNameOf(element), ExtensionsKt.simpleString(element), Modifier.PRIVATE, Modifier.FINAL);
        }
        MethodSpec.Builder addStatement = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).addParameter(typeName, "target", new Modifier[0]).addStatement("this.$L = new WeakReference<$T>($N)", "weakTarget", typeName, "target");
        List<Element> parameters3 = executableElement.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters3, "needsMethod.parameters");
        for (Element it2 : parameters3) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            Element element2 = it2;
            String simpleString = ExtensionsKt.simpleString(element2);
            addStatement.addParameter(HelpersKt.typeNameOf(element2), simpleString, new Modifier[0]).addStatement("this.$L = $N", simpleString, simpleString);
        }
        addModifiers.addMethod(addStatement.build());
        MethodSpec.Builder addStatement2 = MethodSpec.methodBuilder("proceed").addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).returns(TypeName.VOID).addStatement("$T target = $N.get()", typeName, "weakTarget").addStatement("if (target == null) return", new Object[0]);
        Intrinsics.checkExpressionValueIsNotNull(addStatement2, "MethodSpec.methodBuilder…(target == null) return\")");
        String requestCodeFieldName = HelpersKt.requestCodeFieldName(executableElement);
        SensitivePermissionInterface sensitivePermissionInterface = this.ADD_WITH_CHECK_BODY_MAP.get(((NeedsPermission) executableElement.getAnnotation(NeedsPermission.class)).value()[0]);
        if (sensitivePermissionInterface != null) {
            sensitivePermissionInterface.addRequestPermissionsStatement(addStatement2, "target", getActivityName("target"), requestCodeFieldName);
        } else {
            addRequestPermissionsStatement(addStatement2, "target", HelpersKt.permissionFieldName(executableElement), requestCodeFieldName);
        }
        addModifiers.addMethod(addStatement2.build());
        MethodSpec.Builder returns = MethodSpec.methodBuilder("cancel").addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).returns(TypeName.VOID);
        Intrinsics.checkExpressionValueIsNotNull(returns, "MethodSpec.methodBuilder…  .returns(TypeName.VOID)");
        Element findOnDeniedForNeeds = runtimePermissionsElement.findOnDeniedForNeeds(executableElement);
        if (findOnDeniedForNeeds != null) {
            returns.addStatement("$T target = $N.get()", typeName, "weakTarget").addStatement("if (target == null) return", new Object[0]).addStatement("target.$N()", ExtensionsKt.simpleString(findOnDeniedForNeeds));
        }
        addModifiers.addMethod(returns.build());
        if (z) {
            MethodSpec.Builder returns2 = MethodSpec.methodBuilder("grant").addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).returns(TypeName.VOID);
            Intrinsics.checkExpressionValueIsNotNull(returns2, "MethodSpec.methodBuilder…  .returns(TypeName.VOID)");
            returns2.addStatement("$T target = $N.get()", typeName, "weakTarget").addStatement("if (target == null) return", new Object[0]);
            returns2.addCode(CodeBlock.builder().add("target.$N(", ExtensionsKt.simpleString((Element) executableElement)).add(HelpersKt.varargsParametersCodeBlock(executableElement)).addStatement(")", new Object[0]).build());
            addModifiers.addMethod(returns2.build());
        }
        TypeSpec build = addModifiers.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "builder.build()");
        return build;
    }
}
