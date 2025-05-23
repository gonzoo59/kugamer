package com.baidu.kwgames.bean;
/* loaded from: classes.dex */
public class ViewInfo {
    public String selectTag = "";
    public MaskDTO gun1 = new MaskDTO();
    public MaskDTO gun2 = new MaskDTO();
    public MaskDTO mirrorDTO = new MaskDTO();
    public MaskDTO downDTO = new MaskDTO();
    public MaskDTO squatDTO = new MaskDTO();
    public MaskDTO doubleMirror = new MaskDTO();
    public MaskDTO tipMask = new MaskDTO();
    public MaskDTO bagTag = new MaskDTO();
    public MaskDTO gun1Head = new MaskDTO();
    public MaskDTO gun2Head = new MaskDTO();
    public MaskDTO gun1Handle = new MaskDTO();
    public MaskDTO gun2Handle = new MaskDTO();
    public MaskDTO gun1Tail = new MaskDTO();
    public MaskDTO gun2Tail = new MaskDTO();
    public MaskDTO takeOffTag = new MaskDTO();

    /* loaded from: classes.dex */
    public static class MaskDTO {
        public Integer height;
        public Integer width;
        public Integer x;
        public Integer y;

        MaskDTO() {
            this.y = 100;
            this.x = 100;
            this.height = 100;
            this.width = 100;
        }

        MaskDTO(int i, int i2, int i3, int i4) {
            this.x = Integer.valueOf(i);
            this.y = Integer.valueOf(i2);
            this.width = Integer.valueOf(i3);
            this.height = Integer.valueOf(i4);
        }

        public void set(int i, int i2, int i3, int i4) {
            this.x = Integer.valueOf(i);
            this.y = Integer.valueOf(i2);
            this.width = Integer.valueOf(i3);
            this.height = Integer.valueOf(i4);
        }

        public void minus_x() {
            if (this.x.intValue() > 0) {
                this.x = Integer.valueOf(this.x.intValue() - 1);
            }
        }

        public void minus_y() {
            if (this.y.intValue() > 0) {
                this.y = Integer.valueOf(this.y.intValue() - 1);
            }
        }

        public void plus_x(int i) {
            if (this.x.intValue() + this.width.intValue() + 1 > i) {
                return;
            }
            this.x = Integer.valueOf(this.x.intValue() + 1);
        }

        public void plus_y(int i) {
            if (this.y.intValue() + this.height.intValue() + 1 > i) {
                return;
            }
            this.y = Integer.valueOf(this.y.intValue() + 1);
        }
    }
}
