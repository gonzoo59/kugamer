package kotlin.reflect.jvm;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.internal.EmptyContainerForLocal;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
/* compiled from: reflectLambda.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003¨\u0006\u0004"}, d2 = {"reflect", "Lkotlin/reflect/KFunction;", "R", "Lkotlin/Function;", "kotlin-reflection"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ReflectLambdaKt {
    public static final <R> KFunction<R> reflect(Function<? extends R> reflect) {
        Intrinsics.checkParameterIsNotNull(reflect, "$this$reflect");
        Metadata metadata = (Metadata) reflect.getClass().getAnnotation(Metadata.class);
        if (metadata != null) {
            String[] d1 = metadata.d1();
            if (d1.length == 0) {
                d1 = null;
            }
            if (d1 != null) {
                Pair<JvmNameResolver, ProtoBuf.Function> readFunctionDataFrom = JvmProtoBufUtil.readFunctionDataFrom(d1, metadata.d2());
                JvmNameResolver component1 = readFunctionDataFrom.component1();
                ProtoBuf.Function component2 = readFunctionDataFrom.component2();
                JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(metadata.mv(), (metadata.xi() & 8) != 0);
                ProtoBuf.TypeTable typeTable = component2.getTypeTable();
                Intrinsics.checkExpressionValueIsNotNull(typeTable, "proto.typeTable");
                SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) UtilKt.deserializeToDescriptor(reflect.getClass(), component2, component1, new TypeTable(typeTable), jvmMetadataVersion, ReflectLambdaKt$reflect$descriptor$1.INSTANCE);
                if (simpleFunctionDescriptor != null) {
                    return new KFunctionImpl(EmptyContainerForLocal.INSTANCE, simpleFunctionDescriptor);
                }
            }
        }
        return null;
    }
}
