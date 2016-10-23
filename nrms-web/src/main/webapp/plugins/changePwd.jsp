<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	function showWrongMessage(detectedItem, message) {
		if (!checkBlank(detectedItem)) {
			$("#msgBoxInfo").html(message + "不能为空！");
			$("#msgBox").modal('show');
			return true;
		}
		if (!checkLengthBetween(detectedItem,3,20)) {
			$("#msgBoxInfo").html(message + "长度必须为6-20！");
			$("#msgBox").modal('show');
			return true;
		}
		if (!checkCharAndNum(detectedItem)) {
			$("#msgBoxInfo").html(message + "只允许为字母和数字或者其组合！");
			$("#msgBox").modal('show');
			return true;
		}
		return false;
	}

	function changePassword() {

		var loginName = $("#loginName").val();
		var password = $("#password").val();
		var newPassword = $("#newPassword").val();
		var reNewPassword = $("#reNewPassword").val();
		var userId = '${sessionScope.LOGIN_USER.sno}';
		if (showWrongMessage(password, "密码"))
			return;
		if (showWrongMessage(newPassword, "新密码"))
			return;
		if (showWrongMessage(reNewPassword, "重复密码"))
			return;

		if (!(newPassword === reNewPassword)) {

			$("#msgBoxInfo").html("新密码和重复密码输入不一致！");
			$("#msgBox").modal('show');
			return;
		}

		$.ajax({
			type : 'POST',
			url : '${basePath}/changePwd',
			data : {

				'userId' : userId,
				'password' : password,
				'newPassword' : newPassword,

			},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
					setTimeout(function() {
						window.location.href = "${basePath}/logOut";
					}, 2000);
				} else {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
				}
			},
			error : function(data) {
				$("#msgBoxInfo").html("密码修改失败");
				$("#msgBox").modal('show');
			}
		});

	}
</script>

<head>
</head>

<body>
	<div id="wrap" class="">
		<!--    头部 和  菜单 start -->
		<%@include file="/common/headAndLeft.jsp"%>
		<!--    头部 和  菜单 end -->

		<main class="main up-container-fluid">
		<div class='up-tab-content'>
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title up-clearfix">
					<h4 class="up-pull-left">修改密码</h4>
				</div>

				<div class="up_page_con">
					<form class="up-form-horizontal" style="max-width: 800px;padding-top: 20px;">
						<div class="up-form-group">
							<label  for="" class="up-col-sm-1 up-control-label" style="width: 100px"><span
								class="up-red-star"><span
								>*</span>登录名</label>
							<div class="up-col-sm-5">
								<div
									class="input_icon up-has-error input_warning form_state up-cq-set">
									<input type="text" class="up-form-control" id="loginName"
										name="loginName" placeholder="输入登录名"
										value="${sessionScope.LOGIN_USER.name}"
										readonly="readonly">
								</div>
							</div>
						</div>
						<div class="up-form-group">
							<label for="" class="up-col-sm-1 up-control-label" style="width: 100px"><span
								class="up-red-star">*</span>旧密码</label>
							<div class="up-col-sm-5">
								<div
									class="input_icon up-has-error input_warning form_state up-cq-set">
									<input type="password" class="up-form-control" id="password"
										name="password" maxlength="20" placeholder="输入旧密码">
								</div>
							</div>
						</div>
						<div class="up-form-group">
							<label for="" class="up-col-sm-1 up-control-label" style="width: 100px"><span
								class="up-red-star">*</span>新密码</label>
							<div class="up-col-sm-5">
								<div
									class="input_icon up-has-error input_warning form_state up-cq-set">
									<input type="password" class="up-form-control" id="newPassword"
										name="newPassword" maxlength="20" placeholder="输入新密码">
								</div>
							</div>
						</div>
						<div class="up-form-group">
							<label for="" class="up-col-sm-1 up-control-label" style="width: 100px"><span
								class="up-red-star">*</span>重复密码</label>
							<div class="up-col-sm-5">
								<div
									class="input_icon up-has-error input_warning form_state up-cq-set">
									<input type="password" class="up-form-control"
										id="reNewPassword" maxlength="20" name="reNewPassword"
										placeholder="重新输入新密码">
								</div>
							</div>
						</div>
						<div class="up-form-group up-text-center mar-40" style="float: left;padding-left: 180px">
							<button type="button" class="up-btn up-btn-add"
								onClick="changePassword()">确定</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="up-btn up-btn-reset mar-rig30"
								onClick="window.location.href='${basePath}/'">返回</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		</main>

		<!-- 侧栏提示工具容器 -->
		<div id="tooltip_box"></div>
		<!-- 侧栏提示工具容器 -->

		<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->

		<div class="up-modal up-fade" id="modalDialog" data-drag="true"
			data-backdrop="static">
			<div class="up-modal-dialog up-modal-lg">
				<div class="up-modal-content">
					<div class="up-modal-header">
						<button type="button" class="up-close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="up-modal-title" id="modalDialogTitle">新增用户</h4>
					</div>
					<iframe id="modalDialogFrame" width="100%" height="420px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>

	</div>
</body>

</html>
