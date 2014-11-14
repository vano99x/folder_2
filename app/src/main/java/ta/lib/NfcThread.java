package ta.lib;

import android.content.Intent;
import android.nfc.tech.MifareClassic;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainActivityProxy;
import ta.timeattendance.MainEngine;
import ta.timeattendance.Services.NfcEventService;

public class NfcThread implements Runnable
{
	private MainActivity ma;
	//private MainEngine   me;
	private NfcEventService _service;
	private boolean      isRun;

	public NfcThread(
		MainActivity mainActivity, 
		NfcEventService   service)
	{
		this.ma = mainActivity;
		//this.me = mainEngine;
		this._service = service;
		this.isRun = true;
		Thread t = new Thread(this,"--= thread checking nfc device =--");

		/*
		//MainActivityProxy maprx = mainActivity.get_FragmentActivity();
		final MainActivityProxy faForEx = mainActivity.get_FragmentActivity();
		t.setUncaughtExceptionHandler(
		//new ExWithTag(maprx)
		new Thread.UncaughtExceptionHandler()
		{
			@Override public void uncaughtException(Thread thread, Throwable ex)
			{
				//java.lang.StackTraceElement[] st = ex.getStackTrace();
				//StackTraceElement first = st[0];
				//String type1 = first.getClassName();
				String msg = ex.getMessage();

				Intent intent = new Intent("ru.startandroid.intent.action.showdate");
				intent.putExtra("Message", msg );
				faForEx.startActivity(intent);

				int p = android.os.Process.myPid();
				android.os.Process.killProcess(p);
			}
		}
		);*/

		t.start();
	}

	/*private static class ExWithTag implements Thread.UncaughtExceptionHandler
	{
		private Object _tag;
		public ExWithTag(Object tag){ this._tag = tag; }
		@Override public void uncaughtException(Thread thread, Throwable ex)
		{
			MainActivityProxy faForEx = (MainActivityProxy)this._tag;

			String msg = ex.getMessage();

			Intent intent = new Intent("ru.startandroid.intent.action.showdate");
			intent.putExtra("Message", msg );
			faForEx.startActivity(intent);

			int p = android.os.Process.myPid();
			android.os.Process.killProcess(p);
		}
	}*/

	public void Stop()
	{
		this.isRun = false;
	}

	public void run()
	{
		//Thread t = Thread.currentThread();
		//String str = t.getName();
		//ThreadGroup tg = t.getThreadGroup();
		//if(tg!= null)
		//{
		//	if(tg == t.getUncaughtExceptionHandler())
		//	{
		//		MainActivityProxy maprx = this.ma.get_FragmentActivity();
		//		t.setUncaughtExceptionHandler( new ExWithTag(maprx) );
		//	}
		//}

		//boolean isBusy = false;
		while( isRun )
		{
			try
			{
				int aaa1 = 9;
				int aaa2 = aaa1 - 2;

				if(
					(ma.__tagFromIntent != null)
					//&& (isBusy == false)
				)
				{
					//isBusy = true;
					this.readTag(ma.__tagFromIntent);
					ma.__tagFromIntent = null;
					//isBusy = false;
				}
				try
				{
					Thread.sleep(100L);
				}
				catch (Exception localException)
				{
				}
			}
			finally
			{
			}

			if(isRun == false)
			{
				int aaa1 = 9;
				int aaa2 = ((int)aaa1) - 2;
			}
		}
	}


	private void readTag(android.nfc.Tag paramTag)
	{
		if( paramTag == null )
			return;

		byte[] arrayOfByte = MifareClassic.get(paramTag).getTag().getId();
		long[] arrayOfLong = new long[4];

		if( (arrayOfByte == null) || (arrayOfByte.length != 4) )
			return;
		
		for( int i = 0; i < 4; ++i)
		{
			if (arrayOfByte[i] < 0)
			{
				arrayOfLong[i] = (256 + arrayOfByte[i]);
			}
			else
			{
				arrayOfLong[i] = arrayOfByte[i];
			}
		}

		long l = 
			arrayOfLong[0] + 
			256L * arrayOfLong[1] + 
			256L * (256L * arrayOfLong[2]) + 
			256L * (256L * (256L * arrayOfLong[3]));

		//me.OnNfcTagApply(l);
		this._service.RunEvent(l);
	}
}