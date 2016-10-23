package com.eshore.nrms.sysmgr.service;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.vo.PageVo;

public interface IStuBasicInfoService extends IBaseService<StuBasicInfo> {

	public StuBasicInfo userLogin(String name);

	public PageVo<StuBasicInfo> queryUserInfoByPage(StuBasicInfo userInfo, PageConfig page);
	
	public Integer getUserCountBySno(String sno);
}
