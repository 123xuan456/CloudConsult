package com.mtm.cloudconsult.app.callback;

import com.kingja.loadsir.callback.Callback;
import com.mtm.cloudconsult.R;


/**
 *
 */

public class ErrorCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading_error;
    }

}
