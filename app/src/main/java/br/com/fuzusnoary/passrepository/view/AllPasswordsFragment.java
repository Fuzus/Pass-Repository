package br.com.fuzusnoary.passrepository.view;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fuzusnoary.passrepository.R;
import br.com.fuzusnoary.passrepository.constants.PasswordConstants;
import br.com.fuzusnoary.passrepository.listeners.ClickList;
import br.com.fuzusnoary.passrepository.model.PasswordModel;
import br.com.fuzusnoary.passrepository.view.adapter.PassAdapter;
import br.com.fuzusnoary.passrepository.view.viewmodel.AllPasswordsViewModel;

public class AllPasswordsFragment extends Fragment {

    private ViewHolder _viewHolder = new ViewHolder();
    private ClickList _listener;
    private PassAdapter _adapter = new PassAdapter();
    private AllPasswordsViewModel _viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this._viewModel = new ViewModelProvider(this).get(AllPasswordsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_passwords, container, false);

        this._viewHolder.recyclerView = root.findViewById(R.id.recycler_list);
        this._viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this._viewHolder.recyclerView.setAdapter(this._adapter);

        this._listener = new ClickList() {
            @Override
            public void onClick(int id) {
                Bundle bundle = new Bundle();
                bundle.putInt(PasswordConstants.ID, id);

                Intent intent = new Intent(getContext(), PasswordReadActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }

            @Override
            public void onDelete(int id) {
                _viewModel.delete(id);
                _viewModel.getList();
            }
        };

        this._adapter.setListeners(this._listener);
        this.setObservers();

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        this._viewModel.getList();
    }

    private void setObservers() {
        this._viewModel.list.observe(getViewLifecycleOwner(), new Observer<List<PasswordModel>>() {
            @Override
            public void onChanged(List<PasswordModel> passwordModels) {
                _adapter.attachList(passwordModels);
            }
        });

        this._viewModel.feedback.observe(getViewLifecycleOwner(), feedback -> {
            Toast.makeText(getContext(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private static class ViewHolder {
        RecyclerView recyclerView;
    }

}