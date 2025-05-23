package com.squareup.kotlinpoet;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: LineWrapper.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006J\u0006\u0010\u0014\u001a\u00020\u0012J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u000eH\u0002J\u000e\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\bR\u0012\u0010\n\u001a\u00060\u000bj\u0002`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/squareup/kotlinpoet/LineWrapper;", "", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "indent", "", "columnLimit", "", "(Ljava/lang/Appendable;Ljava/lang/String;I)V", "buffer", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "closed", "", "column", "indentLevel", "append", "", "s", "close", "flush", "wrap", "wrappingSpace", "kotlinpoet"}, k = 1, mv = {1, 1, 7})
/* loaded from: classes2.dex */
public final class LineWrapper {
    private final StringBuilder buffer;
    private boolean closed;
    private int column;
    private final int columnLimit;
    private final String indent;
    private int indentLevel;
    private final Appendable out;

    public LineWrapper(Appendable out, String indent, int i) {
        Intrinsics.checkParameterIsNotNull(out, "out");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        this.out = out;
        this.indent = indent;
        this.columnLimit = i;
        this.buffer = new StringBuilder();
        this.indentLevel = -1;
    }

    public final void append(String s) {
        int length;
        Intrinsics.checkParameterIsNotNull(s, "s");
        if (!(!this.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (this.indentLevel != -1) {
            int indexOf$default = StringsKt.indexOf$default((CharSequence) s, '\n', 0, false, 6, (Object) null);
            if (indexOf$default == -1 && this.column + s.length() <= this.columnLimit) {
                this.buffer.append(s);
                this.column += s.length();
                return;
            }
            flush(indexOf$default == -1 || this.column + indexOf$default > this.columnLimit);
        }
        String str = s;
        this.out.append(str);
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, '\n', 0, false, 6, (Object) null);
        if (lastIndexOf$default != -1) {
            length = (s.length() - lastIndexOf$default) - 1;
        } else {
            length = s.length() + this.column;
        }
        this.column = length;
    }

    public final void wrappingSpace(int i) {
        if (!(!this.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (this.indentLevel != -1) {
            flush(false);
        }
        this.column++;
        this.indentLevel = i;
    }

    public final void close() {
        if (this.indentLevel != -1) {
            flush(false);
        }
        this.closed = true;
    }

    private final void flush(boolean z) {
        if (z) {
            this.out.append('\n');
            int i = this.indentLevel;
            for (int i2 = 0; i2 < i; i2++) {
                this.out.append(this.indent);
            }
            int length = this.indentLevel * this.indent.length();
            this.column = length;
            this.column = length + this.buffer.length();
        } else {
            this.out.append(' ');
        }
        this.out.append(this.buffer);
        StringBuilder sb = this.buffer;
        sb.delete(0, sb.length());
        this.indentLevel = -1;
    }
}
