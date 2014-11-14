package ta.lib;

public abstract class RunnableWithEvent<TArg,TRes> extends RunnableWithArgs<TArg,TRes>
{
	public RunnableWithEvent() // <Arg,TRes>
	{
		this.BackgroundFuncComplete = this.new BackgroundFuncCompleteEventClass();
		this.BackgroundFuncComplete.set_EventTypeHolder(this);
	}

	public RunnableWithEvent(RunnableWithArgs handler) // <Arg,TRes>
	{
		this.BackgroundFuncComplete = this.new BackgroundFuncCompleteEventClass();
		this.BackgroundFuncComplete.set_EventTypeHolder(this);

		this.BackgroundFuncComplete.Add(handler);
	}

	public RunnableWithEvent<TArg,TRes> Add(RunnableWithArgs handler)
	{
		this.BackgroundFuncComplete.Add(handler);
		return this;
	}

	public class BackgroundFuncCompleteEventClass extends Event<TArg,TRes> {}
	public BackgroundFuncCompleteEventClass BackgroundFuncComplete;

	public void call()
			throws Exception
	{
		run();
	}
}
