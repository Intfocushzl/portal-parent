package com.yonghui.portal.mapper.api;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.api.TokenApi;


/**
 * 用户Token
 * 
 */
public interface TokenApiMapper extends BaseMapper<TokenApi> {

    TokenApi queryByJobNumber(String jobNumber);

    TokenApi queryByToken(String token);

    int deleteByJobNumber(String jobNumber);

    int deleteByToken(String token);
	
}
