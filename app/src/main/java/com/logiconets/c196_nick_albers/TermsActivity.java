package com.logiconets.c196_nick_albers;

import android.content.Intent;
import android.os.Bundle;

import com.logiconets.c196_nick_albers.database.TermEntity;
import com.logiconets.c196_nick_albers.ui.TermListAdapter;
import com.logiconets.c196_nick_albers.utility.PopulateData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends AppCompatActivity {

    List<TermEntity> terms = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.newTermFab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<TermEntity> termData = new ArrayList<>();
    private TermListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();

 /*       RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
*/
/*        FloatingActionButton fab = findViewById(R.id.newTermFab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TermActivity",PopulateData.getTerms().toString());
                    Snackbar.make(view, PopulateData.getTerms().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
    }

    private void initRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        termData = PopulateData.getTerms();

        mAdapter = new TermListAdapter(termData,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
