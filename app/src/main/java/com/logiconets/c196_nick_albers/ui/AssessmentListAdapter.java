package com.logiconets.c196_nick_albers.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.logiconets.c196_nick_albers.AssessmentEditorActivity;
import com.logiconets.c196_nick_albers.R;
import com.logiconets.c196_nick_albers.database.AssessmentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.logiconets.c196_nick_albers.utility.Constants.ASSESSMENT_ID_KEY;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {

    private final List<AssessmentEntity> mAssessments;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public AssessmentListAdapter(List<AssessmentEntity> mAssessments, Context mContext) {
        this.mAssessments = mAssessments;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.assessmentTextView)
        TextView mAssessmentView;

        @BindView(R.id.assessmentFAB)
        FloatingActionButton mFab;


        public AssessmentViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        Log.i("AssessmentListAdapter", "Triggering onBindViewHolder");
        final AssessmentEntity assessment = mAssessments.get(position);
        holder.mAssessmentView.setText(assessment.getTitle());

        holder.mFab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AssessmentEditorActivity.class);
            intent.putExtra(ASSESSMENT_ID_KEY, assessment.getAssessmentId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}


