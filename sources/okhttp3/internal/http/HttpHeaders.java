package okhttp3.internal.http;

import com.liulishuo.filedownloader.model.FileDownloadModel;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.ByteString;
/* compiled from: HttpHeaders.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0005\n\u0000\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0004*\u00020\u0006\u001a\u001a\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012H\u0002\u001a\u000e\u0010\u0013\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u001a\u0010\u0015\u001a\u00020\u000f*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n\u001a\f\u0010\u001a\u001a\u00020\u0004*\u00020\u0010H\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0004*\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"QUOTED_STRING_DELIMITERS", "Lokio/ByteString;", "TOKEN_DELIMITERS", "hasBody", "", "response", "Lokhttp3/Response;", "parseChallenges", "", "Lokhttp3/Challenge;", "Lokhttp3/Headers;", "headerName", "", "promisesBody", "readChallengeHeader", "", "Lokio/Buffer;", "result", "", "readQuotedString", "readToken", "receiveHeaders", "Lokhttp3/CookieJar;", FileDownloadModel.URL, "Lokhttp3/HttpUrl;", "headers", "skipCommasAndWhitespace", "startsWith", "prefix", "", "okhttp"}, k = 2, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class HttpHeaders {
    private static final ByteString QUOTED_STRING_DELIMITERS = ByteString.Companion.encodeUtf8("\"\\");
    private static final ByteString TOKEN_DELIMITERS = ByteString.Companion.encodeUtf8("\t ,=");

    public static final List<Challenge> parseChallenges(Headers parseChallenges, String headerName) {
        Intrinsics.checkParameterIsNotNull(parseChallenges, "$this$parseChallenges");
        Intrinsics.checkParameterIsNotNull(headerName, "headerName");
        ArrayList arrayList = new ArrayList();
        int size = parseChallenges.size();
        for (int i = 0; i < size; i++) {
            if (StringsKt.equals(headerName, parseChallenges.name(i), true)) {
                try {
                    readChallengeHeader(new Buffer().writeUtf8(parseChallenges.value(i)), arrayList);
                } catch (EOFException e) {
                    Platform.Companion.get().log(5, "Unable to parse challenge", e);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x008c, code lost:
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x008c, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final void readChallengeHeader(okio.Buffer r7, java.util.List<okhttp3.Challenge> r8) throws java.io.EOFException {
        /*
            r0 = 0
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1
        L4:
            r1 = r0
        L5:
            if (r1 != 0) goto L11
            skipCommasAndWhitespace(r7)
            java.lang.String r1 = readToken(r7)
            if (r1 != 0) goto L11
            return
        L11:
            boolean r2 = skipCommasAndWhitespace(r7)
            java.lang.String r3 = readToken(r7)
            if (r3 != 0) goto L2f
            boolean r7 = r7.exhausted()
            if (r7 != 0) goto L22
            return
        L22:
            okhttp3.Challenge r7 = new okhttp3.Challenge
            java.util.Map r0 = kotlin.collections.MapsKt.emptyMap()
            r7.<init>(r1, r0)
            r8.add(r7)
            return
        L2f:
            r4 = 61
            byte r4 = (byte) r4
            int r5 = okhttp3.internal.Util.skipAll(r7, r4)
            boolean r6 = skipCommasAndWhitespace(r7)
            if (r2 != 0) goto L6d
            if (r6 != 0) goto L44
            boolean r2 = r7.exhausted()
            if (r2 == 0) goto L6d
        L44:
            okhttp3.Challenge r2 = new okhttp3.Challenge
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            java.lang.String r3 = "="
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            java.lang.String r3 = kotlin.text.StringsKt.repeat(r3, r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.util.Map r3 = java.util.Collections.singletonMap(r0, r3)
            java.lang.String r4 = "Collections.singletonMap…ek + \"=\".repeat(eqCount))"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r4)
            r2.<init>(r1, r3)
            r8.add(r2)
            goto L4
        L6d:
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.util.Map r2 = (java.util.Map) r2
            int r6 = okhttp3.internal.Util.skipAll(r7, r4)
            int r5 = r5 + r6
        L79:
            if (r3 != 0) goto L8a
            java.lang.String r3 = readToken(r7)
            boolean r5 = skipCommasAndWhitespace(r7)
            if (r5 == 0) goto L86
            goto L8c
        L86:
            int r5 = okhttp3.internal.Util.skipAll(r7, r4)
        L8a:
            if (r5 != 0) goto L97
        L8c:
            okhttp3.Challenge r4 = new okhttp3.Challenge
            r4.<init>(r1, r2)
            r8.add(r4)
            r1 = r3
            goto L5
        L97:
            r6 = 1
            if (r5 <= r6) goto L9b
            return
        L9b:
            boolean r6 = skipCommasAndWhitespace(r7)
            if (r6 == 0) goto La2
            return
        La2:
            r6 = 34
            byte r6 = (byte) r6
            boolean r6 = startsWith(r7, r6)
            if (r6 == 0) goto Lb0
            java.lang.String r6 = readQuotedString(r7)
            goto Lb4
        Lb0:
            java.lang.String r6 = readToken(r7)
        Lb4:
            if (r6 == 0) goto Lce
            java.lang.Object r3 = r2.put(r3, r6)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto Lbf
            return
        Lbf:
            boolean r3 = skipCommasAndWhitespace(r7)
            if (r3 != 0) goto Lcc
            boolean r3 = r7.exhausted()
            if (r3 != 0) goto Lcc
            return
        Lcc:
            r3 = r0
            goto L79
        Lce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.HttpHeaders.readChallengeHeader(okio.Buffer, java.util.List):void");
    }

    private static final boolean skipCommasAndWhitespace(Buffer buffer) {
        boolean z = false;
        while (!buffer.exhausted()) {
            byte b = buffer.getByte(0L);
            if (b == 9 || b == 32) {
                buffer.readByte();
            } else if (b != 44) {
                break;
            } else {
                buffer.readByte();
                z = true;
            }
        }
        return z;
    }

    private static final boolean startsWith(Buffer buffer, byte b) {
        return !buffer.exhausted() && buffer.getByte(0L) == b;
    }

    private static final String readQuotedString(Buffer buffer) throws EOFException {
        byte b = (byte) 34;
        if (!(buffer.readByte() == b)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Buffer buffer2 = new Buffer();
        while (true) {
            long indexOfElement = buffer.indexOfElement(QUOTED_STRING_DELIMITERS);
            if (indexOfElement == -1) {
                return null;
            }
            if (buffer.getByte(indexOfElement) == b) {
                buffer2.write(buffer, indexOfElement);
                buffer.readByte();
                return buffer2.readUtf8();
            } else if (buffer.size() == indexOfElement + 1) {
                return null;
            } else {
                buffer2.write(buffer, indexOfElement);
                buffer.readByte();
                buffer2.write(buffer, 1L);
            }
        }
    }

    private static final String readToken(Buffer buffer) {
        long indexOfElement = buffer.indexOfElement(TOKEN_DELIMITERS);
        if (indexOfElement == -1) {
            indexOfElement = buffer.size();
        }
        if (indexOfElement != 0) {
            return buffer.readUtf8(indexOfElement);
        }
        return null;
    }

    public static final void receiveHeaders(CookieJar receiveHeaders, HttpUrl url, Headers headers) {
        Intrinsics.checkParameterIsNotNull(receiveHeaders, "$this$receiveHeaders");
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(headers, "headers");
        if (receiveHeaders == CookieJar.NO_COOKIES) {
            return;
        }
        List<Cookie> parseAll = Cookie.Companion.parseAll(url, headers);
        if (parseAll.isEmpty()) {
            return;
        }
        receiveHeaders.saveFromResponse(url, parseAll);
    }

    public static final boolean promisesBody(Response promisesBody) {
        Intrinsics.checkParameterIsNotNull(promisesBody, "$this$promisesBody");
        if (Intrinsics.areEqual(promisesBody.request().method(), "HEAD")) {
            return false;
        }
        int code = promisesBody.code();
        return (((code >= 100 && code < 200) || code == 204 || code == 304) && Util.headersContentLength(promisesBody) == -1 && !StringsKt.equals("chunked", Response.header$default(promisesBody, com.google.common.net.HttpHeaders.TRANSFER_ENCODING, null, 2, null), true)) ? false : true;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "No longer supported", replaceWith = @ReplaceWith(expression = "response.promisesBody()", imports = {}))
    public static final boolean hasBody(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        return promisesBody(response);
    }
}
