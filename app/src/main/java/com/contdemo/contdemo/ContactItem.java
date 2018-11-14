package com.contdemo.contdemo;

public class ContactItem {

    private int itemId;
    private String contactName;
    private String contactNumber;

//    public ContactItem(int itemId, String contactName, String contactNumber) {
//        this.itemId = itemId;
//        this.contactName = contactName;
//        this.contactNumber = contactNumber;
//    }

    public ContactItem(String contactName, String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}
