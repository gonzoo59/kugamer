package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public interface MenuPresenter {

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    /* loaded from: assets/adb/sincoserver.dex */
    public interface Callback {
        void onCloseMenu(MenuBuilder menuBuilder, boolean z);

        boolean onOpenSubMenu(MenuBuilder menuBuilder);
    }

    boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl);

    boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl);

    boolean flagActionItems();

    int getId();

    MenuView getMenuView(ViewGroup viewGroup);

    void initForMenu(Context context, MenuBuilder menuBuilder);

    void onCloseMenu(MenuBuilder menuBuilder, boolean z);

    void onRestoreInstanceState(Parcelable parcelable);

    Parcelable onSaveInstanceState();

    boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder);

    void setCallback(Callback callback);

    void updateMenuView(boolean z);
}
