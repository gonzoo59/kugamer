package com.baidu.kwgames.ai;

import android.graphics.Bitmap;
import com.baidu.kwgames.Units;
/* loaded from: classes.dex */
public class AIwlzy {
    public static boolean is_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < i4 - 80) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }

    public static boolean is_gun_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int i3;
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        int make_in_range = Units.make_in_range(i, 0, width);
        int make_in_range2 = Units.make_in_range(i + 20, 0, width);
        int make_in_range3 = Units.make_in_range(i + 40, 0, width);
        int make_in_range4 = Units.make_in_range(i2, 0, height);
        int make_in_range5 = Units.make_in_range(i2 + 1, 0, height);
        int i4 = make_in_range4;
        while (true) {
            if (i4 > make_in_range5) {
                i3 = 0;
                break;
            }
            int pixel = bitmap.getPixel(make_in_range, i4);
            int i5 = (pixel & 16711680) >> 16;
            int i6 = (pixel & 65280) >> 8;
            int i7 = pixel & 255;
            if (i5 > 150 && i6 > 130 && i7 < i5 - 60) {
                i3 = 1;
                break;
            }
            i4++;
        }
        int i8 = make_in_range4;
        while (true) {
            if (i8 > make_in_range5) {
                break;
            }
            int pixel2 = bitmap.getPixel(make_in_range2, i8);
            int i9 = (pixel2 & 16711680) >> 16;
            int i10 = (pixel2 & 65280) >> 8;
            int i11 = pixel2 & 255;
            if (i9 > 150 && i10 > 130 && i11 < i9 - 60) {
                i3++;
                break;
            }
            i8++;
        }
        while (true) {
            if (make_in_range4 > make_in_range5) {
                break;
            }
            int pixel3 = bitmap.getPixel(make_in_range3, make_in_range4);
            int i12 = (pixel3 & 16711680) >> 16;
            int i13 = (pixel3 & 65280) >> 8;
            int i14 = pixel3 & 255;
            if (i12 > 150 && i13 > 130 && i14 < i12 - 60) {
                i3++;
                break;
            }
            make_in_range4++;
        }
        return i3 >= 2;
    }

    public static boolean is_scope_bitmap_pixel_on(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth() - 1;
        int height = bitmap.getHeight() - 1;
        boolean z = false;
        int make_in_range = Units.make_in_range(i + 1, 0, width);
        int make_in_range2 = Units.make_in_range(i2, 0, height);
        int make_in_range3 = Units.make_in_range(i2 + 1, 0, height);
        for (int make_in_range4 = Units.make_in_range(i, 0, width); make_in_range4 <= make_in_range && !z; make_in_range4++) {
            int i3 = make_in_range2;
            while (true) {
                if (i3 <= make_in_range3) {
                    int pixel = bitmap.getPixel(make_in_range4, i3);
                    int i4 = (16711680 & pixel) >> 16;
                    int i5 = (65280 & pixel) >> 8;
                    int i6 = pixel & 255;
                    if (i4 > 150 && i5 > 150 && i6 < i4 - 80) {
                        z = true;
                        break;
                    }
                    i3++;
                }
            }
        }
        return z;
    }
}
