package com.examples.hw14_downs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread t = null;
    Context context;

    public MainView(Context context) {
        super(context);
        this.context = context;
        SurfaceHolder holder = getHolder();
        this.context = context;
        Assets.state = Assets.GameState.GettingReady;
        Assets.livesLeft = 3;
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
		    Assets.sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            Assets.sp = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        Assets.sound_squish_1 = Assets.sp.load(context, R.raw.squish_1, 1);
        Assets.sound_squish_2 = Assets.sp.load(context, R.raw.squish_2, 1);
        Assets.sound_squish_3 = Assets.sp.load(context, R.raw.squish_3, 1);
        Assets.sound_eat = Assets.sp.load(context, R.raw.eat, 1);
        Assets.sound_get_ready = Assets.sp.load(context, R.raw.get_ready, 1);
        Assets.sound_game_over = Assets.sp.load(context, R.raw.game_over, 1);
        Assets.sound_thump = Assets.sp.load(context, R.raw.thump, 1);
        holder.addCallback(this);
    }

    public void pause() {
        t.setRunning(false);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        t = null;
    }

    public void resume() {
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x, y;
        int action = event.getAction();
        x = event.getX();
        y = event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            if (t != null)
                t.setXY ((int)x, (int)y);
        }
        return true;
    }
    @Override
    public void surfaceCreated (SurfaceHolder holder) {
        if (t == null) {
            t = new MainThread(holder, context);
            t.setRunning(true);
            t.start();
            setFocusable(true);
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {
    }
	@Override
    public void surfaceDestroyed(SurfaceHolder sh) {
    }
}
