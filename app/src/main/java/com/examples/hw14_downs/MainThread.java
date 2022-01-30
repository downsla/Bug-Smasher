package com.examples.hw14_downs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private Handler handler;
    private boolean isRunning = false;
    private int tx, ty;
    private static final Object lock = new Object();
    private boolean initialized, touched;
    private Context context;
    private Paint paint = new Paint(Color.BLACK);
    private final SharedPreferences prefs;
    private int score = 0;

    MainThread (SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        initialized = touched = false;
        this.context = context;
        handler = new Handler();
        paint.setTextSize(100);
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Assets.high_score = prefs.getInt("key_high_score", 0);
    }

    void setRunning(boolean b) {
        isRunning = b;
    }

    void setXY (int x, int y) {
        synchronized (lock) {
            tx = x;
            ty = y;
            touched = true;
        }
    }
    @Override
    public void run() {
        while(isRunning) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                render(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void LoadGraphics(Canvas canvas) {
        if(!initialized) {
            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug_1);
            int newWidth = (int) (canvas.getWidth() * 0.4f);
            float scaleFactor = (float) newWidth / bmp.getWidth();
            int newHeight = (int) (bmp.getHeight() * scaleFactor);
            Assets.bug_1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);

            bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug_2);
            Assets.bug_2 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);

            bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug_dead);
            Assets.bug_dead = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);

            bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.game);
            Assets.background = Bitmap.createScaledBitmap(bmp, canvas.getWidth(), canvas.getHeight(), false);

            bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
            Assets.life = Bitmap.createScaledBitmap(bmp, (newWidth / 2), (newHeight / 2), false);

            Assets.bug = new ArrayList<>();
            Assets.bug.add(new Bug());
            Assets.bug.add(new Bug());
        }
    }

    @SuppressLint("ApplySharedPref")
    private void render (Canvas canvas) {
        int x, y, i;
        if(!initialized) {
            LoadGraphics(canvas);
            initialized = true;
        }

        if(Assets.state == Assets.GameState.GettingReady) {
            canvas.drawBitmap(Assets.background, 0, 0, null);
            Assets.sp.play(Assets.sound_get_ready, 1, 1, 1, 0, 1);
            Assets.gameTimer = System.nanoTime() / 1000000000f;
            Assets.state = Assets.GameState.Starting;
        } else if(Assets.state == Assets.GameState.Starting) {
            canvas.drawBitmap(Assets.background, 0, 0, null);
            float currentTime = System.nanoTime() / 1000000000f;
            if(3 <= currentTime - Assets.gameTimer) {
                Assets.state = Assets.GameState.Running;
            }
        } else if(Assets.state == Assets.GameState.Running) {
            canvas.drawBitmap(Assets.background, 0, 0, null);
            int spacing = 64;
            int radius = (int) (canvas.getWidth() * 0.08f);
            y = 8;
            //scorebar
            canvas.drawText(Integer.toString(score), (y), radius + spacing, paint);
            x = canvas.getWidth() - radius - spacing;
            for(i = 0; i < Assets.livesLeft; i++) {
                canvas.drawBitmap(Assets.life, x, y, null);
                x -= radius * 2;
            }

            if(touched) {
                touched = false;
                boolean bugKilled = false;
                for(int j = 0; j < Assets.bug.size(); j++) {
                    bugKilled = Assets.bug.get(j).touched(tx, ty);
                    if(bugKilled) {
                        break;
                    }
                }
                if(bugKilled) {
                    Random random = new Random();
                    int r = random.nextInt(3);
                    System.out.println(r);
                    if(r == 0) {
                        Assets.sp.play(Assets.sound_squish_1, 1, 1, 1, 0, 1);
                    } else if(r == 1) {
                        Assets.sp.play(Assets.sound_squish_2, 1, 1, 1, 0, 1);
                    } else if(r == 2) {
                        Assets.sp.play(Assets.sound_squish_3, 1, 1, 1, 0, 1);
                    }
                    score++;
                } else {
                    Assets.sp.play(Assets.sound_thump, 1, 1, 1, 0, 1);
                }
            }
            for(int j = 0; j < Assets.bug.size(); j++) {
                Assets.bug.get(j).drawDead(canvas);
                Assets.bug.get(j).move(canvas);
                Assets.bug.get(j).birth(canvas);
            }

            float currentTime = System.nanoTime() / 1000000000f;
            if((Assets.bug.size() < (currentTime - Assets.gameTimer) / 30) && (Assets.bug.size() < 32)) {
                Assets.bug.add(new Bug());
            }

            if(Assets.livesLeft == 0) {
                Assets.state = Assets.GameState.GameEnding;
            }
        } else if(Assets.state == Assets.GameState.GameEnding) {
            if(Assets.high_score < score) {
                Assets.high_score = score;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("key_high_score", Assets.high_score);
                editor.commit();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "New High Score", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Assets.state = Assets.GameState.GameOver;
            Assets.sp.play(Assets.sound_game_over, 1, 1, 1, 0, 1);
        } else if(Assets.state == Assets.GameState.GameOver) {
            canvas.drawColor(Color.BLACK);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((Activity) context).finish();
                }
            }, 2000);
        }
    }
}