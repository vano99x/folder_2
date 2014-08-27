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
		connection.setRequestMethod("GET");//ProtocolException
		connection.setReadTimeout(TIMEOUT);
		connection.connect();//IOException

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

			outputstream.write(buffer, 0, readBytes);//IOException , IndexOutOfBoundsException
			totlaBytes += readBytes;
		}

		if(messageReceiver != null){
			messageReceiver.AddMessage("end receive data");
			try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
		}

		//try{ Thread.sleep(1000000L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }

		outputstream.flush();//IOException
		inputstream.close();//IOException
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

		huc.setDoInput(true);   //IllegalAccessError
		huc.setDoOutput(false); //IllegalAccessError
		huc.setUseCaches(false);//IllegalStateException
		//huc.setRequestMethod("GET"); huc.setReadTimeout(TIMEOUT);
		huc.setRequestProperty("Connection", "Keep-Alive"); //IllegalStateException , NullPointerException
		huc.setRequestProperty("Content-Type", "text/json");//IllegalStateException , NullPointerException
		//huc.connect();

		//InputStream stream = huc.getInputStream();
		//byte[] arr = new byte[BUFFER_SIZE];
		baos = new ByteArrayOutputStream();
		////while(true)
		//for (int i = 0; i < BUFFER_SIZE; i++)
		//{
		//    int dword = stream.read(arr);
		//    if( dword <= 0 ) { break; }
		//    baos.write(arr, 0, dword);
		//}
		//baos.flush();
		HttpHelper.FromConnectionToStream(baos, huc, null);

		resultStr = new String(baos.toByteArray());
		//TALog.Log("=RESPOND=GET=");
		//TALog.Log(str);

		} finally {
			try{
				if(baos != null){
					baos.close();
				}
			}catch(Exception e){
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
			URL url = new URL(fileURL);                      //MalformedURLException
			con = (HttpURLConnection)(url.openConnection()); //IOException

			if(messageReceiver != null) {
				messageReceiver.AddMessage("open connection");
				try{ Thread.sleep(500L); }catch(InterruptedException e){ UIHelper.Instance().MsgProgress(e); }
			}

			//c.setRequestMethod("GET"); //c.setDoOutput(true); //c.connect();
			fs = new FileOutputStream(directory);                                                  //FileNotFoundException
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
				if(fs != null){
					fs.close();
				}
			}catch(Exception e){
			}

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
		String str = "http://93.153.172.26/authorization?pin=" + mPin;
		//TALog.Log("AUTH URL >> " + str);
		return str;
	}

	public static final String getSearchURL(String mPin)
	{
		//return HttpHelper.getSearchURL( mPin, null);
		return "http://93.153.172.26/get_employee?resp_man=" + mPin;
	}
	//public static final String getSearchURL (String mPin, String paramString)
	//{
		//return "http://93.153.172.26/get_employee?resp_man=" + mPin;
	//}

	public static final String getCheckinURL(Checkin paramCheckin)
	{
		String str = 
		"http://93.153.172.26/check?" +
		"id=" + paramCheckin.SupervicerId + 
		"&cardId="		+ paramCheckin.CardId +
		"&status="		+ paramCheckin.Mode + 
		"&pointId="		+ paramCheckin.PointId + 
		"&date="		+ paramCheckin.DateTime +
		"&pcode="		+ paramCheckin.WorkerId;

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
}