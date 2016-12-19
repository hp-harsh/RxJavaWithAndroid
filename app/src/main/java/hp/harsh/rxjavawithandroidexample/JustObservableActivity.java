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

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JustObservableActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "JustObservable";

    private TextView mTxtResult;
    private Button mBtnJust1, mBtnJust2, mBtnJust3;

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_observable);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        mBtnJust1 = (Button) findViewById(R.id.btnJust1);
        mBtnJust2 = (Button) findViewById(R.id.btnJust2);
        mBtnJust3 = (Button) findViewById(R.id.btnJust3);

        mBtnJust1.setOnClickListener(this);
        mBtnJust2.setOnClickListener(this);
        mBtnJust3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnJust1:
                justPrintString();
                break;
            case R.id.btnJust2:
                justCallMethod();
                break;
            case R.id.btnJust3:
                justSumIntegers();
                break;
        }
    }

    private void justPrintString() {
        // Observable which emits String
        Observable<String> observable = Observable.just("Hello");

        // Observer catch emitted data or error.
        Observer<String> observer = new Observer<String>() {
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
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + s);

                // Print result
                mTxtResult.setText("" + s);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void justCallMethod() {
        // Observable which emits current time
        Observable<String> observable = Observable.just(CommonUtil.getTime());

        // Observer catch emitted data or error.
        Observer<String> observer = new Observer<String>() {
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
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + s);

                // Print result
                mTxtResult.setText("" + s);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void justSumIntegers() {
        // Observable which emits Sum of integers
        Observable<Integer> observable = Observable.just(sumMe());

        // Observer catch emitted data or error.
        Observer<Integer> observer = new Observer<Integer>() {
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
            public void onNext(Integer i) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + i);

                // Print result
                mTxtResult.setText("1 + 5 =" + 6);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Integer sumMe() {
        return 1 + 5;
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
        startActivity(new Intent(JustObservableActivity.this, MainActivity.class));
        finish();
    }
}
