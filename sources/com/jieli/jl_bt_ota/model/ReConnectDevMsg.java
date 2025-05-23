package com.jieli.jl_bt_ota.model;

import java.util.Objects;
/* loaded from: classes2.dex */
public class ReConnectDevMsg {
    private String address;
    private int way;

    public ReConnectDevMsg(int i, String str) {
        setWay(i).setAddress(str);
    }

    public int getWay() {
        return this.way;
    }

    public ReConnectDevMsg setWay(int i) {
        this.way = i;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    public ReConnectDevMsg setAddress(String str) {
        this.address = str;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReConnectDevMsg reConnectDevMsg = (ReConnectDevMsg) obj;
        return this.way == reConnectDevMsg.way && Objects.equals(this.address, reConnectDevMsg.address);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.way), this.address);
    }

    public String toString() {
        return "ReConnectDevMsg{way=" + this.way + ", address='" + this.address + "'}";
    }

    public ReConnectDevMsg cloneObject() {
        return new ReConnectDevMsg(this.way, this.address);
    }
}
