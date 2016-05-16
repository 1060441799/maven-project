package com.newframe.web.service.impl;

import com.newframe.core.service.impl.CommonServiceImpl;
import com.newframe.web.service.DeclareService;
import java.util.List;

import com.newframe.core.pojo.pojoimpl.impl.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("declareService")
@Transactional
public class DeclareServiceImpl extends CommonServiceImpl implements DeclareService {

	public List<Attachment> getAttachmentsByCode(String businessKey, String description) {
		String hql = "from TSAttachment t where t.businessKey='" + businessKey + "' and t.description='" + description
				+ "'";
		return commonDao.findByQueryString(hql);
	}

}
