package bleshadow.dagger.releasablereferences;

import java.lang.annotation.Annotation;
/* loaded from: classes.dex */
public interface TypedReleasableReferenceManager<M extends Annotation> extends ReleasableReferenceManager {
    M metadata();
}
