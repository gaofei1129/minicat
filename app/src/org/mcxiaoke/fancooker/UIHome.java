package org.mcxiaoke.fancooker;

import org.mcxiaoke.fancooker.adapter.HomePagesAdapter;
import org.mcxiaoke.fancooker.controller.UIController;
import org.mcxiaoke.fancooker.fragments.ConversationListFragment;
import org.mcxiaoke.fancooker.fragments.ProfileFragment;
import org.mcxiaoke.fancooker.menu.MenuCallback;
import org.mcxiaoke.fancooker.menu.MenuFragment;
import org.mcxiaoke.fancooker.menu.MenuItemResource;
import org.mcxiaoke.fancooker.util.NetworkHelper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.slidingmenu.lib.SlidingMenu;

/**
 * @author mcxiaoke
 * 
 */
public class UIHome extends UIBaseSlidingSupport implements MenuCallback,
		OnPageChangeListener {

	public static final String TAG = UIHome.class.getSimpleName();

	private ViewGroup mContainer;
	private Fragment mMenuFragment;

	private ViewPager mViewPager;
	private PagerTabStrip mPagerTabStrip;
	private HomePagesAdapter mPagesAdapter;

	private void log(String message) {
		Log.d(TAG, message);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (AppContext.DEBUG) {
			log("onCreate()");
		}
		setLayout();
	}

	@Override
	protected void onMenuHomeClick() {
		onBackPressed();
		getSlidingMenu().showContent();
	}

	protected void setLayout() {
		setContentView(R.layout.content_frame);
		mContainer = (ViewGroup) findViewById(R.id.content_frame);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setOnPageChangeListener(this);
		mPagerTabStrip = (PagerTabStrip) findViewById(R.id.viewpager_strip);
		mPagerTabStrip.setDrawFullUnderline(false);
		mPagerTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.light_blue));
		mPagerTabStrip.setTextColor(Color.WHITE);
		mPagesAdapter = new HomePagesAdapter(getFragmentManager());
		mViewPager.setAdapter(mPagesAdapter);

		setSlidingMenu(R.layout.menu_frame);
		FragmentManager fm = getFragmentManager();
		mMenuFragment = MenuFragment.newInstance();
		fm.beginTransaction().replace(R.id.menu_frame, mMenuFragment).commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!NetworkHelper.isWifi(this)) {
			AppContext.getImageLoader().clearQueue();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppContext.getImageLoader().shutdown();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected int getMenuResourceId() {
		return R.menu.home_menu;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
	}

	private void replaceFramgnt(Fragment fragment) {
		log("fragment=" + fragment);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack(null);
		ft.commit();
		getSlidingMenu().toggle();
	}

	private void showProfileFragment() {
		replaceFramgnt(ProfileFragment.newInstance(AppContext.getAccount()));
	}

	private void showMessageFragment() {
		replaceFramgnt(ConversationListFragment.newInstance(false));
	}

	@Override
	public void onMenuItemSelected(int position, MenuItemResource menuItem) {
		log("onMenuItemSelected: " + menuItem);
		int id = menuItem.getId();
		switch (id) {
		case MenuFragment.MENU_ID_HOME:
			UIController.showHome(this);
			break;
		case MenuFragment.MENU_ID_PROFILE:
			showProfileFragment();
			break;
		case MenuFragment.MENU_ID_MESSAGE:
			showMessageFragment();
			break;
		case MenuFragment.MENU_ID_TOPIC:
			UIController.showTopic(this);
			break;
		case MenuFragment.MENU_ID_RECORD:
			UIController.showRecords(this);
			break;
		case MenuFragment.MENU_ID_DIGEST:
			UIController.showFanfouBlog(this);
			break;
		case MenuFragment.MENU_ID_THEME:
			break;
		case MenuFragment.MENU_ID_OPTION:
			UIController.showOption(this);
			break;
		case MenuFragment.MENU_ID_ABOUT:
			UIController.showAbout(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int page) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int page) {
		if (page == 0) {
			setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

}