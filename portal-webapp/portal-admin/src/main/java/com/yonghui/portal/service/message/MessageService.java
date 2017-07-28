package com.yonghui.portal.service.message;


import org.quartz.Scheduler;

import com.yonghui.portal.model.message.IntfocusMessage;

public interface MessageService {

	boolean add(IntfocusMessage message , Scheduler scheduler);
	
	boolean modify(IntfocusMessage message , Scheduler scheduler);
	
	boolean delete(IntfocusMessage message , Scheduler scheduler);
	
}
