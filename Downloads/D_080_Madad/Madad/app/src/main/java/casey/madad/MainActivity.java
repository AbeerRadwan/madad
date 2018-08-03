package casey.madad;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import java.util.Date;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import android.widget.TextView;

import java.util.ArrayList;

import casey.app20.R;

public class MainActivity extends Activity {
    Date dNow = new Date( );
    SimpleDateFormat ft =
            new SimpleDateFormat ("'at' hh:mm:ss");
    ArrayList<numMsg> ItemScroll=new ArrayList<numMsg>();
    IntentFilter intentFilter;
static  int count = 0;
String delivermsg ;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          //  Toast.makeText(getBaseContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();




           // Toast.makeText(getBaseContext(), intent.getExtras().getString("sms1"), Toast.LENGTH_SHORT).show();
            String msg = "الحالات:";
            String msg1 = "1-تسمم غذائي";
            String msg2 = "2-صرع";
            String msg3 = "3-ضربة شمس";
            String msg4 = "4-اغماء";
            String msg5 = "5-اختناق";
            String msg6 = "6-أزمة قلبية";
            /*
            String msg7 = "7-جروح/كسور";
            String msg8 = "8-لا أعلم";
            */

   String full_menu = msg+"\n"+msg1+"\n"+msg2+"\n"+msg3+"\n"+msg4+"\n"+msg5+"\n"+msg6;



            if(count==0) {

      String choice = (intent.getExtras().getString("sms1"));



             //  Toast.makeText(getBaseContext(), "هةهبتتينتاليتنلانت", Toast.LENGTH_SHORT).show();
           sendSMS("6505551212",full_menu);




    }
    else{

        String choice = (intent.getExtras().getString("sms1"));
        switch (choice) {

            case "1":

               delivermsg = "تسمم غذائي";
                break;
            case "2":

                delivermsg ="صرع";
                break;

            case "3":

                delivermsg ="ضربة شمس";
                break;
            case "4":

                delivermsg ="اغماء";
                break;
            case "5":

                delivermsg ="اختناق";
                break;
            case "6":

                delivermsg ="أزمة قلبية";
                break;
            case "7":

                delivermsg ="جروح/كسور";
                break;
            case "8":

                delivermsg ="لا أعلم";
                break;


        }


                ItemScroll.add(new numMsg("Portland ukanda",delivermsg,ft.format(dNow)));
                LinearLayout line= (LinearLayout) findViewById(R.id.line);
                View view ;
               // LayoutInflater inflater=LayoutInflater.from(this);
                LayoutInflater layoutflater=getLayoutInflater();
                view=layoutflater.inflate(R.layout.new_item_list,null);
                TextView taskn=(TextView)view.findViewById(R.id.msgtype);
                taskn.setText(delivermsg);
                TextView taskt=(TextView)view.findViewById(R.id.time);

                taskt.setText(ft.format(dNow));
                line.setPadding(2,2,2,2);
                line.setTag(ItemScroll.size());



                line.addView(view);


    }


          //  if((intent.getExtras().getString("sms1")).equals("555")){
            //    Toast.makeText(context,"im in", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getBaseContext(), intent.getExtras().getString("sms"), Toast.LENGTH_SHORT).show();
/*
                sendSMS(intent.getExtras().getString("sms"), "Hala");
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.putExtra("address", "Recipient Number Here");
                i.putExtra("sms_body", "Your Message Here");
                i.setType("vnd.android-dir/mms-sms");
                startActivity(i);*/


           // }
            count++;
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendSMS("6505551212", "مرحباً بك في خدمة مدد : " +
                "\n1-مٌسعف\n 2-أقرب صيدلية");

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        LinearLayout line= (LinearLayout) findViewById(R.id.line);
        View view ;
        for(int i = 0; i< ItemScroll.size(); i++){
            LayoutInflater layoutflater=getLayoutInflater();
            view=layoutflater.inflate(R.layout.new_item_list,null);
            TextView taskn=(TextView)view.findViewById(R.id.msgtype);
            TextView taskt=(TextView)view.findViewById(R.id.time);
            taskn.setText(ItemScroll.get(i).msg);
            taskt.setText(ItemScroll.get(i).time);
            line.setPadding(2,2,2,2);
                line.setTag(i);
            line.addView(view);

        }
//open detail

    line.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int id = (int)view.getTag();



            Intent move=new Intent(MainActivity.this,detailed_msg.class);
            //  TextView t = (TextView)view.findViewById(R.id.msgtype) ;
          //  Toast.makeText(getBaseContext(), ItemScroll.get(id-1).msg, Toast.LENGTH_SHORT).show();

            String y = ItemScroll.get(id-1).msg;
            move.putExtra(y.toString(),"msgType".toString());
            move.putExtra(ItemScroll.get(id-1).time,"msgTime");

            startActivity(move);
        }
    });








    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

    private void sendSMS(String phoneNumber, String message) {

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }

                }
            }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
                }
            }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // for display item on horizontal scroll

        LinearLayout line= (LinearLayout) findViewById(R.id.line);
        View view ;
        LayoutInflater inflater=LayoutInflater.from(this);
        for(int i = 0; i< ItemScroll.size(); i++){
            LayoutInflater layoutflater=getLayoutInflater();
            view=layoutflater.inflate(R.layout.new_item_list,null);
            TextView taskn=(TextView)view.findViewById(R.id.msgtype);
            taskn.setText(ItemScroll.get(i).msg);

            line.setPadding(2,2,2,2);

            line.addView(view);
        }


        return true;

    }

}