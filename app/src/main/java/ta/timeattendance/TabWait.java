package ta.timeattendance;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import ta.lib.*;
import ta.lib.tabui.Tab;
import ta.lib.tabui.Tab.AnimationRunner;
import ta.timeattendance.R;

public class TabWait extends Tab implements View.OnClickListener
{
	private TextView _labelMode;
	private ImageView _iconMode;
	private AnimationDrawable wavesAnim = null;

	public TabWait(Context paramContext, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(paramContext, paramViewGroup, paramInt1, paramInt2);

		ImageView imageView = (ImageView)this.root.findViewById(R.id.waves_animation);
		this.wavesAnim = ((AnimationDrawable)imageView.getDrawable());
		imageView.post(new Tab.AnimationRunner(this.wavesAnim));

		this._iconMode = ((ImageView)this.root.findViewById(R.id.iconMode));
		this._labelMode = ((TextView)this.root.findViewById(R.id.mode));

		Button back_button = (Button)this.root.findViewById(R.id.PageWait_back_button);
		back_button.setOnClickListener(this);
		back_button.setTag(R.id.PageWait_back_button );

		Button select_button = (Button)this.root.findViewById(R.id.PageWait_select_button);
		select_button.setOnClickListener(this);
		select_button.setTag(R.id.PageWait_select_button);
	}

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

	public void onClick_back_button()
	{
		UIHelper.Instance().switchState(MainActivity.State.MODE_SELECTION);
	}

	public void onClick_select_button()
	{
		UIHelper.Instance().switchState(MainActivity.State.PERSONEL_LIST_MODE);
	}

	public void onClick(View paramView)
	{
		Object tag = paramView.getTag();

		Integer integer = operator.as(Integer.class, tag);

		if( integer != null)
		{
		    switch(integer)
		    {
		        case R.id.PageWait_back_button:{
		            onClick_back_button();
		        break;}
		        case R.id.PageWait_select_button:{
		            onClick_select_button();
		        break;}
		    }
		}
	}
}