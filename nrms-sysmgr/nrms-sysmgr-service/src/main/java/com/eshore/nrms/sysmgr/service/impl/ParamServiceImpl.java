package com.eshore.nrms.sysmgr.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.PagerUtils;
import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IParamDao;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.service.IParamService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ParamServiceImpl extends BaseServiceImpl<Param> implements
		IParamService {
	
	@Autowired
	private IParamDao paramDao;
	
	@Override
	public IBaseDao<Param> getDao() {
		return paramDao;
	}

	@Override
	public List<Param> queryParamByParamType(String paramType) {
		return paramDao.queryParamByParamType(paramType);
	}

	@Override
	public PageVo<Param> queryParamList(Param param, PageConfig page) {
		List<Param> paramList = this.paramDao.queryParamList(param, page);
		return PageUtil.getPageList(page,paramList);
	}

	@Override
	public Integer queryParamCount() {
		return this.paramDao.queryParamCount();
	}
	
	@Override
	public Integer queryParamCountByIpSegmentAndSE(String id , String paramValue , String beginIp , String endIp){
		return this.paramDao.queryParamCountByIpSegmentAndSE(id , paramValue, beginIp, endIp);
	}
	
	@Override
	public Integer queryParamCountByParamName(String paramName){
		return this.paramDao.queryParamCountByParamName(paramName);
	}

	@Override
	public Integer queryParamCountByParamNameAndParamValue(String id, String paramName, String paramValue) {
		return this.paramDao.queryParamCountByParamNameAndParamValue(id, paramName, paramValue);
	}

	@Override
	public Param getParamByParamName(String paramName) {
		return this.paramDao.getParamByParamName(paramName);
	}
		
}
