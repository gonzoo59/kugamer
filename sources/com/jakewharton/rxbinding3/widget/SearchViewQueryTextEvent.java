package com.jakewharton.rxbinding3.widget;

import android.widget.SearchView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SearchViewQueryTextEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/SearchViewQueryTextEvent;", "", "view", "Landroid/widget/SearchView;", "queryText", "", "isSubmitted", "", "(Landroid/widget/SearchView;Ljava/lang/CharSequence;Z)V", "()Z", "getQueryText", "()Ljava/lang/CharSequence;", "getView", "()Landroid/widget/SearchView;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class SearchViewQueryTextEvent {
    private final boolean isSubmitted;
    private final CharSequence queryText;
    private final SearchView view;

    public static /* synthetic */ SearchViewQueryTextEvent copy$default(SearchViewQueryTextEvent searchViewQueryTextEvent, SearchView searchView, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            searchView = searchViewQueryTextEvent.view;
        }
        if ((i & 2) != 0) {
            charSequence = searchViewQueryTextEvent.queryText;
        }
        if ((i & 4) != 0) {
            z = searchViewQueryTextEvent.isSubmitted;
        }
        return searchViewQueryTextEvent.copy(searchView, charSequence, z);
    }

    public final SearchView component1() {
        return this.view;
    }

    public final CharSequence component2() {
        return this.queryText;
    }

    public final boolean component3() {
        return this.isSubmitted;
    }

    public final SearchViewQueryTextEvent copy(SearchView view, CharSequence queryText, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(queryText, "queryText");
        return new SearchViewQueryTextEvent(view, queryText, z);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof SearchViewQueryTextEvent) {
                SearchViewQueryTextEvent searchViewQueryTextEvent = (SearchViewQueryTextEvent) obj;
                if (Intrinsics.areEqual(this.view, searchViewQueryTextEvent.view) && Intrinsics.areEqual(this.queryText, searchViewQueryTextEvent.queryText)) {
                    if (this.isSubmitted == searchViewQueryTextEvent.isSubmitted) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        SearchView searchView = this.view;
        int hashCode = (searchView != null ? searchView.hashCode() : 0) * 31;
        CharSequence charSequence = this.queryText;
        int hashCode2 = (hashCode + (charSequence != null ? charSequence.hashCode() : 0)) * 31;
        boolean z = this.isSubmitted;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
    }

    public String toString() {
        return "SearchViewQueryTextEvent(view=" + this.view + ", queryText=" + this.queryText + ", isSubmitted=" + this.isSubmitted + ")";
    }

    public SearchViewQueryTextEvent(SearchView view, CharSequence queryText, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(queryText, "queryText");
        this.view = view;
        this.queryText = queryText;
        this.isSubmitted = z;
    }

    public final SearchView getView() {
        return this.view;
    }

    public final CharSequence getQueryText() {
        return this.queryText;
    }

    public final boolean isSubmitted() {
        return this.isSubmitted;
    }
}
