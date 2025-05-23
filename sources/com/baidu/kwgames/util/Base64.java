package com.baidu.kwgames.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
/* loaded from: classes.dex */
public class Base64 {
    private Base64() {
    }

    public static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    public static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }

    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    public static Encoder getMimeEncoder(int i, byte[] bArr) {
        Objects.requireNonNull(bArr);
        int[] iArr = Decoder.fromBase64;
        for (byte b : bArr) {
            if (iArr[b & 255] != -1) {
                throw new IllegalArgumentException("Illegal base64 line separator character 0x" + Integer.toString(b, 16));
            }
        }
        if (i <= 0) {
            return Encoder.RFC4648;
        }
        return new Encoder(false, bArr, (i >> 2) << 2, true);
    }

    public static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    public static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }

    public static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }

    /* loaded from: classes.dex */
    public static class Encoder {
        private static final byte[] CRLF;
        private static final int MIMELINEMAX = 76;
        static final Encoder RFC2045;
        private final boolean doPadding;
        private final boolean isURL;
        private final int linemax;
        private final byte[] newline;
        private static final char[] toBase64 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        private static final char[] toBase64URL = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        static final Encoder RFC4648 = new Encoder(false, null, -1, true);
        static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);

        private Encoder(boolean z, byte[] bArr, int i, boolean z2) {
            this.isURL = z;
            this.newline = bArr;
            this.linemax = i;
            this.doPadding = z2;
        }

        static {
            byte[] bArr = {13, 10};
            CRLF = bArr;
            RFC2045 = new Encoder(false, bArr, 76, true);
        }

        private final int outLength(int i) {
            int i2;
            if (this.doPadding) {
                i2 = ((i + 2) / 3) * 4;
            } else {
                int i3 = i % 3;
                i2 = ((i / 3) * 4) + (i3 == 0 ? 0 : i3 + 1);
            }
            int i4 = this.linemax;
            return i4 > 0 ? i2 + (((i2 - 1) / i4) * this.newline.length) : i2;
        }

        public byte[] encode(byte[] bArr) {
            int outLength = outLength(bArr.length);
            byte[] bArr2 = new byte[outLength];
            int encode0 = encode0(bArr, 0, bArr.length, bArr2);
            return encode0 != outLength ? Arrays.copyOf(bArr2, encode0) : bArr2;
        }

        public int encode(byte[] bArr, byte[] bArr2) {
            if (bArr2.length < outLength(bArr.length)) {
                throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
            }
            return encode0(bArr, 0, bArr.length, bArr2);
        }

        public String encodeToString(byte[] bArr) {
            byte[] encode = encode(bArr);
            return new String(encode, 0, 0, encode.length);
        }

        public ByteBuffer encode(ByteBuffer byteBuffer) {
            int encode0;
            int outLength = outLength(byteBuffer.remaining());
            byte[] bArr = new byte[outLength];
            if (byteBuffer.hasArray()) {
                encode0 = encode0(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.arrayOffset() + byteBuffer.limit(), bArr);
                byteBuffer.position(byteBuffer.limit());
            } else {
                int remaining = byteBuffer.remaining();
                byte[] bArr2 = new byte[remaining];
                byteBuffer.get(bArr2);
                encode0 = encode0(bArr2, 0, remaining, bArr);
            }
            if (encode0 != outLength) {
                bArr = Arrays.copyOf(bArr, encode0);
            }
            return ByteBuffer.wrap(bArr);
        }

        public OutputStream wrap(OutputStream outputStream) {
            Objects.requireNonNull(outputStream);
            return new EncOutputStream(outputStream, this.isURL ? toBase64URL : toBase64, this.newline, this.linemax, this.doPadding);
        }

        public Encoder withoutPadding() {
            return !this.doPadding ? this : new Encoder(this.isURL, this.newline, this.linemax, false);
        }

        private int encode0(byte[] bArr, int i, int i2, byte[] bArr2) {
            char[] cArr = this.isURL ? toBase64URL : toBase64;
            int i3 = ((i2 - i) / 3) * 3;
            int i4 = i + i3;
            int i5 = this.linemax;
            if (i5 > 0 && i3 > (i5 / 4) * 3) {
                i3 = (i5 / 4) * 3;
            }
            int i6 = 0;
            while (i < i4) {
                int min = Math.min(i + i3, i4);
                int i7 = i;
                int i8 = i6;
                while (i7 < min) {
                    int i9 = i7 + 1;
                    int i10 = i9 + 1;
                    int i11 = ((bArr[i7] & 255) << 16) | ((bArr[i9] & 255) << 8);
                    int i12 = i10 + 1;
                    int i13 = i11 | (bArr[i10] & 255);
                    int i14 = i8 + 1;
                    bArr2[i8] = (byte) cArr[(i13 >>> 18) & 63];
                    int i15 = i14 + 1;
                    bArr2[i14] = (byte) cArr[(i13 >>> 12) & 63];
                    int i16 = i15 + 1;
                    bArr2[i15] = (byte) cArr[(i13 >>> 6) & 63];
                    i8 = i16 + 1;
                    bArr2[i16] = (byte) cArr[i13 & 63];
                    i7 = i12;
                }
                int i17 = ((min - i) / 3) * 4;
                i6 += i17;
                if (i17 == this.linemax && min < i2) {
                    byte[] bArr3 = this.newline;
                    int length = bArr3.length;
                    int i18 = 0;
                    while (i18 < length) {
                        bArr2[i6] = bArr3[i18];
                        i18++;
                        i6++;
                    }
                }
                i = min;
            }
            if (i < i2) {
                int i19 = i + 1;
                int i20 = bArr[i] & 255;
                int i21 = i6 + 1;
                bArr2[i6] = (byte) cArr[i20 >> 2];
                if (i19 == i2) {
                    int i22 = i21 + 1;
                    bArr2[i21] = (byte) cArr[(i20 << 4) & 63];
                    if (this.doPadding) {
                        int i23 = i22 + 1;
                        bArr2[i22] = 61;
                        int i24 = i23 + 1;
                        bArr2[i23] = 61;
                        return i24;
                    }
                    return i22;
                }
                int i25 = bArr[i19] & 255;
                int i26 = i21 + 1;
                bArr2[i21] = (byte) cArr[((i20 << 4) & 63) | (i25 >> 4)];
                int i27 = i26 + 1;
                bArr2[i26] = (byte) cArr[(i25 << 2) & 63];
                if (this.doPadding) {
                    int i28 = i27 + 1;
                    bArr2[i27] = 61;
                    return i28;
                }
                return i27;
            }
            return i6;
        }
    }

    /* loaded from: classes.dex */
    public static class Decoder {
        static final Decoder RFC2045;
        static final Decoder RFC4648;
        static final Decoder RFC4648_URLSAFE;
        private static final int[] fromBase64;
        private static final int[] fromBase64URL;
        private final boolean isMIME;
        private final boolean isURL;

        private Decoder(boolean z, boolean z2) {
            this.isURL = z;
            this.isMIME = z2;
        }

        static {
            int[] iArr = new int[256];
            fromBase64 = iArr;
            Arrays.fill(iArr, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++) {
                fromBase64[Encoder.toBase64[i]] = i;
            }
            fromBase64[61] = -2;
            int[] iArr2 = new int[256];
            fromBase64URL = iArr2;
            Arrays.fill(iArr2, -1);
            for (int i2 = 0; i2 < Encoder.toBase64URL.length; i2++) {
                fromBase64URL[Encoder.toBase64URL[i2]] = i2;
            }
            fromBase64URL[61] = -2;
            RFC4648 = new Decoder(false, false);
            RFC4648_URLSAFE = new Decoder(true, false);
            RFC2045 = new Decoder(false, true);
        }

        public byte[] decode(byte[] bArr) {
            int outLength = outLength(bArr, 0, bArr.length);
            byte[] bArr2 = new byte[outLength];
            int decode0 = decode0(bArr, 0, bArr.length, bArr2);
            return decode0 != outLength ? Arrays.copyOf(bArr2, decode0) : bArr2;
        }

        public byte[] decode(String str) {
            return decode(str.getBytes(StandardCharsets.ISO_8859_1));
        }

        public int decode(byte[] bArr, byte[] bArr2) {
            if (bArr2.length < outLength(bArr, 0, bArr.length)) {
                throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
            }
            return decode0(bArr, 0, bArr.length, bArr2);
        }

        public ByteBuffer decode(ByteBuffer byteBuffer) {
            int remaining;
            byte[] bArr;
            int i;
            int position = byteBuffer.position();
            try {
                if (byteBuffer.hasArray()) {
                    bArr = byteBuffer.array();
                    i = byteBuffer.arrayOffset() + byteBuffer.position();
                    remaining = byteBuffer.arrayOffset() + byteBuffer.limit();
                    byteBuffer.position(byteBuffer.limit());
                } else {
                    remaining = byteBuffer.remaining();
                    bArr = new byte[remaining];
                    byteBuffer.get(bArr);
                    i = 0;
                }
                byte[] bArr2 = new byte[outLength(bArr, i, remaining)];
                return ByteBuffer.wrap(bArr2, 0, decode0(bArr, i, remaining, bArr2));
            } catch (IllegalArgumentException e) {
                byteBuffer.position(position);
                throw e;
            }
        }

        public InputStream wrap(InputStream inputStream) {
            Objects.requireNonNull(inputStream);
            return new DecInputStream(inputStream, this.isURL ? fromBase64URL : fromBase64, this.isMIME);
        }

        private int outLength(byte[] bArr, int i, int i2) {
            int i3;
            int[] iArr = this.isURL ? fromBase64URL : fromBase64;
            int i4 = i2 - i;
            int i5 = 0;
            if (i4 == 0) {
                return 0;
            }
            if (i4 < 2) {
                if (this.isMIME && iArr[0] == -1) {
                    return 0;
                }
                throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (this.isMIME) {
                int i6 = 0;
                while (true) {
                    if (i >= i2) {
                        break;
                    }
                    int i7 = i + 1;
                    int i8 = bArr[i] & 255;
                    if (i8 == 61) {
                        i4 -= (i2 - i7) + 1;
                        break;
                    }
                    if (iArr[i8] == -1) {
                        i6++;
                    }
                    i = i7;
                }
                i4 -= i6;
            } else if (bArr[i2 - 1] == 61) {
                i5 = bArr[i2 - 2] == 61 ? 2 : 1;
            }
            if (i5 == 0 && (i3 = i4 & 3) != 0) {
                i5 = 4 - i3;
            }
            return (((i4 + 3) / 4) * 3) - i5;
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
            if (r11[r8] == 61) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0030, code lost:
            if (r4 != 18) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
            if (r4 != 6) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
            r14[r5] = (byte) (r3 >> 16);
            r5 = r5 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x008a, code lost:
            if (r4 != 0) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x008c, code lost:
            r1 = r5 + 1;
            r14[r5] = (byte) (r3 >> 16);
            r5 = r1 + 1;
            r14[r1] = (byte) (r3 >> 8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x009d, code lost:
            if (r4 == 12) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x009f, code lost:
            if (r12 >= r13) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00a3, code lost:
            if (r10.isMIME == false) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00a5, code lost:
            r14 = r12 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00ab, code lost:
            if (r0[r11[r12]] >= 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00ad, code lost:
            r12 = r14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00af, code lost:
            r12 = r14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00c6, code lost:
            throw new java.lang.IllegalArgumentException("Input byte array has incorrect ending byte at " + r12);
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00c7, code lost:
            return r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00cf, code lost:
            throw new java.lang.IllegalArgumentException("Last unit does not have enough valid bits");
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private int decode0(byte[] r11, int r12, int r13, byte[] r14) {
            /*
                Method dump skipped, instructions count: 208
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.util.Base64.Decoder.decode0(byte[], int, int, byte[]):int");
        }
    }

    /* loaded from: classes.dex */
    private static class EncOutputStream extends FilterOutputStream {
        private int b0;
        private int b1;
        private int b2;
        private final char[] base64;
        private boolean closed;
        private final boolean doPadding;
        private int leftover;
        private final int linemax;
        private int linepos;
        private final byte[] newline;

        EncOutputStream(OutputStream outputStream, char[] cArr, byte[] bArr, int i, boolean z) {
            super(outputStream);
            this.leftover = 0;
            this.closed = false;
            this.linepos = 0;
            this.base64 = cArr;
            this.newline = bArr;
            this.linemax = i;
            this.doPadding = z;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(int i) throws IOException {
            write(new byte[]{(byte) (i & 255)}, 0, 1);
        }

        private void checkNewline() throws IOException {
            if (this.linepos == this.linemax) {
                this.out.write(this.newline);
                this.linepos = 0;
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return;
            }
            int i3 = this.leftover;
            if (i3 != 0) {
                if (i3 == 1) {
                    int i4 = i + 1;
                    this.b1 = bArr[i] & 255;
                    i2--;
                    if (i2 == 0) {
                        this.leftover = i3 + 1;
                        return;
                    }
                    i = i4;
                }
                this.b2 = bArr[i] & 255;
                i2--;
                checkNewline();
                this.out.write(this.base64[this.b0 >> 2]);
                this.out.write(this.base64[((this.b0 << 4) & 63) | (this.b1 >> 4)]);
                this.out.write(this.base64[((this.b1 << 2) & 63) | (this.b2 >> 6)]);
                this.out.write(this.base64[this.b2 & 63]);
                this.linepos += 4;
                i++;
            }
            int i5 = i2 / 3;
            this.leftover = i2 - (i5 * 3);
            while (true) {
                int i6 = i5 - 1;
                if (i5 <= 0) {
                    break;
                }
                checkNewline();
                int i7 = i + 1;
                int i8 = i7 + 1;
                int i9 = ((bArr[i] & 255) << 16) | ((bArr[i7] & 255) << 8) | (bArr[i8] & 255);
                this.out.write(this.base64[(i9 >>> 18) & 63]);
                this.out.write(this.base64[(i9 >>> 12) & 63]);
                this.out.write(this.base64[(i9 >>> 6) & 63]);
                this.out.write(this.base64[i9 & 63]);
                this.linepos += 4;
                i = i8 + 1;
                i5 = i6;
            }
            int i10 = this.leftover;
            if (i10 == 1) {
                this.b0 = bArr[i] & 255;
            } else if (i10 == 2) {
                this.b0 = bArr[i] & 255;
                this.b1 = bArr[i + 1] & 255;
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            int i = this.leftover;
            if (i == 1) {
                checkNewline();
                this.out.write(this.base64[this.b0 >> 2]);
                this.out.write(this.base64[(this.b0 << 4) & 63]);
                if (this.doPadding) {
                    this.out.write(61);
                    this.out.write(61);
                }
            } else if (i == 2) {
                checkNewline();
                this.out.write(this.base64[this.b0 >> 2]);
                this.out.write(this.base64[((this.b0 << 4) & 63) | (this.b1 >> 4)]);
                this.out.write(this.base64[(this.b1 << 2) & 63]);
                if (this.doPadding) {
                    this.out.write(61);
                }
            }
            this.leftover = 0;
            this.out.close();
        }
    }

    /* loaded from: classes.dex */
    private static class DecInputStream extends InputStream {
        private final int[] base64;
        private final InputStream is;
        private final boolean isMIME;
        private int bits = 0;
        private int nextin = 18;
        private int nextout = -8;
        private boolean eof = false;
        private boolean closed = false;
        private byte[] sbBuf = new byte[1];

        DecInputStream(InputStream inputStream, int[] iArr, boolean z) {
            this.is = inputStream;
            this.base64 = iArr;
            this.isMIME = z;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (read(this.sbBuf, 0, 1) == -1) {
                return -1;
            }
            return this.sbBuf[0] & 255;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3;
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            if (!this.eof || this.nextout >= 0) {
                if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                    throw new IndexOutOfBoundsException();
                }
                if (this.nextout >= 0) {
                    int i4 = i;
                    while (i2 != 0) {
                        i3 = i4 + 1;
                        int i5 = this.bits;
                        int i6 = this.nextout;
                        bArr[i4] = (byte) (i5 >> i6);
                        i2--;
                        int i7 = i6 - 8;
                        this.nextout = i7;
                        if (i7 < 0) {
                            this.bits = 0;
                        } else {
                            i4 = i3;
                        }
                    }
                    return i4 - i;
                }
                i3 = i;
                while (true) {
                    if (i2 <= 0) {
                        break;
                    }
                    int read = this.is.read();
                    if (read == -1) {
                        this.eof = true;
                        int i8 = this.nextin;
                        if (i8 != 18) {
                            if (i8 == 12) {
                                throw new IOException("Base64 stream has one un-decoded dangling byte.");
                            }
                            int i9 = i3 + 1;
                            int i10 = this.bits;
                            bArr[i3] = (byte) (i10 >> 16);
                            int i11 = i2 - 1;
                            if (i8 == 0) {
                                if (i11 == 0) {
                                    this.bits = i10 >> 8;
                                    this.nextout = 0;
                                } else {
                                    i3 = i9 + 1;
                                    bArr[i9] = (byte) (i10 >> 8);
                                }
                            }
                            i3 = i9;
                        }
                        if (i3 == i) {
                            return -1;
                        }
                        return i3 - i;
                    } else if (read == 61) {
                        int i12 = this.nextin;
                        if (i12 == 18 || i12 == 12 || (i12 == 6 && this.is.read() != 61)) {
                            throw new IOException("Illegal base64 ending sequence:" + this.nextin);
                        }
                        int i13 = i3 + 1;
                        int i14 = this.bits;
                        bArr[i3] = (byte) (i14 >> 16);
                        int i15 = i2 - 1;
                        if (this.nextin == 0) {
                            if (i15 == 0) {
                                this.bits = i14 >> 8;
                                this.nextout = 0;
                            } else {
                                bArr[i13] = (byte) (i14 >> 8);
                                i3 = i13 + 1;
                                this.eof = true;
                            }
                        }
                        i3 = i13;
                        this.eof = true;
                    } else {
                        int i16 = this.base64[read];
                        if (i16 == -1) {
                            if (!this.isMIME) {
                                throw new IOException("Illegal base64 character " + Integer.toString(i16, 16));
                            }
                        } else {
                            int i17 = this.bits;
                            int i18 = this.nextin;
                            this.bits = (i16 << i18) | i17;
                            if (i18 == 0) {
                                this.nextin = 18;
                                this.nextout = 16;
                                while (true) {
                                    int i19 = this.nextout;
                                    if (i19 >= 0) {
                                        int i20 = i3 + 1;
                                        bArr[i3] = (byte) (this.bits >> i19);
                                        i2--;
                                        int i21 = i19 - 8;
                                        this.nextout = i21;
                                        if (i2 == 0 && i21 >= 0) {
                                            return i20 - i;
                                        }
                                        i3 = i20;
                                    } else {
                                        this.bits = 0;
                                        break;
                                    }
                                }
                            } else {
                                this.nextin = i18 - 6;
                            }
                        }
                    }
                }
                return i3 - i;
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            return this.is.available();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.is.close();
        }
    }
}
