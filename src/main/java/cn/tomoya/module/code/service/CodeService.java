package cn.tomoya.module.code.service;

import cn.tomoya.module.code.dao.CodeDao;
import cn.tomoya.module.code.entity.Code;
import cn.tomoya.module.code.entity.CodeEnum;
import cn.tomoya.util.DateUtil;
import cn.tomoya.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by tomoya on 17-6-6.
 */
@Service
@Transactional
public class CodeService {

  @Autowired
  private CodeDao codeDao;

  public Code findByCodeAndType(String code, CodeEnum type) {
    List<Code> codes = codeDao.findByCodeAndType(code, type.name());
    if(codes.size() > 0) return codes.get(0);
    return null;
  }

  public void save(Code code) {
    codeDao.save(code);
  }

  public String genEmailCode() {
    String genCode = StrUtil.randomString(6);
    Code code = findByCodeAndType(genCode, CodeEnum.EMAIL);
    if(code != null) {
      return genEmailCode();
    } else {
      code = new Code();
      code.setCode(genCode);
      code.setExpireTime(DateUtil.getMinuteAfter(new Date(), 10));
      code.setType(CodeEnum.EMAIL.name());
      code.setUsed(false);
      save(code);
      return genCode;
    }
  }

  public int validateCode(String code, CodeEnum type) {
    Code code1 = findByCodeAndType(code, type);
    if(code1 == null) return 1;// 验证码不正确
    if(DateUtil.isExpire(code1.getExpireTime())) return 2; // 过期了
    code1.setUsed(true);
    save(code1);
    return 0; // 正常
  }
}
