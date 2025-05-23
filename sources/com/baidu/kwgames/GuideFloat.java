package com.baidu.kwgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import java.io.IOException;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class GuideFloat extends AbsFloatBase {
    private BGABanner mBanner;
    private Context mContext;
    private String mPackageName;

    @Override // com.baidu.kwgames.AbsFloatBase
    protected void onAddWindowFailed(Exception exc) {
    }

    public GuideFloat(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public void create() {
        super.create();
        this.mViewMode = 1;
        inflate(R.layout.dialog_guide);
        findView(R.id.root);
        this.mBanner = (BGABanner) findView(R.id.banner);
        final CheckBox checkBox = (CheckBox) findView(R.id.checkbox);
        ((Button) findView(R.id.close_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GuideFloat.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    SPUtils sPUtils = SPUtils.getInstance();
                    sPUtils.put(GuideFloat.this.mPackageName + "-no-tip", 1);
                }
                GuideFloat.this.closeUi();
            }
        });
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public synchronized void show() {
        super.show();
        getImageList();
    }

    private void getImageList() {
        String str = "guide/" + Constants.find_guide_for_app(this.mPackageName);
        ArrayList arrayList = new ArrayList();
        try {
            String[] list = this.mContext.getAssets().list(str);
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].toLowerCase();
                if (-1 != list[i].indexOf(".jpg") || -1 != list[i].indexOf(".png")) {
                    arrayList.add(BitmapFactory.decodeStream(this.mContext.getAssets().open(str + "/" + list[i])));
                }
            }
            this.mBanner.setAdapter(new BGABanner.Adapter<ImageView, Bitmap>() { // from class: com.baidu.kwgames.GuideFloat.2
                @Override // cn.bingoogolapple.bgabanner.BGABanner.Adapter
                public void fillBannerItem(BGABanner bGABanner, ImageView imageView, Bitmap bitmap, int i2) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                    Glide.with(GuideFloat.this.mContext).load(bitmap).into(imageView);
                }
            });
            this.mBanner.setData(arrayList, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeUi() {
        remove();
    }
}
