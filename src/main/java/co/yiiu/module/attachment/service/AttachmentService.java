package co.yiiu.module.attachment.service;

import co.yiiu.module.attachment.mapper.AttachmentMapper;
import co.yiiu.module.attachment.pojo.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tomoya at 2018/1/24
 */
@Service
@Transactional
public class AttachmentService {

  @Autowired
  private AttachmentMapper attachmentMapper;

  public void save(Attachment attachment) {
    attachmentMapper.insert(attachment);
  }

  public Attachment createAttachment(String localPath, String fileName, String requestUrl, String fileType,
                                     Integer width, Integer height, String size, String suffix, String md5) {
    Attachment attachment = new Attachment();
    attachment.setLocalPath(localPath);
    attachment.setFileName(fileName);
    attachment.setInTime(new Date());
    attachment.setRequestUrl(requestUrl);
    attachment.setType(fileType);
    attachment.setWidth(width);
    attachment.setHeight(height);
    attachment.setSize(size);
    attachment.setSuffix(suffix);
    attachment.setMd5(md5);
    this.save(attachment);
    return attachment;
  }

  public Attachment findByMd5(String md5) {
    return attachmentMapper.findByMd5(md5);
  }
}
