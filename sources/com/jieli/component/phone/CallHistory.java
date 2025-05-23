package com.jieli.component.phone;
/* loaded from: classes2.dex */
public class CallHistory extends ContactBean {
    private long date;
    private int duration;
    private String keySort;
    private String name;
    private int news;
    private String number;
    private String phoneUrl;
    private int type;
    private int via;

    @Override // com.jieli.component.phone.ContactBean
    public void setKeySort(String str) {
        this.keySort = str;
    }

    @Override // com.jieli.component.phone.ContactBean
    public String getKeySort() {
        return this.keySort;
    }

    @Override // com.jieli.component.phone.ContactBean
    public String getName() {
        return this.name;
    }

    @Override // com.jieli.component.phone.ContactBean
    public void setName(String str) {
        this.name = str;
    }

    @Override // com.jieli.component.phone.ContactBean
    public String getNumber() {
        return this.number;
    }

    @Override // com.jieli.component.phone.ContactBean
    public void setNumber(String str) {
        this.number = str;
    }

    @Override // com.jieli.component.phone.ContactBean
    public String getPhoneUrl() {
        return this.phoneUrl;
    }

    @Override // com.jieli.component.phone.ContactBean
    public void setPhoneUrl(String str) {
        this.phoneUrl = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long j) {
        this.date = j;
    }

    public int getNews() {
        return this.news;
    }

    public void setNews(int i) {
        this.news = i;
    }

    public int getVia() {
        return this.via;
    }

    public void setVia(int i) {
        this.via = i;
    }
}
