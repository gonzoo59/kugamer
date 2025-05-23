package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: JvmMemberSignature.kt */
/* loaded from: classes2.dex */
public abstract class JvmMemberSignature {
    public abstract String asString();

    public abstract String getDesc();

    public abstract String getName();

    private JvmMemberSignature() {
    }

    public /* synthetic */ JvmMemberSignature(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: JvmMemberSignature.kt */
    /* loaded from: classes2.dex */
    public static final class Method extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof Method) {
                    Method method = (Method) obj;
                    return Intrinsics.areEqual(getName(), method.getName()) && Intrinsics.areEqual(getDesc(), method.getDesc());
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            String name = getName();
            int hashCode = (name != null ? name.hashCode() : 0) * 31;
            String desc = getDesc();
            return hashCode + (desc != null ? desc.hashCode() : 0);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name, String desc) {
            super(null);
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(desc, "desc");
            this.name = name;
            this.desc = desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return getName() + getDesc();
        }
    }

    /* compiled from: JvmMemberSignature.kt */
    /* loaded from: classes2.dex */
    public static final class Field extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public final String component1() {
            return getName();
        }

        public final String component2() {
            return getDesc();
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof Field) {
                    Field field = (Field) obj;
                    return Intrinsics.areEqual(getName(), field.getName()) && Intrinsics.areEqual(getDesc(), field.getDesc());
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            String name = getName();
            int hashCode = (name != null ? name.hashCode() : 0) * 31;
            String desc = getDesc();
            return hashCode + (desc != null ? desc.hashCode() : 0);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(String name, String desc) {
            super(null);
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(desc, "desc");
            this.name = name;
            this.desc = desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return getName() + ':' + getDesc();
        }
    }

    public final String toString() {
        return asString();
    }
}
