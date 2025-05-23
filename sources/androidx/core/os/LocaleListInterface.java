package androidx.core.os;

import java.util.Locale;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
interface LocaleListInterface {
    boolean equals(Object obj);

    Locale get(int i);

    Locale getFirstMatch(String[] strArr);

    Object getLocaleList();

    int hashCode();

    int indexOf(Locale locale);

    boolean isEmpty();

    void setLocaleList(Locale... localeArr);

    int size();

    String toLanguageTags();

    String toString();
}
