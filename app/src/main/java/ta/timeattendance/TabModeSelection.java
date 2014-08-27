package ta.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RelativeLayout;

import ta.lib.tabui.*;
import ta.lib.*;
import ta.timeattendance.R;

public class TabModeSelection extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	public final String ACTION_START_WORK_SELECTION = "START.WORK";

	public TabModeSelection(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		//TALog.Log("===========TabModeSelection=================:" + this);
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
		this._engine.setCurrentMode(Mode.Pause);
		//this.mEngine.showScreen(State.WAIT_MODE, 0L);
		               UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
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