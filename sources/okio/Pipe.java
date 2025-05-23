package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Pipe.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\nJ\r\u0010\u0011\u001a\u00020\nH\u0007¢\u0006\u0002\b J\r\u0010\u0018\u001a\u00020\u0019H\u0007¢\u0006\u0002\b!J&\u0010\"\u001a\u00020\u001f*\u00020\n2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u001f0$¢\u0006\u0002\b%H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0011\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0018\u001a\u00020\u00198\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017¨\u0006&"}, d2 = {"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$jvm", "()Lokio/Buffer;", "foldedSink", "Lokio/Sink;", "getFoldedSink$jvm", "()Lokio/Sink;", "setFoldedSink$jvm", "(Lokio/Sink;)V", "getMaxBufferSize$jvm", "()J", "sink", "sinkClosed", "", "getSinkClosed$jvm", "()Z", "setSinkClosed$jvm", "(Z)V", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$jvm", "setSourceClosed$jvm", "fold", "", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "jvm"}, k = 1, mv = {1, 1, 11})
/* loaded from: classes2.dex */
public final class Pipe {
    private final Buffer buffer = new Buffer();
    private Sink foldedSink;
    private final long maxBufferSize;
    private final Sink sink;
    private boolean sinkClosed;
    private final Source source;
    private boolean sourceClosed;

    public Pipe(long j) {
        this.maxBufferSize = j;
        if (!(j >= 1)) {
            throw new IllegalArgumentException(("maxBufferSize < 1: " + j).toString());
        }
        this.sink = new Sink() { // from class: okio.Pipe$sink$1
            private final Timeout timeout = new Timeout();

            @Override // okio.Sink
            public void write(Buffer source, long j2) {
                boolean hasDeadline;
                Intrinsics.checkParameterIsNotNull(source, "source");
                Sink sink = null;
                synchronized (Pipe.this.getBuffer$jvm()) {
                    if (!(!Pipe.this.getSinkClosed$jvm())) {
                        throw new IllegalStateException("closed".toString());
                    }
                    while (true) {
                        if (j2 <= 0) {
                            break;
                        }
                        Sink foldedSink$jvm = Pipe.this.getFoldedSink$jvm();
                        if (foldedSink$jvm != null) {
                            sink = foldedSink$jvm;
                            break;
                        } else if (Pipe.this.getSourceClosed$jvm()) {
                            throw new IOException("source is closed");
                        } else {
                            long maxBufferSize$jvm = Pipe.this.getMaxBufferSize$jvm() - Pipe.this.getBuffer$jvm().size();
                            if (maxBufferSize$jvm == 0) {
                                this.timeout.waitUntilNotified(Pipe.this.getBuffer$jvm());
                            } else {
                                long min = Math.min(maxBufferSize$jvm, j2);
                                Pipe.this.getBuffer$jvm().write(source, min);
                                j2 -= min;
                                Buffer buffer$jvm = Pipe.this.getBuffer$jvm();
                                if (buffer$jvm == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                }
                                buffer$jvm.notifyAll();
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink != null) {
                    Pipe pipe = Pipe.this;
                    Timeout timeout = sink.timeout();
                    Timeout timeout2 = pipe.sink().timeout();
                    long timeoutNanos = timeout.timeoutNanos();
                    timeout.timeout(Timeout.Companion.minTimeout(timeout2.timeoutNanos(), timeout.timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (timeout.hasDeadline()) {
                        long deadlineNanoTime = timeout.deadlineNanoTime();
                        if (timeout2.hasDeadline()) {
                            timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                        }
                        try {
                            sink.write(source, j2);
                            if (hasDeadline) {
                                return;
                            }
                            return;
                        } finally {
                            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                            if (timeout2.hasDeadline()) {
                                timeout.deadlineNanoTime(deadlineNanoTime);
                            }
                        }
                    }
                    if (timeout2.hasDeadline()) {
                        timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                    }
                    try {
                        sink.write(source, j2);
                    } finally {
                        timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (timeout2.hasDeadline()) {
                            timeout.clearDeadline();
                        }
                    }
                }
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() {
                boolean hasDeadline;
                Sink sink = null;
                synchronized (Pipe.this.getBuffer$jvm()) {
                    if (!(!Pipe.this.getSinkClosed$jvm())) {
                        throw new IllegalStateException("closed".toString());
                    }
                    Sink foldedSink$jvm = Pipe.this.getFoldedSink$jvm();
                    if (foldedSink$jvm != null) {
                        sink = foldedSink$jvm;
                    } else if (Pipe.this.getSourceClosed$jvm() && Pipe.this.getBuffer$jvm().size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink != null) {
                    Pipe pipe = Pipe.this;
                    Timeout timeout = sink.timeout();
                    Timeout timeout2 = pipe.sink().timeout();
                    long timeoutNanos = timeout.timeoutNanos();
                    timeout.timeout(Timeout.Companion.minTimeout(timeout2.timeoutNanos(), timeout.timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (timeout.hasDeadline()) {
                        long deadlineNanoTime = timeout.deadlineNanoTime();
                        if (timeout2.hasDeadline()) {
                            timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                        }
                        try {
                            sink.flush();
                            if (hasDeadline) {
                                return;
                            }
                            return;
                        } finally {
                            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                            if (timeout2.hasDeadline()) {
                                timeout.deadlineNanoTime(deadlineNanoTime);
                            }
                        }
                    }
                    if (timeout2.hasDeadline()) {
                        timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                    }
                    try {
                        sink.flush();
                    } finally {
                        timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (timeout2.hasDeadline()) {
                            timeout.clearDeadline();
                        }
                    }
                }
            }

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                boolean hasDeadline;
                Sink sink = null;
                synchronized (Pipe.this.getBuffer$jvm()) {
                    if (Pipe.this.getSinkClosed$jvm()) {
                        return;
                    }
                    Sink foldedSink$jvm = Pipe.this.getFoldedSink$jvm();
                    if (foldedSink$jvm != null) {
                        sink = foldedSink$jvm;
                    } else {
                        if (Pipe.this.getSourceClosed$jvm() && Pipe.this.getBuffer$jvm().size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe.this.setSinkClosed$jvm(true);
                        Buffer buffer$jvm = Pipe.this.getBuffer$jvm();
                        if (buffer$jvm == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        }
                        buffer$jvm.notifyAll();
                    }
                    Unit unit = Unit.INSTANCE;
                    if (sink != null) {
                        Pipe pipe = Pipe.this;
                        Timeout timeout = sink.timeout();
                        Timeout timeout2 = pipe.sink().timeout();
                        long timeoutNanos = timeout.timeoutNanos();
                        timeout.timeout(Timeout.Companion.minTimeout(timeout2.timeoutNanos(), timeout.timeoutNanos()), TimeUnit.NANOSECONDS);
                        if (timeout.hasDeadline()) {
                            long deadlineNanoTime = timeout.deadlineNanoTime();
                            if (timeout2.hasDeadline()) {
                                timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                            }
                            try {
                                sink.close();
                                if (hasDeadline) {
                                    return;
                                }
                                return;
                            } finally {
                                timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                                if (timeout2.hasDeadline()) {
                                    timeout.deadlineNanoTime(deadlineNanoTime);
                                }
                            }
                        }
                        if (timeout2.hasDeadline()) {
                            timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                        }
                        try {
                            sink.close();
                        } finally {
                            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                            if (timeout2.hasDeadline()) {
                                timeout.clearDeadline();
                            }
                        }
                    }
                }
            }

            @Override // okio.Sink
            public Timeout timeout() {
                return this.timeout;
            }
        };
        this.source = new Source() { // from class: okio.Pipe$source$1
            private final Timeout timeout = new Timeout();

            @Override // okio.Source
            public long read(Buffer sink, long j2) {
                Intrinsics.checkParameterIsNotNull(sink, "sink");
                synchronized (Pipe.this.getBuffer$jvm()) {
                    if (!(!Pipe.this.getSourceClosed$jvm())) {
                        throw new IllegalStateException("closed".toString());
                    }
                    while (Pipe.this.getBuffer$jvm().size() == 0) {
                        if (Pipe.this.getSinkClosed$jvm()) {
                            return -1L;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.getBuffer$jvm());
                    }
                    long read = Pipe.this.getBuffer$jvm().read(sink, j2);
                    Buffer buffer$jvm = Pipe.this.getBuffer$jvm();
                    if (buffer$jvm != null) {
                        buffer$jvm.notifyAll();
                        return read;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                }
            }

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                synchronized (Pipe.this.getBuffer$jvm()) {
                    Pipe.this.setSourceClosed$jvm(true);
                    Buffer buffer$jvm = Pipe.this.getBuffer$jvm();
                    if (buffer$jvm == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer$jvm.notifyAll();
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // okio.Source
            public Timeout timeout() {
                return this.timeout;
            }
        };
    }

    public final long getMaxBufferSize$jvm() {
        return this.maxBufferSize;
    }

    public final Buffer getBuffer$jvm() {
        return this.buffer;
    }

    public final boolean getSinkClosed$jvm() {
        return this.sinkClosed;
    }

    public final void setSinkClosed$jvm(boolean z) {
        this.sinkClosed = z;
    }

    public final boolean getSourceClosed$jvm() {
        return this.sourceClosed;
    }

    public final void setSourceClosed$jvm(boolean z) {
        this.sourceClosed = z;
    }

    public final Sink getFoldedSink$jvm() {
        return this.foldedSink;
    }

    public final void setFoldedSink$jvm(Sink sink) {
        this.foldedSink = sink;
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }

    public final void fold(Sink sink) throws IOException {
        boolean z;
        Buffer buffer;
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        while (true) {
            synchronized (this.buffer) {
                if (!(this.foldedSink == null)) {
                    throw new IllegalStateException("sink already folded".toString());
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = sink;
                    return;
                }
                z = this.sinkClosed;
                buffer = new Buffer();
                Buffer buffer2 = this.buffer;
                buffer.write(buffer2, buffer2.size());
                Buffer buffer3 = this.buffer;
                if (buffer3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                }
                buffer3.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            try {
                sink.write(buffer, buffer.size());
                if (z) {
                    sink.close();
                } else {
                    sink.flush();
                }
            } catch (Throwable th) {
                synchronized (this.buffer) {
                    this.sourceClosed = true;
                    Buffer buffer4 = this.buffer;
                    if (buffer4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer4.notifyAll();
                    Unit unit2 = Unit.INSTANCE;
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forward(Sink sink, Function1<? super Sink, Unit> function1) {
        Timeout timeout = sink.timeout();
        Timeout timeout2 = sink().timeout();
        long timeoutNanos = timeout.timeoutNanos();
        timeout.timeout(Timeout.Companion.minTimeout(timeout2.timeoutNanos(), timeout.timeoutNanos()), TimeUnit.NANOSECONDS);
        if (timeout.hasDeadline()) {
            long deadlineNanoTime = timeout.deadlineNanoTime();
            if (timeout2.hasDeadline()) {
                timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
            }
            try {
                function1.invoke(sink);
                return;
            } finally {
                InlineMarker.finallyStart(1);
                timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                if (timeout2.hasDeadline()) {
                    timeout.deadlineNanoTime(deadlineNanoTime);
                }
                InlineMarker.finallyEnd(1);
            }
        }
        if (timeout2.hasDeadline()) {
            timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
        }
        try {
            function1.invoke(sink);
        } finally {
            InlineMarker.finallyStart(1);
            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
            if (timeout2.hasDeadline()) {
                timeout.clearDeadline();
            }
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sink", imports = {}))
    /* renamed from: -deprecated_sink  reason: not valid java name */
    public final Sink m1312deprecated_sink() {
        return this.sink;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "source", imports = {}))
    /* renamed from: -deprecated_source  reason: not valid java name */
    public final Source m1313deprecated_source() {
        return this.source;
    }
}
