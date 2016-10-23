package com.eshore.nrms.sysmgr.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IVMInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.vo.Conts;

@Repository
public class VMInfoDaoImpl extends JpaDaoImpl<VMInfo> implements IVMInfoDao {


	@Override
	public VMInfo queryVmByFullIp(String fullIp) {
		String hql = "from VMInfo v where v.fullIp = ?";
		
		return this.getPojo(hql, new Object[]{fullIp});
	}

	@Override
	public List<VMInfo> queryVmList(VMInfo vmInfo, PageConfig page) {
		StringBuilder hql = new StringBuilder("from VMInfo v where 1=1 ");
		List params = new ArrayList();
		if(StringUtils.isNotBlank(vmInfo.getProjectName())){
			hql.append(" and v.projectName like ? ");
			params.add("%" + vmInfo.getProjectName() + "%");
		}
		
		if(StringUtils.isNotBlank(vmInfo.getServerId())){
			hql.append(" and v.serverId = ? ");
			params.add(vmInfo.getServerId());
		}		
		
		if(StringUtils.isNotBlank(vmInfo.getFullIp())){
			hql.append(" and v.fullIp like ? ");
			params.add("%" + vmInfo.getFullIp() + "%");
		}
		
		if(vmInfo.getStatus() != null && vmInfo.getStatus() != -1){
			hql.append(" and v.status = ? ");
			params.add( vmInfo.getStatus() );
		}
		
		
		hql.append(" order by v.fullIp ");
		
		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer getVmCountByFullIp(String fullIp) {
		StringBuilder hql = new StringBuilder("select count(*) from VMInfo v where v.status = 1 and v.fullIp = ? ");
		ArrayList params = new ArrayList();
		params.add(fullIp);
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public VMInfo queryVMInfoByOrderVMId(String orderVMId) {
		String hql = "from VMInfo v where 1=1 and v.orderVMId = ?";
		return this.getPojo(hql, new Object[]{orderVMId});
	}

	@Override
	public List<VMInfo> queryVmListByPMId(String pmId, PageConfig page) {
		String hql = "from VMInfo v where 1=1 and v.serverId = ?";
		return this.queryPage(hql, page, new Object[]{pmId});
	}
	
	@Override
	public Integer getCountByIpSegmentId(String ipSegmentId) {
		StringBuilder hql = new StringBuilder("select count(*) from VMInfo v where 1= 1 and v.status = 1 and v.ipSegId = ?  ");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId});
	}
	
	@Override
	public Integer getCountByIpSegmentIdAndSE(String ipSegmentId , String beginIp , String endIp){
		StringBuilder hql = new StringBuilder("select count(*) from VMInfo v where 1= 1 and v.status = 1 "
					+"and v.ipSegId = ? "
					+"and (v.ipNum < ? or v.ipNum > ?)");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId,beginIp,endIp});
	}
}
