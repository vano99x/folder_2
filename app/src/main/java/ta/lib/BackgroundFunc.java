package ta.lib;

import java.lang.reflect.*;
import android.os.Handler;

public class BackgroundFunc<TArg,TRes> extends RunnableWithArgs<Object,Object>
{
	public BackgroundFunc()
	{
		//this.BackgroundFuncComplete = this.new BackgroundFuncCompleteEventClass();
		//this.BackgroundFuncComplete.set_EventTypeHolder(this);
	}

	public static void Go(
		BackgroundFunc _this, RunnableWithArgs targetFunc, RunnableWithArgs completeFunc, String name)
	{
		int aaa = 9;
		int aaa2 = aaa - 2;

		_this.arg1 = targetFunc;
		_this.BackgroundFuncComplete = _this.new BackgroundFuncCompleteEventClass();
		//_this.BackgroundFuncComplete.set_EventTypeHolder(_this);
		_this.BackgroundFuncComplete.set_EventTypeHolder(targetFunc);
		_this.BackgroundFuncComplete.Add(completeFunc);
		new Thread(_this,name).start();
	}

	public static void Go( RunnableWithEvent tF, String name)
	{
		class TempClass implements Runnable{
			private RunnableWithEvent _tF;
			public TempClass(RunnableWithEvent tF){
				_tF = tF;
			}
			public void run()
			{
				final RunnableWithEvent targetFunc  = this._tF;

				targetFunc.run();
				try
				{
					Handler h = ta.timeattendance.MainActivity.get_RespondHandler();
					h.post(new Runnable(){public void run(){
						//RunnableWithEvent.this.BackgroundFuncComplete.RunEvent(targetFunc.arg,targetFunc.result);
						targetFunc.BackgroundFuncComplete.RunEvent(targetFunc.arg,targetFunc.result);
					}});
				}
				catch(Exception e)
				{
					Exception ex = e;
				}
			}
		}

		new Thread(new TempClass(tF),name).start();
	}

	public static <TArg2,TRes2> void RunEventInUiThread(Event<TArg2,TRes2> event, TArg2 arg, TRes2 res)
	{
		Handler h = ta.timeattendance.MainActivity.get_RespondHandler();
		h.post(new RunnableWithArgs(event,arg,res){public void run(){
			((Event<TArg2,TRes2>)this.arg1).RunEvent( (TArg2)this.arg2, (TRes2)this.arg3);
		}});
	}

	public void run()
	{
		//String message = null;
		final RunnableWithArgs<TArg,TRes> targetFunc  = (RunnableWithArgs<TArg,TRes>)this.arg1;
		//Object eventHolder = this.arg2; //final String eventName = (String)this.arg3;
		//try {
		//	UIHelper.Instance().RunAndWait( targetFunc );
		//}
		//catch(NoSuchFieldException nsfe)  { Exception e = nsfe; message = e.getMessage(); }
		//catch(NoSuchMethodException nsme) { Exception e = nsme; }
		//catch(IllegalAccessException iae) { Exception e = iae;  message = e.getMessage(); }
		//catch(Exception ite)//InvocationTargetException
		//{
		//	Exception e = ite;
		//	message = e.getMessage();
		//}
		targetFunc.run();

		//if(message == null)
		//{
		//	message = "SUCCESS";
		//}

		//this.result = new Object[]{message};
		//BackgroundFuncComplete.RunEvent(new Object[]{message});
		try {
			Handler h = ta.timeattendance.MainActivity.get_RespondHandler();
			h.post(new Runnable() { public void run() {
				//Object res = targetFunc.result;
				BackgroundFunc.this.BackgroundFuncComplete.RunEvent(targetFunc.arg,targetFunc.result);
			}});
		}
		catch(Exception e)
		{
			Exception ex = e;
		}
	}

	public class  BackgroundFuncCompleteEventClass extends Event<TArg,TRes> {}
	public BackgroundFuncCompleteEventClass BackgroundFuncComplete;

	//public static <TArg,TRes> BackgroundFunc<TArg,TRes> get_BackgroundFunc(
	//	RunnableWithArgs<TArg,TRes> targetFunc, 
	//	Object eventHolder //,String eventName //,Object[] paramArr
	//)
	//{
	//	BackgroundFunc<TArg,TRes> bf = new BackgroundFunc<TArg,TRes>();
	//	bf.BackgroundFuncComplete = bf.new BackgroundFuncCompleteEventClass();

	//	bf.arg1 = (RunnableWithArgs<TArg,TRes>)targetFunc; //bf.arg2 = eventHolder; //bf.arg3 = eventName; //bf.arg4 = paramArr;
	//	return bf;
	//}

	//public static <TArg,TRes> void Go(RunnableWithArgs<TArg,TRes> targetFunc, RunnableWithArgs<TArg,TRes> completeFunc,String name)
	//{
	//	BackgroundFunc<TArg,TRes> bf = BackgroundFunc.<TArg,TRes>get_BackgroundFunc( targetFunc, null );
	//	bf.BackgroundFuncComplete.Add(completeFunc);
	//	new Thread(bf,name).start();
	//}

}





/*
		final Object[] eventParams = (Object[])targetFunc.result;

			Field eventField = eventHolder.getClass().getField(eventName);
			final Object eventObj = eventField.get(eventHolder);

			//Class classf = field.getType();
			//Method method = classf.getMethod("RunEvent", eventParams.getClass());

			//final Method method = eventField.getType().getMethod("RunEvent", eventParams.getClass());
			//final Method method = eventField.getType().getMethod("RunEvent");
			Method [] methods = eventField.getType().getMethods();
			Method method = null;
			for (Method m : methods)
			{
				String name = m.getName();
				//if( name.equals("RunEvent") )
				//{
				//    method = m;
				//    break;
				//}
//				if(eventName == "SaveCheckinCompleteEvent")
				{
				    if( name.equals("RunEvent2") )
				    {
				        method = m;
				        break;
				    }
				}
				else
				{
				    if( name.equals("RunEvent") )
				    {
				        method = m;
				        break;
				    }
				}
			}

			if(method != null)
			{
				final Method methodF = method;

				UIHelper.Instance().get_RespondHandler().post(new Runnable()
				{
				  public void run()
				  {
					try
					{
					final Method   _methodF     = methodF;
					final Object   _eventObj    = eventObj;
					final Object[] _eventParams = eventParams;
		//UIHelper.Instance().ToastInUIThread("aaa \n before methodF.invoke", 3);
		//UIHelper.Instance().Toast("aaa \n before methodF.invoke", 3);
//						if(eventName == "SaveCheckinCompleteEvent")
						{
						Object resinvoke = _methodF.invoke( _eventObj, new Object [] { _eventParams, "in ui thread" } );
						}
						else
						{
						Object resinvoke = _methodF.invoke( _eventObj, new Object [] { _eventParams } );
						}
						int aaa = 9;
						int aaa2 = aaa - 2;
					}
					catch(Exception e)
					{
						Exception ex = e;
					}

				  }
				});
			}
			else
			{
				message = "событие чекина не найдено";
			}
*/