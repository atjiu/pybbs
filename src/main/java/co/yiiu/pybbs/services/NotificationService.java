package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.Notification;
import co.yiiu.pybbs.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tomoya at 2018/9/14
 */
@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public void save(Notification notification) {
    notificationRepository.save(notification);
  }

  public long countNotRead(String targetUserId) {
    Notification notification = new Notification();
    notification.setRead(false);
    notification.setTargetUserId(targetUserId);
    return notificationRepository.count(Example.of(notification));
  }

  public void create(String topicId, String userId, String targetUserId, String action) {
    // userId 与 targetUserId 相同的话，说明是自己给自己评论、回复、收藏，则不创建通知
    if (!userId.equals(targetUserId)) {
      Notification notification = new Notification();
      notification.setTopicId(topicId);
      notification.setUserId(userId);
      notification.setTargetUserId(targetUserId);
      notification.setAction(action);
      notification.setInTime(new Date());
      notification.setRead(false);
      this.save(notification);
    }
  }

  public Page<Notification> findByUserId(String targetUserId, Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1,  pageSize, Sort.by(Sort.Order.asc("read"), Sort.Order.desc("inTime")));
    Notification notification = new Notification();
    notification.setTargetUserId(targetUserId);
    Page<Notification> page = notificationRepository.findAll(Example.of(notification), pageable);
    this.updateRead(page.getContent());
    return page;
  }

  private void updateRead(List<Notification> notifications) {
    new Thread(()->{
      try {
        Thread.sleep(500);
        notifications.stream()
            .filter(notification1 -> !notification1.getRead())
            .forEach(notification1 -> {
              notification1.setRead(true);
              this.save(notification1);
            });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
