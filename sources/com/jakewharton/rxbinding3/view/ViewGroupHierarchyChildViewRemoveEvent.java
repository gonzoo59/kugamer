package com.jakewharton.rxbinding3.view;

import android.view.View;
import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ViewGroupHierarchyChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/jakewharton/rxbinding3/view/ViewGroupHierarchyChildViewRemoveEvent;", "Lcom/jakewharton/rxbinding3/view/ViewGroupHierarchyChangeEvent;", "view", "Landroid/view/ViewGroup;", "child", "Landroid/view/View;", "(Landroid/view/ViewGroup;Landroid/view/View;)V", "getChild", "()Landroid/view/View;", "getView", "()Landroid/view/ViewGroup;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class ViewGroupHierarchyChildViewRemoveEvent extends ViewGroupHierarchyChangeEvent {
    private final View child;
    private final ViewGroup view;

    public static /* synthetic */ ViewGroupHierarchyChildViewRemoveEvent copy$default(ViewGroupHierarchyChildViewRemoveEvent viewGroupHierarchyChildViewRemoveEvent, ViewGroup viewGroup, View view, int i, Object obj) {
        if ((i & 1) != 0) {
            viewGroup = viewGroupHierarchyChildViewRemoveEvent.getView();
        }
        if ((i & 2) != 0) {
            view = viewGroupHierarchyChildViewRemoveEvent.getChild();
        }
        return viewGroupHierarchyChildViewRemoveEvent.copy(viewGroup, view);
    }

    public final ViewGroup component1() {
        return getView();
    }

    public final View component2() {
        return getChild();
    }

    public final ViewGroupHierarchyChildViewRemoveEvent copy(ViewGroup view, View child) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(child, "child");
        return new ViewGroupHierarchyChildViewRemoveEvent(view, child);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ViewGroupHierarchyChildViewRemoveEvent) {
                ViewGroupHierarchyChildViewRemoveEvent viewGroupHierarchyChildViewRemoveEvent = (ViewGroupHierarchyChildViewRemoveEvent) obj;
                return Intrinsics.areEqual(getView(), viewGroupHierarchyChildViewRemoveEvent.getView()) && Intrinsics.areEqual(getChild(), viewGroupHierarchyChildViewRemoveEvent.getChild());
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        ViewGroup view = getView();
        int hashCode = (view != null ? view.hashCode() : 0) * 31;
        View child = getChild();
        return hashCode + (child != null ? child.hashCode() : 0);
    }

    public String toString() {
        return "ViewGroupHierarchyChildViewRemoveEvent(view=" + getView() + ", child=" + getChild() + ")";
    }

    @Override // com.jakewharton.rxbinding3.view.ViewGroupHierarchyChangeEvent
    public ViewGroup getView() {
        return this.view;
    }

    @Override // com.jakewharton.rxbinding3.view.ViewGroupHierarchyChangeEvent
    public View getChild() {
        return this.child;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewGroupHierarchyChildViewRemoveEvent(ViewGroup view, View child) {
        super(null);
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(child, "child");
        this.view = view;
        this.child = child;
    }
}
