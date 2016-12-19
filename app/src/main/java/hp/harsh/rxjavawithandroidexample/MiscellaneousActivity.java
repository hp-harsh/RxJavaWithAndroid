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
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Harsh Patel on 12/10/2016.
 */

public class MiscellaneousActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MiscellaneousActivity";

    private Subscription mSubscription;

    private TextView mTxtResult;
    private Button mBtnCreate1, mBtnCreate2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneos);

        mTxtResult = (TextView) findViewById(R.id.txtResult);

        mBtnCreate1 = (Button) findViewById(R.id.btnCreate1);
        mBtnCreate2 = (Button) findViewById(R.id.btnCreate2);

        mBtnCreate1.setOnClickListener(this);
        mBtnCreate2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Clear current subscription
        clearSubscription();

        // Clear previous result to show latest result
        mTxtResult.setText("");
        switch (view.getId()) {
            case R.id.btnCreate1:
                DelayedOperator();
                break;
            case R.id.btnCreate2:
                containsOperator();
                break;
        }
    }

    private void DelayedOperator() {
        Observable observable = Observable.just("Hello").doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "doOnNext " + s);
            }
        }).delay(5, TimeUnit.SECONDS).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "delayed doOnNext " + s);
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

        observable.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void containsOperator() {
        Observable<Boolean> observable = Observable.from(new String[]{"Mango is a fruit", "Mango is not a flower", "Potato is a vegetable", "Potato is not a flower", "Lotus is a flower"})
                .contains("Lotus is a flower");

        Observer<Boolean> observer = new Observer<Boolean>() {
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
            public void onNext(Boolean b) {
                // Called each time the observable emits data
                Log.i(TAG, "Emitted Observer " + b);

                // Print result
                mTxtResult.setText("Yes! Lotus is a flower");
            }
        };

        observable.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
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
        startActivity(new Intent(MiscellaneousActivity.this, MainActivity.class));
        finish();
    }
}
