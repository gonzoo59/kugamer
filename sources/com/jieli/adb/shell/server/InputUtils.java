package com.jieli.adb.shell.server;

import android.os.SystemClock;
import android.view.MotionEvent;
/* loaded from: assets/adb/sincoserver.dex */
class InputUtils {
    private static final int DEVICE_ID = -1;
    private static final int POINT_COUNT = 6;
    MotionEvent.PointerProperties[] m_arrPointProp = new MotionEvent.PointerProperties[6];
    MotionEvent.PointerCoords[] m_arrPointXY = new MotionEvent.PointerCoords[6];
    private ImInjectHelper inputManager = new ImInjectHelper();

    /* JADX INFO: Access modifiers changed from: package-private */
    public InputUtils() {
        for (int i = 0; i < 6; i++) {
            this.m_arrPointProp[i] = new MotionEvent.PointerProperties();
            this.m_arrPointProp[i].toolType = 1;
            this.m_arrPointXY[i] = new MotionEvent.PointerCoords();
            this.m_arrPointXY[i].pressure = 1.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void action_down(int i, int i2, int i3) {
        this.m_arrPointProp[0].id = i;
        MotionEvent.PointerCoords[] pointerCoordsArr = this.m_arrPointXY;
        pointerCoordsArr[0].x = i2;
        pointerCoordsArr[0].y = i3;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.inputManager.injectInputEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 1, this.m_arrPointProp, this.m_arrPointXY, 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void action_up(int i, int i2, int i3) {
        this.m_arrPointProp[0].id = i;
        MotionEvent.PointerCoords[] pointerCoordsArr = this.m_arrPointXY;
        pointerCoordsArr[0].x = i2;
        pointerCoordsArr[0].y = i3;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.inputManager.injectInputEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 1, 1, this.m_arrPointProp, this.m_arrPointXY, 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0));
    }

    MotionEvent.PointerProperties[] get_action_prop(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            if (((1 << i3) & i) != 0) {
                this.m_arrPointProp[i2].id = i3;
                i2++;
            }
        }
        return this.m_arrPointProp;
    }

    MotionEvent.PointerCoords[] get_action_xy(int i, int[] iArr, int[] iArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            if (((1 << i3) & i) != 0) {
                MotionEvent.PointerCoords[] pointerCoordsArr = this.m_arrPointXY;
                pointerCoordsArr[i2].x = iArr[i3];
                pointerCoordsArr[i2].y = iArr2[i3];
                i2++;
            }
        }
        return this.m_arrPointXY;
    }

    MotionEvent.PointerCoords[] get_action_up_xy(int i, int[] iArr, int[] iArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            if (((1 << i3) & i) != 0) {
                MotionEvent.PointerCoords[] pointerCoordsArr = this.m_arrPointXY;
                pointerCoordsArr[i2].x = iArr[i3];
                pointerCoordsArr[i2].y = iArr2[i3];
                i2++;
            }
        }
        return this.m_arrPointXY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pointer_down(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.inputManager.injectInputEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, (i3 << 8) | 5, i, get_action_prop(i2), get_action_xy(i2, iArr, iArr2), 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pointer_up(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.inputManager.injectInputEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, (i3 << 8) | 6, i, get_action_prop(i2), get_action_up_xy(i2, iArr, iArr2), 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void move(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.inputManager.injectInputEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, (i3 << 8) | 2, i, get_action_prop(i2), get_action_xy(i2, iArr, iArr2), 0, 0, 1.0f, 1.0f, -1, 0, 4098, 0));
    }
}
