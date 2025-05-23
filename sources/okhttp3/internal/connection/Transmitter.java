package okhttp3.internal.connection;

import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.polidea.rxandroidble2.ClientComponent;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;
/* compiled from: Transmitter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u007f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001 \u0018\u00002\u00020\u0001:\u0001FB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010#\u001a\u00020$2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010%\u001a\u00020$J\u0006\u0010&\u001a\u00020\tJ\u0006\u0010'\u001a\u00020$J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u0006\u0010,\u001a\u00020$J;\u0010-\u001a\u0002H.\"\n\b\u0000\u0010.*\u0004\u0018\u00010/2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\t2\u0006\u00102\u001a\u0002H.H\u0000¢\u0006\u0004\b3\u00104J\u0006\u00105\u001a\u00020\tJ)\u00106\u001a\u0002H.\"\n\b\u0000\u0010.*\u0004\u0018\u00010/2\u0006\u00102\u001a\u0002H.2\u0006\u00107\u001a\u00020\tH\u0002¢\u0006\u0002\u00108J\u001d\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\tH\u0000¢\u0006\u0002\b=J\u0012\u0010\u001c\u001a\u0004\u0018\u00010/2\b\u00102\u001a\u0004\u0018\u00010/J\u000e\u0010>\u001a\u00020$2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010?\u001a\u0004\u0018\u00010@J\u0006\u0010\u001f\u001a\u00020AJ\u0006\u0010\"\u001a\u00020$J\u0006\u0010B\u001a\u00020$J!\u0010C\u001a\u0002H.\"\n\b\u0000\u0010.*\u0004\u0018\u00010/2\u0006\u0010D\u001a\u0002H.H\u0002¢\u0006\u0002\u0010ER\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0004\n\u0002\u0010!R\u000e\u0010\"\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lokhttp3/internal/connection/Transmitter;", "", "client", "Lokhttp3/OkHttpClient;", "call", "Lokhttp3/Call;", "(Lokhttp3/OkHttpClient;Lokhttp3/Call;)V", "callStackTrace", "canceled", "", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "setConnection", "(Lokhttp3/internal/connection/RealConnection;)V", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "eventListener", "Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "exchangeRequestDone", "exchangeResponseDone", "isCanceled", "()Z", "noMoreExchanges", "request", "Lokhttp3/Request;", ClientComponent.NamedSchedulers.TIMEOUT, "okhttp3/internal/connection/Transmitter$timeout$1", "Lokhttp3/internal/connection/Transmitter$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callStart", "canRetry", "cancel", "createAddress", "Lokhttp3/Address;", FileDownloadModel.URL, "Lokhttp3/HttpUrl;", "exchangeDoneDueToException", "exchangeMessageDone", "E", "Ljava/io/IOException;", "requestDone", "responseDone", "e", "exchangeMessageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "hasExchange", "maybeReleaseConnection", "force", "(Ljava/io/IOException;Z)Ljava/io/IOException;", "newExchange", "chain", "Lokhttp3/Interceptor$Chain;", "doExtensiveHealthChecks", "newExchange$okhttp", "prepareToConnect", "releaseConnectionNoEvents", "Ljava/net/Socket;", "Lokio/Timeout;", "timeoutEnter", "timeoutExit", "cause", "(Ljava/io/IOException;)Ljava/io/IOException;", "TransmitterReference", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class Transmitter {
    private final Call call;
    private Object callStackTrace;
    private boolean canceled;
    private final OkHttpClient client;
    private RealConnection connection;
    private final RealConnectionPool connectionPool;
    private final EventListener eventListener;
    private Exchange exchange;
    private ExchangeFinder exchangeFinder;
    private boolean exchangeRequestDone;
    private boolean exchangeResponseDone;
    private boolean noMoreExchanges;
    private Request request;
    private final Transmitter$timeout$1 timeout;
    private boolean timeoutEarlyExit;

    /* JADX WARN: Type inference failed for: r4v2, types: [okhttp3.internal.connection.Transmitter$timeout$1] */
    public Transmitter(OkHttpClient client, Call call) {
        Intrinsics.checkParameterIsNotNull(client, "client");
        Intrinsics.checkParameterIsNotNull(call, "call");
        this.client = client;
        this.call = call;
        this.connectionPool = client.connectionPool().getDelegate$okhttp();
        this.eventListener = client.eventListenerFactory().create(call);
        ?? r4 = new AsyncTimeout() { // from class: okhttp3.internal.connection.Transmitter$timeout$1
            @Override // okio.AsyncTimeout
            protected void timedOut() {
                Transmitter.this.cancel();
            }
        };
        r4.timeout(client.callTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.timeout = r4;
    }

    public final RealConnection getConnection() {
        return this.connection;
    }

    public final void setConnection(RealConnection realConnection) {
        this.connection = realConnection;
    }

    public final boolean isCanceled() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.canceled;
        }
        return z;
    }

    public final Timeout timeout() {
        return this.timeout;
    }

    public final void timeoutEnter() {
        enter();
    }

    public final void timeoutEarlyExit() {
        if (!(!this.timeoutEarlyExit)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        this.timeoutEarlyExit = true;
        exit();
    }

    private final <E extends IOException> E timeoutExit(E e) {
        if (!this.timeoutEarlyExit && exit()) {
            InterruptedIOException interruptedIOException = new InterruptedIOException(ClientComponent.NamedSchedulers.TIMEOUT);
            if (e != null) {
                interruptedIOException.initCause(e);
            }
            return interruptedIOException;
        }
        return e;
    }

    public final void callStart() {
        this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
        this.eventListener.callStart(this.call);
    }

    public final void prepareToConnect(Request request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        Request request2 = this.request;
        if (request2 != null) {
            if (request2 == null) {
                Intrinsics.throwNpe();
            }
            if (Util.canReuseConnectionFor(request2.url(), request.url())) {
                ExchangeFinder exchangeFinder = this.exchangeFinder;
                if (exchangeFinder == null) {
                    Intrinsics.throwNpe();
                }
                if (exchangeFinder.hasRouteToTry()) {
                    return;
                }
            }
            if (!(this.exchange == null)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            if (this.exchangeFinder != null) {
                maybeReleaseConnection(null, true);
                this.exchangeFinder = null;
            }
        }
        this.request = request;
        this.exchangeFinder = new ExchangeFinder(this, this.connectionPool, createAddress(request.url()), this.call, this.eventListener);
    }

    private final Address createAddress(HttpUrl httpUrl) {
        SSLSocketFactory sSLSocketFactory;
        HostnameVerifier hostnameVerifier;
        CertificatePinner certificatePinner;
        if (httpUrl.isHttps()) {
            sSLSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        } else {
            sSLSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    public final Exchange newExchange$okhttp(Interceptor.Chain chain, boolean z) {
        Intrinsics.checkParameterIsNotNull(chain, "chain");
        synchronized (this.connectionPool) {
            boolean z2 = true;
            if (!(!this.noMoreExchanges)) {
                throw new IllegalStateException("released".toString());
            }
            if (this.exchange != null) {
                z2 = false;
            }
            if (!z2) {
                throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        ExchangeFinder exchangeFinder = this.exchangeFinder;
        if (exchangeFinder == null) {
            Intrinsics.throwNpe();
        }
        ExchangeCodec find = exchangeFinder.find(this.client, chain, z);
        Call call = this.call;
        EventListener eventListener = this.eventListener;
        ExchangeFinder exchangeFinder2 = this.exchangeFinder;
        if (exchangeFinder2 == null) {
            Intrinsics.throwNpe();
        }
        Exchange exchange = new Exchange(this, call, eventListener, exchangeFinder2, find);
        synchronized (this.connectionPool) {
            this.exchange = exchange;
            this.exchangeRequestDone = false;
            this.exchangeResponseDone = false;
        }
        return exchange;
    }

    public final void acquireConnectionNoEvents(RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        Thread.holdsLock(this.connectionPool);
        if (!(this.connection == null)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        this.connection = connection;
        connection.getTransmitters().add(new TransmitterReference(this, this.callStackTrace));
    }

    public final Socket releaseConnectionNoEvents() {
        Thread.holdsLock(this.connectionPool);
        RealConnection realConnection = this.connection;
        if (realConnection == null) {
            Intrinsics.throwNpe();
        }
        Iterator<Reference<Transmitter>> it = realConnection.getTransmitters().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (Intrinsics.areEqual(it.next().get(), this)) {
                break;
            } else {
                i++;
            }
        }
        if (!(i != -1)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        RealConnection realConnection2 = this.connection;
        if (realConnection2 == null) {
            Intrinsics.throwNpe();
        }
        realConnection2.getTransmitters().remove(i);
        this.connection = null;
        if (realConnection2.getTransmitters().isEmpty()) {
            realConnection2.setIdleAtNanos$okhttp(System.nanoTime());
            if (this.connectionPool.connectionBecameIdle(realConnection2)) {
                return realConnection2.socket();
            }
        }
        return null;
    }

    public final void exchangeDoneDueToException() {
        synchronized (this.connectionPool) {
            if (!(!this.noMoreExchanges)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            Exchange exchange = null;
            this.exchange = null;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final <E extends IOException> E exchangeMessageDone$okhttp(Exchange exchange, boolean z, boolean z2, E e) {
        boolean z3;
        Intrinsics.checkParameterIsNotNull(exchange, "exchange");
        synchronized (this.connectionPool) {
            boolean z4 = true;
            if (!Intrinsics.areEqual(exchange, this.exchange)) {
                return e;
            }
            if (z) {
                z3 = !this.exchangeRequestDone;
                this.exchangeRequestDone = true;
            } else {
                z3 = false;
            }
            if (z2) {
                if (!this.exchangeResponseDone) {
                    z3 = true;
                }
                this.exchangeResponseDone = true;
            }
            if (this.exchangeRequestDone && this.exchangeResponseDone && z3) {
                Exchange exchange2 = this.exchange;
                if (exchange2 == null) {
                    Intrinsics.throwNpe();
                }
                RealConnection connection = exchange2.connection();
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                connection.setSuccessCount$okhttp(connection.getSuccessCount$okhttp() + 1);
                Exchange exchange3 = null;
                this.exchange = null;
            } else {
                z4 = false;
            }
            Unit unit = Unit.INSTANCE;
            return z4 ? (E) maybeReleaseConnection(e, false) : e;
        }
    }

    public final IOException noMoreExchanges(IOException iOException) {
        synchronized (this.connectionPool) {
            this.noMoreExchanges = true;
            Unit unit = Unit.INSTANCE;
        }
        return maybeReleaseConnection(iOException, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0019 A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:6:0x000c, B:14:0x0019, B:16:0x0024, B:19:0x002a, B:21:0x002e, B:23:0x0034, B:25:0x0038, B:26:0x003d, B:28:0x0041, B:32:0x0048, B:53:0x0086, B:54:0x0093), top: B:57:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0086 A[Catch: all -> 0x0013, TRY_ENTER, TryCatch #0 {all -> 0x0013, blocks: (B:6:0x000c, B:14:0x0019, B:16:0x0024, B:19:0x002a, B:21:0x002e, B:23:0x0034, B:25:0x0038, B:26:0x003d, B:28:0x0041, B:32:0x0048, B:53:0x0086, B:54:0x0093), top: B:57:0x000c }] */
    /* JADX WARN: Type inference failed for: r4v4, types: [T, okhttp3.Connection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final <E extends java.io.IOException> E maybeReleaseConnection(E r7, boolean r8) {
        /*
            r6 = this;
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            okhttp3.internal.connection.RealConnectionPool r1 = r6.connectionPool
            monitor-enter(r1)
            r2 = 0
            r3 = 1
            if (r8 == 0) goto L16
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L11
            goto L16
        L11:
            r4 = 0
            goto L17
        L13:
            r7 = move-exception
            goto L94
        L16:
            r4 = 1
        L17:
            if (r4 == 0) goto L86
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: java.lang.Throwable -> L13
            okhttp3.Connection r4 = (okhttp3.Connection) r4     // Catch: java.lang.Throwable -> L13
            r0.element = r4     // Catch: java.lang.Throwable -> L13
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: java.lang.Throwable -> L13
            r5 = 0
            if (r4 == 0) goto L33
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L33
            if (r8 != 0) goto L2e
            boolean r8 = r6.noMoreExchanges     // Catch: java.lang.Throwable -> L13
            if (r8 == 0) goto L33
        L2e:
            java.net.Socket r8 = r6.releaseConnectionNoEvents()     // Catch: java.lang.Throwable -> L13
            goto L34
        L33:
            r8 = r5
        L34:
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: java.lang.Throwable -> L13
            if (r4 == 0) goto L3d
            r4 = r5
            okhttp3.Connection r4 = (okhttp3.Connection) r4     // Catch: java.lang.Throwable -> L13
            r0.element = r5     // Catch: java.lang.Throwable -> L13
        L3d:
            boolean r4 = r6.noMoreExchanges     // Catch: java.lang.Throwable -> L13
            if (r4 == 0) goto L47
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L47
            r4 = 1
            goto L48
        L47:
            r4 = 0
        L48:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L13
            monitor-exit(r1)
            if (r8 == 0) goto L50
            okhttp3.internal.Util.closeQuietly(r8)
        L50:
            T r8 = r0.element
            okhttp3.Connection r8 = (okhttp3.Connection) r8
            if (r8 == 0) goto L66
            okhttp3.EventListener r8 = r6.eventListener
            okhttp3.Call r1 = r6.call
            T r0 = r0.element
            okhttp3.Connection r0 = (okhttp3.Connection) r0
            if (r0 != 0) goto L63
            kotlin.jvm.internal.Intrinsics.throwNpe()
        L63:
            r8.connectionReleased(r1, r0)
        L66:
            if (r4 == 0) goto L85
            if (r7 == 0) goto L6b
            r2 = 1
        L6b:
            java.io.IOException r7 = r6.timeoutExit(r7)
            if (r2 == 0) goto L7e
            okhttp3.EventListener r8 = r6.eventListener
            okhttp3.Call r0 = r6.call
            if (r7 != 0) goto L7a
            kotlin.jvm.internal.Intrinsics.throwNpe()
        L7a:
            r8.callFailed(r0, r7)
            goto L85
        L7e:
            okhttp3.EventListener r8 = r6.eventListener
            okhttp3.Call r0 = r6.call
            r8.callEnd(r0)
        L85:
            return r7
        L86:
            java.lang.String r7 = "cannot release connection while it is in use"
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L13
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L13
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L13
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch: java.lang.Throwable -> L13
            throw r8     // Catch: java.lang.Throwable -> L13
        L94:
            monitor-exit(r1)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.Transmitter.maybeReleaseConnection(java.io.IOException, boolean):java.io.IOException");
    }

    public final boolean canRetry() {
        ExchangeFinder exchangeFinder = this.exchangeFinder;
        if (exchangeFinder == null) {
            Intrinsics.throwNpe();
        }
        if (exchangeFinder.hasStreamFailure()) {
            ExchangeFinder exchangeFinder2 = this.exchangeFinder;
            if (exchangeFinder2 == null) {
                Intrinsics.throwNpe();
            }
            if (exchangeFinder2.hasRouteToTry()) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasExchange() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.exchange != null;
        }
        return z;
    }

    public final void cancel() {
        Exchange exchange;
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            this.canceled = true;
            exchange = this.exchange;
            ExchangeFinder exchangeFinder = this.exchangeFinder;
            if (exchangeFinder == null || (realConnection = exchangeFinder.connectingConnection()) == null) {
                realConnection = this.connection;
            }
            Unit unit = Unit.INSTANCE;
        }
        if (exchange != null) {
            exchange.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    /* compiled from: Transmitter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lokhttp3/internal/connection/Transmitter$TransmitterReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/Transmitter;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/Transmitter;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public static final class TransmitterReference extends WeakReference<Transmitter> {
        private final Object callStackTrace;

        public final Object getCallStackTrace() {
            return this.callStackTrace;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TransmitterReference(Transmitter referent, Object obj) {
            super(referent);
            Intrinsics.checkParameterIsNotNull(referent, "referent");
            this.callStackTrace = obj;
        }
    }
}
