package ta.timeattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ExceptionActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

		setContentView(R.layout.exception_activity);
		TextView textView = (TextView)this.findViewById(R.id.ExceptionActivity_TextView);

		Intent intent = getIntent();
		String message = intent.getStringExtra("Message");

		textView.setText(message);
	}
}