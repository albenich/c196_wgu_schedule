package com.logiconets.c196_nick_albers.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.logiconets.c196_nick_albers.CourseEditorActivity;
import com.logiconets.c196_nick_albers.R;
import com.logiconets.c196_nick_albers.database.CourseEntity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.logiconets.c196_nick_albers.utility.Constants.COURSE_ID_KEY;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private final List<CourseEntity> mCourses;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public CourseListAdapter(List<CourseEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.courseTextView)
        TextView mCourseView;

        @BindView(R.id.courseFab)
        FloatingActionButton mFab;

        public CourseViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

        final CourseEntity course = mCourses.get(position);
        holder.mCourseView.setText(course.getTitle());

        holder.mFab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CourseEditorActivity.class);
            intent.putExtra(COURSE_ID_KEY, course.getCourseId());
            mContext.startActivity(intent);
        });
        /*
//Use this instead of mFab to allow the entire list item to be clicked on instead of FAB
        holder.mCourseView.setOnClickListener(v -> {
            Intent intent1 = new Intent(mContext, CourseEditorActivity.class);
            intent1.putExtra(COURSE_ID_KEY, course.getCourseId());
            mContext.startActivity(intent1);


        }); */
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}


