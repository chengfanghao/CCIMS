<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function saveUser(){
		 if( !checkCharAndNum( $("#sno").val() ) || !checkLengthBetween($("#sno").val() , 3, 20) ){
			$("#msgBoxInfo").html("帐号只允许数字字母,长度3-20位");
			$("#msgBox").modal('show');
			return;
		}
		 
		 if( !checkLengthBetween($("#name").val() , 2, 20) ){
				$("#msgBoxInfo").html("姓名长度2-20位");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkName($("#name").val())){
				$("#msgBoxInfo").html("姓名只能为中文和字母");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#sex").val()) ){
				$("#msgBoxInfo").html("请选择性别");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#gradeName").val()) ){
				$("#msgBoxInfo").html("请选择年级名称");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#className").val()) ){
				$("#msgBoxInfo").html("请选择班级名称");
				$("#msgBox").modal('show');
				return;
			}
		
		$.ajax({
			url : '${basePath}/userInfo/saveOrUpdateUser',
			dataType : 'json' ,
			data : $("#dataForm").serialize(),
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
			<input type="hidden" id="id" name="id" value="${stuUser.id }" />
			<div class="up-form-group">				
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>帐号
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="sno" name="sno" placeholder="请输入帐号" 
						<c:if test="${not empty  stuUser }">
							readOnly="true"
						</c:if> 
					value="${stuUser.sno }">
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>姓名
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="name" name="name" placeholder="请输入姓名" <c:if test="${not empty  stuUser }">readOnly="true"</c:if>  value="${stuUser.name }">
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>性别
				</label>
				<div class="up-col-sm-4">
					<select name="sex" id="sex" class="up-form-control" style="width:260px">
						<option value="">请选择</option>
						<option value="男" <c:if test="${stuUser.sex eq '男' }">selected</c:if> >男</option>
						<option value="女" <c:if test="${stuUser.sex eq '女' }">selected</c:if> >女</option>
					</select>
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>年级名称
				</label>
				<div class="up-col-sm-4">
					<select name="gradeName" id="gradeName" class="up-form-control" style="width:260px">
						<option value="">请选择</option>
						<option value="2013级" <c:if test="${stuUser.gradeName eq '2013级' }">selected</c:if> >2013级</option>
						<option value="2012级" <c:if test="${stuUser.gradeName eq '2012级' }">selected</c:if> >2012级</option>
					</select>
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>班级名称
				</label>
				<div class="up-col-sm-4">
					<select name="className" id="className" class="up-form-control" style="width:260px">
						<option value="">请选择</option>
						<option value="理科班" <c:if test="${stuUser.className eq '理科班' }">selected</c:if> >理科班</option>
						<option value="文科班" <c:if test="${stuUser.className eq '文科班' }">selected</c:if> >文科班</option>
					</select>
				</div>
			</div>	
							
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary" onClick="saveUser()">保存</button>
		<button type="button" class="up-btn up-btn-default" onClick="parent.window.hideDialog()">取消</button>
	</div>
	
	<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->
</body>
</html>