package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: BuiltInsResourceLoader.kt */
/* loaded from: classes2.dex */
public final class BuiltInsResourceLoader {
    public final InputStream loadResource(String path) {
        InputStream resourceAsStream;
        Intrinsics.checkParameterIsNotNull(path, "path");
        ClassLoader classLoader = getClass().getClassLoader();
        return (classLoader == null || (resourceAsStream = classLoader.getResourceAsStream(path)) == null) ? ClassLoader.getSystemResourceAsStream(path) : resourceAsStream;
    }
}
