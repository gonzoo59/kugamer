package com.baidu.kwgames;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import com.baidu.kwgames.util.MsgBox;
import com.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.opencv.core.Core;
/* loaded from: classes.dex */
public class KeyMappingDisplay extends AbsFloatBase {
    private List<KeyInfo> mCurKeyInfoConfig;
    private ArrayList<DotData> mDotDatas;
    private HashMap<String, View> mKeyMapForUI;
    private LineRelativeLayout mKeyRoot;
    private int m_nCuKeyMap;

    @Override // com.baidu.kwgames.AbsFloatBase
    protected void onAddWindowFailed(Exception exc) {
    }

    public KeyMappingDisplay(Context context) {
        super(context);
        this.mDotDatas = new ArrayList<>();
    }

    @Override // com.baidu.kwgames.AbsFloatBase
    public void create() {
        super.create();
        this.mViewMode = 2;
        inflate(R.layout.key_mapping_display);
        this.mKeyMapForUI = new HashMap<>();
        this.mCurKeyInfoConfig = new ArrayList();
        RelativeLayout relativeLayout = (RelativeLayout) findView(R.id.root);
        LineRelativeLayout lineRelativeLayout = (LineRelativeLayout) findView(R.id.key_root);
        this.mKeyRoot = lineRelativeLayout;
        lineRelativeLayout.setLineColor(-65536);
        this.m_nCuKeyMap = AppInstance.s_nCurKeyMap;
    }

    private void addKey(KeyInfo keyInfo, DotData dotData, int i) {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this.mContext, R.layout.normal_key, null);
        relativeLayout.setTag(R.id.tag_key_info, keyInfo);
        relativeLayout.setTag(R.id.tag_dot_data, dotData);
        relativeLayout.setTag(R.id.tag_dot_index, Integer.valueOf(i));
        relativeLayout.setOnTouchListener(null);
        relativeLayout.setGravity(17);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.image);
        if (keyInfo.imageId == R.mipmap.mouse || keyInfo.imageId == R.mipmap.mouse_l || keyInfo.imageId == R.mipmap.mouse_m || keyInfo.imageId == R.mipmap.mouse_s1 || keyInfo.imageId == R.mipmap.mouse_s2 || keyInfo.imageId == R.mipmap.direction || keyInfo.imageId == R.mipmap.beijing_exchange1) {
            imageView.setImageResource(keyInfo.imageId);
            imageView.setAlpha(1.0f);
        } else if (keyInfo.imageId == R.mipmap.mouse_r) {
            imageView.setImageResource(keyInfo.imageId);
            if (AppInstance.m_boAIWorking) {
                imageView.setAlpha(0.3f);
            } else {
                imageView.setAlpha(1.0f);
            }
        } else if (keyInfo.imageId == R.mipmap.wave || keyInfo.imageId == R.mipmap.beijing_exchange || keyInfo.imageId == R.mipmap.beijing_scale2 || keyInfo.imageId == R.mipmap.beijing_scale) {
            imageView.setImageResource(keyInfo.imageId);
            imageView.setAlpha(0.3f);
        } else {
            imageView.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_display_key));
            imageView.setAlpha(0.3f);
            imageView.setImageResource(keyInfo.imageId);
        }
        if (dotData.mId == -9 || dotData.mId == -12) {
            imageView.setAlpha(0.3f);
        }
        TextView textView = (TextView) relativeLayout.findViewById(R.id.text);
        textView.setText(keyInfo.title);
        textView.setMaxLines(1);
        textView.setAlpha(0.8f);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView, 1, 50, 1, 1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(keyInfo.w, keyInfo.h);
        relativeLayout.setX(keyInfo.x);
        relativeLayout.setY(keyInfo.y);
        this.mKeyRoot.addView(relativeLayout, layoutParams);
        if (this.mKeyMapForUI == null) {
            this.mKeyMapForUI = new HashMap<>();
        }
        this.mKeyMapForUI.put(keyInfo.tag, relativeLayout);
    }

    public void on_ble_get_key_map() {
        new Thread(new Runnable() { // from class: com.baidu.kwgames.KeyMappingDisplay.1
            @Override // java.lang.Runnable
            public void run() {
                KeyMappingDisplay keyMappingDisplay = KeyMappingDisplay.this;
                keyMappingDisplay.loadKey(keyMappingDisplay.m_nCuKeyMap);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadKey(int i) {
        this.mDotDatas = new ArrayList<>();
        byte[] bArr = Units.get_key_map(i);
        if (bArr == null) {
            return;
        }
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2 += 14) {
            DotData dotData = new DotData();
            dotData.parse(bArr, i2);
            this.mDotDatas.add(dotData);
        }
        this.mHandler.post(new Runnable() { // from class: com.baidu.kwgames.KeyMappingDisplay$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyMappingDisplay.this.m18lambda$loadKey$0$combaidukwgamesKeyMappingDisplay();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: insertInUI */
    public void m18lambda$loadKey$0$combaidukwgamesKeyMappingDisplay() {
        char c = 0;
        KeyInfo keyInfo = null;
        int i = 0;
        int i2 = 1;
        while (i < this.mDotDatas.size()) {
            DotData dotData = this.mDotDatas.get(i);
            if (dotData.mType == 0 || dotData.mType == 3 || dotData.mType == 6) {
                KeyInfo keyInfo2 = new KeyInfo();
                keyInfo2.tag = ((int) dotData.mId) + "";
                keyInfo2.id = dotData.mId;
                keyInfo2.type = dotData.mType;
                if (dotData.mId == -12 || dotData.mId == -9) {
                    keyInfo2.x = dotData.mX[0] - ConvertUtils.dp2px(25.0f);
                    keyInfo2.y = dotData.mY[0] - ConvertUtils.dp2px(25.0f);
                    keyInfo2.w = ConvertUtils.dp2px(50.0f);
                    keyInfo2.h = ConvertUtils.dp2px(50.0f);
                    keyInfo2.imageId = dotData.mId == -12 ? R.mipmap.direction : R.mipmap.udlr;
                } else {
                    byte b = dotData.mId;
                    if (b == -11) {
                        keyInfo2.imageId = R.mipmap.mouse_s1;
                    } else if (b == -10) {
                        keyInfo2.imageId = R.mipmap.mouse_s2;
                    } else if (b == -8) {
                        keyInfo2.imageId = R.mipmap.beijing_exchange1;
                    } else if (b == -7) {
                        keyInfo2.imageId = R.mipmap.beijing_scale2;
                    } else if (b != 53) {
                        switch (b) {
                            case Core.BadNumChannel1U /* -16 */:
                                keyInfo2.imageId = R.mipmap.mouse_l;
                                break;
                            case Core.BadNumChannels /* -15 */:
                                keyInfo2.imageId = R.mipmap.mouse_m;
                                break;
                            case Core.BadModelOrChSeq /* -14 */:
                                keyInfo2.imageId = R.mipmap.mouse_r;
                                break;
                            case Core.BadStep /* -13 */:
                                keyInfo2.imageId = R.mipmap.mouse;
                                break;
                        }
                    } else {
                        keyInfo2.imageId = R.mipmap.wave;
                    }
                    if (keyInfo2.imageId == 0) {
                        keyInfo2.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                    }
                    if (AppInstance.m_boAIWorking && (dotData.mId == 30 || dotData.mId == 31)) {
                        keyInfo2.x = dotData.mX[0] - ConvertUtils.dp2px(8.0f);
                        keyInfo2.y = dotData.mY[0] - ConvertUtils.dp2px(8.0f);
                        keyInfo2.w = ConvertUtils.dp2px(16.0f);
                        keyInfo2.h = ConvertUtils.dp2px(16.0f);
                    } else {
                        keyInfo2.x = dotData.mX[0] - ConvertUtils.dp2px(10.0f);
                        keyInfo2.y = dotData.mY[0] - ConvertUtils.dp2px(10.0f);
                        keyInfo2.w = ConvertUtils.dp2px(20.0f);
                        keyInfo2.h = ConvertUtils.dp2px(20.0f);
                    }
                }
                if (dotData.mType == 0) {
                    keyInfo2.property = dotData.mX[2];
                }
                addKey(keyInfo2, dotData, 0);
            } else if (dotData.mType == 2 || (dotData.mType == 1 && (dotData.mId == 53 || dotData.mId == -8))) {
                KeyInfo keyInfo3 = new KeyInfo();
                keyInfo3.tag = ((int) dotData.mId) + "";
                byte b2 = dotData.mId;
                if (b2 == -8) {
                    keyInfo3.imageId = R.mipmap.beijing_exchange;
                } else if (b2 == 53) {
                    keyInfo3.imageId = R.mipmap.wave;
                }
                if (keyInfo3.imageId == 0) {
                    keyInfo3.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                }
                keyInfo3.id = dotData.mId;
                keyInfo3.type = dotData.mType;
                keyInfo3.x = dotData.mX[0] - ConvertUtils.dp2px(10.0f);
                keyInfo3.y = dotData.mY[0] - ConvertUtils.dp2px(10.0f);
                keyInfo3.w = ConvertUtils.dp2px(20.0f);
                keyInfo3.h = ConvertUtils.dp2px(20.0f);
                new KeyInfo();
                this.mCurKeyInfoConfig.add(keyInfo3);
                addKey(keyInfo3, dotData, 0);
            } else if (dotData.mType == 4 || dotData.mType == 5) {
                KeyInfo keyInfo4 = new KeyInfo();
                keyInfo4.title = Constants.ID2String.get(Byte.valueOf(dotData.mId));
                if (keyInfo == null) {
                    keyInfo = keyInfo4;
                }
                keyInfo4.tag = keyInfo4.title + i2;
                int i3 = i2 + 1;
                keyInfo4.id = dotData.mId;
                keyInfo4.type = dotData.mType;
                keyInfo4.x = dotData.mX[c] - ConvertUtils.dp2px(10.0f);
                keyInfo4.y = dotData.mY[c] - ConvertUtils.dp2px(10.0f);
                keyInfo4.w = ConvertUtils.dp2px(20.0f);
                keyInfo4.h = ConvertUtils.dp2px(20.0f);
                KeyInfo keyInfo5 = new KeyInfo();
                keyInfo5.title = i3 + "";
                if (dotData.mType == 5) {
                    keyInfo5.title = "";
                }
                keyInfo5.tag = Constants.ID2String.get(Byte.valueOf(dotData.mId)) + i3;
                int i4 = i3 + 1;
                keyInfo5.id = dotData.mId;
                keyInfo5.type = dotData.mType;
                keyInfo5.x = dotData.mX[1] - ConvertUtils.dp2px(10.0f);
                keyInfo5.y = dotData.mY[1] - ConvertUtils.dp2px(10.0f);
                keyInfo5.w = ConvertUtils.dp2px(20.0f);
                keyInfo5.h = ConvertUtils.dp2px(20.0f);
                KeyInfo keyInfo6 = new KeyInfo();
                keyInfo6.title = i4 + "";
                if (dotData.mType == 5) {
                    keyInfo6.title = this.mContext.getString(R.string.end);
                }
                keyInfo6.tag = Constants.ID2String.get(Byte.valueOf(dotData.mId)) + i4;
                i2 = i4 + 1;
                keyInfo6.id = dotData.mId;
                keyInfo6.type = dotData.mType;
                keyInfo6.x = dotData.mX[2] - ConvertUtils.dp2px(10.0f);
                keyInfo6.y = dotData.mY[2] - ConvertUtils.dp2px(10.0f);
                keyInfo6.w = ConvertUtils.dp2px(20.0f);
                keyInfo6.h = ConvertUtils.dp2px(20.0f);
                addKey(keyInfo4, dotData, 0);
                keyInfo.property++;
            }
            i++;
            c = 0;
        }
        if (AppInstance.m_boAIWorking) {
            MsgBox.msg_box1_float(this.mContext, this.mContext.getString(R.string.display_key_remind_while_ai));
        }
    }
}
