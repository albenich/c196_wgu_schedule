package com.logiconets.c196_nick_albers.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.logiconets.c196_nick_albers.R;
import com.logiconets.c196_nick_albers.model.TermEntity;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms;

    public TermListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.content_terms, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (mTerms != null) {
            TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.termItemView.setText("No TermEntity");
        }
    }

    void setWords(List<TermEntity> words){
        mTerms = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}


