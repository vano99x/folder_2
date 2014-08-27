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

import ta.lib.*;
import ta.lib.tabui.Tab;
import ta.timeattendance.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.R;

public class PanelButton extends Tab implements View.OnClickListener
{
	private MainEngine _engine;
	private UIHelper   _uiHelper;
	//private Button _SyncBtn;
	//private Button _CheckinListBtn;
	//private Button _SettingsBtn;
	//private Button _FacilityInfoBtn;

	public PanelButton(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2, UIHelper uiHelper)
	{
		super( mainActivity, rootView, paramInt1, paramInt2);
		this._engine   = MainEngine.getInstance();
		this._uiHelper = uiHelper;
		this._uiHelper.set_CurrentStateChanged(get_CurrentStateChanged());

		LinearLayout _SyncBtn =    (LinearLayout)this.root.findViewById(R.id.PnBtn_SyncBtn_Id);
		//ImageView iv =    (ImageView)this.root.findViewById(R.id.PnBtn_SyncImg_Id);
		//try{
		//iv.setImageResource(R.drawable._2);
		//}catch(Exception e){
		//Exception ex = e;
		//}

		LinearLayout _CheckinListBtn =  (LinearLayout)this.root.findViewById(R.id.PnBtn_CheckinListBtn_Id);
		LinearLayout _SettingsBtn =     (LinearLayout)this.root.findViewById(R.id.PnBtn_SettingsBtn_Id);
		LinearLayout _FacilityInfoBtn = (LinearLayout)this.root.findViewById(R.id.PnBtn_FacilityInfoBtn_Id);
		LinearLayout _ReferenceBtn    = (LinearLayout)this.root.findViewById(R.id.PnBtn_ReferenceBtn_Id);

		_SyncBtn.setOnClickListener(this);
		_CheckinListBtn.setOnClickListener(this);
		_SettingsBtn.setOnClickListener(this);
		_FacilityInfoBtn.setOnClickListener(this);
		_ReferenceBtn.setOnClickListener(this);

		_SyncBtn.setTag(        R.id.PnBtn_SyncBtn_Id);
		_CheckinListBtn.setTag( R.id.PnBtn_CheckinListBtn_Id);
		_SettingsBtn.setTag(    R.id.PnBtn_SettingsBtn_Id);
		_FacilityInfoBtn.setTag(R.id.PnBtn_FacilityInfoBtn_Id);
		_ReferenceBtn.setTag(   R.id.PnBtn_ReferenceBtn_Id);

		//this.Hide();
		int w = 62;
		this.ScaleBtn( _SyncBtn, w);
		this.ScaleBtn( _CheckinListBtn, w);
		this.ScaleBtn( _SettingsBtn, w);
		this.ScaleBtn( _FacilityInfoBtn, w);
		this.ScaleBtn( _ReferenceBtn, w);
	}



	void ScaleBtn(ViewGroup v, int targetSize)
	{
		ImageView iv = (ImageView)v.getChildAt(0);
		Drawable drawing = iv.getDrawable();
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
		param.width =  newWidth;
		param.height = newHeight;
		//iv.setLayoutParams(param);
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
	//       Control Handler

	public void onClick_sync()
	{
		this._engine.Sync();
	}

	public void onClick_list()
	{
		UIHelper.Instance().switchState(MainActivity.State.CHECKIN_LIST);
	}

	public void onClick_Settings()
	{
		UIHelper.Instance().switchState(MainActivity.State.FLAG_SETTINGS);
	}

	public void onClick_FacilityInfoBtn()
	{
		UIHelper.Instance().switchState(MainActivity.State.FACILITY_INFO);
	}

	public void onClick_ReferenceBtn()
	{
		UIHelper.Instance().switchState(MainActivity.State.REFERENCE);
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
					onClick_sync();
				break;}
				case R.id.PnBtn_CheckinListBtn_Id:{
					onClick_list();
				break;}
				case R.id.PnBtn_SettingsBtn_Id:{
					onClick_Settings();
				break;}
				case R.id.PnBtn_FacilityInfoBtn_Id:{
					onClick_FacilityInfoBtn();
				break;}
				case R.id.PnBtn_ReferenceBtn_Id:{
					onClick_ReferenceBtn();
				break;}
			}
		}
	}
}