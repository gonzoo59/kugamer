package com.squareup.kotlinpoet;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ParameterSpec.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u00012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0006H\u0000¨\u0006\b"}, d2 = {"emit", "Lcom/squareup/kotlinpoet/CodeWriter;", "", "Lcom/squareup/kotlinpoet/ParameterSpec;", "codeWriter", "emitBlock", "Lkotlin/Function1;", "", "kotlinpoet"}, k = 2, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class ParameterSpecKt {
    public static /* bridge */ /* synthetic */ CodeWriter emit$default(List list, final CodeWriter codeWriter, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<ParameterSpec, Unit>() { // from class: com.squareup.kotlinpoet.ParameterSpecKt$emit$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ParameterSpec parameterSpec) {
                    invoke2(parameterSpec);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(ParameterSpec it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    ParameterSpec.emit$kotlinpoet$default(it, CodeWriter.this, false, 2, null);
                }
            };
        }
        return emit(list, codeWriter, function1);
    }

    public static final CodeWriter emit(List<ParameterSpec> receiver, CodeWriter codeWriter, Function1<? super ParameterSpec, Unit> emitBlock) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(codeWriter, "codeWriter");
        Intrinsics.checkParameterIsNotNull(emitBlock, "emitBlock");
        codeWriter.emit("(");
        int size = receiver.size();
        if (size != 0) {
            int i = 0;
            if (size == 1) {
                emitBlock.invoke(receiver.get(0));
            } else if (size == 2) {
                emitBlock.invoke(receiver.get(0));
                codeWriter.emit(", ");
                emitBlock.invoke(receiver.get(1));
            } else {
                codeWriter.emit("\n");
                codeWriter.indent(2);
                for (ParameterSpec parameterSpec : receiver) {
                    int i2 = i + 1;
                    if (i > 0) {
                        codeWriter.emit(",\n");
                    }
                    emitBlock.invoke(parameterSpec);
                    i = i2;
                }
                codeWriter.unindent(2);
                codeWriter.emit("\n");
            }
        } else {
            codeWriter.emit("");
        }
        return codeWriter.emit(")");
    }
}
