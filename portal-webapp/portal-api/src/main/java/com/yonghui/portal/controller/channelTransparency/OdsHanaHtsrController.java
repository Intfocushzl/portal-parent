package com.yonghui.portal.controller.channelTransparency;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.mapper.channelTransparency.OdsHanaHtsrMapper;
import com.yonghui.portal.model.channelTransparency.OdsHanaHtsr;
import com.yonghui.portal.service.channelTransparency.MenuService;
import com.yonghui.portal.service.channelTransparency.OdsHanaHtsrService;
import com.yonghui.portal.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/bravo/odsHanaHtsr")
public class OdsHanaHtsrController {

	@Reference
	private OdsHanaHtsrService odsHanaHtsrService;
	@Reference
	private MenuService menuService;
	//费用类型
	@RequestMapping(value="/tlxList")
	public R tlxList(HttpServletResponse reseponse){
		reseponse.setHeader("Access-Control-Allow-Origin", "*");
		return R.success(odsHanaHtsrService.tlxList());
	}

	//店铺列表
	@RequestMapping(value="/listShop")
	public R listShop(HttpServletResponse reseponse){
		reseponse.setHeader("Access-Control-Allow-Origin", "*");
		try {
			return R.success(menuService.getAllBroveStoreList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("程序错误！");
	}

	
	//采购组织集合
	@RequestMapping(value="/purchOrgList")
	public R purchOrgList(HttpServletResponse reseponse){
		reseponse.setHeader("Access-Control-Allow-Origin", "*");
		return R.success(odsHanaHtsrService.purchOrgList());
	}
	
	//采购组集合
	@RequestMapping(value="/purGroupList")
	public R purGroupList(HttpServletResponse reseponse){
		reseponse.setHeader("Access-Control-Allow-Origin", "*");
		return R.success(odsHanaHtsrService.purGourpList());
	}
	
	//通道费数据
	@RequestMapping(value="getAllOdshanaHtsr")
	public R getAllOdshanaHtsr(String tlx,String purchOrg,String purGroup,String plant,String sDate,String eDate,Map<String, Object> model){
		Map<String,Object> map = new HashMap<>();
		purchOrg = purchOrg.substring(1);
		String[] array = purchOrg.split(",");

		String[] arraygroup = purGroup.split(",");

		map.put("tlx", tlx);	
		map.put("purchOrg", array.length==0?"all":array);
		map.put("purGroup", arraygroup.length==0?"all":arraygroup);
		map.put("plant", ("").equals(plant)?"all":plant);
		map.put("sDate", sDate.equals("")?"all":sDate);
		map.put("eDate", eDate.equals("")?"all":eDate);
		List<OdsHanaHtsr> list = odsHanaHtsrService.getAllGalleryCost(map);
		for (OdsHanaHtsr odsHanaHtsr : list) {
			odsHanaHtsr.setTpurchOrg(odsHanaHtsr.getTpurchOrg().substring(2, 4));
			odsHanaHtsr.setTpurGroup(odsHanaHtsr.getTpurGroup().substring(0, odsHanaHtsr.getTpurGroup().length()-1));
			if(odsHanaHtsr.getTplant().indexOf("--")>0){
				odsHanaHtsr.setTplant(odsHanaHtsr.getTplant().substring(odsHanaHtsr.getTplant().indexOf("--")+2));
			}
		}
		model.put("row", list);
		JSONObject json = new JSONObject();
		json.put("rows",list);
		return R.success(list);
	}
	
	//合同号详情
	@RequestMapping(value="/getZopcList")
	public R getZopcList(String zopc,Map<String,Object> model){
		List<OdsHanaHtsr> list = odsHanaHtsrService.getAllZopc(zopc);
		for (OdsHanaHtsr odsHanaHtsr : list) {
			odsHanaHtsr.setTpurchOrg(odsHanaHtsr.getTpurchOrg().substring(2, 4));
			odsHanaHtsr.setTpurGroup(odsHanaHtsr.getTpurGroup().substring(0, odsHanaHtsr.getTpurGroup().length()-1));
			if(odsHanaHtsr.getTplant().indexOf("--")>0){
				odsHanaHtsr.setTplant(odsHanaHtsr.getTplant().substring(odsHanaHtsr.getTplant().indexOf("--")+2));
			}
			//odsHanaHtsr.setGmVldFr(TimeUtil.formatDate(odsHanaHtsr.getGmVldFr(),2));
		}
		JSONObject json = new JSONObject();
		json.put("rows", list);
		return R.success(list);
	}
	
}
