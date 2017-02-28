package com.designers.kuwo.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SlideBar extends View {

    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // �Ƿ񻭳�����
    private boolean showBg = false;
    // ѡ�е���
    private int choose = -1;
    // ׼���õ�A~Z����ĸ����
    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};

    // ���췽��
    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // ��ȡ���͸�
        int width = getWidth();
        int height = getHeight()-150;
        // ÿ����ĸ�ĸ߶�
        int singleHeight = height / letters.length;
        if (showBg) {
            // ��������
            canvas.drawColor(Color.parseColor("#55000000"));
        }
        // ����ĸ
        for (int i = 0; i < letters.length; i++) {
            paint.setColor(Color.BLACK);
            // ���������ʽ
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30f);
            // �����һ�ѡ�У���һ����ɫ��
            if (i == choose) {
                paint.setColor(Color.parseColor("#3366cc"));
                paint.setFakeBoldText(true);
            }
            // Ҫ������ĸ��x,y����
            float posX = width / 2 - paint.measureText(letters[i]) / 2;
            float posY = i * singleHeight + singleHeight;
            // ������ĸ
            canvas.drawText(letters[i], posX, posY, paint);
            // �������û���
            paint.reset();
        }
    }

    /**
     * ����SlideBar��״̬
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // ����������ĸ������
        final int index = (int) (y / (getHeight()-150) * letters.length);
        // �����ϴε������ĸ��������oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                if (listenner != null) {
                    if (index <= 0) {
                        listenner.onTouchLetterChange(showBg, "A");
                    } else if (index > 0 && index < letters.length) {
                        listenner.onTouchLetterChange(showBg, letters[index]);
                    } else if (index >= letters.length) {
                        listenner.onTouchLetterChange(showBg, "Z");
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * �ص�������ע�������
     *
     * @param listenner
     */
    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }

    /**
     * SlideBar �ļ������ӿ�
     *
     * @author Folyd
     */
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(boolean isTouched, String s);
    }

}