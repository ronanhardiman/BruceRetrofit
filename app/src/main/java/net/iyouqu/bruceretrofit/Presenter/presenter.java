package net.iyouqu.bruceretrofit.Presenter;

import net.iyouqu.bruceretrofit.network.BruceApi;
import net.iyouqu.bruceretrofit.network.BruceFactory;
import net.iyouqu.bruceretrofit.ui.view.BaseView;

/**
 * Created by q on 2016/1/9.
 */
public class Presenter<V extends BaseView> {
	protected V mView;
	public static final BruceApi mBRUCE_API = BruceFactory.getSingLeton();

	public Presenter(V mView) {
		this.mView = mView;
	}
}
