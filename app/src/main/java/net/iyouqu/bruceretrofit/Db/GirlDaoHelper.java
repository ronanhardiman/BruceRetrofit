package net.iyouqu.bruceretrofit.Db;

import android.content.Context;

import net.iyouqu.bruceretrofit.Bean.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2015/9/28.
 */
public class GirlDaoHelper {
	private static GirlDaoHelper girlDaoHelper;
	private Context mContext;
	private GirlDb mGirlDb;
	private GirlDaoHelper(Context mContext){
		this.mContext = mContext;
		this.mGirlDb = GirlDb.getInstance(mContext);
	}

	public synchronized static GirlDaoHelper getInstance(Context mContext) {
		if (girlDaoHelper == null) {
			girlDaoHelper = new GirlDaoHelper(mContext);
		}
		return girlDaoHelper;
	}

	public synchronized boolean addGirl(Girl girl) {
		if (girl == null) {
			return false;
		}
		if (mGirlDb.addGirl(girl)) {
			return true;
		}
		return false;
	}

	public synchronized boolean addGirlList(List<Girl> girlList) {
		if (girlList == null && !girlList.isEmpty()) {
			return false;
		}
		if (mGirlDb.addGirlList(girlList)) {
			return true;
		}
		return false;
	}

	public synchronized ArrayList<Girl> getGirlList() {
		return mGirlDb.getGirlList();
	}

}
