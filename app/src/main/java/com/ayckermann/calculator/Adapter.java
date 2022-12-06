package com.ayckermann.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<History> listHistory;
    private SharedPreferences sharedPreferences;
    public Context context;

    public Adapter(ArrayList<History> listHistory, SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.item_history, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        History history = listHistory.get(position);
        holder.txtItem.setText(history.toString());

    }

    @Override
    @Nullable
    public int getItemCount() {


        return listHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);

            itemView.setOnLongClickListener(view -> {

                int p = getLayoutPosition();
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Hapus Riwayat?")
                        .setMessage("Ingin Hapus Riwayat?")
                        .setPositiveButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                        .setNegativeButton("Yes", (dialogInterface, i) -> {
                            int id = listHistory.get(p).getId();

                            sharedPreferences.edit().remove(Integer.toString(id)).commit();

                            for (int j=0;j<listHistory.size();j++){
                                if (id == listHistory.get(j).getId()){
                                    listHistory.remove(j);
                                    notifyItemRemoved(j);
                                    notifyItemRangeChanged(j, listHistory.size());

                                }
                            }
                        });
                AlertDialog dialog = alert.create();
                dialog.show();

                return true;
            });
        }


    }
}
