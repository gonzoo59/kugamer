package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.ArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;
import java.util.Iterator;
import java.util.Map;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
    final Context mContext;
    private Map<SupportMenuItem, MenuItem> mMenuItems;
    private Map<SupportSubMenu, SubMenu> mSubMenus;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseMenuWrapper(Context context, T t) {
        super(t);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
            if (this.mMenuItems == null) {
                this.mMenuItems = new ArrayMap();
            }
            MenuItem menuItem2 = this.mMenuItems.get(menuItem);
            if (menuItem2 == null) {
                MenuItem wrapSupportMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, supportMenuItem);
                this.mMenuItems.put(supportMenuItem, wrapSupportMenuItem);
                return wrapSupportMenuItem;
            }
            return menuItem2;
        }
        return menuItem;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (subMenu instanceof SupportSubMenu) {
            SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
            if (this.mSubMenus == null) {
                this.mSubMenus = new ArrayMap();
            }
            SubMenu subMenu2 = this.mSubMenus.get(supportSubMenu);
            if (subMenu2 == null) {
                SubMenu wrapSupportSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, supportSubMenu);
                this.mSubMenus.put(supportSubMenu, wrapSupportSubMenu);
                return wrapSupportSubMenu;
            }
            return subMenu2;
        }
        return subMenu;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalClear() {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map != null) {
            map.clear();
        }
        Map<SupportSubMenu, SubMenu> map2 = this.mSubMenus;
        if (map2 != null) {
            map2.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalRemoveGroup(int i) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map == null) {
            return;
        }
        Iterator<SupportMenuItem> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (i == it.next().getGroupId()) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalRemoveItem(int i) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map == null) {
            return;
        }
        Iterator<SupportMenuItem> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (i == it.next().getItemId()) {
                it.remove();
                return;
            }
        }
    }
}
