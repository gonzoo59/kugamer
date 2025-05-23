package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.parameter.NotifyCommunicationWayParam;
/* loaded from: classes2.dex */
public class NotifyCommunicationWayCmd extends CommandWithParamAndResponse<NotifyCommunicationWayParam, CommonResponse> {
    public NotifyCommunicationWayCmd(NotifyCommunicationWayParam notifyCommunicationWayParam) {
        super(11, "NotifyCommunicationWayCmd", notifyCommunicationWayParam);
    }
}
