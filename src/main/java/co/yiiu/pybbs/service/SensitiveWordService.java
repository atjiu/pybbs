package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.SensitiveWordMapper;
import co.yiiu.pybbs.model.SensitiveWord;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class SensitiveWordService {

  @Autowired
  private SensitiveWordMapper sensitiveWordMapper;

  public void save(SensitiveWord sensitiveWord) {
    sensitiveWordMapper.insert(sensitiveWord);
  }

  public void update(SensitiveWord sensitiveWord) {
    sensitiveWordMapper.updateById(sensitiveWord);
  }

  public List<SensitiveWord> selectAll() {
    return sensitiveWordMapper.selectList(null);
  }

  public void deleteById(Integer id) {
    sensitiveWordMapper.deleteById(id);
  }
}
