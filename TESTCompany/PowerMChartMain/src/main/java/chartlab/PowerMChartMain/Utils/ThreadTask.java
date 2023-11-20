package chartlab.PowerMChartMain.Utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public abstract class ThreadTask<T1,T2> implements Runnable {
    // Argument
    T1 mArgument;

    // Result
    T2 mResult;

    // Handle the result
    public final int WORK_DONE = 0;
    @SuppressLint("HandlerLeak")
    Handler mResultHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            // Call onPostExecute
            onPostExecute();
        }
    };

    // Execute
    final public void execute() {

        // Call onPreExecute
        onPreExecute();

        // Begin thread work
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // Call doInBackground
        doInBackground();

        // Notify main thread that the work is done
        mResultHandler.sendEmptyMessage(WORK_DONE);
    }

    // onPreExecute
    protected abstract void onPreExecute();

    // doInBackground
    protected abstract void doInBackground();

    // onPostExecute
    protected abstract void onPostExecute();

    protected abstract void onCancelled();

    protected abstract void onProgressUpdate();
}
