package ta.timeattendance;

import android.content.Context;
import android.view.ViewGroup;

import android.webkit.WebView;

import java.io.IOException;
import android.util.Base64;
import android.content.res.Resources;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import ta.lib.tabui.Tab;
import ta.timeattendance.R;

public class TabReference extends Tab
{
	private WebView _textView;
	private String  _html;

	public TabReference(Context context, ViewGroup paramViewGroup, int paramInt1, int paramInt2)
	{
		super(context, paramViewGroup, paramInt1, paramInt2);
		this._html = null;

		//int articleResId = context.getResources().getIdentifier(R.string.Reference_Text_Id, "string", context.getPackageName());

		_textView = (WebView)this.root.findViewById(R.id.Reference_TextView_Id);
	}

	private String get_Html()
	{
		if(this._html == null)
		{
			try
			{
				this._html = TabReference.ReadByteString(this.context, R.raw.ref_text___utf8_2);
			}
			catch(Exception e)
			{
				Exception ex = e;
			}
		}
		return this._html;
	}

	public void Show()
	{
		super.Show();

		//CharSequence str = _textView.getText();
		//if(str.length() == 0)
		//{
			//String text = context.getString(R.string.Reference_Text_Id3);
			//android.text.Spanned spannedText = android.text.Html.fromHtml(text);
			//_textView.setText(spannedText);
			_textView.loadData(get_Html(), "text/html", "base64");
		//}
	}



	private static String ReadByteString(Context context, int id) throws IOException
	{
		byte [] bytes  = null;
		String  result = null;

		//1
		InputStream           stream = context.getResources().openRawResource(id);
		ByteArrayOutputStream baos   = new ByteArrayOutputStream();

		//2
		//java.io.InputStreamReader isr = new java.io.InputStreamReader(context.getResources().openRawResource(id), "windows-1251");
		//java.io.BufferedReader    br  = new java.io.BufferedReader(isr);
		//java.lang.StringBuilder   sb  = new java.lang.StringBuilder();

		try
		{
			//1
			byte[] buffer = new byte[512];
			int readBytes = 0;
			while( (readBytes = stream.read(buffer)) != -1)
			{
			baos.write(buffer, 0, readBytes);
			}
			bytes = baos.toByteArray();
			if(bytes != null)
			{
			result = Base64.encodeToString(bytes, Base64.DEFAULT);
			}

			//2
			//String str=null;
			//while(   (str = br.readLine()) !=null   )
			//{
			//sb.append(str);
			//}
			//result = sb.toString();
		}
		finally
		{
			//1
			baos.close();
			stream.close();
		}

		return result;
	}
}