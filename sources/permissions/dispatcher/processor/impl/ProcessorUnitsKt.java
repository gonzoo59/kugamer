package permissions.dispatcher.processor.impl;

import java.util.List;
import javax.annotation.processing.Messager;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.processor.impl.java.JavaActivityProcessorUnit;
import permissions.dispatcher.processor.impl.java.JavaBaseProcessorUnit;
import permissions.dispatcher.processor.impl.java.JavaNativeFragmentProcessorUnit;
import permissions.dispatcher.processor.impl.java.JavaSupportFragmentProcessorUnit;
import permissions.dispatcher.processor.impl.kotlin.KotlinActivityProcessorUnit;
import permissions.dispatcher.processor.impl.kotlin.KotlinBaseProcessorUnit;
import permissions.dispatcher.processor.impl.kotlin.KotlinNativeFragmentProcessorUnit;
import permissions.dispatcher.processor.impl.kotlin.KotlinSupportFragmentProcessorUnit;
/* compiled from: ProcessorUnits.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"javaProcessorUnits", "", "Lpermissions/dispatcher/processor/impl/java/JavaBaseProcessorUnit;", "messager", "Ljavax/annotation/processing/Messager;", "kotlinProcessorUnits", "Lpermissions/dispatcher/processor/impl/kotlin/KotlinBaseProcessorUnit;", "processor"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes2.dex */
public final class ProcessorUnitsKt {
    public static final List<JavaBaseProcessorUnit> javaProcessorUnits(Messager messager) {
        Intrinsics.checkParameterIsNotNull(messager, "messager");
        return CollectionsKt.listOf((Object[]) new JavaBaseProcessorUnit[]{new JavaActivityProcessorUnit(messager), new JavaSupportFragmentProcessorUnit(messager), new JavaNativeFragmentProcessorUnit(messager)});
    }

    public static final List<KotlinBaseProcessorUnit> kotlinProcessorUnits(Messager messager) {
        Intrinsics.checkParameterIsNotNull(messager, "messager");
        return CollectionsKt.listOf((Object[]) new KotlinBaseProcessorUnit[]{new KotlinActivityProcessorUnit(messager), new KotlinSupportFragmentProcessorUnit(messager), new KotlinNativeFragmentProcessorUnit(messager)});
    }
}
