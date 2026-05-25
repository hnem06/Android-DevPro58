package com.hnem06.takenote;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {

    // Mảng màu nền và màu icon cho các note item xoay vòng
    private static final int[] ICON_BG_COLORS = {
            R.color.icon_bg_red,
            R.color.icon_bg_blue,
            R.color.icon_bg_orange,
            R.color.icon_bg_green,
            R.color.icon_bg_purple
    };

    private static final int[] ICON_FG_COLORS = {
            R.color.icon_fg_red,
            R.color.icon_fg_blue,
            R.color.icon_fg_orange,
            R.color.icon_fg_green,
            R.color.icon_fg_purple
    };

    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
        void onMoreClick(Note note, View anchor);
    }

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle())
                            && oldItem.getDescription().equals(newItem.getDescription())
                            && oldItem.isCompleted() == newItem.isCompleted();
                }
            };

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = getItem(position);
        holder.bind(note, position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final View viewIconBg;
        private final ImageView ivNoteIcon;
        private final TextView tvTitle;
        private final TextView tvBadgeNew;
        private final TextView tvDateTime;
        private final ImageButton btnMore;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIconBg = itemView.findViewById(R.id.viewIconBg);
            ivNoteIcon = itemView.findViewById(R.id.ivNoteIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBadgeNew = itemView.findViewById(R.id.tvBadgeNew);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            btnMore = itemView.findViewById(R.id.btnMore);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onNoteClick(getItem(getAdapterPosition()));
                }
            });

            btnMore.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onMoreClick(getItem(getAdapterPosition()), btnMore);
                }
            });
        }

        void bind(Note note, int position) {
            tvTitle.setText(note.getTitle());

            // get Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
            String dateStr = sdf.format(new Date(note.getCreatedAt()));

            // get Time
            SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String timeStr = timeFmt.format(new Date(note.getCreatedAt()));


            tvDateTime.setText(dateStr + "  -  " + timeStr);

            long elapsed = System.currentTimeMillis() - note.getCreatedAt();

            // NEW Note
            if (elapsed < TimeUnit.HOURS.toMillis(24)) {
                tvBadgeNew.setVisibility(View.VISIBLE);
            } else {
                tvBadgeNew.setVisibility(View.GONE);
            }

            // Mauf icon theo vị trí
            int colorIndex = position % ICON_BG_COLORS.length;
            GradientDrawable bgDrawable = new GradientDrawable();
            bgDrawable.setShape(GradientDrawable.OVAL);
            bgDrawable.setColor(ContextCompat.getColor(itemView.getContext(), ICON_BG_COLORS[colorIndex]));
            viewIconBg.setBackground(bgDrawable);

            ivNoteIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), ICON_FG_COLORS[colorIndex]));

            // Hiệu ứng completed: giảm opacity
            float alpha = note.isCompleted() ? 0.5f : 1.0f;
            itemView.setAlpha(alpha);
        }
    }
}
