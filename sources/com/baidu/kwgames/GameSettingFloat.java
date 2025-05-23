package com.baidu.kwgames;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.baidu.kwgames.GameSettingFloat;
import com.baidu.kwgames.util.FloatCallbackDragRemIni;
import com.baidu.kwgames.util.FloatMgr;
import com.baidu.kwgames.util.NEAT;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;
/* loaded from: classes.dex */
public class GameSettingFloat {
    public static final String FLOAT_X = "GameSetX";
    public static final String FLOAT_Y = "GameSetY";
    public static final int GAME_DATA_LEN = 50;
    public static final String SETTING_STATUS = "status";
    public static final String STR_VIDEO_URL = "https://www.bilibili.com/video/BV11n4y197gJ";
    public static final String TAG_ATTR_DATA = "data";
    public static final String TAG_ATTR_GAME = "game";
    private static String TAG_FLOAT = "GameSetting";
    public static final String TAG_GAME_INFO = "gameinfo";
    public static final String TAG_GAME_INFO_FILE_NAME = "/gameinfo.xml";
    byte[] m_arrCurGameData;
    boolean m_boParamChanged;
    Context m_context;
    LinearLayout m_layout235lianfaMore;
    LinearLayout m_layoutAutoReverseRecover;
    LinearLayout m_layoutAutoReverseRecoverTime;
    LinearLayout m_layoutVMouseContinuesMore;
    int m_n3liandianDownMs;
    int m_nMouseContinuesSpeed;
    int m_nby3liandianUpMs;
    IThrone m_service;
    String m_strCurGameAPPID;
    Switch m_sw235lianffa;
    Switch m_swCursorContinuesShoot;
    Switch m_swMouseContinuesDisableWhileCtrlDown;
    TextView m_textContinuesSpeed;
    TextView m_textRecoverTime;
    TextView m_textShootStopTime;
    TextView m_textShootTime;
    boolean m_boAutoScopeOff = false;
    boolean m_boReverse = false;
    boolean m_boReverseAutoRecover = true;
    int m_nReverseAutoRecoverTime = 20;
    public Map<String, byte[]> m_mapGameToParam = new HashMap();
    byte[] m_arrEmptyData = new byte[16];
    int m_nFloatX = 0;
    int m_nFloatY = 0;

    byte[] create_new_param() {
        byte[] bArr = new byte[50];
        bArr[0] = 0;
        bArr[5] = 50;
        bArr[6] = 60;
        bArr[7] = 10;
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GameSettingFloat(Context context, IThrone iThrone) {
        this.m_context = context;
        this.m_service = iThrone;
        load();
    }

    byte[] get_game_param(String str) {
        byte[] bArr = this.m_mapGameToParam.get(str);
        if (bArr == null) {
            byte[] create_new_param = create_new_param();
            this.m_mapGameToParam.put(str, create_new_param);
            return create_new_param;
        }
        return bArr;
    }

    void update_UI() {
        boolean is_bit_on = Units.is_bit_on(this.m_arrCurGameData[0], 0);
        if (!AppInstance.s_strGamePackageName.contains(Constants.E_GAME_NAME_WMCQ) && !is_bit_on) {
            this.m_swCursorContinuesShoot.setVisibility(8);
            this.m_layoutVMouseContinuesMore.setVisibility(8);
        } else {
            this.m_swCursorContinuesShoot.setChecked(is_bit_on);
            this.m_swMouseContinuesDisableWhileCtrlDown.setChecked(Units.is_bit_on(this.m_arrCurGameData[0], 1));
            this.m_layoutVMouseContinuesMore.setVisibility(is_bit_on ? 0 : 8);
            TextView textView = this.m_textContinuesSpeed;
            textView.setText("" + this.m_nMouseContinuesSpeed);
        }
        boolean is_bit_on2 = Units.is_bit_on(this.m_arrCurGameData[0], 2);
        this.m_sw235lianffa.setChecked(is_bit_on2);
        this.m_layout235lianfaMore.setVisibility(is_bit_on2 ? 0 : 8);
        TextView textView2 = this.m_textShootTime;
        textView2.setText("" + (this.m_n3liandianDownMs * 5));
        TextView textView3 = this.m_textShootStopTime;
        textView3.setText("" + (this.m_nby3liandianUpMs * 5));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void bleSendParams(String str) {
        try {
            byte[] bArr = this.m_mapGameToParam.get(str);
            if (bArr == null) {
                bArr = this.m_arrEmptyData;
            }
            int length = this.m_arrEmptyData.length + 4;
            byte[] bArr2 = new byte[length];
            bArr2[0] = -1;
            bArr2[1] = -105;
            int i = length - 4;
            bArr2[2] = (byte) i;
            bArr2[3] = 0;
            System.arraycopy(bArr, 0, bArr2, 4, i);
            IThrone iThrone = this.m_service;
            if (iThrone != null) {
                iThrone.bleSendShortData(bArr2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void onUIParamChange() {
        byte[] bArr = this.m_arrCurGameData;
        bArr[5] = (byte) (this.m_nMouseContinuesSpeed & 255);
        bArr[6] = (byte) (this.m_n3liandianDownMs & 255);
        bArr[7] = (byte) (this.m_nby3liandianUpMs & 255);
        this.m_boParamChanged = true;
        bleSendParams(this.m_strCurGameAPPID);
        update_UI();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void show_float(String str) {
        if (EasyFloat.getAppFloatView(TAG_FLOAT) != null) {
            EasyFloat.dismissAppFloat(TAG_FLOAT);
            return;
        }
        this.m_strCurGameAPPID = str;
        byte[] bArr = get_game_param(str);
        this.m_arrCurGameData = bArr;
        this.m_nMouseContinuesSpeed = bArr[5];
        this.m_n3liandianDownMs = bArr[6];
        this.m_nby3liandianUpMs = bArr[7];
        this.m_nFloatX = SPUtils.getInstance().getInt("GameSetX", 100);
        this.m_nFloatY = SPUtils.getInstance().getInt("GameSetY", 100);
        EasyFloat.Builder layout = EasyFloat.with(AppInstance.get()).setTag(TAG_FLOAT).setGravity(17).setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(true).setAnimator(null).setLayout(R.layout.float_game_setting, new AnonymousClass1());
        layout.registerCallbacks(new FloatCallbackDragRemIni("GameSetX", "GameSetY"));
        layout.setLocation(this.m_nFloatX, this.m_nFloatY);
        layout.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.baidu.kwgames.GameSettingFloat$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements OnInvokeView {
        AnonymousClass1() {
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            GameSettingFloat.this.m_boParamChanged = false;
            ((TextView) view.findViewById(R.id.m_textTitle)).setText(NEAT.s(R.string.game_setting_title) + Units.getAppName(GameSettingFloat.this.m_context, GameSettingFloat.this.m_strCurGameAPPID));
            GameSettingFloat.this.m_layoutVMouseContinuesMore = (LinearLayout) view.findViewById(R.id.m_layoutVMouseContinuesMore);
            GameSettingFloat.this.m_layout235lianfaMore = (LinearLayout) view.findViewById(R.id.m_layout235lianfaMore);
            GameSettingFloat.this.m_textContinuesSpeed = (TextView) view.findViewById(R.id.m_textContinuesSpeed);
            GameSettingFloat.this.m_textShootTime = (TextView) view.findViewById(R.id.m_textShootTime);
            GameSettingFloat.this.m_textShootStopTime = (TextView) view.findViewById(R.id.m_textShootStopTime);
            GameSettingFloat.this.m_swCursorContinuesShoot = (Switch) view.findViewById(R.id.m_swCursorContinuesShoot);
            GameSettingFloat.this.m_swMouseContinuesDisableWhileCtrlDown = (Switch) view.findViewById(R.id.m_swMouseContinuesDisableWhileCtrlDown);
            GameSettingFloat.this.m_sw235lianffa = (Switch) view.findViewById(R.id.m_sw235lianffa);
            GameSettingFloat.this.update_UI();
            view.findViewById(R.id.close_window).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m11lambda$invoke$0$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnQuestion).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.lambda$invoke$1(view2);
                }
            });
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat.1.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (GameSettingFloat.this.m_swCursorContinuesShoot == view2) {
                        GameSettingFloat.this.m_arrCurGameData[0] = Units.set_bit(GameSettingFloat.this.m_arrCurGameData[0], 0, GameSettingFloat.this.m_swCursorContinuesShoot.isChecked());
                    } else if (GameSettingFloat.this.m_swMouseContinuesDisableWhileCtrlDown == view2) {
                        GameSettingFloat.this.m_arrCurGameData[0] = Units.set_bit(GameSettingFloat.this.m_arrCurGameData[0], 1, GameSettingFloat.this.m_swMouseContinuesDisableWhileCtrlDown.isChecked());
                    } else if (GameSettingFloat.this.m_sw235lianffa == view2) {
                        GameSettingFloat.this.m_arrCurGameData[0] = Units.set_bit(GameSettingFloat.this.m_arrCurGameData[0], 2, GameSettingFloat.this.m_sw235lianffa.isChecked());
                    }
                    GameSettingFloat.this.onUIParamChange();
                }
            };
            GameSettingFloat.this.m_swCursorContinuesShoot.setOnClickListener(onClickListener);
            GameSettingFloat.this.m_swMouseContinuesDisableWhileCtrlDown.setOnClickListener(onClickListener);
            GameSettingFloat.this.m_sw235lianffa.setOnClickListener(onClickListener);
            view.findViewById(R.id.m_btnContinuesSpeedMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m12lambda$invoke$2$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnContinuesSpeedPlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m13lambda$invoke$3$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnShootTimeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m14lambda$invoke$4$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnShootTimePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m15lambda$invoke$5$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnShootStopTimeMinus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m16lambda$invoke$6$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            view.findViewById(R.id.m_btnShootStopTimePlus).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.GameSettingFloat$1$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GameSettingFloat.AnonymousClass1.this.m17lambda$invoke$7$combaidukwgamesGameSettingFloat$1(view2);
                }
            });
            FloatMgr.resetVirtualMouse();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$0$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m11lambda$invoke$0$combaidukwgamesGameSettingFloat$1(View view) {
            EasyFloat.dismissAppFloat(GameSettingFloat.TAG_FLOAT);
            if (GameSettingFloat.this.m_boParamChanged) {
                GameSettingFloat.this.save();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void lambda$invoke$1(View view) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(GameSettingFloat.STR_VIDEO_URL));
            AppInstance.s_context.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$2$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m12lambda$invoke$2$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_nMouseContinuesSpeed > 20) {
                GameSettingFloat gameSettingFloat = GameSettingFloat.this;
                gameSettingFloat.m_nMouseContinuesSpeed -= 2;
                GameSettingFloat.this.onUIParamChange();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$3$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m13lambda$invoke$3$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_nMouseContinuesSpeed < 250) {
                GameSettingFloat.this.m_nMouseContinuesSpeed += 2;
                GameSettingFloat.this.onUIParamChange();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$4$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m14lambda$invoke$4$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_n3liandianDownMs > 10) {
                GameSettingFloat gameSettingFloat = GameSettingFloat.this;
                gameSettingFloat.m_n3liandianDownMs--;
                GameSettingFloat.this.onUIParamChange();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$5$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m15lambda$invoke$5$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_n3liandianDownMs < 250) {
                GameSettingFloat.this.m_n3liandianDownMs++;
                GameSettingFloat.this.onUIParamChange();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$6$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m16lambda$invoke$6$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_nby3liandianUpMs > 4) {
                GameSettingFloat gameSettingFloat = GameSettingFloat.this;
                gameSettingFloat.m_nby3liandianUpMs--;
                GameSettingFloat.this.onUIParamChange();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$invoke$7$com-baidu-kwgames-GameSettingFloat$1  reason: not valid java name */
        public /* synthetic */ void m17lambda$invoke$7$combaidukwgamesGameSettingFloat$1(View view) {
            if (GameSettingFloat.this.m_nby3liandianUpMs < 250) {
                GameSettingFloat.this.m_nby3liandianUpMs++;
                GameSettingFloat.this.onUIParamChange();
            }
        }
    }

    void write_xml(XmlSerializer xmlSerializer, String str, String str2, byte[] bArr) throws IOException {
        if (bArr == null || str2 == null || bArr.length == 0 || str2.isEmpty() || str.isEmpty()) {
            return;
        }
        xmlSerializer.startTag(null, str);
        xmlSerializer.attribute(null, TAG_ATTR_GAME, str2);
        xmlSerializer.text(ConvertUtils.bytes2HexString(bArr));
        xmlSerializer.endTag(null, str);
        xmlSerializer.text(System.getProperty("line.separator"));
    }

    void save() {
        try {
            StringWriter stringWriter = new StringWriter();
            XmlSerializer newSerializer = Xml.newSerializer();
            newSerializer.setOutput(stringWriter);
            newSerializer.startDocument("utf-8", true);
            for (Map.Entry<String, byte[]> entry : this.m_mapGameToParam.entrySet()) {
                write_xml(newSerializer, TAG_GAME_INFO, entry.getKey(), entry.getValue());
            }
            newSerializer.endDocument();
            File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
            externalFilesDir.mkdirs();
            FileIOUtils.writeFileFromString(externalFilesDir + TAG_GAME_INFO_FILE_NAME, stringWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean load() {
        try {
            byte[] readFile2BytesByStream = FileIOUtils.readFile2BytesByStream(AppInstance.get().getExternalFilesDir(null) + TAG_GAME_INFO_FILE_NAME);
            if (readFile2BytesByStream != null && readFile2BytesByStream.length != 0) {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(new ByteArrayInputStream(readFile2BytesByStream), "utf-8");
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType == 2 && TAG_GAME_INFO.equals(newPullParser.getName())) {
                        this.m_mapGameToParam.put(newPullParser.getAttributeValue(null, TAG_ATTR_GAME), ConvertUtils.hexString2Bytes(newPullParser.nextText()));
                        continue;
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
