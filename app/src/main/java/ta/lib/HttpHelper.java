package ta.lib;

import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.lang.Exception;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Array;
import android.util.Base64;
import java.util.SortedMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Collections;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import ta.lib.UIHelper.Act;
import ta.Database.Checkin;

public class HttpHelper
{
	static int TIMEOUT = 15000;//final 
	//static final int BUFFER_SIZE = 0x40000;
	public static String AkmeHost = "akme";//final 

	private static void FromConnectionToStream(OutputStream outputstream, HttpURLConnection connection, IMessageReceiver messageReceiver)
		throws java.io.IOException, 
			   java.net.ProtocolException, 
			   java.lang.IndexOutOfBoundsException
	{
		connection.setRequestMethod("GET");					//ProtocolException
		connection.setReadTimeout(TIMEOUT);
		connection.connect();								//IOException

		InputStream inputstream = connection.getInputStream();//IOException

		if(messageReceiver != null){
			messageReceiver.AddMessage("connect to server");
			try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
		}

		byte[] buffer = new byte[0x4000];
		int totlaBytes = 0;
		int readBytes = 0;

		if(messageReceiver != null){
			messageReceiver.AddMessage("start receive data");
			try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
		}

		if(messageReceiver != null)
		messageReceiver.SetTitle("---");

		while( (readBytes = inputstream.read(buffer)) != -1)//IOException
		{
			if(messageReceiver != null)
				messageReceiver.SetTitle(Integer.toString(totlaBytes));

			outputstream.write(buffer, 0, readBytes);		//IOException , IndexOutOfBoundsException
			totlaBytes += readBytes;
		}

		if(messageReceiver != null){
			messageReceiver.AddMessage("end receive data");
			try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
		}

		//try{ Thread.sleep(1000000L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }

		outputstream.flush();								//IOException
		inputstream.close();								//IOException
	}

	public static final String httpGet(URL url)
		throws java.io.IOException,
			   java.net.ProtocolException,
			   java.lang.IndexOutOfBoundsException,
			   java.lang.IllegalAccessError,
			   java.lang.IllegalStateException,
			   java.lang.NullPointerException
	{
		String resultStr = null;
		HttpURLConnection huc = null;
		ByteArrayOutputStream baos = null;
		try{

		huc = (HttpURLConnection)url.openConnection();//IOException

		huc.setDoInput(true);								//IllegalAccessError
		huc.setDoOutput(false);								//IllegalAccessError
		huc.setUseCaches(false);							//IllegalStateException
		//huc.setRequestMethod("GET"); huc.setReadTimeout(TIMEOUT);
		huc.setRequestProperty("Connection", "Keep-Alive"); //IllegalStateException , NullPointerException
		huc.setRequestProperty("Content-Type", "text/json");//IllegalStateException , NullPointerException

		baos = new ByteArrayOutputStream();
		HttpHelper.FromConnectionToStream(baos, huc, null);

		byte [] bytes = baos.toByteArray();
		//bytes = Exchange1251SymbolToUTF8(bytes);
		resultStr = new String(bytes);

		} finally {
			try{
				if(baos != null){ baos.close(); }
			}catch(Exception e)
			{
				Exception ex = e;
			}

			if(huc != null)
				huc.disconnect();
		}
		return resultStr;
	}

	public static boolean DownloadFile(String fileURL, File directory) throws java.net.MalformedURLException, java.io.IOException, java.io.FileNotFoundException, java.net.ProtocolException, java.lang.IndexOutOfBoundsException
	{
		return DownloadFile(fileURL, directory, null);
	}

	public static boolean DownloadFile(String fileURL, File directory, IMessageReceiver messageReceiver)
		throws java.net.MalformedURLException,
			   java.io.IOException,
			   java.io.FileNotFoundException,
			   java.net.ProtocolException,
			   java.lang.IndexOutOfBoundsException
	{
		boolean result = false;
		FileOutputStream fs = null;
		HttpURLConnection con = null;
		try
		{
			URL url = new URL(fileURL);						//MalformedURLException
			con = (HttpURLConnection)(url.openConnection());//IOException

			if(messageReceiver != null) {
				messageReceiver.AddMessage("open connection");
				try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
			}

			//c.setRequestMethod("GET"); //c.setDoOutput(true); //c.connect();

			fs = new FileOutputStream(directory);			//FileNotFoundException

			//InputStream stream = c.getInputStream(); //byte[] buffer = new byte[1024]; int readBytes = 0; //while( (readBytes = stream.read(buffer)) > 0 ) { f.write(buffer, 0, readBytes); }

			HttpHelper.FromConnectionToStream(fs, con, messageReceiver);
			result = true;
		}
		//catch (Exception e) {
		//    if(messageReceiver != null) UIHelper.Instance().MsgProgress(e); //e.toString() e.printStackTrace();
		//}
		finally
		{
			try{
				if(fs != null){ fs.close(); }
			}catch(Exception e){ }

			if(con != null)
				con.disconnect();
		}
		return result;
	}

	public static boolean IsInternetAvailable(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		//NetworkInfo[] niArr = cm.getAllNetworkInfo();
		//TALog.Log2File("=GET=URL=");
		//TALog.Log2File(url.getPath());

		boolean result = false;
		if(netInfo != null && netInfo.isConnectedOrConnecting())
		{
			result = true;
		}
		return result;
	}

	public static final String getAuthRequestURL(String mPin)
	{
		//String str = "http://93.153.172.26/authorization?pin=" + mPin;
		  String str = "http://93.153.172.26/get_supervisor?pin=" + mPin + "&fields[0]=personel";
		//TALog.Log("AUTH URL >> " + str);
		return str;
	}

	public static final String getWorkersBySupervisor(String mPin)
	{
		return "http://93.153.172.26/get_employee?resp_man=" + mPin;
	}

	public static final String getCheckinURL(Checkin paramCheckin)
	{
		String str = 
		"http://93.153.172.26/check?" +
		"id="			+ paramCheckin.SupervicerId +
		"&pcode="		+ paramCheckin.WorkerId + 
		"&cardId="		+ paramCheckin.CardId +
		"&status="		+ paramCheckin.Mode + 
		"&pointId="		+ paramCheckin.PointId + 
		"&date="		+ paramCheckin.DateTime;

		return str;
	}

	public static final String getEOFurl(Checkin paramCheckin)
	{
		String str = "http://akme.telemetry.i-free.ru/Api/sync/checkin?id=4&WorkerId=5&cardId=2999600235&status=3&pointId=4&date=1382632193964";
		return str;
	}

	public static String ByteArrayToStr(byte[] bytes)
	{
		return null;
	}

	public static String getPhotoAddress(int paramInt)
	{
		return "http://" + AkmeHost + "/avatar?id=" + Integer.toString(paramInt) + "&hash=true";
	}

	public static URL GetFacilityInfoURL(String pin, String startDate, String endDate) throws java.net.MalformedURLException
	{
		return new URL("http://"+
			"93.153.172.26/get_statistics?" +
			"pin="        + pin             +"&"+
			"start_date=" + startDate       +"&"+ //"start_date=26.10.2013&"+
			"end_date="   + endDate);              //"end_date=27.10.2013");
	}

	//public static boolean CheckInternetAndShowMessage(com.ifree.timeattendance.MainEngine engine, Context сontext)
	//{
	//	boolean result = HttpHelper.IsInternetAvailable(сontext);

	//	if(!engine.get_IsIntenetDisable() && !result)
	//	{
	//		//UIHelper.Instance().ToastInUIThread("aaa \n before methodF.invoke", 3);
	//		UIHelper.Instance().Toast("отсутствует подключение к интернету,\nиспользуйте автономную работу");
	//	}
	//	return result;
	//}

	public static void ExceptionHandler(Exception e)
	{
		SocketTimeoutException        ste = null;
		UnknownHostException          uhe = null;
		java.net.ConnectException     ce  = null;
		java.io.FileNotFoundException fnf = null;

		if(
			( ste = operator.as(SocketTimeoutException.class, e)) != null
		){
			UIHelper.Instance().MsgFromBackground( Act.TimeoutException );
		}
		else if(
			( uhe = operator.as(UnknownHostException.class, e)) != null
		){
			String message = uhe.getMessage();
			if( message.equals(HttpHelper.AkmeHost) ) {
				UIHelper.Instance().MsgFromBackground(Act.ServerNotAvailable);
			}else{
				UIHelper.Instance().MsgFromBackground(Act.ServerNotRespond);
			}
		}
		else if(
			( ce  = operator.as(java.net.ConnectException.class, e))     != null
		){
			UIHelper.Instance().MsgFromBackground(Act.ServerNotAvailable);
		}
		else if(
			( fnf = operator.as(java.io.FileNotFoundException.class, e)) != null
		){
			//int aaa = 9;
			UIHelper.Instance().MsgFromBackground(Act.ServerNotAvailable);
			//UIHelper.Instance().MsgExceptionFromBackground(e);
		}
		else
		{
			UIHelper.Instance().MsgExceptionFromBackground(e);
		}
	}

	private static byte [] Exchange1251SymbolToUTF8(byte [] array)
		throws java.io.UnsupportedEncodingException
	{
		ArrayList<Byte> list = new ArrayList<Byte>();
		int count = array.length;
		boolean is1251 = false;
		for (int i=0;i<count;i++)
		{
			is1251 = false;
			int item = array[i] & 0xFF;

			if(127<item)
			{
				is1251 = true;
				if(191<item)
				{
					int nextI = i+1;
					if(nextI < count)
					{
						int next = array[nextI] & 0xFF;
						if(127<next && next<192)
						{
							is1251 = false;
						}
					}
				}
			}

			if(is1251)
			{
				byte [] arr = {array[i]};
				String str = new String(arr, get_Windows1251Charset());
				short shortBuffer = (short)str.charAt(0);

				/*byte[] b = null;
				try{
					b = str.getBytes("UTF-8");
					//b = str.getBytes("windows-1251");
				}catch(Exception e) { Exception ex = e; }
				ShortBuffer shb = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
				short shortBuffer = shb.get(0);*/

				String shortStr = null;
				//String shortStr = String.valueOf(shortBuffer);
				//String aaa = Integer.toHexString(shortBuffer);
				//String shortStr = null;
				//String shortStr2 = null;
				//String shortStr3 = null;
				//String shortStr4 = null;
				//try{
				//	shortStr  = String.format("%1$" + 7 + "s", aaa);
				//	shortStr2 = String.format("Amount : %08d %n" , 225);
				//	shortStr3 = String.format("%08d" , 221);
					shortStr = String.format("%04x" , shortBuffer);
				//}
				//catch(Exception e)
				//{
				//	Exception ex = e;
				//}
				shortStr = "\\u" + shortStr;

				byte[] b = shortStr.getBytes("UTF-8");
				int count2 = b.length;
				for (int i2=0; i2<count2; i2++)
				{
					Byte b2 = new Byte(b[i2]);
					list.add(b2);
				}
			}
			else
			{
				Byte b3 = new Byte(array[i]);
				list.add(b3);
			}
		}

		byte[] ret = new byte[list.size()];
		Iterator<Byte> iterator = list.iterator();
		for (int i3 = 0; i3 < ret.length; i3++)
		{
			ret[i3] = iterator.next().byteValue();
		}
		return ret;
	}

	private static Charset _windows1251Charset;
	public  static Charset get_Windows1251Charset()
	{
		if(_windows1251Charset == null)
		{
			Charset windows1251Charset = null;
			ArrayList<Charset> list = new ArrayList<Charset>();

			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			Set names = charsets.keySet();

			for(Iterator e = names.iterator(); e.hasNext();)
			{
				String name = (String) e.next();
				Charset chs = (Charset) charsets.get(name);
				String str = chs.name();
				if(str.indexOf("1251") > -1)
				{
					list.add(chs);
				}
			}

			Charset[] array = (Charset[])list.toArray(new Charset[0]);
			_windows1251Charset = array[0];
		}
		return _windows1251Charset;
	}
}