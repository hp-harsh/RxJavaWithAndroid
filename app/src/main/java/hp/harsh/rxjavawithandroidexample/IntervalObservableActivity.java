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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IntervalObservableActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "IntervalObservable";

    private TextView mTxtResult;
    private Button mBtnInterval1, mBtnInterval2, mBtnInterval3;

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_observable);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        mBtnInterval1 = (Button) findViewById(R.id.btnInterval1);
        mBtnInterval2 = (Button) findViewById(R.id.btnInterval2);
        mBtnInterval3 = (Button) findViewById(R.id.btnInterval3);

        mBtnInterval1.setOnClickListener(this);
        mBtnInterval2.setOnClickListener(this);
        mBtnInterval3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnInterval1:
                getCurrentTimeEverySecond();
                break;
            case R.id.btnInterval2:
                getCurrentTimeAfterEvery5Second();
                break;
            case R.id.btnInterval3:
                getCurrentTimeAfter5SecondsAndUpdateEverySecond();
                break;
        }
    }

    private void getCurrentTimeEverySecond() {
        // Observable which emits current time every second
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);

        // Observer catch emitted data or error.
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                Log.i(TAG, "onError" + e);
            }

            @Override
            public void onNext(Long s) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + CommonUtil.getTime());

                // Print result
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + CommonUtil.getTime());
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getCurrentTimeAfterEvery5Second() {
        // Observable which emits current time after every 5 seconds
        Observable<Long> observable = Observable.interval(5, TimeUnit.SECONDS);

        // Observer catch emitted data or error.
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                Log.i(TAG, "onError" + e);
            }

            @Override
            public void onNext(Long s) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + CommonUtil.getTime());

                // Print result
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + CommonUtil.getTime());
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getCurrentTimeAfter5SecondsAndUpdateEverySecond() {
        // Observable which emits current time after 5 second and update at every second
        Observable<Long> observable = Observable.interval(5, 1, TimeUnit.SECONDS);

        // Observer catch emitted data or error.
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                Log.i(TAG, "onError" + e);
            }

            @Override
            public void onNext(Long s) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + CommonUtil.getTime());

                // Print result
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + CommonUtil.getTime());
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
        startActivity(new Intent(IntervalObservableActivity.this, MainActivity.class));
        finish();
    }
}
