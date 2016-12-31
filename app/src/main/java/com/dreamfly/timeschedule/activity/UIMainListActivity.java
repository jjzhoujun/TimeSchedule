package com.dreamfly.timeschedule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.adapter.MainBaseAdapter;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.Tools;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.widget.EditTextWithDel;
import com.dreamfly.timeschedule.view.widget.ListViewPullToRef;
import com.dreamfly.timeschedule.view.widget.ListViewPullToRef.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import de.greenrobot.event.EventBus;


public class UIMainListActivity extends BaseActivity{
	
	private ImageView mImgAdd = null;
	private EditTextWithDel mEditText = null;
	private ListViewPullToRef mListView;
	private MainBaseAdapter mAdapter;
    private Context mContext;
    private int mClickItem;     // Record the mData position to refresh the list.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tasks);
		initUI();
		initData();
        EventBus.getDefault().register(this);
		setClickListener();
		// create adapt
		mAdapter = new MainBaseAdapter(this);
		// For test
//		for(int i = 0; i < 10; i++){
//			mAdapter.addItem("item " + i);
//		}
		mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 此处position从1开始. id从0开始.
                LogPrint.Debug("position = " + position + "; id = " + id);
                mClickItem = (int)id;
                TimeItemEntity timeItemEntity = (TimeItemEntity) adapterView.getItemAtPosition(position);
                CommonUtils.getInstance(mContext).startAddTaskActivity(UIMainListActivity.this, timeItemEntity);
            }
        });

		mListView.setonRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
//						try {
//							Thread.sleep(500);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						data.addFirst("刷新后的内容");
                        LogPrint.Debug("========noting to do=after refresh the item....");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        addTimeTask();
//                        mListView.onRefreshComplete();
                    }

                }.execute();
            }
        });

        showDatabaseView();
		
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initUI(){
		mEditText = (EditTextWithDel)findViewById(R.id.main_edit_task);
		mImgAdd = (ImageView)findViewById(R.id.main_add);
		mListView = (ListViewPullToRef)findViewById(R.id.main_list);
        // Not use the header view.
//		View headerView = getLayoutInflater().inflate(R.layout.showlisthead, mListView, false);
//		mListView.addHeaderView(headerView);
	}
	
	private void initData(){
        mContext = this;

	}

    private void showDatabaseView() {
		TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(this);
		List<TimeItemEntity> timeStructList = tsDatabaseMgrMul.getAllBoxesData();
        int size = timeStructList.size();
		for(int i=0; i<size; i++) {
			mAdapter.addItem(timeStructList.get(i));
		}
        long lastId;
        if(size > 0) {
            lastId = timeStructList.get(size-1).getId() + 1;
        } else {
            lastId = 0;
        }
        LogPrint.Debug("jayden, lastId == " + lastId);
        CommonUtils.getInstance(mContext).setId(lastId);
    }
	
	private void setClickListener(){
		View.OnClickListener onClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
					case R.id.main_add:
						LogPrint.Debug("====mEditText.getText() = " + mEditText.getText().toString());
                        addTimeTask();
						break;
					default:
						break;
				}
			}
		};
		
		mImgAdd.setOnClickListener(onClickListener);
	}

    private void addTimeTask() {
        String title = mEditText.getText().toString();
        if("".equals(title)){
            Intent intent = new Intent();
            intent.setClass(UIMainListActivity.this, UIAddTaskActivity.class);
            startActivity(intent);
        } else {
            TimeItemEntity timeItemEntity = new TimeItemEntity();
            long id = CommonUtils.getInstance(mContext).getId();
            timeItemEntity.setId(id);
            timeItemEntity.setB_finish(false);
            timeItemEntity.setS_titile(title);
            timeItemEntity.setI_status(ConstantVar.STATUS_FIRST_LEVEL);
            String date = Tools.getCurTimeStr();
            LogPrint.Debug("==>>date = " + date);
            timeItemEntity.setS_start_time(date);
            CommonUtils.getInstance(mContext).saveTimeStruct(timeItemEntity);
            mAdapter.addItem(timeItemEntity);
            mEditText.setText("");
            id++;
            LogPrint.Debug("jayden, add new, lastId ==>> id = " + id);
            CommonUtils.getInstance(mContext).setId(id);
        }
    }

    /**
     * 1. 接收从UIAddTaskActivity传过来的EventBus添加或者更新item.
     * */
    public void onEventMainThread(TimeItemEntity timeItemEntity) {
        boolean isAdd = timeItemEntity.getAddFlag();
        LogPrint.Debug("onEventMainThread...timeItemEntity = " + timeItemEntity.toString() + "; isAdd = " + isAdd);
        if(isAdd){
            mAdapter.addItem(timeItemEntity);
        } else {
            mAdapter.updateItem(mClickItem, timeItemEntity);
        }
    }

}