package okhttp3.internal.http2;

import com.baidu.kwgames.GameSettingFloat;
import com.google.common.net.HttpHeaders;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Http2Reader;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
/* compiled from: Http2Connection.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000 \u008e\u00012\u00020\u0001:\b\u008d\u0001\u008e\u0001\u008f\u0001\u0090\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010H\u001a\u00020IJ\b\u0010J\u001a\u00020IH\u0016J'\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020L2\b\u0010N\u001a\u0004\u0018\u00010OH\u0000¢\u0006\u0002\bPJ\u0012\u0010Q\u001a\u00020I2\b\u0010R\u001a\u0004\u0018\u00010OH\u0002J\u0006\u0010S\u001a\u00020IJ\u0010\u0010T\u001a\u0004\u0018\u00010;2\u0006\u0010U\u001a\u00020\u0010J\u0006\u0010V\u001a\u00020\u0010J&\u0010W\u001a\u00020;2\u0006\u0010X\u001a\u00020\u00102\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020[0Z2\u0006\u0010\\\u001a\u00020\u0006H\u0002J\u001c\u0010W\u001a\u00020;2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020[0Z2\u0006\u0010\\\u001a\u00020\u0006J\u0006\u0010]\u001a\u00020\u0010J-\u0010^\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020\u00102\u0006\u0010c\u001a\u00020\u0006H\u0000¢\u0006\u0002\bdJ+\u0010e\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020[0Z2\u0006\u0010c\u001a\u00020\u0006H\u0000¢\u0006\u0002\bfJ#\u0010g\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020[0ZH\u0000¢\u0006\u0002\bhJ\u001d\u0010i\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010j\u001a\u00020LH\u0000¢\u0006\u0002\bkJ$\u0010l\u001a\u00020;2\u0006\u0010X\u001a\u00020\u00102\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020[0Z2\u0006\u0010\\\u001a\u00020\u0006J\u0015\u0010m\u001a\u00020\u00062\u0006\u0010_\u001a\u00020\u0010H\u0000¢\u0006\u0002\bnJ\u0017\u0010o\u001a\u0004\u0018\u00010;2\u0006\u0010_\u001a\u00020\u0010H\u0000¢\u0006\u0002\bpJ\u000e\u0010q\u001a\u00020I2\u0006\u0010r\u001a\u00020\"J\u000e\u0010s\u001a\u00020I2\u0006\u0010t\u001a\u00020LJ\u0012\u0010u\u001a\u00020I2\b\b\u0002\u0010v\u001a\u00020\u0006H\u0007J\u0015\u0010w\u001a\u00020I2\u0006\u0010x\u001a\u00020+H\u0000¢\u0006\u0002\byJ(\u0010z\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010{\u001a\u00020\u00062\b\u0010|\u001a\u0004\u0018\u00010}2\u0006\u0010b\u001a\u00020+J,\u0010~\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010{\u001a\u00020\u00062\f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020[0ZH\u0000¢\u0006\u0003\b\u0080\u0001J\"\u0010\u0081\u0001\u001a\u00020I2\u0007\u0010\u0082\u0001\u001a\u00020\u00062\u0007\u0010\u0083\u0001\u001a\u00020\u00102\u0007\u0010\u0084\u0001\u001a\u00020\u0010J\u0007\u0010\u0085\u0001\u001a\u00020IJ\u001f\u0010\u0086\u0001\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010t\u001a\u00020LH\u0000¢\u0006\u0003\b\u0087\u0001J\u001f\u0010\u0088\u0001\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0006\u0010j\u001a\u00020LH\u0000¢\u0006\u0003\b\u0089\u0001J \u0010\u008a\u0001\u001a\u00020I2\u0006\u0010_\u001a\u00020\u00102\u0007\u0010\u008b\u0001\u001a\u00020+H\u0000¢\u0006\u0003\b\u008c\u0001R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00068F@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\t\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0017\"\u0004\b \u0010\u0019R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010,\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020+@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u001e\u0010/\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020+@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b0\u0010.R\u0015\u00101\u001a\u000602R\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0014\u00105\u001a\u000206X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R \u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020;0:X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u001e\u0010>\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020+@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b?\u0010.R\u001e\u0010@\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020+@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bA\u0010.R\u0011\u0010B\u001a\u00020C¢\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020GX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0091\u0001"}, d2 = {"Lokhttp3/internal/http2/Http2Connection;", "Ljava/io/Closeable;", "builder", "Lokhttp3/internal/http2/Http2Connection$Builder;", "(Lokhttp3/internal/http2/Http2Connection$Builder;)V", "awaitingPong", "", "client", "getClient$okhttp", "()Z", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "currentPushRequests", "", "", "<set-?>", "isShutdown", "setShutdown$okhttp", "(Z)V", "lastGoodStreamId", "getLastGoodStreamId$okhttp", "()I", "setLastGoodStreamId$okhttp", "(I)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "nextStreamId", "getNextStreamId$okhttp", "setNextStreamId$okhttp", "okHttpSettings", "Lokhttp3/internal/http2/Settings;", "getOkHttpSettings", "()Lokhttp3/internal/http2/Settings;", "peerSettings", "getPeerSettings", "pushExecutor", "Ljava/util/concurrent/ThreadPoolExecutor;", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "readBytesTotal", "getReadBytesTotal", "readerRunnable", "Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "getReaderRunnable", "()Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "streams", "", "Lokhttp3/internal/http2/Http2Stream;", "getStreams$okhttp", "()Ljava/util/Map;", "writeBytesMaximum", "getWriteBytesMaximum", "writeBytesTotal", "getWriteBytesTotal", "writer", "Lokhttp3/internal/http2/Http2Writer;", "getWriter", "()Lokhttp3/internal/http2/Http2Writer;", "writerExecutor", "Ljava/util/concurrent/ScheduledThreadPoolExecutor;", "awaitPong", "", "close", "connectionCode", "Lokhttp3/internal/http2/ErrorCode;", "streamCode", "cause", "Ljava/io/IOException;", "close$okhttp", "failConnection", "e", "flush", "getStream", "id", "maxConcurrentStreams", "newStream", "associatedStreamId", "requestHeaders", "", "Lokhttp3/internal/http2/Header;", "out", "openStreamCount", "pushDataLater", "streamId", "source", "Lokio/BufferedSource;", "byteCount", "inFinished", "pushDataLater$okhttp", "pushHeadersLater", "pushHeadersLater$okhttp", "pushRequestLater", "pushRequestLater$okhttp", "pushResetLater", "errorCode", "pushResetLater$okhttp", "pushStream", "pushedStream", "pushedStream$okhttp", "removeStream", "removeStream$okhttp", "setSettings", "settings", "shutdown", "statusCode", "start", "sendConnectionPreface", "updateConnectionFlowControl", "read", "updateConnectionFlowControl$okhttp", "writeData", "outFinished", "buffer", "Lokio/Buffer;", "writeHeaders", "alternating", "writeHeaders$okhttp", "writePing", "reply", "payload1", "payload2", "writePingAndAwaitPong", "writeSynReset", "writeSynReset$okhttp", "writeSynResetLater", "writeSynResetLater$okhttp", "writeWindowUpdateLater", "unacknowledgedBytesRead", "writeWindowUpdateLater$okhttp", "Builder", "Companion", "Listener", "ReaderRunnable", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class Http2Connection implements Closeable {
    public static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private boolean awaitingPong;
    private final boolean client;
    private final String connectionName;
    private final Set<Integer> currentPushRequests;
    private boolean isShutdown;
    private int lastGoodStreamId;
    private final Listener listener;
    private int nextStreamId;
    private final Settings okHttpSettings;
    private final Settings peerSettings;
    private final ThreadPoolExecutor pushExecutor;
    private final PushObserver pushObserver;
    private long readBytesAcknowledged;
    private long readBytesTotal;
    private final ReaderRunnable readerRunnable;
    private final Socket socket;
    private final Map<Integer, Http2Stream> streams;
    private long writeBytesMaximum;
    private long writeBytesTotal;
    private final Http2Writer writer;
    private final ScheduledThreadPoolExecutor writerExecutor;
    public static final Companion Companion = new Companion(null);
    private static final ThreadPoolExecutor listenerExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));

    public final boolean pushedStream$okhttp(int i) {
        return i != 0 && (i & 1) == 0;
    }

    public final void start() throws IOException {
        start$default(this, false, 1, null);
    }

    public Http2Connection(Builder builder) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        boolean client$okhttp = builder.getClient$okhttp();
        this.client = client$okhttp;
        this.listener = builder.getListener$okhttp();
        this.streams = new LinkedHashMap();
        String connectionName$okhttp = builder.getConnectionName$okhttp();
        this.connectionName = connectionName$okhttp;
        this.nextStreamId = builder.getClient$okhttp() ? 3 : 2;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", connectionName$okhttp), false));
        this.writerExecutor = scheduledThreadPoolExecutor;
        this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", connectionName$okhttp), true));
        this.pushObserver = builder.getPushObserver$okhttp();
        Settings settings = new Settings();
        if (builder.getClient$okhttp()) {
            settings.set(7, 16777216);
        }
        this.okHttpSettings = settings;
        Settings settings2 = new Settings();
        settings2.set(7, 65535);
        settings2.set(5, 16384);
        this.peerSettings = settings2;
        this.writeBytesMaximum = settings2.getInitialWindowSize();
        this.socket = builder.getSocket$okhttp();
        this.writer = new Http2Writer(builder.getSink$okhttp(), client$okhttp);
        this.readerRunnable = new ReaderRunnable(this, new Http2Reader(builder.getSource$okhttp(), client$okhttp));
        this.currentPushRequests = new LinkedHashSet();
        if (builder.getPingIntervalMillis$okhttp() != 0) {
            scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection.1
                {
                    Http2Connection.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Thread currentThread = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                    String name = currentThread.getName();
                    currentThread.setName("OkHttp " + Http2Connection.this.getConnectionName$okhttp() + " ping");
                    try {
                        Http2Connection.this.writePing(false, 0, 0);
                    } finally {
                        currentThread.setName(name);
                    }
                }
            }, builder.getPingIntervalMillis$okhttp(), builder.getPingIntervalMillis$okhttp(), TimeUnit.MILLISECONDS);
        }
    }

    public final boolean getClient$okhttp() {
        return this.client;
    }

    public final Listener getListener$okhttp() {
        return this.listener;
    }

    public final Map<Integer, Http2Stream> getStreams$okhttp() {
        return this.streams;
    }

    public final String getConnectionName$okhttp() {
        return this.connectionName;
    }

    public final int getLastGoodStreamId$okhttp() {
        return this.lastGoodStreamId;
    }

    public final void setLastGoodStreamId$okhttp(int i) {
        this.lastGoodStreamId = i;
    }

    public final int getNextStreamId$okhttp() {
        return this.nextStreamId;
    }

    public final void setNextStreamId$okhttp(int i) {
        this.nextStreamId = i;
    }

    public final synchronized boolean isShutdown() {
        return this.isShutdown;
    }

    public final void setShutdown$okhttp(boolean z) {
        this.isShutdown = z;
    }

    public final Settings getOkHttpSettings() {
        return this.okHttpSettings;
    }

    public final Settings getPeerSettings() {
        return this.peerSettings;
    }

    public final long getReadBytesTotal() {
        return this.readBytesTotal;
    }

    public final long getReadBytesAcknowledged() {
        return this.readBytesAcknowledged;
    }

    public final long getWriteBytesTotal() {
        return this.writeBytesTotal;
    }

    public final long getWriteBytesMaximum() {
        return this.writeBytesMaximum;
    }

    public final Socket getSocket$okhttp() {
        return this.socket;
    }

    public final Http2Writer getWriter() {
        return this.writer;
    }

    public final ReaderRunnable getReaderRunnable() {
        return this.readerRunnable;
    }

    public final synchronized int openStreamCount() {
        return this.streams.size();
    }

    public final synchronized Http2Stream getStream(int i) {
        return this.streams.get(Integer.valueOf(i));
    }

    public final synchronized Http2Stream removeStream$okhttp(int i) {
        Http2Stream remove;
        remove = this.streams.remove(Integer.valueOf(i));
        notifyAll();
        return remove;
    }

    public final synchronized int maxConcurrentStreams() {
        return this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
    }

    public final synchronized void updateConnectionFlowControl$okhttp(long j) {
        long j2 = this.readBytesTotal + j;
        this.readBytesTotal = j2;
        long j3 = j2 - this.readBytesAcknowledged;
        if (j3 >= this.okHttpSettings.getInitialWindowSize() / 2) {
            writeWindowUpdateLater$okhttp(0, j3);
            this.readBytesAcknowledged += j3;
        }
    }

    public final Http2Stream pushStream(int i, List<Header> requestHeaders, boolean z) throws IOException {
        Intrinsics.checkParameterIsNotNull(requestHeaders, "requestHeaders");
        if (!(!this.client)) {
            throw new IllegalStateException("Client cannot push requests.".toString());
        }
        return newStream(i, requestHeaders, z);
    }

    public final Http2Stream newStream(List<Header> requestHeaders, boolean z) throws IOException {
        Intrinsics.checkParameterIsNotNull(requestHeaders, "requestHeaders");
        return newStream(0, requestHeaders, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x0048 A[Catch: all -> 0x0087, TryCatch #1 {, blocks: (B:49:0x0006, B:68:0x0053, B:70:0x0056, B:74:0x0066, B:71:0x005c, B:73:0x0061, B:79:0x0071, B:80:0x007e, B:50:0x0007, B:52:0x000e, B:53:0x0013, B:55:0x0017, B:57:0x002a, B:59:0x0032, B:64:0x0042, B:66:0x0048, B:67:0x0051, B:81:0x007f, B:82:0x0086), top: B:89:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final okhttp3.internal.http2.Http2Stream newStream(int r11, java.util.List<okhttp3.internal.http2.Header> r12, boolean r13) throws java.io.IOException {
        /*
            r10 = this;
            r6 = r13 ^ 1
            r4 = 0
            okhttp3.internal.http2.Http2Writer r7 = r10.writer
            monitor-enter(r7)
            monitor-enter(r10)     // Catch: java.lang.Throwable -> L8a
            int r0 = r10.nextStreamId     // Catch: java.lang.Throwable -> L87
            r1 = 1073741823(0x3fffffff, float:1.9999999)
            if (r0 <= r1) goto L13
            okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.REFUSED_STREAM     // Catch: java.lang.Throwable -> L87
            r10.shutdown(r0)     // Catch: java.lang.Throwable -> L87
        L13:
            boolean r0 = r10.isShutdown     // Catch: java.lang.Throwable -> L87
            if (r0 != 0) goto L7f
            int r8 = r10.nextStreamId     // Catch: java.lang.Throwable -> L87
            int r0 = r8 + 2
            r10.nextStreamId = r0     // Catch: java.lang.Throwable -> L87
            okhttp3.internal.http2.Http2Stream r9 = new okhttp3.internal.http2.Http2Stream     // Catch: java.lang.Throwable -> L87
            r5 = 0
            r0 = r9
            r1 = r8
            r2 = r10
            r3 = r6
            r0.<init>(r1, r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L87
            r0 = 1
            if (r13 == 0) goto L41
            long r1 = r10.writeBytesTotal     // Catch: java.lang.Throwable -> L87
            long r3 = r10.writeBytesMaximum     // Catch: java.lang.Throwable -> L87
            int r13 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r13 >= 0) goto L41
            long r1 = r9.getWriteBytesTotal()     // Catch: java.lang.Throwable -> L87
            long r3 = r9.getWriteBytesMaximum()     // Catch: java.lang.Throwable -> L87
            int r13 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r13 < 0) goto L3f
            goto L41
        L3f:
            r13 = 0
            goto L42
        L41:
            r13 = 1
        L42:
            boolean r1 = r9.isOpen()     // Catch: java.lang.Throwable -> L87
            if (r1 == 0) goto L51
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r1 = r10.streams     // Catch: java.lang.Throwable -> L87
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L87
            r1.put(r2, r9)     // Catch: java.lang.Throwable -> L87
        L51:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L87
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L8a
            if (r11 != 0) goto L5c
            okhttp3.internal.http2.Http2Writer r11 = r10.writer     // Catch: java.lang.Throwable -> L8a
            r11.headers(r6, r8, r12)     // Catch: java.lang.Throwable -> L8a
            goto L66
        L5c:
            boolean r1 = r10.client     // Catch: java.lang.Throwable -> L8a
            r0 = r0 ^ r1
            if (r0 == 0) goto L71
            okhttp3.internal.http2.Http2Writer r0 = r10.writer     // Catch: java.lang.Throwable -> L8a
            r0.pushPromise(r11, r8, r12)     // Catch: java.lang.Throwable -> L8a
        L66:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L8a
            monitor-exit(r7)
            if (r13 == 0) goto L70
            okhttp3.internal.http2.Http2Writer r11 = r10.writer
            r11.flush()
        L70:
            return r9
        L71:
            java.lang.String r11 = "client streams shouldn't have associated stream IDs"
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L8a
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> L8a
            r12.<init>(r11)     // Catch: java.lang.Throwable -> L8a
            java.lang.Throwable r12 = (java.lang.Throwable) r12     // Catch: java.lang.Throwable -> L8a
            throw r12     // Catch: java.lang.Throwable -> L8a
        L7f:
            okhttp3.internal.http2.ConnectionShutdownException r11 = new okhttp3.internal.http2.ConnectionShutdownException     // Catch: java.lang.Throwable -> L87
            r11.<init>()     // Catch: java.lang.Throwable -> L87
            java.lang.Throwable r11 = (java.lang.Throwable) r11     // Catch: java.lang.Throwable -> L87
            throw r11     // Catch: java.lang.Throwable -> L87
        L87:
            r11 = move-exception
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L8a
            throw r11     // Catch: java.lang.Throwable -> L8a
        L8a:
            r11 = move-exception
            monitor-exit(r7)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.newStream(int, java.util.List, boolean):okhttp3.internal.http2.Http2Stream");
    }

    public final void writeHeaders$okhttp(int i, boolean z, List<Header> alternating) throws IOException {
        Intrinsics.checkParameterIsNotNull(alternating, "alternating");
        this.writer.headers(z, i, alternating);
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x003b, code lost:
        throw new java.io.IOException("stream closed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x003d, code lost:
        r3.element = (int) java.lang.Math.min(r13, r6 - r4);
        r3.element = java.lang.Math.min(r3.element, r9.writer.maxDataLength());
        r9.writeBytesTotal += r3.element;
        r4 = kotlin.Unit.INSTANCE;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void writeData(int r10, boolean r11, okio.Buffer r12, long r13) throws java.io.IOException {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
            int r3 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r3 != 0) goto Ld
            okhttp3.internal.http2.Http2Writer r13 = r9.writer
            r13.data(r11, r10, r12, r0)
            return
        Ld:
            int r3 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r3 <= 0) goto L85
            kotlin.jvm.internal.Ref$IntRef r3 = new kotlin.jvm.internal.Ref$IntRef
            r3.<init>()
            monitor-enter(r9)
        L17:
            long r4 = r9.writeBytesTotal     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            long r6 = r9.writeBytesMaximum     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 < 0) goto L3c
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r4 = r9.streams     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            java.lang.Integer r5 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            boolean r4 = r4.containsKey(r5)     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            if (r4 == 0) goto L32
            r4 = r9
            java.lang.Object r4 = (java.lang.Object) r4     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            r4.wait()     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            goto L17
        L32:
            java.io.IOException r10 = new java.io.IOException     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            java.lang.String r11 = "stream closed"
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            java.lang.Throwable r10 = (java.lang.Throwable) r10     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
            throw r10     // Catch: java.lang.Throwable -> L72 java.lang.InterruptedException -> L74
        L3c:
            long r6 = r6 - r4
            long r4 = java.lang.Math.min(r13, r6)     // Catch: java.lang.Throwable -> L72
            int r5 = (int) r4     // Catch: java.lang.Throwable -> L72
            r3.element = r5     // Catch: java.lang.Throwable -> L72
            int r4 = r3.element     // Catch: java.lang.Throwable -> L72
            okhttp3.internal.http2.Http2Writer r5 = r9.writer     // Catch: java.lang.Throwable -> L72
            int r5 = r5.maxDataLength()     // Catch: java.lang.Throwable -> L72
            int r4 = java.lang.Math.min(r4, r5)     // Catch: java.lang.Throwable -> L72
            r3.element = r4     // Catch: java.lang.Throwable -> L72
            long r4 = r9.writeBytesTotal     // Catch: java.lang.Throwable -> L72
            int r6 = r3.element     // Catch: java.lang.Throwable -> L72
            long r6 = (long) r6     // Catch: java.lang.Throwable -> L72
            long r4 = r4 + r6
            r9.writeBytesTotal = r4     // Catch: java.lang.Throwable -> L72
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L72
            monitor-exit(r9)
            int r4 = r3.element
            long r4 = (long) r4
            long r13 = r13 - r4
            okhttp3.internal.http2.Http2Writer r4 = r9.writer
            if (r11 == 0) goto L6b
            int r5 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r5 != 0) goto L6b
            r5 = 1
            goto L6c
        L6b:
            r5 = 0
        L6c:
            int r3 = r3.element
            r4.data(r5, r10, r12, r3)
            goto Ld
        L72:
            r10 = move-exception
            goto L83
        L74:
            java.lang.Thread r10 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L72
            r10.interrupt()     // Catch: java.lang.Throwable -> L72
            java.io.InterruptedIOException r10 = new java.io.InterruptedIOException     // Catch: java.lang.Throwable -> L72
            r10.<init>()     // Catch: java.lang.Throwable -> L72
            java.lang.Throwable r10 = (java.lang.Throwable) r10     // Catch: java.lang.Throwable -> L72
            throw r10     // Catch: java.lang.Throwable -> L72
        L83:
            monitor-exit(r9)
            throw r10
        L85:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.writeData(int, boolean, okio.Buffer, long):void");
    }

    public final void writeSynResetLater$okhttp(final int i, final ErrorCode errorCode) {
        Intrinsics.checkParameterIsNotNull(errorCode, "errorCode");
        final String str = "OkHttp " + this.connectionName + " stream " + i;
        try {
            this.writerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$writeSynResetLater$$inlined$tryExecute$1
                @Override // java.lang.Runnable
                public final void run() {
                    String str2 = str;
                    Thread currentThread = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                    String name = currentThread.getName();
                    currentThread.setName(str2);
                    try {
                        try {
                            this.writeSynReset$okhttp(i, errorCode);
                        } catch (IOException e) {
                            this.failConnection(e);
                        }
                    } finally {
                        currentThread.setName(name);
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    public final void writeSynReset$okhttp(int i, ErrorCode statusCode) throws IOException {
        Intrinsics.checkParameterIsNotNull(statusCode, "statusCode");
        this.writer.rstStream(i, statusCode);
    }

    public final void writeWindowUpdateLater$okhttp(final int i, final long j) {
        final String str = "OkHttp Window Update " + this.connectionName + " stream " + i;
        try {
            this.writerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$writeWindowUpdateLater$$inlined$tryExecute$1
                @Override // java.lang.Runnable
                public final void run() {
                    String str2 = str;
                    Thread currentThread = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                    String name = currentThread.getName();
                    currentThread.setName(str2);
                    try {
                        try {
                            this.getWriter().windowUpdate(i, j);
                        } catch (IOException e) {
                            this.failConnection(e);
                        }
                    } finally {
                        currentThread.setName(name);
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    public final void writePing(boolean z, int i, int i2) {
        boolean z2;
        if (!z) {
            synchronized (this) {
                z2 = this.awaitingPong;
                this.awaitingPong = true;
                Unit unit = Unit.INSTANCE;
            }
            if (z2) {
                failConnection(null);
                return;
            }
        }
        try {
            this.writer.ping(z, i, i2);
        } catch (IOException e) {
            failConnection(e);
        }
    }

    public final void writePingAndAwaitPong() throws InterruptedException {
        writePing(false, 1330343787, -257978967);
        awaitPong();
    }

    public final synchronized void awaitPong() throws InterruptedException {
        while (this.awaitingPong) {
            wait();
        }
    }

    public final void flush() throws IOException {
        this.writer.flush();
    }

    public final void shutdown(ErrorCode statusCode) throws IOException {
        Intrinsics.checkParameterIsNotNull(statusCode, "statusCode");
        synchronized (this.writer) {
            synchronized (this) {
                if (this.isShutdown) {
                    return;
                }
                this.isShutdown = true;
                int i = this.lastGoodStreamId;
                Unit unit = Unit.INSTANCE;
                this.writer.goAway(i, statusCode, Util.EMPTY_BYTE_ARRAY);
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        close$okhttp(ErrorCode.NO_ERROR, ErrorCode.CANCEL, null);
    }

    public final void close$okhttp(ErrorCode connectionCode, ErrorCode streamCode, IOException iOException) {
        int i;
        Intrinsics.checkParameterIsNotNull(connectionCode, "connectionCode");
        Intrinsics.checkParameterIsNotNull(streamCode, "streamCode");
        Thread.holdsLock(this);
        try {
            shutdown(connectionCode);
        } catch (IOException unused) {
        }
        Http2Stream[] http2StreamArr = null;
        synchronized (this) {
            if (!this.streams.isEmpty()) {
                Object[] array = this.streams.values().toArray(new Http2Stream[0]);
                if (array != null) {
                    http2StreamArr = (Http2Stream[]) array;
                    this.streams.clear();
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        if (http2StreamArr != null) {
            for (Http2Stream http2Stream : http2StreamArr) {
                try {
                    http2Stream.close(streamCode, iOException);
                } catch (IOException unused2) {
                }
            }
        }
        try {
            this.writer.close();
        } catch (IOException unused3) {
        }
        try {
            this.socket.close();
        } catch (IOException unused4) {
        }
        this.writerExecutor.shutdown();
        this.pushExecutor.shutdown();
    }

    public final void failConnection(IOException iOException) {
        close$okhttp(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR, iOException);
    }

    public static /* synthetic */ void start$default(Http2Connection http2Connection, boolean z, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            z = true;
        }
        http2Connection.start(z);
    }

    public final void start(boolean z) throws IOException {
        if (z) {
            this.writer.connectionPreface();
            this.writer.settings(this.okHttpSettings);
            int initialWindowSize = this.okHttpSettings.getInitialWindowSize();
            if (initialWindowSize != 65535) {
                this.writer.windowUpdate(0, initialWindowSize - 65535);
            }
        }
        new Thread(this.readerRunnable, "OkHttp " + this.connectionName).start();
    }

    public final void setSettings(Settings settings) throws IOException {
        Intrinsics.checkParameterIsNotNull(settings, "settings");
        synchronized (this.writer) {
            synchronized (this) {
                if (this.isShutdown) {
                    throw new ConnectionShutdownException();
                }
                this.okHttpSettings.merge(settings);
                Unit unit = Unit.INSTANCE;
            }
            this.writer.settings(settings);
            Unit unit2 = Unit.INSTANCE;
        }
    }

    /* compiled from: Http2Connection.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u00102\u001a\u000203J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ.\u0010&\u001a\u00020\u00002\u0006\u0010&\u001a\u00020'2\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010,\u001a\u00020-2\b\b\u0002\u0010 \u001a\u00020!H\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001a\u0010\b\u001a\u00020\tX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020-X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101¨\u00064"}, d2 = {"Lokhttp3/internal/http2/Http2Connection$Builder;", "", "client", "", "(Z)V", "getClient$okhttp", "()Z", "setClient$okhttp", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "setConnectionName$okhttp", "(Ljava/lang/String;)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "setListener$okhttp", "(Lokhttp3/internal/http2/Http2Connection$Listener;)V", "pingIntervalMillis", "", "getPingIntervalMillis$okhttp", "()I", "setPingIntervalMillis$okhttp", "(I)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "getPushObserver$okhttp", "()Lokhttp3/internal/http2/PushObserver;", "setPushObserver$okhttp", "(Lokhttp3/internal/http2/PushObserver;)V", "sink", "Lokio/BufferedSink;", "getSink$okhttp", "()Lokio/BufferedSink;", "setSink$okhttp", "(Lokio/BufferedSink;)V", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "setSocket$okhttp", "(Ljava/net/Socket;)V", "source", "Lokio/BufferedSource;", "getSource$okhttp", "()Lokio/BufferedSource;", "setSource$okhttp", "(Lokio/BufferedSource;)V", "build", "Lokhttp3/internal/http2/Http2Connection;", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public static final class Builder {
        private boolean client;
        public String connectionName;
        private int pingIntervalMillis;
        public BufferedSink sink;
        public Socket socket;
        public BufferedSource source;
        private Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        private PushObserver pushObserver = PushObserver.CANCEL;

        public final Builder socket(Socket socket) throws IOException {
            return socket$default(this, socket, null, null, null, 14, null);
        }

        public final Builder socket(Socket socket, String str) throws IOException {
            return socket$default(this, socket, str, null, null, 12, null);
        }

        public final Builder socket(Socket socket, String str, BufferedSource bufferedSource) throws IOException {
            return socket$default(this, socket, str, bufferedSource, null, 8, null);
        }

        public Builder(boolean z) {
            this.client = z;
        }

        public final boolean getClient$okhttp() {
            return this.client;
        }

        public final void setClient$okhttp(boolean z) {
            this.client = z;
        }

        public final Socket getSocket$okhttp() {
            Socket socket = this.socket;
            if (socket == null) {
                Intrinsics.throwUninitializedPropertyAccessException("socket");
            }
            return socket;
        }

        public final void setSocket$okhttp(Socket socket) {
            Intrinsics.checkParameterIsNotNull(socket, "<set-?>");
            this.socket = socket;
        }

        public final String getConnectionName$okhttp() {
            String str = this.connectionName;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("connectionName");
            }
            return str;
        }

        public final void setConnectionName$okhttp(String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.connectionName = str;
        }

        public final BufferedSource getSource$okhttp() {
            BufferedSource bufferedSource = this.source;
            if (bufferedSource == null) {
                Intrinsics.throwUninitializedPropertyAccessException("source");
            }
            return bufferedSource;
        }

        public final void setSource$okhttp(BufferedSource bufferedSource) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "<set-?>");
            this.source = bufferedSource;
        }

        public final BufferedSink getSink$okhttp() {
            BufferedSink bufferedSink = this.sink;
            if (bufferedSink == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sink");
            }
            return bufferedSink;
        }

        public final void setSink$okhttp(BufferedSink bufferedSink) {
            Intrinsics.checkParameterIsNotNull(bufferedSink, "<set-?>");
            this.sink = bufferedSink;
        }

        public final Listener getListener$okhttp() {
            return this.listener;
        }

        public final void setListener$okhttp(Listener listener) {
            Intrinsics.checkParameterIsNotNull(listener, "<set-?>");
            this.listener = listener;
        }

        public final PushObserver getPushObserver$okhttp() {
            return this.pushObserver;
        }

        public final void setPushObserver$okhttp(PushObserver pushObserver) {
            Intrinsics.checkParameterIsNotNull(pushObserver, "<set-?>");
            this.pushObserver = pushObserver;
        }

        public final int getPingIntervalMillis$okhttp() {
            return this.pingIntervalMillis;
        }

        public final void setPingIntervalMillis$okhttp(int i) {
            this.pingIntervalMillis = i;
        }

        public static /* synthetic */ Builder socket$default(Builder builder, Socket socket, String str, BufferedSource bufferedSource, BufferedSink bufferedSink, int i, Object obj) throws IOException {
            if ((i & 2) != 0) {
                str = Util.connectionName(socket);
            }
            if ((i & 4) != 0) {
                bufferedSource = Okio.buffer(Okio.source(socket));
            }
            if ((i & 8) != 0) {
                bufferedSink = Okio.buffer(Okio.sink(socket));
            }
            return builder.socket(socket, str, bufferedSource, bufferedSink);
        }

        public final Builder socket(Socket socket, String connectionName, BufferedSource source, BufferedSink sink) throws IOException {
            Intrinsics.checkParameterIsNotNull(socket, "socket");
            Intrinsics.checkParameterIsNotNull(connectionName, "connectionName");
            Intrinsics.checkParameterIsNotNull(source, "source");
            Intrinsics.checkParameterIsNotNull(sink, "sink");
            this.socket = socket;
            this.connectionName = connectionName;
            this.source = source;
            this.sink = sink;
            return this;
        }

        public final Builder listener(Listener listener) {
            Intrinsics.checkParameterIsNotNull(listener, "listener");
            this.listener = listener;
            return this;
        }

        public final Builder pushObserver(PushObserver pushObserver) {
            Intrinsics.checkParameterIsNotNull(pushObserver, "pushObserver");
            this.pushObserver = pushObserver;
            return this;
        }

        public final Builder pingIntervalMillis(int i) {
            this.pingIntervalMillis = i;
            return this;
        }

        public final Http2Connection build() {
            return new Http2Connection(this);
        }
    }

    /* compiled from: Http2Connection.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J8\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J(\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\fH\u0016J \u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0010H\u0016J.\u0010$\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010%\u001a\u00020\f2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J \u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\fH\u0016J(\u0010-\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\u0017H\u0016J&\u00101\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00102\u001a\u00020\f2\f\u00103\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J\u0018\u00104\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0016J\b\u00105\u001a\u00020\tH\u0016J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u00106\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00107\u001a\u00020\u0014H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u00068"}, d2 = {"Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "Ljava/lang/Runnable;", "Lokhttp3/internal/http2/Http2Reader$Handler;", "reader", "Lokhttp3/internal/http2/Http2Reader;", "(Lokhttp3/internal/http2/Http2Connection;Lokhttp3/internal/http2/Http2Reader;)V", "getReader$okhttp", "()Lokhttp3/internal/http2/Http2Reader;", "ackSettings", "", "alternateService", "streamId", "", HttpHeaders.ReferrerPolicyValues.ORIGIN, "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "applyAndAckSettings", "clearPrevious", "", "settings", "Lokhttp3/internal/http2/Settings;", GameSettingFloat.TAG_ATTR_DATA, "inFinished", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "run", "windowUpdate", "windowSizeIncrement", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public final class ReaderRunnable implements Runnable, Http2Reader.Handler {
        private final Http2Reader reader;
        final /* synthetic */ Http2Connection this$0;

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void ackSettings() {
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void alternateService(int i, String origin, ByteString protocol, String host, int i2, long j) {
            Intrinsics.checkParameterIsNotNull(origin, "origin");
            Intrinsics.checkParameterIsNotNull(protocol, "protocol");
            Intrinsics.checkParameterIsNotNull(host, "host");
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void priority(int i, int i2, int i3, boolean z) {
        }

        public ReaderRunnable(Http2Connection http2Connection, Http2Reader reader) {
            Intrinsics.checkParameterIsNotNull(reader, "reader");
            this.this$0 = http2Connection;
            this.reader = reader;
        }

        public final Http2Reader getReader$okhttp() {
            return this.reader;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [okhttp3.internal.http2.ErrorCode] */
        /* JADX WARN: Type inference failed for: r0v7, types: [java.io.Closeable] */
        /* JADX WARN: Type inference failed for: r0v8, types: [okhttp3.internal.http2.ErrorCode] */
        @Override // java.lang.Runnable
        public void run() {
            ErrorCode errorCode;
            Http2Reader http2Reader = ErrorCode.INTERNAL_ERROR;
            ErrorCode errorCode2 = ErrorCode.INTERNAL_ERROR;
            IOException e = null;
            try {
                try {
                    this.reader.readConnectionPreface(this);
                    while (this.reader.nextFrame(false, this)) {
                    }
                    http2Reader = ErrorCode.NO_ERROR;
                    errorCode2 = ErrorCode.CANCEL;
                    errorCode = http2Reader;
                } catch (IOException e2) {
                    e = e2;
                    ErrorCode errorCode3 = ErrorCode.PROTOCOL_ERROR;
                    errorCode2 = ErrorCode.PROTOCOL_ERROR;
                    errorCode = errorCode3;
                }
            } finally {
                this.this$0.close$okhttp(http2Reader, errorCode2, e);
                Util.closeQuietly(this.reader);
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void data(boolean z, int i, BufferedSource source, int i2) throws IOException {
            Intrinsics.checkParameterIsNotNull(source, "source");
            if (this.this$0.pushedStream$okhttp(i)) {
                this.this$0.pushDataLater$okhttp(i, source, i2, z);
                return;
            }
            Http2Stream stream = this.this$0.getStream(i);
            if (stream == null) {
                this.this$0.writeSynResetLater$okhttp(i, ErrorCode.PROTOCOL_ERROR);
                long j = i2;
                this.this$0.updateConnectionFlowControl$okhttp(j);
                source.skip(j);
                return;
            }
            stream.receiveData(source, i2);
            if (z) {
                stream.receiveHeaders(Util.EMPTY_HEADERS, true);
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void headers(final boolean z, final int i, int i2, final List<Header> headerBlock) {
            Intrinsics.checkParameterIsNotNull(headerBlock, "headerBlock");
            if (this.this$0.pushedStream$okhttp(i)) {
                this.this$0.pushHeadersLater$okhttp(i, headerBlock, z);
                return;
            }
            synchronized (this.this$0) {
                final Http2Stream stream = this.this$0.getStream(i);
                if (stream == null) {
                    if (this.this$0.isShutdown()) {
                        return;
                    }
                    if (i <= this.this$0.getLastGoodStreamId$okhttp()) {
                        return;
                    }
                    if (i % 2 == this.this$0.getNextStreamId$okhttp() % 2) {
                        return;
                    }
                    final Http2Stream http2Stream = new Http2Stream(i, this.this$0, false, z, Util.toHeaders(headerBlock));
                    this.this$0.setLastGoodStreamId$okhttp(i);
                    this.this$0.getStreams$okhttp().put(Integer.valueOf(i), http2Stream);
                    final String str = "OkHttp " + this.this$0.getConnectionName$okhttp() + " stream " + i;
                    Http2Connection.listenerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str2 = str;
                            Thread currentThread = Thread.currentThread();
                            Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                            String name = currentThread.getName();
                            currentThread.setName(str2);
                            try {
                                try {
                                    this.this$0.getListener$okhttp().onStream(http2Stream);
                                } catch (IOException e) {
                                    Platform platform = Platform.Companion.get();
                                    platform.log(4, "Http2Connection.Listener failure for " + this.this$0.getConnectionName$okhttp(), e);
                                    try {
                                        http2Stream.close(ErrorCode.PROTOCOL_ERROR, e);
                                    } catch (IOException unused) {
                                    }
                                }
                            } finally {
                                currentThread.setName(name);
                            }
                        }
                    });
                    return;
                }
                Unit unit = Unit.INSTANCE;
                stream.receiveHeaders(Util.toHeaders(headerBlock), z);
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void rstStream(int i, ErrorCode errorCode) {
            Intrinsics.checkParameterIsNotNull(errorCode, "errorCode");
            if (this.this$0.pushedStream$okhttp(i)) {
                this.this$0.pushResetLater$okhttp(i, errorCode);
                return;
            }
            Http2Stream removeStream$okhttp = this.this$0.removeStream$okhttp(i);
            if (removeStream$okhttp != null) {
                removeStream$okhttp.receiveRstStream(errorCode);
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void settings(final boolean z, final Settings settings) {
            Intrinsics.checkParameterIsNotNull(settings, "settings");
            final String str = "OkHttp " + this.this$0.getConnectionName$okhttp() + " ACK Settings";
            try {
                this.this$0.writerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$ReaderRunnable$settings$$inlined$tryExecute$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str2 = str;
                        Thread currentThread = Thread.currentThread();
                        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                        String name = currentThread.getName();
                        currentThread.setName(str2);
                        try {
                            this.applyAndAckSettings(z, settings);
                        } finally {
                            currentThread.setName(name);
                        }
                    }
                });
            } catch (RejectedExecutionException unused) {
            }
        }

        public final void applyAndAckSettings(boolean z, Settings settings) {
            int i;
            long j;
            Intrinsics.checkParameterIsNotNull(settings, "settings");
            Http2Stream[] http2StreamArr = null;
            synchronized (this.this$0.getWriter()) {
                synchronized (this.this$0) {
                    int initialWindowSize = this.this$0.getPeerSettings().getInitialWindowSize();
                    if (z) {
                        this.this$0.getPeerSettings().clear();
                    }
                    this.this$0.getPeerSettings().merge(settings);
                    int initialWindowSize2 = this.this$0.getPeerSettings().getInitialWindowSize();
                    if (initialWindowSize2 == -1 || initialWindowSize2 == initialWindowSize) {
                        j = 0;
                    } else {
                        j = initialWindowSize2 - initialWindowSize;
                        if (!this.this$0.getStreams$okhttp().isEmpty()) {
                            Object[] array = this.this$0.getStreams$okhttp().values().toArray(new Http2Stream[0]);
                            if (array == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                            }
                            http2StreamArr = (Http2Stream[]) array;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                try {
                    this.this$0.getWriter().applyAndAckSettings(this.this$0.getPeerSettings());
                } catch (IOException e) {
                    this.this$0.failConnection(e);
                }
                Unit unit2 = Unit.INSTANCE;
            }
            if (http2StreamArr != null) {
                if (http2StreamArr == null) {
                    Intrinsics.throwNpe();
                }
                for (Http2Stream http2Stream : http2StreamArr) {
                    synchronized (http2Stream) {
                        http2Stream.addBytesToWriteWindow(j);
                        Unit unit3 = Unit.INSTANCE;
                    }
                }
            }
            final String str = "OkHttp " + this.this$0.getConnectionName$okhttp() + " settings";
            Http2Connection.listenerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$execute$1
                @Override // java.lang.Runnable
                public final void run() {
                    String str2 = str;
                    Thread currentThread = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                    String name = currentThread.getName();
                    currentThread.setName(str2);
                    try {
                        this.this$0.getListener$okhttp().onSettings(this.this$0);
                    } finally {
                        currentThread.setName(name);
                    }
                }
            });
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void ping(boolean z, final int i, final int i2) {
            if (!z) {
                final String str = "OkHttp " + this.this$0.getConnectionName$okhttp() + " ping";
                try {
                    this.this$0.writerExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$ReaderRunnable$ping$$inlined$tryExecute$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str2 = str;
                            Thread currentThread = Thread.currentThread();
                            Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                            String name = currentThread.getName();
                            currentThread.setName(str2);
                            try {
                                this.this$0.writePing(true, i, i2);
                            } finally {
                                currentThread.setName(name);
                            }
                        }
                    });
                    return;
                } catch (RejectedExecutionException unused) {
                    return;
                }
            }
            synchronized (this.this$0) {
                this.this$0.awaitingPong = false;
                Http2Connection http2Connection = this.this$0;
                if (http2Connection != null) {
                    http2Connection.notifyAll();
                    Unit unit = Unit.INSTANCE;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                }
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void goAway(int i, ErrorCode errorCode, ByteString debugData) {
            int i2;
            Http2Stream[] http2StreamArr;
            Intrinsics.checkParameterIsNotNull(errorCode, "errorCode");
            Intrinsics.checkParameterIsNotNull(debugData, "debugData");
            debugData.size();
            synchronized (this.this$0) {
                Object[] array = this.this$0.getStreams$okhttp().values().toArray(new Http2Stream[0]);
                if (array != null) {
                    http2StreamArr = (Http2Stream[]) array;
                    this.this$0.setShutdown$okhttp(true);
                    Unit unit = Unit.INSTANCE;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            for (Http2Stream http2Stream : http2StreamArr) {
                if (http2Stream.getId() > i && http2Stream.isLocallyInitiated()) {
                    http2Stream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    this.this$0.removeStream$okhttp(http2Stream.getId());
                }
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void windowUpdate(int i, long j) {
            if (i == 0) {
                synchronized (this.this$0) {
                    Http2Connection http2Connection = this.this$0;
                    http2Connection.writeBytesMaximum = http2Connection.getWriteBytesMaximum() + j;
                    Http2Connection http2Connection2 = this.this$0;
                    if (http2Connection2 != null) {
                        http2Connection2.notifyAll();
                        Unit unit = Unit.INSTANCE;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                }
                return;
            }
            Http2Stream stream = this.this$0.getStream(i);
            if (stream != null) {
                synchronized (stream) {
                    stream.addBytesToWriteWindow(j);
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void pushPromise(int i, int i2, List<Header> requestHeaders) {
            Intrinsics.checkParameterIsNotNull(requestHeaders, "requestHeaders");
            this.this$0.pushRequestLater$okhttp(i2, requestHeaders);
        }
    }

    public final void pushRequestLater$okhttp(final int i, final List<Header> requestHeaders) {
        Intrinsics.checkParameterIsNotNull(requestHeaders, "requestHeaders");
        synchronized (this) {
            if (this.currentPushRequests.contains(Integer.valueOf(i))) {
                writeSynResetLater$okhttp(i, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(Integer.valueOf(i));
            if (this.isShutdown) {
                return;
            }
            final String str = "OkHttp " + this.connectionName + " Push Request[" + i + ']';
            try {
                this.pushExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$pushRequestLater$$inlined$tryExecute$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PushObserver pushObserver;
                        Set set;
                        String str2 = str;
                        Thread currentThread = Thread.currentThread();
                        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                        String name = currentThread.getName();
                        currentThread.setName(str2);
                        try {
                            pushObserver = this.pushObserver;
                            if (pushObserver.onRequest(i, requestHeaders)) {
                                try {
                                    this.getWriter().rstStream(i, ErrorCode.CANCEL);
                                    synchronized (this) {
                                        set = this.currentPushRequests;
                                        set.remove(Integer.valueOf(i));
                                    }
                                } catch (IOException unused) {
                                }
                            }
                        } finally {
                            currentThread.setName(name);
                        }
                    }
                });
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    public final void pushHeadersLater$okhttp(final int i, final List<Header> requestHeaders, final boolean z) {
        Intrinsics.checkParameterIsNotNull(requestHeaders, "requestHeaders");
        if (this.isShutdown) {
            return;
        }
        final String str = "OkHttp " + this.connectionName + " Push Headers[" + i + ']';
        try {
            this.pushExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$pushHeadersLater$$inlined$tryExecute$1
                @Override // java.lang.Runnable
                public final void run() {
                    PushObserver pushObserver;
                    Set set;
                    String str2 = str;
                    Thread currentThread = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                    String name = currentThread.getName();
                    currentThread.setName(str2);
                    try {
                        pushObserver = this.pushObserver;
                        boolean onHeaders = pushObserver.onHeaders(i, requestHeaders, z);
                        if (onHeaders) {
                            try {
                                this.getWriter().rstStream(i, ErrorCode.CANCEL);
                            } catch (IOException unused) {
                            }
                        }
                        if (onHeaders || z) {
                            synchronized (this) {
                                set = this.currentPushRequests;
                                set.remove(Integer.valueOf(i));
                            }
                        }
                    } finally {
                        currentThread.setName(name);
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    public final void pushDataLater$okhttp(final int i, BufferedSource source, final int i2, final boolean z) throws IOException {
        Intrinsics.checkParameterIsNotNull(source, "source");
        final Buffer buffer = new Buffer();
        long j = i2;
        source.require(j);
        source.read(buffer, j);
        if (this.isShutdown) {
            return;
        }
        final String str = "OkHttp " + this.connectionName + " Push Data[" + i + ']';
        this.pushExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$pushDataLater$$inlined$execute$1
            @Override // java.lang.Runnable
            public final void run() {
                PushObserver pushObserver;
                Set set;
                String str2 = str;
                Thread currentThread = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                String name = currentThread.getName();
                currentThread.setName(str2);
                try {
                    pushObserver = this.pushObserver;
                    boolean onData = pushObserver.onData(i, buffer, i2, z);
                    if (onData) {
                        this.getWriter().rstStream(i, ErrorCode.CANCEL);
                    }
                    if (onData || z) {
                        synchronized (this) {
                            set = this.currentPushRequests;
                            set.remove(Integer.valueOf(i));
                        }
                    }
                } catch (IOException unused) {
                } catch (Throwable th) {
                    currentThread.setName(name);
                    throw th;
                }
                currentThread.setName(name);
            }
        });
    }

    public final void pushResetLater$okhttp(final int i, final ErrorCode errorCode) {
        Intrinsics.checkParameterIsNotNull(errorCode, "errorCode");
        if (this.isShutdown) {
            return;
        }
        final String str = "OkHttp " + this.connectionName + " Push Reset[" + i + ']';
        this.pushExecutor.execute(new Runnable() { // from class: okhttp3.internal.http2.Http2Connection$pushResetLater$$inlined$execute$1
            @Override // java.lang.Runnable
            public final void run() {
                PushObserver pushObserver;
                Set set;
                String str2 = str;
                Thread currentThread = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
                String name = currentThread.getName();
                currentThread.setName(str2);
                try {
                    pushObserver = this.pushObserver;
                    pushObserver.onReset(i, errorCode);
                    synchronized (this) {
                        set = this.currentPushRequests;
                        set.remove(Integer.valueOf(i));
                    }
                } finally {
                    currentThread.setName(name);
                }
            }
        });
    }

    /* compiled from: Http2Connection.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH&¨\u0006\u000b"}, d2 = {"Lokhttp3/internal/http2/Http2Connection$Listener;", "", "()V", "onSettings", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public static abstract class Listener {
        public static final Companion Companion = new Companion(null);
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() { // from class: okhttp3.internal.http2.Http2Connection$Listener$Companion$REFUSE_INCOMING_STREAMS$1
            @Override // okhttp3.internal.http2.Http2Connection.Listener
            public void onStream(Http2Stream stream) throws IOException {
                Intrinsics.checkParameterIsNotNull(stream, "stream");
                stream.close(ErrorCode.REFUSED_STREAM, null);
            }
        };

        public void onSettings(Http2Connection connection) {
            Intrinsics.checkParameterIsNotNull(connection, "connection");
        }

        public abstract void onStream(Http2Stream http2Stream) throws IOException;

        /* compiled from: Http2Connection.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lokhttp3/internal/http2/Http2Connection$Listener$Companion;", "", "()V", "REFUSE_INCOMING_STREAMS", "Lokhttp3/internal/http2/Http2Connection$Listener;", "okhttp"}, k = 1, mv = {1, 1, 15})
        /* loaded from: classes2.dex */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }

    /* compiled from: Http2Connection.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lokhttp3/internal/http2/Http2Connection$Companion;", "", "()V", "OKHTTP_CLIENT_WINDOW_SIZE", "", "listenerExecutor", "Ljava/util/concurrent/ThreadPoolExecutor;", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
