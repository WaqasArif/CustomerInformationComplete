package mm.customerinformation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mm.customerinformation.R;

public class PinActivity extends AppCompatActivity implements View.OnClickListener {

    TextView pinHead;
    EditText pinM,pinC;
    Button saveBtn;
    SharedPreferences sharedPreferences;
    int pin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        pin=sharedPreferences.getInt("pin",0);

        pinHead=findViewById(R.id.tv1);
        pinM=findViewById(R.id.pinMain);
        pinC=findViewById(R.id.pinMain2);
        saveBtn=findViewById(R.id.addCust);

        if (pin!=0){
            pinHead.setVisibility(View.INVISIBLE);
            pinC.setVisibility(View.INVISIBLE);
        }else {
            pinHead.setVisibility(View.VISIBLE);
            pinC.setVisibility(View.VISIBLE);
        }

        saveBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (pinM.getText().toString().length() == 4){
            int pinned= Integer.parseInt(pinM.getText().toString());
            if (pin==0){
                if (pinC.getText().toString().equals(String.valueOf(pinned))){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("pin", pinned);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Pin Created Successfully",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(PinActivity.this,MainActivity.class));

                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),"Pin does not match",Toast.LENGTH_LONG).show();
                }
            }else {
                if (pinned==pin){
                    Toast.makeText(getApplicationContext(),"Logged In!",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(PinActivity.this,MainActivity.class));

                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Wrong Pin!",Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(),"Invalid Pin",Toast.LENGTH_LONG).show();
        }
    }
}
