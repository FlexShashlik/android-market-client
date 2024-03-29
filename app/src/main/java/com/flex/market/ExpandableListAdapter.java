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

class ExpandableListAdapter extends BaseExpandableListAdapter {
    static final List<Catalog> catalog = new ArrayList<>();
    static final List<SubCatalog> subCatalog = new ArrayList<>();
    private final Context context;
    static int selectedItemId = -1;
    static int lastExpandedPosition = -1;
    private final List<String> listCatalog;
    private final HashMap<String, List<String>> listHashMap;

    ExpandableListAdapter(Context context, List<String> listCatalog, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listCatalog = listCatalog;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listCatalog.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listCatalog.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCatalog.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listCatalog.get(groupPosition)).get(childPosition);
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
            convertView = inflater.inflate(R.layout.list_catalog, null);
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
            convertView = inflater.inflate(R.layout.list_catalog_item, null);
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
