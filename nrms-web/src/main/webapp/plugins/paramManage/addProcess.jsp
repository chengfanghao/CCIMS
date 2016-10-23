<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function saveProcess() {
		if (!checkBlank($("#auditState").val())) {
			$("#msgBoxInfo").html("请填写审批人姓名");
			$("#msgBox").modal('show');
			return;
		}
		var addName = $("#auditState").val();
		var processValue = $("#processValue").val();
		$.ajax({
			url : '${basePath}/auditProcessManage/saveProcess',
			dataType : 'json',
			data : {
				'addName' : addName,
				'nextProcessValue' : processValue
			},
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
					$("#msgBoxOKButton").on('click', function() {
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
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>审批人 </label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="auditState"
						name="auditState" placeholder="请输入审批人姓名">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label"><span
					class="up-cq-red-star">*</span>添加到： </label>
				<div class="up-col-sm-7">
					<select name="processValue" id="processValue"
						class="up-form-control">
						<c:forEach var="process" items="${processList }">
							<c:if test="${sessionScope.LOGIN_USER.userRole eq '1'}">
								<c:if test="${process.value gt 90}">
									<option value="${process.value }">${process.auditState }</option>
								</c:if>
							</c:if>
							<c:if test="${sessionScope.LOGIN_USER.userRole eq '2'}">
								<c:if test="${process.value gt 1}">
									<option value="${process.value }">${process.auditState }</option>
								</c:if>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<label style="position:relative;min-height:1px;line-height:30px;text-align: left;">审批之前
				</label>
			</div>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary"
			onClick="saveProcess()">保存</button>
		<button type="button" class="up-btn up-btn-default"
			onClick="parent.window.hideDialog()">取消</button>
	</div>

	<!--    提示框 start -->
	<%@include file="/common/msgBox.jsp"%>
	<!--    提示框 -->
</body>
</html>