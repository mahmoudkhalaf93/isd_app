package com.example.android.isd;

import java.util.ArrayList;

/**
 * Created by modye on 3/13/2018.
 */

public class SectionDataModel {



    private String headerTitle;
    private String subct;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle,String sunb, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.subct=sunb;
        this.allItemsInSection = allItemsInSection;
    }

    public String getsubct() {
        return subct;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }
    public void setsubct(String suberTitle) {
        this.subct = suberTitle;
    }
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
