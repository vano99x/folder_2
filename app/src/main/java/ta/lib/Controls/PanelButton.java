package ta.lib.Controls;

import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;

import ta.Tabs.SessionList.SessionMode;
import ta.lib.*;
import ta.lib.tabui.Tab;
import ta.timeattendance.*;
import ta.timeattendance.MainActivity.State;
import ta.Tabs.SessionList.ISessionModel;
import ta.timeattendance.R;

public class PanelButton extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	private UIHelper   _uiHelper;
	private ISessionModel   __sessionModel;

	public PanelButton(Context mainActivity, ViewGroup rootView, int i1, int i2, UIHelper uiHelper)
	{
		super( mainActivity, rootView, i1, i2, 0);

		//this.Hide();

		this._engine   = MainEngine.getInstance();
		this._uiHelper = uiHelper;
		this._uiHelper.set_CurrentStateChanged(get_CurrentStateChanged());

		this.__sessionModel = ta.timeattendance.Models.Bootstrapper.Resolve( ISessionModel.class );

		//ImageView iv =    (ImageView)this.root.findViewById(R.id.PnBtn_SyncImg_Id);
		//try{
		//iv.setImageResource(R.drawable._2);
		//}catch(Exception e){
		//Exception ex = e;
		//}

		LinearLayout _SyncBtn =    (LinearLayout)this.root.findViewById(R.id.PnBtn_SyncBtn_Id);
		LinearLayout _CheckinListBtn =  (LinearLayout)this.root.findViewById(R.id.PnBtn_CheckinListBtn_Id);
		LinearLayout _SettingsBtn =     (LinearLayout)this.root.findViewById(R.id.PnBtn_SettingsBtn_Id);
		LinearLayout _FacilityInfoBtn = (LinearLayout)this.root.findViewById(R.id.PnBtn_FacilityInfoBtn_Id);
		LinearLayout _ReferenceBtn    = (LinearLayout)this.root.findViewById(R.id.PnBtn_ReferenceBtn_Id);

		LinearLayout ArrivalOk =          (LinearLayout)this.root.findViewById(R.id.PnBtn_ArrivalOk_Id);
		LinearLayout DepartureOk =        (LinearLayout)this.root.findViewById(R.id.PnBtn_DepartureOk_Id);
		LinearLayout DepartureMiss    =   (LinearLayout)this.root.findViewById(R.id.PnBtn_DepartureMiss_Id);

		_SyncBtn.setOnClickListener(this);
		_CheckinListBtn.setOnClickListener(this);
		_SettingsBtn.setOnClickListener(this);
		_FacilityInfoBtn.setOnClickListener(this);
		_ReferenceBtn.setOnClickListener(this);

		ArrivalOk.setOnClickListener(this);
		DepartureOk.setOnClickListener(this);
		DepartureMiss.setOnClickListener(this);

		_SyncBtn.setTag(        R.id.PnBtn_SyncBtn_Id);
		_CheckinListBtn.setTag( R.id.PnBtn_CheckinListBtn_Id);
		_SettingsBtn.setTag(    R.id.PnBtn_SettingsBtn_Id);
		_FacilityInfoBtn.setTag(R.id.PnBtn_FacilityInfoBtn_Id);
		_ReferenceBtn.setTag(   R.id.PnBtn_ReferenceBtn_Id);

		ArrivalOk.setTag(       R.id.PnBtn_ArrivalOk_Id);
		DepartureOk.setTag(     R.id.PnBtn_DepartureOk_Id);
		DepartureMiss.setTag(   R.id.PnBtn_DepartureMiss_Id);

		//this.Hide();

		int w = 62;
		this.ScaleBtn( _SyncBtn, w);
		this.ScaleBtn( _CheckinListBtn, w);
		this.ScaleBtn( _SettingsBtn, w);
		this.ScaleBtn( _FacilityInfoBtn, w);
		this.ScaleBtn( _ReferenceBtn, w);/**/
		w = 25;
		this.ScaleBtn( ArrivalOk, w, 2);
		this.ScaleBtn( DepartureOk, w, 2);
		this.ScaleBtn( DepartureMiss, w, 2);
	}



	void ScaleBtn(ViewGroup v, int targetSize){
		ScaleBtn(v, targetSize, 1);
	}
	void ScaleBtn(ViewGroup v, int targetSize, int widthFactor)
	{
		ImageView iv = (ImageView)v.getChildAt(0);
		Drawable drawing = iv.getDrawable();
		if(drawing == null)
		{
			drawing = iv.getBackground();
			if(drawing == null)
				return;
		}
		Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();
		float width  = (float)bitmap.getWidth();
		float height = (float)bitmap.getHeight();

		float xScale = targetSize / width;
		float yScale = targetSize / height;

		float scale = 0;
		//scale = ((xScale <= yScale) ? xScale : yScale);
		if(xScale < yScale)
			scale = xScale;
		else
			scale = yScale;

		int newWidth  = (int)(width  * scale);
		int newHeight = (int)(height * scale);

		//Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
		////BitmapDrawable result = new BitmapDrawable(this.context.getResources(),scaledBitmap);
		////BitmapDrawable result = new BitmapDrawable(scaledBitmap);
		//iv.setImageBitmap(scaledBitmap);
		////iv.setImageDrawable(result);
		////newWidth = scaledBitmap.getWidth();
		////newHeight = scaledBitmap.getHeight();

		LayoutParams param = iv.getLayoutParams();
		param.width =  newWidth * widthFactor;
		param.height = newHeight;
		param = null;
		//iv.setLayoutParams(param);
		//param = v.getLayoutParams();
		//param.width = targetSize;
		//param.height = targetSize;
	}



	//*********************************************************************************************
	//**     Event Handler
	private onCurStChng get_CurrentStateChanged() { onCurStChng o = new onCurStChng(); o.arg1 = this; return o; }
	class   onCurStChng extends RunnableWithArgs<State,Boolean> { public void run()
	{
		PanelButton _this = (PanelButton)this.arg1;
		State state = this.arg;

		if(!state.equals(State.PIN))
		{
			if(_this.getRoot().getVisibility() == View.GONE)
			{
				_this.Show();
			}
		}
		else
		{
			if(_this.getRoot().getVisibility() == View.VISIBLE)
			{
				_this.Hide();
			}
		}

		//_this._labelPoint.setText( p.Name);
	}}



	//*********************************************************************************************
	//**     Control Handler

	public void sync_Click() {
		this._engine.Sync();
	}

	public void list_Click() {
		UIHelper.Instance().switchState(State.CHECKIN_LIST);
	}

	public void Settings_Click() {
		UIHelper.Instance().switchState(State.FLAG_SETTINGS);
	}

	public void FacilityInfoBtn_Click() {
		UIHelper.Instance().switchState(State.FACILITY_INFO);
	}

	public void ReferenceBtn_Click() {
		UIHelper.Instance().switchState(State.REFERENCE);
	}


	public void ArrivalOk_Click() {
		this.__sessionModel.set_SessionMode(SessionMode.ArrivalOk);
		UIHelper.Instance().switchState(State.SESSION_FLAG);
	}
	public void DepartureOk_Click() {
		this.__sessionModel.set_SessionMode(SessionMode.DepartureOk);
		UIHelper.Instance().switchState(State.SESSION_FLAG);
	}
	public void DepartureMiss_Click() {
		this.__sessionModel.set_SessionMode(SessionMode.DepartureMiss);
		UIHelper.Instance().switchState(State.SESSION_FLAG);
	}



	//*********************************************************************************************
	//**     Code behind override
	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Integer integer = ta.lib.operator.as(Integer.class, tag);

		if( integer != null)
		{
			switch(integer)
			{
				case R.id.PnBtn_SyncBtn_Id:{
					sync_Click();
				break;}
				case R.id.PnBtn_CheckinListBtn_Id:{
					list_Click();
				break;}
				case R.id.PnBtn_SettingsBtn_Id:{
					Settings_Click();
				break;}
				case R.id.PnBtn_FacilityInfoBtn_Id:{
					FacilityInfoBtn_Click();
				break;}
				case R.id.PnBtn_ReferenceBtn_Id:{
					ReferenceBtn_Click();
				break;}

				case R.id.PnBtn_ArrivalOk_Id:{
					ArrivalOk_Click();
				break;}
				case R.id.PnBtn_DepartureOk_Id:{
					DepartureOk_Click();
				break;}
				case R.id.PnBtn_DepartureMiss_Id:{
					DepartureMiss_Click();
				break;}
			}
		}
	}
}