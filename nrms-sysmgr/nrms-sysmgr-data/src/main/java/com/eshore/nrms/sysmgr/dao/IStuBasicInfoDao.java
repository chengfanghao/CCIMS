package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;

public interface IStuBasicInfoDao extends IBaseDao<StuBasicInfo> {
	public StuBasicInfo queryStuUserBySno(String sno);

	public List<StuBasicInfo> queryUserList(StuBasicInfo userInfo, PageConfig page);
	
	public Integer getUserCountBySno(String sno);
}
