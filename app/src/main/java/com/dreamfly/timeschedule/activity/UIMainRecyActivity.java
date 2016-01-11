package com.dreamfly.timeschedule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.adapter.RecyclerViewAdapter;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.listener.RecItemClickListener;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.Tools;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.timeschedule.view.widget.EditTextWithDel;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;


public class UIMainRecyActivity extends BaseActivity{

	private DrawerLayout mDrawerLayout;
	private ImageView mImgAdd = null;
	private EditTextWithDel mEditText = null;
	private RecyclerView mRecyclerView;
	private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private int mClickItem;     // Record the mData position to refresh the list.
	private List<Entity> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_recy_tasks);
        initData();
		initUI();
        EventBus.getDefault().register(this);
		// create adapt
		showDatabaseView();

		mAdapter =  new RecyclerViewAdapter(datas);
		mRecyclerView.setAdapter(mAdapter);
		setListener();

		//0则不执行拖动或者滑动
		ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
			/**
			 * @param recyclerView
			 * @param viewHolder 拖动的ViewHolder
			 * @param target 目标位置的ViewHolder
			 * @return
			 */
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
				int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
				int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
				LogPrint.Debug("==>> fromPosition = " + fromPosition + "; toPos = " + toPosition);
				if (fromPosition < toPosition) {
					//分别把中间所有的item的位置重新交换
					for (int i = fromPosition; i < toPosition; i++) {
						swapItemData(datas, i, i + 1);
					}
				} else {
					for (int i = fromPosition; i > toPosition; i--) {
						swapItemData(datas, i, i - 1);
					}
				}
				mAdapter.notifyItemMoved(fromPosition, toPosition);
				//返回true表示执行拖动
				return true;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
				int position = viewHolder.getAdapterPosition();
				TimeItemEntity itemEntity = (TimeItemEntity) datas.get(position);
				CommonUtils.getInstance(mContext).delTimeStruct(itemEntity.getId());
				datas.remove(position);
				mAdapter.notifyItemRemoved(position);
			}

			@Override
			public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
				super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
				if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
					//左右滑动时改变Item的透明度
					final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
					viewHolder.itemView.setAlpha(alpha);
					viewHolder.itemView.setTranslationX(dX);
				}
			}

			@Override
			public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
				//当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
				LogPrint.Debug("onSelectedChanged, actionState = " + actionState);
				super.onSelectedChanged(viewHolder, actionState);

			}

			@Override
			public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
				super.clearView(recyclerView, viewHolder);
				//当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
				Log.v("zxy", "clearView");
			}
		};
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
		itemTouchHelper.attachToRecyclerView(mRecyclerView);

	}
		
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("LeftFragment");
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("LeftFragment");
		MobclickAgent.onPause(this);
	}

	private void initUI(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
//		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.LEFT);
		mEditText = (EditTextWithDel)findViewById(R.id.main_edit_task);
		mImgAdd = (ImageView)findViewById(R.id.main_add);
		mRecyclerView = (RecyclerView)findViewById(R.id.main_list);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
	}
	
	private void initData(){
        mContext = this;
	}


    private void showDatabaseView() {
		TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(this);
		List<TimeItemEntity> timeStructList = tsDatabaseMgrMul.getAllBoxesData();
        int size = timeStructList.size();
		datas = new ArrayList<>();
		for(int i=0; i<size; i++) {
			datas.add(timeStructList.get(i));
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

	private void setListener(){

		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
//				LogPrint.Debug("onDrawerSlide.. slideOffset = " + slideOffset);
//				View content = mDrawerLayout.getChildAt(0);
//				View menu = drawerView;
//				float scale = 1 - slideOffset;
//				if (drawerView.getTag().equals("LEFT")) {
//					float leftScale = 1 - 0.3f * scale;
//
//				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
//				LogPrint.Debug("== onDrawerOpened.....");

			}

			@Override
			public void onDrawerClosed(View drawerView) {
//				LogPrint.Debug("== onDrawerClosed.....");

			}

			@Override
			public void onDrawerStateChanged(int newState) {
//				LogPrint.Debug("== onDrawerStateChanged...... newState = " + newState);

			}
		});
		setDrawerLeftEdgeSize(mDrawerLayout, 0.3f);


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

		mAdapter.setOnItemClickListener(new RecItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				LogPrint.Debug("==>> recyclerview onclick.. position = " + position);
				MobclickAgent.onEvent(UIMainRecyActivity.this, "ItemClickIn");
				mClickItem = position;
				TimeItemEntity timeItemEntity = (TimeItemEntity) datas.get(position);
				CommonUtils.getInstance(mContext).startAddTaskActivity(UIMainRecyActivity.this, timeItemEntity);
			}
		});

	}

    private void addTimeTask() {
        String title = mEditText.getText().toString();
        if("".equals(title)){
            Intent intent = new Intent();
            intent.setClass(UIMainRecyActivity.this, UIAddTaskActivity.class);
            startActivity(intent);
			MobclickAgent.onEvent(this, "AddNew");
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
			datas.add(timeItemEntity);
			mAdapter.notifyDataSetChanged();
            mEditText.setText("");
            id++;
            LogPrint.Debug("jayden, add new, lastId ==>> id = " + id);
            CommonUtils.getInstance(mContext).setId(id);
			MobclickAgent.onEvent(this, "AddOnce");
        }
    }

    /**
     * 1. 接收从UIAddTaskActivity传过来的EventBus。
     * */
    public void onEventMainThread(TimeItemEntity timeItemEntity) {
        boolean isAdd = timeItemEntity.getAddFlag();
        LogPrint.Debug("onEventMainThread...timeItemEntity = " + timeItemEntity.toString() + "; isAdd = " + isAdd);
        if(isAdd){
			datas.add(timeItemEntity);
			mAdapter.notifyDataSetChanged();
        } else {
			datas.set(mClickItem, timeItemEntity);
			mAdapter.notifyItemChanged(mClickItem);
        }
    }

	private void swapItemData(List<Entity> list, int src, int dest) {
		MobclickAgent.onEvent(this, "SwapItem");
		Collections.swap(list, src, dest);
		//Swap database and save.
		TimeItemEntity srcEntity = (TimeItemEntity) list.get(src);
		TimeItemEntity destEntity = (TimeItemEntity) list.get(dest);
		TimeItemEntity tmpEntity = destEntity;
//		LogPrint.Debug("before src = " + srcEntity.toString() + "; tmp = " + tmpEntity.toString() + ";dest = " + destEntity.toString());
		long destId = destEntity.getId();
		long srcId = srcEntity.getId();
//		LogPrint.Debug("destId = " + destId);
		destEntity = srcEntity;  // This will change srcEntity Id to destEntity Id, why ?
		destEntity.setId(destId);
//		LogPrint.Debug("srcId = " + srcId);
		srcEntity = tmpEntity;
		srcEntity.setId(srcId);
//		LogPrint.Debug("after src = " + srcEntity.toString() + "; tmp = " + tmpEntity.toString() + ";dest = " + destEntity.toString());
		CommonUtils.getInstance(mContext).saveTimeStruct(srcEntity);
		CommonUtils.getInstance(mContext).saveTimeStruct(destEntity);
	}

	/**
	 * 反射的方法让它在displayWidthPercent的情况下, 也可以左滑拉出来.
	 * 由于此方法会导致长按也可以呼唤出来, 所以不能设置太大.
	 * 参考http://tieba.baidu.com/p/3125135645
	 * */
	private void setDrawerLeftEdgeSize(DrawerLayout drawerLayout, float displayWidthPercent) {
		if(mContext == null || drawerLayout == null) {
			return;
		}
		try {
			Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
			leftDraggerField.setAccessible(true);
			ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
			Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
			edgeSizeField.setAccessible(true);
			int edgeSize = edgeSizeField.getInt(leftDragger);
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
					(int)(dm.widthPixels * displayWidthPercent)));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
