package mm.customerinformation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mm.customerinformation.R;
import mm.customerinformation.dataStructure.infoDS;

/**
 * Created by Az on 2017-08-08.
 */

public class ListAdapter extends ArrayAdapter<infoDS> {

    private TextView txt_phone, txt_date,txt_regNo;
    private Context context;

    public ListAdapter(Context context1, ArrayList<infoDS> objects) {
        super(context1, 0, objects);
        context = context1;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // TODO Auto-generated method stub
        infoDS obj = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false);
        }

        getID(convertView);
        setData(obj);

        return convertView;
    }

    private void getID(View view) {


        txt_date = view.findViewById(R.id.dateEt);
        txt_phone = view.findViewById(R.id.phoneEt);
        txt_regNo = view.findViewById(R.id.regNoEt);


    }

    @SuppressLint("SetTextI18n")
    private void setData(infoDS Obj1) {
        try {


            txt_date.setText(Obj1.Date + "");
            txt_phone.setText(Obj1.Phone + "");
            txt_regNo.setText(Obj1.RegNo + "");


        } catch (Exception e) {

        }

    }
}
