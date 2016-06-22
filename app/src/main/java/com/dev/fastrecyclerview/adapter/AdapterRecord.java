package com.dev.fastrecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.fastrecyclerview.R;
import com.dev.fastrecyclerview.interfaces.ClickListener;
import com.dev.fastrecyclerview.models.RecordModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MyPC on 6/21/2016.
 */
public class AdapterRecord extends SelectableAdapter<AdapterRecord.ViewHolder> {
    private List<RecordModel> listRecord;
    private ClickListener clickListener;
    private Context context;
    public AdapterRecord(List<RecordModel> listRecord, ClickListener clickListener, Context context){
        this.listRecord = listRecord;
        this.clickListener = clickListener;
        this.context = context;
    }

    public void addRow(RecordModel record){
        listRecord.add(record);
        notifyItemInserted(listRecord.size()-1);
    }
    public void deleteRow(int pos){
        listRecord.remove(pos);
        notifyItemRemoved(pos);
    }
    public void updateRow(int pos,RecordModel record){
        listRecord.set(pos,record);

        notifyItemChanged(pos);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_record,parent,false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder!=null){
            RecordModel record = listRecord.get(position);
            holder.getmTvName().setText(record.getPhoneNumber());
           // holder.getmImgAvatar().setImageResource(record.getPath());
            holder.getmTvDuration().setText("00:50");
            holder.getmTvNote().setText(record.getNote());
            holder.getmTvTime().setText("12:00");
          //  holder.getmImgAvatar().setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            if(isSelected(position)){
                holder.getmLayout().setBackgroundResource(R.drawable.background_item_selected);
                holder.getmImgAvatar().setImageResource(R.drawable.check);

            }else{

                holder.getmLayout().setBackgroundColor(Color.WHITE);
                if(record.getAvatar()!=null){
                    holder.getmImgAvatar().setImageBitmap(record.getAvatar());
                }else
                holder.getmImgAvatar().setImageResource(R.drawable.ic_account_circle_black_24dp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listRecord.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mTvName;
        private TextView mTvTime;

        private TextView mTvDuration;

        private TextView mTvNote;
        private LinearLayout mLayout;
        private CircleImageView mImgAvatar;
        private ClickListener listener;
      //  private final View selectedOverlay;
        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            this.listener = listener;
            mLayout = (LinearLayout) itemView.findViewById(R.id.item);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.ima_avatar);
            mTvDuration =(TextView) itemView.findViewById(R.id.tv_duration);
            mTvNote = (TextView)itemView.findViewById(R.id.tv_note);
            mTvTime=(TextView)itemView.findViewById(R.id.tv_time);
           // selectedOverlay = (View) itemView.findViewById(R.id.selected_overlay);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public LinearLayout getmLayout() {
            return mLayout;
        }

        public void setmLayout(LinearLayout mLayout) {
            this.mLayout = mLayout;
        }

        public TextView getmTvName() {
            return mTvName;
        }

        public void setmTvName(TextView mTvName) {
            this.mTvName = mTvName;
        }

        public TextView getmTvTime() {
            return mTvTime;
        }

        public void setmTvTime(TextView mTvTime) {
            this.mTvTime = mTvTime;
        }

        public TextView getmTvDuration() {
            return mTvDuration;
        }

        public void setmTvDuration(TextView mTvDuration) {
            this.mTvDuration = mTvDuration;
        }

        public TextView getmTvNote() {
            return mTvNote;
        }

        public void setmTvNote(TextView mTvNote) {
            this.mTvNote = mTvNote;
        }

        public CircleImageView getmImgAvatar() {
            return mImgAvatar;
        }

        public void setmImgAvatar(CircleImageView mImgAvatar) {
            this.mImgAvatar = mImgAvatar;
        }



    }
}
