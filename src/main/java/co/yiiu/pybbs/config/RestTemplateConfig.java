package co.yiiu.pybbs.config;

import co.yiiu.pybbs.service.ISystemConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Component
@DependsOn("mybatisPlusConfig")
public class RestTemplateConfig {

    private String proxyHost;
    private Integer proxyPort;

    @Resource
    private ISystemConfigService systemConfigService;

    @PostConstruct
    public void init() {
        proxyHost = systemConfigService.selectAllConfig().get("http_proxy");
        String http_proxy_port = systemConfigService.selectAllConfig().get("http_proxy_port");
        if (!StringUtils.isEmpty(http_proxy_port)) proxyPort = Integer.parseInt(http_proxy_port);
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        if (!StringUtils.isEmpty(proxyHost) && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            requestFactory.setProxy(proxy);
        }

        return new RestTemplate(requestFactory);
    }

}