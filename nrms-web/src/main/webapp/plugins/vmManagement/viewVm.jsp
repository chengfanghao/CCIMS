<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<div class="up-modal-body">
		<form class="up-form-horizontal">
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>项目：</label>
				<div class="up-col-sm-4">${vmInfo.projectName }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>使用人：</label>
				<div class="up-col-sm-4">${vmInfo.userName }</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>物理服务器：</label>
				<div class="up-col-sm-4">${vmInfo.serverId }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>CPU（个）：</label>
				<div class="up-col-sm-4">${vmInfo.cpu }</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>虚拟机IP：</label>
				<div class="up-col-sm-4">${vmInfo.fullIp }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>内存（G）：</label>
				<div class="up-col-sm-4">${vmInfo.memory }</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>操作系统：</label>
				<div class="up-col-sm-4">${vmInfo.os }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>硬盘（G）：</label>
				<div class="up-col-sm-4">${vmInfo.disk }</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">管理员账号：</label>
				<div class="up-col-sm-4">${vmInfo.managerLogin }</div>
				<label for=""
					class="up-col-sm-2 up-control-label  up-control-label2"><span
					class="up-cq-red-star">*</span>用户账号：</label>
				<div class="up-col-sm-4">${vmInfo.userLogin }</div>
			</div>
			<div class="up-form-group">
				<label for=""
					class="up-col-sm-2 up-control-label  up-control-label2">管理员密码：</label>
				<div class="up-col-sm-4">${vmInfo.managerPwd }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>用户密码：</label>
				<div class="up-col-sm-4">${vmInfo.userPwd }</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">开始时间：</label>
				<div class="up-col-sm-4">${vmInfo.beginDate.toString().substring(0,10) }</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">结束时间：</label>
				<div class="up-col-sm-4">${vmInfo.endDate.toString().substring(0,10) }</div>
			</div>
			<c:if test="${not empty vmInfo.orderVMId and vmInfo.status == 0}">
				<div class="up-form-group">
					<b style="font-size: 15px;color: red;padding-left: 37%;">*****该虚拟机已被回收*****</b>
				</div>
			</c:if>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-default"
			onClick="parent.window.hideDialog()">关闭</button>
	</div>

	<!--    提示框 start -->
	<%@include file="/common/msgBox.jsp"%>
	<!--    提示框 -->
</body>
</html>