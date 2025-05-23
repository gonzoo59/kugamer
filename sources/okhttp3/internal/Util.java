package okhttp3.internal;

import com.google.common.net.HttpHeaders;
import com.polidea.rxandroidble2.ClientComponent;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Source;
/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0080\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0013\u001a'\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001e0\u001d\"\u00020\u001e¢\u0006\u0002\u0010\u001f\u001a\u0017\u0010 \u001a\u00020\u00172\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0086\b\u001a-\u0010#\u001a\b\u0012\u0004\u0012\u0002H%0$\"\u0004\b\u0000\u0010%2\u0012\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u0002H%0\u001d\"\u0002H%H\u0007¢\u0006\u0002\u0010'\u001a1\u0010(\u001a\u0004\u0018\u0001H%\"\u0004\b\u0000\u0010%2\u0006\u0010)\u001a\u00020\u001e2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H%0+2\u0006\u0010,\u001a\u00020\u0011¢\u0006\u0002\u0010-\u001a\u0016\u0010.\u001a\u00020/2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u00100\u001a\u000201\u001a\u001f\u00102\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0086\b\u001a\u0015\u00103\u001a\u00020\u000f*\u0002042\u0006\u00105\u001a\u00020\u000fH\u0086\u0004\u001a\u0015\u00103\u001a\u00020\u0013*\u00020\u000f2\u0006\u00105\u001a\u00020\u0013H\u0086\u0004\u001a\u0015\u00103\u001a\u00020\u000f*\u0002062\u0006\u00105\u001a\u00020\u000fH\u0086\u0004\u001a\n\u00107\u001a\u000208*\u000209\u001a\n\u0010:\u001a\u000201*\u00020\u0011\u001a\u0012\u0010;\u001a\u000201*\u00020<2\u0006\u0010=\u001a\u00020<\u001a\n\u0010>\u001a\u00020\u0017*\u00020?\u001a\n\u0010>\u001a\u00020\u0017*\u00020@\u001a\n\u0010>\u001a\u00020\u0017*\u00020A\u001a#\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00110\u001d*\b\u0012\u0004\u0012\u00020\u00110\u001d2\u0006\u0010C\u001a\u00020\u0011¢\u0006\u0002\u0010D\u001a\n\u0010E\u001a\u00020\u0011*\u00020A\u001a&\u0010F\u001a\u00020\u000f*\u00020\u00112\u0006\u0010G\u001a\u00020H2\b\b\u0002\u0010I\u001a\u00020\u000f2\b\b\u0002\u0010J\u001a\u00020\u000f\u001a&\u0010F\u001a\u00020\u000f*\u00020\u00112\u0006\u0010K\u001a\u00020\u00112\b\b\u0002\u0010I\u001a\u00020\u000f2\b\b\u0002\u0010J\u001a\u00020\u000f\u001a\u001a\u0010L\u001a\u000201*\u00020M2\u0006\u0010N\u001a\u00020\u000f2\u0006\u0010O\u001a\u00020\u0015\u001a%\u0010P\u001a\u00020\u0017*\u00020Q2\u0006\u0010\u0010\u001a\u00020\u00112\u000e\b\u0004\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0086\b\u001a5\u0010R\u001a\u000201*\b\u0012\u0004\u0012\u00020\u00110\u001d2\u000e\u0010=\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u001d2\u000e\u0010S\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110T¢\u0006\u0002\u0010U\u001a\n\u0010V\u001a\u00020\u0013*\u00020W\u001a+\u0010X\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00110\u001d2\u0006\u0010C\u001a\u00020\u00112\f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00110T¢\u0006\u0002\u0010Y\u001a\n\u0010Z\u001a\u00020\u000f*\u00020\u0011\u001a\u001e\u0010[\u001a\u00020\u000f*\u00020\u00112\b\b\u0002\u0010I\u001a\u00020\u000f2\b\b\u0002\u0010J\u001a\u00020\u000f\u001a\u001e\u0010\\\u001a\u00020\u000f*\u00020\u00112\b\b\u0002\u0010I\u001a\u00020\u000f2\b\b\u0002\u0010J\u001a\u00020\u000f\u001a\u0014\u0010]\u001a\u00020\u000f*\u00020\u00112\b\b\u0002\u0010I\u001a\u00020\u000f\u001a9\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00110\u001d*\b\u0012\u0004\u0012\u00020\u00110\u001d2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00110\u001d2\u000e\u0010S\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110T¢\u0006\u0002\u0010_\u001a\u0012\u0010`\u001a\u00020\u0017*\u00020\u001e2\u0006\u0010a\u001a\u00020\u0013\u001a\r\u0010b\u001a\u00020\u0017*\u00020\u001eH\u0086\b\u001a\r\u0010c\u001a\u00020\u0017*\u00020\u001eH\u0086\b\u001a\n\u0010d\u001a\u00020\u000f*\u00020H\u001a\u0012\u0010e\u001a\u00020f*\u00020g2\u0006\u0010h\u001a\u00020f\u001a\n\u0010i\u001a\u00020\u000f*\u00020g\u001a\u0012\u0010j\u001a\u00020\u000f*\u00020k2\u0006\u0010l\u001a\u000204\u001a\u001a\u0010j\u001a\u000201*\u00020M2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010O\u001a\u00020\u0015\u001a\u0010\u0010m\u001a\b\u0012\u0004\u0012\u00020n0$*\u00020\u0003\u001a\u0010\u0010o\u001a\u00020\u0003*\b\u0012\u0004\u0012\u00020n0$\u001a\n\u0010p\u001a\u00020\u0011*\u00020\u000f\u001a\n\u0010p\u001a\u00020\u0011*\u00020\u0013\u001a\u0014\u0010q\u001a\u00020\u0011*\u00020<2\b\b\u0002\u0010r\u001a\u000201\u001a\u001c\u0010s\u001a\b\u0012\u0004\u0012\u0002H%0$\"\u0004\b\u0000\u0010%*\b\u0012\u0004\u0012\u0002H%0$\u001a.\u0010t\u001a\u000e\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hw0u\"\u0004\b\u0000\u0010v\"\u0004\b\u0001\u0010w*\u000e\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hw0u\u001a\u0012\u0010x\u001a\u00020\u0013*\u00020\u00112\u0006\u0010y\u001a\u00020\u0013\u001a\u0014\u0010z\u001a\u00020\u000f*\u0004\u0018\u00010\u00112\u0006\u0010y\u001a\u00020\u000f\u001a\u001e\u0010{\u001a\u00020\u0011*\u00020\u00112\b\b\u0002\u0010I\u001a\u00020\u000f2\b\b\u0002\u0010J\u001a\u00020\u000f\u001a%\u0010|\u001a\u00020\u0017*\u00020Q2\u0006\u0010\u0010\u001a\u00020\u00112\u000e\b\u0004\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\"H\u0086\b\u001a\r\u0010}\u001a\u00020\u0017*\u00020\u001eH\u0086\b\u001a\u001c\u0010~\u001a\u00020\u0017*\u00020\u001e2\u0006\u0010N\u001a\u00020\u00132\b\b\u0002\u0010a\u001a\u00020\u000f\u001a\u0014\u0010\u007f\u001a\u00020\u0017*\u00030\u0080\u00012\u0007\u0010\u0081\u0001\u001a\u00020\u000f\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0082\u0001"}, d2 = {"EMPTY_BYTE_ARRAY", "", "EMPTY_HEADERS", "Lokhttp3/Headers;", "EMPTY_REQUEST", "Lokhttp3/RequestBody;", "EMPTY_RESPONSE", "Lokhttp3/ResponseBody;", "UNICODE_BOMS", "Lokio/Options;", "UTC", "Ljava/util/TimeZone;", "VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "checkDuration", "", "name", "", "duration", "", "unit", "Ljava/util/concurrent/TimeUnit;", "checkOffsetAndCount", "", "arrayLength", "offset", "count", "format", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "immutableListOf", "", "T", "elements", "([Ljava/lang/Object;)Ljava/util/List;", "readFieldOrNull", "instance", "fieldType", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "daemon", "", "threadName", "and", "", "mask", "", "asFactory", "Lokhttp3/EventListener$Factory;", "Lokhttp3/EventListener;", "canParseAsIpAddress", "canReuseConnectionFor", "Lokhttp3/HttpUrl;", "other", "closeQuietly", "Ljava/io/Closeable;", "Ljava/net/ServerSocket;", "Ljava/net/Socket;", "concat", "value", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "connectionName", "delimiterOffset", "delimiter", "", "startIndex", "endIndex", "delimiters", "discard", "Lokio/Source;", ClientComponent.NamedSchedulers.TIMEOUT, "timeUnit", "execute", "Ljava/util/concurrent/Executor;", "hasIntersection", "comparator", "Ljava/util/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "headersContentLength", "Lokhttp3/Response;", "indexOf", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "indexOfControlOrNonAscii", "indexOfFirstNonAsciiWhitespace", "indexOfLastNonAsciiWhitespace", "indexOfNonWhitespace", "intersect", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "lockAndWaitNanos", "nanos", "notify", "notifyAll", "parseHexDigit", "readBomAsCharset", "Ljava/nio/charset/Charset;", "Lokio/BufferedSource;", "default", "readMedium", "skipAll", "Lokio/Buffer;", "b", "toHeaderList", "Lokhttp3/internal/http2/Header;", "toHeaders", "toHexString", "toHostHeader", "includeDefaultPort", "toImmutableList", "toImmutableMap", "", "K", "V", "toLongOrDefault", "defaultValue", "toNonNegativeInt", "trimSubstring", "tryExecute", "wait", "waitMillis", "writeMedium", "Lokio/BufferedSink;", "medium", "okhttp"}, k = 2, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final RequestBody EMPTY_REQUEST;
    public static final ResponseBody EMPTY_RESPONSE;
    public static final TimeZone UTC;
    private static final Regex VERIFY_AS_IP_ADDRESS;
    public static final Headers EMPTY_HEADERS = Headers.Companion.of(new String[0]);
    private static final Options UNICODE_BOMS = Options.Companion.of(ByteString.Companion.decodeHex("efbbbf"), ByteString.Companion.decodeHex("feff"), ByteString.Companion.decodeHex("fffe"), ByteString.Companion.decodeHex("0000ffff"), ByteString.Companion.decodeHex("ffff0000"));

    public static final int and(byte b, int i) {
        return b & i;
    }

    public static final int and(short s, int i) {
        return s & i;
    }

    public static final long and(int i, long j) {
        return i & j;
    }

    public static final int parseHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        char c2 = 'a';
        if ('a' > c || 'f' < c) {
            c2 = 'A';
            if ('A' > c || 'F' < c) {
                return -1;
            }
        }
        return (c - c2) + 10;
    }

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        EMPTY_RESPONSE = ResponseBody.Companion.create$default(ResponseBody.Companion, bArr, (MediaType) null, 1, (Object) null);
        EMPTY_REQUEST = RequestBody.Companion.create$default(RequestBody.Companion, bArr, (MediaType) null, 0, 0, 7, (Object) null);
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        if (timeZone == null) {
            Intrinsics.throwNpe();
        }
        UTC = timeZone;
        VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    }

    public static final void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static final ThreadFactory threadFactory(final String name, final boolean z) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new ThreadFactory() { // from class: okhttp3.internal.Util$threadFactory$1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, name);
                thread.setDaemon(z);
                return thread;
            }
        };
    }

    public static final String[] intersect(String[] intersect, String[] other, Comparator<? super String> comparator) {
        Intrinsics.checkParameterIsNotNull(intersect, "$this$intersect");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        ArrayList arrayList = new ArrayList();
        for (String str : intersect) {
            int length = other.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (comparator.compare(str, other[i]) == 0) {
                    arrayList.add(str);
                    break;
                } else {
                    i++;
                }
            }
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final boolean hasIntersection(String[] hasIntersection, String[] strArr, Comparator<? super String> comparator) {
        Intrinsics.checkParameterIsNotNull(hasIntersection, "$this$hasIntersection");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if (!(hasIntersection.length == 0) && strArr != null) {
            if (!(strArr.length == 0)) {
                for (String str : hasIntersection) {
                    for (String str2 : strArr) {
                        if (comparator.compare(str, str2) == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static /* synthetic */ String toHostHeader$default(HttpUrl httpUrl, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toHostHeader(httpUrl, z);
    }

    public static final String toHostHeader(HttpUrl toHostHeader, boolean z) {
        String host;
        Intrinsics.checkParameterIsNotNull(toHostHeader, "$this$toHostHeader");
        if (StringsKt.contains$default((CharSequence) toHostHeader.host(), (CharSequence) ":", false, 2, (Object) null)) {
            host = '[' + toHostHeader.host() + ']';
        } else {
            host = toHostHeader.host();
        }
        if (z || toHostHeader.port() != HttpUrl.Companion.defaultPort(toHostHeader.scheme())) {
            return host + ':' + toHostHeader.port();
        }
        return host;
    }

    public static final String[] concat(String[] concat, String value) {
        Intrinsics.checkParameterIsNotNull(concat, "$this$concat");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Object[] copyOf = Arrays.copyOf(concat, concat.length + 1);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        String[] strArr = (String[]) copyOf;
        strArr[ArraysKt.getLastIndex(strArr)] = value;
        if (strArr != null) {
            return strArr;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
    }

    public static /* synthetic */ int indexOfFirstNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfFirstNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfFirstNonAsciiWhitespace(String indexOfFirstNonAsciiWhitespace, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(indexOfFirstNonAsciiWhitespace, "$this$indexOfFirstNonAsciiWhitespace");
        while (i < i2) {
            char charAt = indexOfFirstNonAsciiWhitespace.charAt(i);
            if (charAt != '\t' && charAt != '\n' && charAt != '\f' && charAt != '\r' && charAt != ' ') {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int indexOfLastNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfLastNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfLastNonAsciiWhitespace(String indexOfLastNonAsciiWhitespace, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(indexOfLastNonAsciiWhitespace, "$this$indexOfLastNonAsciiWhitespace");
        int i3 = i2 - 1;
        if (i3 >= i) {
            while (true) {
                char charAt = indexOfLastNonAsciiWhitespace.charAt(i3);
                if (charAt != '\t' && charAt != '\n' && charAt != '\f' && charAt != '\r' && charAt != ' ') {
                    return i3 + 1;
                }
                if (i3 == i) {
                    break;
                }
                i3--;
            }
        }
        return i;
    }

    public static /* synthetic */ String trimSubstring$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return trimSubstring(str, i, i2);
    }

    public static final String trimSubstring(String trimSubstring, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(trimSubstring, "$this$trimSubstring");
        int indexOfFirstNonAsciiWhitespace = indexOfFirstNonAsciiWhitespace(trimSubstring, i, i2);
        String substring = trimSubstring.substring(indexOfFirstNonAsciiWhitespace, indexOfLastNonAsciiWhitespace(trimSubstring, indexOfFirstNonAsciiWhitespace, i2));
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, String str2, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, str2, i, i2);
    }

    public static final int delimiterOffset(String delimiterOffset, String delimiters, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(delimiterOffset, "$this$delimiterOffset");
        Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
        while (i < i2) {
            if (StringsKt.contains$default((CharSequence) delimiters, delimiterOffset.charAt(i), false, 2, (Object) null)) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, c, i, i2);
    }

    public static final int delimiterOffset(String delimiterOffset, char c, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(delimiterOffset, "$this$delimiterOffset");
        while (i < i2) {
            if (delimiterOffset.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static final int indexOfControlOrNonAscii(String indexOfControlOrNonAscii) {
        Intrinsics.checkParameterIsNotNull(indexOfControlOrNonAscii, "$this$indexOfControlOrNonAscii");
        int length = indexOfControlOrNonAscii.length();
        for (int i = 0; i < length; i++) {
            char charAt = indexOfControlOrNonAscii.charAt(i);
            if (charAt <= 31 || charAt >= 127) {
                return i;
            }
        }
        return -1;
    }

    public static final boolean canParseAsIpAddress(String canParseAsIpAddress) {
        Intrinsics.checkParameterIsNotNull(canParseAsIpAddress, "$this$canParseAsIpAddress");
        return VERIFY_AS_IP_ADDRESS.matches(canParseAsIpAddress);
    }

    public static final String format(String format, Object... args) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        Intrinsics.checkParameterIsNotNull(args, "args");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.US;
        Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.US");
        Object[] copyOf = Arrays.copyOf(args, args.length);
        String format2 = String.format(locale, format, Arrays.copyOf(copyOf, copyOf.length));
        Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(locale, format, *args)");
        return format2;
    }

    public static final Charset readBomAsCharset(BufferedSource readBomAsCharset, Charset charset) throws IOException {
        Intrinsics.checkParameterIsNotNull(readBomAsCharset, "$this$readBomAsCharset");
        Intrinsics.checkParameterIsNotNull(charset, "default");
        int select = readBomAsCharset.select(UNICODE_BOMS);
        if (select != -1) {
            if (select == 0) {
                Charset UTF_8 = StandardCharsets.UTF_8;
                Intrinsics.checkExpressionValueIsNotNull(UTF_8, "UTF_8");
                return UTF_8;
            } else if (select == 1) {
                Charset UTF_16BE = StandardCharsets.UTF_16BE;
                Intrinsics.checkExpressionValueIsNotNull(UTF_16BE, "UTF_16BE");
                return UTF_16BE;
            } else if (select == 2) {
                Charset UTF_16LE = StandardCharsets.UTF_16LE;
                Intrinsics.checkExpressionValueIsNotNull(UTF_16LE, "UTF_16LE");
                return UTF_16LE;
            } else if (select != 3) {
                if (select == 4) {
                    return Charsets.INSTANCE.UTF32_LE();
                }
                throw new AssertionError();
            } else {
                return Charsets.INSTANCE.UTF32_BE();
            }
        }
        return charset;
    }

    public static final int checkDuration(String name, long j, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        boolean z = true;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (!(i >= 0)) {
            throw new IllegalStateException((name + " < 0").toString());
        }
        if (!(timeUnit != null)) {
            throw new IllegalStateException("unit == null".toString());
        }
        long millis = timeUnit.toMillis(j);
        if (!(millis <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException((name + " too large.").toString());
        }
        if (millis == 0 && i > 0) {
            z = false;
        }
        if (z) {
            return (int) millis;
        }
        throw new IllegalArgumentException((name + " too small.").toString());
    }

    public static final Headers toHeaders(List<Header> toHeaders) {
        Intrinsics.checkParameterIsNotNull(toHeaders, "$this$toHeaders");
        Headers.Builder builder = new Headers.Builder();
        for (Header header : toHeaders) {
            builder.addLenient$okhttp(header.component1().utf8(), header.component2().utf8());
        }
        return builder.build();
    }

    public static final List<Header> toHeaderList(Headers toHeaderList) {
        Intrinsics.checkParameterIsNotNull(toHeaderList, "$this$toHeaderList");
        IntRange until = RangesKt.until(0, toHeaderList.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(until, 10));
        Iterator<Integer> it = until.iterator();
        while (it.hasNext()) {
            int nextInt = ((IntIterator) it).nextInt();
            arrayList.add(new Header(toHeaderList.name(nextInt), toHeaderList.value(nextInt)));
        }
        return arrayList;
    }

    public static final boolean canReuseConnectionFor(HttpUrl canReuseConnectionFor, HttpUrl other) {
        Intrinsics.checkParameterIsNotNull(canReuseConnectionFor, "$this$canReuseConnectionFor");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Intrinsics.areEqual(canReuseConnectionFor.host(), other.host()) && canReuseConnectionFor.port() == other.port() && Intrinsics.areEqual(canReuseConnectionFor.scheme(), other.scheme());
    }

    public static final EventListener.Factory asFactory(final EventListener asFactory) {
        Intrinsics.checkParameterIsNotNull(asFactory, "$this$asFactory");
        return new EventListener.Factory() { // from class: okhttp3.internal.Util$asFactory$1
            @Override // okhttp3.EventListener.Factory
            public EventListener create(Call call) {
                Intrinsics.checkParameterIsNotNull(call, "call");
                return EventListener.this;
            }
        };
    }

    public static final void writeMedium(BufferedSink writeMedium, int i) throws IOException {
        Intrinsics.checkParameterIsNotNull(writeMedium, "$this$writeMedium");
        writeMedium.writeByte((i >>> 16) & 255);
        writeMedium.writeByte((i >>> 8) & 255);
        writeMedium.writeByte(i & 255);
    }

    public static final int readMedium(BufferedSource readMedium) throws IOException {
        Intrinsics.checkParameterIsNotNull(readMedium, "$this$readMedium");
        return and(readMedium.readByte(), 255) | (and(readMedium.readByte(), 255) << 16) | (and(readMedium.readByte(), 255) << 8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0051, code lost:
        if (r5 == kotlin.jvm.internal.LongCompanionObject.MAX_VALUE) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0053, code lost:
        r11.timeout().clearDeadline();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005b, code lost:
        r11.timeout().deadlineNanoTime(r0 + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:
        if (r5 != kotlin.jvm.internal.LongCompanionObject.MAX_VALUE) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0081, code lost:
        return r12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean skipAll(okio.Source r11, int r12, java.util.concurrent.TimeUnit r13) throws java.io.IOException {
        /*
            java.lang.String r0 = "$this$skipAll"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r11, r0)
            java.lang.String r0 = "timeUnit"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r13, r0)
            long r0 = java.lang.System.nanoTime()
            okio.Timeout r2 = r11.timeout()
            boolean r2 = r2.hasDeadline()
            r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r2 == 0) goto L27
            okio.Timeout r2 = r11.timeout()
            long r5 = r2.deadlineNanoTime()
            long r5 = r5 - r0
            goto L28
        L27:
            r5 = r3
        L28:
            okio.Timeout r2 = r11.timeout()
            long r7 = (long) r12
            long r12 = r13.toNanos(r7)
            long r12 = java.lang.Math.min(r5, r12)
            long r12 = r12 + r0
            r2.deadlineNanoTime(r12)
            okio.Buffer r12 = new okio.Buffer     // Catch: java.lang.Throwable -> L64 java.io.InterruptedIOException -> L7a
            r12.<init>()     // Catch: java.lang.Throwable -> L64 java.io.InterruptedIOException -> L7a
        L3e:
            r7 = 8192(0x2000, double:4.0474E-320)
            long r7 = r11.read(r12, r7)     // Catch: java.lang.Throwable -> L64 java.io.InterruptedIOException -> L7a
            r9 = -1
            int r13 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r13 == 0) goto L4e
            r12.clear()     // Catch: java.lang.Throwable -> L64 java.io.InterruptedIOException -> L7a
            goto L3e
        L4e:
            r12 = 1
            int r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r13 != 0) goto L5b
        L53:
            okio.Timeout r11 = r11.timeout()
            r11.clearDeadline()
            goto L81
        L5b:
            okio.Timeout r11 = r11.timeout()
            long r0 = r0 + r5
            r11.deadlineNanoTime(r0)
            goto L81
        L64:
            r12 = move-exception
            int r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r13 != 0) goto L71
            okio.Timeout r11 = r11.timeout()
            r11.clearDeadline()
            goto L79
        L71:
            okio.Timeout r11 = r11.timeout()
            long r0 = r0 + r5
            r11.deadlineNanoTime(r0)
        L79:
            throw r12
        L7a:
            r12 = 0
            int r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r13 != 0) goto L5b
            goto L53
        L81:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.Util.skipAll(okio.Source, int, java.util.concurrent.TimeUnit):boolean");
    }

    public static final boolean discard(Source discard, int i, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(discard, "$this$discard");
        Intrinsics.checkParameterIsNotNull(timeUnit, "timeUnit");
        try {
            return skipAll(discard, i, timeUnit);
        } catch (IOException unused) {
            return false;
        }
    }

    public static final String connectionName(Socket connectionName) {
        Intrinsics.checkParameterIsNotNull(connectionName, "$this$connectionName");
        SocketAddress remoteSocketAddress = connectionName.getRemoteSocketAddress();
        if (remoteSocketAddress instanceof InetSocketAddress) {
            String hostName = ((InetSocketAddress) remoteSocketAddress).getHostName();
            Intrinsics.checkExpressionValueIsNotNull(hostName, "address.hostName");
            return hostName;
        }
        return remoteSocketAddress.toString();
    }

    public static final void ignoreIoExceptions(Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        try {
            block.invoke();
        } catch (IOException unused) {
        }
    }

    public static final void threadName(String name, Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
        String name2 = currentThread.getName();
        currentThread.setName(name);
        try {
            block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            currentThread.setName(name2);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void execute(Executor execute, String name, Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(execute, "$this$execute");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(block, "block");
        execute.execute(new Util$execute$1(name, block));
    }

    public static final int skipAll(Buffer skipAll, byte b) {
        Intrinsics.checkParameterIsNotNull(skipAll, "$this$skipAll");
        int i = 0;
        while (!skipAll.exhausted() && skipAll.getByte(0L) == b) {
            i++;
            skipAll.readByte();
        }
        return i;
    }

    public static /* synthetic */ int indexOfNonWhitespace$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return indexOfNonWhitespace(str, i);
    }

    public static final int indexOfNonWhitespace(String indexOfNonWhitespace, int i) {
        Intrinsics.checkParameterIsNotNull(indexOfNonWhitespace, "$this$indexOfNonWhitespace");
        int length = indexOfNonWhitespace.length();
        while (i < length) {
            char charAt = indexOfNonWhitespace.charAt(i);
            if (charAt != ' ' && charAt != '\t') {
                return i;
            }
            i++;
        }
        return indexOfNonWhitespace.length();
    }

    public static final long headersContentLength(Response headersContentLength) {
        Intrinsics.checkParameterIsNotNull(headersContentLength, "$this$headersContentLength");
        String str = headersContentLength.headers().get(HttpHeaders.CONTENT_LENGTH);
        if (str != null) {
            return toLongOrDefault(str, -1L);
        }
        return -1L;
    }

    public static final long toLongOrDefault(String toLongOrDefault, long j) {
        Intrinsics.checkParameterIsNotNull(toLongOrDefault, "$this$toLongOrDefault");
        try {
            return Long.parseLong(toLongOrDefault);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static final int toNonNegativeInt(String str, int i) {
        if (str != null) {
            try {
                long parseLong = Long.parseLong(str);
                if (parseLong > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (parseLong < 0) {
                    return 0;
                }
                return (int) parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public static final <T> List<T> toImmutableList(List<? extends T> toImmutableList) {
        Intrinsics.checkParameterIsNotNull(toImmutableList, "$this$toImmutableList");
        List<T> unmodifiableList = Collections.unmodifiableList(CollectionsKt.toMutableList((Collection) toImmutableList));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableList, "Collections.unmodifiableList(toMutableList())");
        return unmodifiableList;
    }

    @SafeVarargs
    public static final <T> List<T> immutableListOf(T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Object[] objArr = (Object[]) elements.clone();
        List<T> unmodifiableList = Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(objArr, objArr.length)));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableList, "Collections.unmodifiable…sList(*elements.clone()))");
        return unmodifiableList;
    }

    public static final <K, V> Map<K, V> toImmutableMap(Map<K, ? extends V> toImmutableMap) {
        Intrinsics.checkParameterIsNotNull(toImmutableMap, "$this$toImmutableMap");
        if (toImmutableMap.isEmpty()) {
            return MapsKt.emptyMap();
        }
        Map<K, V> unmodifiableMap = Collections.unmodifiableMap(new LinkedHashMap(toImmutableMap));
        Intrinsics.checkExpressionValueIsNotNull(unmodifiableMap, "Collections.unmodifiableMap(LinkedHashMap(this))");
        return unmodifiableMap;
    }

    public static final void closeQuietly(Closeable closeQuietly) {
        Intrinsics.checkParameterIsNotNull(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
        }
    }

    public static final void closeQuietly(Socket closeQuietly) {
        Intrinsics.checkParameterIsNotNull(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception unused) {
        }
    }

    public static final void closeQuietly(ServerSocket closeQuietly) {
        Intrinsics.checkParameterIsNotNull(closeQuietly, "$this$closeQuietly");
        try {
            closeQuietly.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
        }
    }

    public static final String toHexString(long j) {
        String hexString = Long.toHexString(j);
        Intrinsics.checkExpressionValueIsNotNull(hexString, "java.lang.Long.toHexString(this)");
        return hexString;
    }

    public static final String toHexString(int i) {
        String hexString = Integer.toHexString(i);
        Intrinsics.checkExpressionValueIsNotNull(hexString, "Integer.toHexString(this)");
        return hexString;
    }

    public static final void lockAndWaitNanos(Object lockAndWaitNanos, long j) throws InterruptedException {
        Intrinsics.checkParameterIsNotNull(lockAndWaitNanos, "$this$lockAndWaitNanos");
        long j2 = j / 1000000;
        long j3 = j - (1000000 * j2);
        synchronized (lockAndWaitNanos) {
            waitMillis(lockAndWaitNanos, j2, (int) j3);
            Unit unit = Unit.INSTANCE;
        }
    }

    public static final void wait(Object wait) {
        Intrinsics.checkParameterIsNotNull(wait, "$this$wait");
        wait.wait();
    }

    public static /* synthetic */ void waitMillis$default(Object obj, long j, int i, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        waitMillis(obj, j, i);
    }

    public static final void waitMillis(Object waitMillis, long j, int i) {
        Intrinsics.checkParameterIsNotNull(waitMillis, "$this$waitMillis");
        if (j > 0 || i > 0) {
            waitMillis.wait(j, i);
        }
    }

    public static final void notify(Object notify) {
        Intrinsics.checkParameterIsNotNull(notify, "$this$notify");
        notify.notify();
    }

    public static final void notifyAll(Object notifyAll) {
        Intrinsics.checkParameterIsNotNull(notifyAll, "$this$notifyAll");
        notifyAll.notifyAll();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0039, code lost:
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> T readFieldOrNull(java.lang.Object r5, java.lang.Class<T> r6, java.lang.String r7) {
        /*
            java.lang.String r0 = "instance"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r5, r0)
            java.lang.String r0 = "fieldType"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            java.lang.String r0 = "fieldName"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            java.lang.Class r0 = r5.getClass()
        L13:
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r2 = 1
            r1 = r1 ^ r2
            r3 = 0
            if (r1 == 0) goto L44
            java.lang.reflect.Field r1 = r0.getDeclaredField(r7)     // Catch: java.lang.NoSuchFieldException -> L3a
            java.lang.String r4 = "field"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r1, r4)     // Catch: java.lang.NoSuchFieldException -> L3a
            r1.setAccessible(r2)     // Catch: java.lang.NoSuchFieldException -> L3a
            java.lang.Object r1 = r1.get(r5)     // Catch: java.lang.NoSuchFieldException -> L3a
            boolean r2 = r6.isInstance(r1)     // Catch: java.lang.NoSuchFieldException -> L3a
            if (r2 != 0) goto L35
            goto L39
        L35:
            java.lang.Object r3 = r6.cast(r1)     // Catch: java.lang.NoSuchFieldException -> L3a
        L39:
            return r3
        L3a:
            java.lang.Class r0 = r0.getSuperclass()
            java.lang.String r1 = "c.superclass"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            goto L13
        L44:
            java.lang.String r0 = "delegate"
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            r1 = r1 ^ r2
            if (r1 == 0) goto L5a
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            java.lang.Object r5 = readFieldOrNull(r5, r1, r0)
            if (r5 == 0) goto L5a
            java.lang.Object r5 = readFieldOrNull(r5, r6, r7)
            return r5
        L5a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.Util.readFieldOrNull(java.lang.Object, java.lang.Class, java.lang.String):java.lang.Object");
    }

    public static final int indexOf(String[] indexOf, String value, Comparator<String> comparator) {
        Intrinsics.checkParameterIsNotNull(indexOf, "$this$indexOf");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int length = indexOf.length;
        for (int i = 0; i < length; i++) {
            if (comparator.compare(indexOf[i], value) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static final void tryExecute(Executor tryExecute, String name, Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(tryExecute, "$this$tryExecute");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(block, "block");
        try {
            tryExecute.execute(new Util$execute$1(name, block));
        } catch (RejectedExecutionException unused) {
        }
    }
}
