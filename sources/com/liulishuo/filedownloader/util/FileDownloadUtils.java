package com.liulishuo.filedownloader.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import com.bumptech.glide.load.Key;
import com.google.common.net.HttpHeaders;
import com.liulishuo.filedownloader.BuildConfig;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.exception.FileDownloadSecurityException;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes2.dex */
public class FileDownloadUtils {
    private static final String FILEDOWNLOADER_PREFIX = "FileDownloader";
    private static final String INTERNAL_DOCUMENT_NAME = "filedownloader";
    private static final String OLD_FILE_CONVERTED_FILE_NAME = ".old_file_converted";
    private static String defaultSaveRootPath = null;
    private static Boolean filenameConverted = null;
    private static Boolean isDownloaderProcess = null;
    private static int minProgressStep = 65536;
    private static long minProgressTime = 2000;
    private static final Pattern CONTENT_DISPOSITION_WITH_ASTERISK_PATTERN = Pattern.compile("attachment;\\s*filename\\*\\s*=\\s*\"*([^\"]*)'\\S*'([^\"]*)\"*");
    private static final Pattern CONTENT_DISPOSITION_WITHOUT_ASTERISK_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*\"*([^\"\\n]*)\"*");

    public static boolean isFilenameValid(String str) {
        return true;
    }

    public static void setMinProgressStep(int i) throws IllegalAccessException {
        if (isDownloaderProcess(FileDownloadHelper.getAppContext())) {
            minProgressStep = i;
            return;
        }
        throw new IllegalAccessException("This value is used in the :filedownloader process, so set this value in your process is without effect. You can add 'process.non-separate=true' in 'filedownloader.properties' to share the main process to FileDownloadService. Or you can configure this value in 'filedownloader.properties' by 'download.min-progress-step'.");
    }

    public static void setMinProgressTime(long j) throws IllegalAccessException {
        if (isDownloaderProcess(FileDownloadHelper.getAppContext())) {
            minProgressTime = j;
            return;
        }
        throw new IllegalAccessException("This value is used in the :filedownloader process, so set this value in your process is without effect. You can add 'process.non-separate=true' in 'filedownloader.properties' to share the main process to FileDownloadService. Or you can configure this value in 'filedownloader.properties' by 'download.min-progress-time'.");
    }

    public static int getMinProgressStep() {
        return minProgressStep;
    }

    public static long getMinProgressTime() {
        return minProgressTime;
    }

    public static String getDefaultSaveRootPath() {
        if (!TextUtils.isEmpty(defaultSaveRootPath)) {
            return defaultSaveRootPath;
        }
        boolean z = false;
        if (FileDownloadHelper.getAppContext().getExternalCacheDir() != null && Environment.getExternalStorageState().equals("mounted") && Environment.getExternalStorageDirectory().getFreeSpace() > 0) {
            z = true;
        }
        if (z) {
            return FileDownloadHelper.getAppContext().getExternalCacheDir().getAbsolutePath();
        }
        return FileDownloadHelper.getAppContext().getCacheDir().getAbsolutePath();
    }

    public static String getDefaultSaveFilePath(String str) {
        return generateFilePath(getDefaultSaveRootPath(), generateFileName(str));
    }

    public static String generateFileName(String str) {
        return md5(str);
    }

    public static String generateFilePath(String str, String str2) {
        if (str2 != null) {
            if (str != null) {
                return formatString("%s%s%s", str, File.separator, str2);
            }
            throw new IllegalStateException("can't generate real path, the directory is null");
        }
        throw new IllegalStateException("can't generate real path, the file name is null");
    }

    public static void setDefaultSaveRootPath(String str) {
        defaultSaveRootPath = str;
    }

    public static String getTempPath(String str) {
        return formatString("%s.temp", str);
    }

    public static int generateId(String str, String str2) {
        return CustomComponentHolder.getImpl().getIdGeneratorInstance().generateId(str, str2, false);
    }

    public static int generateId(String str, String str2, boolean z) {
        return CustomComponentHolder.getImpl().getIdGeneratorInstance().generateId(str, str2, z);
    }

    public static String md5(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes(Key.STRING_CHARSET_NAME));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Huh, MD5 should be supported?", e2);
        }
    }

    public static String getStack() {
        return getStack(true);
    }

    public static String getStack(boolean z) {
        return getStack(new Throwable().getStackTrace(), z);
    }

    public static String getStack(StackTraceElement[] stackTraceElementArr, boolean z) {
        if (stackTraceElementArr == null || stackTraceElementArr.length < 4) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < stackTraceElementArr.length; i++) {
            if (stackTraceElementArr[i].getClassName().contains(BuildConfig.APPLICATION_ID)) {
                sb.append("[");
                sb.append(stackTraceElementArr[i].getClassName().substring(28));
                sb.append(":");
                sb.append(stackTraceElementArr[i].getMethodName());
                if (z) {
                    sb.append("(");
                    sb.append(stackTraceElementArr[i].getLineNumber());
                    sb.append(")]");
                } else {
                    sb.append("]");
                }
            }
        }
        return sb.toString();
    }

    public static boolean isDownloaderProcess(Context context) {
        Boolean bool = isDownloaderProcess;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean z = false;
        if (!FileDownloadProperties.getImpl().processNonSeparate) {
            int myPid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                FileDownloadLog.w(FileDownloadUtils.class, "fail to get the activity manager!", new Object[0]);
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
                FileDownloadLog.w(FileDownloadUtils.class, "The running app process info list from ActivityManager is null or empty, maybe current App is not running.", new Object[0]);
                return false;
            }
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (next.pid == myPid) {
                    z = next.processName.endsWith(":filedownloader");
                    break;
                }
            }
        } else {
            z = true;
        }
        Boolean valueOf = Boolean.valueOf(z);
        isDownloaderProcess = valueOf;
        return valueOf.booleanValue();
    }

    public static String[] convertHeaderString(String str) {
        String[] split = str.split("\n");
        String[] strArr = new String[split.length * 2];
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split(": ");
            int i2 = i * 2;
            strArr[i2] = split2[0];
            strArr[i2 + 1] = split2[1];
        }
        return strArr;
    }

    public static long getFreeSpaceBytes(String str) {
        StatFs statFs = new StatFs(str);
        if (Build.VERSION.SDK_INT >= 18) {
            return statFs.getAvailableBytes();
        }
        return statFs.getAvailableBlocks() * statFs.getBlockSize();
    }

    public static String formatString(String str, Object... objArr) {
        return String.format(Locale.ENGLISH, str, objArr);
    }

    public static void markConverted(Context context) {
        File convertedMarkedFile = getConvertedMarkedFile(context);
        try {
            convertedMarkedFile.getParentFile().mkdirs();
            convertedMarkedFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFilenameConverted(Context context) {
        if (filenameConverted == null) {
            filenameConverted = Boolean.valueOf(getConvertedMarkedFile(context).exists());
        }
        return filenameConverted.booleanValue();
    }

    public static File getConvertedMarkedFile(Context context) {
        return new File(context.getFilesDir().getAbsolutePath() + File.separator + "filedownloader", OLD_FILE_CONVERTED_FILE_NAME);
    }

    public static long parseContentRangeFoInstanceLength(String str) {
        if (str == null) {
            return -1L;
        }
        String[] split = str.split("/");
        if (split.length >= 2) {
            try {
                return Long.parseLong(split[1]);
            } catch (NumberFormatException unused) {
                FileDownloadLog.w(FileDownloadUtils.class, "parse instance length failed with %s", str);
            }
        }
        return -1L;
    }

    public static String parseContentDisposition(String str) {
        Matcher matcher;
        if (str == null) {
            return null;
        }
        try {
            matcher = CONTENT_DISPOSITION_WITH_ASTERISK_PATTERN.matcher(str);
        } catch (UnsupportedEncodingException | IllegalStateException unused) {
        }
        if (matcher.find()) {
            return URLDecoder.decode(matcher.group(2), matcher.group(1));
        }
        Matcher matcher2 = CONTENT_DISPOSITION_WITHOUT_ASTERISK_PATTERN.matcher(str);
        if (matcher2.find()) {
            return matcher2.group(1);
        }
        return null;
    }

    public static String getTargetFilePath(String str, boolean z, String str2) {
        if (str == null) {
            return null;
        }
        if (z) {
            if (str2 == null) {
                return null;
            }
            return generateFilePath(str, str2);
        }
        return str;
    }

    public static String getParent(String str) {
        int length = str.length();
        int i = 2;
        int i2 = (File.separatorChar == '\\' && length > 2 && str.charAt(1) == ':') ? 2 : 0;
        int lastIndexOf = str.lastIndexOf(File.separatorChar);
        if (lastIndexOf != -1 || i2 <= 0) {
            i = lastIndexOf;
        }
        if (i == -1 || str.charAt(length - 1) == File.separatorChar) {
            return null;
        }
        if (str.indexOf(File.separatorChar) == i && str.charAt(i2) == File.separatorChar) {
            return str.substring(0, i + 1);
        }
        return str.substring(0, i);
    }

    public static String getThreadPoolName(String str) {
        return "FileDownloader-" + str;
    }

    public static boolean isNetworkNotOnWifiType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) FileDownloadHelper.getAppContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            FileDownloadLog.w(FileDownloadUtils.class, "failed to get connectivity manager!", new Object[0]);
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo == null || activeNetworkInfo.getType() != 1;
    }

    public static boolean checkPermission(String str) {
        return FileDownloadHelper.getAppContext().checkCallingOrSelfPermission(str) == 0;
    }

    public static long convertContentLengthString(String str) {
        if (str == null) {
            return -1L;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    public static String findEtag(int i, FileDownloadConnection fileDownloadConnection) {
        if (fileDownloadConnection == null) {
            throw new RuntimeException("connection is null when findEtag");
        }
        String responseHeaderField = fileDownloadConnection.getResponseHeaderField("Etag");
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(FileDownloadUtils.class, "etag find %s for task(%d)", responseHeaderField, Integer.valueOf(i));
        }
        return responseHeaderField;
    }

    public static boolean isAcceptRange(int i, FileDownloadConnection fileDownloadConnection) {
        if (i == 206 || i == 1) {
            return true;
        }
        return "bytes".equals(fileDownloadConnection.getResponseHeaderField(HttpHeaders.ACCEPT_RANGES));
    }

    public static long findInstanceLengthForTrial(FileDownloadConnection fileDownloadConnection) {
        long findInstanceLengthFromContentRange = findInstanceLengthFromContentRange(fileDownloadConnection);
        if (findInstanceLengthFromContentRange < 0) {
            FileDownloadLog.w(FileDownloadUtils.class, "don't get instance length fromContent-Range header", new Object[0]);
            findInstanceLengthFromContentRange = -1;
        }
        if (findInstanceLengthFromContentRange == 0 && FileDownloadProperties.getImpl().trialConnectionHeadMethod) {
            return -1L;
        }
        return findInstanceLengthFromContentRange;
    }

    public static long findInstanceLengthFromContentRange(FileDownloadConnection fileDownloadConnection) {
        return parseContentRangeFoInstanceLength(getContentRangeHeader(fileDownloadConnection));
    }

    private static String getContentRangeHeader(FileDownloadConnection fileDownloadConnection) {
        return fileDownloadConnection.getResponseHeaderField(HttpHeaders.CONTENT_RANGE);
    }

    public static long findContentLength(int i, FileDownloadConnection fileDownloadConnection) {
        long convertContentLengthString = convertContentLengthString(fileDownloadConnection.getResponseHeaderField(HttpHeaders.CONTENT_LENGTH));
        String responseHeaderField = fileDownloadConnection.getResponseHeaderField(HttpHeaders.TRANSFER_ENCODING);
        if (convertContentLengthString < 0) {
            if (!(responseHeaderField != null && responseHeaderField.equals("chunked"))) {
                if (FileDownloadProperties.getImpl().httpLenient) {
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadLog.d(FileDownloadUtils.class, "%d response header is not legal but HTTP lenient is true, so handle as the case of transfer encoding chunk", Integer.valueOf(i));
                    }
                } else {
                    throw new FileDownloadGiveUpRetryException("can't know the size of the download file, and its Transfer-Encoding is not Chunked either.\nyou can ignore such exception by add http.lenient=true to the filedownloader.properties");
                }
            }
            return -1L;
        }
        return convertContentLengthString;
    }

    public static long findContentLengthFromContentRange(FileDownloadConnection fileDownloadConnection) {
        long parseContentLengthFromContentRange = parseContentLengthFromContentRange(getContentRangeHeader(fileDownloadConnection));
        if (parseContentLengthFromContentRange < 0) {
            return -1L;
        }
        return parseContentLengthFromContentRange;
    }

    public static long parseContentLengthFromContentRange(String str) {
        if (str != null && str.length() != 0) {
            try {
                Matcher matcher = Pattern.compile("bytes (\\d+)-(\\d+)/\\d+").matcher(str);
                if (matcher.find()) {
                    return (Long.parseLong(matcher.group(2)) - Long.parseLong(matcher.group(1))) + 1;
                }
            } catch (Exception e) {
                FileDownloadLog.e(FileDownloadUtils.class, e, "parse content length from content range error", new Object[0]);
            }
        }
        return -1L;
    }

    public static String findFilename(FileDownloadConnection fileDownloadConnection, String str) throws FileDownloadSecurityException {
        String parseContentDisposition = parseContentDisposition(fileDownloadConnection.getResponseHeaderField(HttpHeaders.CONTENT_DISPOSITION));
        if (TextUtils.isEmpty(parseContentDisposition)) {
            parseContentDisposition = findFileNameFromUrl(str);
        }
        if (TextUtils.isEmpty(parseContentDisposition)) {
            return generateFileName(str);
        }
        if (parseContentDisposition.contains("../")) {
            throw new FileDownloadSecurityException(formatString("The filename [%s] from the response is not allowable, because it contains '../', which can raise the directory traversal vulnerability", parseContentDisposition));
        }
        return parseContentDisposition;
    }

    public static FileDownloadOutputStream createOutputStream(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            throw new RuntimeException("found invalid internal destination path, empty");
        }
        if (!isFilenameValid(str)) {
            throw new RuntimeException(formatString("found invalid internal destination filename %s", str));
        }
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            throw new RuntimeException(formatString("found invalid internal destination path[%s], & path is directory[%B]", str, Boolean.valueOf(file.isDirectory())));
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException(formatString("create new file error  %s", file.getAbsolutePath()));
        }
        return CustomComponentHolder.getImpl().createOutputStream(file);
    }

    public static boolean isBreakpointAvailable(int i, FileDownloadModel fileDownloadModel) {
        return isBreakpointAvailable(i, fileDownloadModel, null);
    }

    public static boolean isBreakpointAvailable(int i, FileDownloadModel fileDownloadModel, Boolean bool) {
        if (fileDownloadModel == null) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d model == null", Integer.valueOf(i));
            }
            return false;
        } else if (fileDownloadModel.getTempFilePath() == null) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d temp path == null", Integer.valueOf(i));
            }
            return false;
        } else {
            return isBreakpointAvailable(i, fileDownloadModel, fileDownloadModel.getTempFilePath(), bool);
        }
    }

    public static boolean isBreakpointAvailable(int i, FileDownloadModel fileDownloadModel, String str, Boolean bool) {
        if (str == null) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d path = null", Integer.valueOf(i));
                return false;
            }
            return false;
        }
        File file = new File(str);
        boolean exists = file.exists();
        boolean isDirectory = file.isDirectory();
        if (!exists || isDirectory) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d file not suit, exists[%B], directory[%B]", Integer.valueOf(i), Boolean.valueOf(exists), Boolean.valueOf(isDirectory));
                return false;
            }
            return false;
        }
        long length = file.length();
        long soFar = fileDownloadModel.getSoFar();
        if (fileDownloadModel.getConnectionCount() <= 1 && soFar == 0) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d the downloaded-record is zero.", Integer.valueOf(i));
                return false;
            }
            return false;
        }
        long total = fileDownloadModel.getTotal();
        if (length < soFar || (total != -1 && (length > total || soFar >= total))) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d dirty data fileLength[%d] sofar[%d] total[%d]", Integer.valueOf(i), Long.valueOf(length), Long.valueOf(soFar), Long.valueOf(total));
                return false;
            }
            return false;
        } else if (bool == null || bool.booleanValue() || total != length) {
            return true;
        } else {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d, because of the output stream doesn't support seek, but the task has already pre-allocated, so we only can download it from the very beginning.", Integer.valueOf(i));
                return false;
            }
            return false;
        }
    }

    public static void deleteTaskFiles(String str, String str2) {
        deleteTempFile(str2);
        deleteTargetFile(str);
    }

    public static void deleteTempFile(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void deleteTargetFile(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static boolean isNeedSync(long j, long j2) {
        return j > ((long) getMinProgressStep()) && j2 > getMinProgressTime();
    }

    public static String defaultUserAgent() {
        return formatString("FileDownloader/%s", BuildConfig.VERSION_NAME);
    }

    private static boolean isAppOnForeground(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        PowerManager powerManager;
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService("activity");
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null || (powerManager = (PowerManager) context.getSystemService("power")) == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT > 19) {
            if (!powerManager.isInteractive()) {
                return false;
            }
        } else if (!powerManager.isScreenOn()) {
            return false;
        }
        String packageName = context.getApplicationContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(packageName) && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static boolean needMakeServiceForeground(Context context) {
        return Build.VERSION.SDK_INT >= 26 && !isAppOnForeground(context);
    }

    static String findFileNameFromUrl(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                String path = new URL(str).getPath();
                String substring = path.substring(path.lastIndexOf(47) + 1);
                if (substring.isEmpty()) {
                    return null;
                }
                return substring;
            } catch (MalformedURLException unused) {
            }
        }
        return null;
    }
}
