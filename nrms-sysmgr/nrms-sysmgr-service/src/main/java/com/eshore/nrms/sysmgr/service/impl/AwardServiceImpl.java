package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IAwardDao;
import com.eshore.nrms.sysmgr.pojo.Award;
import com.eshore.nrms.sysmgr.service.IAwardService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AwardServiceImpl extends BaseServiceImpl<Award> implements IAwardService {

	@Autowired
	private IAwardDao awardDao;

	@Override
	public IBaseDao<Award> getDao() {
		return awardDao;
	}

	@Override
	public PageVo<Award> queryAwardByPage(Award award, PageConfig page) {
		List<Award> list = this.awardDao.queryAwardList(award, page);
		return PageUtil.getPageList(page, list);
	}

	@Override
	public Integer getAwardCountBySno(String sno) {

		return this.awardDao.getAwardCountBySno(sno);
	}

}
