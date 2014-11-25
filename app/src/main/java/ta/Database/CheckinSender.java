package ta.Database;

import android.content.Context;
import java.net.URL;
import java.util.ArrayList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ta.lib.*;

public class CheckinSender
{
	private static boolean _isBusy;
	private static final Lock _lock;

	public static void SendCheckinArray(Context context)
		throws java.io.IOException,
			java.net.ProtocolException,
			java.lang.IndexOutOfBoundsException,
			java.lang.IllegalAccessError,
			java.lang.IllegalStateException,
			java.lang.NullPointerException,
    java.lang.NoSuchFieldException,
    java.lang.IllegalAccessException,
    java.lang.NoSuchMethodException
	{
		if( CheckinSender._lock.tryLock() )
		{
			try
			{
				if(CheckinSender._isBusy == false)
				{
					CheckinSender._isBusy = true; //android.os.MessageQueue

					ArrayList<Checkin> ch = Checkin.GetLocalCheckins(context);
					String respond = null;
					for(Checkin item : ch)
					{
						String strUrl = HttpHelper.getCheckinURL( item );
						//String strUrl = HttpHelper.getEOFurl( item );
						respond = HttpHelper.httpGet(new URL(strUrl));
						if( respond != null && !respond.isEmpty() )
						{
							item.IsCheckinExistOnServer = true;
							item.set_StateCheckinOnServer(respond);
							//Checkin.UpdateLocalDB( item );
							item.Update();
						}
						else
						{
							item.IsCheckinExistOnServer = false;
							item.set_StateCheckinOnServer(-2);
							//Checkin.UpdateLocalDB( item );
							item.Update();
						}
					}
					CheckinSender._isBusy = false;
				}
			}
			finally
			{
				CheckinSender._isBusy = false;
				CheckinSender._lock.unlock();
			}
		}
	}

	static //ctor
	{
		_isBusy = false;
		_lock = new ReentrantLock();
	}
}