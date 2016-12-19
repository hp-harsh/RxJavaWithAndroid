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
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by Harsh Patel on 12/10/2016.
 */

public class DeferObservableActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "DeferObservable";

    private Subscription mSubscription;

    private TextView mTxtResult;
    private Button mBtnDefer1, mBtnDefer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defer_observable);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        mBtnDefer1 = (Button) findViewById(R.id.btnDefer1);
        mBtnDefer2 = (Button) findViewById(R.id.btnDefer2);

        mBtnDefer1.setOnClickListener(this);
        mBtnDefer2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnDefer1:
                getCurrentTime();
                break;
            case R.id.btnDefer2:
                getCountryName();
                break;
        }
    }

    private void getCurrentTime() {
        // Observable which emits current time
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(CommonUtil.getTime());
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

    private void getCountryName() {
        // Observable which emits country names
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.from(new String[]{"USA", "AUSTRALIA", "CANADA", "INDIA"});
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
        startActivity(new Intent(DeferObservableActivity.this, MainActivity.class));
        finish();
    }
}
