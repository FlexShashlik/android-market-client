package com.flex.market;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogFragment extends Fragment {
    private ExpandableListView listView;
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

        listView = view.findViewById(R.id.listViewCategories);
        listView.setGroupIndicator(null);

        // Fetch data here
        MarketAPI.GetCatalog(getContext());

        listCatalog = new ArrayList<>();
        listHashMap = new HashMap<>();

        listAdapter = new ExpandableListAdapter(getContext(), listCatalog, listHashMap);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != ExpandableListAdapter.lastExpandedPosition) {
                    listView.collapseGroup(ExpandableListAdapter.lastExpandedPosition);
                }
                ExpandableListAdapter.lastExpandedPosition = groupPosition;
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                // Reset list view
                listView.collapseGroup(ExpandableListAdapter.lastExpandedPosition);
                ExpandableListAdapter.selectedItemId = -1;
                listAdapter.notifyDataSetChanged();

                Singleton.getInstance()
                        .getManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_left, R.anim.exit_left, R.anim.exit_right, R.anim.enter_right)
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
