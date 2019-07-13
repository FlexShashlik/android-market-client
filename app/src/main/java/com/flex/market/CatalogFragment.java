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
    private ExpandableListAdapter listAdapter;
    private List<String> listCategories;
    private HashMap<String, List<String>> listHashMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.catalog_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listViewCategories);
        listView.setGroupIndicator(null);

        // Fetch data here
        listCategories = new ArrayList<>();
        listHashMap = new HashMap<>();

        listCategories.add("Кровельные элементы");
        listCategories.add("Доборные элементы");
        listHashMap.put("Кровельные элементы", new ArrayList<String>(){{add("Крыша 1"); add("Крыша 2");}});
        listHashMap.put("Доборные элементы", new ArrayList<String>(){{add("Кирпич 1"); add("Кирпичик 2"); add("Камень 1");}});

        listAdapter = new ExpandableListAdapter(getContext(), listCategories, listHashMap);
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
            int lastExpandedPosition = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }
}
