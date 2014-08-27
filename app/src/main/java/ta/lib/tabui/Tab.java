package ta.lib.tabui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

public abstract class Tab
{
	protected Context context; // main activity
	//protected TabActionListener listener;
	protected ViewGroup parent;    // root of Tab
	protected View      tabView; // attached view
	protected ViewGroup root;    // root of Tab

		//rootView - view of main activity
		//paramInt1 - view
		//paramInt2 - id in attached view
	public Tab(Context mainActivity, ViewGroup rootView, int paramInt1, int paramInt2)
	{
		this.context = mainActivity;
		this.parent  = rootView;
		
		//try{
			this.tabView = LayoutInflater.from(mainActivity).inflate( paramInt1, this.parent, true);
		//}catch(Exception e){
		//int aaa = 9;
		//int aaa2 = aaa-2; Exception ex = e;
		//}
		
		this.root    = ((ViewGroup)rootView.findViewById(paramInt2));
	}
	public void Clear()
	{
		this.Hide();
		//this.parent.removeView(this.tabView);
		this.root    = null;
		this.tabView = null;
		this.parent  = null;
		this.context = null;
	}

	public void Show()
	{
		this.root.setVisibility(View.VISIBLE);
	}

	public void Hide()
	{
		this.root.setVisibility(View.GONE);
	}

	public ViewGroup getRoot()
	{
		return this.root;
	}
	public ViewGroup getParent()
	{
		return this.parent;
	}



	//*********************************************************************************************
	//*      public static
	public static void Show(View ctrl)
	{
		if( ! Tab.IsShow(ctrl)) {
			ctrl.setVisibility(View.VISIBLE);
		}
	}
	public static void Hide(View ctrl)
	{
		if(Tab.IsShow(ctrl)) {
			ctrl.setVisibility(View.GONE);
			int aaa = ctrl.getVisibility();
			int aaa2 = aaa-2;
		}
	}
	public static boolean IsShow(View ctrl)
	{
		int aaa = ctrl.getVisibility();
		boolean bbb = aaa == View.VISIBLE;
		return bbb;
	}

	public static void UpdateTextView(TextView ctrl, String text)
	{
		if(ctrl == null)
			return;

		boolean isShow = false;
		if(text != null)
		{
			isShow = !(text.isEmpty());
		}

		if(isShow)
		{
			if( ! Tab.IsShow(ctrl)) {
				Tab.Show(ctrl);
			}
			ctrl.setText(text);
		}
		else
		{
			if(Tab.IsShow(ctrl)) {
				Tab.Hide(ctrl);
			}
		}
	}

	//public void setListener(TabActionListener paramTabActionListener)
	//{
	//    this.listener = paramTabActionListener;
	//}

	//public void showDialog(
	//    String paramString1, 
	//    String paramString2, 
	//    DialogInterface.OnClickListener paramOnClickListener)
	//{
	//    AlertDialog.Builder b = new AlertDialog.Builder(this.context);
	//    b.setMessage(paramString1);
	//    b.setTitle(paramString2);
	//    b.setCancelable(true);
	//    b.setNeutralButton("Ok", paramOnClickListener);
	//    b.create().show();
	//}

	public class AnimationRunner implements Runnable
	{
		AnimationDrawable animation = null;

		public AnimationRunner(AnimationDrawable arg2)
		{
			this.animation = arg2;
		}

		public void run()
		{
			if (this.animation != null)
			{
				this.animation.start();
			}
		}
	}
}