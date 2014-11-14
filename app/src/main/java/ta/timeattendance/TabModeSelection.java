package ta.timeattendance;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RelativeLayout;

import ta.lib.tabui.*;
import ta.lib.*;
import ta.Database.*;
import ta.timeattendance.R;
import ta.ui.NotificationMessage.NotificationMessageService;

public class TabModeSelection extends Tab implements View.OnClickListener
{
	MainEngine _engine;

	public TabModeSelection(Context context, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(context, paramViewGroup, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance();

		RelativeLayout start_button = (RelativeLayout)this.root.findViewById(R.id.PageModeSelection_start_button);
		start_button.setOnClickListener(this);
		start_button.setTag(R.id.PageModeSelection_start_button);

		RelativeLayout end_button = (RelativeLayout)this.root.findViewById(R.id.PageModeSelection_end_button);
		end_button.setOnClickListener(this);
		end_button.setTag(R.id.PageModeSelection_end_button);

		RelativeLayout check_button = (RelativeLayout)this.root.findViewById(R.id.PageModeSelection_check_button);
		check_button.setOnClickListener(this);
		check_button.setTag(R.id.PageModeSelection_check_button);

		RelativeLayout pause_button = (RelativeLayout)this.root.findViewById(R.id.PageModeSelection_pause_button);
		pause_button.setOnClickListener(this);
		pause_button.setTag(R.id.PageModeSelection_pause_button);

		//android.widget.FrameLayout fl = new android.widget.FrameLayout(context);
		//android.widget.LinearLayout ll = new android.widget.LinearLayout(context);
		//fl.set
	}

	public void onClick_start_button()
	{
		this._engine.setCurrentMode(Mode.StartWork);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick_end_button()
	{
		this._engine.setCurrentMode(Mode.EndWork);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick_check_button()
	{
		this._engine.setCurrentMode(Mode.Check);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	}

	public void onClick_pause_button()
	{
		//this._engine.setCurrentMode(Mode.Pause);
		//UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);

		/*Checkin ch = null;
		int dt = 0;
		Personel[] arr = null;
		try{
			arr = Personel.GetAll();
		}
		catch(Exception e)
		{
			Exception ex = e;
		}

		int		svId = 2;
		//int		wId1   = 34923;
		//String	wCard1 = "444231477";
		//int		wId2   = 54806;
		//String	wCard2 = "444240565";
		int		wId1   = arr[0].Id;
		String	wCard1 = arr[0].CardId;
		int		wId2   = arr[1].Id;
		String	wCard2 = arr[1].CardId;


		dt = ta.lib.Common.DateTime.GetCurrentDateTime().ToUnixSeconds();
		ch = new Checkin(
			svId,		// SupervicerId
			wId1,		// WorkerId
			wCard1,		// CardId
			ta.timeattendance.Mode.StartWork, 15,
			dt,			// DateTime
			32, 15232
		);
		long res1 = ch.Save();


		dt = ta.lib.Common.DateTime.GetCurrentDateTime().AddDays(1).ToUnixSeconds();
		ch = new Checkin(
			svId,		// SupervicerId
			wId1,		// WorkerId
			wCard1,		// CardId
			ta.timeattendance.Mode.EndWork, 15,
			dt,			// DateTime
			32, 15232
		);
		//long res2 = ch.Save();

		int aaa = 9;*/

		//NotificationMessageService nms = new NotificationMessageService();
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.PageModeSelection_start_button:{
					onClick_start_button();
				break;}
				case R.id.PageModeSelection_end_button:{
					onClick_end_button();
				break;}
				case R.id.PageModeSelection_check_button:{
					onClick_check_button();
				break;}
				case R.id.PageModeSelection_pause_button:{
					onClick_pause_button();
				break;}
			}
		}
	}
}