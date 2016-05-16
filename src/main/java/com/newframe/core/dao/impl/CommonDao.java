package com.newframe.core.dao.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.newframe.core.dao.ICommonDao;
import com.newframe.core.dao.IGenericBaseCommonDao;
import com.newframe.core.extend.swftools.SwfToolsUtil;
import com.newframe.core.extend.template.DataSourceMap;
import com.newframe.core.extend.template.Template;
import com.newframe.core.hibernate.qbc.CriteriaQuery;
import com.newframe.core.pojo.pojoimpl.impl.Depart;
import com.newframe.core.pojo.pojoimpl.impl.RelRoleUser;
import com.newframe.core.pojo.pojoimpl.impl.User;
import com.newframe.core.util.DataUtils;
import com.newframe.core.util.FileUtils;
import com.newframe.core.util.GenericsUtils;
import com.newframe.core.util.PasswordUtil;
import com.newframe.core.util.PinyinUtil;
import com.newframe.core.util.ReflectHelper;
import com.newframe.core.util.ResourceUtil;
import com.newframe.core.util.StreamUtils;
import com.newframe.core.util.StringUtil;
import com.newframe.core.util.oConvertUtils;
import com.newframe.core.web.model.common.UploadFile;
import com.newframe.core.web.model.json.ComboTree;
import com.newframe.core.web.model.json.DataGridReturn;
import com.newframe.core.web.model.json.ImportFile;

@Repository(value = "commonDao")
public class CommonDao extends GenericBaseCommonDao implements ICommonDao, IGenericBaseCommonDao {

	public User getUserByUserName(User user) {
		String query = "from User u where u.userName = :username";
		Query queryObject = getCurrentSession().createQuery(query);
		queryObject.setParameter("username", user.getUserName());
		List<User> users = queryObject.list();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public User getUserByUserIdAndUserNameExits(User user) {
		String password = PasswordUtil.encrypt(user.getUserName(), user.getPassword(), PasswordUtil.getStaticSalt());
		String query = "from User u where u.userName = :username and u.password=:passowrd";
		Query queryObject = getCurrentSession().createQuery(query);
		queryObject.setParameter("username", user.getUserName());
		queryObject.setParameter("passowrd", password);
		List<User> users = queryObject.list();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public void pwdInit(User user, String newPwd) {
		String query = "from User u where u.userName = :username ";
		Query queryObject = getCurrentSession().createQuery(query);
		queryObject.setParameter("username", user.getUserName());
		List<User> users = queryObject.list();
		if (null != users && users.size() > 0) {
			user = users.get(0);
			String pwd = PasswordUtil.encrypt(user.getUserName(), newPwd, PasswordUtil.getStaticSalt());
			user.setPassword(pwd);
			save(user);
		}

	}

	public String getUserRole(User user) {
		String userRole = "";
		List<RelRoleUser> sRoleUser = findByProperty(RelRoleUser.class, "User.id", user.getId());
		for (RelRoleUser tsRoleUser : sRoleUser) {
			userRole += tsRoleUser.getRole().getRoleCode() + ",";
		}
		return userRole;
	}

	@SuppressWarnings("unchecked")
	public Object uploadFile(UploadFile uploadFile) {
		Object object = uploadFile.getObject();
		if (uploadFile.getFileKey() != null) {
			updateEntitie(object);
		} else {
			try {
				uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
				MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
				ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
				String uploadbasepath = uploadFile.getBasePath();// 文件上传根目录
				if (uploadbasepath == null) {
					uploadbasepath = ResourceUtil.getConfigByName("uploadpath");
				}
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
				// 文件数据库保存路径
				String path = uploadbasepath + "/";// 文件保存在硬盘的相对路径
				String realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/")
						+ "/" + path;// 文件的硬盘真实路径
				File file = new File(realPath);
				if (!file.exists()) {
					file.mkdirs();// 创建根目录
				}
				if (uploadFile.getCusPath() != null) {
					realPath += uploadFile.getCusPath() + "/";
					path += uploadFile.getCusPath() + "/";
					file = new File(realPath);
					if (!file.exists()) {
						file.mkdirs();// 创建文件自定义子目录
					}
				} else {
					realPath += DataUtils.getDataString(DataUtils.yyyyMMdd) + "/";
					path += DataUtils.getDataString(DataUtils.yyyyMMdd) + "/";
					file = new File(realPath);
					if (!file.exists()) {
						file.mkdir();// 创建文件时间子目录
					}
				}
				String entityName = uploadFile.getObject().getClass().getSimpleName();
				// 设置文件上传路径
				if (entityName.equals("TSTemplate")) {
					realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/")
							+ ResourceUtil.getConfigByName("templatepath") + "/";
					path = ResourceUtil.getConfigByName("templatepath") + "/";
				} else if (entityName.equals("TSIcon")) {
					realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("/")
							+ uploadFile.getCusPath() + "/";
					path = uploadFile.getCusPath() + "/";
				}
				String fileName = "";
				String swfName = "";
				for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
					MultipartFile mf = entity.getValue();// 获取上传文件对象
					fileName = mf.getOriginalFilename();// 获取文件名
					swfName = PinyinUtil
							.getPinYinHeadChar(oConvertUtils.replaceBlank(FileUtils.getFilePrefix(fileName)));// 取文件名首字母作为SWF文件名
					String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
					String myfilename = "";
					String noextfilename = "";// 不带扩展名
					if (uploadFile.isRename()) {

						noextfilename = DataUtils.getDataString(DataUtils.yyyymmddhhmmss) + StringUtil.random(8);// 自定义文件名称
						myfilename = noextfilename + "." + extend;// 自定义文件名称
					} else {
						myfilename = fileName;
					}

					String savePath = realPath + myfilename;// 文件保存全路径
					String fileprefixName = FileUtils.getFilePrefix(fileName);
					if (uploadFile.getTitleField() != null) {
						reflectHelper.setMethodValue(uploadFile.getTitleField(), fileprefixName);// 动态调用set方法给文件对象标题赋值
					}
					if (uploadFile.getExtend() != null) {
						// 动态调用 set方法给文件对象内容赋值
						reflectHelper.setMethodValue(uploadFile.getExtend(), extend);
					}
					if (uploadFile.getByteField() != null) {
						// 二进制文件保存在数据库中
						reflectHelper.setMethodValue(uploadFile.getByteField(),
								StreamUtils.InputStreamTOByte(mf.getInputStream()));
					}
					File savefile = new File(savePath);
					if (uploadFile.getRealPath() != null) {
						// 设置文件数据库的物理路径
						reflectHelper.setMethodValue(uploadFile.getRealPath(), path + myfilename);
					}
					saveOrUpdate(object);
					// 文件拷贝到指定硬盘目录
					FileCopyUtils.copy(mf.getBytes(), savefile);
					// if (uploadFile.getSwfpath() != null) {
					// // 转SWF
					// reflectHelper.setMethodValue(uploadFile.getSwfpath(),
					// path + swfName + ".swf");
					// SwfToolsUtil.convert2SWF(savePath);
					// }
					// FileCopyUtils.copy(mf.getBytes(), savefile);
					if (uploadFile.getSwfpath() != null) {
						// 转SWF
						reflectHelper.setMethodValue(uploadFile.getSwfpath(),
								path + FileUtils.getFilePrefix(myfilename) + ".swf");
						SwfToolsUtil.convert2SWF(savePath);
					}

				}
			} catch (Exception e1) {
			}
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile) {
		uploadFile.getResponse().setContentType("UTF-8");
		uploadFile.getResponse().setCharacterEncoding("UTF-8");
		InputStream bis = null;
		BufferedOutputStream bos = null;
		HttpServletResponse response = uploadFile.getResponse();
		HttpServletRequest request = uploadFile.getRequest();
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		String downLoadPath = "";
		long fileLength = 0;
		if (uploadFile.getRealPath() != null && uploadFile.getContent() == null) {
			downLoadPath = ctxPath + uploadFile.getRealPath();
			fileLength = new File(downLoadPath).length();
			try {
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			if (uploadFile.getContent() != null)
				bis = new ByteArrayInputStream(uploadFile.getContent());
			fileLength = uploadFile.getContent().length;
		}
		try {
			if (!uploadFile.isView() && uploadFile.getExtend() != null) {
				if (uploadFile.getExtend().equals("text")) {
					response.setContentType("text/plain;");
				} else if (uploadFile.getExtend().equals("doc")) {
					response.setContentType("application/msword;");
				} else if (uploadFile.getExtend().equals("xls")) {
					response.setContentType("application/ms-excel;");
				} else if (uploadFile.getExtend().equals("pdf")) {
					response.setContentType("application/pdf;");
				} else if (uploadFile.getExtend().equals("jpg") || uploadFile.getExtend().equals("jpeg")) {
					response.setContentType("image/jpeg;");
				} else {
					response.setContentType("application/x-msdownload;");
				}
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(
								(uploadFile.getTitleField() + "." + uploadFile.getExtend()).getBytes("GBK"),
								"ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
			}
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public Map<Object, Object> getDataSourceMap(Template template) {
		return DataSourceMap.getDataSourceMap();
	}

	/**
	 * 生成XML importFile 导出xml工具类
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		HttpServletResponse response = importFile.getResponse();
		HttpServletRequest request = importFile.getRequest();
		try {
			// 创建document对象
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("UTF-8");
			// 创建根节点
			String rootname = importFile.getEntityName() + "s";
			Element rElement = document.addElement(rootname);
			Class entityClass = importFile.getEntityClass();
			String[] fields = importFile.getField().split(",");
			// 得到导出对象的集合
			List objList = loadAll(entityClass);
			Class classType = entityClass.getClass();
			for (Object t : objList) {
				Element childElement = rElement.addElement(importFile.getEntityName());
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i];
					// 第一为实体的主键
					if (i == 0) {
						// childElement.addAttribute(fieldName,
						// String.valueOf(TagUtil.fieldNametoValues(fieldName,
						// t)));
					} else {
						Element name = childElement.addElement(fieldName);
						// name.setText(String.valueOf(TagUtil.fieldNametoValues(fieldName,
						// t)));
					}
				}

			}
			String ctxPath = request.getSession().getServletContext().getRealPath("");
			File fileWriter = new File(ctxPath + "/" + importFile.getFileName());
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(fileWriter));

			xmlWriter.write(document);
			xmlWriter.close();
			// 下载生成的XML文件
			UploadFile uploadFile = new UploadFile(request, response);
			uploadFile.setRealPath(importFile.getFileName());
			uploadFile.setTitleField(importFile.getFileName());
			uploadFile.setExtend("bak");
			viewOrDownloadFile(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 解析XML文件将数据导入数据库中
	 */
	@SuppressWarnings("unchecked")
	public void parserXml(String fileName) {
		try {
			File inputXml = new File(fileName);
			Class entityClass;
			// 读取文件
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			// 遍历根节点下的子节点
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				// 有实体名反射得到实体类
				entityClass = GenericsUtils.getEntityClass(employee.getName());
				// 得到实体属性
				Field[] fields = null;/* TagUtil.getFiled(entityClass); */
				// 得到实体的ID
				String id = employee.attributeValue(fields[0].getName());
				// 判断实体是否已存在
				Object obj1 = getEntity(entityClass, id);
				// 实体不存在new个实体
				if (obj1 == null) {
					obj1 = entityClass.newInstance();
				}
				// 根据反射给实体属性赋值
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					for (int k = 0; k < fields.length; k++) {
						if (node.getName().equals(fields[k].getName())) {
							String fieldName = fields[k].getName();
							String stringLetter = fieldName.substring(0, 1).toUpperCase();
							String setName = "set" + stringLetter + fieldName.substring(1);
							Method setMethod = entityClass.getMethod(setName, new Class[] { fields[k].getType() });
							String type = /*
											 * TagUtil.getColumnType(fieldName,
											 * fields)
											 */null;
							if (type.equals("int")) {
								setMethod.invoke(obj1, new Integer(node.getText()));
							} else if (type.equals("string")) {
								setMethod.invoke(obj1, node.getText().toString());
							} else if (type.equals("short")) {
								setMethod.invoke(obj1, new Short(node.getText()));
							} else if (type.equals("double")) {
								setMethod.invoke(obj1, new Double(node.getText()));
							} else if (type.equals("Timestamp")) {
								setMethod.invoke(obj1, new Timestamp(
										DataUtils.str2Date(node.getText(), DataUtils.datetimeFormat).getTime()));
							}
						}
					}
				}
				if (obj1 != null) {
					saveOrUpdate(obj1);
				} else {
					save(obj1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ComboTree tree(Depart depart, boolean recursive) {
		ComboTree tree = new ComboTree();
		tree.setId(oConvertUtils.getString(depart.getId()));
		tree.setText(depart.getDepartname());
		List<Depart> departsList = findByProperty(Depart.class, "TSPDepart.id", depart.getId());
		if (departsList != null && departsList.size() > 0) {
			tree.setState("closed");
			tree.setChecked(false);
			if (recursive) {// 递归查询子节点
				List<Depart> departList = new ArrayList<Depart>(departsList);
				// Collections.sort(departList, new SetListSort());// 排序
				List<ComboTree> children = new ArrayList<ComboTree>();
				for (Depart d : departList) {
					ComboTree t = tree(d, true);
					children.add(t);
				}
				tree.setChildren(children);
			}
		}
		return tree;
	}

	public DataGridReturn getDataGridReturn(CriteriaQuery cq, boolean isOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ComboTree> comTree(List<Depart> all, ComboTree comboTree) {
		// TODO Auto-generated method stub
		return null;
	}
}
