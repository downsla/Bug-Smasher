package com.examples.hw14_downs;

import android.graphics.Canvas;

class Bug {
    enum BugState {
        Dead,
        ComingBackToLife,
        Alive,
        DrawDead,
    }

    private BugState state;
    private int x, y;
    private float timeToBirth, startBirthTimer, deathTime, animateTimer;
    private double speed;

    Bug() {
        state = BugState.Dead;
    }

    void birth(Canvas canvas) {
        if(state == BugState.Dead) {
            state = BugState.ComingBackToLife;
            timeToBirth = (float) Math.random() * 5;
            startBirthTimer = System.nanoTime() / 1000000000f;
        } else if(state == BugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;
            if(timeToBirth <= curTime - startBirthTimer) {
                state = BugState.Alive;
                x = (int) (Math.random() * canvas.getWidth());
                if(x < Assets.bug_1.getWidth() / 2) {
                    x = Assets.bug_1.getWidth() / 2;
                } else if(canvas.getWidth() - (Assets.bug_1.getWidth() / 2) < x) {
                    x = canvas.getWidth() - (Assets.bug_1.getWidth() / 2);
                }
                y = 0;
                speed = (int) (Math.random() * (canvas.getHeight() / 8)) + (canvas.getHeight() / 8);
                animateTimer = curTime;
            }
        }
    }

    void move(Canvas canvas) {
        if(state == BugState.Alive) {
            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - animateTimer;
            animateTimer = curTime;
            y += speed * elapsedTime * (1 + ((curTime - Assets.gameTimer) / 600));
            long curTime2 = System.currentTimeMillis() / 100 % 10;
            if(curTime2 % 2 == 0) {
                canvas.drawBitmap(Assets.bug_1, (float) (x - Assets.bug_1.getWidth() / 2), (float) (y - Assets.bug_1.getHeight() / 2), null);
            } else {
                canvas.drawBitmap(Assets.bug_2, (float) (x - Assets.bug_1.getWidth() / 2), (float) (y - Assets.bug_1.getHeight() / 2), null);
            }
            if(canvas.getHeight() <= y) {
                state = BugState.Dead;
                Assets.sp.play(Assets.sound_eat, 1, 1, 1, 0, 1);
                Assets.livesLeft--;
            }
        }
    }

    boolean touched(int touchx, int touchy) {
        boolean touched = false;
        if(state == BugState.Alive) {
            float dis = (float) Math.sqrt((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y));
            if(dis <= Assets.bug_1.getWidth() * 0.2f) {
                state = BugState.DrawDead;
                touched = true;
                deathTime = System.nanoTime() / 1000000000f;
            }
        }
        return touched;
    }

    void drawDead(Canvas canvas) {
        if(state == BugState.DrawDead) {
            canvas.drawBitmap(Assets.bug_dead, (float) (x - Assets.bug_1.getWidth() / 2), (float) (y - Assets.bug_1.getHeight() / 2), null);
            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;
            if(4 < timeSinceDeath) {
                state = BugState.Dead;
            }
        }
    }
}
