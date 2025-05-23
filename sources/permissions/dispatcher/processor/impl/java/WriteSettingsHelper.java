package permissions.dispatcher.processor.impl.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.BuildConfig;
/* compiled from: WriteSettingsHelper.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J(\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0016R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lpermissions/dispatcher/processor/impl/java/WriteSettingsHelper;", "Lpermissions/dispatcher/processor/impl/java/SensitivePermissionInterface;", "()V", "INTENT", "Lcom/squareup/javapoet/ClassName;", "kotlin.jvm.PlatformType", "PERMISSION_UTILS", "SETTINGS", "URI", "addHasSelfPermissionsCondition", "", "builder", "Lcom/squareup/javapoet/MethodSpec$Builder;", "activityVar", "", "permissionField", "addRequestPermissionsStatement", "targetParam", "requestCodeField", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class WriteSettingsHelper implements SensitivePermissionInterface {
    private final ClassName PERMISSION_UTILS = ClassName.get(BuildConfig.APPLICATION_ID, "PermissionUtils", new String[0]);
    private final ClassName SETTINGS = ClassName.get("android.provider", "Settings", new String[0]);
    private final ClassName INTENT = ClassName.get("android.content", "Intent", new String[0]);
    private final ClassName URI = ClassName.get("android.net", "Uri", new String[0]);

    @Override // permissions.dispatcher.processor.impl.java.SensitivePermissionInterface
    public void addHasSelfPermissionsCondition(MethodSpec.Builder builder, String activityVar, String permissionField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(activityVar, "activityVar");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        builder.beginControlFlow("if ($T.hasSelfPermissions($N, $N) || $T.System.canWrite($N))", this.PERMISSION_UTILS, activityVar, permissionField, this.SETTINGS, activityVar);
    }

    @Override // permissions.dispatcher.processor.impl.java.SensitivePermissionInterface
    public void addRequestPermissionsStatement(MethodSpec.Builder builder, String targetParam, String activityVar, String requestCodeField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        Intrinsics.checkParameterIsNotNull(activityVar, "activityVar");
        Intrinsics.checkParameterIsNotNull(requestCodeField, "requestCodeField");
        ClassName className = this.INTENT;
        builder.addStatement("$T intent = new $T($T.ACTION_MANAGE_WRITE_SETTINGS, $T.parse(\"package:\" + $N.getPackageName()))", className, className, this.SETTINGS, this.URI, activityVar);
        builder.addStatement("$N.startActivityForResult(intent, $N)", targetParam, requestCodeField);
    }
}
