package br.com.fuzusnoary.passrepository.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.listeners.ClickList;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.view.viewholder.PassViewHolder;

public class PassAdapter extends RecyclerView.Adapter<PassViewHolder> {

    private List<PasswordModel> _list = new ArrayList<>();
    private ClickList _listener;

    @NonNull
    @Override
    public PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.password_row, parent, false);

        return new PassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassViewHolder holder, int position) {
        holder.bind(this._list.get(position), this._listener);
    }

    @Override
    public int getItemCount() {
        return this._list.size();
    }

    public void attachList(List<PasswordModel> list){
        this._list = list;
    }

    public void setListeners(ClickList listener){
        this._listener = listener;
    }
}
