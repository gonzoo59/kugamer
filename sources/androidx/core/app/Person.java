package androidx.core.app;

import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import com.baidu.kwgames.Constants;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public class Person {
    private static final String ICON_KEY = "icon";
    private static final String IS_BOT_KEY = "isBot";
    private static final String IS_IMPORTANT_KEY = "isImportant";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String URI_KEY = "uri";
    private IconCompat mIcon;
    private boolean mIsBot;
    private boolean mIsImportant;
    private String mKey;
    private CharSequence mName;
    private String mUri;

    public static Person fromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(Constants.FLOAT_TAG_BALL);
        return new Builder().setName(bundle.getCharSequence("name")).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString("uri")).setKey(bundle.getString("key")).setBot(bundle.getBoolean("isBot")).setImportant(bundle.getBoolean("isImportant")).build();
    }

    private Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("name", this.mName);
        IconCompat iconCompat = this.mIcon;
        bundle.putBundle(Constants.FLOAT_TAG_BALL, iconCompat != null ? iconCompat.toBundle() : null);
        bundle.putString("uri", this.mUri);
        bundle.putString("key", this.mKey);
        bundle.putBoolean("isBot", this.mIsBot);
        bundle.putBoolean("isImportant", this.mIsImportant);
        return bundle;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    public CharSequence getName() {
        return this.mName;
    }

    public IconCompat getIcon() {
        return this.mIcon;
    }

    public String getUri() {
        return this.mUri;
    }

    public String getKey() {
        return this.mKey;
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    /* loaded from: assets/adb/sincoserver.dex */
    public static class Builder {
        private IconCompat mIcon;
        private boolean mIsBot;
        private boolean mIsImportant;
        private String mKey;
        private CharSequence mName;
        private String mUri;

        public Builder() {
        }

        private Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        public Builder setName(CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public Builder setIcon(IconCompat iconCompat) {
            this.mIcon = iconCompat;
            return this;
        }

        public Builder setUri(String str) {
            this.mUri = str;
            return this;
        }

        public Builder setKey(String str) {
            this.mKey = str;
            return this;
        }

        public Builder setBot(boolean z) {
            this.mIsBot = z;
            return this;
        }

        public Builder setImportant(boolean z) {
            this.mIsImportant = z;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
