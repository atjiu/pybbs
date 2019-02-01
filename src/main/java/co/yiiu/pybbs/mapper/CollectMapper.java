package co.yiiu.pybbs.mapper;

import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface CollectMapper extends BaseMapper<Collect> {

  MyPage<Map<String, Object>> selectByUserId(MyPage<Map<String, Object>> iPage, @Param("userId") Integer userId);
}
