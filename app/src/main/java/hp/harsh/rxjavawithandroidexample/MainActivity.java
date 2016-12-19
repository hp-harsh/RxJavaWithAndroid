

package hp.harsh.rxjavawithandroidexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8, mBtn9, mBtn10, mBtn11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
        mBtn7 = (Button) findViewById(R.id.btn7);
        mBtn8 = (Button) findViewById(R.id.btn8);
        mBtn9 = (Button) findViewById(R.id.btn9);
        mBtn10 = (Button) findViewById(R.id.btn10);
        mBtn11 = (Button) findViewById(R.id.btn11);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);
        mBtn9.setOnClickListener(this);
        mBtn10.setOnClickListener(this);
        mBtn11.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, CreateObservableActivity.class));
                finish();
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, DeferObservableActivity.class));
                finish();
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this, FromObservableActivity.class));
                finish();
                break;
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this, IntervalObservableActivity.class));
                finish();
                break;
            case R.id.btn5:
                startActivity(new Intent(MainActivity.this, JustObservableActivity.class));
                finish();
                break;
            case R.id.btn6:
                startActivity(new Intent(MainActivity.this, RangeObservableActivity.class));
                finish();
                break;
            case R.id.btn7:
                startActivity(new Intent(MainActivity.this, TimerObservableActivity.class));
                finish();
                break;
            case R.id.btn8:
                startActivity(new Intent(MainActivity.this, FilteringObservableActivity.class));
                finish();
                break;
            case R.id.btn9:
                startActivity(new Intent(MainActivity.this, CombiningObservableActivity.class));
                finish();
                break;
            case R.id.btn10:
                startActivity(new Intent(MainActivity.this, MiscellaneousActivity.class));
                finish();
                break;
            case R.id.btn11:
                startActivity(new Intent(MainActivity.this, DownloadFileActivity.class));
                finish();
                break;
        }
    }
}
