package com.newframe.web.service;

import com.newframe.core.service.CommonService;
import java.util.List;

import com.newframe.core.pojo.pojoimpl.impl.Attachment;

public interface DeclareService extends CommonService {
	
	public List<Attachment> getAttachmentsByCode(String businessKey,String description);
	
}
