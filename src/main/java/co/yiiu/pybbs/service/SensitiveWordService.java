package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.SensitiveWordMapper;
import co.yiiu.pybbs.model.SensitiveWord;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class SensitiveWordService {

  @Autowired
  private SystemConfigService systemConfigService;

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

  // ---------admin------------

  public IPage<SensitiveWord> page(Integer pageNo, String word) {
    IPage<SensitiveWord> iPage = new MyPage<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()));
    QueryWrapper<SensitiveWord> wrapper = new QueryWrapper<>();
    if (!StringUtils.isEmpty(word)) wrapper.lambda().eq(SensitiveWord::getWord, word);
    return sensitiveWordMapper.selectPage(iPage, wrapper);
  }

  public void updateWordById(Integer id, String word) {
    SensitiveWord sensitiveWord = new SensitiveWord();
    sensitiveWord.setId(id);
    sensitiveWord.setWord(word);
    sensitiveWordMapper.updateById(sensitiveWord);
  }

  public SensitiveWord selectByWord(String word) {
    QueryWrapper<SensitiveWord> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(SensitiveWord::getWord, word);
    return sensitiveWordMapper.selectOne(wrapper);
  }
}
