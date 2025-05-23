package com.baidu.kwgames.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.R;
import com.blankj.utilcode.util.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.util.HashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class MsgBox {
    private static SPUtils m_ini = SPUtils.getInstance();
    private static Set<Integer> m_setRemind = new HashSet();

    public static void msg_box1(int i) {
        try {
            new AlertDialog.Builder(AppInstance.s_context).setTitle("").setMessage(i).setIcon(R.mipmap.ic_launcher).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1(String str) {
        try {
            new AlertDialog.Builder(AppInstance.s_context).setTitle("").setMessage(str).setIcon(R.mipmap.ic_launcher).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1(Context context, String str) {
        try {
            new AlertDialog.Builder(context).setTitle("").setMessage(str).setIcon(R.mipmap.ic_launcher).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1(Context context, int i) {
        try {
            new AlertDialog.Builder(context).setTitle("").setMessage(i).setIcon(R.mipmap.ic_launcher).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1_float(Context context, final String str) {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_MSG_BOX1) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX1);
        }
        EasyFloat.Builder layout = EasyFloat.with(context).setTag(Constants.FLOAT_MSG_BOX1).setGravity(17).setLayout(R.layout.msgbox1, new OnInvokeView() { // from class: com.baidu.kwgames.util.MsgBox.1
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                ((TextView) view.findViewById(R.id.m_textMsg)).setText(str);
                FloatMgr.resetVirtualMouse();
                view.findViewById(R.id.m_btnOK).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX1);
                    }
                });
                EasyFloat.updateFloat(Constants.FLOAT_MSG_BOX1, (AppInstance.s_nScreenW - view.getWidth()) / 2, (AppInstance.s_nScreenH - view.getHeight()) / 2);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false);
        layout.show();
    }

    public static void msg_box1_float(Context context, final String str, final boolean z) {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_MSG_BOX1) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX1);
        }
        EasyFloat.Builder layout = EasyFloat.with(context).setTag(Constants.FLOAT_MSG_BOX1).setGravity(17).setLayout(R.layout.msgbox1, new OnInvokeView() { // from class: com.baidu.kwgames.util.MsgBox.2
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                TextView textView = (TextView) view.findViewById(R.id.m_textMsg);
                textView.setText(str);
                if (z) {
                    textView.setTextAlignment(5);
                }
                FloatMgr.resetVirtualMouse();
                view.findViewById(R.id.m_btnOK).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX1);
                    }
                });
                EasyFloat.updateFloat(Constants.FLOAT_MSG_BOX1, (AppInstance.s_nScreenW - view.getWidth()) / 2, (AppInstance.s_nScreenH - view.getHeight()) / 2);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false);
        layout.show();
    }

    public static void msg_box_float_with_never_remind_once(Context context, int i, String str, String str2) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str2, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return;
        }
        if (EasyFloat.getAppFloatView(Constants.FLOAT_MSG_BOX2) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
        }
        EasyFloat.Builder layout = EasyFloat.with(context).setTag(Constants.FLOAT_MSG_BOX2).setGravity(17).setLayout(R.layout.msgbox2, new AnonymousClass3(str, str2));
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false);
        layout.show();
    }

    /* renamed from: com.baidu.kwgames.util.MsgBox$3  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements OnInvokeView {
        final /* synthetic */ String val$strIniName;
        final /* synthetic */ String val$strTitle;

        AnonymousClass3(String str, String str2) {
            this.val$strTitle = str;
            this.val$strIniName = str2;
        }

        @Override // com.lzf.easyfloat.interfaces.OnInvokeView
        public void invoke(View view) {
            ((TextView) view.findViewById(R.id.m_textMsg)).setText(this.val$strTitle);
            FloatMgr.resetVirtualMouse();
            view.findViewById(R.id.m_btnOK).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$3$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
                }
            });
            view.findViewById(R.id.m_btnNeverRemind).setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.3.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    SPUtils sPUtils = MsgBox.m_ini;
                    sPUtils.put("MsgReverRemind" + AnonymousClass3.this.val$strIniName, true);
                    EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
                }
            });
            EasyFloat.updateFloat(Constants.FLOAT_MSG_BOX2, (AppInstance.s_nScreenW - view.getWidth()) / 2, (AppInstance.s_nScreenH - view.getHeight()) / 2);
        }
    }

    public static void msg_box_float_with_choice2(final Context context, final int i, final int i2, final int i3, final Handler handler) {
        if (EasyFloat.getAppFloatView(Constants.FLOAT_MSG_BOX2) != null) {
            EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
        }
        EasyFloat.Builder layout = EasyFloat.with(context).setTag(Constants.FLOAT_MSG_BOX2).setGravity(17).setLayout(R.layout.msgbox2, new OnInvokeView() { // from class: com.baidu.kwgames.util.MsgBox.4
            @Override // com.lzf.easyfloat.interfaces.OnInvokeView
            public void invoke(View view) {
                ((TextView) view.findViewById(R.id.m_textMsg)).setText(NEAT.s(context, i));
                FloatMgr.resetVirtualMouse();
                Button button = (Button) view.findViewById(R.id.m_btnOK);
                button.setText(i2);
                button.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.4.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        Util.send_handler_msg(handler, i, 0);
                        EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
                    }
                });
                Button button2 = (Button) view.findViewById(R.id.m_btnNeverRemind);
                button2.setText(i3);
                button2.setOnClickListener(new View.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.4.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        Util.send_handler_msg(handler, i, 1);
                        EasyFloat.dismissAppFloat(Constants.FLOAT_MSG_BOX2);
                    }
                });
                EasyFloat.updateFloat(Constants.FLOAT_MSG_BOX2, (AppInstance.s_nScreenW - view.getWidth()) / 2, (AppInstance.s_nScreenH - view.getHeight()) / 2);
            }
        });
        layout.setShowPattern(ShowPattern.ALL_TIME).setMatchParent(false, false).setDragEnable(false);
        layout.show();
    }

    public static void msg_box1_with_choice2(final int i, int i2, int i3, final Handler handler) {
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(AppInstance.s_context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(AppInstance.s_context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNegativeButton(AppInstance.s_context.getString(i3), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1_with_choice1(Context context, int i, int i2, Handler handler) {
        msg_box1_with_choice1(context, i, context.getString(i2), handler);
    }

    public static void msg_box1_with_choice1(Context context, final int i, String str, final Handler handler) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(str, new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1_with_choice1_no_cancel(Context context, final int i, int i2, final Handler handler) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setCancelable(false);
            builder.setPositiveButton(context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg_box1_with_choice2(Context context, final int i, int i2, int i3, final Handler handler) {
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNegativeButton(context.getString(i3), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean msg_box1_once(int i) {
        return msg_box1_once(AppInstance.s_context, i);
    }

    public static boolean msg_box1_once(Context context, int i) {
        if (m_setRemind.contains(Integer.valueOf(i))) {
            return false;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            new AlertDialog.Builder(context).setTitle("").setMessage(i).setIcon(R.mipmap.ic_launcher).create().show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean msg_box_with_never_remind_once(int i, final String str) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return false;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(AppInstance.s_context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(AppInstance.s_context.getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                }
            });
            builder.setNegativeButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    SPUtils sPUtils2 = MsgBox.m_ini;
                    sPUtils2.put("MsgReverRemind" + str, true);
                }
            });
            builder.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean msg_box_with_never_remind(String str, final String str2) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str2, false)) {
            return false;
        }
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AppInstance.s_context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(str);
            builder.setPositiveButton(AppInstance.s_context.getString(R.string.iamknow), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.setNegativeButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.8
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    SPUtils sPUtils2 = MsgBox.m_ini;
                    sPUtils2.put("MsgReverRemind" + str2, true);
                }
            });
            builder.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean msg_box_with_never_remind_once_choice1(Context context, final int i, final String str, int i2, final Handler handler) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return false;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNeutralButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.9
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    SPUtils sPUtils2 = MsgBox.m_ini;
                    sPUtils2.put("MsgReverRemind" + str, true);
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean msg_box_with_never_remind_once_choice2(final int i, final String str, int i2, int i3, final Handler handler) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return false;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(AppInstance.s_context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(AppInstance.s_context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda12
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNegativeButton(AppInstance.s_context.getString(i3), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.setNeutralButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    MsgBox.lambda$msg_box_with_never_remind_once_choice2$9(str, dialogInterface, i4);
                }
            });
            builder.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$msg_box_with_never_remind_once_choice2$9(String str, DialogInterface dialogInterface, int i) {
        SPUtils sPUtils = m_ini;
        sPUtils.put("MsgReverRemind" + str, true);
    }

    public static boolean msg_box_with_never_remind_once_choice2(Context context, final int i, final String str, int i2, int i3, final Handler handler) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return false;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(context.getString(i2), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNegativeButton(context.getString(i3), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.setNeutralButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i4) {
                    MsgBox.lambda$msg_box_with_never_remind_once_choice2$12(str, dialogInterface, i4);
                }
            });
            builder.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$msg_box_with_never_remind_once_choice2$12(String str, DialogInterface dialogInterface, int i) {
        SPUtils sPUtils = m_ini;
        sPUtils.put("MsgReverRemind" + str, true);
    }

    public static void msg_box_with_never_remind_once_yesno(final int i, final String str, final Handler handler) {
        SPUtils sPUtils = m_ini;
        if (sPUtils.getBoolean("MsgReverRemind" + str, false) || m_setRemind.contains(Integer.valueOf(i))) {
            return;
        }
        try {
            m_setRemind.add(Integer.valueOf(i));
            AlertDialog.Builder builder = new AlertDialog.Builder(AppInstance.s_context);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("");
            builder.setMessage(i);
            builder.setPositiveButton(AppInstance.s_context.getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.10
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    Util.send_handler_msg(handler, i, 1);
                }
            });
            builder.setNegativeButton(AppInstance.s_context.getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.11
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    Util.send_handler_msg(handler, i, 0);
                }
            });
            builder.setNeutralButton(AppInstance.s_context.getString(R.string.no_tip), new DialogInterface.OnClickListener() { // from class: com.baidu.kwgames.util.MsgBox.12
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    Util.send_handler_msg(handler, i, 2);
                    SPUtils sPUtils2 = MsgBox.m_ini;
                    sPUtils2.put("MsgReverRemind" + str, true);
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean is_reminded(int i, String str) {
        if (!m_setRemind.contains(Integer.valueOf(i))) {
            SPUtils sPUtils = m_ini;
            if (!sPUtils.getBoolean("MsgReverRemind" + str, false)) {
                return false;
            }
        }
        return true;
    }
}
