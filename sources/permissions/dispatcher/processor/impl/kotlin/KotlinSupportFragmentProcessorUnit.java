package permissions.dispatcher.processor.impl.kotlin;

import com.squareup.kotlinpoet.FunSpec;
import javax.annotation.processing.Messager;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.util.HelpersKt;
/* compiled from: KotlinSupportFragmentProcessorUnit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016J \u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000fH\u0016¨\u0006\u0014"}, d2 = {"Lpermissions/dispatcher/processor/impl/kotlin/KotlinSupportFragmentProcessorUnit;", "Lpermissions/dispatcher/processor/impl/kotlin/KotlinBaseProcessorUnit;", "messager", "Ljavax/annotation/processing/Messager;", "(Ljavax/annotation/processing/Messager;)V", "addRequestPermissionsStatement", "", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "targetParam", "", "permissionField", "requestCodeField", "addShouldShowRequestPermissionRationaleCondition", "isPositiveCondition", "", "getActivityName", "getTargetType", "Ljavax/lang/model/type/TypeMirror;", "isDeprecated", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class KotlinSupportFragmentProcessorUnit extends KotlinBaseProcessorUnit {
    @Override // permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit
    public boolean isDeprecated() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinSupportFragmentProcessorUnit(Messager messager) {
        super(messager);
        Intrinsics.checkParameterIsNotNull(messager, "messager");
    }

    @Override // permissions.dispatcher.processor.ProcessorUnit
    public TypeMirror getTargetType() {
        return HelpersKt.typeMirrorOf("androidx.fragment.app.Fragment");
    }

    @Override // permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit
    public String getActivityName(String targetParam) {
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        return targetParam + ".activity";
    }

    @Override // permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit
    public void addShouldShowRequestPermissionRationaleCondition(FunSpec.Builder builder, String permissionField, boolean z) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        builder.beginControlFlow("if (%N%T.shouldShowRequestPermissionRationale(%N, *%N))", z ? "" : "!", getPERMISSION_UTILS(), "this", permissionField);
    }

    @Override // permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit
    public void addRequestPermissionsStatement(FunSpec.Builder builder, String targetParam, String permissionField, String requestCodeField) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.checkParameterIsNotNull(targetParam, "targetParam");
        Intrinsics.checkParameterIsNotNull(permissionField, "permissionField");
        Intrinsics.checkParameterIsNotNull(requestCodeField, "requestCodeField");
        builder.addStatement("%N.requestPermissions(%N, %N)", targetParam, permissionField, requestCodeField);
    }
}
