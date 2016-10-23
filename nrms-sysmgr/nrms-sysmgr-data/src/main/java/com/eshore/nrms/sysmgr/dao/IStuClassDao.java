package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.sysmgr.pojo.StuClass;
import com.eshore.nrms.sysmgr.pojo.UserInfo;

public interface IStuClassDao extends IBaseDao<StuClass> {	
	public List<StuClass> queryClassList(StuClass stuclass ,  PageConfig page);
	
	public StuClass queryStuClassByClassId(Integer classId) ;
}
