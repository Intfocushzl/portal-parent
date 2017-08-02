package com.yonghui.portal.service.platform;

import com.yonghui.portal.model.platform.BravoShop;

import java.util.List;
import java.util.Map;

/**
 * 绿标门店
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-26 17:02:43
 */
public interface BravoShopService {

	BravoShop queryObject(String shopid);
	
	List<BravoShop> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BravoShop bravoShop);
	
	void update(BravoShop bravoShop);
	
	void delete(String shopid);
	
	void deleteBatch(String[] shopids);
}
