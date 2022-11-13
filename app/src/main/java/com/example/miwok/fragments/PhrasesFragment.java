package com.example.miwok.fragments;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.miwok.R;
import com.example.miwok.activities.PhrasesActivity;
import com.example.miwok.utils.ListAdapter;
import com.example.miwok.utils.ListItem;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {
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

        listItems.add(new ListItem("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        listItems.add(new ListItem("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        listItems.add(new ListItem("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        listItems.add(new ListItem("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        listItems.add(new ListItem("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        listItems.add(new ListItem("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        listItems.add(new ListItem("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        listItems.add(new ListItem("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        listItems.add(new ListItem("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        listItems.add(new ListItem("Come here.", "әnni'nem", R.raw.phrase_come_here));

        ListAdapter wordsAdapter = new ListAdapter(getActivity(), listItems, R.color.category_phrases);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(wordsAdapter);

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
}
