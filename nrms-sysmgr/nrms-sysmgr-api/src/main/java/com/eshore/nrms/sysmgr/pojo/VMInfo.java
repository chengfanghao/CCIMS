package com.eshore.nrms.sysmgr.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="b_vm_info")
public class VMInfo implements Serializable {
	private String id;// 主键
	private String orderVMId;// 工单申请虚拟机明细id
	private String serverId;// 物理服务器
	private String projectName;// 项目没
	private String ipSegId;// 服务器ip段id
	private String ipSeg;// 服务器ip段
	private String ipNum;// 服务器ip
	private String fullIp;// 全ip
	private Integer cpu;
	private Integer memory;
	private Integer disk;
	private String os;
	private String managerLogin;// 管理员账号
	private String managerPwd;// 管理员密码
	private String userLogin;// 用户账号
	private String userPwd;// 用户密码
	private Date beginDate;// 开始时间
	private Date endDate;// 结束时间
	private String userId;// 使用人id
	private String userName;// 使用人姓名
	private Integer status;// 状态

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "order_vm_id")
	public String getOrderVMId() {
		return orderVMId;
	}

	public void setOrderVMId(String orderVMId) {
		this.orderVMId = orderVMId;
	}

	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "ip_segment_id")
	public String getIpSegId() {
		return ipSegId;
	}

	public void setIpSegId(String ipSegId) {
		this.ipSegId = ipSegId;
	}

	@Column(name = "ip_segment")
	public String getIpSeg() {
		return ipSeg;
	}

	public void setIpSeg(String ipSeg) {
		this.ipSeg = ipSeg;
	}

	@Column(name = "ip_num")
	public String getIpNum() {
		return ipNum;
	}

	public void setIpNum(String ipNum) {
		this.ipNum = ipNum;
	}

	@Column(name = "full_ip")
	public String getFullIp() {
		return fullIp;
	}

	public void setFullIp(String fullIp) {
		this.fullIp = fullIp;
	}

	@Column(name = "cpu")
	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	@Column(name = "memory")
	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	@Column(name = "disk")
	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}

	@Column(name = "os")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "manager_login")
	public String getManagerLogin() {
		return managerLogin;
	}

	public void setManagerLogin(String managerLogin) {
		this.managerLogin = managerLogin;
	}

	@Column(name = "manager_pwd")
	public String getManagerPwd() {
		return managerPwd;
	}

	public void setManagerPwd(String managerPwd) {
		this.managerPwd = managerPwd;
	}

	@Column(name = "user_login")
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Column(name = "user_pwd")
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Column(name = "begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
