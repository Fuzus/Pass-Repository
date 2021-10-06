package br.com.fuzusnoary.passrepository.view.viewholder;


import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.listeners.ClickList;
import br.com.fuzusnoary.passrepository.model.PasswordModel;

public class PassViewHolder extends RecyclerView.ViewHolder {

    private TextView _textPassName;
    private ImageView _imgDelete;
    private Context _context;

    public PassViewHolder(@NonNull View itemView) {
        super(itemView);

        this._context = itemView.getContext();
        this._textPassName = itemView.findViewById(R.id.text_password_name);
        this._imgDelete = itemView.findViewById(R.id.img_delete);

    }

    public void bind(PasswordModel pass, ClickList listener) {
        this._textPassName.setText(pass.getPassName());
        this.setListeners(pass, listener);
    }

    public void setListeners(PasswordModel pass, ClickList listener) {
        this._imgDelete.setOnClickListener((view) -> {
            new AlertDialog.Builder(_context)
                    .setTitle("Remoção de senha")
                    .setMessage("Deseja realmente remover esta senha?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.onDelete(pass.getId());
                        }
                    })
                    .setNeutralButton("Não", null)
                    .show();
        });

        this._textPassName.setOnClickListener((view) -> {
            listener.onClick(pass.getId());
        });
    }


}
