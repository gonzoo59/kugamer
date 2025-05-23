package com.chad.library.adapter.base.entity;

import com.chad.library.adapter.base.entity.SectionEntity;
/* loaded from: classes.dex */
public abstract class JSectionEntity implements SectionEntity {
    @Override // com.chad.library.adapter.base.entity.SectionEntity, com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        if (isHeader()) {
            SectionEntity.Companion companion = SectionEntity.Companion;
            return -99;
        }
        SectionEntity.Companion companion2 = SectionEntity.Companion;
        return -100;
    }
}
