package com.example.mvvmtaskapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NotesTableItemAdapter extends ListAdapter<NotesTable,NotesTableItemAdapter.NotesViewHolder> {
    private OnItemClickListener listener;

    public NotesTableItemAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<NotesTable> DIFF_CALLBACK = new DiffUtil.ItemCallback<NotesTable>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotesTable oldItem, @NonNull NotesTable newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotesTable oldItem, @NonNull NotesTable newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    &&oldItem.getDescription().equals(newItem.getDescription())
                    &&oldItem.getPriority()== newItem.getPriority();
        }
    };



//    public NotesTableItemAdapter(List<NotesTable> notesTables) {
//        this.notesTables = notesTables;
//    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notestable_items,parent,false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesTable currentNotesTable = getItem(position);

        holder.textViewTitle.setText(currentNotesTable.getTitle());
        holder.textViewDescription.setText(currentNotesTable.getDescription());
        holder.textViewPeriority.setText(String.valueOf(currentNotesTable.getPriority()));

    }

//    @Override
//    public int getItemCount() {
//        return notesTables.size();
//    }
//
//    public void setNotesTables(List<NotesTable> notesTables){
//        this.notesTables = notesTables;
//        notifyDataSetChanged();
//    }
    public NotesTable getNotesTableAt(int position){
        return getItem(position);}

   public class  NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPeriority;
        public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewDescription = itemView.findViewById(R.id.text_view_description);
        textViewPeriority= itemView.findViewById(R.id.text_view_periority);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnItemClick(getItem(position));
                }
            }
        });
    }
}

public interface OnItemClickListener{
        void OnItemClick(NotesTable notesTable);
}

public  void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
