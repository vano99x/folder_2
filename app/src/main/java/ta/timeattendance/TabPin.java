package ta.timeattendance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ta.timeattendance.*;
import ta.Database.Personel;
import ta.lib.*;
import ta.lib.tabui.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.R;

public class TabPin extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	private EditText mPinEditText;
	private boolean _isBisy;

	private ISupervisorModel __svModel;

	public TabPin(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2)
	{
		super( mainActivity, rootView, paramInt1, paramInt2);
		this._engine = MainEngine.getInstance(); //TALog.Log("===========TabPin=================:" + this);
		this._isBisy = false;

		// ctrls
		View continue_button = this.root.findViewById(R.id.continue_button);
		continue_button.setOnClickListener(this);
		continue_button.setTag(R.id.continue_button);

		this.mPinEditText = ((EditText)this.root.findViewById(R.id.pin_edit));
		
		// subscribe on event
		//this._engine.Clearing.Add(get_onClearing());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		onAuthSV aaa = get_onAuthSV();
		this.__svModel.SvChanged_EventAdd(aaa);
	}



	//*********************************************************************************************
	//       Event Handler
	private onAuthSV get_onAuthSV() { onAuthSV o = new onAuthSV(); return o; }
	class onAuthSV extends RunnableWithArgs<Personel,Object> { public void run()
	{
		//Object[] resultArr = (Object[])this.result;
		//boolean result = ((Boolean)resultArr[0]).booleanValue();

		//if(result) {
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
		//}
	}}
	//private onClearing get_onClearing()
	//{
	//onClearing o = new onClearing(); o.arg1 = this; return o;
	//}
	//class onClearing extends RunnableWithArgs { public void run()
	//{
	//TabPin _this = (TabPin)this.arg1;
	//_this.mPinEditText.setText("");
	//}}


	//*********************************************************************************************
	//       Control Handler
	private void continue_button_Click()
	{
		//int Width= this.getParent().getWidth();
		//int Height= this.getParent().getHeight();

		if(this._isBisy == false)
		{
			this._isBisy = true;

			String mPin = this.mPinEditText.getText().toString().trim();
			if(mPin == null || mPin.isEmpty())
			{
				UIHelper.Instance().Toast("Неверный пин-код!");
			}
			else
			{
				UIHelper.Instance().hideKeyboard();
				this._engine.AuthenticateSV( mPin );
			}
			this._isBisy = false;
		}
	}

	//*********************************************************************************************
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.continue_button:{
		            continue_button_Click();
		        break;}
		    }
		}
	}
}