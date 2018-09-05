package co.yiiu.pybbs.services;

import co.yiiu.pybbs.models.Comment;
import co.yiiu.pybbs.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tomoya at 2018/9/4
 */
@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  public void save(Comment comment) {
    commentRepository.save(comment);
  }

  public List<Comment> findByTopicId(String topicId) {
    Comment comment = new Comment();
    comment.setTopicId(topicId);
    return commentRepository.findAll(Example.of(comment));
  }

  public void deleteByTopicId(String topicId) {
    commentRepository.deleteByTopicId(topicId);
  }

  public void deleteById(String id) {
    commentRepository.deleteById(id);
  }

  public Comment findById(String id) {
    return commentRepository.findById(id).orElse(null);
  }

  public Page<Comment> findByUserId(String userId, Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "inTime");
    Comment comment = new Comment();
    comment.setUserId(userId);
    return commentRepository.findAll(Example.of(comment), pageable);
  }
}
