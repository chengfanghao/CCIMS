<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	function saveVM(){
		if( !checkBlank($("#ip_segment").val()) ){
			$("#msgBoxInfo").html("请选择IP段");
			$("#msgBox").modal('show');
			return;
		}
		var ipSegment = $("#ip_segment").find("option:selected");
		var begin = Number(ipSegment.attr("begin"));
		var end = Number(ipSegment.attr("end"));
		var ip = Number($("#ipNum").val());
		if( ip < begin || ip > end){
			$("#msgBoxInfo").html("IP必须在IP段范围内");
			$("#msgBox").modal('show');
			return;
		}
		if( !checkBlank($("#ipNum").val()) || !checkNum($("#ipNum").val())){
			$("#msgBoxInfo").html("请输入合法IP");
			$("#msgBox").modal('show');
			return;
		} 
		/*
		 if( !checkCharAndNum( $("#manager_login").val() ) || !checkLengthBetween($("#manager_login").val() , 3, 20) ){
			$("#msgBoxInfo").html("管理员帐号只允许数字字母,长度3-20位");
			$("#msgBox").modal('show');
			return;
		}
		 if( !checkCharAndNum( $("#manager_pwd").val() ) || !checkLengthBetween($("#manager_pwd").val() , 3, 20) ){
				$("#msgBoxInfo").html("管理员密码只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}*/
		 if( !checkCharAndNum( $("#user_login").val() ) || !checkLengthBetween($("#user_login").val() , 3, 20) ){
				$("#msgBoxInfo").html("用户帐号只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}
		 if( !checkCharAndNum( $("#user_pwd").val() ) || !checkLengthBetween($("#user_pwd").val() , 3, 20) ){
				$("#msgBoxInfo").html("用户密码只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}
		 
		 var vmOrderInfoId = '${vmInfo.id}';
		 var serverId = $("#serverId").val();
		 var ipSegId = $("#ip_segment").val();
		var managerLogin = $("#manager_login").val();
		var managerPwd = $("#manager_pwd").val();
		var userLogin = $("#user_login").val();
		var userPwd = $("#user_pwd").val();
		 
		$.ajax({
			url : '${basePath}/vmManagement/deploySaveVm',
			dataType : 'json' ,
			/*data : $("#dataForm").serialize(),*/
			data :{
				"vmOrderInfoId": vmOrderInfoId,
				"serverId": serverId,
				"ipSegId": ipSegId,
				"ipNum": ip,
				"managerLogin": managerLogin,
				"managerPwd": managerPwd,
				"userLogin": userLogin,
				"userPwd": userPwd
			},
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
					$("#msgBoxOKButton").on('click' , function(){
						parent.window.location.reload();
					});
					
				} else {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
				}
			},
			error : function(data) {
				$("#msgBoxInfo").html("程序执行出错");
				$("#msgBox").modal('show');
			}
		});
	}
</script>
</head>
<body>
	<div class="up-modal-body">
		<form id="dataForm" class="up-form-horizontal">
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">项目</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" name="projectName"
						value="${vmInfo.projectName }" readonly="true">
				</div>
				<label for="" class="up-col-sm-2 up-control-label">申请人</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" name="userName"
						value="${vmInfo.userName }" readonly="true">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">内存（G）</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" name="memory"
						value="${vmInfo.memory }" readonly="true">
				</div>
				<label for="" class="up-col-sm-2 up-control-label">CPU（个）</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" value="${vmInfo.cpu }"
						name="cpu" readonly="true">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">操作系统</label>
				<div class="up-col-sm-4">
						<input type="text" class="up-form-control" value="${vmInfo.os }"
							name="os" readonly="true">
				</div>
				<label for="" class="up-col-sm-2 up-control-label">硬盘（G）</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" value="${vmInfo.disk }"
						name="disk" readonly="true">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">开始时间</label>
				<div class="up-col-sm-4">
					<div class="input_icon">
						<input type="text" class="up-form-control" name="beginDate"
							value="${vmInfo.beginDate.toString().substring(0,10) }"
							readonly="true">
					</div>
				</div>
				<label for="" class="up-col-sm-2 up-control-label">结束时间</label>
				<div class="up-col-sm-4">
					<div class="input_icon">
						<input type="text" class="up-form-control" name="endDate"
							value="${vmInfo.endDate.toString().substring(0,10) }"
							readonly="true">
					</div>
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">ip要求</label>
				<div class="up-col-sm-4">
					<div class="input_icon">
						<c:forEach var="ipList" items="${ipRequireList }">
							<c:if test="${ipList.id eq vmInfo.ipRequire }">
								<input type="text" class="up-form-control"
									value="${ipList.paramName }" readonly="true">
							</c:if>
						</c:forEach>
					</div>
				</div>
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>物理机</label>
				<div class="up-col-sm-3">
					<select name="serverId" id="serverId" class="up-form-control">
						<c:forEach var="pm" items="${pmList }">
							<option value="${pm.id }">${pm.fullIp }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>虚拟机IP</label>
				<div class="up-col-sm-3">
					<select name="ipSegId" id="ip_segment" class="up-form-control">
						<c:forEach var="ipList" items="${ipList }">
							<option value="${ipList.id }" begin="${ipList.ipBegin }"
								end="${ipList.ipEnd }">${ipList.paramName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="up-col-sm-2" style="padding-left: 0">
					<input type="text" id="ipNum" name="ipNum" class="up-form-control"
						placeholder="请输入IP" />
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">管理员账号</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" id="manager_login"
						name="managerLogin">
				</div>
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>用户账号</label>
				<div class="up-col-sm-4">
					<input type="text" class="up-form-control" id="user_login"
						name="userLogin">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">管理员密码</label>
				<div class="up-col-sm-4">
					<input type="password" class="up-form-control" id="manager_pwd"
						name="managerPwd">
				</div>
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>用户密码</label>
				<div class="up-col-sm-4">
					<input type="password" class="up-form-control" id="user_pwd"
						name="userPwd">
				</div>
			</div>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary" onclick="saveVM()">确定</button>
		<button type="button" class="up-btn up-btn-default"
			onClick="parent.window.hideDialog()">取消</button>
	</div>

	<!--    提示框 start -->
	<%@include file="/common/msgBox.jsp"%>
	<!--    提示框 -->
</body>
</html>