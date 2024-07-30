package co.yiiu.pybbs.config.websocket;

import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.Message;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(Message o) {
        return JsonUtil.objectToJson(o);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
