package ta.timeattendance;

import android.content.Context;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Handler;
import android.os.PowerManager;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;

import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.timeattendance.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.util.Log;

public class MainActivity
{
	private static Handler __respondHandler;
	private MainActivityProxy __fragmentActivity;
	private MainEngine mEngine;
	private ViewGroup rootView;

	private IAppService __appService;
	private ISupervisorModel __svModel;
	private NfcThread _nfcThread;
	public  android.nfc.Tag __tagFromIntent;

	private boolean _isRunning;
	private int __index;
	private PowerManager __powerManager;
	private NfcManager __nfcManager;

	private ta.lib.BroadcastReceiver.UpdateReceiver _updateReceiver;

	void _1()
	{
		/*
		android.content.IntentFilter updateFilter = new android.content.IntentFilter();
		//updateFilter.addAction(android.content.Intent.ACTION_BOOT_COMPLETED);
		updateFilter.addAction(android.content.Intent.ACTION_PACKAGE_ADDED);
		//updateFilter.addAction(android.content.Intent.ACTION_PACKAGE_INSTALL);
		//updateFilter.addAction(android.content.Intent.ACTION_PACKAGE_CHANGED);
		updateFilter.addAction(android.content.Intent.ACTION_PACKAGE_REPLACED);

		updateFilter.addAction(android.content.Intent.ACTION_PACKAGE_REMOVED);
		updateFilter.addDataScheme("package");
		updateFilter.addDataPath("com.ifree.lib.BroadcastReceiver", android.os.PatternMatcher.PATTERN_LITERAL);

		_updateReceiver = new com.ifree.lib.BroadcastReceiver.UpdateReceiver();
		android.content.Intent i = context.registerReceiver(_updateReceiver, updateFilter);
		if(i == null)
		{
			UIHelper.Instance().Toast("android.content.Intent = null");
		}*/

		/*String packageName = context.getPackageName();
		Intent showSettings = new Intent();
		showSettings.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uriAppSettings = Uri.fromParts("package", packageName, null);
		showSettings.setData(uriAppSettings);
		context.startActivity(showSettings);*/

		//com.ifree.lib.BroadcastReceiver.Test1 t1 = new com.ifree.lib.BroadcastReceiver.Test1();
		//t1.onCreate(this);

		/*int aaa = 9;
		
		try{
		//String var0 = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		//String var0 = android.os.Environment.getRootDirectory().getAbsolutePath();
		//String var0 = context.getFilesDir().getPath();
		//String var0 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_ALARMS).getAbsolutePath();
		//String var1 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DCIM).getAbsolutePath();
		String var2 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		//String var3 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_MOVIES).getAbsolutePath();
		//String var4 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_MUSIC).getAbsolutePath();
		//String var5 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
		//String var6 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath();
		//String var7 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PODCASTS).getAbsolutePath();
		//String var8 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_RINGTONES).getAbsolutePath();
		//String var9 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_BAD_REMOVAL).getAbsolutePath();
		//String var10 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_CHECKING).getAbsolutePath();
		//String var11 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_MOUNTED).getAbsolutePath();
		//String var12 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_MOUNTED_READ_ONLY).getAbsolutePath();
		//String var13 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_NOFS).getAbsolutePath();
		//String var14 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_REMOVED).getAbsolutePath();
		//String var15 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_SHARED).getAbsolutePath();
		//String var16 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_UNMOUNTABLE).getAbsolutePath();
		//String var17 = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.MEDIA_UNMOUNTED).getAbsolutePath();
		//String var0 = android.os.Environment.getDataDirectory().getAbsolutePath();
		//String var1 = android.os.Environment.getDownloadCacheDirectory().getAbsolutePath();
		//String var2 = android.os.Environment.getRootDirectory().getAbsolutePath();
		java.io.File file = new java.io.File(var2+"/" + "qwe.txt");
		//java.io.File file = new java.io.File(var2); String [] arr = file.list();
		//String text = "***"; if(arr != null) for(String item : arr) { text = text + item; }
		//UIHelper.Instance().Toast(text);
		if(file.exists())
		{
		    file.delete();
		}
		java.io.FileOutputStream stream = new java.io.FileOutputStream(file, true);

		byte[] bytes = new byte[]{(byte)0xff};
		stream.write(bytes);//.write("aaa");
		stream.flush();
		stream.close();
		
		int aaa = 9;
		int aaa2 = aaa - 2;UIHelper.Instance().Toast("ok");
		}
		catch(Exception e)
		{
			Exception ex = e;
		}

		try{
			
		java.io.File directory = null;//uhe = operator.as(UnknownHostException.class, e)
			
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		java.io.FileOutputStream      f =    new java.io.FileOutputStream(directory);

		int aaa = 9;

		}
		catch(Exception e)
		{
			Exception ex = e;
		}*/
	}

	/*final Lock _lock = new ReentrantLock();
	private static final String TAG = "MyActivity";
    private Runnable newRunable()
	{
        return new Runnable()
		{
            @Override
            public void run()
			{
                //do {
                    try
					{
                        if( MainActivity.this._lock.tryLock() )//500, TimeUnit.MILLISECONDS
						{
                            try {
 
                                Log.println(Log.ASSERT,TAG, "locked thread " + Thread.currentThread().getName());
 
                                Thread.sleep(1000);
 
                            } finally {
                                MainActivity.this._lock.unlock();
                                Log.println(Log.ASSERT,TAG, "unlocked locked thread " + Thread.currentThread().getName());
 
                            }
                        } else {
                            Log.println(Log.ASSERT,TAG, "unable to lock thread " + Thread.currentThread().getName() + " will re try again");
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                //} while (true);
            }
        };
    }*/

	void _2()
	{
		//try{
		//Thread t1 = new Thread(newRunable(), "Thread1");
		//Thread t2 = new Thread(newRunable(), "Thread2");

		//t1.start();
		//t2.start();

		//t1.join();
		//t2.join();
		//}catch(Exception e){
		//    Exception ex = e;
		//}
		
		ArrayList<Checkin> ch = Checkin.GetLocalCheckins(this.__fragmentActivity);

		int aaa = 9;
		int aaa2 = 9 - 2;
	}

	//=============================================================================================
	//       ctor
	public MainActivity(MainActivityProxy fa)
	{
		this.Nulling();

		//
		final MainActivityProxy faForEx = fa;
		//Thread.setDefaultUncaughtExceptionHandler(
		//java.lang.Process.this.
		Thread.currentThread().setUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread thread, Throwable ex)
			{
				int aaa = 9;
				int aaa2 = aaa-2;

				//try{ Thread.sleep(500L); }catch(InterruptedException e){  }
				//java.lang.StackTraceElement[] st = ex.getStackTrace();
				//StackTraceElement first = st[0];
				//String type1 = first.getClassName();
				//ta.lib.Common.CommonHelper.GetExceptionName();
				//Class type2 = ex.getClass();
				String msg = ex.getMessage();

				//try{
				Intent intent;
				intent = new Intent("ru.startandroid.intent.action.showdate");
				intent.putExtra("Message", msg );
				faForEx.startActivity(intent);

				int p = android.os.Process.myPid();
				android.os.Process.killProcess(p);
				//}catch(Exception e){
				//	Exception ex2 = e;
				//}
			}
		});/**/
		//

		this.set_FragmentActivity( fa );
		MainActivityProxy context = this.get_FragmentActivity();

		// 3 Db
		DbConnector.CreateInstance( context );
		try{
		//DbConnector.getInstance().exec("DROP TABLE IF EXISTS Checkin");
		//DbConnector.getInstance(this).exec("DROP TABLE IF EXISTS Personel");
		//DbConnector.getInstance(this).exec("DROP TABLE IF EXISTS Checkin");
			//DbConnector.getInstance().exec("DROP TABLE IF EXISTS SettingSv");
		}catch(Exception e){
			Exception ex = e;
		}
		
		// 1 context
		context.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
		context.setContentView(R.layout.main);
		this.rootView = ((ViewGroup)context.findViewById(R.id.main_layout));
		
		//ViewGroup view = (ViewGroup)context.getWindow().getDecorView();

		// 2 Engine
		MainEngine.CreateInstance(context);
		//MainEngine.CreateAAA(context);
		this.mEngine = MainEngine.getInstance();

		// 4 UI
		UIHelper.CreateInstance( context, this.rootView );
		//int paddingTop = UIHelper.Instance().tabPin.getRoot().getPaddingTop();

		this._nfcThread = new NfcThread(this, this.mEngine);
		this.mEngine.SaveCheckinCompleteEvent.Add(get_onSaveCheckin_Handler());

		Bootstrapper.Init();
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );

		this.__appService.CreateRunEvent();
		//_2();
	}
	public void MainActivity_Clear()
	{
		this._nfcThread.Stop();

		// 4 UI
		UIHelper.Instance().UIHelper_Clear();
		// !!! //UIHelper.DeleteInstance();

		// 3 Db
		DbConnector.getInstance().Clear();
		DbConnector.DeleteInstance();

		// 2 Engine
		MainEngine.getInstance().MainEngine_Clear();
		MainEngine.DeleteEngine();

		//// 1 context
		this.rootView.setVisibility(android.view.View.GONE);
		////this.rootView.removeAllViews();
		////this.rootView.removeAllViewsInLayout();

		if(NFCHelper.Instance() != null) {
		NFCHelper.Instance().Clear(); NFCHelper.DeleteInstance();
		}

		Bootstrapper.Clear();
		this.Nulling();
	}
	private void Nulling()
	{
		this.__fragmentActivity = null;
		this.mEngine = null;
		this.rootView = null;

		this._nfcThread = null;
		this.__tagFromIntent = null;

		this._isRunning = false;
		this.__index = -1;
		this.__powerManager = null;
		this.__nfcManager = null;
	}
	public void MainActivity_Logout()
	{
		this.__appService.LogoutRunEvent();
	}



	public void Start(String threadMarker)
	{
		if( this._isRunning == false )
		{
			this._isRunning = true;
			if(this.__svModel.get_CurrentSuperviser() == null){
				UIHelper.Instance().switchState(State.PIN);
				//UIHelper.Instance().switchState(State.PERSONEL_INFO);
			}else{
				UIHelper.Instance().switchState(State.MODE_SELECTION);
			}

			NFCHelper.CreateInstance( this.get_FragmentActivity() );
			this.__appService.RunningRunEvent();
		}
	}
	public void Stop()
	{
		if( this._isRunning == true )
		{
			this._isRunning = false;
			if( MainEngine.getInstance() != null ) {
				MainEngine.getInstance().MainEngine_ToDefault();
			}

			if(NFCHelper.Instance() != null) {
				NFCHelper.Instance().Clear(); NFCHelper.DeleteInstance();
			}
			this.__appService.ClosingRunEvent();
		}
	}
	
	//===== 3 =====================================================================================
	public void Resume()
	{
		NFCHelper.Instance().Enable(); //TALog.Log("===========onResume=================");
	}
	public void Pause()
	{
		//if(this.get_PowerManager().isScreenOn()) { int aaa = 9; int aaa2 = aaa - 2; }
		NFCHelper.Instance().Disable();
		//MainActivityProxy context = this.get_FragmentActivity();
		//if(_updateReceiver != null){ context.unregisterReceiver(_updateReceiver); }

		//this.mEngine.cancelTimerTask();
		//this.mEngine.saveState(UIHelper.Instance().currentState);
	}
	public void GotFocus()
	{
		this.__appService.GotFocusRunEvent();
	}

	//=============================================================================================
	//       Event Handler
	private onSaveChkn get_onSaveCheckin_Handler() { onSaveChkn o = new onSaveChkn(); o.arg1 = this; return o; }
	class onSaveChkn extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		MainActivity      _this   = (MainActivity)this.arg1;
		MainActivityProxy context = _this.get_FragmentActivity();
		boolean           result  = this.result;

		context.NfcNotBusy();
	}}

	

	//=============================================================================================
	//*      properties
	public MainActivityProxy get_FragmentActivity()
	{
		return this.__fragmentActivity;
	}
	private void set_FragmentActivity(MainActivityProxy fa)
	{
		if( this.__index == -1 ) {
			this.__index = fa._index;
		}

		this.__fragmentActivity = fa;
	}
	public int get_Index()
	{
		return this.__index;
	}

	public void set_TagFromIntent(android.nfc.Tag tag)
	{
		this.__tagFromIntent = tag;
	}
	public PowerManager get_PowerManager()
	{
		if(this.__powerManager == null)
		{
			this.__powerManager = (PowerManager)get_FragmentActivity().getSystemService(Context.POWER_SERVICE);
		}
		return this.__powerManager;
	}
	public NfcManager get_NfcManager()
	{
		if(this.__nfcManager == null)
		{
			this.__nfcManager = (NfcManager)get_FragmentActivity().getSystemService(Context.NFC_SERVICE);
		}
		return this.__nfcManager;
	}
	public static Handler get_RespondHandler()
	{
		if(__respondHandler == null)
		{
			__respondHandler = new Handler();
		}
		return __respondHandler;
	}

/*
@Override
public void onConfigurationChanged(Configuration newConfig)
{
    super.onConfigurationChanged(newConfig); // Checks whether a hardware keyboard is available
    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
        Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
    } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
        Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
    }
}*/

	public static enum State
	{
		NULL(              "NULL",               0),
		PIN(               "PIN",                1),
		//FLAG_MAIN_MENU(         "FLAG_MAIN_MENU",          2),
		FLAG_POINTS_LIST(       "FLAG_POINTS_LIST",        3),
		MODE_SELECTION(    "MODE_SELECTION",     4),
		WAIT_MODE(         "WAIT_MODE",          5),
		PERSONEL_LIST_MODE("PERSONEL_LIST_MODE", 6),
		PERSONEL_INFO(     "PERSONEL_INFO",      7),
		CHECKIN_LIST(      "CHECKIN_LIST",       8),
		REFERENCE(      "REFERENCE",       9),
		FACILITY_INFO(      "FACILITY_INFO",       10),
		FLAG_SETTINGS(      "FLAG_SETTINGS",       15);
		//DATA_TRANSMISSION("DATA_TRANSMISSION", 4), //ERROR_READING_LABLE("ERROR_READING_LABLE", 5),
		//ERROR_CONNECTION("ERROR_CONNECTION", 6), //FINISH("FINISH", 7), //SYNC("SYNC", 12),

		private String _strString;
		private int _intString;
		State(String var1, int var2)
		{
			_strString = var1;
			_intString = var2;
		}
		public String toString()
		{
			return _strString;
		}
		public int toInt()
		{
			return _intString;
		}
	}

}
