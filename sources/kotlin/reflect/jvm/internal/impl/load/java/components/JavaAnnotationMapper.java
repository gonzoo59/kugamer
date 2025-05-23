package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
/* compiled from: JavaAnnotationMapper.kt */
/* loaded from: classes2.dex */
public final class JavaAnnotationMapper {
    private static final Name DEPRECATED_ANNOTATION_MESSAGE;
    public static final JavaAnnotationMapper INSTANCE = new JavaAnnotationMapper();
    private static final FqName JAVA_DEPRECATED_FQ_NAME;
    private static final FqName JAVA_DOCUMENTED_FQ_NAME;
    private static final FqName JAVA_REPEATABLE_FQ_NAME;
    private static final FqName JAVA_RETENTION_FQ_NAME;
    private static final FqName JAVA_TARGET_FQ_NAME;
    private static final Name RETENTION_ANNOTATION_VALUE;
    private static final Name TARGET_ANNOTATION_ALLOWED_TARGETS;
    private static final Map<FqName, FqName> javaToKotlinNameMap;
    private static final Map<FqName, FqName> kotlinToJavaNameMap;

    static {
        FqName fqName = new FqName(Target.class.getCanonicalName());
        JAVA_TARGET_FQ_NAME = fqName;
        FqName fqName2 = new FqName(Retention.class.getCanonicalName());
        JAVA_RETENTION_FQ_NAME = fqName2;
        FqName fqName3 = new FqName(Deprecated.class.getCanonicalName());
        JAVA_DEPRECATED_FQ_NAME = fqName3;
        FqName fqName4 = new FqName(Documented.class.getCanonicalName());
        JAVA_DOCUMENTED_FQ_NAME = fqName4;
        FqName fqName5 = new FqName("java.lang.annotation.Repeatable");
        JAVA_REPEATABLE_FQ_NAME = fqName5;
        Name identifier = Name.identifier("message");
        Intrinsics.checkExpressionValueIsNotNull(identifier, "Name.identifier(\"message\")");
        DEPRECATED_ANNOTATION_MESSAGE = identifier;
        Name identifier2 = Name.identifier("allowedTargets");
        Intrinsics.checkExpressionValueIsNotNull(identifier2, "Name.identifier(\"allowedTargets\")");
        TARGET_ANNOTATION_ALLOWED_TARGETS = identifier2;
        Name identifier3 = Name.identifier("value");
        Intrinsics.checkExpressionValueIsNotNull(identifier3, "Name.identifier(\"value\")");
        RETENTION_ANNOTATION_VALUE = identifier3;
        kotlinToJavaNameMap = MapsKt.mapOf(TuplesKt.to(KotlinBuiltIns.FQ_NAMES.target, fqName), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.retention, fqName2), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.repeatable, fqName5), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.mustBeDocumented, fqName4));
        javaToKotlinNameMap = MapsKt.mapOf(TuplesKt.to(fqName, KotlinBuiltIns.FQ_NAMES.target), TuplesKt.to(fqName2, KotlinBuiltIns.FQ_NAMES.retention), TuplesKt.to(fqName3, KotlinBuiltIns.FQ_NAMES.deprecated), TuplesKt.to(fqName5, KotlinBuiltIns.FQ_NAMES.repeatable), TuplesKt.to(fqName4, KotlinBuiltIns.FQ_NAMES.mustBeDocumented));
    }

    private JavaAnnotationMapper() {
    }

    public final Name getDEPRECATED_ANNOTATION_MESSAGE$descriptors_jvm() {
        return DEPRECATED_ANNOTATION_MESSAGE;
    }

    public final Name getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm() {
        return TARGET_ANNOTATION_ALLOWED_TARGETS;
    }

    public final Name getRETENTION_ANNOTATION_VALUE$descriptors_jvm() {
        return RETENTION_ANNOTATION_VALUE;
    }

    public final AnnotationDescriptor mapOrResolveJavaAnnotation(JavaAnnotation annotation, LazyJavaResolverContext c) {
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
        Intrinsics.checkParameterIsNotNull(c, "c");
        ClassId classId = annotation.getClassId();
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_TARGET_FQ_NAME))) {
            return new JavaTargetAnnotationDescriptor(annotation, c);
        }
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_RETENTION_FQ_NAME))) {
            return new JavaRetentionAnnotationDescriptor(annotation, c);
        }
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_REPEATABLE_FQ_NAME))) {
            FqName fqName = KotlinBuiltIns.FQ_NAMES.repeatable;
            Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.repeatable");
            return new JavaAnnotationDescriptor(c, annotation, fqName);
        } else if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_DOCUMENTED_FQ_NAME))) {
            FqName fqName2 = KotlinBuiltIns.FQ_NAMES.mustBeDocumented;
            Intrinsics.checkExpressionValueIsNotNull(fqName2, "KotlinBuiltIns.FQ_NAMES.mustBeDocumented");
            return new JavaAnnotationDescriptor(c, annotation, fqName2);
        } else if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_DEPRECATED_FQ_NAME))) {
            return null;
        } else {
            return new LazyJavaAnnotationDescriptor(c, annotation);
        }
    }

    public final AnnotationDescriptor findMappedJavaAnnotation(FqName kotlinName, JavaAnnotationOwner annotationOwner, LazyJavaResolverContext c) {
        JavaAnnotation findAnnotation;
        JavaAnnotation findAnnotation2;
        Intrinsics.checkParameterIsNotNull(kotlinName, "kotlinName");
        Intrinsics.checkParameterIsNotNull(annotationOwner, "annotationOwner");
        Intrinsics.checkParameterIsNotNull(c, "c");
        if (Intrinsics.areEqual(kotlinName, KotlinBuiltIns.FQ_NAMES.deprecated) && ((findAnnotation2 = annotationOwner.findAnnotation(JAVA_DEPRECATED_FQ_NAME)) != null || annotationOwner.isDeprecatedInJavaDoc())) {
            return new JavaDeprecatedAnnotationDescriptor(findAnnotation2, c);
        }
        FqName fqName = kotlinToJavaNameMap.get(kotlinName);
        if (fqName == null || (findAnnotation = annotationOwner.findAnnotation(fqName)) == null) {
            return null;
        }
        return INSTANCE.mapOrResolveJavaAnnotation(findAnnotation, c);
    }
}
