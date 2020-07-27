package life.juanjuan.community.provider;

import com.alibaba.fastjson.JSON;
import life.juanjuan.community.dto.AccessTokenDTO;
import life.juanjuan.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();//access_token=5b8924060f3f23bcbeef0e577073f859871b5687&scope=user&token_type=bearer
            //拆分string，用来获得token
//            String[] split = string.split("&");
//            String tokenstr = split[0];
//            String token = tokenstr.split("=")[1];

            //上面代码的改进
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把String的json对象自动转换解析成java的类对象，这样就不要自己去解析string
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }
}
