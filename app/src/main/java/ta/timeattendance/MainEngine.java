package ta.timeattendance;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import java.io.IOException;

import java.net.URL;
import java.net.*;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.MainActivity.State;
import ta.lib.UIHelper.Act;
import ta.timeattendance.Models.*;

public class MainEngine
{
	private static MainEngine _mainEngineObj = null;
	public Context mContext;
	private String mPin;
	private int currentMode;

	private Personel __currentWorker;
	private int __currentPointId;
	private ISupervisorModel __svModel;
	private IPointModel __pointModel;

	//*********************************************************************************************
	class SaveCheckinCompleteEventClass extends Event<Object,Boolean> {}
	public SaveCheckinCompleteEventClass SaveCheckinCompleteEvent;

	class  WorkerFoundEventClass extends Event<Personel[],Object> {}
	public WorkerFoundEventClass WorkerFound;

	//Runnable showPersonelRunnable; Runnable showScreenRunnable; Runnable serverRunnable;
	//*********************************************************************************************
	//       instance
	public static MainEngine getInstance()
	{
		return MainEngine._mainEngineObj;
	}
	public static void CreateInstance( Context context )
	{
		MainEngine._mainEngineObj = new MainEngine(context);
	}
	public static void DeleteEngine()
	{
		MainEngine._mainEngineObj = null;
	}

	//*********************************************************************************************
	//       ctor
	private MainEngine( Context context )
	{
		this.Nulling();

		this.mContext = context;
		//this.mSettings = context.getSharedPreferences("com.ifree.timeattendance", 0);
		this.SaveCheckinCompleteEvent    = new SaveCheckinCompleteEventClass();
		this.WorkerFound                 = new WorkerFoundEventClass();
		this.SaveCheckinCompleteEvent.Add(get_onSaveChkn());

		//***   Models ***
		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
	}
	public void MainEngine_Clear()
	{
		//this.Closing.RunEvent(null);
		this.Nulling();
	}
	public void Nulling()
	{
		this.mContext = null;
		this.mPin = null;
		this.currentMode = 1;

		this.__currentWorker = null;
		//this.__isIntenetDisable = false;
		this.__currentPointId = -1;

		this.SaveCheckinCompleteEvent = null;
		this.WorkerFound = null;

		//this.showPersonelRunnable = null; this.showScreenRunnable = null; this.serverRunnable = null;
	}
	public void MainEngine_ToDefault()
	{
		setCurrentMode(1);
		//MainEngine._mainEngineObj.Clearing.RunEvent(null);
	}



	//*********************************************************************************************
	//       Event Handler
	private onSaveChkn get_onSaveChkn() { onSaveChkn o = new onSaveChkn(); return o; }
	class onSaveChkn extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		if(this.result)
		{
			switch(UIHelper.Instance().currentState)
			{
				case PERSONEL_INFO:{
					//UIHelper.Instance().switchState(State.WAIT_MODE);
					UIHelper.Instance().tabPersonelInfo.IsShowCheckiedWorker = true;
					UIHelper.Instance().tabPersonelInfo.UpdateData();
				break;}
				case WAIT_MODE:{
					boolean result = this.result;
					if(result)
					{
						UIHelper.Instance().tabPersonelInfo.IsShowCheckiedWorker = true;
						UIHelper.Instance().switchState(State.PERSONEL_INFO);
					}
				break;}
				default:{
					// ...
				break;}
			}
		}
	}}


  //private void showError()
  //{
  //  showScreen(MainActivity.State.ERROR_CONNECTION, 0L);
  //  showScreen(MainActivity.State.WAIT_MODE, 3000L);
  //}

	//public void cancelOtherTasks()
	//{
	//    try
	//    {
	//        if (this.showScreenRunnable != null){
	//            this.respondHandler.removeCallbacks(this.showScreenRunnable);
	//        }
	//        if (this.serverRunnable != null){
	//            this.respondHandler.removeCallbacks(this.serverRunnable);
	//        }
	//        if (this.showPersonelRunnable != null){
	//            this.respondHandler.removeCallbacks(this.showPersonelRunnable);
	//        }
	//    }
	//    catch (Exception localException)
	//    {
	//        //TALog.Log(localException.toString());
	//    }
	//}

  //public void cancelTimerTask()
  //{
  //  if (this.mTimerTask != null)
  //  {
  //    this.mTimerTask.cancel();
  //    this.mTimerTask = null;
  //  }
  //}

  //void saveState(MainActivity.State paramState)
  //{
  //  SharedPreferences.Editor localEditor = this.mSettings.edit();
  //  TALog.Log("SAVE_STATE : state = " + paramState);
  //  localEditor.putInt("timeattendance.state", paramState.ordinal());
  //  localEditor.putString("timeattendance.PIN", this.mPin);
  //  localEditor.putInt("timeattendance.currentMode", this.currentMode);
  //  localEditor.putInt("timeattendance.pointID", this.get_CurrentPointId());
  //  localEditor.commit();
  //}
	
	public int getCurrentMode()
	{
		return this.currentMode;
	}
	public void setCurrentMode(int paramInt)
	{
		this.currentMode = paramInt;
	}
	
	public Personel get_CurrentWorker()
	{
		return this.__currentWorker;
	}
	public void set_CurrentWorker(Personel p)
	{
		this.__currentWorker = p;
	}

	private void MsgFromBackground(Act act)
	{
		UIHelper h = UIHelper.Instance();
		h.MsgFromBackground( act );
	}

	private static void Synchronization( Personel sv, Personel [] workers, Point [] points, PersonelPoint [] ppoints, Context context)
		throws
			java.io.IOException,
			java.net.ProtocolException,
			java.lang.IndexOutOfBoundsException,
			java.lang.IllegalAccessError,
			java.lang.IllegalStateException,
			java.lang.NullPointerException
	{
		Personel.sync( new Personel [] { sv }, context);
		Personel.sync(      workers,           context);
		Point.sync(         points,            context);
		PersonelPoint.sync( ppoints,           context);

		CheckinSender.SendCheckinArray(context);
	}

	private static Personel LoadPersonelFromServer( String pin, Context context)
		throws MalformedURLException , IOException , JSONException
	{
		Personel p = null;
		String str = HttpHelper.getAuthRequestURL(pin);
		URL url = new URL(str);
		String data = HttpHelper.httpGet(url);
		//java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		//byte[] buffer = new byte[0x100];
		////baos.write(buffer, 0, 0);
		//baos.write(0);
		//byte[] arr = baos.toByteArray();//new byte[0];
		//String data = new String( arr );

		if(!(data.isEmpty()))//"".equals(data)
		{
			//int aaa = 9;
			//int aaa2 = aaa-2;
			JSONObject json = new JSONObject(data);
			p = Personel.FromJson( json, context);
			if(p != null)
			{
				p.Pin = pin;
			}
		}

		return p;
	}



	//************************************************************************************************
	// 1
	private onBfAuth get_onBfAuth() { onBfAuth o = new onBfAuth(); o.arg1 = this; return o; }
	class onBfAuth extends RunnableWithArgs<Personel,Boolean> { public void run()
	{
		boolean result = this.result;   MainEngine _this = (MainEngine)this.arg1;
		if(result) {
			_this.__svModel.set_CurrentSuperviser(this.arg);
		}
	}}

	class AuthenticateSVBFClass extends BackgroundFunc<Personel,Boolean> {}
	public void AuthenticateSV(final String pinStr)
	{
		this.mPin = pinStr;
		BackgroundFunc.Go( new AuthenticateSVBFClass(), get_onAuthenticateSV(this.mPin), get_onBfAuth(), "-Authenticate-");
	}

	private onAuthSV get_onAuthenticateSV(String pinStr) { onAuthSV o = new onAuthSV(); o.arg1 = this; o.arg2 = pinStr; return o; }
	class   onAuthSV extends RunnableWithArgs<Personel,Boolean> { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		String pinStr = (String)this.arg2;
		boolean result = false;

		MsgFromBackground( Act.StartOperation );
		Personel sv = null;

		if( HttpHelper.IsInternetAvailable(engine.mContext))
		{
			try
			{
				sv = LoadPersonelFromServer( pinStr, engine.mContext);
				if(sv != null)
				{
					Personel [] workers = sv.LoadWorkers(engine.mContext);
					MainEngine.Synchronization( sv, workers, sv.pointArray, sv.get_PersonelPoints(), engine.mContext);
					MsgFromBackground(Act.ServerAuthOk);
					result = true;
				} // else { MsgFromBackground(Act.ServerAuthError); }
			}
			catch( Exception e)
			{
				HttpHelper.ExceptionHandler( e );
			}
		}

		// if not load from server - load from local db
		if(!result)
		{
			try
			{
				sv = Personel.SelecByPin( pinStr );
				if( sv != null && sv.Id != -1 && sv.IsSupervisor) {
					MsgFromBackground(Act.LocalAuthOk);
					result = true;
				}else{
					MsgFromBackground(Act.LocalAuthError);
				}
			}
			catch( Exception e)
			{
				int aaa = 9;
				int aaa2 = aaa - 2;
				Exception ex = e;
			}
		}

		this.arg = sv;
		this.result = result;
	}}



	//************************************************************************************************
	// 2 entering tag
	private onClickOkMessageBox get_onClickOkMessageBox() { onClickOkMessageBox o = new onClickOkMessageBox(); o._this = this; return o; } 
	class onClickOkMessageBox implements OnClickListener { public MainEngine _this;public void onClick(DialogInterface di, int paramAnonymousInt)
	{
	}}
	public void OnNfcTagApply(final long cardId) // ---> call in bg thread
	{
		Personel p = Personel.SelectByCard( cardId );
		if(p != null && p.Id != -1)
		{
			this.set_CurrentWorker(p);
			SaveCheckin();
		}
		else
		{
			ta.timeattendance.MainActivity.get_RespondHandler().post(new Runnable() { public void run() {
				MainEngine.getInstance().SaveCheckinCompleteEvent.RunEvent(null,false);
			}});
			UIHelper.Instance().MessageBoxInUIThread(
				"Сотрудник не найден! \r\n " + Long.toHexString(cardId),
				get_onClickOkMessageBox(),
				null
			);
		}
	}


	//************************************************************************************************
	// 3 save checkin from personel info
	class SaveCheckinBFClass extends BackgroundFunc<Checkin,Boolean> {}
	public void SaveCheckin() // ---> call in ui thread and bg thread
	{
		if((this.get_CurrentWorker() != null) && (this.get_CurrentWorker().Id != -1) )
		{
			BackgroundFunc.Go( new SaveCheckinBFClass(), get_onSaveCheckin(), get_onBfComplete(), "-SaveCheckin-");
		}
	}

	private onBfComplete get_onBfComplete() { onBfComplete o = new onBfComplete(); o.arg1 = this; return o; }
	class onBfComplete extends RunnableWithArgs<Checkin,Boolean> { public void run() // ---> call in ui thread
	{
		MainEngine _this = (MainEngine)this.arg1;
		Boolean res = this.result;
		_this.SaveCheckinCompleteEvent.RunEvent(null,res);
	}}
	
	private onSaveCheckin get_onSaveCheckin() { onSaveCheckin o = new onSaveCheckin(); o.arg1 = this; return o; }
	class onSaveCheckin extends RunnableWithArgs<Checkin,Boolean> { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		boolean result = false;
		Checkin ch = null;

		//MsgFromBackground(Act.StartOperation);

		Point p = engine.__pointModel.get_CurrentPoint();
		Personel w = engine.get_CurrentWorker();
		if(p == null)
		{
			result = false;
			MsgFromBackground( Act.CheckpointNotSelect );
		}
		else //if(!w.IsDismiss)
		{
			ch = new Checkin(
				engine.__svModel.get_CurrentSuperviser().Id,	// SupervicerId
				w.Id,											// WorkerId
				w.CardId,										// CardId
				engine.getCurrentMode(),						// Mode
				p.Id,											// PointId
				String.valueOf(System.currentTimeMillis())		// DateTime
			);

			ch.IsCheckinExistOnServer = false;
			ch.save( engine.mContext );
			//MsgFromBackground( Act.CheckinSaveLocal );
			result = true;

		}

		this.arg = ch;
		this.result = result;
	}}

			/*if(HttpHelper.IsInternetAvailable(engine.mContext))
			{
				try
				{
					String strUrl = HttpHelper.getCheckinURL( ch );
					String respond = null;

					respond = HttpHelper.httpGet(new URL(strUrl));
				
					if( respond != null && !respond.isEmpty() )
					{
						ch.IsCheckinExistOnServer = true;
						ch.set_StateCheckinOnServer(respond);
						ch.save( engine.mContext );
				
						MsgFromBackground( Act.CheckinSuccess );
						result = true;
					} else {
						throw new java.net.ConnectException("Request not returned response");
					}
				}
				catch( Exception e)
				{
					ch.IsCheckinExistOnServer = false;
					ch.set_StateCheckinOnServer(-2);
					ch.save( engine.mContext );
				
					HttpHelper.ExceptionHandler( e );
					result = false;
				}

				try {
					CheckinSender.SendCheckinArray(engine.mContext);
				}
				catch( Exception e)
				{
					HttpHelper.ExceptionHandler( e );
					result = false;
				}
			}
			else*/

	//************************************************************************************************
	// 4 search worker
	public void searchPersonel(final String paramString)
	{
		final Personel[] arrayOfPersonel = Personel.search(paramString, this.mContext);
		if( arrayOfPersonel != null )
		{
			WorkerFound.RunEvent(arrayOfPersonel);
		}
	}

	//************************************************************************************************
	// 5 Sync
	class SyncBFClass extends BackgroundFunc<Personel,Boolean> {}
	public void Sync()
	{
		BackgroundFunc.Go( new SyncBFClass(), get_onSync(), get_onSyncComplete(), "-Sync-");
	}

	private onSyncComplete get_onSyncComplete() { onSyncComplete o = new onSyncComplete(); o.arg1 = this; return o; }
	class   onSyncComplete extends RunnableWithArgs<Personel,Boolean> { public void run() // ---> call in ui thread
	{
		boolean result = this.result;   MainEngine _this = (MainEngine)this.arg1;
		if(result) {
			Personel p = this.arg;
			_this.__svModel.set_CurrentSuperviser(p);
		}
	}}

	private onSync get_onSync() { onSync o = new onSync(); o.arg1 = this; return o; }
	class onSync extends RunnableWithArgs<Personel,Boolean> { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		boolean result = false;

		MsgFromBackground( Act.StartOperation );
		Personel sv = null;

		try
		{
			sv = LoadPersonelFromServer( engine.mPin, engine.mContext);
			if(sv.Id != 0)
			{
				Personel [] workers = sv.LoadWorkers(engine.mContext);
				MainEngine.Synchronization( sv, workers, sv.pointArray, sv.get_PersonelPoints(), engine.mContext);
				MsgFromBackground(Act.SyncOk);
				result = true;
			} else {
				MsgFromBackground(Act.SyncError);
			}
		}
		catch( Exception e)
		{
			HttpHelper.ExceptionHandler( e );
		}

		this.arg = sv;
		this.result = result;
	}}

	//************************************************************************************************
	// 6 Facility Info

	class GetFacilityInfoBFClass extends BackgroundFunc<FacilityInfoEntity,Boolean> {}
	public void GetFacilityInfo(String pin, String t1, String t2)
	{
		BackgroundFunc.Go( new GetFacilityInfoBFClass(), get_onGFI(pin,t1,t2), get_onGFIComplete(), "-onGFI-");
	}

	private onGFIComplete get_onGFIComplete() { onGFIComplete o = new onGFIComplete(); o.arg1 = this; return o; }
	class   onGFIComplete extends RunnableWithArgs<FacilityInfoEntity,Boolean> { public void run() // ---> call in ui thread
	{
		boolean result = this.result;
		if(result) {
			FacilityInfoEntity fie = this.arg;
			UIHelper.Instance().tabFacilityInfo.SetFacilityInfo(fie);
		}
	}}

	private onGFI get_onGFI(String pin,String t1,String t2) { onGFI o = new onGFI(); o.arg1 = this; o.arg2 = pin; o.arg3 = t1; o.arg4 = t2; return o; }
	class onGFI extends RunnableWithArgs<FacilityInfoEntity,Boolean> { public void run()
	{
		MainEngine engine = (MainEngine)this.arg1;
		String pin        = (String)this.arg2;
		String time1      = (String)this.arg3;
		String time2      = (String)this.arg4;

		boolean result = false;
		MsgFromBackground( Act.StartOperation );
		FacilityInfoEntity fie = null;

		try
		{
			URL url = HttpHelper.GetFacilityInfoURL( pin, time1, time2 );
			String data = HttpHelper.httpGet(url);
			JSONObject json = new JSONObject(data);

			fie = FacilityInfoEntity.FromJson(json, data);//, engine.mContext
			//if(fie != null) { fie.Pin = pin; }

			if(fie != null) {
				MsgFromBackground(Act.FacilityInfoRequestOk);
				result = true;
			} else {
				MsgFromBackground(Act.FacilityInfoRequestError);
			}
		}
		catch( Exception e)
		{
			HttpHelper.ExceptionHandler( e );
		}
		this.arg = fie;
		this.result = result;
	}}
}