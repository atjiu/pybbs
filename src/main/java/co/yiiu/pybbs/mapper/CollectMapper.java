package co.yiiu.pybbs.mapper;

import co.yiiu.pybbs.model.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface CollectMapper extends BaseMapper<Collect> {

  IPage<Map<String, Object>> selectByUserId(IPage<Map<String, Object>> iPage, @Param("userId") Integer userId);
}
