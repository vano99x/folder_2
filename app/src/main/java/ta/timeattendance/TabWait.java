package ta.timeattendance;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import ta.Database.Personel;
import ta.Database.Point;
import ta.Tabs.CheckinList.IChekinService;
import ta.Tabs.PersonalInfo.TabPersonalInfo;
import ta.Tabs.PersonalList.IPersonalListModel;
import ta.lib.*;
import ta.lib.UIHelper.Act;
import ta.lib.tabui.Tab;
import ta.lib.tabui.Tab.AnimationRunner;
import ta.timeattendance.Models.Bootstrapper;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.Models.ISupervisorModel;
import ta.timeattendance.R;
import ta.timeattendance.Services.INfcEventService;

public class TabWait extends Tab implements View.OnClickListener
{
	private TextView _labelMode;
	private ImageView _iconMode;
	private AnimationDrawable wavesAnim = null;
	//private INfcEventService __nfcEventService;
	private IChekinService __chekinService;
	private ISupervisorModel __svModel;

	public TabWait(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);//R.drawable.style_button

		ImageView imageView = (ImageView)this.root.findViewById(R.id.waves_animation);
		this.wavesAnim = ((AnimationDrawable)imageView.getDrawable());
		imageView.post(new Tab.AnimationRunner(this.wavesAnim));

		this._iconMode = ((ImageView)this.root.findViewById(R.id.iconMode));
		this._labelMode = ((TextView)this.root.findViewById(R.id.mode));

		View back_button = this.root.findViewById(R.id.PageWait_back_button);
		back_button.setOnClickListener(this);
		back_button.setTag(R.id.PageWait_back_button );

		View select_button = this.root.findViewById(R.id.PageWait_select_button);
		select_button.setOnClickListener(this);
		select_button.setTag(R.id.PageWait_select_button);

		//this.__nfcEventService = Bootstrapper.Resolve( INfcEventService.class );
		//this.__nfcEventService.add_NewTagReceived(get_onNfcTagApply());

		this.__chekinService = Bootstrapper.Resolve( IChekinService.class );
		this.__chekinService.add_SaveCheckinComplete(get_onSaveCheckin());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
	}



	//*********************************************************************************************
	//**     Event Handler
	//private temp1 get_onNfcTagApply() { temp1 o = new temp1(); o.arg1 = this; return o; }
	//private static class temp1 extends RunnableWithArgs<Long,Object> { public void run()
	//{
	//	TabWait _this = (TabWait)this.arg1;
	//	if(_this.IsShow())
	//	{
	//		_this.__svModel.OnNfcTagApply(this.arg);
	//	}
	//}}

	private RunnableWithArgs get_onSaveCheckin() { return new RunnableWithArgs<Object,Boolean>(this){ public void run()
	{
		TabWait _this = (TabWait)this.arg1;
		if(_this.IsShow())
		{
			TabPersonalInfo pi = ((TabPersonalInfo)UIHelper.Instance().get_TabConteiner().GetByEnum(State.PERSONEL_INFO).Tab);
			pi.IsShowCheckiedWorker = true;
			UIHelper.Instance().switchState(State.PERSONEL_INFO);
		}
	}};}
	

	
	//*********************************************************************************************
	//*      override
	@Override
	public void Show()
	{
		super.Show();

		MainEngine engine = MainEngine.getInstance();
		//Mode mode = engine.getCurrentMode();

		if (engine.getCurrentMode() == Mode.StartWork)
		{
			this._iconMode.setImageResource(R.drawable.start);
			this._labelMode.setText(R.string.mode_start);
		}
		else if (engine.getCurrentMode() == Mode.EndWork)
		{
			this._iconMode.setImageResource(R.drawable.finish);
			this._labelMode.setText(R.string.mode_end);
		}
		else if (engine.getCurrentMode() == Mode.Check)
		{
			this._iconMode.setImageResource(R.drawable.check);
			this._labelMode.setText(R.string.mode_check);
		}
		else if (engine.getCurrentMode() == Mode.Pause)
		{
			this._iconMode.setImageResource(R.drawable.pause2);
			this._labelMode.setText(R.string.mode_pause);
		}
	}



	//*********************************************************************************************
	//       Control Handler
	public void back_button_Click()
	{
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
	}

	public void select_button_Click()
	{
		IPersonalListModel plm = Bootstrapper.Resolve(IPersonalListModel.class);
		plm.set_CallerView(MainActivity.State.PERSONEL_INFO);

		UIHelper.Instance().switchState(MainActivity.State.PERSONEL_LIST_MODE);
	}



	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer) {
			case R.id.PageWait_back_button:{
				back_button_Click();
			break;}
			case R.id.PageWait_select_button:{
				select_button_Click();
			break;}}
		}
	}
}