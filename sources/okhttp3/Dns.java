package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Dns.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001¨\u0006\b"}, d2 = {"Lokhttp3/Dns;", "", "lookup", "", "Ljava/net/InetAddress;", "hostname", "", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public interface Dns {
    public static final Companion Companion = new Companion(null);
    public static final Dns SYSTEM = new Dns() { // from class: okhttp3.Dns$Companion$SYSTEM$1
        @Override // okhttp3.Dns
        public List<InetAddress> lookup(String hostname) {
            Intrinsics.checkParameterIsNotNull(hostname, "hostname");
            try {
                InetAddress[] allByName = InetAddress.getAllByName(hostname);
                Intrinsics.checkExpressionValueIsNotNull(allByName, "InetAddress.getAllByName(hostname)");
                return ArraysKt.toList(allByName);
            } catch (NullPointerException e) {
                UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour for dns lookup of " + hostname);
                unknownHostException.initCause(e);
                throw unknownHostException;
            }
        }
    };

    List<InetAddress> lookup(String str) throws UnknownHostException;

    /* compiled from: Dns.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0002\n\u0000¨\u0006\u0001\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001¨\u0006\u0005"}, d2 = {"Lokhttp3/Dns$Companion;", "", "()V", "SYSTEM", "Lokhttp3/Dns;", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = null;

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
