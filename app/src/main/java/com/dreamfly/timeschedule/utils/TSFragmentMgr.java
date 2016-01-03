package com.dreamfly.timeschedule.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.dreamfly.timeschedule.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jayden on 16-01-01.
 */
public class TSFragmentMgr {
	private static volatile TSFragmentMgr instance = null;
	private Context mContext;
	private FragmentManager mFragmentManager;
	private List<Bundle> bundles = new ArrayList<Bundle>();
	private Map<String, Fragment> fragmentMap = new HashMap<String, Fragment>();

	private TSFragmentMgr() {

	}

	public static TSFragmentMgr getInstance() {
		if(instance == null) {
			synchronized (TSFragmentMgr.class) {
				if(instance == null) {
					instance = new TSFragmentMgr();
				}
			}
		}
		return instance;
	}

	public void init(Context context, FragmentManager fm) {
		mContext = context;
		mFragmentManager = fm;
	}

	public void showFragment(Class<? extends Fragment> clazz) {
		showFragment(clazz, null);
	}

	public void showFragment(Class<? extends Fragment> clazz, String menuId) {
		showFragment(clazz, menuId, null);
	}

	public void showFragment(Class<? extends Fragment> clazz, String menuId, Bundle args) {
		if(args == null) {
			args = new Bundle();
		}
		String tag;
		if(menuId != null) {
			args.putString("menuId", menuId);
			tag = menuId;
		} else {
			args.putString("fragmentId", clazz.getSimpleName());
			tag = clazz.getSimpleName();
		}
		args.putString("tag", tag);
		Fragment fragment = Fragment.instantiate(mContext, clazz.getName(), args);
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.container, fragment, tag);
		transaction.addToBackStack(menuId);
		transaction.commit();
		Bundle tmpBundle = new Bundle(args);
		bundles.add(tmpBundle);
	}

	public void backToPrevFragment(Bundle args) {
		int count = mFragmentManager.getBackStackEntryCount();
		if(count > 1 && bundles.size() > 1) {
			Bundle topBundle = bundles.remove(bundles.size() - 1);
			if(args != null) {
				bundles.get(bundles.size() - 1).putAll(args);
			}
			FragmentTransaction transaction = mFragmentManager.beginTransaction();
			String tag = topBundle.getString("tag");
			Fragment fragment = mFragmentManager.findFragmentByTag(tag);
			transaction.remove(fragment);
			transaction.commit();
			FragmentManager.BackStackEntry lastEntry = mFragmentManager.getBackStackEntryAt(count - 1);
			mFragmentManager.popBackStack(lastEntry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} else if(count == 1) {
			try{
				((Activity) mContext).finish();
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public Bundle getCurFragmentArgs() {
		if(bundles.size() > 0) {
			return bundles.get(bundles.size() - 1);
		} else {
			return null;
		}
	}

	public int getBackStackEntryCount() {
		return mFragmentManager.getBackStackEntryCount();
	}

	public Fragment getFragment(String tag) {
		return fragmentMap.get(tag);
	}

	public void putFragment(String tag, Fragment fragment) {
		fragmentMap.put(tag, fragment);
	}

}
