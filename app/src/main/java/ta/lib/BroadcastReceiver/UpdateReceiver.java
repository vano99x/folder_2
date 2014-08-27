package ta.lib.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

import ta.lib.*;

public class UpdateReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		int aaa = 9;
		UIHelper.Instance().Toast("UpdateReceiver");
		/*if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED))
		{
			Intent serviceIntent = new Intent();
			serviceIntent.setClass( context, com.ifree.timeattendance.MainActivityProxy.class);
			serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(serviceIntent);
		}
		else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED))
		{
			Intent serviceIntent = new Intent();
			serviceIntent.setClass( context, com.ifree.timeattendance.MainActivityProxy.class);
			serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(serviceIntent);
		}*/
	}
}