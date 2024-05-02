package com.rumanweb.goalsetter;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    String taskTitleStr, dueDateStr, notesStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = myView.findViewById(R.id.recyclerView);


        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return myView;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false);

            Bundle args = getArguments();
            if (args != null) {
                taskTitleStr = args.getString("taskTitleMain");
                dueDateStr = args.getString("dueDate");
                notesStr = args.getString("notes");
            }
            else {
                Toast.makeText(getContext(), "Strings not found", Toast.LENGTH_SHORT).show();

            }


            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tvRecyclerItemTitle.setText(taskTitleStr);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageViewRecyclerItem;
            TextView tvRecyclerItemTitle, tvRecyclerItemDesc;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageViewRecyclerItem = itemView.findViewById(R.id.imageViewRecyclerItem);
                tvRecyclerItemTitle = itemView.findViewById(R.id.tvRecyclerItemTitle);
            }
        }
    }
}