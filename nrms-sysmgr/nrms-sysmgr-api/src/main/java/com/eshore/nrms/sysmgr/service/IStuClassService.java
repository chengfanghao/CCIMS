package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.sysmgr.pojo.StuClass;
import com.eshore.nrms.vo.PageVo;

public interface IStuClassService extends IBaseService<StuClass>{
	
	public StuClass queryStuClassByClassId(Integer classId) ;
	
	public PageVo<StuClass> queryClassList(StuClass stuclass, PageConfig page) ;
	
	

}
