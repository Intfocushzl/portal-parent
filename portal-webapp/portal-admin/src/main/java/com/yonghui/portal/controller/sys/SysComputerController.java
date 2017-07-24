package com.yonghui.portal.controller.sys;

import com.yonghui.portal.controller.AbstractController;
import com.yonghui.portal.model.sys.SysCompterEntity;
import com.yonghui.portal.util.GetComputer;
import com.yonghui.portal.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息
 */
@RestController
@RequestMapping("/sys/computer")
public class SysComputerController extends AbstractController {

    /**
     * 配置信息
     */
    @RequestMapping("/info")
    public R info() {
        SysCompterEntity computerInfo = new SysCompterEntity();
        computerInfo.setSysTotalPhysicalMemorySize(GetComputer.getTotalPhysicalMemorySize());
        computerInfo.setSysFreePhysicalMemorySize(GetComputer.getFreePhysicalMemorySize());
        computerInfo.setAvailableProcessors(GetComputer.getAvailableProcessors());

        computerInfo.setMaxControl(GetComputer.getMaxControl());
        computerInfo.setCurrentUse(GetComputer.getCurrentUse());
        computerInfo.setFreeMemory(GetComputer.getFreeMemory());
        return R.success().put("computerInfo", computerInfo).put("properties", GetComputer.getProperties());
    }

}
