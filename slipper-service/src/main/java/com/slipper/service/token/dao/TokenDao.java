package com.slipper.service.token.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slipper.service.token.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {

    TokenEntity queryByToken (@Param("token") String token);

    TokenEntity queryByUserId (@Param("userId") Long userId);

}
