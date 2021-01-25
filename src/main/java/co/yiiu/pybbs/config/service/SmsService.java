package co.yiiu.pybbs.config.service;

import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.JsonUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

@Component
@DependsOn("mybatisPlusConfig")
public class SmsService {

    private Logger log = LoggerFactory.getLogger(SmsService.class);

    @Resource
    private ISystemConfigService systemConfigService;

    private IAcsClient client;
    private String signName;
    private String templateCode;
    private String regionId;

    private SmsService() {
    }

    public IAcsClient instance() {
        if (client != null) return client;
        String accessKeyId = (String) systemConfigService.selectAllConfig().get("sms_access_key_id");
        String secret = (String) systemConfigService.selectAllConfig().get("sms_secret");
        signName = (String) systemConfigService.selectAllConfig().get("sms_sign_name");
        templateCode = (String) systemConfigService.selectAllConfig().get("sms_template_code");
        regionId = (String) systemConfigService.selectAllConfig().get("sms_region_id");
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(secret) || StringUtils.isEmpty(signName) ||
                StringUtils.isEmpty(templateCode) || StringUtils.isEmpty(regionId)) {
            return null;
        }
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        this.client = client;
        return client;
    }

    // 发短信
    public boolean sendSms(String mobile, String code) {
        try {
            if (StringUtils.isEmpty(mobile)) return false;
            // 获取连接
            if (this.instance() == null) return false;
            // 构建请求体
            CommonRequest request = new CommonRequest();
            //request.setProtocol(ProtocolType.HTTPS);
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", regionId);
            request.putQueryParameter("PhoneNumbers", mobile);
            request.putQueryParameter("SignName", signName);
            request.putQueryParameter("TemplateCode", templateCode);
            request.putQueryParameter("TemplateParam", String.format("{\"code\": \"%s\"}", code));
            CommonResponse response = client.getCommonResponse(request);
            //{"Message":"OK","RequestId":"93E35E66-B2B2-4D7A-8AC9-2BDD97F5FB18","BizId":"689615750980282428^0","Code":"OK"}
            Map responseMap = JsonUtil.jsonToObject(response.getData(), Map.class);
            if (responseMap.get("Code").equals("OK")) return true;
        } catch (ClientException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
