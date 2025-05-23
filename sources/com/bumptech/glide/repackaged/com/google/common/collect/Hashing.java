package com.bumptech.glide.repackaged.com.google.common.collect;
/* loaded from: classes.dex */
final class Hashing {
    private static int MAX_TABLE_SIZE = 1073741824;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int smear(int i) {
        return Integer.rotateLeft(i * (-862048943), 15) * 461845907;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int smearedHash(Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }
}
