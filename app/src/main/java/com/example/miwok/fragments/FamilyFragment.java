package com.example.miwok.fragments;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.miwok.R;
import com.example.miwok.activities.FamilyActivity;
import com.example.miwok.utils.ListAdapter;
import com.example.miwok.utils.ListItem;

import java.util.ArrayList;

public class FamilyFragment extends Fragment {
    private MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(audioFocusChangeListener, new Handler())
                .build();

        ArrayList<ListItem> listItems = new ArrayList<>();

        listItems.add(new ListItem("father", "әpә", R.drawable.family_father, R.raw.family_father));
        listItems.add(new ListItem("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        listItems.add(new ListItem("son", "angsi", R.drawable.family_son, R.raw.family_son));
        listItems.add(new ListItem("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        listItems.add(new ListItem("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        listItems.add(new ListItem("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        listItems.add(new ListItem("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        listItems.add(new ListItem("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        listItems.add(new ListItem("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        listItems.add(new ListItem("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        ListAdapter listAdapter = new ListAdapter(getActivity(), listItems, R.color.category_family);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                if (audioManager.requestAudioFocus(focusRequest) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    ListItem listItem = listItems.get(i);

                    mediaPlayer = MediaPlayer.create(getActivity(), listItem.getAudioResourceID());
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });

                    mediaPlayer.start();
                }
            }
        });

        return rootView;
    }

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }
        }
    };

    private void releaseMediaPlayer() {
        if (mediaPlayer == null) {
            return;
        }

        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
