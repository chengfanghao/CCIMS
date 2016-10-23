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
		
		 if( !checkLengthBetween($("#awardName").val(), 3, 20)){
				$("#msgBoxInfo").html("获奖名称长度3-20位");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#gradeId").val())){
				$("#msgBoxInfo").html("请选择获奖级别");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#rankId").val())){
				$("#msgBoxInfo").html("请选择获奖等次");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#unit").val())){
				$("#msgBoxInfo").html("请输入发奖单位");
				$("#msgBox").modal('show');
				return;
			}
		 
		 if( !checkBlank($("#awardTime").val())){
				$("#msgBoxInfo").html("请选择获奖时间");
				$("#msgBox").modal('show');
				return;
			}
		
		$.ajax({
			url : '${basePath}/awardInfo/saveOrUpdateUser',
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
			<input type="hidden" id="awardId" name="awardId" value="${awardInfo.awardId}" />
			
			<div class="up-form-group">				
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>获奖名称
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="awardName" name="awardName" placeholder="请输入获奖名称"
					value="${awardInfo.awardName}">
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>获奖时间
				</label>
				<div class="up-col-sm-7">
					<input type="text" style="width:180px;height:30px" class="Wdate up-form-control" id="adwardTime" name="awardTime" placeholder="请输入获奖时间"
					value="${awardInfo.awardTime}" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy年MM月',isShowClear:false,readOnly:true})">
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>获奖级别
				</label> 	
				<div class="up-col-sm-4">		
					<select name="gradeId" id="gradeId" class="up-form-control" style="width:160px">
						<option value="">请选择</option>
						<c:forEach var="gradeItem" items="${gradeList }">
							<option value="${gradeItem.gradeId}" <c:if test="${gradeItem.gradeId eq awardInfo.gradeId }">selected</c:if>>${gradeItem.gradeName }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>获奖等次
				</label> 
				<div class="up-col-sm-4">
					<select class="up-form-control" name="rankId" id="rankId" style="width:260px">
						<option value="">请选择</option>
						<c:forEach var="rankItem" items="${rankList }">																		
							<option value="${rankItem.rankId}" <c:if test="${rankItem.rankId eq awardInfo.rankId }">selected</c:if>>${rankItem.rankName }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="up-form-group">				
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>发奖单位
				</label>
				<div class="up-col-sm-7">
					<input type="text" class="up-form-control" id="unit" name="unit" placeholder="请输入发奖单位"
					value="${awardInfo.unit}">
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