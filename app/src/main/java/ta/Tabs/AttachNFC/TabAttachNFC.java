package ta.Tabs.AttachNFC;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import ta.Database.Personel;
import ta.lib.tabui.Tab;
import ta.Tabs.PersonalInfo.TabPersonalInfo;
import ta.lib.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.*;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.timeattendance.R;

public class TabAttachNFC extends Tab implements View.OnClickListener
{
	private TextView _firstName;
	private TextView _lastName;
	private TextView _thirdName;
	private TextView _nfcTagValue;

	private ISupervisorModel __svModel;
	//private Personel _currentWorker;
	private long _currentNfcTag;

	private ListView listView;
	private EditText txtSearch;
	private IPersonalService __personalService;
	private INfcEventService __nfcEventService;

	public TabAttachNFC(Context context, ViewGroup viewGroup, int i1, int i2)
	{
		super(context, viewGroup, i1, i2);

		_currentNfcTag = -1;

		_firstName = (TextView)this.root.findViewById(R.id.AttachNFC_FirstName_Id);
		_lastName = (TextView)this.root.findViewById(R.id.AttachNFC_LastName_Id);
		_thirdName = (TextView)this.root.findViewById(R.id.AttachNFC_ThirdName_Id);
		_nfcTagValue = (TextView)this.root.findViewById(R.id.AttachNFC_NfcTagValue_Id);

		View save = this.root.findViewById(R.id.AttachNFC_Save_Id);
		save.setOnClickListener(this);
		save.setTag(new Object[]{R.id.AttachNFC_Save_Id});

		View discard = this.root.findViewById(R.id.AttachNFC_Discard_Id);
		discard.setOnClickListener(this);
		discard.setTag(new Object[]{R.id.AttachNFC_Discard_Id});

		//listView        = (ListView)this.root.findViewById(R.id.PersonelList_ListView_Id);
		//txtSearch       = (EditText)this.root.findViewById(R.id.PersonelList_EditText_Id);

		//View searchBtn = this.root.findViewById(R.id.SearchButton_Id);
		//searchBtn.setOnClickListener(this);
		//searchBtn.setTag(new Object[]{R.id.SearchButton_Id});

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );

		this.__nfcEventService = Bootstrapper.Resolve( INfcEventService.class );
		this.__nfcEventService.add_NewTagReceived(get_onNfcTagApply());

		this.__personalService = Bootstrapper.Resolve(IPersonalService.class);
		this.__personalService.add_UpdateWorkerCardIdOnServerComplete(get_onUpdCardId());
	}



	//*********************************************************************************************
	//**     Event Handler
	private temp1 get_onNfcTagApply() { temp1 o = new temp1(); o.arg1 = this; return o; }
	private static class temp1 extends RunnableWithArgs<Long,Object> { public void run()
	{
		TabAttachNFC _this = (TabAttachNFC)this.arg1;
		if(_this.IsShow())
		{
			//_this.__svModel.OnNfcTagApply(this.arg);
			_this._currentNfcTag = this.arg;
			String str = Long.toHexString(_this._currentNfcTag);
			_this._nfcTagValue.setText( str);
		}
	}}
	private temp2 get_onUpdCardId() { temp2 o = new temp2(); o.arg1 = this; return o; }
	private static class temp2 extends RunnableWithArgs<String,Boolean> { public void run()
	{
		TabAttachNFC _this = (TabAttachNFC)this.arg1;
		if(_this.IsShow())
		{
			int updateResultServer = -1;
			if(this.arg != null)
			{
				String respond = this.arg;
				try {
					JSONObject jo = new JSONObject(respond);
					if(jo.has("Status")) {
						String status = jo.getString("Status");
						updateResultServer = Integer.parseInt(status);
					}
				} catch(JSONException jse){ } catch(NumberFormatException nfe){ }
			}

			if(updateResultServer == 1)
			{
				try {
				Personel w = _this.__svModel.get_SelectedWorker();
				w.CardId = String.valueOf(_this._currentNfcTag);
				w.Update();
				} catch(Exception e){
					Exception ex = e;
				}
			}

			String text = null;
			if(this.result && updateResultServer == 1)
			{
				text = "Карта сотрудник обновлена! \r\n " + Long.toHexString(_this._currentNfcTag);
			}else{
				text = "Обновление не удалось,\r\nпопробуйте позже! \r\n " + Long.toHexString(_this._currentNfcTag);
			}

			UIHelper.Instance().MessageBoxInUIThread(
				text,
				_this.get_onClickOkMessageBox(),
				null
			);
		}
	}}

	private temp3 get_onClickOkMessageBox() { temp3 o = new temp3(); o._this = this; return o; }
	private static class temp3 implements OnClickListener { public TabAttachNFC _this;public void onClick(DialogInterface di, int anonymousInt)
	{
	}}



	//*********************************************************************************************
	//**     Override
	@Override
	public void Show()
	{
		super.Show();

		//_currentWorker = Bootstrapper.Resolve( ISupervisorModel.class ).get_SelectedWorker();
		Personel currentWorker = this.__svModel.get_SelectedWorker();

		this._firstName.setText( currentWorker.FirstName);
		this._lastName.setText(  currentWorker.LastName);
		this._thirdName.setText( currentWorker.ThirdName);
		
		long   idLong = Long.parseLong(currentWorker.CardId);
		String idStr  = Long.toHexString(idLong);

		this._nfcTagValue.setText( idStr );
	}



	//*********************************************************************************************
	//**     Control Handler
	private void save()
	{
		if(this._currentNfcTag != -1)
		{
			Personel temp = new Personel();
			temp.Id = this.__svModel.get_SelectedWorker().Id;
			temp.CardId = String.valueOf(this._currentNfcTag);
			this.__personalService.UpdateWorkerCardIdOnServer(temp);
		}
	}
	private void discard()
	{
	}



	public void onClick(View ctrl)
	{
		Object tag = ctrl.getTag();
		Object [] arr = (Object[])tag;
		Integer integer = (Integer)arr[0];

		if( integer != null)
		{
			switch(integer) {
			case R.id.AttachNFC_Save_Id:{
				save();
			break;}
			case R.id.AttachNFC_Discard_Id:{
				discard();
			break;}
			}
		}
	}
}
