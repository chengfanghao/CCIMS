<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function saveParam() {
		var ipBegin = $("#ipBegin").val();
		var ipEnd = $("#ipEnd").val();
		var paramType = '${paramInfo.paramType }';
		if (!checkBlank($("#paramValue").val())) {
			$("#msgBoxInfo").html("请填写参数值");
			$("#msgBox").modal('show');
			return;
		}
		if (paramType != 'mail_config' && (!checkBlank(ipBegin) || !checkNum(ipBegin) || ipBegin<0 || ipBegin >=255)) {
			$("#msgBoxInfo").html("请正确填写起始ip");
			$("#msgBox").modal('show');
			return;
		}
		if (paramType != 'mail_config' && (!checkBlank(ipEnd) || !checkNum(ipEnd) || ipEnd<=0 || ipEnd >255)) {
			$("#msgBoxInfo").html("请正确填写结束ip");
			$("#msgBox").modal('show');
			return;
		}
		if(paramType != 'mail_config' && (parseInt(ipBegin) > parseInt(ipEnd))){
			$("#msgBoxInfo").html("结束ip应大于或等于起始ip"  );
			$("#msgBox").modal('show');
			return;
		}
		if(paramType == 'mail_config' && !checkBlank($("#paramValue").val())){
			$("#msgBoxInfo").html("请填写参数值");
			$("#msgBox").modal('show');
			return;
		}
		if(paramType == 'mail_config' && checkBlank($("#paramValue").val())){
			var input = $("#paramValue").val();
			if(input != '1' && input != '0'){
				$("#msgBoxInfo").html("参数值只能为0或1");
				$("#msgBox").modal('show');
				return;
			}
		}
		
		$.ajax({
			url : '${basePath}/paramManage/saveEditedParam',
			dataType : 'json',
			data : $("#dataForm").serialize(),
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
			<input type="hidden"  id="id" name="id" value="${paramInfo.id }" readonly="readonly" >
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">参数类型 </label> <input
					type="hidden" id="pageNum" name="pageNum" value="1">
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="paramType"
						name="paramType" value="${paramInfo.paramType }" readonly="readonly">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label"> <span
					class="up-cq-red-star">*</span>参数值
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="paramValue"
						name="paramValue" value="${paramInfo.paramValue }" placeholder="请输入ip段">
				</div>
			</div>
			<c:if test="${paramInfo.paramType ne 'mail_config'}">
				<div class="up-form-group">
					<label for="" class="up-col-sm-2 up-control-label"> <span
						class="up-cq-red-star">*</span>起始ip
					</label>
					<div class="up-col-sm-7">
						<input type="text" class="up-form-control" id="ipBegin"
							name="ipBegin" value="${paramInfo.ipBegin }" placeholder="请输入起始ip">
					</div>
				</div>
				<div class="up-form-group">
					<label for="" class="up-col-sm-2 up-control-label"> <span
						class="up-cq-red-star">*</span>结束ip
					</label>
					<div class="up-col-sm-7">
						<input type="text" class="up-form-control" id="ipEnd" name="ipEnd"
							value="${paramInfo.ipEnd }" placeholder="请输入结束ip">
					</div>
				</div>
			</c:if>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary"
			onClick="saveParam()">保存</button>
		<button type="button" class="up-btn up-btn-default"
			onClick="parent.window.hideDialog()">取消</button>
	</div>

	<!--    提示框 start -->
	<%@include file="/common/msgBox.jsp"%>
	<!--    提示框 -->
</body>
</html>