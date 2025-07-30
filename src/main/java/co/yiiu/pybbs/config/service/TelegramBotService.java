package co.yiiu.pybbs.config.service;

import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@DependsOn("mybatisPlusConfig")
public class TelegramBotService {

    private Logger log = LoggerFactory.getLogger(TelegramBotService.class);

    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    @Lazy
    private RestTemplate restTemplate;

    private String accessToken;
    private String webhookSecretToken;
    private String chatId;
    private String contentStyle;

    private TelegramBotService() {
    }

    public TelegramBotService init() {
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = systemConfigService.selectAllConfig().get("tg_access_token");
        }
        if (StringUtils.isEmpty(webhookSecretToken)) {
            webhookSecretToken = systemConfigService.selectAllConfig().get("tg_webhook_secret_token");
        }
        if (StringUtils.isEmpty(chatId)) {
            chatId = systemConfigService.selectAllConfig().get("tg_to_chat_id");
        }
        if (StringUtils.isEmpty(contentStyle)) {
            contentStyle = systemConfigService.selectAllConfig().get("content_style");
        }
        return this;
    }

    private String getStyle() {
        if (!StringUtils.isEmpty(contentStyle) && contentStyle.equals("RICH")) return "HTML";
        return "MarkdownV2";
    }

    public Integer sendMessage(String message, boolean btn, Integer reply_to_message_id) {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(chatId)) {
            log.info("Telegram Bot未启用或配置有误，跳过！！");
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=utf-8");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("chat_id", chatId);
        dataMap.put("text", message);
        dataMap.put("parse_mode", getStyle());
        if (reply_to_message_id != null) {
            dataMap.put("reply_to_message_id", reply_to_message_id);
        }

        if (btn) {
            List<List<Map<String, Object>>> inline_keyboard = new ArrayList<>();
            List<Map<String, Object>> inline_keyboard2 = new ArrayList<>();

            if (systemConfigService.selectAllConfig().get("comment_need_examine").equals("1")) {
                Map<String, Object> passBtnMap = new HashMap<>();
                passBtnMap.put("text", "过");
                passBtnMap.put("callback_data", "pass_btn");
                inline_keyboard2.add(passBtnMap);
            }

            Map<String, Object> delBtnMap = new HashMap<>();
            delBtnMap.put("text", "删");
            delBtnMap.put("callback_data", "del_btn");
            inline_keyboard2.add(delBtnMap);

            inline_keyboard.add(inline_keyboard2);

            Map<String, Object> reply_markup = new HashMap<>();
            reply_markup.put("inline_keyboard", inline_keyboard);
            dataMap.put("reply_markup", reply_markup);
        }

        HttpEntity<String> entity = new HttpEntity<>(JsonUtil.objectToJson(dataMap), headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://api.telegram.org/bot" + accessToken + "/sendMessage", HttpMethod.POST, entity, String.class);
        if (exchange.getStatusCodeValue() != 200) {
            log.error("发送TG通知失败: {}", exchange.getStatusCodeValue());
        } else {
            Map map1 = JsonUtil.jsonToObject(exchange.getBody(), Map.class);
            return (Integer) ((Map) map1.get("result")).get("message_id");
        }
        return null;
    }

    public void setWebHook(Map<String, String> map) {
        String baseUrl = map.get("base_url");
        String secretToken = map.get("tg_webhook_secret_token");
        String token = map.get("tg_access_token");
        if (!StringUtils.isEmpty(baseUrl) && !StringUtils.isEmpty(secretToken) && !StringUtils.isEmpty(token)) {
            if (secretToken.equals("*******")) {
                secretToken = systemConfigService.selectAllConfig().get("tg_webhook_secret_token");
            }
            if (token.equals("*******")) {
                token = systemConfigService.selectAllConfig().get("tg_access_token");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("url", baseUrl + "/bot/tg/webhook");
            data.add("secret_token", secretToken);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(data, headers);

            ResponseEntity<String> exchange = restTemplate.exchange("https://api.telegram.org/bot" + token + "/setWebhook", HttpMethod.POST, entity, String.class);
            if (exchange.getStatusCodeValue() != 200) {
                log.info("Telegram setWebhook失败，errorCode: {}", exchange.getStatusCode());
            } else {
                log.info("Telegram setWebhook成功");
            }
        } else {
            log.info("Telegram未配置，跳过setWebhook");
        }
    }
}
