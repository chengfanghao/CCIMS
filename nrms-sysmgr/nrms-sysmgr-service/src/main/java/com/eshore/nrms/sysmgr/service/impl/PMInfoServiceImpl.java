package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IPMInfoDao;
import com.eshore.nrms.sysmgr.pojo.PMInfo;
import com.eshore.nrms.sysmgr.service.IPMInfoService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;
/**
*@author lile
*@date 2016年8月1日
**/
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PMInfoServiceImpl extends BaseServiceImpl<PMInfo>implements IPMInfoService {
	
	@Autowired
	private IPMInfoDao pmInfoDao;
	
	@Override
	public IBaseDao<PMInfo> getDao() {
		return pmInfoDao;
	}
	
	/*@Override
	public List<PMInfo> queryAllPMInfo() {
		return null;
	}*/

	@Override
	public Integer queryPMInfoByFullIpAndId(String fullIp,String id) {
		return this.pmInfoDao.queryPMInfoByFullIpAndId(fullIp,id);
	}

	@Override
	public PageVo<PMInfo> queryPMInfoByPage(PMInfo pmInfo, PageConfig page) {
		List<PMInfo> list = this.pmInfoDao.queryPMInfoList(pmInfo,page);
		return PageUtil.getPageList(page, list);
	}

	@Override
	public Integer getPMCountByFullIp(String fullIp) {
		return this.pmInfoDao.getPMCountByFullIp(fullIp);
	}

	@Override
	public List<PMInfo> queryAllPMInfo(PageConfig page) {
		return pmInfoDao.queryPMInfoListAll(page);
	}
	
	@Override
	public Integer queryCountByIpSegmentId(String ipSegmentId) {
		return pmInfoDao.getCountByIpSegmentId(ipSegmentId);
	}
	
	@Override
	public Integer queryCountByIpSegmentIdANDSE(String ipSegmentId , String beginIp, String endIp ){
		return pmInfoDao.getCountByIpSegmentIdAndSE(ipSegmentId, beginIp, endIp);
	}
}
