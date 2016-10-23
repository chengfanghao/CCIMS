package com.eshore.nrms.sysmgr.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IUserInfoDao;
import com.eshore.nrms.sysmgr.dao.IVMInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VMInfoServiceImpl extends BaseServiceImpl<VMInfo> implements IVMInfoService {

	@Autowired
	private IVMInfoDao vmInfoDao;
	
	
	@Override
	public IBaseDao<VMInfo> getDao() {
		return vmInfoDao;
	}
	
	@Override
	@Transactional(readOnly = true)
	public VMInfo vmDetails(String fullIp) {
		VMInfo vmInfo = this.vmInfoDao.queryVmByFullIp(fullIp);
		return vmInfo;
	}

	@Override
	public PageVo<VMInfo> queryVMInfoByPage(VMInfo vmInfo, PageConfig page) {
		List<VMInfo> list = this.vmInfoDao.queryVmList(vmInfo, page);
		return PageUtil.getPageList(page, list);
	}

	@Override
	public Integer getVmCountByFullIp(String fullIp) {
		return this.vmInfoDao.getVmCountByFullIp(fullIp);
	}

	@Override
	public VMInfo queryVMInfoByOrderVMId(String orderVMId) {
		return this.vmInfoDao.queryVMInfoByOrderVMId(orderVMId);
	}

	@Override
	public PageVo<VMInfo> queryVMInfoByServerId(String serverId, PageConfig page) {
		List<VMInfo> list = this.vmInfoDao.queryVmListByPMId(serverId, page);
		return PageUtil.getPageList(page, list);
	}
	
	@Override
	public Integer queryCountByIpSegmentId(String ipSegmentId) {
		return vmInfoDao.getCountByIpSegmentId(ipSegmentId);
	}
	
	@Override
	public Integer queryCountByIpSegmentIdANDSE(String ipSegmentId , String beginIp, String endIp ){
		return vmInfoDao.getCountByIpSegmentIdAndSE(ipSegmentId, beginIp, endIp);
	}
}
