package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IStuBasicInfoDao;
import com.eshore.nrms.sysmgr.dao.IStuClassDao;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.sysmgr.pojo.StuClass;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.service.IStuBasicInfoService;
import com.eshore.nrms.sysmgr.service.IStuClassService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StuClassServiceImpl extends BaseServiceImpl<StuClass> implements IStuClassService {

	@Autowired
	private IStuClassDao stuClassDao;
	
	@Override
	public IBaseDao<StuClass> getDao() {
		// TODO Auto-generated method stub
		return stuClassDao;
	}

	@Override
	public StuClass queryStuClassByClassId(Integer classId) {
		StuClass stuClass = this.stuClassDao.queryStuClassByClassId(classId) ;
		return stuClass;
	}

	@Override
	public PageVo<StuClass> queryClassList(StuClass stuclass, PageConfig page) {
		List<StuClass> list = this.stuClassDao.queryClassList(stuclass, page);
		return PageUtil.getPageList(page, list);
	}



	

}
