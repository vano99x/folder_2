package ta.timeattendance;

public class MainEngine_Temp
{

	//private Personel[] parsePersonelArrayRespond(String paramString)
	//{
	//    Personel[] arrayOfPersonel;
	//    try
	//    {
	//        JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("personels");
	//        arrayOfPersonel = new Personel[localJSONArray.length()];
	//        for (int i = 0; i < localJSONArray.length(); i++)
	//        {
	//            arrayOfPersonel[i] = Personel.CreatePersonelFromJson( localJSONArray.getJSONObject(i), this.mContext);
	//        }
	//    }
	//    catch (Exception localException)
	//    {
	//        arrayOfPersonel = new Personel[0];
	//    }
	//    return arrayOfPersonel;
	//}

	//private Point[] parsePointsArrayRespond(String paramString)
	//{
	//    Point[] arrayOfPoint;
	//    try
	//    {
	//        //JSONObject jo = new JSONObject(paramString);
	//        //JSONArray localJSONArray = jo.getJSONArray("Points");
	//        //arrayOfPoint = new Point[localJSONArray.length()];
	//        //for (int i = 0; i < localJSONArray.length(); i++)
	//        //{
	//        //    arrayOfPoint[i] = Point.getInstance(localJSONArray.getJSONObject(i));
	//        //}
	//        arrayOfPoint = this.currentSuperviser.pointArray;
	//    }
	//    catch (Exception localException)
	//    {
	//        arrayOfPoint = new Point[0];
	//    }
	//    return arrayOfPoint;
	//}

	//private PersonelPoint[] parsePersonelPointsArrayRespond(String paramString)
	//{
	//    PersonelPoint[] ppArr;
	//    try
	//    {
	//        //JSONObject jo = new JSONObject(paramString);
	//        //JSONArray jsArr = jo.getJSONArray("pointPersonel");
	//        //ppArr = new PersonelPoint[jsArr.length()];
	//        //for (int i = 0; i < jsArr.length(); i++)
	//        //{
	//        //    ppArr[i] = PersonelPoint.getInstance(jsArr.getJSONObject(i));
	//        //}
	//        int PersonelId = this.currentSuperviser.Id;

	//        int count = this.currentSuperviser.pointArray.length;
	//        ppArr = new PersonelPoint[count];

	//        for (int i = 0; i < count; i++)
	//        {
	//            int PointId = this.currentSuperviser.pointArray[i].Id;
	//            ppArr[i] = PersonelPoint.getInstance( PersonelId, PointId);
	//        }
	//    }
	//    catch (Exception localException)
	//    {
	//        ppArr = new PersonelPoint[0];
	//    }
	//    return ppArr;
	//}


















	//private void checkInAssync(long paramLong)
	//{
	//    try
	//    {
	//        this.currentPersonel = Personel.getByCard(paramLong, this.mContext);
	//        if (this.currentPersonel != null)
	//        {
	//            saveCheckin(this.currentPersonel.CardId);
	//        }
	//        showScreen(MainActivity.State.PERSONEL_INFO, 0L);
	//    }
	//    catch (Exception localException)
	//    {
	//        showScreen(MainActivity.State.PERSONEL_LIST_MODE, 0L);
	//    }
	//}





	//final Object _lockObj = new Object();
	//private static enum Act { 
	//    StartOperation, ServerNotRespond, UnhandledException, 
	//    AuthOk, AuthError, 
	//    SyncCompleted, CheckinSuccess, 
	//    CheckinFailed; }
	//private MyRunnable _myRunnable;
	//private MyRunnable get_MyRunnable(Act act)
	//{
	//    if(_myRunnable == null)
	//    {
	//        _myRunnable = new MyRunnable();
	//    }
	//    _myRunnable._data = act;
	//    return _myRunnable;
	//}
	//private class MyRunnable implements Runnable
	//{
	//    public Act _data;
	//    public void run() { synchronized( _lockObj )
	//    {
	//        if(this._data == Act.StartOperation)
	//        {
	//            MainEngine.this.mProgress = ProgressDialog.show( MainEngine.this.mContext, "", "Пожалуйста, подождите...", true );
	//        }
	//        else
	//        {
	//            MainEngine.this.mProgress.dismiss();
	//            switch(this._data)
	//            {
	//                case AuthOk:{
	//                    MainEngine.this.showToast("Авторизация прошла успешно!");
	//                    //TALog.Log("Авторизация прошла успешно!");
	//                    MainEngine.this.mListener.showScreen(MainActivity.State.MAIN_MENU);
	//                break;}
	//                case AuthError:{
	//                    MainEngine.this.showToast("Неверный пин!");
	//                break;}
	//                case ServerNotRespond:{
	//                    MainEngine.this.showToast("Сервер не отвечет!" );
	//                break;}
	//                case SyncCompleted:{
	//                    MainEngine.this.showToast("Синхронизация завершена!" );
	//                break;}

	//                case CheckinSuccess:{
	//                    MainEngine.this.showToast("Чекин прошёл успешно!");
	//                break;}
	//                case CheckinFailed:{
	//                    MainEngine.this.showToast("соединение с интернетом отсутствует \n нажмите на кнопку синхронизации при появлении интернета");
	//                break;}
	//                case UnhandledException:{
	//                    MainEngine.this.showToast("неожиданная ошибка");
	//                break;}
	//            }
	//        }
	//        _lockObj.notifyAll();
	//    }}
	//}
	//private void WaitUpdateUI(MyRunnable r)
	//{
	//    this.respondHandler.post(r);
	//    synchronized( _lockObj ) { try {
	//        _lockObj.wait();
	//    } catch (InterruptedException e) { } }
	//}









  //public boolean onCreateOptionsMenu(Menu paramMenu)
  //{
  //  if (UIHelper.Instance().currentState != State.POINTS_LIST)
  //  {
  //    getMenuInflater().inflate(2131296256, paramMenu);
  //    return true;
  //  }
  //  return false;
  //}

	// <summary>
	// tab main menu
	// </summary>
	// <param name="paramView"></param>
	//public void startWork(View paramView)
	//{
	//    UIHelper.Instance().switchState(State.POINTS_LIST);
	//}
	//public void startSync(View paramView) //throws Exception
	//{
	//    this.mEngine.startSync();
	//}
	//public void ShowCheckinList(View paramView)
	//{
	//    try
	//    {
	//        UIHelper.Instance().switchState(State.CHECKIN_LIST);
	//    }
	//    catch(Exception e)
	//    {
	//        Exception ex = e;
	//    }
	//}


	//// tab mode selection
	//public void setStartWorkMode(View paramView)
	//{
	//    this.mEngine.setCurrentMode(Mode.StartWork);
	//    //this.mEngine.showScreen(State.WAIT_MODE, 0L);
	//                   showScreen(State.WAIT_MODE);
	//}
	//public void setEndWorkMode(View paramView)
	//{
	//    this.mEngine.setCurrentMode(Mode.EndWork);
	//    //this.mEngine.showScreen(State.WAIT_MODE, 0L);
	//                   showScreen(State.WAIT_MODE);
	//}
	//public void setCheckMode(View paramView)
	//{
	//    this.mEngine.setCurrentMode(Mode.Check);
	//    //this.mEngine.showScreen(State.WAIT_MODE, 0L);
	//                   showScreen(State.WAIT_MODE);
	//}


	//// tab wait
	//public void setSearchMode(View paramView)
	//{
	//    showScreen(State.PERSONEL_LIST_MODE);
	//    //long AzanatCard = 2999286619L; this.mEngine.OnNfcTagApply(AzanatCard);
	//}
	//public void returnToModeSelection(View paramView)
	//{
	//    //this.mEngine.showScreen(State.MODE_SELECTION, 0L);
	//    showScreen(State.MODE_SELECTION);
	//}


	//// tab personel list
	//public void searchPersonel(View paramView)
	//{
	//    //String str = UIHelper.Instance().tabPersonelList.getSearchString();
	//    //this.mEngine.searchPersonel(str);
	//}
	//public void ShowListPersonel(Personel[] arrPersonel)
	//{
	//    //UIHelper.Instance().tabPersonelList.changeList(arrPersonel);
	//    //    //this.mEngine.showScreen(State.PERSONEL_LIST_MODE, 0L);
	//    //showScreen(State.PERSONEL_LIST_MODE);
	//}


	//// tab personel info
	//public void onClick_CheckinBtn(View paramView)
	//{
	//    int aaa = 9;
	//}
	








	//public static boolean IsCreate()
	//{
	//    boolean isCreate = MainActivity.__instance == null? false: true;
	//    return isCreate;
	//}
	
	//=============================================================================================
	//      1+2
	//public static void Create(MainActivityProxy fa)
	//{
	//    if(MainActivity.__instance == null)
	//    {
	//        MainActivity.__instance = new MainActivity();
	//        MainActivity.__instance.set_FragmentActivity( fa );
	//        MainActivity.__instance.onCreate();
	//    }
	//}
	//public static void Delete()
	//{
	//    if(MainActivity.__instance != null)
	//    {
	//        MainActivity.__instance.onDelete();
	//        MainActivity.__instance = null;
	//    }
	//}
	//public static void Start()
	//{
	//    if(
	//        //MainActivity.__instance != null && 
	//        MainActivity.__instance._isRunning == false
	//    )
	//    {
	//        MainActivity.__instance._isRunning = true;
	//        //MainActivity.__instance.onCreate();
	//        MainActivity.__instance.onStart();
	//    }
	//}
	//public static void Stop()
	//{
	//        MainActivity.__instance._isRunning = false;
	//        MainActivity.__instance.onStop();
	//}

	//=============================================================================================
	//      3
	//public static void Resume()
	//{
	//    if(MainActivity.__instance != null)
	//    {
	//        MainActivity.__instance.onResume();
	//    }
	//}
	//public static void Pause()
	//{
	//    if(MainActivity.__instance != null)
	//    {
	//        MainActivity.__instance.onPause();
	//    }
	//}









	//// show selected personel
	//public void ShowTabPersonelInfo( final Personel p)
	//{
	//    this.set_CurrentPersonel(p);
	//    UIHelper.Instance().tabPersonelInfo.IsShowCheckiedWorker = false;
	//    UIHelper.Instance().ShowScreenFromBackground(MainActivity.State.PERSONEL_INFO, 0L);
	//}
}