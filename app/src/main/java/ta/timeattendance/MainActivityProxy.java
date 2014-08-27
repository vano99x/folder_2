package ta.timeattendance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.BroadcastReceiver;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import ta.lib.*;
import ta.lib.tabui.*;
import ta.Database.*;
import ta.timeattendance.Models.*;

public class MainActivityProxy extends FragmentActivity
{
	// static
	public static MainActivity ma;
	private static int _count;
	// private
	private String ActivityState;
	private boolean _isOld;
	//public
	public int _index;
	public String _threadMarker;
	public boolean isNfcBusy;

	public MainActivityProxy()
	{
		this._index = MainActivityProxy._count;
		MainActivityProxy._count++;
		this.ActivityState = "Destroy";
		this._threadMarker = null;
		this.isNfcBusy = false;
		this._isOld = false;
	}

	private void Begin()
	{
		//Thread t = Thread.currentThread();
		//t.
		this._threadMarker = Thread.currentThread().toString();

		if( MainActivityProxy.ma == null || MainActivityProxy.ma.get_Index() < this._index )
		{
			if( MainActivityProxy.ma != null )
			{
				try{
					MainActivityProxy oldActivity = MainActivityProxy.ma.get_FragmentActivity();
					MainActivityProxy.ma.MainActivity_Clear();
					oldActivity.finish();
					MainActivityProxy.ma = null;
				}catch(Exception e){
					Exception ex = e;
				}
			}
			MainActivityProxy.ma = new MainActivity(this);
			MainActivityProxy.ma.Start(_threadMarker);
		}
		else if( MainActivityProxy.ma.get_Index() == this._index )
		{
			MainActivityProxy.ma.Start(_threadMarker);
		}
	}

	/*@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if( this._isOld == true )
		{
			this.moveTaskToBack(true);
		}
	}*/

	//****  1  ************************************************************************************
	@Override
	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		if( MainActivityProxy.ma != null && this._index < MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		this.Begin();
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if( MainActivityProxy.ma != null && this._index < MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		if(MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == this._index)
		{
			int aaa = 9;
			if(ActivityState == "Stop")
			{
				ActivityState = "Destroy";
				MainActivityProxy.ma.MainActivity_Clear();
				MainActivityProxy.ma = null;
			}
		}
	}

	//****  2  ************************************************************************************
	@Override
	protected void onStart()
	{
		super.onStart();
		if( MainActivityProxy.ma != null && this._index < MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		if(MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == this._index)
		{
			int aaa = 9;
			int aaa2 = aaa - 2;
		}
		this.Begin();
	}
	@Override
	protected void onStop()
	{
		super.onStop();
		if( MainActivityProxy.ma != null && this._index < MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		if(MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == this._index)
		{
			int aaa = 9;
			if(ActivityState == "Pause")
			{
				ActivityState = "Stop";
				MainActivityProxy.ma.Stop();
			}
		}
	}

	//****  3  ************************************************************************************
	@Override
	protected void onResume()
	{
		super.onResume();
		if( MainActivityProxy.ma != null && this._index <  MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		if( MainActivityProxy.ma != null && this._index == MainActivityProxy.ma.get_Index() ) {
			int aaa = 9;
			int aaa2 = aaa - 2;
			ActivityState = "Resume";
			MainActivityProxy.ma.Resume();
		}
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		if( MainActivityProxy.ma != null && this._index < MainActivityProxy.ma.get_Index() ) {
			this._isOld = true;
		}
		if( MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == this._index)
		{
			int aaa = 9;
			int aaa2 = aaa - 2;
			ActivityState = "Pause";
			MainActivityProxy.ma.Pause();
		}
	}




	
	public void NfcNotBusy()
	{
		this.isNfcBusy = false;
	}
	//*********************************************************************************************
	@Override
	protected void onNewIntent(Intent paramIntent)
	{
		//TALog.Log("intent = " + paramIntent);
		int flag = paramIntent.getFlags();
		int mask = 0x100000;
		boolean res = (mask & flag) == mask;
		//if ((0x100000 & paramIntent.getFlags()) == 1048576)
		if( res ) {
			//MainActivityProxy.ma.set_TagFromIntent( null );
			//TALog.Log("fromHistory");
		} else {
			if(
				(
					UIHelper.Instance().currentState == MainActivity.State.WAIT_MODE ||
					UIHelper.Instance().currentState == MainActivity.State.PERSONEL_INFO
				)
				&& !this.isNfcBusy )
			{
				this.isNfcBusy = true;
				MainActivityProxy.ma.set_TagFromIntent(
				((android.nfc.Tag)paramIntent.getParcelableExtra(android.nfc.NfcAdapter.EXTRA_TAG))
				);
			}

		}
	}
	@Override
	public void onBackPressed()
	{
		UIHelper.Instance().onBackPressed();
	}
	@Override
	public void finish()
	{
		super.finish();
		UIHelper.Instance().currentState = MainActivity.State.PIN;
	}
	/*@Override
	public void onWindowFocusChanged (boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			if(MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == this._index)
			{
				
				MainActivityProxy.ma.GotFocus();
			}
		}
	}*/



	private FragmentManager _fragmentManager;
	public FragmentManager get_FragmentManager()
	{
		if(_fragmentManager == null)
		{
			_fragmentManager = this.getSupportFragmentManager();
		}
		return _fragmentManager;
	}



	//*********************************************************************************************
	//*      Event Handler
	public onClickBackBtn get_onClickBackBtn() { onClickBackBtn o = new onClickBackBtn(); o._this = this; return o; } 
	class onClickBackBtn implements DialogInterface.OnClickListener { public MainActivityProxy _this;public void onClick( 
		DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
	{
		if( MainActivityProxy.ma != null && MainActivityProxy.ma.get_Index() == _this._index)
		{
			MainActivityProxy.ma.MainActivity_Logout();
		}

		try{
			_this.finish();
		}catch(Exception e ){
			Exception ex = e;
		}
	}}



	static
	{
		MainActivityProxy._count = 0;
		MainActivityProxy.ma = null;
	}
}