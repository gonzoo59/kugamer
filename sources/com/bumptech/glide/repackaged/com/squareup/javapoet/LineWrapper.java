package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;
/* loaded from: classes.dex */
final class LineWrapper {
    private boolean closed;
    private final int columnLimit;
    private final String indent;
    private final Appendable out;
    private final StringBuilder buffer = new StringBuilder();
    private int column = 0;
    private int indentLevel = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LineWrapper(Appendable appendable, String str, int i) {
        Util.checkNotNull(appendable, "out == null", new Object[0]);
        this.out = appendable;
        this.indent = str;
        this.columnLimit = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void append(String str) throws IOException {
        int length;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.indentLevel != -1) {
            int indexOf = str.indexOf(10);
            if (indexOf == -1 && this.column + str.length() <= this.columnLimit) {
                this.buffer.append(str);
                this.column += str.length();
                return;
            }
            flush(indexOf == -1 || this.column + indexOf > this.columnLimit);
        }
        this.out.append(str);
        int lastIndexOf = str.lastIndexOf(10);
        if (lastIndexOf != -1) {
            length = (str.length() - lastIndexOf) - 1;
        } else {
            length = str.length() + this.column;
        }
        this.column = length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void wrappingSpace(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.indentLevel != -1) {
            flush(false);
        }
        this.column++;
        this.indentLevel = i;
    }

    private void flush(boolean z) throws IOException {
        int i;
        if (z) {
            this.out.append('\n');
            int i2 = 0;
            while (true) {
                i = this.indentLevel;
                if (i2 >= i) {
                    break;
                }
                this.out.append(this.indent);
                i2++;
            }
            int length = i * this.indent.length();
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
