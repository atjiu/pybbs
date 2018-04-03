package co.yiiu.module.attachment.repository;

import co.yiiu.module.attachment.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya at 2018/1/24
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

  Attachment findByMd5(String md5);
}
