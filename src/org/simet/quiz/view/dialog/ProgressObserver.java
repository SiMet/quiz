package org.simet.quiz.view.dialog;

public interface ProgressObserver {

    public void setMaxOperations(int amount);

    public void processedOperationChanged(int value);

    public void processingStarted();

    public void processedFinished();
}
