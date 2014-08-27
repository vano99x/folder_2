package ta.lib.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.widget.*;
import android.view.*;
import android.os.*;
import android.app.*;

import ta.timeattendance.*;
import ta.lib.*;
import ta.timeattendance.MainActivityProxy;

public class Test1
{
    Button btnSendSMS;
    EditText txtPhoneNo;
    EditText txtMessage;
    public EditText Details;
    public String user;

    /** Called when the activity is first created. */
    //@Override
    public void onCreate(MainActivity ma) 
    {
    	
		//super.onCreate(savedInstanceState);
        //setContentView(R.layout.end);
    	
        //Details = (EditText)findViewById(R.id.details);
        //btnSendSMS = (Button) findViewById(R.id.btnSend);
    	
        //Bundle b = this.getIntent().getExtras();
        final String email="vano99x@yandex.ru";//b.getString("keym");
        final String pno="";//b.getString("keys");

		//btnSendSMS.setOnClickListener(new View.OnClickListener()
		//{
			//public void onClick(View v)
			//{
				MainActivityProxy context = ma.get_FragmentActivity();
				
				//String detail = Details.getText().toString();
				//Mail m = new Mail("abc@gmail.com", "sdfsa");
				//String[] toArr = {email};
				//m.setTo(toArr);
				//m.setFrom("asdasd11@gmail.com");
				//m.setSubject("EMERGENCY");
				//m.setBody(detail);
				//try
				//{
				//    // m.addAttachment("/sdcard/filelocation"); 
				//    if(m.send())
				//    { 
				        Toast.makeText(context, "Email was sent successfully.", Toast.LENGTH_LONG).show();
				//    }
				//    else
				//    { 
				//        Toast.makeText(context, "Email was not sent.", Toast.LENGTH_LONG).show(); 
				//    }
				//}
				//catch(Exception e)
				//{ 
				//    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
				//    //Log.e("MailApp", "Could not send email", e); 
				//}

				sendSMS(ma);
                context.finish();
                Intent intent = new Intent( context, context.getClass() );
                context.startActivity(intent);
			//}
		//});

    }

	private void sendSMS(MainActivity ma)
	{
		MainActivityProxy context = ma.get_FragmentActivity();
		
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI      = PendingIntent.getBroadcast(context, 0, new Intent(SENT),      0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);

		//---when the SMS has been sent---
		Intent i = context.registerReceiver(new BroadcastReceiver()
		{
			Context context;
			@Override
			public void onReceive(Context arg0, Intent arg1)
			{
				MainActivityProxy context = MainActivityProxy.ma.get_FragmentActivity();
				int resultCode = this.getResultCode();
				//switch (resultCode)
				//{
				//    case Activity.RESULT_OK:
				        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
				//    break;
				//    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				//        Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
				//    break;
				//    case SmsManager.RESULT_ERROR_NO_SERVICE:
				//        Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
				//    break;
				//    case SmsManager.RESULT_ERROR_NULL_PDU:
				//        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
				//    break;
				//    case SmsManager.RESULT_ERROR_RADIO_OFF:
				//        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
				//    break;
				//}
			}
		}, new IntentFilter(SENT));

		//---when the SMS has been delivered---
		context.registerReceiver(new BroadcastReceiver()
		{
			Context context;
			@Override
			public void onReceive(Context arg0, Intent arg1) 
			{
				MainActivityProxy context = MainActivityProxy.ma.get_FragmentActivity();
				switch (getResultCode())
				{
					case Activity.RESULT_OK:
						Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
					break;
					case Activity.RESULT_CANCELED:
						Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		//SmsManager sms = SmsManager.getDefault();
		try
		{
			sentPI.send();
			deliveredPI.send();
		}
		catch(Exception e)
		{
		}
		//sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}


}