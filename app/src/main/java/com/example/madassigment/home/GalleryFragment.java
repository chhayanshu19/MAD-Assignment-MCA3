package com.example.madassigment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassigment.R;

public class GalleryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_gallery);

        // This is what makes it a Grid! The "3" means 3 columns.
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        // Attach the adapter
        recyclerView.setAdapter(new GalleryAdapter());

        return view;
    }

    // --- Inner Class: The Adapter to feed images into the Grid ---
    private static class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

        // A dummy list size so we can see the grid in action
        private final int ITEM_COUNT = 15;

        @NonNull
        @Override
        public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
            // If you had a list of different images, you would set them here.
            // For now, it defaults to your mad.png from the XML.
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }

        static class GalleryViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public GalleryViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.img_gallery_item);
            }
        }
    }
}