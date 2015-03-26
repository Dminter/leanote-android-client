package com.zncm.leanote.services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zncm.leanote.utils.XUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class RestServiceErrorHandler implements ErrorHandler {

    final static int BUFFER_SIZE = 4096;

    @Override
    public Throwable handleError(RetrofitError cause) {
        String message = cause.getMessage();
        if (message != null && (message.equals("No authentication challenges found") ||
                message.equals("Received authentication challenge is null"))) {
            return new Exception("授权失败");
        }
        Exception e = (Exception) cause.getCause();


        if (e instanceof UnknownHostException) {
            return new Exception("没有网络连接");
        }
        if (e instanceof SocketTimeoutException) {
            return new Exception("请求超时");
        }
        if (e instanceof ConnectException) {
            return new Exception("无法连接服务器");
        }
        Response r = cause.getResponse();
        if (r != null) {
            int code = r.getStatus();
            if (code == 401) {
                return new Exception("授权失败");
            } else {
                TypedInput body = r.getBody();
                if (body != null) {
                    try {
                        InputStream stream = body.in();
                        String json = RestServiceErrorHandler.InputStreamToString(stream);
                        android.util.Log.e("Network Error", json);
                        ServiceError error = getError(json);
                        if (error != null && XUtil.notEmptyOrNull(error.msg)) {
                            XUtil.tShort(error.msg);
                            return new Exception(error.msg);
                        } else {
                            return new Exception("服务器错误");
                        }
                    } catch (IOException ex) {
                        return new Exception("未知错误");
                    }
                }
            }
        }
        if (e instanceof SSLException) {
            return new Exception("网络连接异常");
        }

        XUtil.debug("message: " + message + " \n" + e);

        return new Exception("网络连接异常");
    }

    static Gson gson = new Gson();

    private static ServiceError getError(String json) {
        try {
            return gson.fromJson(json, ServiceError.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }


    public static String InputStreamToString(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "ISO-8859-1");
    }
}
