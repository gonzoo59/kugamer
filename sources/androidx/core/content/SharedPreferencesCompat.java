package androidx.core.content;

import android.content.SharedPreferences;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
@Deprecated
/* loaded from: assets/adb/sincoserver.dex */
public final class SharedPreferencesCompat {

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    @Deprecated
    /* loaded from: assets/adb/sincoserver.dex */
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* JADX WARN: Classes with same name are omitted:
          classes.dex
         */
        /* loaded from: assets/adb/sincoserver.dex */
        private static class Helper {
            Helper() {
            }

            public void apply(SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError unused) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        @Deprecated
        public void apply(SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
