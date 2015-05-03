package com.coderpage.weather.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coderpage.weather.R;
import com.coderpage.weather.adapter.SlidingDrawerAdapter;
import com.coderpage.weather.data.Cities;

@SuppressWarnings("deprecation")
public class SlidingDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	private NavigationDrawerCallbacks mCallbacks;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 1;

	private static SlidingDrawerAdapter mAdapter;

	public SlidingDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
		}

		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 在actionbar上设置按钮响应
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.fragment_drawer, container, false);
        mDrawerListView = (ListView) contentView.findViewById(R.id.drawer_lv);
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});
		mAdapter = new SlidingDrawerAdapter(getActivity(), Cities.mCities);
		mDrawerListView.setAdapter(mAdapter);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return contentView;
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}

    public void open(){
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }


	public static void updateListView() {
		mAdapter.notifyDataSetChanged();
	}
}
