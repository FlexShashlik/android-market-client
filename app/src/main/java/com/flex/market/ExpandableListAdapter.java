package com.flex.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listCategories;
    private HashMap<String, List<String>> listHashMap;
    static int selectedItemId = -1;
    static int lastExpandedPosition = -1;
    static List<Catalog> catalog = new ArrayList<>();
    static List<SubCatalog> subCatalog = new ArrayList<>();

    ExpandableListAdapter(Context context, List<String> listCategories, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listCategories = listCategories;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listCategories.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listCategories.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String category = (String)getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_category, null);
        }

        TextView listCategory = convertView.findViewById(R.id.listCategory);
        listCategory.setText(category);
        listCategory.setTextColor(parent.getContext().getResources().getColor(R.color.gray));

        if (selectedItemId == groupPosition) {
            listCategory.setTextColor(parent.getContext().getResources().getColor(R.color.colorPrimary));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = (String)getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_category_item, null);
        }

        TextView listChild = convertView.findViewById(R.id.listItem);
        listChild.setText(child);
        listChild.setTextColor(parent.getContext().getResources().getColor(R.color.gray));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
