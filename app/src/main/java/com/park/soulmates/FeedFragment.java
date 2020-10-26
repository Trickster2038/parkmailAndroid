package com.park.soulmates;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    public FeedFragment() {

    }

    private RecyclerView feedRecycler;
    public int elemCnt = 3;
    private ArrayList<Data> resource = new ArrayList<>();
    private MyAdapter adapter = new MyAdapter(resource);

    // TODO: change placeholders
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resource.clear();

        for (int j = 1; j <= elemCnt; j++)
            resource.add(new Data(Integer.toString(j).concat(" Lorem ipsum")));

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        return view;
    }

    // no modifications
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedRecycler = view.findViewById(R.id.recyclerFeed);
        feedRecycler.setAdapter(adapter);

        feedRecycler.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        // TODO: fix null context
    }



    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private ArrayList<Data> data;

        public MyAdapter(ArrayList dataArg) {
            this.data = dataArg;
        }

        // no modifications
        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_prof, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }


        // TODO: add all info
        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.profDescription.setText(data.get(position).textDescription);
        }

        // TODO: we really need it?
        @Override
        public int getItemCount() {
            return data.size();
        }
    }



    // TODO: add all info here and everywhere further, no deep or serious modifications

    class MyHolder extends RecyclerView.ViewHolder {
        TextView profDescription;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profDescription = itemView.findViewById(R.id.profDescription);
        }
    }

    class Data {
        String textDescription;

        public Data(String text) {
            this.textDescription = text;
        }
    }
}
