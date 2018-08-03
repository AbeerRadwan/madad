package casey.madad;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import casey.app20.R;
import android.widget.Toast;

public class detailed_msg extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_msg);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Toast.makeText(getBaseContext(), extras.getString("msgType"), Toast.LENGTH_SHORT).show();
            String type = extras.getString("msgType");
            String Time = extras.getString("msgTime");

             TextView a = (TextView)findViewById(R.id.type);
           //  a.setText(type);

            TextView b = (TextView)findViewById(R.id.time);
           // b.setText(Time);


        }
    }
}
