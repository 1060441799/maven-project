package com.newframe.core.dao;

import com.newframe.core.pojo.pojoimpl.impl.Depart;
import com.newframe.core.web.model.common.UploadFile;
import com.newframe.core.web.model.json.ComboTree;
import com.newframe.core.web.model.json.ImportFile;
import com.newframe.core.extend.template.Template;
import com.newframe.core.pojo.pojoimpl.impl.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ICommonDao extends IGenericBaseCommonDao {
	public void pwdInit(User user, String newPwd);

	public User getUserByUserIdAndUserNameExits(User user);

	public String getUserRole(User user);

	public <T> T uploadFile(UploadFile uploadFile);

	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object, Object> getDataSourceMap(Template template);

	public HttpServletResponse createXml(ImportFile importFile);

	public void parserXml(String fileName);

	public List<ComboTree> comTree(List<Depart> all, ComboTree comboTree);

	public User getUserByUserName(User user);
}
