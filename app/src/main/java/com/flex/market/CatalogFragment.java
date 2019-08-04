package com.flex.market;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogFragment extends Fragment {
    ExpandableListView listViewCatalog;
    static ExpandableListAdapter listAdapter;
    static List<String> listCatalog;
    static HashMap<String, List<String>> listHashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewCatalog = view.findViewById(R.id.listViewCatalog);
        listViewCatalog.setGroupIndicator(null);

        if (listCatalog == null) {
            MarketAPI.GetCatalog(getContext());

            listCatalog = new ArrayList<>();
            listHashMap = new HashMap<>();
        }

        listAdapter = new ExpandableListAdapter(getContext(), listCatalog, listHashMap);
        listViewCatalog.setAdapter(listAdapter);

        listViewCatalog.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    if (ExpandableListAdapter.selectedItemId == groupPosition) {
                        ExpandableListAdapter.selectedItemId = -1;
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    ExpandableListAdapter.selectedItemId = groupPosition;
                    listAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });

        listViewCatalog.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != ExpandableListAdapter.lastExpandedPosition) {
                    listViewCatalog.collapseGroup(ExpandableListAdapter.lastExpandedPosition);
                }
                ExpandableListAdapter.lastExpandedPosition = groupPosition;
            }
        });

        listViewCatalog.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                for (int j = 0; j < ExpandableListAdapter.subCatalog.size(); j++) {
                    SubCatalog subCatalog = ExpandableListAdapter.subCatalog.get(j);
                    String subCatalogName = listHashMap.get(listCatalog.get(i)).get(i1);

                    if (subCatalog.Name.equals(subCatalogName)) {
                        MarketAPI.selectedSubCatalog = subCatalog.ID;
                        Toast.makeText(getContext(), String.valueOf(subCatalog.ID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                // Reset list view
                listViewCatalog.collapseGroup(ExpandableListAdapter.lastExpandedPosition);
                ExpandableListAdapter.selectedItemId = -1;
                listAdapter.notifyDataSetChanged();

                SingletonFragmentManager.getInstance()
                        .getManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_left, R.anim.exit_left)
                        .replace(
                                R.id.fragment_container,
                                new ProductsFragment()
                        ).commit();

                return false;
            }
        });
    }

    public static void SetCatalog(List<Catalog> catalogList) {
        listCatalog.clear();
        listHashMap.clear();

        for (int i = 0; i < catalogList.size(); i++) {
            listCatalog.add(catalogList.get(i).Name);
        }
    }

    public static void SetSubCatalog(List<Catalog> catalogList, List<SubCatalog> subCatalogList) {
        for (int i = 0; i < catalogList.size(); i++) {
            List<String> sc = new ArrayList<>();

            for (int j = 0; j < subCatalogList.size(); j++) {
                if (subCatalogList.get(j).CatalogID == catalogList.get(i).ID) {
                    sc.add(subCatalogList.get(j).Name);
                }
            }

            listHashMap.put(catalogList.get(i).Name, sc);
        }

        listAdapter.notifyDataSetChanged();
    }
}
