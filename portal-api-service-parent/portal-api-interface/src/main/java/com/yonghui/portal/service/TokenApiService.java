package com.yonghui.portal.service;


import com.yonghui.portal.model.api.TokenApi;

/**
 * 用户Token
 * 
 */
public interface TokenApiService {

	/**
	 * 根据工号查询
	 * @param jobNumber
	 * @return
	 */
	TokenApi queryByJobNumber(String jobNumber);

	/**
	 * 根据token值查询
	 * @param token
	 * @return
	 */
	TokenApi queryByToken(String token);

	/**
	 * 保存
	 * @param token
	 */
	void save(TokenApi token);

	/**
	 * 更新
	 * @param token
	 */
	void update(TokenApi token);

	/**
	 * 根据工号删除
	 * @param jobNumber
	 * @return
	 */
	int deleteByJobNumber(String jobNumber);

	/**
	 * 根据token删除
	 * @param token
	 * @return
	 */
	int deleteByToken(String token);

}
