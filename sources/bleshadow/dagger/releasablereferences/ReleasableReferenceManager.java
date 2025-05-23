package bleshadow.dagger.releasablereferences;

import java.lang.annotation.Annotation;
/* loaded from: classes.dex */
public interface ReleasableReferenceManager {
    void releaseStrongReferences();

    void restoreStrongReferences();

    Class<? extends Annotation> scope();
}
