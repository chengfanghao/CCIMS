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
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.sysmgr.service.IStuBasicInfoService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StuBasicInfoServiceImpl extends BaseServiceImpl<StuBasicInfo> implements IStuBasicInfoService {

	@Autowired
	private IStuBasicInfoDao stuBasicInfoDao;

	@Override
	public IBaseDao<StuBasicInfo> getDao() {
		// TODO Auto-generated method stub
		return stuBasicInfoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public StuBasicInfo userLogin(String sno) {
		StuBasicInfo stuBasicInfo = this.stuBasicInfoDao.queryStuUserBySno(sno);
		return stuBasicInfo;
	}

	@Override
	public PageVo<StuBasicInfo> queryUserInfoByPage(StuBasicInfo userInfo, PageConfig page) {
		List<StuBasicInfo> list = this.stuBasicInfoDao.queryUserList(userInfo, page);
		return PageUtil.getPageList(page, list);
	}

	@Override
	public Integer getUserCountBySno(String sno) {

		return this.stuBasicInfoDao.getUserCountBySno(sno);
	}

}
