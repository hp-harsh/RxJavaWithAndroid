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

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Harsh Patel on 12/10/2016.
 */

public class FilteringObservableActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "FilteringObservable";

    private Subscription mSubscription;

    private ScrollView scrollView;

    private TextView mTxtResult;
    private Button mBtnOperator1, mBtnOperator2, mBtnOperator3, mBtnOperator4, mBtnOperator5, mBtnOperator6, mBtnOperator7, mBtnOperator8, mBtnOperator9, mBtnOperator10, mBtnOperator11, mBtnOperator12, mBtnOperator13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering_observable);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        mBtnOperator1 = (Button) findViewById(R.id.btnOperator1);
        mBtnOperator2 = (Button) findViewById(R.id.btnOperator2);
        mBtnOperator3 = (Button) findViewById(R.id.btnOperator3);
        mBtnOperator4 = (Button) findViewById(R.id.btnOperator4);
        mBtnOperator5 = (Button) findViewById(R.id.btnOperator5);
        mBtnOperator6 = (Button) findViewById(R.id.btnOperator6);
        mBtnOperator7 = (Button) findViewById(R.id.btnOperator7);
        mBtnOperator8 = (Button) findViewById(R.id.btnOperator8);
        mBtnOperator9 = (Button) findViewById(R.id.btnOperator9);
        mBtnOperator10 = (Button) findViewById(R.id.btnOperator10);
        mBtnOperator11 = (Button) findViewById(R.id.btnOperator11);
        mBtnOperator12 = (Button) findViewById(R.id.btnOperator12);
        mBtnOperator13 = (Button) findViewById(R.id.btnOperator13);

        mBtnOperator1.setOnClickListener(this);
        mBtnOperator2.setOnClickListener(this);
        mBtnOperator3.setOnClickListener(this);
        mBtnOperator4.setOnClickListener(this);
        mBtnOperator5.setOnClickListener(this);
        mBtnOperator6.setOnClickListener(this);
        mBtnOperator7.setOnClickListener(this);
        mBtnOperator8.setOnClickListener(this);
        mBtnOperator9.setOnClickListener(this);
        mBtnOperator10.setOnClickListener(this);
        mBtnOperator11.setOnClickListener(this);
        mBtnOperator12.setOnClickListener(this);
        mBtnOperator13.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnOperator1:
                squareOfValue();
                break;
            case R.id.btnOperator2:
                getDistinctValue();
                break;
            case R.id.btnOperator3:
                removeSingeValue();
                break;
            case R.id.btnOperator4:
                getDistinctValue();
                break;
            case R.id.btnOperator5:
                findAllOddValue();
                break;
            case R.id.btnOperator6:
                RemoveFirstValue();
                break;
            case R.id.btnOperator7:
                IgnoreValues();
                break;
            case R.id.btnOperator8:
                removeLastValues();
                break;
            case R.id.btnOperator9:
                getRecentValueWithinTimePeriod();
                break;
            case R.id.btnOperator10:
                skipFirstTwoValue();
                break;
            case R.id.btnOperator11:
                skipLastTwoValue();
                break;
            case R.id.btnOperator12:
                getFirstTwoValue();
                break;
            case R.id.btnOperator13:
                getLastTwoValue();
                break;
        }

        // Scroll to first position to see result
        scrollView.scrollTo(5, 10);
    }

    private void squareOfValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5});

        // map operator returns a new Observable, it doesn’t change the original Observable
        observable = observable.map(new Func1<Integer, Integer>() { // Input value as a integer and output value as a integer
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        });

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
            public void onNext(Integer s) {
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

    private void getDistinctValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 1, 3, 3, 4, 2, 5, 5, 6})
                .distinct();

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void removeSingeValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .elementAt(2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void findAllOddValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 1;
                    }
                });

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void RemoveFirstValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .first();

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void IgnoreValues() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .ignoreElements();

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void removeLastValues() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .last();

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getRecentValueWithinTimePeriod() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .sample(2, TimeUnit.SECONDS);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void skipFirstTwoValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .skip(2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void skipLastTwoValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .skipLast(2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getFirstTwoValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .take(2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
            }
        };

        // Subscribe observer. Observable emits data from io thread and emitted data is observe from main thread
        mSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getLastTwoValue() {
        // Observable which emits each item of the array, one at a time
        Observable<Integer> observable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6})
                .takeLast(2);

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
                mTxtResult.setText(mTxtResult.getText().toString() + "\n" + i);
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
        startActivity(new Intent(FilteringObservableActivity.this, MainActivity.class));
        finish();
    }
}
