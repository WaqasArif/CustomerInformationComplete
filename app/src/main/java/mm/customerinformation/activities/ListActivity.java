package mm.customerinformation.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import mm.customerinformation.R;
import mm.customerinformation.abstractClasses.DatabaseHandler;
import mm.customerinformation.adapters.ListAdapter;
import mm.customerinformation.dataStructure.infoDS;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchtxt;
    ListView listView;
    Button searchBtn;
    ArrayList<infoDS> fullList=new ArrayList<>();
    ArrayList<infoDS> fullListCopy=new ArrayList<>();
    ImageView backIv;
    DatabaseHandler db;
    ListAdapter listAdapter;
    boolean search=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchtxt=findViewById(R.id.searchEt0);
        listView=findViewById(R.id.listview);
        searchBtn=findViewById(R.id.searchBtn);
        backIv=findViewById(R.id.back);

        db=new DatabaseHandler(this);

        try {
            fullList=db.LoadCustomers("");
            fullListCopy.addAll(fullList);

            if (!fullListCopy.isEmpty()){
                listAdapter=new ListAdapter(getApplicationContext(),fullListCopy);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        try {
                            showDialogN(i);
                        }catch (Exception ignored){

                        }

                    }
                });
            }
        }catch (Exception ignored){

        }

        backIv.setOnClickListener(this);
        searchBtn.setOnClickListener(this);


    }

    @SuppressLint("SetTextI18n")
    private void showDialogN(final int position) {
        try {

            final Dialog dialog = new Dialog(Objects.requireNonNull(this));

            dialog.setContentView(R.layout.itemdetail);
            // dialog.setTitle("Item Detail");
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView name = dialog.findViewById(R.id.nameEt);
            TextView phone = dialog.findViewById(R.id.phoneEt);

            TextView register = dialog.findViewById(R.id.regNoEt);
            TextView entry = dialog.findViewById(R.id.entryNoEt);
            TextView description = dialog.findViewById(R.id.descEt);
           // TextView description = dialog.findViewById(R.id.descEt);


            Button delete = dialog.findViewById(R.id.ID_delbtn);

            name.setText(fullListCopy.get(position).Name);

            phone.setText(fullListCopy.get(position).Phone+"");

            register.setText(fullListCopy.get(position).RegNo+"");

            entry.setText(fullListCopy.get(position).EntryNo+"");

            description.setText(fullListCopy.get(position).Descp+"");


            delete.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View v) {
                    /* TODO Auto-generated method stub */
                    try {

                        new AlertDialog.Builder(ListActivity.this)
                                .setTitle(R.string.delete)
                                .setMessage(R.string.sureD).setCancelable(true).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (db.DeleteCustomer(fullListCopy.get(position).id)){
                                    fullListCopy.remove(position);
                                    listAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();

                    } catch (Exception e) {
                        //Log.e(ActivityName + " |delete onclick", e.getMessage());
                        dialog.dismiss();
                    }

                }
            });

            dialog.show();

        } catch (Exception e) {
            // TODO: handle exception
            String error = e.getMessage();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchBtn:
                if (!searchtxt.getText().toString().equals("") && searchtxt.getText().toString().length()==11){
                    fullListCopy.clear();


                    fullListCopy=db.LoadCustomers(searchtxt.getText().toString());
                    if (!fullListCopy.isEmpty()){
                        search=false;
                        listAdapter=new ListAdapter(getApplicationContext(),fullListCopy);
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }else {
                        search=true;
                        fullListCopy.addAll(fullList);
                        Toast.makeText(getApplicationContext(),"No Result found!",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Number!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.back:
                if (search){
                    onBackPressed();

                }else {
                    searchtxt.setText("");
                    fullListCopy.clear();
                    fullListCopy.addAll(fullList);
                    listAdapter=new ListAdapter(getApplicationContext(),fullListCopy);
                    listView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                }

                break;
        }
    }
}
