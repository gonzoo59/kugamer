package com.jieli.jl_bt_ota.tool;

import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import java.util.HashMap;
/* loaded from: classes2.dex */
public class CommandHelper {
    private static volatile CommandHelper instance;
    private final HashMap<String, CommandBase> cacheCommandMap = new HashMap<>();

    private CommandHelper() {
    }

    public static CommandHelper getInstance() {
        if (instance == null) {
            synchronized (CommandHelper.class) {
                if (instance == null) {
                    instance = new CommandHelper();
                }
            }
        }
        return instance;
    }

    public void putCommandBase(CommandBase commandBase) {
        if (commandBase != null) {
            if (commandBase.getType() == 2 || commandBase.getType() == 3) {
                this.cacheCommandMap.put(getCacheCommandKey(commandBase.getId(), commandBase.getOpCodeSn()), commandBase);
            }
        }
    }

    public CommandBase getCommand(int i, int i2) {
        return this.cacheCommandMap.get(getCacheCommandKey(i, i2));
    }

    public void removeCommandBase(BasePacket basePacket) {
        if (basePacket != null) {
            removeCommandBase(basePacket.getOpCode(), basePacket.getOpCodeSn());
        }
    }

    public void removeCommandBase(int i, int i2) {
        this.cacheCommandMap.remove(getCacheCommandKey(i, i2));
    }

    public void release() {
        this.cacheCommandMap.clear();
        instance = null;
    }

    public static String getCacheCommandKey(int i, int i2) {
        String valueOf = String.valueOf(i);
        if (i2 >= 0) {
            return valueOf + "-" + i2;
        }
        return valueOf;
    }
}
