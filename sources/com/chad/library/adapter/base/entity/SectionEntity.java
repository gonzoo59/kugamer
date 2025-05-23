package com.chad.library.adapter.base.entity;

import kotlin.Metadata;
/* compiled from: SectionEntity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u0000 \t2\u00020\u0001:\u0001\tR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lcom/chad/library/adapter/base/entity/SectionEntity;", "Lcom/chad/library/adapter/base/entity/MultiItemEntity;", "isHeader", "", "()Z", "itemType", "", "getItemType", "()I", "Companion", "com.github.CymChad.brvah"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes.dex */
public interface SectionEntity extends MultiItemEntity {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int HEADER_TYPE = -99;
    public static final int NORMAL_TYPE = -100;

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    int getItemType();

    boolean isHeader();

    /* compiled from: SectionEntity.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes.dex */
    public static final class DefaultImpls {
        public static int getItemType(SectionEntity sectionEntity) {
            return sectionEntity.isHeader() ? -99 : -100;
        }
    }

    /* compiled from: SectionEntity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/chad/library/adapter/base/entity/SectionEntity$Companion;", "", "()V", "HEADER_TYPE", "", "NORMAL_TYPE", "com.github.CymChad.brvah"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes.dex */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int HEADER_TYPE = -99;
        public static final int NORMAL_TYPE = -100;

        private Companion() {
        }
    }
}
