package permissions.dispatcher.processor.impl.java;

import com.squareup.javapoet.MethodSpec;
import kotlin.Metadata;
/* compiled from: SensitivePermissionInterface.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J(\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H&¨\u0006\f"}, d2 = {"Lpermissions/dispatcher/processor/impl/java/SensitivePermissionInterface;", "", "addHasSelfPermissionsCondition", "", "builder", "Lcom/squareup/javapoet/MethodSpec$Builder;", "activityVar", "", "permissionField", "addRequestPermissionsStatement", "targetParam", "requestCodeField", "processor"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public interface SensitivePermissionInterface {
    void addHasSelfPermissionsCondition(MethodSpec.Builder builder, String str, String str2);

    void addRequestPermissionsStatement(MethodSpec.Builder builder, String str, String str2, String str3);
}
