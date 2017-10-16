package co.yiiu.module.code.service;

import co.yiiu.core.util.DateUtil;
import co.yiiu.core.util.StrUtil;
import co.yiiu.module.code.model.Code;
import co.yiiu.module.code.model.CodeEnum;
import co.yiiu.module.code.repository.CodeRepository;
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
  private CodeRepository codeRepository;

  public Code findByCodeAndType(String code, CodeEnum type) {
    List<Code> codes = codeRepository.findByCodeAndType(code, type.name());
    if (codes.size() > 0) return codes.get(0);
    return null;
  }

  public void save(Code code) {
    codeRepository.save(code);
  }

  public String genEmailCode(String email) {
    String genCode = StrUtil.randomString(6);
    Code code = findByCodeAndType(genCode, CodeEnum.EMAIL);
    if (code != null) {
      return genEmailCode(email);
    } else {
      code = new Code();
      code.setCode(genCode);
      code.setExpireTime(DateUtil.getMinuteAfter(new Date(), 10));
      code.setType(CodeEnum.EMAIL.name());
      code.setEmail(email);
      code.setUsed(false);
      save(code);
      return genCode;
    }
  }

  public int validateCode(String code, CodeEnum type) {
    Code code1 = findByCodeAndType(code, type);
    if (code1 == null) return 1;// 验证码不正确
    if (DateUtil.isExpire(code1.getExpireTime())) return 2; // 过期了
    if (code1.isUsed()) return 3; // 验证码已经被使用了
    code1.setUsed(true);
    save(code1);
    return 0; // 正常
  }
}
