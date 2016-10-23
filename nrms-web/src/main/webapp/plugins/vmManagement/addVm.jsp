<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	
	function checkEndToSatrtTime(){  
    	var beginDate=$("#beginDate").val();  
    	var start=new Date(beginDate.replace("-", "/").replace("-", "/"));  
    	var endDate=$("#endDate").val();  
    	var end=new Date(endDate.replace("-", "/").replace("-", "/"));  
    	if(end<start){  
        	return false;  
    	}  
    	return true;  
	} 
	
	function checkSatrtTime(){  
    	var beginDate=$("#beginDate").val();  
    	var start=new Date(beginDate.replace("-", "/").replace("-", "/"));  
    	var localDate=new Date();  
    	localDate.setDate(localDate.getDate()-1);
    	if(localDate>start){  
        	return false;  
    	}  
    	return true;  
	} 
	
	function saveVm(){
		
		if( !checkBlank($("#projectName").val()) ){
			$("#msgBoxInfo").html("请输入项目名");
			$("#msgBox").modal('show');
			return;
		}
		
		if( !checkBlank($("#userName").val()) ){
			$("#msgBoxInfo").html("请输入申请人");
			$("#msgBox").modal('show');
			return;
		}
		
		if( !checkBlank($("#serverId").val()) ){
			$("#msgBoxInfo").html("请选择物理服务器");
			$("#msgBox").modal('show');
			return;
		}
		
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
		if( !checkBlank($("#cpu").val()) ){
			$("#msgBoxInfo").html("请输入cpu个数");
			$("#msgBox").modal('show');
			return;
		}
		if( !checkNum($("#cpu").val()) || $("#cpu").val() >= 2147483647){
			$("#msgBoxInfo").html("请输入合法cpu个数");
			$("#msgBox").modal('show');
			return;
		} 
		if( !checkBlank($("#memory").val()) ){
			$("#msgBoxInfo").html("请输入内存大小");
			$("#msgBox").modal('show');
			return;
		}
		if( !checkNum($("#memory").val()) || $("#memory").val() >= 2147483647 ){
			$("#msgBoxInfo").html("请输入合法内存大小");
			$("#msgBox").modal('show');
			return;
		} 
		if( !checkBlank($("#disk").val()) ){
			$("#msgBoxInfo").html("请输入硬盘大小");
			$("#msgBox").modal('show');
			return;
		}
		if( !checkNum($("#disk").val()) || $("#disk").val() >= 2147483647 ){
			$("#msgBoxInfo").html("请输入合法硬盘大小");
			$("#msgBox").modal('show');
			return;
		} 
		if( !checkBlank($("#os").val()) ){
			$("#msgBoxInfo").html("请选择操作系统");
			$("#msgBox").modal('show');
			return;
		}
		 if( checkBlank($("#managerLogin").val())&&(!checkCharAndNum( $("#managerLogin").val() ) || !checkLengthBetween($("#managerLogin").val() , 3, 20)) ){
			$("#msgBoxInfo").html("管理员帐号只允许数字字母,长度3-20位");
			$("#msgBox").modal('show');
			return;
		}
		 if( checkBlank($("#managerPwd").val())&&(!checkCharAndNum( $("#managerPwd").val() ) || !checkLengthBetween($("#managerPwd").val() , 3, 20) )){
				$("#msgBoxInfo").html("管理员密码只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}
		 if( !checkBlank($("#userLogin").val()) ){
				$("#msgBoxInfo").html("请输入用户账号");
				$("#msgBox").modal('show');
				return;
			}
		 if( !checkCharAndNum( $("#userLogin").val() ) || !checkLengthBetween($("#userLogin").val() , 3, 20) ){
				$("#msgBoxInfo").html("用户帐号只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}
		 if( !checkBlank($("#userPwd").val()) ){
				$("#msgBoxInfo").html("请输入用户密码");
				$("#msgBox").modal('show');
				return;
			}
		 if( !checkCharAndNum( $("#userPwd").val() ) || !checkLengthBetween($("#userPwd").val() , 3, 20) ){
				$("#msgBoxInfo").html("用户密码只允许数字字母,长度3-20位");
				$("#msgBox").modal('show');
				return;
		}
		
		 if($("#beginDate").val()==""){  
			 $("#msgBoxInfo").html("请完整输入开始时间！");
			 $("#msgBox").modal('show');
			 return;  
		}  
		 if(!checkSatrtTime()){  
			 $("#msgBoxInfo").html("开始时间必须大于等于当前日期！");
			 $("#msgBox").modal('show');
			 return;  
		}  
		if($("#endDate").val()==""){  
			$("#msgBoxInfo").html("请完整输入结束时间！");
			 $("#msgBox").modal('show');
			 return;   
		}  
		if(!checkEndToSatrtTime()){  
			$("#msgBoxInfo").html("结束时间必须大于开始时间！");
			 $("#msgBox").modal('show');
			 return;   
		}  
		
		
		$.ajax({
			url : '${basePath}/vmManagement/saveVm',
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
				$("#msgBoxInfo").html("程序执行出错" +  XMLHttpRequest.responseText  );
				$("#msgBox").modal('show');
			}
		});
		
	}
</script>

</head>
<body>
	<div class="up-modal-body">
		<form id="dataForm" class="up-form-horizontal">
			<!-- <input type="hidden" id="id" name="id" value="${user.id }" /> -->
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>项目
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="projectName" name="projectName" placeholder="请输入项目名" >
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>申请人
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="userName" name="userName" placeholder="请输入申请人姓名" >
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>物理服务器
				</label>
				<div class="up-col-sm-3">
					<select name="serverId" id="serverId" class="up-form-control" value="">
						<option value="">请选择物理服务器</option>
						<c:forEach var="pmInfo" items="${pmList}">
								<option value="${pmInfo.id }" >${pmInfo.fullIp }</option>
						</c:forEach>
					</select>
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2"><span
					class="up-cq-red-star">*</span>虚拟机IP</label>
				<div class="up-col-sm-3">
					<select name="ipSegId" id="ip_segment" class="up-form-control">
						<option value="">请选择虚拟机网段</option>
						<c:forEach var="vmIpSegList" items="${vmIpSegList }">
							<option value="${vmIpSegList.id }" begin="${vmIpSegList.ipBegin }"
								end="${vmIpSegList.ipEnd }">${vmIpSegList.paramName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="up-col-sm-2" style="padding-left: 0">
					<input type="text" id="ipNum" name="ipNum" class="up-form-control"
						placeholder="请输入IP" />
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>CPU(个)
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="cpu" name="cpu" placeholder="请输入CPU个数" >
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>内存
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="memory" name="memory" placeholder="请输入内存大小"   value="${user.userName }">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star">*</span>硬盘
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="disk" name="disk" placeholder="请输入磁盘大小" value="${user.seatNum }">
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>操作系统
				</label>
				<div class="up-col-sm-3">
					<select name="os" id="os" class="up-form-control" value="">
						<option value="">请选择操作系统</option>
						<c:forEach var="os" items="${osList}">
								<option value="${os.paramName }" >${os.paramName }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star"></span>管理员账号
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="managerLogin" name="managerLogin" placeholder="请输入管理员账号" value="${user.userCode }">
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star"></span>管理员密码
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="managerPwd" name="managerPwd" placeholder="请输入管理员密码" value="${user.userCode }">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star">*</span>用户账号
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="userLogin" name="userLogin" placeholder="请输入用户账号" value="${user.userCode }">
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star">*</span>用户密码
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="userPwd" name="userPwd" placeholder="请输入用户密码" ">
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star">*</span>开始时间
				</label>
				<div class="up-col-sm-3">
					<input type="date" class="up-form-control" id="beginDate" name="beginDate" placeholder="请选择开始时间" >
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<spanclass="up-cq-red-star">*</span>结束时间
				</label>
				<div class="up-col-sm-3">
					<input type="date" class="up-form-control" id="endDate" name="endDate" placeholder="请选择结束时间" >
				</div>
			</div>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary" onClick="saveVm()">保存</button>
		<button type="button" class="up-btn up-btn-default" onClick="parent.window.hideDialog()">取消</button>
	</div>
	
	<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->
</body>
</html>