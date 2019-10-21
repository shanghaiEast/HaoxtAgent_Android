package tft.mpos.library.manager;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tft.mpos.library.base.BaseApplication;
import tft.mpos.library.util.StringUtil;

public class TokenHeaderInterceptor implements Interceptor {


    private Context context;
    private  String token;
    private static TokenHeaderInterceptor instance;// 单例
    public static TokenHeaderInterceptor getInstance(String token) {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new TokenHeaderInterceptor(BaseApplication.getInstance(),token);
                }
            }
        }
        return instance;
    }

    public TokenHeaderInterceptor(Application context, String token) {
        this.context = context;
        this.token  = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (StringUtil.isEmpty(token)) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }else {
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest.newBuilder().header("token", token).build();
            return chain.proceed(updateRequest);
        }
    }
}
