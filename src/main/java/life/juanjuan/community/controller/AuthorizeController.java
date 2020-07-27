package life.juanjuan.community.controller;

import life.juanjuan.community.dto.AccessTokenDTO;
import life.juanjuan.community.dto.GithubUser;
import life.juanjuan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    //登陆成功返回index
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id("ba7fbe720ee2f1777b5b");
        accessTokenDTO.setClient_secret("b69a7fed2fe75ce6bca19d48e4de0532dd174372");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);//小张,表示成功，拿到了用户名
        System.out.println(user.getName());
        return "index";
    }
}
