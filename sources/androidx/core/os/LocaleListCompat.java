package androidx.core.os;

import android.os.Build;
import android.os.LocaleList;
import java.util.Locale;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class LocaleListCompat {
    static final LocaleListInterface IMPL;
    private static final LocaleListCompat sEmptyLocaleList = new LocaleListCompat();

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new LocaleListCompatApi24Impl();
        } else {
            IMPL = new LocaleListCompatBaseImpl();
        }
    }

    /* loaded from: assets/adb/sincoserver.dex */
    static class LocaleListCompatBaseImpl implements LocaleListInterface {
        private LocaleListHelper mLocaleList = new LocaleListHelper(new Locale[0]);

        LocaleListCompatBaseImpl() {
        }

        @Override // androidx.core.os.LocaleListInterface
        public void setLocaleList(Locale... localeArr) {
            this.mLocaleList = new LocaleListHelper(localeArr);
        }

        @Override // androidx.core.os.LocaleListInterface
        public Object getLocaleList() {
            return this.mLocaleList;
        }

        @Override // androidx.core.os.LocaleListInterface
        public Locale get(int i) {
            return this.mLocaleList.get(i);
        }

        @Override // androidx.core.os.LocaleListInterface
        public boolean isEmpty() {
            return this.mLocaleList.isEmpty();
        }

        @Override // androidx.core.os.LocaleListInterface
        public int size() {
            return this.mLocaleList.size();
        }

        @Override // androidx.core.os.LocaleListInterface
        public int indexOf(Locale locale) {
            return this.mLocaleList.indexOf(locale);
        }

        @Override // androidx.core.os.LocaleListInterface
        public boolean equals(Object obj) {
            return this.mLocaleList.equals(((LocaleListCompat) obj).unwrap());
        }

        @Override // androidx.core.os.LocaleListInterface
        public int hashCode() {
            return this.mLocaleList.hashCode();
        }

        @Override // androidx.core.os.LocaleListInterface
        public String toString() {
            return this.mLocaleList.toString();
        }

        @Override // androidx.core.os.LocaleListInterface
        public String toLanguageTags() {
            return this.mLocaleList.toLanguageTags();
        }

        @Override // androidx.core.os.LocaleListInterface
        public Locale getFirstMatch(String[] strArr) {
            LocaleListHelper localeListHelper = this.mLocaleList;
            if (localeListHelper != null) {
                return localeListHelper.getFirstMatch(strArr);
            }
            return null;
        }
    }

    /* loaded from: assets/adb/sincoserver.dex */
    static class LocaleListCompatApi24Impl implements LocaleListInterface {
        private LocaleList mLocaleList = new LocaleList(new Locale[0]);

        LocaleListCompatApi24Impl() {
        }

        @Override // androidx.core.os.LocaleListInterface
        public void setLocaleList(Locale... localeArr) {
            this.mLocaleList = new LocaleList(localeArr);
        }

        @Override // androidx.core.os.LocaleListInterface
        public Object getLocaleList() {
            return this.mLocaleList;
        }

        @Override // androidx.core.os.LocaleListInterface
        public Locale get(int i) {
            return this.mLocaleList.get(i);
        }

        @Override // androidx.core.os.LocaleListInterface
        public boolean isEmpty() {
            return this.mLocaleList.isEmpty();
        }

        @Override // androidx.core.os.LocaleListInterface
        public int size() {
            return this.mLocaleList.size();
        }

        @Override // androidx.core.os.LocaleListInterface
        public int indexOf(Locale locale) {
            return this.mLocaleList.indexOf(locale);
        }

        @Override // androidx.core.os.LocaleListInterface
        public boolean equals(Object obj) {
            return this.mLocaleList.equals(((LocaleListCompat) obj).unwrap());
        }

        @Override // androidx.core.os.LocaleListInterface
        public int hashCode() {
            return this.mLocaleList.hashCode();
        }

        @Override // androidx.core.os.LocaleListInterface
        public String toString() {
            return this.mLocaleList.toString();
        }

        @Override // androidx.core.os.LocaleListInterface
        public String toLanguageTags() {
            return this.mLocaleList.toLanguageTags();
        }

        @Override // androidx.core.os.LocaleListInterface
        public Locale getFirstMatch(String[] strArr) {
            LocaleList localeList = this.mLocaleList;
            if (localeList != null) {
                return localeList.getFirstMatch(strArr);
            }
            return null;
        }
    }

    private LocaleListCompat() {
    }

    public static LocaleListCompat wrap(Object obj) {
        LocaleListCompat localeListCompat = new LocaleListCompat();
        if (obj instanceof LocaleList) {
            localeListCompat.setLocaleList((LocaleList) obj);
        }
        return localeListCompat;
    }

    public Object unwrap() {
        return IMPL.getLocaleList();
    }

    public static LocaleListCompat create(Locale... localeArr) {
        LocaleListCompat localeListCompat = new LocaleListCompat();
        localeListCompat.setLocaleListArray(localeArr);
        return localeListCompat;
    }

    public Locale get(int i) {
        return IMPL.get(i);
    }

    public boolean isEmpty() {
        return IMPL.isEmpty();
    }

    public int size() {
        return IMPL.size();
    }

    public int indexOf(Locale locale) {
        return IMPL.indexOf(locale);
    }

    public String toLanguageTags() {
        return IMPL.toLanguageTags();
    }

    public Locale getFirstMatch(String[] strArr) {
        return IMPL.getFirstMatch(strArr);
    }

    public static LocaleListCompat getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static LocaleListCompat forLanguageTags(String str) {
        Locale forLanguageTag;
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] split = str.split(",", -1);
        Locale[] localeArr = new Locale[split.length];
        for (int i = 0; i < localeArr.length; i++) {
            if (Build.VERSION.SDK_INT >= 21) {
                forLanguageTag = Locale.forLanguageTag(split[i]);
            } else {
                forLanguageTag = LocaleHelper.forLanguageTag(split[i]);
            }
            localeArr[i] = forLanguageTag;
        }
        LocaleListCompat localeListCompat = new LocaleListCompat();
        localeListCompat.setLocaleListArray(localeArr);
        return localeListCompat;
    }

    public static LocaleListCompat getAdjustedDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getAdjustedDefault()) : create(Locale.getDefault());
    }

    public static LocaleListCompat getDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getDefault()) : create(Locale.getDefault());
    }

    public boolean equals(Object obj) {
        return IMPL.equals(obj);
    }

    public int hashCode() {
        return IMPL.hashCode();
    }

    public String toString() {
        return IMPL.toString();
    }

    private void setLocaleList(LocaleList localeList) {
        int size = localeList.size();
        if (size > 0) {
            Locale[] localeArr = new Locale[size];
            for (int i = 0; i < size; i++) {
                localeArr[i] = localeList.get(i);
            }
            IMPL.setLocaleList(localeArr);
        }
    }

    private void setLocaleListArray(Locale... localeArr) {
        IMPL.setLocaleList(localeArr);
    }
}
