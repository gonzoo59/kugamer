package com.jieli.component.phone;

import java.util.List;
/* loaded from: classes2.dex */
public class ContactBean {
    private List<CallHistory> histories;
    private String keySort;
    private String name;
    private String number;
    private String phoneUrl;
    private PinyinBean pinyinBean;

    public void setPinyinBean(PinyinBean pinyinBean) {
        this.pinyinBean = pinyinBean;
    }

    public PinyinBean getPinyinBean() {
        return this.pinyinBean;
    }

    public void setHistories(List<CallHistory> list) {
        this.histories = list;
    }

    public List<CallHistory> getHistories() {
        return this.histories;
    }

    public void setKeySort(String str) {
        this.keySort = str;
    }

    public String getKeySort() {
        return this.keySort;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public String getPhoneUrl() {
        return this.phoneUrl;
    }

    public void setPhoneUrl(String str) {
        this.phoneUrl = str;
    }

    public String toString() {
        return "Contacts{name='" + this.name + "', number='" + this.number + "', phoneUrl='" + this.phoneUrl + "', keySort='" + this.keySort + "', pinyinBean='" + this.pinyinBean + "', histories=" + this.histories + '}';
    }
}
