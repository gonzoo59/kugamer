package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    /* loaded from: assets/adb/sincoserver.dex */
    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    static boolean handlePerformPrivateCommand(String str, Bundle bundle, OnCommitContentListener onCommitContentListener) {
        ResultReceiver resultReceiver;
        if (!TextUtils.equals("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", str) || bundle == null) {
            return false;
        }
        try {
            resultReceiver = (ResultReceiver) bundle.getParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER");
            try {
                boolean onCommitContent = onCommitContentListener.onCommitContent(new InputContentInfoCompat((Uri) bundle.getParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI"), (ClipDescription) bundle.getParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION"), (Uri) bundle.getParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI")), bundle.getInt("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS"), (Bundle) bundle.getParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS"));
                if (resultReceiver != null) {
                    resultReceiver.send(onCommitContent ? 1 : 0, null);
                }
                return onCommitContent;
            } catch (Throwable th) {
                th = th;
                if (resultReceiver != null) {
                    resultReceiver.send(0, null);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            resultReceiver = null;
        }
    }

    public static boolean commitContent(InputConnection inputConnection, EditorInfo editorInfo, InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
        boolean z;
        ClipDescription description = inputContentInfoCompat.getDescription();
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        int length = contentMimeTypes.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else if (description.hasMimeType(contentMimeTypes[i2])) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            if (Build.VERSION.SDK_INT >= 25) {
                return inputConnection.commitContent((InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI", inputContentInfoCompat.getContentUri());
            bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", inputContentInfoCompat.getDescription());
            bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", inputContentInfoCompat.getLinkUri());
            bundle2.putInt("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", i);
            bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", bundle);
            return inputConnection.performPrivateCommand("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", bundle2);
        }
        return false;
    }

    public static InputConnection createWrapper(InputConnection inputConnection, EditorInfo editorInfo, final OnCommitContentListener onCommitContentListener) {
        if (inputConnection != null) {
            if (editorInfo != null) {
                if (onCommitContentListener == null) {
                    throw new IllegalArgumentException("onCommitContentListener must be non-null");
                }
                if (Build.VERSION.SDK_INT >= 25) {
                    return new InputConnectionWrapper(inputConnection, false) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.1
                        @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                        public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                            if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                                return true;
                            }
                            return super.commitContent(inputContentInfo, i, bundle);
                        }
                    };
                }
                return EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0 ? inputConnection : new InputConnectionWrapper(inputConnection, false) { // from class: androidx.core.view.inputmethod.InputConnectionCompat.2
                    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
                    public boolean performPrivateCommand(String str, Bundle bundle) {
                        if (InputConnectionCompat.handlePerformPrivateCommand(str, bundle, onCommitContentListener)) {
                            return true;
                        }
                        return super.performPrivateCommand(str, bundle);
                    }
                };
            }
            throw new IllegalArgumentException("editorInfo must be non-null");
        }
        throw new IllegalArgumentException("inputConnection must be non-null");
    }
}
