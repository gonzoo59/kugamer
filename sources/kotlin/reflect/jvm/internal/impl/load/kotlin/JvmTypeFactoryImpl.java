package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.text.StringsKt;
/* compiled from: methodSignatureMapping.kt */
/* loaded from: classes2.dex */
final class JvmTypeFactoryImpl implements JvmTypeFactory<JvmType> {
    public static final JvmTypeFactoryImpl INSTANCE = new JvmTypeFactoryImpl();

    private JvmTypeFactoryImpl() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    public JvmType boxType(JvmType possiblyPrimitiveType) {
        Intrinsics.checkParameterIsNotNull(possiblyPrimitiveType, "possiblyPrimitiveType");
        if (possiblyPrimitiveType instanceof JvmType.Primitive) {
            JvmType.Primitive primitive = (JvmType.Primitive) possiblyPrimitiveType;
            if (primitive.getJvmPrimitiveType() != null) {
                JvmClassName byFqNameWithoutInnerClasses = JvmClassName.byFqNameWithoutInnerClasses(primitive.getJvmPrimitiveType().getWrapperFqName());
                Intrinsics.checkExpressionValueIsNotNull(byFqNameWithoutInnerClasses, "JvmClassName.byFqNameWit…mitiveType.wrapperFqName)");
                String internalName = byFqNameWithoutInnerClasses.getInternalName();
                Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byFqNameWit…apperFqName).internalName");
                return createObjectType(internalName);
            }
            return possiblyPrimitiveType;
        }
        return possiblyPrimitiveType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    public JvmType createFromString(String representation) {
        JvmPrimitiveType jvmPrimitiveType;
        Intrinsics.checkParameterIsNotNull(representation, "representation");
        String str = representation;
        str.length();
        char charAt = representation.charAt(0);
        JvmPrimitiveType[] values = JvmPrimitiveType.values();
        int length = values.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                jvmPrimitiveType = null;
                break;
            }
            jvmPrimitiveType = values[i];
            if (jvmPrimitiveType.getDesc().charAt(0) == charAt) {
                break;
            }
            i++;
        }
        if (jvmPrimitiveType != null) {
            return new JvmType.Primitive(jvmPrimitiveType);
        }
        if (charAt != 'V') {
            if (charAt == '[') {
                String substring = representation.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                return new JvmType.Array(createFromString(substring));
            }
            if (charAt == 'L') {
                StringsKt.endsWith$default((CharSequence) str, ';', false, 2, (Object) null);
            }
            String substring2 = representation.substring(1, representation.length() - 1);
            Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return new JvmType.Object(substring2);
        }
        return new JvmType.Primitive(null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    public JvmType createObjectType(String internalName) {
        Intrinsics.checkParameterIsNotNull(internalName, "internalName");
        return new JvmType.Object(internalName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    public String toString(JvmType type) {
        String desc;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (type instanceof JvmType.Array) {
            return "[" + toString(((JvmType.Array) type).getElementType());
        } else if (type instanceof JvmType.Primitive) {
            JvmPrimitiveType jvmPrimitiveType = ((JvmType.Primitive) type).getJvmPrimitiveType();
            return (jvmPrimitiveType == null || (desc = jvmPrimitiveType.getDesc()) == null) ? "V" : desc;
        } else if (type instanceof JvmType.Object) {
            return "L" + ((JvmType.Object) type).getInternalName() + ";";
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory
    public JvmType getJavaLangClassType() {
        return createObjectType("java/lang/Class");
    }
}
