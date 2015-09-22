package xyz.venkateshrao.newapplicatoin;

import com.shipdream.lib.android.mvc.view.MvcActivity;
import com.shipdream.lib.android.mvc.view.MvcFragment;

/**
 * Created by venkatesh on 22/9/15.
 */
public class MyActivity extends MvcActivity {
    @Override
    protected Class<? extends MvcFragment> mapNavigationFragment(String locationId) {
        return null;
    }

    @Override
    protected Class<? extends DelegateFragment> getDelegateFragmentClass() {
        return null;
    }
}
