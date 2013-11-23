package org.simet.quiz.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

public class QuizProgressDialog extends ProgressDialog implements ProgressObserver {

    private QuizProgressDialog self;
    private Handler handler;

    public QuizProgressDialog(Context context) {
        this(context, ProgressDialog.STYLE_HORIZONTAL);
    }

    public QuizProgressDialog(Context context, int theme) {
        super(context, theme);
        this.self = this;
        this.handler = new Handler(getContext().getMainLooper());
    }

    @Override
    public void processingStarted() {
        handler.post(new Runnable() {
            public void run() {
                self.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                self.setProgress(0);
                self.setMax(0);
                self.show();
            }
        });
    }

    @Override
    public void setMaxOperations(final int amount) {
        handler.post(new Runnable() {
            public void run() {
                self.setMax(amount);
            }
        });
    }

    @Override
    public void processedOperationChanged(final int value) {
        handler.post(new Runnable() {
            public void run() {
                if (value > self.getProgress()) {
                    self.setProgress(value);
                }
            }
        });
    }

    @Override
    public void processedFinished() {
        handler.post(new Runnable() {
            public void run() {
                self.cancel();
            }
        });
    }

}
