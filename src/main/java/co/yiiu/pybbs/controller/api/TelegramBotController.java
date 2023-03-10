package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.config.service.TelegramBotService;
import co.yiiu.pybbs.model.Comment;
import co.yiiu.pybbs.service.ICommentService;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/bot/tg")
public class TelegramBotController extends BaseApiController {

    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private ICommentService commentService;
    @Resource
    private TelegramBotService telegramBotService;

    @PostMapping("/webhook")
    @ResponseBody
    public Object webhook(HttpServletRequest request) throws IOException {
        String secretToken = request.getHeader("X-Telegram-Bot-Api-Secret-Token");
        if (StringUtils.isEmpty(secretToken)) return "False";

        String webhookSecretToken = systemConfigService.selectAllConfig().get("tg_webhook_secret_token");
        if (StringUtils.isEmpty(webhookSecretToken)) return "False";
        if (!secretToken.equals(webhookSecretToken)) return "False";

        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String buff;
        while ((buff = reader.readLine()) != null) {
            sb.append(buff);
        }
        Map map = JsonUtil.jsonToObject(sb.toString(), Map.class);
        Map callback_query = (Map) map.get("callback_query");
        if (callback_query != null) {
            Map message = (Map) callback_query.get("message");
            Integer message_id = (Integer) message.get("message_id");
            String data = (String) callback_query.get("data");

            Comment comment = null;
            if (message_id != null) {
                comment = commentService.selectByTgMessageId(message_id);
            }
            if (data.equals("del_btn")) {
                if (comment != null) {
                    commentService.delete(comment);
                    telegramBotService.init().sendMessage("已删除", false, comment.getTgMessageId());
                }
            } else if (data.equals("pass_btn")) {
                if (comment != null && comment.getStatus() != null && !comment.getStatus() && systemConfigService.selectAllConfig().get("comment_need_examine").equals("1")) {
                    comment.setStatus(true);
                    commentService.update(comment);
                    telegramBotService.init().sendMessage("审核通过", false, comment.getTgMessageId());
                }
            }
        }
        return "True";
    }

}
