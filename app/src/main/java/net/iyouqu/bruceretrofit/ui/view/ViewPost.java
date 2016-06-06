package net.iyouqu.bruceretrofit.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.iyouqu.bruceretrofit.R;


/**
 * ViewGroup 关联一个 item 布局
 * from : https://drakeet.me/mvp-and-thinking-in-android
 * Created by liq on 16/6/7.
 */
public class ViewPost extends LinearLayout implements View.OnClickListener {

    private TextView tv_category;
    private TextView tv_title;
    // IPost interface
//    protected IPost mData;
    public ViewPost(Context context) {
        this(context, null);
    }

    public ViewPost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.item_gank,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_title = (TextView) findViewById(R.id.tv_title);
        setOnClickListener(this);
    }

//    public void setData(IPost data) {
//        mData = data;
//        Glides.loadCircleImage(getContext(), data.getAvatar(), mAvatar);
//        mUsername.setText(data.getUsername());
//        mCreateAt.setText(data.getCreatedAtFromNow());
//        mSummary.setText(data.getSummary());
//    }

    @Override
    public void onClick(View v) {
//        Intent intent = StreamActivity.newIntent(getContext(),
//                mData.getAvatar(), mData.getUsername(), mData.getUserId(),
//                mData.isCurrentUser());
//        getContext().startActivity(intent);
    }
}
