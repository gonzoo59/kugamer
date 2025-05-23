package com.baidu.kwgames.bean;

import com.baidu.kwgames.bean.ViewInfo;
/* loaded from: classes.dex */
public class ViewInfo63 {
    public int nviewHeight;
    public int nviewWidth;
    public String selectTag = "";
    public Gun1DTO gun1 = new Gun1DTO();
    public Gun1DTO gun2 = new Gun1DTO();
    public MaskDTO mirrorDTO = new MaskDTO();
    public MaskDTO downDTO = new MaskDTO();
    public MaskDTO squatDTO = new MaskDTO();
    public MaskDTO doubleMirror = new MaskDTO();
    public MaskDTO tipMask = new MaskDTO();

    /* loaded from: classes.dex */
    public static class Gun1DTO {
        public Integer height;
        public Integer selectX;
        public Integer selectY;
        public Integer width;
        public Integer xCus;
        public Integer yCus;

        public void minus_x() {
            if (this.xCus.intValue() > 0) {
                this.xCus = Integer.valueOf(this.xCus.intValue() - 1);
            }
        }

        public void minus_y() {
            if (this.yCus.intValue() > 0) {
                this.yCus = Integer.valueOf(this.yCus.intValue() - 1);
            }
        }

        public void plus_x(int i) {
            if (this.xCus.intValue() + this.width.intValue() + 1 > i) {
                return;
            }
            this.xCus = Integer.valueOf(this.xCus.intValue() + 1);
        }

        public void plus_y(int i) {
            if (this.yCus.intValue() + this.height.intValue() + 1 > i) {
                return;
            }
            this.yCus = Integer.valueOf(this.yCus.intValue() + 1);
        }

        void convert_to_new(ViewInfo.MaskDTO maskDTO) {
            maskDTO.x = this.xCus;
            maskDTO.y = this.yCus;
            maskDTO.width = this.width;
            maskDTO.height = this.height;
        }
    }

    /* loaded from: classes.dex */
    public static class MaskDTO {
        public Integer height;
        public Integer width;
        public Integer x;
        public Integer y;

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

        void convert_to_new(ViewInfo.MaskDTO maskDTO) {
            maskDTO.x = this.x;
            maskDTO.y = this.y;
            maskDTO.width = this.width;
            maskDTO.height = this.height;
        }
    }

    public void convert_to_new(ViewInfo viewInfo) {
        viewInfo.selectTag = this.selectTag;
        this.gun1.convert_to_new(viewInfo.gun1);
        this.gun2.convert_to_new(viewInfo.gun2);
        this.mirrorDTO.convert_to_new(viewInfo.mirrorDTO);
        this.downDTO.convert_to_new(viewInfo.downDTO);
        this.squatDTO.convert_to_new(viewInfo.squatDTO);
        this.doubleMirror.convert_to_new(viewInfo.doubleMirror);
        this.tipMask.convert_to_new(viewInfo.tipMask);
    }
}
