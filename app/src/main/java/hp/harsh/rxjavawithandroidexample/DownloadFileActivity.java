/*
* Copyright 2016 Harsh Patel
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package hp.harsh.rxjavawithandroidexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "DownloadFileActivity";

    private static String IMAGE_URL = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";

    private Subscription mSubscription;

    private Button mBtnDownload;
    private TextView mTxtProgress;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        // Initialize component
        mBtnDownload = (Button) findViewById(R.id.btnClick);
        mTxtProgress = (TextView) findViewById(R.id.txtProgressValue);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Set max progress
        mProgressBar.setMax(100);

        mBtnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClick:
                // Start downloading
                startDownloadProcess();
                break;
        }
    }

    private void startDownloadProcess() {
        mSubscription = downloadFile("" + IMAGE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        // Called when the observable has no more data to emit
                        Log.i(TAG, "onCompleted");
                        Toast.makeText(DownloadFileActivity.this, "" + getResources().getString(R.string.complete_message), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Called when the observable encounters an error
                        Log.i(TAG, "onError" + e);
                    }

                    @Override
                    public void onNext(Integer progressValue) {
                        // Called each time the observable emits data
                        Log.i(TAG, "Emitted Observer " + progressValue);
                        // Update text value
                        mTxtProgress.setText(progressValue + "/100");
                        mProgressBar.setProgress(progressValue);
                    }
                });
    }

    public static Observable<Integer> downloadFile(final String fileUrl) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int count;

                try {

                    URL url = new URL("" + fileUrl);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    int lenghtOfFile = connection.getContentLength();
                    Log.d(TAG, "Lenght of file: " + lenghtOfFile);

                    //set the path where we want to save the file
                    File fileFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "RxAndroid");

                    if (!fileFolder.exists()) {
                        fileFolder.mkdirs();
                    }

                    //create a new file, to save the downloaded file
                    String fileName = "downloaded_file" + System.currentTimeMillis() + ".png";
                    File file = new File(fileFolder, fileName);

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(file);

                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;

                        // calculate latest progress value
                        int totalDownload = (int) ((total * 100) / lenghtOfFile);

                        // Emit calculated progress value
                        subscriber.onNext(totalDownload);

                        // write file data to output file
                        output.write(data, 0, count);
                    }

                    // Indicates that task has completed
                    subscriber.onCompleted();

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    // Indicates that there is an error
                    subscriber.onError(e);
                }
            }
        })
                // This filter is used to remove duplicate progress value which is emitted while writing data in output buffer
                .distinct(new Func1<Integer, Object>() {
                    @Override
                    public Object call(Integer integer) {
                        return integer; // remove all duplicate progress values
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()); // Download is done in other thread
    }

    private void clearSubscription() {
        // To detach an observer from its observable while the observable is still emitting data, need to unsubscribe
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        // Clear current subscription
        clearSubscription();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Back to caller Activity
        startActivity(new Intent(DownloadFileActivity.this, MainActivity.class));
        finish();
    }
}
