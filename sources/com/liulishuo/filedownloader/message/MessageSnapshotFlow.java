package com.liulishuo.filedownloader.message;
/* loaded from: classes2.dex */
public class MessageSnapshotFlow {
    private volatile MessageSnapshotThreadPool flowThreadPool;
    private volatile MessageReceiver receiver;

    /* loaded from: classes2.dex */
    public interface MessageReceiver {
        void receive(MessageSnapshot messageSnapshot);
    }

    /* loaded from: classes2.dex */
    public static final class HolderClass {
        private static final MessageSnapshotFlow INSTANCE = new MessageSnapshotFlow();
    }

    public static MessageSnapshotFlow getImpl() {
        return HolderClass.INSTANCE;
    }

    public void setReceiver(MessageReceiver messageReceiver) {
        this.receiver = messageReceiver;
        if (messageReceiver == null) {
            this.flowThreadPool = null;
        } else {
            this.flowThreadPool = new MessageSnapshotThreadPool(5, messageReceiver);
        }
    }

    public void inflow(MessageSnapshot messageSnapshot) {
        if (messageSnapshot instanceof IFlowDirectly) {
            if (this.receiver != null) {
                this.receiver.receive(messageSnapshot);
            }
        } else if (this.flowThreadPool != null) {
            this.flowThreadPool.execute(messageSnapshot);
        }
    }
}
