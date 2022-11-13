package com.example.miwok.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.miwok.R;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<ListItem> {

    private final int mColorResourceID;

    public ListAdapter(Context context, ArrayList<ListItem> listItems, int mColorResourceID) {
        super(context, 0, listItems);
        this.mColorResourceID = mColorResourceID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        ListItem currentListItem = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.image);

        if (currentListItem.hasImage()) {
            imageView.setImageResource(currentListItem.getImageResourceID());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }


        TextView miwokTextView = listItemView.findViewById(R.id.miwokText);
        miwokTextView.setText(currentListItem.getMiwokTranslation());

        TextView englishTextView = listItemView.findViewById(R.id.englishText);
        englishTextView.setText(currentListItem.getDefaultTranslation());

        RelativeLayout textContainer = listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
