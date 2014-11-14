package ta.lib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.widget.Toast;
import android.os.Handler;

import ta.timeattendance.*;
import ta.Tabs.CheckinList.*;
import ta.Tabs.FacilityInfo.*;
import ta.Tabs.Settings.*;
import ta.Tabs.PersonalList.*;

import ta.timeattendance.MainActivityProxy;
import ta.lib.tabui.Tab;
import ta.lib.Controls.*;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainActivity.State;
import ta.lib.RunnableWithArgs;
import ta.Tabs.PersonalInfo.TabPersonalInfo;
import ta.Tabs.PointsList.TabPointsList;
import ta.Tabs.CategoryList.TabCategoryList;
import ta.Tabs.TemplateList.TabTemplateList;
import ta.Tabs.SessionList.TabSessionList;
import ta.Tabs.AttachNFC.TabAttachNFC;

import android.support.v4.app.FragmentActivity;

import ta.timeattendance.R;


public class UIHelper implements IMessageReceiver
{
	public static UIHelper    __instance;
	private MainActivityProxy context;
	private ViewGroup         rootView;
	public  State             currentState;

	public  SvBox           svBox;
	//public  WrapperCtrl<PanelButton> panelButton;
	//public  TabMainMenu      tabMainMenu;

	//private TabPin           tabPin;
	//private TabReference      tabReference;
	//private TabPointsList    tabPointsList;
	//private TabModeSelection tabModeSelection;
	//private TabWait          tabWait;
	//private TabPersonelInfo  tabPersonelInfo;
	//private TabPersonelList  tabPersonelList;
	//private TabCheckinList   tabCheckinList;
	//private TabFacilityInfo  tabFacilityInfo;
	//private TabSettings      tabSettings;
	//private TabCategoryList      tabCategoryList;
	//private TabTemplateList      tabTemplateList;

	//private HttpMessage       _myRunnable;
	private ProgressDialog    mProgress;
	//private final Object _lockObj = new Object();
	private Runnable          serverRunnable;
	private Runnable          showScreenRunnable;

	
	//*********************************************************************************************
	//       instance
	public static UIHelper Instance()
	{
		return UIHelper.__instance;
	}
	public static void CreateInstance( ta.timeattendance.MainActivityProxy context, ViewGroup root, ViewGroup frame)
	{
		UIHelper.__instance = null;
		UIHelper.__instance = new UIHelper(context,root,frame);
	}
	public static void DeleteInstance()
	{
		UIHelper.__instance = null;
	}

	//*********************************************************************************************
	//       ctor
	private UIHelper( ta.timeattendance.MainActivityProxy context, ViewGroup root, ViewGroup frame)
	{
		this.Nulling();

		this.context = context;
		ViewGroup contentView = root;
		this.rootView = frame;
		MainActivity.get_RespondHandler(); // create handler

		//*****************************************************************************************

		new PanelButton( this.context, contentView, R.layout.panel_button, R.id.PnBtn_RootId, this);
		this.svBox = new SvBox( this.context, this.rootView, this);

		TabItem[] arr = {
		new TabItem(State.PIN.ordinal(),
			new TabPin(            this.context, this.rootView, R.layout._1_pin,                 R.id.Pin_RootId)
		),
		new TabItem(State.REFERENCE.ordinal(),
			new TabReference(      this.context, this.rootView, R.layout._5_reference,              R.id.Reference_Id)
		),
		new TabItem(State.FLAG_POINTS_LIST.ordinal(),
			new TabPointsList(     this.context, this.rootView, R.layout.p_points_list_2,       R.id.PagePointsList)
		),
		new TabItem(State.CHECKIN_LIST.ordinal(),
			new TabCheckinList(    this.context, this.rootView, R.layout._4_checkin_list,        R.id.PageCheckinList)
		),
		new TabItem(State.MODE_SELECTION.ordinal(),
			new TabModeSelection(  this.context, this.rootView, R.layout.page_mode_selection,    R.id.PageModeSelection)
		),
		new TabItem(State.WAIT_MODE.ordinal(),
			new TabWait(           this.context, this.rootView, R.layout.page_wait2,             R.id.PageWait)
		),
		new TabItem(State.PERSONEL_INFO.ordinal(),
			new TabPersonalInfo(   this.context, this.rootView, R.layout.page_personel_info,     R.id.PagePersonelInfo)
		),
		new TabItem(State.PERSONEL_LIST_MODE.ordinal(),
			new TabPersonalList(   this.context, this.rootView, R.layout.p_personel_list,     R.id.PersonelList_RootId)
		),
		new TabItem(State.FACILITY_INFO.ordinal(),
			new TabFacilityInfo(   this.context, this.rootView, R.layout.p_facility_info,        R.id.FacilityInfo_RootId)
		),
		new TabItem(State.FLAG_SETTINGS.ordinal(),
			new TabSettings(       this.context, this.rootView, R.layout.p_settings,             R.id.Settings_RootId)
		),
		new TabItem(State.FLAG_CATEGORY.ordinal(),
			new TabCategoryList(   this.context, this.rootView, R.layout.p_category_list,        R.id.Category_RootId)
		),
		new TabItem(State.FLAG_TEMPLATE.ordinal(),
			new TabTemplateList(   this.context, this.rootView, R.layout.p_template_list,        R.id.Template_RootId)
		),
		new TabItem(State.SESSION_FLAG.ordinal(),
			new TabSessionList(    this.context, this.rootView, R.layout.p_session_list,        R.id.Session_RootId)
		),
		new TabItem(State.ATTACH_NFC_FLAG.ordinal(),
			new TabAttachNFC(    this.context, this.rootView, R.layout.p_attach_nfc,        R.id.AttachNFC_RootId)
		)
		};

		this.__tabConteiner = new TabConteiner(arr);
		this.SetToDefaultState();
	}
	
	private TabConteiner __tabConteiner;
	public TabConteiner get_TabConteiner()
	{
		return __tabConteiner;
	}

	public static class TabConteiner
	{
		private TabItem[] _items;
		public TabConteiner(TabItem[] arr)
		{
			_items = arr;
		}

		public TabItem GetByEnum(State state)
		{
			TabItem res  = null;
			int intState = state.ordinal();
			int count    = _items.length;
			for( int i = 0; i < count; i++)
			{
				if(_items[i].StateInt == intState)
				{
					res = _items[i];
				}
			}
			return res;
		}
		public void HideExceptCurrentAndShowCurrent(State state)
		{
			int intState = state.ordinal();
			int count    = _items.length;
			int indexTab = -1;
			for( int i = 0; i < count; i++)
			{
				if( _items[i].StateInt != intState && _items[i].Tab.IsShow())
				{
					_items[i].Tab.Hide();
				}
				if(_items[i].StateInt == intState)
				{
					indexTab = i;
				}
			}
			if(indexTab != -1)
			{
				_items[indexTab].Tab.Show();
			}
		}
		public void Hide()
		{
			int count = _items.length;
			for( int i = 0; i < count; i++)
			{
				_items[i].Tab.Hide();
			}
		}
		public void Clear()
		{
			int count = _items.length;
			for( int i = 0; i < count; i++)
			{
				_items[i].Tab.Clear();
			}
		}
	}
	
	public static class TabItem 
	{
		public TabItem(int stateInt, Tab tab){ StateInt = stateInt; Tab = tab;}
		public int StateInt;
		public Tab Tab;
	}

	public void UIHelper_Clear()
	{
		get_TabConteiner().Clear();

		this.Nulling();
	}
	private void Nulling()
	{
		//tabPin = null;
		//tabReference = null;
		//tabPointsList = null;
		//tabModeSelection = null;
		//tabWait = null;
		//tabPersonelInfo = null;
		//tabPersonelList = null;
		//tabCheckinList = null;
		//tabFacilityInfo = null;
		//tabSettings = null;
		//tabCategoryList = null;
		//tabTemplateList = null;

		this.context = null;
		this.rootView = null;
		this.currentState = State.NULL;

		//this._myRunnable = null;
		this.mProgress = null;
		this.serverRunnable = null;
		this.showScreenRunnable = null;
	}



	//*********************************************************************************************
	//       manage state
	public void SetToDefaultState()
	{
		this.currentState = State.NULL;
		this.HideAll();
	}
	public void HideAll()
	{
		get_TabConteiner().Hide();
	}



	//*********************************************************************************************
	//**     Event
	class   CurrentStateChangedEventClass extends Event<State,Boolean> {}
	private CurrentStateChangedEventClass __currentStateChanged;
	public void set_CurrentStateChanged(RunnableWithArgs<State,Boolean> runnable)
	{
		if( this.__currentStateChanged == null ){
			this.__currentStateChanged = new CurrentStateChangedEventClass();
		}
		this.__currentStateChanged.Add(runnable);
	}


	public void switchState(State state)
	{
		try {

		this.currentState = state;
		get_TabConteiner().HideExceptCurrentAndShowCurrent(state);
		this.__currentStateChanged.RunEvent( this.currentState );

		} catch(Exception e) {
			Exception ex = e;
		}

		//int intPin              = State.PIN.ordinal();
		//int intPointsList       = State.FLAG_POINTS_LIST.ordinal();
		//int intSelection        = State.MODE_SELECTION.ordinal();
		//int intWaitMode         = State.WAIT_MODE.ordinal();
		//int intPersonelListMode = State.PERSONEL_LIST_MODE.ordinal();
		//int intPersonelInfo     = State.PERSONEL_INFO.ordinal();
		//int intCHECKIN_LIST     = State.CHECKIN_LIST.ordinal();
		//int intReference        = State.REFERENCE.ordinal();
		//int intFACILITY_INFO    = State.FACILITY_INFO.ordinal();
		//int intFLAG_SETTINGS    = State.FLAG_SETTINGS.ordinal();
		//int intFLAG_CATEGORY    = State.FLAG_CATEGORY.ordinal();
		//int intFLAG_TEMPLATE    = State.FLAG_TEMPLATE.ordinal();

		//if(     intParamState == intPin) {
		//	this.tabPin.Show();
		//}
		//else if(intParamState == intReference) {
		//	this.tabReference.Show();
		//}
		//else if(intParamState == intPointsList) {
		//	this.tabPointsList.Show();
		//}
		//else if(intParamState == intSelection) {
		//	this.tabModeSelection.Show();
		//}
		//else if(intParamState == intWaitMode) {
		//	this.tabWait.Show();
		//}
		//else if(intParamState == intPersonelListMode) {
		//	this.tabPersonelList.Show();
		//}
		//else if(intParamState == intPersonelInfo) {
		//	this.tabPersonelInfo.Show();
		//}
		//else if(intParamState == intCHECKIN_LIST) {
		//	this.tabCheckinList.Show();
		//}
		//else if(intParamState == intFACILITY_INFO) {
		//	this.tabFacilityInfo.Show();
		//}
		//else if(intParamState == intFLAG_SETTINGS) {
		//	this.tabSettings.Show();
		//}
		//else if(intParamState == intFLAG_CATEGORY) {
		//	this.tabCategoryList.Show();
		//}
		//else if(intParamState == intFLAG_TEMPLATE) {
		//	this.tabTemplateList.Show();
		//}
	}

	public void onBackPressed()
	{
		State state = this.currentState;

		switch(state)
		{
			case PIN:
			case MODE_SELECTION:{
				Builder b = new android.app.AlertDialog.Builder(this.context);
				MainActivityProxy ma = (MainActivityProxy)this.context;

				Object o1 = b.setMessage("Вы действительно хотите выйти?");
				Object o2 = b.setNegativeButton("Нет", null).setPositiveButton("Да", ma.get_onClickBackBtn() );
				Object o3 = b.create();
				Object o4 = b.show();
			break;}

			case CHECKIN_LIST:
			case REFERENCE:
			case FACILITY_INFO:{
				switchState(State.MODE_SELECTION);
			break;}

			case WAIT_MODE:{
				switchState(State.MODE_SELECTION);
			break;}
			case PERSONEL_LIST_MODE:{
				switchState(State.MODE_SELECTION);
			break;}
			case FLAG_POINTS_LIST:{
				switchState(State.FLAG_SETTINGS);
			break;}
			case PERSONEL_INFO:{
				switchState(State.WAIT_MODE);
			break;}
			case FLAG_SETTINGS:
			case FLAG_CATEGORY:
			case FLAG_TEMPLATE:
			case SESSION_FLAG:
			case ATTACH_NFC_FLAG:{
				switchState(State.MODE_SELECTION);
			break;}
		}
	}





	public static enum Act { 
		StartOperation,
		Text,
		TimeoutException, ServerNotRespond, ServerNotAvailable, UnhandledException, 
		ServerAuthOk, LocalAuthOk, ServerAuthError, LocalAuthError, 
		SyncOk, SyncError, CheckinSuccess, CheckinSaveLocal, CheckinFailed,
		LoadNewVersionFailed, LoadNewVersionSuccess, CanNotCreateFileForLoading,
		NfcDisabled,
		FacilityInfoRequestOk, FacilityInfoRequestError,
		CheckpointNotSelect, CategoryNotSelect, TemplateNotSelect; }
	private HttpMessage get_HttpMessage(Act act)
	{
		return this.get_HttpMessage( act, null, -1);
	}
	private HttpMessage get_HttpMessage(Act act, String str)
	{
		return this.get_HttpMessage( act, str, -1);
	}
	private HttpMessage get_HttpMessage(Act act, String str, int time)
	{
		//if(_myRunnable == null)
		//{
			HttpMessage _myRunnable = new HttpMessage();
			_myRunnable.arg1 = this;
		//}
		_myRunnable.arg2 = act;
		_myRunnable.arg3 = str;
		_myRunnable.arg4 = time;

		return _myRunnable;
	}
	private class HttpMessage extends RunnableWithArgs { public void run() 
	{
		//try {
		//Thread.sleep(250L);
		//} catch (InterruptedException e) {
		//}

		//synchronized( _lockObj )
		//{
			UIHelper _ui = (UIHelper)this.arg1;
			Act _data = (Act)this.arg2;

			if( _data == Act.StartOperation )
			{
				_ui.mProgress = ProgressDialog.show( _ui.context, "", "Пожалуйста, подождите...", true );
			}
			else if( _data == Act.Text )
			{
				_ui.ToastCENTER( (String)this.arg3, (Integer)this.arg3 );
			}
			else
			{
				if(_ui.mProgress != null && _ui.mProgress.isShowing())
				{
					_ui.mProgress.dismiss();
				}
				switch(_data)
				{
					case ServerAuthOk:{
						_ui.Toast("Cерверная авторизация \nпрошла успешно!"); //TALog.Log("Авторизация прошла успешно!");
					break;}
					case LocalAuthOk:{
						_ui.Toast("Локальная авторизация \nпрошла успешно!"); //TALog.Log("Авторизация прошла успешно!");
					break;}
					case ServerAuthError:{
						_ui.Toast("Супервайзер не найден на сервере!");
					break;}
					case LocalAuthError:{
						_ui.Toast("Супервайзер не найден на телефоне!");
					break;}


					case SyncOk:{
						_ui.Toast("Синхронизация завершена!" );
					break;}
					case SyncError:{
						_ui.Toast("На сервере отсутствует текущий супервайзер!" );
					break;}


					case CheckinSuccess:{
						_ui.Toast("Чекин прошёл успешно!");
					break;}
					case CheckinFailed:{
						_ui.Toast("соединение с интернетом отсутствует \n нажмите на кнопку синхронизации при появлении интернета");
					break;}
					case CheckinSaveLocal:{
						_ui.Toast(            "чекин сохранен локально \n нажмите на кнопку синхронизации при появлении интернета");
					break;}


					case TimeoutException:{
						_ui.Toast("Время ожидания ответа от сервера истекло!" );
					break;}
					case ServerNotRespond:{
						_ui.Toast("Сервет не отвечает!" );
					break;}
					case ServerNotAvailable:{
						_ui.Toast("отсутствует подключение к интернету" );
						// инета нет , чекин сохр локально, исп автоном работу
					break;}
					case UnhandledException:{
						_ui.Toast( (String)this.arg3, (Integer)this.arg4 );
					break;}

					case NfcDisabled:{
						_ui.Toast("Nfc датчик отключён");
					break;}


					case FacilityInfoRequestOk:{
						_ui.Toast("Оперативная обстановка доступна!" );
					break;}
					case FacilityInfoRequestError:{
						_ui.Toast("Оперативная обстановка не доступна!" );
					break;}
					case CheckpointNotSelect:{
						_ui.Toast("Выберите точку в настройках!" );
					break;}
					case CategoryNotSelect:{
						_ui.Toast("Выберите категорию в настройках!" );
					break;}
					case TemplateNotSelect:{
						_ui.Toast("Выберите шаблон в настройках!" );
					break;}
				}
			}
		//synchronized( _lockObj )
		//{
		//    _lockObj.notifyAll();
		//} // synchronized
	}}
	private void WaitUpdateUI(Runnable r)
	{
		MainActivity.get_RespondHandler().post(r);
		//synchronized( _lockObj ) { try { _lockObj.wait(); } catch (InterruptedException e) { } }
	}
	public void MsgFromBackground(Act act)
	{
		WaitUpdateUI(get_HttpMessage( act ));
	}
	public void MsgExceptionFromBackground(Exception e)
	{
		String str = ExceptionToMsg(e);
		WaitUpdateUI(get_HttpMessage( Act.UnhandledException, str, android.widget.Toast.LENGTH_LONG ));
	}
	//public void MsgUnknownExceptionFromBackground()
	//{
	//            String name = e.getClass().getName();
	//            int index = name.lastIndexOf(".");
	//            if(index != -1 )
	//            {
	//                String target = ".";
	//                String replacement = "";
	//                name = name.substring(index).replace(target, replacement);

	//                this.MsgExceptionFromBackground( name + "\n\n" + e.getMessage(), android.widget.Toast.LENGTH_LONG );
	//                //UIHelper.Instance()
	//            }
	//}
	public static String ExceptionToMsg(Exception e)
	{
		String res = ta.lib.Common.CommonHelper.GetExceptionName(e);
		res = "*********\n" +    "\t-"+res+"-\n"    +  "*********\n" +    e.getMessage();
		return res;
	}





	//************************************************************************************************
	//       LoadingProgressBar
	//************************************************************************************************
	public void MsgProgress(Act act)     { WaitUpdateUI(get_MsgProgress( act,                    null )); }
	public void MsgProgress(String str)  { WaitUpdateUI(get_MsgProgress( Act.Text,               str )); }
	public void MsgProgress(Exception e) { WaitUpdateUI(get_MsgProgress( Act.UnhandledException, ExceptionToMsg(e) )); }

	private MsgProgress get_MsgProgress(Act act){ return get_MsgProgress(act,null); }
	private MsgProgress get_MsgProgress(Act act, String str)
	{
		MsgProgress o = new MsgProgress();
		o.arg1 = this;
		o.arg2 = act;
		o.arg3 = str;

		return o;
	}
	private LoadingProgressBar _loadingProgressBar;
	private LoadingProgressBar get_LoadingProgressBar()
	{
		if(this._loadingProgressBar == null)
		{
			this._loadingProgressBar = new LoadingProgressBar();
			this._loadingProgressBar.Init(this.context, this.context.get_FragmentManager());
		}
		return this._loadingProgressBar;
	}
	private class MsgProgress extends RunnableWithArgs { public void run()
	{
		UIHelper _ui =   (UIHelper)this.arg1;
		Act      _data = (Act)     this.arg2;
		String   _str =  (String)  this.arg3;

		switch(_data)
		{
			case StartOperation:{
				_ui.get_LoadingProgressBar().Show();
				_ui.get_LoadingProgressBar().SetTitle("Обновление");
			break;}
			case Text:{
				_ui.get_LoadingProgressBar().AddMessage(_str);
			break;}
			case LoadNewVersionFailed:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("обновление не удалось,\nотсутствует интернет");
				//_ui.get_LoadingProgressBar().Message("обновление не удалось,\nотсутствует интернет");
			break;}
			case LoadNewVersionSuccess:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("обновление завершено");
				//_ui.get_LoadingProgressBar().Message("обновление завершено");
			break;}
			case CanNotCreateFileForLoading:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast("не удаётся создать файл\nдля загрезки новой версии");
			break;}
			case UnhandledException:{
				_ui.get_LoadingProgressBar().Hide();
				_ui.Toast( (String)this.arg3 );
			break;}
		}
	}}

	private SetTitle get_SetTitle(String str) { SetTitle o = new SetTitle();o.arg1 = this;o.arg2 = str;return o; }
	private class SetTitle extends RunnableWithArgs { public void run()
	{
		((UIHelper)this.arg1).get_LoadingProgressBar().SetTitle((String)this.arg2);
	}}
	public void SetTitle(String str) { WaitUpdateUI(get_SetTitle(str)); }

	private AddMessage get_AddMessage(String str) { AddMessage o = new AddMessage();o.arg1 = this;o.arg2 = str;return o; }
	private class AddMessage extends RunnableWithArgs { public void run()
	{
		((UIHelper)this.arg1).get_LoadingProgressBar().AddMessage((String)this.arg2);
	}}
	public void AddMessage(String str) { WaitUpdateUI(get_AddMessage(str)); }




	
	public void ToastInUIThread(String str,int time)
	{
		WaitUpdateUI(get_HttpMessage( Act.Text, str, time));
	}
	public void Toast(String paramString)
	{
		Toast( paramString, android.widget.Toast.LENGTH_LONG);
	}
	public void Toast(String paramString, int duration)
	{
		Toast.makeText(this.context, paramString, duration).show();
	}
	public void ToastCENTER(String paramString, int duration)
	{
		Toast toast = Toast.makeText(this.context, paramString, duration);
		toast.setGravity(android.view.Gravity.CENTER, 0, 0);
		toast.show();
	}
	public void hideKeyboard()
	{
		(
			(InputMethodManager)this.context.getSystemService("input_method")
		)
		.hideSoftInputFromWindow(   this.context.getCurrentFocus().getWindowToken(),   2   );
	}





	//************************************************************************************************
	//       MessageBox
	//************************************************************************************************
	public void MessageBoxInUIThread(String message, 
		android.content.DialogInterface.OnClickListener onClickOk, 
		android.content.DialogInterface.OnClickListener onClickNo)
	{
		WaitUpdateUI(get_MessageBox(message,onClickOk,onClickNo));
	}
	public messageBox get_MessageBox(String message, 
		android.content.DialogInterface.OnClickListener onClickOk, 
		android.content.DialogInterface.OnClickListener onClickNo)
	{
		messageBox m = new messageBox();
		m.arg1 = this.context;
		m.arg2 = message;
		m.arg3 = onClickOk;
		m.arg4 = onClickNo;
		return m;
	}
	class messageBox extends RunnableWithArgs{ public void run(){
		Context context = (Context)this.arg1;
		String message = (String)this.arg2;
		android.content.DialogInterface.OnClickListener onClickOk = (android.content.DialogInterface.OnClickListener)this.arg3;
		android.content.DialogInterface.OnClickListener onClickNo = (android.content.DialogInterface.OnClickListener)this.arg4;

		android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(   context   );
		b.setMessage(   message   );
		b.setPositiveButton("Ok",  onClickOk);
		if(onClickNo != null)
			b.setNegativeButton("Нет", onClickNo);
		b.create();
		b.show();
			
		int aaa = 9;
	}}





	//************************************************************************************************
	//       run in other thread and wait
	//************************************************************************************************
	private RunnableAndNotify get_RunnableAndNotify(Runnable runnable)//, Object obj
	{
		RunnableAndNotify r = new RunnableAndNotify(); r.arg1 = runnable;// r.arg2 = obj; 
		return r;
	}
	class RunnableAndNotify extends RunnableWithArgs{ public void run(){
	    Runnable target = (Runnable)this.arg1;
	    //Object obj      =           this.arg2;
	    target.run();
		//synchronized( obj )
		//{
		//    obj.notifyAll();
		//    int aaa = 9;
		//    int aaa2 = aaa - 2;
		//} // synchronized
	}}
	public void RunAndWait(Runnable runnable)//, Object obj
	{
		Thread t = new Thread(get_RunnableAndNotify(runnable),"-RunAndWait-");//, obj
		t.start();

		//synchronized( obj )
		//{
		//    try {
		//        obj.wait();
		//    } catch (InterruptedException e) {
		//        //...
		//    }
		//}
		try {
		t.join();
		} catch (InterruptedException e) {
			Exception ex = e;
		}

		int aaa = 9;
		int aaa2 = aaa - 2;
	}
	//public void RunInUIThreadAndWait(Runnable runnable, Object obj)
	//{
	//    this.serverRunnable = get_RunnableAndNotify(runnable, obj);
	//    this.get_RespondHandler().post(this.serverRunnable);

	//    synchronized( obj )
	//    {
	//        try {
	//            obj.wait();
	//        } catch (InterruptedException e) {
	//            //...
	//        }
	//    }
	//    int aaa = 9;
	//    int aaa2 = aaa - 2;
	//}



	//************************************************************************************************
	//       show status bar
	//************************************************************************************************
	public void ShowStatusBar()
	{
		android.view.Window wnd = this.context.getWindow();
		wnd.clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}




	//*********************************************************************************************
	//       static constructors
	static
	{
		UIHelper.__instance = null;
	}
}