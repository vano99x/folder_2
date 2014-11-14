package ta.Tabs.Settings;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import ta.Tabs.PersonalList.IPersonalListModel;
import ta.lib.*;
import ta.lib.Common.*;
import ta.lib.DatePicker.MyDatePicker;
import ta.lib.tabui.Tab;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;
import ta.Database.*;
//import ta.Tabs.Settings.CurrentVersionServices.CurrentVersionLoadedEventClass;
import ta.timeattendance.R;

public class TabSettings extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	private CheckpointAdapter _checkpointAdapter;
	private ta.timeattendance.MainActivityProxy _mainActivity;
	private TextView  _labelCurrentVersion;

	private ICurrentVersionServices __currentVersionServices;


	public TabSettings(
		MainActivityProxy context, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(context, paramViewGroup, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance();
		this._mainActivity = context;
		
		//Button referenceBtn = (Button)this.root.findViewById(R.id.Settings_ReferenceBtn_Id);
		//referenceBtn.setOnClickListener(this);
		//referenceBtn.setTag(new Object[]{R.id.Settings_ReferenceBtn_Id});
		
		View updateBtn = this.root.findViewById(R.id.Settings_UpdateBtn_Id);
		updateBtn.setOnClickListener(this);
		updateBtn.setTag(new Object[]{R.id.Settings_UpdateBtn_Id});
		
		View pointsListBtn = this.root.findViewById(R.id.Settings_PointsListBtn_Id);
		pointsListBtn.setOnClickListener(this);
		pointsListBtn.setTag(new Object[]{R.id.Settings_PointsListBtn_Id});
		
		View attachNFC_Btn = this.root.findViewById(R.id.Settings_AttachNFC_Btn_Id);
		attachNFC_Btn.setOnClickListener(this);
		attachNFC_Btn.setTag(new Object[]{R.id.Settings_AttachNFC_Btn_Id});

		_labelCurrentVersion = (TextView)this.root.findViewById(R.id.Settings_CurrentVersionTextView_Id);
		this.__currentVersionServices = Bootstrapper.Resolve( ICurrentVersionServices.class );
		this.__currentVersionServices.set_CurrentVersionLoaded(get_onCurrentVersionLoaded());
	}

	//*********************************************************************************************
	//**     Event Handler
	private onCVL get_onCurrentVersionLoaded() { onCVL o = new onCVL(); o.arg1 = this; return o; }
	class   onCVL extends RunnableWithArgs<String,Boolean> { public void run()
	{
		TabSettings _this = (TabSettings)this.arg1;
		_this._labelCurrentVersion.setText( this.arg );
	}}



	//*********************************************************************************************
	//**     private func
	private void LoadAndShowCurrentVersion()
	{
		this.__currentVersionServices.LoadCurrentVersionNumber();
	}


	//*********************************************************************************************
	//**     Code behind override
	public void Show()
	{
		super.Show();
		//___old___//Point [] pointArr   = Point.getBySuperviser( superviser.Id, this.context);
		LoadAndShowCurrentVersion();
	}



	//*********************************************************************************************
	//**     Control Handler
	//private void referenceBtn_Click()
	//{
	//UIHelper.Instance().switchState(MainActivity.State.REFERENCE);
	//}

	private onClickMsb_Yes get_onClickMsb_Yes() { onClickMsb_Yes o = new onClickMsb_Yes(); o._this = this; return o; }
	  class onClickMsb_Yes implements OnClickListener { public TabSettings _this;public void onClick( DialogInterface dialogInterface, int pInt)
	{
		UpdateManager.LoadNewVersionAndRun(_this._mainActivity);
	}}
	private onClickMsb_No get_onClickMsb_No() { onClickMsb_No o = new onClickMsb_No(); o._this = this; return o; }
	  class onClickMsb_No implements OnClickListener { public TabSettings _this;public void onClick( DialogInterface dialogInterface, int pInt)
	{
		int aaa = 9;
	}}
	private void updateBtn_Click()
	{
		UIHelper.Instance().MessageBoxInUIThread(
			"Обновить?",
			get_onClickMsb_Yes(), 
			get_onClickMsb_No());
	}
	private void pointsListBtn_Click()
	{
		UIHelper.Instance().switchState(MainActivity.State.FLAG_POINTS_LIST);
	}
	private void attachNFC_Click()
	{
		IPersonalListModel plm = Bootstrapper.Resolve(IPersonalListModel.class);
		plm.set_CallerView(MainActivity.State.ATTACH_NFC_FLAG);

		UIHelper.Instance().switchState(MainActivity.State.PERSONEL_LIST_MODE);
	}



	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
			switch(integer)
			{
				//case R.id.Settings_ReferenceBtn_Id:{
				//referenceBtn_Click();
				//break;}
				case R.id.Settings_UpdateBtn_Id:{
					updateBtn_Click();
				break;}
				case R.id.Settings_PointsListBtn_Id:{
					pointsListBtn_Click();
				break;}
				case R.id.Settings_AttachNFC_Btn_Id:{
					attachNFC_Click();
				break;}
			}
		}
	}
}