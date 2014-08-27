package ta.lib;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

public final class TALog
{

	private static final String APP_LOG = "T&A";
	private static final String fileName = "MarkitCartLog.txt";
	private static boolean mExternalStorageAvailable = false;
	private static boolean mExternalStorageWriteable = false;
	static String mLogString = "";


	public TALog() { }

	private static boolean CheckExternalStorage()
	{
		boolean var0 = true;
		String var1 = Environment.getExternalStorageState();
		if ("mounted".equals(var1))
		{
			mExternalStorageWriteable = var0;
			mExternalStorageAvailable = var0;
		}
		else if ("mounted_ro".equals(var1))
		{
			mExternalStorageAvailable = var0;
			mExternalStorageWriteable = false;
		}
		else
		{
			mExternalStorageWriteable = false;
			mExternalStorageAvailable = false;
		}

		if (!mExternalStorageAvailable || !mExternalStorageWriteable)
		{
			var0 = false;
		}

		return var0;
	}

	public static final void Log(String var0)
	{
		Log.d("T&A", var0);
	}

	public static final void Log2(String var0)
	{
		Log.d("T&A", var0);
	}

	public static final void Log2File(String var0)
	{
		String var1 = getCurrentDate() + var0;
		mLogString = mLogString + var1;
		mLogString = mLogString + "\n";
	}

	public static final void SaveToFile()
	{
		if (!CheckExternalStorage())
		{
			Log2("LOG: CheckExternalStorage = FALSE");
		}
		else
		{
			String var0 = Environment.getExternalStorageDirectory().getAbsolutePath();
			File file = new File(var0 + "/" + "MarkitCartLog.txt");

			Exception var2;

			label35:
			{
				FileOutputStream fileOutputStream;
				try
				{
					if (!file.exists() && (!file.createNewFile() || !file.canWrite()))
					{
						file.delete();
						return;
					}

					fileOutputStream = new FileOutputStream(file, true);
				}
				catch (Exception var6)
				{
					var2 = var6;
					break label35;
				}

				try
				{
					fileOutputStream.write(mLogString.getBytes());
					fileOutputStream.flush();
					fileOutputStream.close();
					mLogString = "";
					return;
				}
				catch (Exception var5)
				{
					var2 = var5;
				}
			}

			file.delete();
			Log2("LOG: SaveToFile exception " + var2);
		}
	}

	protected static String formatValue(int var0)
	{
		return var0 < 10 ? "0" + var0 : "" + var0;
	}

	protected static String getCurrentDate()
	{
		Date     date     = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int i = calendar.get(5);
		int j = calendar.get(2) + 1;
		int k = calendar.get(1);
		int m = calendar.get(11) + 1;
		int n = calendar.get(12);
		int i1 = calendar.get(13);

		String str = 
			"" + k + "." + 
			formatValue(j) + "." + 
			formatValue(i) + " " + 
			formatValue(m) + ":" + 
			formatValue(n) + ":" + 
			formatValue(i1) + " ";
		//Log(str);
		return str;
	}
}
