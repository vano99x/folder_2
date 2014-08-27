package ta.lib;

import android.app.Activity;
import android.content.Intent;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.nfc.NfcManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;

import ta.lib.UIHelper.Act;
import ta.timeattendance.R;

public class NFCHelper
{
	public  static NFCHelper      __instance;
	private static IntentFilter[] mFilters;
	private static String[][]     mTechLists;

	public  Activity      _context;
	public  NfcAdapter    mAdapter;
	private PendingIntent mPendingIntent;

	public static NFCHelper Instance()
	{
		return NFCHelper.__instance;
	}
	public static void CreateInstance( Activity context )
	{
		NFCHelper.__instance = new NFCHelper(context);
	}
	public static void DeleteInstance()
	{
		NFCHelper.__instance = null;
	}



	private NFCHelper( Activity context )
	{
		NfcManager manager = (NfcManager)context.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter == null || adapter.isEnabled() == false)
		{
			UIHelper.Instance().MsgFromBackground( Act.NfcDisabled );
		}

		this._context = context;
		this.mAdapter = NfcAdapter.getDefaultAdapter(context);

		this.mPendingIntent = 
			PendingIntent.getActivity(
				context, 
				0, 
				new Intent( context, context.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 
				0
			);

		int aaa = 9;
	}
	public void Clear()
	{
		this.mAdapter = null;
		this.mPendingIntent = null;
	}



	public void Enable()
	{
		this.mAdapter.enableForegroundDispatch(this._context, this.mPendingIntent, NFCHelper.mFilters, NFCHelper.mTechLists);
	}
	public void Disable()
	{
		this.mAdapter.disableForegroundDispatch(this._context);
	}

	//public class NFCReceiver extends BroadcastReceiver
	//{
	//    public void onReceive(Context context, Intent intent)
	//    {
	//        String action = intent.getAction();
	//    }
	//}

	static
	{
		NFCHelper.__instance = null;

		try
		{
			IntentFilter intentFilter = new IntentFilter(android.nfc.NfcAdapter.ACTION_TAG_DISCOVERED);
			intentFilter.addDataType("*/*");
			NFCHelper.mFilters = new IntentFilter[] { intentFilter };
		}
		catch( IntentFilter.MalformedMimeTypeException malformedMimeTypeException)
		{
			int aaa1 = 9;
			throw new RuntimeException("fail", malformedMimeTypeException);
		}

		NFCHelper.mTechLists = new String[][]{ new String[]{ MifareClassic.class.getName() } };
	}
}