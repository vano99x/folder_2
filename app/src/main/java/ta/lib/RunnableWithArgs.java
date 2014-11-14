package ta.lib;

import java.lang.Object;
import java.lang.Runnable;

public abstract class RunnableWithArgs<TArg,TRes> implements Runnable
{
	public RunnableWithArgs() { }

	public RunnableWithArgs(Object o1) {
		arg1 = o1;
	}

	public RunnableWithArgs(Object o1,Object o2) {
		arg1 = o1;
		arg2 = o2;
	}

	public RunnableWithArgs(Object o1,Object o2,Object o3) {
		arg1 = o1;
		arg2 = o2;
		arg3 = o3;
	}

	public RunnableWithArgs(Object o1,Object o2,Object o3,Object o4) {
		arg1 = o1;
		arg2 = o2;
		arg3 = o3;
		arg4 = o4;
	}

	public TArg arg;
	public TRes result;

	public Object arg1;
	public Object arg2;
	public Object arg3;
	public Object arg4;
	public Object arg5;
	public Object arg6;
	public Object arg7;
	public Object arg8;
	public Object arg9;
}