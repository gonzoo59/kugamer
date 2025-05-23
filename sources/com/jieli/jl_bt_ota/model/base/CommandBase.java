package com.jieli.jl_bt_ota.model.base;
/* loaded from: classes2.dex */
public class CommandBase {
    public static final int FLAG_HAVE_PARAMETER_AND_RESPONSE = 2;
    public static final int FLAG_HAVE_PARAMETER_NO_RESPONSE = 1;
    public static final int FLAG_NO_PARAMETER_AND_RESPONSE = 0;
    public static final int FLAG_NO_PARAMETER_HAVE_RESPONSE = 3;
    private int OpCodeSn;
    private String name;
    private int opCode;
    private int status;
    private int type;

    public CommandBase(int i, String str) {
        this.opCode = i;
        this.name = str;
        setType(0);
    }

    public CommandBase setOpCodeSn(int i) {
        this.OpCodeSn = i;
        return this;
    }

    public int getOpCodeSn() {
        return this.OpCodeSn;
    }

    public int getId() {
        return this.opCode;
    }

    public CommandBase setId(int i) {
        this.opCode = i;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CommandBase setName(String str) {
        this.name = str;
        return this;
    }

    public int getType() {
        return this.type;
    }

    public CommandBase setType(int i) {
        this.type = i;
        return this;
    }

    public int getStatus() {
        return this.status;
    }

    public CommandBase setStatus(int i) {
        this.status = i;
        return this;
    }

    protected boolean equal(CommandBase commandBase) {
        String str;
        return commandBase != null && this.OpCodeSn == commandBase.OpCodeSn && this.opCode == commandBase.opCode && (str = this.name) != null && str.equals(commandBase.name);
    }

    public String toString() {
        return "CommandBase{OpCodeSn=" + this.OpCodeSn + ", opCode=" + this.opCode + ", name='" + this.name + "', type=" + this.type + ", status=" + this.status + '}';
    }
}
