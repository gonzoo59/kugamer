package permissions.dispatcher.processor.impl.kotlin;

import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.FunSpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.BuildConfig;
/* compiled from: WriteSettingsHelper.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J(\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lpermissions/dispatcher/processor/impl/kotlin/WriteSettingsHelper;", "Lpermissions/dispatcher/processor/impl/kotlin/SensitivePermissionInterface;", "()V", "INTENT", "Lcom/squareup/kotlinpoet/ClassName;", "PERMISSION_UTILS", "SETTINGS", "URI", "addHasSelfPermissionsCondition", "", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "activity", "", "permissionField", "addRequestPermissionsStatement", "targetParam", "activityVar", "requestCodeField", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class WriteSettingsHelper implements SensitivePermissionInterface {
    private final ClassName PERMISSION_UTILS = new ClassName(BuildConfig.APPLICATION_ID, "PermissionUtils", new String[0]);
    private final ClassName SETTINGS = new ClassName("android.provider", "Settings", new String[0]);
    private final ClassName INTENT = new ClassName("android.content", "Intent", new String[0]);
    private final ClassName URI = new ClassName("android.net", "Uri", new String[0]);

    @Override // permissions.dispatcher.processor.impl.kotlin.SensitivePermissionInterface
    public void addHasSelfPermissionsCondition(FunSpec.Builder builder, String activity, String permissionField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        builder.beginControlFlow("if (%T.hasSelfPermissions(%N, *%N) || %T.System.canWrite(%N))", this.PERMISSION_UTILS, activity, permissionField, this.SETTINGS, activity);
    }

    @Override // permissions.dispatcher.processor.impl.kotlin.SensitivePermissionInterface
    public void addRequestPermissionsStatement(FunSpec.Builder builder, String targetParam, String activityVar, String requestCodeField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        Intrinsics.checkParameterIsNotNull(activityVar, "activityVar");
        Intrinsics.checkParameterIsNotNull(requestCodeField, "requestCodeField");
        builder.addStatement("val intent = %T(%T.ACTION_MANAGE_WRITE_SETTINGS, %T.parse(\"package:\" + %N!!.getPackageName()))", this.INTENT, this.SETTINGS, this.URI, activityVar);
        builder.addStatement("%N.startActivityForResult(intent, %N)", targetParam, requestCodeField);
    }
}
