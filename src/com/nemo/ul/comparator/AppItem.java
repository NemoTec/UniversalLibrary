package com.nemo.ul.comparator;


public class AppItem implements AppNameComparator.IAppOrderInfo {

    public String mPkgName;
    public String mLabel;


    public AppItem() {

    }

    public AppItem(String label, String pkgName) {
        mLabel = label;
        mPkgName = pkgName;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }

    @Override
    public String getOrderInfo1() {
        return mLabel;
    }

    @Override
    public String getOrderInfo2() {
        return mPkgName;
    }

    public void buildDiff() {

    }

}