package com.examples.hw14_downs;

import android.graphics.Bitmap;
import android.media.SoundPool;

import java.util.ArrayList;

class Assets {
    static SoundPool sp;
    static int sound_squish_1, sound_squish_2, sound_squish_3, sound_thump, sound_game_over, sound_get_ready, sound_eat, high_score, livesLeft;
    static Bitmap background, bug_1, bug_2, bug_dead, life;
    enum GameState {
        GettingReady,
        Starting,
        Running,
        GameEnding,
        GameOver,
    }
    static GameState state;
    static float gameTimer;
    static ArrayList<Bug> bug;

}
