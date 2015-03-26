package com.zncm.leanote.data;

import com.zncm.leanote.utils.XUtil;

/**
 * Created by MX on 3/12 0012.
 */
public class Msg {
    public boolean Ok;
    public String msg;

    public String ret(Msg tmp) {
        String string = tmp.msg;
        if (!XUtil.notEmptyOrNull(string)) {
            string = Key.MSG_SUCCESS;
        }
        if (tmp.Ok && string.equals(Key.MSG_CONFLICT)) {
            string = "冲突";
        }
        return string;
    }


}
