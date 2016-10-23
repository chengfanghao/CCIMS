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
		if( !checkBlank($("#cpu").val()) ){
			$("#msgBoxInfo").html("请填入cpu");
			$("#msgBox").modal('show');
			return;
		}
		
		if( !checkNum($("#cpu").val())|| $("#cpu").val() >= 2147483647){
			$("#msgBoxInfo").html("请输入合法cpu个数");
			$("#msgBox").modal('show');
			return;
		} 
		
		if( !checkBlank($("#memory").val()) ){
			$("#msgBoxInfo").html("请填入内存大小");
			$("#msgBox").modal('show');
			return;
		}
		
		if( !checkNum($("#memory").val())|| $("#memory").val() >= 2147483647){
			$("#msgBoxInfo").html("请输入合法的内存大小");
			$("#msgBox").modal('show');
			return;
		} 
		
		if( !checkBlank($("#disk").val()) ){
			$("#msgBoxInfo").html("请填入磁盘大小");
			$("#msgBox").modal('show');
			return;
		}
		
		if( !checkNum($("#disk").val())|| $("#disk").val() >= 2147483647){
			$("#msgBoxInfo").html("请输入合法的磁盘大小");
			$("#msgBox").modal('show');
			return;
		} 
		
		$.ajax({
			method : 'POST',
			url : '${basePath}/vmApply/saveVM',
			data : {
				'ip' : $("#ip").find("option:selected").val(),
				'os' : $("#os").val(),
				'cpu' : $("#cpu").val(),
				'memory' : $("#memory").val(),
				'disk' : $("#disk").val(),
				'orderId' : $("#saveId").val(),
				'userId' : $("#userId").val(),				
			},
			dataType : 'json',
			
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html("保存成功");
					$("#msgBox").modal('show');
					
					$("#msgBoxOKButton").on('click' , function(){
						var orderId = $('#saveId').val();
						var url = "${basePath}/vmApply/showOrderApply?orderId="+orderId;
						window.parent.location.href=url; 
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
		<form id="dataForm" class="up-form-horizontal" >
		
		
			<!--  <input type="hidden" id="orderId" name="orderId" value="${orderInfo.id}" />-->
			<input type="hidden" id="saveId" name="saveId" value="${orderInfo.id}" />
			<input type="hidden" id="userId" name="userId" value="${sessionScope.LOGIN_USER.id}" />
			
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>IP要求
				</label>
				<div class="up-col-sm-7">
					<select name="site" id="ip" class="up-form-control"  style="width:260px">
						<c:forEach var="a"  items="${ipList }">
							<option <c:if test="${not empty orderVMInfo }"> value="${orderVMInfo.ipRequire }"</c:if> >${a.paramName  }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>操作系统
				</label>
				<div class="up-col-sm-3">
					<select name="os" id="os" class="up-form-control" value="">
						<c:forEach var="os" items="${osList}">
								<option value="${os.paramName }" >${os.paramName }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>CPU
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="cpu" name="cpu" placeholder="请输入CPU"   <c:if test="${not empty orderVMInfo }"> value="${orderVMInfo.cpu }"</c:if>>
				</div>
			</div>
			
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>内存
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="memory" name="memory" placeholder="请输入内存"  <c:if test="${not empty orderVMInfo }"> value="${orderVMInfo.memory }"</c:if>>
				</div>
			</div>
			
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>硬盘
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="disk" name="disk" placeholder="请输入硬盘"  <c:if test="${not empty orderVMInfo }"> value="${orderVMInfo.disk }"</c:if>>
				</div>
			</div>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary" onClick="saveVM()">保存</button>
		<button type="button" class="up-btn up-btn-default" onClick="parent.window.hideDialog()">取消</button>
	</div>
	
	<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->
</body>
</html>