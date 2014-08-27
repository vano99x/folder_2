package ta.lib;

import android.nfc.tech.MifareClassic;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainEngine;

public class NfcThread implements Runnable
{
	private MainActivity ma;
	private MainEngine   me;
	private boolean      isRun;

	public NfcThread(
		MainActivity mainActivity, 
		MainEngine   mainEngine)
	{
		this.ma = mainActivity;
		this.me = mainEngine;
		this.isRun = true;
		Thread t = new Thread(this,"--= thread checking nfc device =--");
		t.start();
	}

	public void Stop()
	{
		this.isRun = false;
	}

	public void run()
	{
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

		me.OnNfcTagApply(l);
	}
}