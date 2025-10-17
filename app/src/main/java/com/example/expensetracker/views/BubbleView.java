package com.expensetracker.app.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.expensetracker.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleView extends View {

    private List<Bubble> bubbles;
    private Paint paint;
    private Random random;
    private ValueAnimator animator;

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bubbles = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        random = new Random();

        // Create bubbles
        int[] colors = {
                getResources().getColor(R.color.bubble_purple),
                getResources().getColor(R.color.bubble_blue),
                getResources().getColor(R.color.bubble_pink),
                getResources().getColor(R.color.bubble_green),
                getResources().getColor(R.color.bubble_orange)
        };

        for (int i = 0; i < 15; i++) {
            bubbles.add(new Bubble(
                    random.nextInt(1080),
                    random.nextInt(1920),
                    random.nextInt(60) + 40,
                    colors[random.nextInt(colors.length)],
                    random.nextFloat() * 2 + 0.5f
            ));
        }

        // Animation
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(100);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            updateBubbles();
            invalidate();
        });
        animator.start();
    }

    private void updateBubbles() {
        for (Bubble bubble : bubbles) {
            bubble.y -= bubble.speed;
            bubble.x += Math.sin(bubble.y / 100f) * 2;

            if (bubble.y + bubble.radius < 0) {
                bubble.y = getHeight() + bubble.radius;
                bubble.x = random.nextInt(getWidth());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Bubble bubble : bubbles) {
            paint.setColor(bubble.color);
            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
        }
    }

    private static class Bubble {
        float x, y, radius, speed;
        int color;

        Bubble(float x, float y, float radius, int color, float speed) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
            this.speed = speed;
        }
    }
}