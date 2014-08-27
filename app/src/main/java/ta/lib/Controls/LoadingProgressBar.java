package ta.lib.Controls;

//import android.app.*;
import android.content.Context;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.support.v4.app.FragmentManager;
import java.util.PriorityQueue;

import ta.lib.*;
import ta.timeattendance.R;

public class LoadingProgressBar extends DialogFragment implements IMessageReceiver
{
	private FragmentManager _fragmentManager;
	private Context _context;
	private TextView _title;
	private ListView _listView;
	//private PriorityQueue<MessageItem> queueMessage;

	public LoadingProgressBar()
	{
	}
	public void Init(Context context, FragmentManager fragmentManager)
	{
		this._context = context;
		this._fragmentManager = fragmentManager;
	}
    public View onCreateView(
			LayoutInflater inflater, 
			ViewGroup container,
            Bundle savedInstanceState
		)
	{
        this.getDialog().getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE); // !!!

        View view = inflater.inflate(R.layout.ctrl_loading_progress_bar, container);
		_title    = (TextView) view.findViewById(R.id.LoadingProgressBar_Title);
		_listView = (ListView) view.findViewById(R.id.LoadingProgressBar_ListView);
		//int height = _listView.getDividerHeight();
		_listView.setDividerHeight(0);

		//android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(
		//this._context, android.R.layout.simple_list_item_multiple_choice, new String[]{"aaa","bbb"});
		_listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		LoadingProgressBarAdapter adapter = get_Adapter();
		_listView.setAdapter(adapter);

        return view;
    }
	
	//*********************************************************************************************
	//**     Property
	public LoadingProgressBarAdapter _adapter;
	public LoadingProgressBarAdapter get_Adapter()
	{
		if(this._adapter == null)
		{
			this._adapter = new LoadingProgressBarAdapter(this._context, null);//new String[]{"aaa","bbb","aaa2","bbb2","aaa3","bbb3"}
		}
		return this._adapter;
	}

	public void SetTitle(String str)
	{
		if(this._title != null)
		{
			this._title.setText(str);
		}
	}
	public void AddMessage(String str)
	{
		if(this._listView != null)
		{
			this.get_Adapter().get_Items().add(str);
			this.get_Adapter().notifyDataSetChanged();
			this._listView.setSelection(this.get_Adapter().getCount() - 1);
		}
	}

	public void Show()
	{
		this.show( this._fragmentManager, "fragment_edit_name");
	}
	public void Hide()
	{
		this.get_Adapter().get_Items().clear();
		this.get_Adapter().notifyDataSetChanged();
		this.dismiss();
	}

	//private class MessageItem
	//{
	//    public String str;
	//}
}