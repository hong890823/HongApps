package com.hong.hongflowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hong.hongflowlayout.view.CustomView;
import com.hong.hongflowlayout.view.FlowLayout;

public class MainActivity extends Activity
{
	private String[] mVals = new String[]
	{ "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
			"Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
			"Android", "Weclome Hello", "Button Text", "TextView" };

	private FlowLayout mFlowLayout;
	private CustomView customView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFlowLayout = (FlowLayout) findViewById(R.id.id_flowlayout);
		customView = (CustomView) findViewById(R.id.id_custom_view);

		initData();
	}

	public void initData()
	{
		// for (int i = 0; i < mVals.length; i++)
		// {
		// Button btn = new Button(this);
		//
		// MarginLayoutParams lp = new MarginLayoutParams(
		// MarginLayoutParams.WRAP_CONTENT,
		// MarginLayoutParams.WRAP_CONTENT);
		//
		// btn.setText(mVals[i]);
		// mFlowLayout.addView(btn, lp);
		// }
		LayoutInflater mInflater = LayoutInflater.from(this);
		for (int i = 0; i < mVals.length; i++)
		{
			TextView tv = (TextView) mInflater.inflate(R.layout.tv,
					mFlowLayout, false);
			tv.setText(mVals[i]);
			mFlowLayout.addView(tv);
		}

	}

}
