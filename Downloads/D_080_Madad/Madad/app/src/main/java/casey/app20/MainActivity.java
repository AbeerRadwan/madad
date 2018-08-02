package casey.app20;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.TextView;

public class MainActivity extends Activity {
    IntentFilter intentFilter;
static  int count = 0;
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
        TextView SMSes = (TextView) findViewById(R.id.address);
        String choice = (intent.getExtras().getString("sms1"));
        switch (choice) {

            case "1":

                SMSes.setText("تسمم غذائي");
                break;
            case "2":

                SMSes.setText("صرع");
                break;

            case "3":

                SMSes.setText("ضربة شمس");
                break;
            case "4":

                SMSes.setText("اغماء");
                break;
            case "5":

                SMSes.setText("اختناق");
                break;
            case "6":

                SMSes.setText("أزمة قلبية");
                break;
            case "7":

                SMSes.setText("جروح/كسور");
                break;
            case "8":

                SMSes.setText("لا أعلم");
                break;


        }

    }


            if((intent.getExtras().getString("sms1")).equals("555")){
                Toast.makeText(context,"im in", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getBaseContext(), intent.getExtras().getString("sms"), Toast.LENGTH_SHORT).show();

                sendSMS(intent.getExtras().getString("sms"), "Hala");
            /*    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.putExtra("address", "Recipient Number Here");
                i.putExtra("sms_body", "Your Message Here");
                i.setType("vnd.android-dir/mms-sms");
                startActivity(i);*/


            }
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
}