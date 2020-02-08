package com.logiconets.c196_nick_albers.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.logiconets.c196_nick_albers.R;
import com.logiconets.c196_nick_albers.database.TermEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


        public TermViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        /*        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);
        }
*/
    }
/*
    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms;

    public TermListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
*/
    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        final TermEntity term = mTerms.get(position);
        holder.mTextView.setText(term.getTitle());
        /*        if (mTerms != null) {
            TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.termItemView.setText("No TermEntity");
        }
*/
    }

 /*   void setWords(List<TermEntity> words){
        mTerms = words;
        notifyDataSetChanged();
    }
*/
    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}


