package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.EmailService;
import co.yiiu.pybbs.mapper.CodeMapper;
import co.yiiu.pybbs.model.Code;
import co.yiiu.pybbs.util.DateUtil;
import co.yiiu.pybbs.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class CodeService {

  @Autowired
  private CodeMapper codeMapper;
  @Autowired
  private EmailService emailService;

  // 递归生成code，防止code重复
  private String generateToken() {
    String _code = StringUtil.randomString(6);
    Code code = this.selectByCode(_code);
    if (code != null) {
      return this.generateToken();
    }
    return _code;
  }

  public Code selectByCode(String _code) {
    QueryWrapper<Code> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Code::getCode, _code);
    return codeMapper.selectOne(wrapper);
  }

  // 查询没有用过的code
  public Code selectNotUsedCode(Integer userId, String email) {
    QueryWrapper<Code> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Code::getEmail, email)
        .eq(Code::getUserId, userId)
        .eq(Code::getUsed, false)
        .gt(Code::getExpireTime, new Date());
    return codeMapper.selectOne(wrapper);
  }

  // 创建一条验证码记录
  public Code createCode(Integer userId, String email) {
    Code code = this.selectNotUsedCode(userId, email);
    if (code == null) {
      code = new Code();
      String _code = generateToken();
      code.setUserId(userId);
      code.setCode(_code);
      code.setEmail(email);
      code.setInTime(new Date());
      code.setExpireTime(DateUtil.getMinuteAfter(new Date(), 30));
      codeMapper.insert(code);
    }
    return code;
  }

  // 验证邮箱验证码
  public Code validateCode(Integer userId, String email, String _code) {
    QueryWrapper<Code> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Code::getCode, _code)
        .eq(Code::getEmail, email)
        .eq(Code::getUserId, userId)
        .eq(Code::getUsed, false)
        .gt(Code::getExpireTime, new Date());
    return codeMapper.selectOne(wrapper);
  }

  // 发送邮件
  public boolean sendEmail(Integer userId, String email) {
    Code code = this.createCode(userId, email);
    return emailService.sendEmail(email, "修改邮箱验证码", "你的验证码是：" + code.getCode() + "<br>请在30分钟内使用");
  }

  public void update(Code code) {
    codeMapper.updateById(code);
  }
}
