package org.simet.quiz.activities;

public interface ProgressObserver {

    public void setMaxOperations(int amount);

    public void processedOperationChanged(int value);

    public void processingStarted();

    public void processedFinished();
}
