package co.yiiu.pybbs.plugin;

import co.yiiu.pybbs.service.ISystemConfigService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 社会化登录相关
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/6/25 13:42
 * @since 1.0.0
 */
@Component
public class SocialPlugin {

    @Resource
    private ISystemConfigService systemConfigService;

    /**
     * 获取 JustAuth config
     *
     * @param source 来源平台
     * @return {@link AuthConfig}
     */
    private AuthConfig getConfig(String source) {
        Map config = systemConfigService.selectAllConfig();
        String clientId = (String) config.get("oauth_" + source + "_client_id");
        String clientSecret = (String) config.get("oauth_" + source + "_client_secret");
        String callback = (String) config.get("oauth_" + source + "_callback_url");

        Assert.isTrue(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret) && !StringUtils.isEmpty
                (callback), source + "登录还没有相关配置，联系站长吧！");

        return AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(callback)
                .build();
    }

    /**
     * 获取 JustAuth Request
     *
     * @param source 来源平台
     * @return {@link AuthRequest}
     */
    public AuthRequest getRequest(String source) {
        AuthConfig config = this.getConfig(source);
        AuthRequest authRequest = null;
        switch (source) {
            case "github":
                authRequest = new AuthGithubRequest(config);
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(config);
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(config);
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(config);
                break;
            case "wechat":
                authRequest = new AuthWeChatOpenRequest(config);
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException(source + "登录还没有相关配置，联系站长吧！");
        }
        return authRequest;
    }

    /**
     * 获取所有可用的社会化登录平台
     *
     * @return {@link List}
     */
    public List<String> getAllAvailableSocialPlatform() {
        List<String> res = new LinkedList<>();
        Map<String, String> config = systemConfigService.selectAllConfig();
        Set<Map.Entry<String, String>> entrySet = config.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            if (key.startsWith("oauth_")) {
                String type = key.split("_")[1];
                if (res.contains(type)) {
                    continue;
                }
                Object value = entry.getValue();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                res.add(type);
            }
        }
        return res;
    }
}
