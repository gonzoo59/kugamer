package kotlin.reflect.jvm.internal;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty0;
import kotlin.reflect.jvm.internal.KProperty0Impl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
/* compiled from: KProperty0Impl.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0010\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u001cB\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB)\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\r\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\n\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0016J\u000e\u0010\u001b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0019R(\u0010\u000f\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00028\u0000 \u0012*\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00110\u00110\u0010X\u0088\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001d"}, d2 = {"Lkotlin/reflect/jvm/internal/KProperty0Impl;", "R", "Lkotlin/reflect/KProperty0;", "Lkotlin/reflect/jvm/internal/KPropertyImpl;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "name", "", "signature", "boundReceiver", "", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "_getter", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "Lkotlin/reflect/jvm/internal/KProperty0Impl$Getter;", "kotlin.jvm.PlatformType", "delegateFieldValue", "Lkotlin/Lazy;", "getter", "getGetter", "()Lkotlin/reflect/jvm/internal/KProperty0Impl$Getter;", "get", "()Ljava/lang/Object;", "getDelegate", "invoke", "Getter", "kotlin-reflection"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public class KProperty0Impl<R> extends KPropertyImpl<R> implements KProperty0<R> {
    private final ReflectProperties.LazyVal<Getter<R>> _getter;
    private final Lazy<Object> delegateFieldValue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KProperty0Impl(KDeclarationContainerImpl container, PropertyDescriptor descriptor) {
        super(container, descriptor);
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        ReflectProperties.LazyVal<Getter<R>> lazy = ReflectProperties.lazy(new Function0<Getter<? extends R>>() { // from class: kotlin.reflect.jvm.internal.KProperty0Impl$_getter$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KProperty0Impl.Getter<R> invoke() {
                return new KProperty0Impl.Getter<>(KProperty0Impl.this);
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(lazy, "ReflectProperties.lazy { Getter(this) }");
        this._getter = lazy;
        this.delegateFieldValue = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Object>() { // from class: kotlin.reflect.jvm.internal.KProperty0Impl$delegateFieldValue$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KProperty0Impl kProperty0Impl = KProperty0Impl.this;
                return kProperty0Impl.getDelegate(kProperty0Impl.computeDelegateField(), KProperty0Impl.this.getBoundReceiver());
            }
        });
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KProperty0Impl(KDeclarationContainerImpl container, String name, String signature, Object obj) {
        super(container, name, signature, obj);
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(signature, "signature");
        ReflectProperties.LazyVal<Getter<R>> lazy = ReflectProperties.lazy(new Function0<Getter<? extends R>>() { // from class: kotlin.reflect.jvm.internal.KProperty0Impl$_getter$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KProperty0Impl.Getter<R> invoke() {
                return new KProperty0Impl.Getter<>(KProperty0Impl.this);
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(lazy, "ReflectProperties.lazy { Getter(this) }");
        this._getter = lazy;
        this.delegateFieldValue = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Object>() { // from class: kotlin.reflect.jvm.internal.KProperty0Impl$delegateFieldValue$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KProperty0Impl kProperty0Impl = KProperty0Impl.this;
                return kProperty0Impl.getDelegate(kProperty0Impl.computeDelegateField(), KProperty0Impl.this.getBoundReceiver());
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.KPropertyImpl, kotlin.reflect.KProperty
    public Getter<R> getGetter() {
        Getter<R> invoke = this._getter.invoke();
        Intrinsics.checkExpressionValueIsNotNull(invoke, "_getter()");
        return invoke;
    }

    @Override // kotlin.reflect.KProperty0
    public R get() {
        return getGetter().call(new Object[0]);
    }

    @Override // kotlin.reflect.KProperty0
    public Object getDelegate() {
        return this.delegateFieldValue.getValue();
    }

    @Override // kotlin.jvm.functions.Function0
    public R invoke() {
        return get();
    }

    /* compiled from: KProperty0Impl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\nR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lkotlin/reflect/jvm/internal/KProperty0Impl$Getter;", "R", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Getter;", "Lkotlin/reflect/KProperty0$Getter;", "property", "Lkotlin/reflect/jvm/internal/KProperty0Impl;", "(Lkotlin/reflect/jvm/internal/KProperty0Impl;)V", "getProperty", "()Lkotlin/reflect/jvm/internal/KProperty0Impl;", "invoke", "()Ljava/lang/Object;", "kotlin-reflection"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Getter<R> extends KPropertyImpl.Getter<R> implements KProperty0.Getter<R> {
        private final KProperty0Impl<R> property;

        /* JADX WARN: Multi-variable type inference failed */
        public Getter(KProperty0Impl<? extends R> property) {
            Intrinsics.checkParameterIsNotNull(property, "property");
            this.property = property;
        }

        @Override // kotlin.reflect.jvm.internal.KPropertyImpl.Accessor, kotlin.reflect.KProperty.Accessor
        public KProperty0Impl<R> getProperty() {
            return this.property;
        }

        @Override // kotlin.jvm.functions.Function0
        public R invoke() {
            return getProperty().get();
        }
    }
}
