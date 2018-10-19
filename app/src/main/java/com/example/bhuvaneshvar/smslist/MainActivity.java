package com.example.bhuvaneshvar.smslist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asking for permission on run
        requestSmsPermission();


        ListView list1 = (ListView)findViewById(R.id.listv1);
        context = this;
        //arrayColumns is the column name in your cursor where you're getting the data
        // here we are displaying  SMSsender Number i.e. address and SMSBody i.e. body
        String [] arrayColum = new String []{"Address","body"};

        /*
        arrayViewID contains the id of textViews
        you can add more Views as per Requirement
        textViewSMSSender is connected to "address" of arrayColumns
        textViewMessageBody is connected to "body"of arrayColumns
        */
        int [] arrayViewId = new int[]{R.id.textViewSMSSender,R.id.textViewMessageBody};

        Cursor cursor;
        cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,R.layout.listitmcontainer,cursor,arrayColum,arrayViewId);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when user clicks on ListView Item , onItemClick is called
                // with position and View of the item which is clicked
                // we can use the position parameter to get index of clicked item
                TextView textViewSMSSender=(TextView)findViewById(R.id.textViewSMSSender);
                TextView textViewSMSBody=(TextView)findViewById(R.id.textViewMessageBody);
                String smsSender=textViewSMSSender.getText().toString();
                String smsBody=textViewSMSBody.getText().toString();

                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("SMS From : "+smsSender);
                dialog.setIcon(android.R.drawable.ic_dialog_info);//android pre-icon used
                dialog.setMessage(smsBody);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                dialog.dismiss();
                                return;
                            }
                        });
                dialog.show();
            }
        });
    }

    //SMS_READ RUN TIME PERMISSION
    private void requestSmsPermission()
    {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED)//checks permission
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
            Toast.makeText(getApplicationContext(), "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
        }
    }

}
