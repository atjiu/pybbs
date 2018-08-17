package co.yiiu.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/8/17
 */
@Component
@Order(1)
public class SocketServerRunner implements CommandLineRunner {

  @Autowired
  private SocketIOServer server;

  @Override
  public void run(String... args) {
    server.start();
  }
}
