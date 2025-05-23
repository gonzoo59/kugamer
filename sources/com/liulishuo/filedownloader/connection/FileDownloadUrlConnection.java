package com.liulishuo.filedownloader.connection;

import com.liulishuo.filedownloader.util.FileDownloadHelper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class FileDownloadUrlConnection implements FileDownloadConnection {
    protected URLConnection mConnection;

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public boolean dispatchAddResumeOffset(String str, long j) {
        return false;
    }

    public FileDownloadUrlConnection(String str, Configuration configuration) throws IOException {
        this(new URL(str), configuration);
    }

    public FileDownloadUrlConnection(URL url, Configuration configuration) throws IOException {
        if (configuration == null || configuration.proxy == null) {
            this.mConnection = url.openConnection();
        } else {
            this.mConnection = url.openConnection(configuration.proxy);
        }
        if (configuration != null) {
            if (configuration.readTimeout != null) {
                this.mConnection.setReadTimeout(configuration.readTimeout.intValue());
            }
            if (configuration.connectTimeout != null) {
                this.mConnection.setConnectTimeout(configuration.connectTimeout.intValue());
            }
        }
    }

    public FileDownloadUrlConnection(String str) throws IOException {
        this(str, (Configuration) null);
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public void addHeader(String str, String str2) {
        this.mConnection.addRequestProperty(str, str2);
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public InputStream getInputStream() throws IOException {
        return this.mConnection.getInputStream();
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public Map<String, List<String>> getRequestHeaderFields() {
        return this.mConnection.getRequestProperties();
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public Map<String, List<String>> getResponseHeaderFields() {
        return this.mConnection.getHeaderFields();
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public String getResponseHeaderField(String str) {
        return this.mConnection.getHeaderField(str);
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public boolean setRequestMethod(String str) throws ProtocolException {
        URLConnection uRLConnection = this.mConnection;
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).setRequestMethod(str);
            return true;
        }
        return false;
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public void execute() throws IOException {
        this.mConnection.connect();
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public int getResponseCode() throws IOException {
        URLConnection uRLConnection = this.mConnection;
        if (uRLConnection instanceof HttpURLConnection) {
            return ((HttpURLConnection) uRLConnection).getResponseCode();
        }
        return 0;
    }

    @Override // com.liulishuo.filedownloader.connection.FileDownloadConnection
    public void ending() {
        try {
            this.mConnection.getInputStream().close();
        } catch (IOException unused) {
        }
    }

    /* loaded from: classes2.dex */
    public static class Creator implements FileDownloadHelper.ConnectionCreator {
        private final Configuration mConfiguration;

        public Creator() {
            this(null);
        }

        public Creator(Configuration configuration) {
            this.mConfiguration = configuration;
        }

        FileDownloadConnection create(URL url) throws IOException {
            return new FileDownloadUrlConnection(url, this.mConfiguration);
        }

        @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCreator
        public FileDownloadConnection create(String str) throws IOException {
            return new FileDownloadUrlConnection(str, this.mConfiguration);
        }
    }

    /* loaded from: classes2.dex */
    public static class Configuration {
        private Integer connectTimeout;
        private Proxy proxy;
        private Integer readTimeout;

        public Configuration proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Configuration readTimeout(int i) {
            this.readTimeout = Integer.valueOf(i);
            return this;
        }

        public Configuration connectTimeout(int i) {
            this.connectTimeout = Integer.valueOf(i);
            return this;
        }
    }
}
