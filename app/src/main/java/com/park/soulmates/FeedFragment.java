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
    public int elemCnt= 42;
    private ArrayList<Data> resource = new ArrayList<>();
    private MyAdapter adapter = new MyAdapter(resource);
    // private final String CNT_STATE="savedField";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (savedInstanceState != null ) {
//            elemCnt = savedInstanceState.getInt(CNT_STATE);
//        }
        resource.clear();

        for (int j = 1; j <= elemCnt; j++)
            resource.add(new Data(Integer.toString(j).concat(" Lorem ipsum")));

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedRecycler = view.findViewById(R.id.recyclerFeed);
        feedRecycler.setAdapter(adapter);

        feedRecycler.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        // TODO: fix null context

//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                elemCnt++;
//                resource.add(new Data(Integer.toString(elemCnt)));
//                adapter.notifyItemInserted(elemCnt);
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CNT_STATE, elemCnt);

        super.onSaveInstanceState(outState);
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private ArrayList<Data> data;

        public MyAdapter(ArrayList dataArg) {
            this.data = dataArg;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_prof, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.profView.setText(data.get(position).textDescription);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView profView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profView = itemView.findViewById(R.id.profDescription);
//            numberView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String txt = numberView.getText().toString();
//                    androidx.fragment.app.Fragment numFragment = new ShowFragment(txt);
//
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//
//                    ft.replace(R.id.frameFragLayout, numFragment);
//                    ft.addToBackStack(null);
//                    ft.commit();
//                }
//            });
        }
    }

    class Data {
        String textDescription;

        public Data(String text) {
            this.textDescription = text;
        }
    }




}
