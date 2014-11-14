package ta.Tabs.PersonalInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ta.Database.Personel;
import ta.lib.tabui.Tab;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import ta.lib.*;
import ta.timeattendance.*;
import ta.timeattendance.MainActivity.State;
import ta.timeattendance.Models.*;
import ta.timeattendance.Services.*;
import ta.Tabs.CheckinList.*;
import ta.timeattendance.R;

public class TabPersonalInfo extends Tab implements View.OnClickListener
{
	private LinearLayout _layoutRoot;
	private View _dismissImageView;

	private ImageView _iconMode;
	private TextView  _labelMode;
	private TextView  _labelLastName;
	private TextView  _labelName;
	private TextView  _labelThirdName;
	private ImageView _photoImageView;

	private TextView  _hex_number_card;

	private Button _checkin_btn;
	private LinearLayout _block_exit_tracked;
	public boolean IsShowCheckiedWorker;
	public boolean __isResized;

	//private IAppService      __appService;
	private ISupervisorModel __svModel;
	private IChekinService   __chekinService;

	public TabPersonalInfo(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);
		this.IsShowCheckiedWorker = false;
		this.__isResized = false;

		this._layoutRoot = (LinearLayout)this.root.findViewById(R.id.PagePersonelInfo_LayoutRoot);
		this._dismissImageView  = this.root.findViewById(R.id.PagePersonelInfo_DismissImageView_Id);
		this._dismissImageView.setOnClickListener(this);
		this._dismissImageView.setTag(R.id.PagePersonelInfo_DismissImageView_Id);
		Tab.Hide(this._dismissImageView);

		_iconMode =       (ImageView)this.root.findViewById(R.id.iconMode);
		_labelMode =      (TextView)this.root.findViewById(R.id.mode);

		_labelLastName   = (TextView)this.root.findViewById(R.id.last_name);
		_labelName       = (TextView)this.root.findViewById(R.id.name);
		_labelThirdName  = (TextView)this.root.findViewById(R.id.third_name);
		_photoImageView  = (ImageView)this.root.findViewById(R.id.PagePersonelInfo_PhotoImageView_Id);
		_hex_number_card = (TextView)this.root.findViewById(R.id.hex_number_card);
		Tab.UpdateTextView( _hex_number_card, null);

		_checkin_btn = (Button)this.root.findViewById(R.id.checkin_btn);
		_checkin_btn.setOnClickListener(this);
		_checkin_btn.setTag(R.id.checkin_btn);

		_block_exit_tracked = (LinearLayout)this.root.findViewById(R.id.block_exit_tracked);

		//this.__appService = Bootstrapper.Resolve( IAppService.class );
		//this.__appService.get_GotFocus().Add(get_onGotFocus());

		this.__svModel = Bootstrapper.Resolve( ISupervisorModel.class );
		this.__chekinService = Bootstrapper.Resolve( IChekinService.class );
		this.__chekinService.add_SaveCheckinComplete(get_onSaveCheckin());

		//this.vto = this.root.getViewTreeObserver();
		//this.vto = this._photoImageView.getViewTreeObserver();
		//this.onGlobalLayoutListener = get_onGlobalLayoutListener();
		//vto.addOnGlobalLayoutListener(this.onGlobalLayoutListener);
	}



	//*********************************************************************************************
	//**     Event Handler
	//private RunnableWithArgs get_onSaveCheckin() { return new RunnableWithArgs<Object,Boolean>(this){ public void run()
	//{
	//}};}
	private temp1 get_onSaveCheckin() { temp1 o = new temp1(); o.arg1 = this; return o; }
	private static class temp1 extends RunnableWithArgs<Object,Boolean> { public void run()
	{
		TabPersonalInfo _this = (TabPersonalInfo)this.arg1;
		if(_this.IsShow())
		{
			//TabPersonalInfo pi = ((TabPersonalInfo)UIHelper.Instance().get_TabConteiner().GetByEnum(State.PERSONEL_INFO).Tab);
			//pi.IsShowCheckiedWorker = true;
			_this.UpdateData();
		}
	}}

	//private       onGF get_onGotFocus() { onGF o = new onGF(); o.arg1 = this; return o; }
	//private class onGF extends RunnableWithArgs<Object,Object> { public void run()
	//{
	//}}
	private ViewTreeObserver vto;
	private onGll onGlobalLayoutListener;
	private       onGll get_onGlobalLayoutListener() { onGll o = new onGll(); o.arg1 = this; return o; }
	private class onGll implements OnGlobalLayoutListener { Object arg1; public void onGlobalLayout()
	{
		TabPersonalInfo _this = (TabPersonalInfo)this.arg1;
		if( ! _this.__isResized){
			_this.ResizeFoto();
		}

		// detach
		//if(_this.vto != null && _this.vto.isAlive()){
		//	_this.vto.removeOnGlobalLayoutListener(_this.onGlobalLayoutListener);
		//}
	}}



	@Override
	public void Show()
	{
		super.Show();
		UpdateData();
		this.__chekinService.SendCheckin();

		if(this.vto == null || !(this.vto.isAlive()))
		{
			this.vto = this._photoImageView.getViewTreeObserver();
			this.onGlobalLayoutListener = get_onGlobalLayoutListener();
			vto.addOnGlobalLayoutListener(this.onGlobalLayoutListener);
		}
	}



	//*********************************************************************************************
	//**     private func
	private void ResizeFoto()
	{
		int parentHeight = this._layoutRoot.getHeight();
		if(parentHeight == 0) {
			return;
		}

		int count = this._layoutRoot.getChildCount();
		int index = count - 1;
		View lastElement = this._layoutRoot.getChildAt(index);
		if(lastElement == null) {
			return;
		}
		
		String str = (String)lastElement.getTag();
		if(str == null || !(str.equals("LastElement")) ) {
			return;
		}

		int y2 = lastElement.getBottom();
		if(y2 == 0) {
			return;
		}

		int freeHeight = parentHeight - y2;
		int svBoxHeight = UIHelper.Instance().svBox.getRoot().getHeight();
		freeHeight = freeHeight - svBoxHeight;

		int fotoHeight = this._photoImageView.getHeight();
		LayoutParams param = this._photoImageView.getLayoutParams();
		param.height = fotoHeight+freeHeight;
		this._photoImageView.setLayoutParams(param);

		this.__isResized = true;
	}

	public void UpdateData()
	{
		MainEngine engine = MainEngine.getInstance();
		//Personel p = engine.get_CurrentWorker();
		Personel p;

		if(IsShowCheckiedWorker){
			p = this.__svModel.get_WorkerAppliedNFC();
		}else{
			p = this.__svModel.get_SelectedWorker();
		}

		if( p == null || p.Id == -1 ) {
			return;
		}

		this._labelLastName.setText(p.LastName);
		this._labelName.setText(p.FirstName);
		this._labelThirdName.setText(p.ThirdName);

		if(p.CardId != null && !(p.CardId.equals("0")))
		{
			long idLong = Long.parseLong(p.CardId);
			String str = Long.toHexString(idLong);
			Tab.UpdateTextView( _hex_number_card, str);
		}else{
			Tab.UpdateTextView( _hex_number_card, null);
		}

		p.loadCachedPhoto(this.context);

		if( p.Photo != null )
		{
			//Bitmap bitmap = BitmapFactory.decodeByteArray( p.Photo, 0, p.Photo.length );
			//this._photoImageView.setImageBitmap(bitmap);
			Bitmap b = BitmapFactory.decodeByteArray(p.Photo, 0, p.Photo.length);
			float origWidth = b.getWidth();
			float origHeight = b.getHeight();
			float scale = origWidth / origHeight;
			float newHeight = 135 / scale;
			this._photoImageView.setImageBitmap(Bitmap.createScaledBitmap(b, 135, (int)newHeight, false));
		}

		if (engine.getCurrentMode() == Mode.Check)
		{
				this._iconMode.setImageResource(R.drawable.check);
				this._labelMode.setText(R.string.mode_check_result);
				//___old___//UIHelper.Instance().ShowScreenFromBackground(MainActivity.State.MODE_SELECTION, TIME_OUT);
		}
		else if (engine.getCurrentMode() == Mode.StartWork)
		{
				this._iconMode.setImageResource(R.drawable.start);
				this._labelMode.setText(R.string.mode_start_result);
		}
		else if (engine.getCurrentMode() == Mode.EndWork)
		{
				this._iconMode.setImageResource(R.drawable.finish);
				this._labelMode.setText(R.string.mode_end_result);
		}
		else if (engine.getCurrentMode() == Mode.Pause)
		{
				this._iconMode.setImageResource(R.drawable.pause2);
				this._labelMode.setText(R.string.mode_pause);
		}
		//___old___//localMainEngine.showScreen(MainActivity.State.WAIT_MODE, TIME_OUT);

		if(this.IsShowCheckiedWorker) {
			this.__isResized = false;
			Tab.Hide(this._checkin_btn);
			Tab.Show(this._block_exit_tracked);
		} else {
			this.__isResized = false;
			Tab.Show(this._checkin_btn);
			Tab.Hide(this._block_exit_tracked);
		}

		if(p.IsDismiss)
		{
			if(!Tab.IsShow(this._dismissImageView))
				Tab.Show(this._dismissImageView);
		}
		else if(Tab.IsShow(this._dismissImageView)){
			Tab.Hide(this._dismissImageView);
		}

		//ResizeFoto();
		//setImageResource(R.drawable.no_photo);
	}



	////*********************************************************************************************
	////       Event Handler
	//private onSaveCheckinCompleteEventHandler get_onSaveCheckinCompleteEventHandler()
	//{
	//    onSaveCheckinCompleteEventHandler a = new onSaveCheckinCompleteEventHandler();
	//    return a;
	//}
	//class onSaveCheckinCompleteEventHandler extends RunnableWithArgs { public void run()
	//{
	//    Object[] resultArr = (Object[])this.result;
	//    boolean result = ((Boolean)resultArr[0]).booleanValue();
	//    if(result)
	//    {
	//        UIHelper.Instance().switchState(MainActivity.State.WAIT_MODE);
	//    }
	//}}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();
		Integer integer = operator.as(Integer.class, tag);
		
		MainEngine engine = MainEngine.getInstance();

		if( engine != null && integer != null)
		{
			switch(integer)
			{
				case R.id.PagePersonelInfo_DismissImageView_Id:{
					int aaa = 9;
					int aaa2 = aaa-9;
				break;}
				case R.id.checkin_btn:{
					//engine.SaveCheckin();
					this.__svModel.SaveWorkerCheckin(   this.__svModel.get_SelectedWorker()   );
				break;}
			}
		}
	}
}