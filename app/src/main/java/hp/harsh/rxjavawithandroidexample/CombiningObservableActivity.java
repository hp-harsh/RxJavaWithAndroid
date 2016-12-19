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
import android.widget.ScrollView;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class CombiningObservableActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "CombiningObservable";

    private Subscription mSubscription;

    private ScrollView scrollView;

    private TextView mTxtResult;
    private Button mBtnCombine1, mBtnCombine2, mBtnCombine3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combining_observable);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        mBtnCombine1 = (Button) findViewById(R.id.btnCombine1);
        mBtnCombine2 = (Button) findViewById(R.id.btnCombine2);
        mBtnCombine3 = (Button) findViewById(R.id.btnCombine3);

        mBtnCombine1.setOnClickListener(this);
        mBtnCombine2.setOnClickListener(this);
        mBtnCombine3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnCombine1:
                combineLatestValues();
                break;
            case R.id.btnCombine2:
                combineValuesUsingMerge();
                break;
            case R.id.btnCombine3:
                combineValuesTogether();
                break;
        }

        // Scroll to first position to see result
        scrollView.scrollTo(5, 10);
    }

    private void combineLatestValues() {
        // Observable which emits each item of the array, one at a time
        Observable<String> observable1 = Observable.from(new String[]{"Fruit", "Vegetable", "Flower"});

        Observable<String> observable2 = Observable.from(new String[]{"Mango", "Potato", "Lotus"});

        Observable<String> observable = Observable.combineLatest(observable1, observable2, new Func2<String, String, String>() {
            @Override
            public String call(String string1, String string2) {
                return string1 + " " + string2;
            }
        });

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + s);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void combineValuesUsingMerge() {
        // Observable which emits each item of the array, one at a time
        Observable<String> observable1 = Observable.from(new String[]{"Fruit", "Vegetable", "Flower"});

        Observable<String> observable2 = Observable.from(new String[]{"Mango", "Potato", "Lotus"});

        Observable<String> observable = Observable.merge(observable1, observable2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + s);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void combineValuesTogether() {
        Observable<String> observableGoogle = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = "http://www.google.com";
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        Observable<String> observableYahoo = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = "http://www.yahoo.com";
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        observableGoogle.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        observableYahoo.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        Observable<String> observableZipped = Observable.zip(observableGoogle, observableYahoo, new Func2<String, String, String>() {
            @Override
            public String call(String google, String yahoo) {
                return google + " " + yahoo;
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                Log.i(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.i(TAG, "Zipped Emitted Observer " + s);

                // Print result
                mTxtResult.setText(mTxtResult.getText().toString() + "" + s);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        Subscription subscription = observableZipped.subscribe(observer);
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
        startActivity(new Intent(CombiningObservableActivity.this, MainActivity.class));
        finish();
    }
}
