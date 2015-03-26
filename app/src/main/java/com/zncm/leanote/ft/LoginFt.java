package com.zncm.leanote.ft;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.data.User;
import com.zncm.leanote.services.ServiceFactory;
import com.zncm.leanote.services.UserService;
import com.zncm.leanote.ui.MyAc;
import com.zncm.leanote.utils.MySp;
import com.zncm.leanote.utils.XUtil;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by MX on 3/11 0011.
 */
public class LoginFt extends BaseFt {
    private Button btnLogin, btnReg;
    private EditText etUserName, etUserPWD;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ctx = getActivity();
        view = inflater.inflate(R.layout.login_ft, null);
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etUserPWD = (EditText) view.findViewById(R.id.etUserPWD);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnReg = (Button) view.findViewById(R.id.btnReg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XUtil.tShort("注册[暂不可用],请到官网[http://leanote.com/]注册!");
            }
        });
        return view;


    }

    private void login() {
        String email = etUserName.getText().toString().trim();
        String password = etUserPWD.getText().toString().trim();
        ServiceFactory.getService(UserService.class).login(email, password, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                MyApplication.getInstance().user = user;
                MySp.setUserInfo(user.toString());
                XUtil.debug("==>" + user);
                Intent intent = new Intent(ctx, MyAc.class);
                startActivity(intent);
                ctx.finish();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
