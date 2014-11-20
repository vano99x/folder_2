package ta.timeattendance.Models;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import ta.Tabs.CheckinList.IChekinService;
import ta.lib.*;
import ta.lib.UIHelper.Act;
import ta.Database.*;
import ta.timeattendance.MainEngine;
import ta.timeattendance.MainActivity;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.Services.*;

public class SupervisorModel implements ISupervisorModel
{
	private IAppService __appService;
	private boolean __isClearDependencies;
	private boolean __isKeepAlive;
	private IChekinService   __sendChekinService;
	private INfcEventService __nfcEventService;

	private IPointModel      __pointModel;
	private ICategoryModel   __categoryModel;
	private ITemplateModel   __templateModel;
	
	//private static int _counter;
	static
	{
		//_counter = 0;
	}

	public SupervisorModel()
	{
		//_counter++;
		this.__isClearDependencies = false;
		this.__isKeepAlive = true;
		this.CurrentSuperviserApplied = new CurrentSuperviserAppliedEventClass();

		UpdateDependencies();

		this.__sendChekinService = Bootstrapper.Resolve( IChekinService.class );
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );

		this.__categoryModel = Bootstrapper.Resolve( ICategoryModel.class );
		this.__templateModel = Bootstrapper.Resolve( ITemplateModel.class );

		this.__nfcEventService = Bootstrapper.Resolve( INfcEventService.class );
		this.__nfcEventService.add_NewTagReceived(get_onNfcTagApply());
	}


	// call instead nulling reference
	public void ClearDependencies()
	{
		this.__appService = null;
		this.__isClearDependencies = true;
	}
	// if is clear, then in recreation call UpdateDependencies
	public boolean get_IsClearDependencies() {
		return this.__isClearDependencies;
	}
	// call insted recreation
	public void UpdateDependencies()
	{
		this.__appService = Bootstrapper.Resolve( IAppService.class );
		//this.__appService.get_Logout().Add(get_onLogout());
		this.__appService.add_LogoutClearing(get_onLogout());
		this.__isClearDependencies = false;
	}
	// confirmation that is need call ClearDependencies insted recreation
	public boolean get_IsKeepAlive() {
		return this.__isKeepAlive;
	}


	//*********************************************************************************************
	//**     Event Handler
	private onClrn get_onLogout() { onClrn o = new onClrn(); o.arg1 = this; return o; }
	private static class onClrn extends RunnableWithArgs<Object,Object> { public void run()
	{
		SupervisorModel _this = (SupervisorModel)this.arg1;
		_this.__currentSuperviser = null;
		_this.__isKeepAlive = false;
	}}

	private temp3 get_onNfcTagApply() { temp3 o = new temp3(); o.arg1 = this; return o; }
	private static class temp3 extends RunnableWithArgs<Long,Object> { public void run()
	{
		// creating Chekin only in wait tab
		boolean isWaitTab = UIHelper.Instance().currentState.equals(State.WAIT_MODE);
		if( ! isWaitTab) {
			return;
		}

		SupervisorModel _this = (SupervisorModel)this.arg1;

		long cardId = (Long)this.arg;
		Personel w = Personel.SelectByCard( cardId );
		if(w != null && w.Id != -1)
		{
			temp1 r = _this.get_OnNfcTagApply(w);
			r.run();
		}
		else
		{
			//ta.timeattendance.MainActivity.get_RespondHandler().post(new Runnable() { public void run() {
				//MainEngine.getInstance().SaveCheckinCompleteEvent.RunEvent(null,false);
			MainActivity.get_FragmentActivityStatic().NfcNotBusy();
			//}});
			UIHelper.Instance().MessageBoxInUIThread(
				"Сотрудник не найден! \r\n " + Long.toHexString(cardId),
				_this.get_onClickOkMessageBox(),
				null
			);
		}
	}}



	//*********************************************************************************************
	//**     Event
	private static class CurrentSuperviserAppliedEventClass extends Event<Personel,Object> {}
	private              CurrentSuperviserAppliedEventClass CurrentSuperviserApplied;
	@Override
	public void SvChanged_EventAdd(RunnableWithArgs r)
	{
		int aaa = 9;
		CurrentSuperviserApplied.Add(r);
	}



	//*********************************************************************************************
	//**     Property
	private Personel __currentSuperviser;
	@Override
	public Personel get_CurrentSupervisor()
	{
		return this.__currentSuperviser;
	}
	@Override
	public void set_CurrentSuperviser(Personel p)
	{
		if(p != null)
		{
			this.__currentSuperviser = p;
			this.__appService.Login();
			this.CurrentSuperviserApplied.RunEvent( this.__currentSuperviser );
		}
	}



	private Personel __selectedWorker;
	@Override
	public Personel get_SelectedWorker()
	{
		return this.__selectedWorker;
	}
	@Override
	public void set_SelectedWorker(Personel w)
	{
		if(w != null) {
			this.__selectedWorker = w;
		}
	}
	//private Personel WorkerAttachNFC



	private Personel __workerAppliedNFC;
	@Override
	public Personel get_WorkerAppliedNFC()
	{
		return this.__workerAppliedNFC;
	}
	@Override
	public void set_WorkerAppliedNFC(Personel w)
	{
		if(w != null) {
			this.__workerAppliedNFC = w;
		}
	}



	//*********************************************************************************************
	//**    Save Checkin
	//public void OnNfcTagApply(long tag)
	//{
	//	temp1 r = get_OnNfcTagApply(tag);
	//	r.arg = tag;
	//	r.run();
	//	//BackgroundFunc.Go(
 // //          get_OnNfcTagApply(tag).Add(get_onBfAuth()),
	//	//	"-OnNfcTagApply-"
	//	//);
	//}
	public void SaveWorkerCheckin(Personel w)
	{
			temp1 r = this.get_OnNfcTagApply(w);
			r.run();
	}

	//private temp3 get_Task_Completed() { temp3 o = new temp3(); o.arg1 = this; return o; }
	//private static class temp3 extends RunnableWithArgs<ArrayList<TplDateEntity>,Boolean> { public void run(){
	//}}

	private temp2 get_onClickOkMessageBox() { temp2 o = new temp2(); o._this = this; return o; }
	private static class temp2 implements OnClickListener { public SupervisorModel _this;public void onClick(DialogInterface di, int anonymousInt)
	{
	}}


	private temp1 get_OnNfcTagApply(Personel p){temp1 o = new temp1(); o.arg1 = this; o.arg = p; return o; }
	private static class temp1 extends RunnableWithEvent<Personel,Object> { public void run() // -> UI thread
	{
		SupervisorModel _this = (SupervisorModel)this.arg1;
		//long cardId = (Long)this.arg;
		//Personel w = Personel.SelectByCard( cardId );
		//if(w != null && w.Id != -1)
		//{
		Personel w = this.arg;
			_this.set_WorkerAppliedNFC(w);
			Point p = _this.__pointModel.get_CurrentPoint();
			if(p == null)
			{
				result = false;
				UIHelper.Instance().MsgFromBackground( Act.CheckpointNotSelect );
			}
			Category c = _this.__categoryModel.get_CurrentCategory();
			if(c == null)
			{
				UIHelper.Instance().MsgFromBackground( Act.CategoryNotSelect );
			}
			Template t = _this.__templateModel.get_CurrentTemplate();
			if(t == null)
			{
				UIHelper.Instance().MsgFromBackground( Act.TemplateNotSelect );
			}

			if( p!=null && c!=null && t!=null )
			{
				Checkin ch = new Checkin(
					_this.get_CurrentSupervisor().Id,	// SupervicerId
					w.Id,											// WorkerId
					w.CardId,										// CardId
					MainEngine.getInstance().getCurrentMode(),						// Mode
					p.Id,											// PointId
					ta.lib.Common.DateTime.GetCurrentUnixSeconds(),		// DateTime
					c.Id,
					t.Id
				);

				_this.__sendChekinService.SaveCheckin(ch);
			}
		//}
		//else
		//{
		//	//ta.timeattendance.MainActivity.get_RespondHandler().post(new Runnable() { public void run() {
		//		//MainEngine.getInstance().SaveCheckinCompleteEvent.RunEvent(null,false);
		//	MainActivity.get_FragmentActivityStatic().NfcNotBusy();
		//	//}});
		//	UIHelper.Instance().MessageBoxInUIThread(
		//		"Сотрудник не найден! \r\n " + Long.toHexString(cardId),
		//		_this.get_onClickOkMessageBox(),
		//		null
		//	);
		//}
	}}
}
