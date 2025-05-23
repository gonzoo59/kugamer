package com.jieli.adb.shell.server;
/* loaded from: assets/adb/sincoserver.dex */
public class CPointsInfo {
    public static final int POINT_COUNT = 6;
    public static final int POINT_INVALID_XY = 20000;
    private InputUtils mInputUtils;
    public int nPointCount = 0;
    public int nPointMask = 0;
    public int[] arrPointX = new int[6];
    public int[] arrPointY = new int[6];

    public CPointsInfo() {
        this.mInputUtils = null;
        clear();
        this.mInputUtils = new InputUtils();
    }

    public void set_point_xy(int i, int i2, int i3) {
        this.arrPointX[i] = i2;
        this.arrPointY[i] = i3;
        update_info();
    }

    public void clear_point_xy(int i) {
        int[] iArr = this.arrPointX;
        this.arrPointY[i] = 20000;
        iArr[i] = 20000;
        update_info();
    }

    public void clear() {
        for (int i = 0; i < 6; i++) {
            int[] iArr = this.arrPointX;
            this.arrPointY[i] = 20000;
            iArr[i] = 20000;
        }
        this.nPointCount = 0;
        this.nPointMask = 0;
    }

    public void update_info() {
        this.nPointMask = 0;
        this.nPointCount = 0;
        for (int i = 0; i < 6; i++) {
            if (20000 != this.arrPointX[i]) {
                this.nPointMask |= 1 << i;
                this.nPointCount++;
            }
        }
    }

    public int calc_event_index(int i) {
        int i2 = -1;
        for (int i3 = 0; i3 <= i; i3++) {
            if ((this.nPointMask & (1 << i3)) != 0) {
                i2++;
            }
        }
        return i2;
    }

    public void point_down(int i, int i2, int i3) {
        if (this.nPointCount == 0) {
            set_point_xy(i, i2, i3);
            this.mInputUtils.action_down(i, i2, i3);
            return;
        }
        set_point_xy(i, i2, i3);
        this.mInputUtils.pointer_down(this.nPointCount, this.nPointMask, calc_event_index(i), this.arrPointX, this.arrPointY);
    }

    public void point_move(int i, int i2, int i3) {
        set_point_xy(i, i2, i3);
        this.mInputUtils.move(this.nPointCount, this.nPointMask, calc_event_index(i), this.arrPointX, this.arrPointY);
    }

    public void point_up(int i, int i2, int i3) {
        int i4 = this.nPointCount;
        if (i4 > 1) {
            this.mInputUtils.pointer_up(i4, this.nPointMask, calc_event_index(i), this.arrPointX, this.arrPointY);
            clear_point_xy(i);
            return;
        }
        this.mInputUtils.action_up(i, i2, i3);
        clear();
    }

    public void all_point_up() {
        for (int i = 0; i < 6; i++) {
            if ((this.nPointMask & (1 << i)) != 0) {
                point_up(i, this.arrPointX[i], this.arrPointY[i]);
            }
        }
    }
}
