package ir.sanaitgroup.mvvm;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ir.sanaitgroup.mvvm.databinding.NoteItemBinding;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;


    public NoteAdapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note old_item, Note new_item) {
            return old_item.getId() == new_item.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note old_item2,@NonNull Note new_item2) {
            return (new_item2.getPriority()==old_item2.getPriority() &&
                    new_item2.getDescription().equalsIgnoreCase(old_item2.getDescription()) &&
                    new_item2.getTitle().equalsIgnoreCase(old_item2.getTitle()));
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NoteItemBinding noteItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),R.layout.note_item,viewGroup,false);
        return new NoteHolder(noteItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.itemBinding.setNote(currentNote);
    }


    public Note getNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        NoteItemBinding itemBinding;

        public NoteHolder(NoteItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener!=null && position!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
