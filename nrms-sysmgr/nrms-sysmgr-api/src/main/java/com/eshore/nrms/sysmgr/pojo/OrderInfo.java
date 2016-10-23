package com.eshore.nrms.sysmgr.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工单信息
 * 
 * @author hecheng
 *
 */
@Entity
@Table(name = "b_order_info")
public class OrderInfo implements Serializable {
	private String id; // 主键
	private String projectName; // 项目名
	private Date beginTime; // 开始使用时间
	private Date endTime; // 结束使用时间
	private int vmNum; // 申请虚拟机台数
	private int assignedNum; // 已分配台数
	private String createUserId;// 申请人id
	private String createUserName; // 申请人姓名
	private Date createDate; // 工单提交日期
	private Date checkDate; // 审批日期
	private String feedBack; // 审批反馈
	private int state; // 工单状态

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "begin_date")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "end_date")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "vm_num")
	public int getVmNum() {
		return vmNum;
	}

	public void setVmNum(int vmNum) {
		this.vmNum = vmNum;
	}

	@Column(name = "assigned_num")
	public int getAssignedNum() {
		return assignedNum;
	}

	public void setAssignedNum(int assignedNum) {
		this.assignedNum = assignedNum;
	}

	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "check_date")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "feedback")
	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	@Column(name = "state")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
