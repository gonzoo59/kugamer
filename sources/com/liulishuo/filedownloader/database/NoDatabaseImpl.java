package com.liulishuo.filedownloader.database;

import android.util.SparseArray;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes2.dex */
public class NoDatabaseImpl implements FileDownloadDatabase {
    final SparseArray<FileDownloadModel> downloaderModelMap = new SparseArray<>();
    final SparseArray<List<ConnectionModel>> connectionModelListMap = new SparseArray<>();

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void onTaskStart(int i) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateConnected(int i, long j, String str, String str2) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateConnectionCount(int i, int i2) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateError(int i, Throwable th, long j) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateOldEtagOverdue(int i, String str, long j, long j2, int i2) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updatePause(int i, long j) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updatePending(int i) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateProgress(int i, long j) {
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateRetry(int i, Throwable th) {
    }

    public static Maker createMaker() {
        return new Maker();
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public FileDownloadModel find(int i) {
        FileDownloadModel fileDownloadModel;
        synchronized (this.downloaderModelMap) {
            fileDownloadModel = this.downloaderModelMap.get(i);
        }
        return fileDownloadModel;
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public List<ConnectionModel> findConnectionModel(int i) {
        List<ConnectionModel> list;
        ArrayList arrayList = new ArrayList();
        synchronized (this.connectionModelListMap) {
            list = this.connectionModelListMap.get(i);
        }
        if (list != null) {
            arrayList.addAll(list);
        }
        return arrayList;
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void removeConnections(int i) {
        synchronized (this.connectionModelListMap) {
            this.connectionModelListMap.remove(i);
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void insertConnectionModel(ConnectionModel connectionModel) {
        int id = connectionModel.getId();
        synchronized (this.connectionModelListMap) {
            List<ConnectionModel> list = this.connectionModelListMap.get(id);
            if (list == null) {
                list = new ArrayList<>();
                this.connectionModelListMap.put(id, list);
            }
            list.add(connectionModel);
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateConnectionModel(int i, int i2, long j) {
        synchronized (this.connectionModelListMap) {
            List<ConnectionModel> list = this.connectionModelListMap.get(i);
            if (list == null) {
                return;
            }
            for (ConnectionModel connectionModel : list) {
                if (connectionModel.getIndex() == i2) {
                    connectionModel.setCurrentOffset(j);
                    return;
                }
            }
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void insert(FileDownloadModel fileDownloadModel) {
        synchronized (this.downloaderModelMap) {
            this.downloaderModelMap.put(fileDownloadModel.getId(), fileDownloadModel);
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void update(FileDownloadModel fileDownloadModel) {
        if (fileDownloadModel == null) {
            FileDownloadLog.w(this, "update but model == null!", new Object[0]);
        } else if (find(fileDownloadModel.getId()) != null) {
            synchronized (this.downloaderModelMap) {
                this.downloaderModelMap.remove(fileDownloadModel.getId());
                this.downloaderModelMap.put(fileDownloadModel.getId(), fileDownloadModel);
            }
        } else {
            insert(fileDownloadModel);
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public boolean remove(int i) {
        synchronized (this.downloaderModelMap) {
            this.downloaderModelMap.remove(i);
        }
        return true;
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void clear() {
        synchronized (this.downloaderModelMap) {
            this.downloaderModelMap.clear();
        }
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public void updateCompleted(int i, long j) {
        remove(i);
    }

    @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase
    public FileDownloadDatabase.Maintainer maintainer() {
        return new Maintainer();
    }

    /* loaded from: classes2.dex */
    class Maintainer implements FileDownloadDatabase.Maintainer {
        @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer
        public void changeFileDownloadModelId(int i, FileDownloadModel fileDownloadModel) {
        }

        @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer
        public void onFinishMaintain() {
        }

        @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer
        public void onRefreshedValidData(FileDownloadModel fileDownloadModel) {
        }

        @Override // com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer
        public void onRemovedInvalidData(FileDownloadModel fileDownloadModel) {
        }

        Maintainer() {
        }

        @Override // java.lang.Iterable
        public Iterator<FileDownloadModel> iterator() {
            return new MaintainerIterator();
        }
    }

    /* loaded from: classes2.dex */
    class MaintainerIterator implements Iterator<FileDownloadModel> {
        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public FileDownloadModel next() {
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
        }

        MaintainerIterator() {
        }
    }

    /* loaded from: classes2.dex */
    public static class Maker implements FileDownloadHelper.DatabaseCustomMaker {
        @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.DatabaseCustomMaker
        public FileDownloadDatabase customMake() {
            return new NoDatabaseImpl();
        }
    }
}
