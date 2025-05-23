package permissions.dispatcher.processor.impl.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import javax.annotation.processing.Messager;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.util.HelpersKt;
/* compiled from: JavaActivityProcessorUnit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016J(\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0012H\u0016R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lpermissions/dispatcher/processor/impl/java/JavaActivityProcessorUnit;", "Lpermissions/dispatcher/processor/impl/java/JavaBaseProcessorUnit;", "messager", "Ljavax/annotation/processing/Messager;", "(Ljavax/annotation/processing/Messager;)V", "ACTIVITY_COMPAT", "Lcom/squareup/javapoet/ClassName;", "kotlin.jvm.PlatformType", "addRequestPermissionsStatement", "", "builder", "Lcom/squareup/javapoet/MethodSpec$Builder;", "targetParam", "", "permissionField", "requestCodeField", "addShouldShowRequestPermissionRationaleCondition", "isPositiveCondition", "", "getActivityName", "getTargetType", "Ljavax/lang/model/type/TypeMirror;", "isDeprecated", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class JavaActivityProcessorUnit extends JavaBaseProcessorUnit {
    private final ClassName ACTIVITY_COMPAT;

    @Override // permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit
    public String getActivityName(String targetParam) {
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        return targetParam;
    }

    @Override // permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit
    public boolean isDeprecated() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaActivityProcessorUnit(Messager messager) {
        super(messager);
        Intrinsics.checkParameterIsNotNull(messager, "messager");
        this.ACTIVITY_COMPAT = ClassName.get("androidx.core.app", "ActivityCompat", new String[0]);
    }

    @Override // permissions.dispatcher.processor.ProcessorUnit
    public TypeMirror getTargetType() {
        return HelpersKt.typeMirrorOf("android.app.Activity");
    }

    @Override // permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit
    public void addShouldShowRequestPermissionRationaleCondition(MethodSpec.Builder builder, String targetParam, String permissionField, boolean z) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        Object[] objArr = new Object[4];
        objArr[0] = z ? "" : "!";
        objArr[1] = getPERMISSION_UTILS();
        objArr[2] = targetParam;
        objArr[3] = permissionField;
        builder.beginControlFlow("if ($N$T.shouldShowRequestPermissionRationale($N, $N))", objArr);
    }

    @Override // permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit
    public void addRequestPermissionsStatement(MethodSpec.Builder builder, String targetParam, String permissionField, String requestCodeField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        Intrinsics.checkParameterIsNotNull(requestCodeField, "requestCodeField");
        builder.addStatement("$T.requestPermissions($N, $N, $N)", this.ACTIVITY_COMPAT, targetParam, permissionField, requestCodeField);
    }
}
