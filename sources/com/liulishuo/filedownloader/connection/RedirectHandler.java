package com.liulishuo.filedownloader.connection;

import com.google.common.net.HttpHeaders;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class RedirectHandler {
    private static final int HTTP_PERMANENT_REDIRECT = 308;
    private static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final int MAX_REDIRECT_TIMES = 10;

    private static boolean isRedirect(int i) {
        return i == 301 || i == 302 || i == 303 || i == 300 || i == 307 || i == 308;
    }

    public static FileDownloadConnection process(Map<String, List<String>> map, FileDownloadConnection fileDownloadConnection, List<String> list) throws IOException, IllegalAccessException {
        int responseCode = fileDownloadConnection.getResponseCode();
        String responseHeaderField = fileDownloadConnection.getResponseHeaderField(HttpHeaders.LOCATION);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (isRedirect(responseCode)) {
            if (responseHeaderField == null) {
                throw new IllegalAccessException(FileDownloadUtils.formatString("receive %d (redirect) but the location is null with response [%s]", Integer.valueOf(responseCode), fileDownloadConnection.getResponseHeaderFields()));
            }
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(RedirectHandler.class, "redirect to %s with %d, %s", responseHeaderField, Integer.valueOf(responseCode), arrayList);
            }
            fileDownloadConnection.ending();
            fileDownloadConnection = buildRedirectConnection(map, responseHeaderField);
            arrayList.add(responseHeaderField);
            fileDownloadConnection.execute();
            responseCode = fileDownloadConnection.getResponseCode();
            responseHeaderField = fileDownloadConnection.getResponseHeaderField(HttpHeaders.LOCATION);
            i++;
            if (i >= 10) {
                throw new IllegalAccessException(FileDownloadUtils.formatString("redirect too many times! %s", arrayList));
            }
        }
        if (list != null) {
            list.addAll(arrayList);
        }
        return fileDownloadConnection;
    }

    private static FileDownloadConnection buildRedirectConnection(Map<String, List<String>> map, String str) throws IOException {
        FileDownloadConnection createConnection = CustomComponentHolder.getImpl().createConnection(str);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (value != null) {
                for (String str2 : value) {
                    createConnection.addHeader(key, str2);
                }
            }
        }
        return createConnection;
    }
}
