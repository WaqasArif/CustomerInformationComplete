package mm.customerinformation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mm.customerinformation.R;
import mm.customerinformation.abstractClasses.DatabaseHandler;
import mm.customerinformation.dataStructure.infoDS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText nametxt,phonetxt,desctxt,regtxt,entrytxt;
    Button searchBtn,viewBtn,addBtn;
    TextView recordsTv;
    DatabaseHandler db;
    ArrayList<infoDS> allCust=new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(this);


        nametxt=findViewById(R.id.nameEt);
        phonetxt=findViewById(R.id.phoneEt);
        regtxt=findViewById(R.id.regNoEt);
        desctxt=findViewById(R.id.descEt);
        entrytxt=findViewById(R.id.entryNoEt);

        recordsTv=findViewById(R.id.count);

        searchBtn=findViewById(R.id.seeAll);
        viewBtn=findViewById(R.id.viewCust);
        addBtn=findViewById(R.id.addCust);

        allCust=db.LoadCustomers("");

        if (!allCust.isEmpty()){
            int s=allCust.size();
            recordsTv.setText("Records : "+ s);
            recordsTv.setTextColor(getResources().getColor(R.color.white));
        }else {
            recordsTv.setText("No Record Found!");
            recordsTv.setTextColor(getResources().getColor(R.color.red));
        }

        searchBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.viewCust:
            case R.id.seeAll:
                if (!allCust.isEmpty()){
                    startActivity(new Intent(MainActivity.this,ListActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(),"No Record to Show!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.addCust:
                if (!nametxt.getText().toString().equals("") && !regtxt.getText().toString().equals("") && !entrytxt.getText().toString().equals("")){
                    if (phonetxt.getText().toString().length() == 11){

                        infoDS ds=new infoDS();
                        ds.Name=nametxt.getText().toString();
                        ds.Phone=phonetxt.getText().toString();
                        ds.RegNo=regtxt.getText().toString();
                        ds.Descp=desctxt.getText().toString();
                        ds.EntryNo=entrytxt.getText().toString();

                        try {

                            if (db.AddCustomer(ds)){
                                Toast.makeText(getApplicationContext(),"Successfully Added!",Toast.LENGTH_LONG).show();
                                resetFields();
                            }else {
                                Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception ignored){

                        }


                    }else {
                        Toast.makeText(getApplicationContext(),"Invalid Mobile No",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Name or Registry Id",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    @SuppressLint("SetTextI18n")
    private void resetFields() {
        nametxt.setText("");
        phonetxt.setText("");
        regtxt.setText("");
        desctxt.setText("");
        entrytxt.setText("");

        allCust.clear();

        allCust=db.LoadCustomers("");

        if (!allCust.isEmpty()){
            int s=allCust.size();
            recordsTv.setText("Records : "+ s);
            recordsTv.setTextColor(getResources().getColor(R.color.white));
        }else {
            recordsTv.setText("No Record Found!");
            recordsTv.setTextColor(getResources().getColor(R.color.red));
        }
    }


    @Override
    public void onResume(){
        super.onResume();

        if (db!=null){
            allCust.clear();

            allCust=db.LoadCustomers("");

            if (!allCust.isEmpty()){
                int s=allCust.size();
                recordsTv.setText("Records : "+ s);
                recordsTv.setTextColor(getResources().getColor(R.color.white));
            }else {
                recordsTv.setText("No Record Found!");
                recordsTv.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }
}
