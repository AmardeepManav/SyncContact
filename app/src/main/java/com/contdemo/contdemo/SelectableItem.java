package com.contdemo.contdemo;

public class SelectableItem extends ContactItem{

    private boolean isSelected = false;

    public SelectableItem(ContactItem item, boolean isSelected) {
        super(item.getContactName(), item.getContactNumber());
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}