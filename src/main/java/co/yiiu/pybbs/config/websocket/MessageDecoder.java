package co.yiiu.pybbs.config.websocket;

import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.Message;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String s) {
        return JsonUtil.jsonToObject(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        // 验证json字符串是否合法，合法才会进入decode()方法进行转换，不合法直接抛异常
        return JsonUtil.isValid(s);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
