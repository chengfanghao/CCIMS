package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IPMInfoDao;
import com.eshore.nrms.sysmgr.pojo.PMInfo;

/**
*@author lile
*@date 2016年8月1日
**/
@Repository
public class PMInfoDaoImpl extends JpaDaoImpl<PMInfo> implements IPMInfoDao  {

	@Override
	public List<PMInfo> queryPMInfoList(PMInfo pmInfo,PageConfig page) {
		StringBuilder hql = new StringBuilder("from PMInfo pmInfo where 1 = 1 ");
		List params = new ArrayList();
		
		if(StringUtils.isNotBlank(pmInfo.getFullIp())){
			hql.append(" and pmInfo.fullIp like ? ");
			params.add("%" + pmInfo.getFullIp() + "%");
		}
		hql.append(" and pmInfo.status = 1");
		hql.append(" order by fullIp ");
		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer queryPMInfoByFullIpAndId(String fullIp, String id) {
		StringBuilder hql = new StringBuilder("select count(*) from PMInfo pmInfo where  1 = 1 and pmInfo.status = 1 and pmInfo.fullIp = ? and pmInfo.id = ?");
		return this.queryCount(hql.toString(),new Object[]{fullIp,id});
	}

	@Override 
	public Integer getPMCountByFullIp(String fullIp) {
		StringBuilder hql = new StringBuilder("select count(*) from PMInfo pmInfo where 1= 1 and pmInfo.status = 1 and pmInfo.fullIp = ? ");
		
		return this.queryCount(hql.toString(),new Object[]{fullIp});
	}

	@Override
	public List<PMInfo> queryPMInfoListAll(PageConfig page) {
		StringBuilder hql = new StringBuilder("from PMInfo pmInfo where 1 = 1 and pmInfo.status = 1");
		return this.queryPage(hql.toString(), page, null);
	}

	@Override
	public Integer getCountByIpSegmentId(String ipSegmentId) {
		StringBuilder hql = new StringBuilder("select count(*) from PMInfo pmInfo where 1= 1 and pmInfo.status = 1 and pmInfo.ipSegmentId = ? ");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId});
	}
	
	@Override
	public Integer getCountByIpSegmentIdAndSE(String ipSegmentId , String beginIp , String endIp){
		StringBuilder hql = new StringBuilder("select count(*) from PMInfo pmInfo where 1= 1 and pmInfo.status = 1 "
					+"and pmInfo.ipSegmentId = ? "
					+"and (pmInfo.ipNum < ? or pmInfo.ipNum > ?)");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId,beginIp,endIp});
	}

}
