package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.api.TokenApiMapper;
import com.yonghui.portal.model.api.TokenApi;
import com.yonghui.portal.service.TokenApiService;
import org.springframework.beans.factory.annotation.Autowired;


public class TokenApiServiceImpl implements TokenApiService {
    @Autowired
    private TokenApiMapper tokenApiMapper;

    @Override
    public TokenApi queryByJobNumber(String jobNumber) {
        return tokenApiMapper.queryByJobNumber(jobNumber);
    }

    @Override
    public TokenApi queryByToken(String token) {
        return tokenApiMapper.queryByToken(token);
    }

    @Override
    public void save(TokenApi token) {
        tokenApiMapper.save(token);
    }

    @Override
    public void update(TokenApi token) {
        tokenApiMapper.update(token);
    }

    @Override
    public int deleteByJobNumber(String jobNumber) {
        return tokenApiMapper.deleteByJobNumber(jobNumber);
    }

    @Override
    public int deleteByToken(String token) {
        return tokenApiMapper.deleteByToken(token);
    }
}
