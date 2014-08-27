package ta.lib.Controls;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ta.lib.*;
import ta.lib.tabui.*;
import ta.timeattendance.*;
import ta.Database.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.R;

public class SvBox extends Tab implements View.OnClickListener
{
	MainEngine _engine;
	private TextView     _labelPoint;
	private TextView     _labelLastName;
	private TextView     _labelName;
	private TextView     _labelThirdName;

	private ISupervisorModel __svModel;
	private IPointModel      __pointModel;

	private boolean _isUiDataValid;

	public SvBox(Context mainActivity, ViewGroup rootView, UIHelper uiHelper)
	{
		super( mainActivity, rootView, R.layout.sv_box, R.id.SvBox_RootId);

		uiHelper.set_CurrentStateChanged(get_CurrentStateChanged());
		this._engine = MainEngine.getInstance();
		_isUiDataValid = false;

		this._labelPoint      = (TextView)    this.root.findViewById(R.id.SvBox_Point);
		//this._NameBlock       = (LinearLayout)this.root.findViewById(R.id.SvBox_NameBlockId);
		this._labelLastName   = (TextView)    this.root.findViewById(R.id.SvBox_LastName);
		this._labelName       = (TextView)    this.root.findViewById(R.id.SvBox_Name);
		this._labelThirdName  = (TextView)    this.root.findViewById(R.id.SvBox_ThirdName);

		Tab.Hide(this._labelPoint);
		Tab.Hide(this._labelLastName);
		Tab.Hide(this._labelName);
		Tab.Hide(this._labelThirdName);

		// subscribe on event
		//this._engine.Clearing.Add(get_onClearing());
		//this._engine.Closing.Add(get_onClosing());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		//this.__svModel.SvChanged_EventAdd(get_onAuthSV());
		this.__pointModel = Bootstrapper.Resolve( IPointModel.class );
		this.__pointModel.set_CurrentPointChanged(get_onCurPtChanged());
	}



	//*********************************************************************************************
	//**     Event Handler
	private onCurStChng get_CurrentStateChanged() { onCurStChng o = new onCurStChng(); o.arg1 = this; return o; }
	class   onCurStChng extends RunnableWithArgs<State,Boolean> { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		State state = this.arg;

		if(state.equals(State.MODE_SELECTION))
		{
			if( ! _this._isUiDataValid)
			{
				_this.UpdateSvTextView();
				_this.UpdatePointTextView();
				_this._isUiDataValid = true;
			}
		}
	}}

	private onCurPtCh get_onCurPtChanged() { onCurPtCh o = new onCurPtCh(); o.arg1 = this; return o; }
	class   onCurPtCh extends RunnableWithArgs<Point,Boolean> { public void run()
	{
		SvBox _this = (SvBox)this.arg1;
		//Point p = this.arg;
		_this.UpdatePointTextView();
	}}

	/*private onAuthSV get_onAuthSV() { onAuthSV a = new onAuthSV(); a.arg1 = this; return a; }
	class   onAuthSV extends RunnableWithArgs<Personel,Object> { public void run()
	{
		if( this.arg != null)
		{
			SvBox _this = (SvBox)this.arg1;
			//Personel p = this.arg;

			_this.UpdateSvTextView();
			_this.UpdatePointTextView();
		}
	}}*/



	//*********************************************************************************************
	//**     private func
	private void UpdateSvTextView()
	{
		Personel p = this.__svModel.get_CurrentSuperviser();
		if(p != null)
		{
			Tab.UpdateTextView( this._labelLastName,  p.LastName);
			Tab.UpdateTextView( this._labelName,      p.FirstName);
			Tab.UpdateTextView( this._labelThirdName, p.ThirdName);
		}
	}

	private void UpdatePointTextView()
	{
		String str = null;

		Point point = this.__pointModel.get_CurrentPoint();
		if( point == null || point.Name == null ){
			str = "Выберите точку в настройках!";
		}else{
			str = point.Name;
		}

		String ctrlStr = this._labelPoint.getText().toString();
		if(ctrlStr == null || ! ctrlStr.equals(str)){
			Tab.UpdateTextView( this._labelPoint, str);
		}
	}

	//private onClearing get_onClearing() { onClearing o = new onClearing(); o.arg1 = this; return o; }
	//class onClearing extends RunnableWithArgs { public void run()
	//{
	//SvBox _this = (SvBox)this.arg1;
	//_this.HideNameBlock();
	//}}

	//private onClosing get_onClosing() { onClosing a = new onClosing(); a.arg1 = this; return a; }
	//class onClosing extends RunnableWithArgs { public void run()
	//{
	//    SvBox _this = (SvBox)this.arg1;
	//    _this.Hide();
	//}}



	//*********************************************************************************************
	//       Control Handler
	//private void CheckBoxInternet_CheckedOrUnchecked(View ctrl)
	//{
	//boolean isChecked = ((CheckBox)ctrl).isChecked();
	//this._engine.set_IsIntenetDisable(isChecked);
	//}



	//*********************************************************************************************
	//**     Code behind override
	//@Override
	//public void Show()
	//{
	//super.Show();
	//UpdateCtrlData();
	//}
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = ta.lib.operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				//case R.id.checkBoxInternet:{
				//CheckBoxInternet_CheckedOrUnchecked(ctrl);
				//break;}
			}
		}
	}
}
