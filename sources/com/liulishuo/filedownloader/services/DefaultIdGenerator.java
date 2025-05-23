package com.liulishuo.filedownloader.services;

import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
/* loaded from: classes2.dex */
public class DefaultIdGenerator implements FileDownloadHelper.IdGenerator {
    @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.IdGenerator
    public int transOldId(int i, String str, String str2, boolean z) {
        return generateId(str, str2, z);
    }

    @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.IdGenerator
    public int generateId(String str, String str2, boolean z) {
        if (z) {
            return FileDownloadUtils.md5(FileDownloadUtils.formatString("%sp%s@dir", str, str2)).hashCode();
        }
        return FileDownloadUtils.md5(FileDownloadUtils.formatString("%sp%s", str, str2)).hashCode();
    }
}
