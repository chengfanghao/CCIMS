package com.eshore.nrms.sysmgr.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工单申请虚拟机明细
 * 
 * @author hecheng
 *
 */

@Entity
@Table(name = "b_order_vm")
public class OrderVMInfo implements Serializable {
	private String id;// 主键
	private String orderId;// 所属工单
	private String projectName; // 项目名
	private int cpu; // cup个数
	private int memory;// 内存大小
	private int disk;// 磁盘大小
	private String os;// 操作系统
	private Date beginDate;// 开始使用日期
	private Date endDate; // 结束使用日期
	private int isAssigned;// 是否分配虚拟机
	private String userId;// 使用人id
	private String userName;// 使用人姓名
	private int ipRequire;// ip要求

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "order_id")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "cpu")
	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	@Column(name = "memory")
	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	@Column(name = "disk")
	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	@Column(name = "os")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
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

	@Column(name = "is_assigned")
	public int getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(int isAssigned) {
		this.isAssigned = isAssigned;
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

	@Column(name = "ip_require")
	public int getIpRequire() {
		return ipRequire;
	}

	public void setIpRequire(int ipRequire) {
		this.ipRequire = ipRequire;
	}

}
