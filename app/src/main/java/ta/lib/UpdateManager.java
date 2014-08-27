package ta.lib;

import android.content.Context;

import java.io.File;

import android.os.Environment;
import android.content.Intent;
import android.net.Uri;

import java.io.IOException;
import java.util.Calendar;

import ta.Database.FacilityInfoEntity;
import ta.lib.*;
import ta.lib.UIHelper.Act;
import ta.timeattendance.*;
//import ta.timeattendance.MainEngine.GetFacilityInfoBFClass;

public class UpdateManager
{
	public static String GetName()
	{
		java.util.Calendar calendar = java.util.Calendar.getInstance();

		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day   = calendar.get(Calendar.DAY_OF_MONTH);
		int hour  = calendar.get(Calendar.HOUR_OF_DAY);
		int min   = calendar.get(Calendar.MINUTE);
		int sec   = calendar.get(Calendar.SECOND);
		
		StringBuilder sb = new StringBuilder();
		sb.append("time_check_");
		sb.append(Integer.toString(year) +"-");
		sb.append(Integer.toString(month)+"-");
		sb.append(Integer.toString(day)  +"_");
		sb.append(Integer.toString(hour) +"-");
		sb.append(Integer.toString(min)  +"-");
		sb.append(Integer.toString(sec));
		sb.append(".apk");

		return sb.toString();
	}
	
	public static void LoadNewVersionAndRun(MainActivityProxy context)
	{
		//BackgroundFunc bf = 
		//	BackgroundFunc.get_BackgroundFunc( 
		//		UpdateManager.get_onLoadNewVersionAndRun(context), null
		//	);
		//bf.BackgroundFuncComplete.Add(UpdateManager.get_onLoadAndRunComplete(context));
		//new Thread(bf,"-LoadNewVersionAndRun-").start();
		BackgroundFunc.Go(
			new LoadNewVersionAndRunBFClass(), 
			UpdateManager.get_LoadAndRun(context), 
			UpdateManager.get_LoadAndRunCmpl(context), 
			"-LoadNewVersionAndRun-");
	}

	private static LoadAndRunCmpl get_LoadAndRunCmpl(MainActivityProxy context) { LoadAndRunCmpl o = new LoadAndRunCmpl(); o.arg1 = context; return o; }
	private static LoadAndRun     get_LoadAndRun(    MainActivityProxy context) { LoadAndRun     o = new LoadAndRun();     o.arg1 = context; return o; }
}
	class LoadNewVersionAndRunBFClass extends BackgroundFunc<FacilityInfoEntity,Boolean> {}
	class LoadAndRunCmpl extends RunnableWithArgs<FacilityInfoEntity,Boolean> { public void run()
	{
		MainActivityProxy context = (MainActivityProxy)this.arg1;
		//context.finish();

		boolean isOk = this.result;
		if( isOk == true )
		{
			UIHelper.Instance().MsgFromBackground( Act.LoadNewVersionSuccess );
		}
	}}
	class LoadAndRun extends RunnableWithArgs { public void run()
	{
		MainActivityProxy 
			context = (MainActivityProxy)this.arg1; //boolean loadAndRunResult = false;
		this.result = new Object[]{false};

		File    _file           = null;
		boolean isCreateNewFile = false;
		boolean loadResult      = false;

		UIHelper.Instance().MsgProgress( Act.StartOperation );

		String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		String name = UpdateManager.GetName();
		_file = new File(folder + "/" + name);

		if( _file.exists() ) {
			_file.delete();
		}

		try {
			isCreateNewFile = _file.createNewFile();
		} catch (IOException e) {
			UIHelper.Instance().MsgProgress(Act.CanNotCreateFileForLoading);
		}

		if( !isCreateNewFile ) {
			return;
		} else {
			UIHelper.Instance().MsgProgress("new file successfully created");
			try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
		}

//String strUrl = "http://akme.telemetry.i-free.ru/Api/sync/checkin?id=4&WorkerId=5&cardId=2999600235&status=3&pointId=4&date=1382632193964";
//String strUrl = "http://6-ch.org/test6.apk";
String strUrl = "http://93.153.172.26/get_latest_version";

		try {
			loadResult = HttpHelper.DownloadFile(strUrl, _file, UIHelper.Instance());
		}
		catch (Exception e)
		{
			java.net.MalformedURLException      mue = null;
			java.io.FileNotFoundException       fnf  = null;
			java.io.IOException                 ioe = null;
			java.net.ProtocolException          pre  = null;
			java.lang.IndexOutOfBoundsException iob   = null;

			if((       mue = operator.as(java.net.MalformedURLException.class,       e))!=null){
				UIHelper.Instance().MsgProgress( Act.LoadNewVersionFailed );

			}else if(( fnf = operator.as(java.io.FileNotFoundException.class,        e))!=null){
				UIHelper.Instance().MsgProgress( Act.LoadNewVersionFailed );

			}else if(( ioe = operator.as(java.io.IOException.class,                  e))!=null){
				UIHelper.Instance().MsgProgress( Act.LoadNewVersionFailed );

			} else if(( pre = operator.as(java.net.ProtocolException.class,          e))!=null){
				UIHelper.Instance().MsgProgress( Act.LoadNewVersionFailed );

			} else if(( iob = operator.as(java.lang.IndexOutOfBoundsException.class, e))!=null){
				UIHelper.Instance().MsgProgress( Act.LoadNewVersionFailed );
			}else{
				UIHelper.Instance().MsgProgress(e);
			}
		}

		try {
			if( loadResult )
			{
				if( _file.exists() )
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(
						Uri.fromFile(_file),						//NullPointerException 
						"application/vnd.android.package-archive"
					);
					//context.finish();
					context.startActivity(intent);					//ActivityNotFoundException
					this.result = new Object[]{true};
				}
			}
		}
		catch (Exception e) {
		}
	}}

