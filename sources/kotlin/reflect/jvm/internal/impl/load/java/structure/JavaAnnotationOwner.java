package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.FqName;
/* compiled from: javaElements.kt */
/* loaded from: classes2.dex */
public interface JavaAnnotationOwner extends JavaElement {
    JavaAnnotation findAnnotation(FqName fqName);

    Collection<JavaAnnotation> getAnnotations();

    boolean isDeprecatedInJavaDoc();
}
