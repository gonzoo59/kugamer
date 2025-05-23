package com.baidu.kwgames;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class GameSelectActivity extends AppCompatActivity {
    List<GameItemInfo> gameItemInfos;
    private GridView mGridView;
    private int mViewId;

    public static List<PackageInfo> getAllGames(Context context) {
        ArrayList arrayList = new ArrayList();
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            if ((packageInfo.applicationInfo.flags & 1) <= 0) {
                arrayList.add(packageInfo);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_game_select);
        getSupportActionBar().hide();
        this.mViewId = getIntent().getIntExtra("viewId", 0);
        this.mGridView = (GridView) findViewById(R.id.games);
        List<PackageInfo> allGames = getAllGames(getApplication());
        this.gameItemInfos = new ArrayList();
        for (int i = 0; i < allGames.size(); i++) {
            PackageInfo packageInfo = allGames.get(i);
            GameItemInfo gameItemInfo = new GameItemInfo();
            gameItemInfo.setIcon(getPackageManager().getApplicationIcon(packageInfo.applicationInfo));
            gameItemInfo.setLabel(getPackageManager().getApplicationLabel(packageInfo.applicationInfo).toString());
            gameItemInfo.setPackageName(packageInfo.applicationInfo.packageName);
            this.gameItemInfos.add(gameItemInfo);
        }
        this.mGridView.setAdapter((ListAdapter) new baseAdapter());
        this.mGridView.setOnItemClickListener(new ClickListener());
    }

    /* loaded from: classes.dex */
    private class baseAdapter extends BaseAdapter {
        LayoutInflater inflater;

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        private baseAdapter() {
            this.inflater = LayoutInflater.from(GameSelectActivity.this.getApplication());
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return GameSelectActivity.this.gameItemInfos.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.inflater.inflate(R.layout.item_game, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) view.findViewById(R.id.app_icon);
                viewHolder.label = (TextView) view.findViewById(R.id.app_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.icon.setImageDrawable(GameSelectActivity.this.gameItemInfos.get(i).getIcon());
            viewHolder.label.setText(GameSelectActivity.this.gameItemInfos.get(i).getLabel());
            return view;
        }
    }

    /* loaded from: classes.dex */
    private class ViewHolder {
        private ImageView icon;
        private TextView label;

        private ViewHolder() {
        }
    }

    /* loaded from: classes.dex */
    private class ClickListener implements AdapterView.OnItemClickListener {
        private ClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Intent intent = new Intent();
            intent.putExtra("package", GameSelectActivity.this.gameItemInfos.get(i).getPackageName());
            intent.putExtra("viewId", GameSelectActivity.this.mViewId);
            GameSelectActivity.this.setResult(-1, intent);
            GameSelectActivity.this.finish();
        }
    }

    /* loaded from: classes.dex */
    private class GameItemInfo {
        private Drawable icon;
        private String label;
        private String packageName;

        private GameItemInfo() {
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String str) {
            this.label = str;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }
    }
}
