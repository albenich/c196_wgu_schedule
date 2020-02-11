package com.logiconets.c196_nick_albers.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.logiconets.c196_nick_albers.TermEditorActivity;
import com.logiconets.c196_nick_albers.R;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.logiconets.c196_nick_albers.utility.Constants.TERM_ID_KEY;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private final List<TermEntity> mTerms;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public TermListAdapter(List<TermEntity> mTerms, Context mContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.termTextView)
        TextView mTextView;
        @BindView(R.id.fab)
        FloatingActionButton mFab;


        public TermViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        final TermEntity term = mTerms.get(position);
        holder.mTextView.setText(term.getTitle());

        holder.mFab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TermEditorActivity.class);
            intent.putExtra(TERM_ID_KEY, term.getTitleId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}


