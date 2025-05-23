package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
/* loaded from: classes2.dex */
public class GetDevMD5CmdHandler implements ICmdHandler {
    /* JADX WARN: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0061  */
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.jieli.jl_bt_ota.model.base.CommandBase parseDataToCmd(com.jieli.jl_bt_ota.model.base.BasePacket r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto L4
            return r0
        L4:
            int r1 = r6.getOpCode()
            r2 = 212(0xd4, float:2.97E-43)
            if (r1 == r2) goto Ld
            return r0
        Ld:
            byte[] r0 = r6.getParamData()
            int r1 = r6.getType()
            r2 = 1
            if (r1 != r2) goto L26
            com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd r0 = new com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd
            r0.<init>()
            int r6 = r6.getOpCodeSn()
            com.jieli.jl_bt_ota.model.base.CommandBase r6 = r0.setOpCodeSn(r6)
            return r6
        L26:
            com.jieli.jl_bt_ota.tool.CommandHelper r1 = com.jieli.jl_bt_ota.tool.CommandHelper.getInstance()
            int r2 = r6.getOpCode()
            int r3 = r6.getOpCodeSn()
            com.jieli.jl_bt_ota.model.base.CommandBase r1 = r1.getCommand(r2, r3)
            if (r0 == 0) goto L48
            int r2 = r0.length
            r3 = 32
            if (r2 < r3) goto L48
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L44
            r4 = 0
            r2.<init>(r0, r4, r3)     // Catch: java.lang.Exception -> L44
            goto L4a
        L44:
            r2 = move-exception
            r2.printStackTrace()
        L48:
            java.lang.String r2 = ""
        L4a:
            com.jieli.jl_bt_ota.model.response.GetDevMD5Response r3 = new com.jieli.jl_bt_ota.model.response.GetDevMD5Response
            r3.<init>(r2)
            r3.setRawData(r0)
            if (r1 == 0) goto L61
            com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd r1 = (com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd) r1
            r1.setResponse(r3)
            int r6 = r6.getStatus()
            r1.setStatus(r6)
            return r1
        L61:
            com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd r0 = new com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd
            r0.<init>()
            int r1 = r6.getOpCodeSn()
            r0.setOpCodeSn(r1)
            r0.setResponse(r3)
            int r6 = r6.getStatus()
            r0.setStatus(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.model.cmdHandler.GetDevMD5CmdHandler.parseDataToCmd(com.jieli.jl_bt_ota.model.base.BasePacket):com.jieli.jl_bt_ota.model.base.CommandBase");
    }
}
