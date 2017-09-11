package ccc.tcl.com.sprotappui.model;

/**
 * Created by user on 17-9-6.
 */

public class ChooseItemModel {
    private int iconId;
    private String itemName;

    public ChooseItemModel(int iconId, String itemName) {
        this.iconId = iconId;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getIconId() {
        return iconId;
    }

}
