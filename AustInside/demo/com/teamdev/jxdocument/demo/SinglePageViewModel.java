/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxdocument.demo;

import com.teamdev.jxdocument.SinglePagePresentation;

public class SinglePageViewModel {

    private final int pagesCount;
    private final SinglePagePresentation presentation;

    private int pageIndex;

    public SinglePageViewModel(SinglePagePresentation presentation) {
        this.presentation = presentation;
        this.pagesCount = presentation.getDocument().getPageCount();
        this.setSelectedPageIndex(0);
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getSelectedPageIndex() {
        return pageIndex;
    }

    public void setSelectedPageIndex(int index) {
        if (index < 0 || index >= pagesCount) {
            throw new IllegalArgumentException("The index must be 0 <= index < pages count");
        }
        pageIndex = index;
        presentation.displayPage(pageIndex);
    }

    public void selectNextPage() {
        if (canSelectNextPage()) {
            setSelectedPageIndex(getSelectedPageIndex() + 1);
        }
    }

    public void selectPrevPage() {
        if (canSelectPrevPage()) {
            setSelectedPageIndex(getSelectedPageIndex() - 1);
        }
    }

    public boolean canSelectNextPage() {
        return (getSelectedPageIndex() + 1) < getPagesCount();
    }

    public boolean canSelectPrevPage() {
        return getSelectedPageIndex() > 0;
    }


}
