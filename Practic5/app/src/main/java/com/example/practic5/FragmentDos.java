package com.example.practic5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDos extends Fragment {

    public FragmentDos() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dos, container, false);

        ListView listView = view.findViewById(R.id.listView);

        String[] datos = {
                "Teatro Principal",
                "Teatro Municipal",
                "Auditorio Central",
                "Sala Cultural Norte"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                datos
        );

        listView.setAdapter(adapter);

        return view;
    }
}
