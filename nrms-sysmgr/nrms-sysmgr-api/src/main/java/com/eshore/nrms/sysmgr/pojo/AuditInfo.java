package com.eshore.nrms.sysmgr.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审批明细
 * 
 * @author hecheng
 *
 */

@Entity
@Table(name = "b_audit_info")
public class AuditInfo implements Serializable {
	private String id; // 主键
	private String orderId; // 工单号
	private String operUserId; // 操作人
	private String operUserName; // 操作人姓名
	private Date operDate; // 操作时间
	private int operType; // 操作类型
	private int operResult; // 操作结果
	private String operFeedBack; // 操作反馈

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

	@Column(name = "oper_user_id")
	public String getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
	}

	@Column(name = "oper_user_name")
	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	@Column(name = "oper_date")
	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	@Column(name = "oper_type")
	public int getOperType() {
		return operType;
	}

	public void setOperType(int operType) {
		this.operType = operType;
	}

	@Column(name = "oper_result")
	public int getOperResult() {
		return operResult;
	}

	public void setOperResult(int operResult) {
		this.operResult = operResult;
	}

	@Column(name = "oper_feedback")
	public String getOperFeedBack() {
		return operFeedBack;
	}

	public void setOperFeedBack(String operFeedBack) {
		this.operFeedBack = operFeedBack;
	}
}
