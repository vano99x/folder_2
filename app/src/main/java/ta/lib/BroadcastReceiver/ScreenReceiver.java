//package com.ifree.lib;

//import android.content.Context;
//import android.content.Intent;
//import android.content.BroadcastReceiver;


//public class ScreenReceiver extends BroadcastReceiver
//{
//    public static boolean wasScreenOn = true;

//    @Override
//    public void onReceive(Context context, Intent intent)
//    {
//        int aaa1 = 9;
//        int aaa2 = ((int)aaa1) - 2;

//        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
//        {
//            // do whatever you need to do here
//            wasScreenOn = false;
//        }
//        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
//        {
//            // and do whatever you need to do here
//            wasScreenOn = true;
//        }
//    }
//}

/*
        // when the screen is about to turn off
        if (ScreenReceiver.wasScreenOn)
		{
            // this is the case when onPause() is called by the system due to a screen state change
            System.out.println("SCREEN TURNED OFF");
        }
		else
		{
            // this is when onPause() is called when the screen state has not changed
        }*/

/*IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_ON");
Intent i = this.registerReceiver(
	new BroadcastReceiver(){
		@ Override
		public void onReceive (Context context, Intent intent) {
			Log.d( intent.getAction(), "RECEIVE" );
		}
	},
	filter
);
	    
		
BroadcastReceiver receiver = new BroadcastReceiver() {
@Override
public void onReceive(Context context, Intent intent) {
if (intent == null)
	return;
//do something you need when broadcast received

}
};

IntentFilter filter = new IntentFilter();
filter.addAction(Intent.ACTION_SCREEN_ON);
filter.addAction(Intent.ACTION_SCREEN_OFF);
//BroadcastReceiver mReceiver = new ScreenReceiver();
Intent i = this.registerReceiver(receiver, filter);
*/