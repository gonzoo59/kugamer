package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
/* compiled from: constantValues.kt */
/* loaded from: classes2.dex */
public abstract class ErrorValue extends ConstantValue<Unit> {
    public static final Companion Companion = new Companion(null);

    public ErrorValue() {
        super(Unit.INSTANCE);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    public Unit getValue() {
        throw new UnsupportedOperationException();
    }

    /* compiled from: constantValues.kt */
    /* loaded from: classes2.dex */
    public static final class ErrorValueWithMessage extends ErrorValue {
        private final String message;

        public ErrorValueWithMessage(String message) {
            Intrinsics.checkParameterIsNotNull(message, "message");
            this.message = message;
        }

        @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
        public SimpleType getType(ModuleDescriptor module) {
            Intrinsics.checkParameterIsNotNull(module, "module");
            SimpleType createErrorType = ErrorUtils.createErrorType(this.message);
            Intrinsics.checkExpressionValueIsNotNull(createErrorType, "ErrorUtils.createErrorType(message)");
            return createErrorType;
        }

        @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
        public String toString() {
            return this.message;
        }
    }

    /* compiled from: constantValues.kt */
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ErrorValue create(String message) {
            Intrinsics.checkParameterIsNotNull(message, "message");
            return new ErrorValueWithMessage(message);
        }
    }
}
