package com.logiconets.c196_nick_albers;

import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.ui.TermListAdapter;
import com.logiconets.c196_nick_albers.utility.PopulateData;
import com.logiconets.c196_nick_albers.viewmodel.TermViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newTermFab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<TermEntity> termData = new ArrayList<>();
    private TermListAdapter mAdapter;
    private TermViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
        initRecyclerView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermViewModel.class);
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        termData = mViewModel.mTerms;

        mAdapter = new TermListAdapter(termData,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
