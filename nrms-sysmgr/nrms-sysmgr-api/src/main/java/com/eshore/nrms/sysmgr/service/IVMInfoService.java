package com.eshore.nrms.sysmgr.service;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.vo.PageVo;

public interface IVMInfoService extends IBaseService<VMInfo> {
	
	public VMInfo vmDetails(String fullIp);
	
	public PageVo<VMInfo> queryVMInfoByPage(VMInfo vmInfo , PageConfig page);
	
	public Integer getVmCountByFullIp(String fullIp);
	
	/**
	 * 根据工单下申请的虚拟机id查出部署的虚拟机信息
	 * @param orderVMId:VMInfo中的order_vm_id
	 * @return
	 */
	public VMInfo queryVMInfoByOrderVMId(String orderVMId);
	
	public PageVo<VMInfo> queryVMInfoByServerId(String serverId , PageConfig page);
	
	public Integer queryCountByIpSegmentId(String ipSegmentId);
	
	public Integer queryCountByIpSegmentIdANDSE(String ipSegmentId , String beginIp, String endIp ); 
}
