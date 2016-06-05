package com.haoye.eightpuzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView   mTvSteps;
    private final int  NUM_CNT     = 9;
    private int        mSpaceIndex = 0;
    private int        mStepCount  = 0;
    private TextView[] mTvList     = new TextView[NUM_CNT];
    private int[]      mNumbers    = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private int[]      mTvIds      = new int[]{R.id.tv1, R.id.tv2, R.id.tv3,
                                               R.id.tv4, R.id.tv5, R.id.tv6,
                                               R.id.tv7, R.id.tv8, R.id.tv9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        startNewGame();
    }

    private void initViews() {

        // init menu
        TextView tvNewGame = (TextView) findViewById(R.id.tvNewGame);
        TextView tvExit    = (TextView) findViewById(R.id.tvExit);
        tvNewGame.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        // init steps record
        mTvSteps = (TextView) findViewById(R.id.tvStep);
        // init numbers' view
        for (int i = 0; i < mTvList.length; i++) {
            mTvList[i] = (TextView) findViewById(mTvIds[i]);
            mTvList[i].setOnClickListener(this);
        }
    }

    private void resetParams() {
        // reset step count
        mStepCount = 0;
        // reset origin numbers
        int index1;
        int index2;
        for (int i = 0; i < 30; i++) {
            do {
                index1 = new Random().nextInt(NUM_CNT);
                index2 = new Random().nextInt(NUM_CNT);
            } while (index1 == index2);
            int temp = mNumbers[index1];
            mNumbers[index1] = mNumbers[index2];
            mNumbers[index2] = temp;
        }
        // get the space index
        for (int i = 0; i < mNumbers.length; i++) {
            if (mNumbers[i] == 0) {
                mSpaceIndex = i;
                break;
            }
        }
    }

    private void resetViewsNumber() {
        mTvSteps.setText(String.valueOf(getResources().getString(R.string.steps) + mStepCount));
        for (int i = 0; i < mTvList.length; i++) {
            if (i == mSpaceIndex) {
                mTvList[i].setText("");
            }
            else {
                mTvList[i].setText(String.valueOf(mNumbers[i]));
            }
        }
    }

    private void startNewGame() {
        resetParams();
        resetViewsNumber();
    }

    private boolean exchangeNumbers(int id) {
        for (int i = 0; i < NUM_CNT; i++) {
            if (id == mTvIds[i]
                    && ((i+1 == mSpaceIndex && mSpaceIndex != 3 && mSpaceIndex != 6)
                    || (i-1 == mSpaceIndex && mSpaceIndex != 2 && mSpaceIndex != 5)
                    || i+3 == mSpaceIndex
                    || i-3 == mSpaceIndex)) {
                //--- increase steps
                mStepCount++;
                //--- exchanges numbers
                int temp = mNumbers[i];
                mNumbers[i] = mNumbers[mSpaceIndex];
                mNumbers[mSpaceIndex] = temp;
                mSpaceIndex = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.tvNewGame:
            startNewGame();
            break;
        case R.id.tvExit:
            finish();
            break;
        default:
            if (exchangeNumbers(id)) {
                resetViewsNumber();
            }
            break;
        }
    }

}
