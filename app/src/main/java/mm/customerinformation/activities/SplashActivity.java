package mm.customerinformation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mm.customerinformation.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(2200);
                    }
                } catch (InterruptedException ignored) {
                }

                finally {
                    Intent mainIntent = new Intent(SplashActivity.this, PinActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        };

        mSplashThread.start();

    }
}
