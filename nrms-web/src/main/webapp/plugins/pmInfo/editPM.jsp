<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
function checkIp(){
	var ip = document.getElementById('consoleUrl').value; 
	var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
	if( reSpaceCheck.test(ip) )
	{
		ip.match( reSpaceCheck );
		if ( RegExp.$1 <= 255 && RegExp.$1 > 0
		  && RegExp.$2 <= 255 && RegExp.$2 >= 0
		  && RegExp.$3 <= 255 && RegExp.$3 >= 0 
		  && RegExp.$4 <= 255 && RegExp.$4 >=0 )
		{
			return true; 
		}
		else
		{
			return false;
		}
	}else
	{
		return false;
	}
}
function updatePM(){
	/*校验IP段选择下拉框未选，必选项*/
	if( !checkBlank($("#ipSegmentId").val()) ){
		$("#msgBoxInfo").html("请选择IP段");
		$("#msgBox").modal('show');
		return;
	}
	/*校验ipNum输入框，必填项*/
	if( !checkBlank($("#ipNum").val())  ){
		$("#msgBoxInfo").html("请输入IP末位");
		$("#msgBox").modal('show');
		return;
	}
	if( !checkNum($("#ipNum").val())){
		$("#msgBoxInfo").html("请输入合法IP末位");
		$("#msgBox").modal('show');
		return;
	}
	/*ipNum输入框输入范围*/
	var ipSegment = $("#ipSegmentId").find("option:selected");
	var begin = Number(ipSegment.attr("begin"));
	var end = Number(ipSegment.attr("end"));
	var ip = Number($("#ipNum").val());
	if( ip < begin || ip > end){
		$("#msgBoxInfo").html("IP必须在IP段范围内");
		$("#msgBox").modal('show');
		return;
	}
	/*校验控制台地址(consoleUrl)输入框为空，必填项*/
	if( !checkBlank($("#consoleUrl").val())  ){
		$("#msgBoxInfo").html("请输入控制台地址");
		$("#msgBox").modal('show');
		return;
	}
	//校验控制台地址的IP格式
	if( !checkIp() ){
		$("#msgBoxInfo").html("请输入合理的控制台IP地址");
		$("#msgBox").modal('show');
		return;
	}
	/*校验cpu输入框，必填项*/
	if( !checkBlank($("#cpu").val()) ){
		$("#msgBoxInfo").html("请输入cpu数目");
		$("#msgBox").modal('show');
		return;
	}
	if( !checkNum($("#cpu").val())){
		$("#msgBoxInfo").html("cpu数目只能为整数");
		$("#msgBox").modal('show');
		return;
	}
	var cpu =  Number($("#cpu").val());
	if( cpu > 128 || cpu <= 0 ){
		$("#msgBoxInfo").html("cpu数量不合理");
		$("#msgBox").modal('show');
		return;
	}
	/*校验memory输入，必填项*/
	if( !checkBlank($("#memory").val())  ){
		$("#msgBoxInfo").html("请输入内存大小");
		$("#msgBox").modal('show');
		return;
	}
	if( !checkNum($("#memory").val())){
		$("#msgBoxInfo").html("内存大小只能为整数");
		$("#msgBox").modal('show');
		return;
	}
	var memory =  Number($("#memory").val());
	if(memory > 2048 || memory <= 0){
		$("#msgBoxInfo").html("内存大小不合理");
		$("#msgBox").modal('show');
		return;
	}
	/*校验disk输入，必填项*/
	if( !checkBlank($("#disk").val())  ){
		$("#msgBoxInfo").html("请输入硬盘大小");
		$("#msgBox").modal('show');
		return;
	}
	if( !checkNum($("#disk").val())){
		$("#msgBoxInfo").html("硬盘大小只能为整数");
		$("#msgBox").modal('show');
		return;
	}
	var disk =  Number($("#disk").val());
	if(disk > 16 * 1024 || disk <= 0){
		$("#msgBoxInfo").html("硬盘大小不合理");
		$("#msgBox").modal('show');
		return;
	}
	/*校验用户账号和密码输入框，必填项*/
	var serverLoginIsFilled = checkBlank( $("#serverLogin").val() );//不为空时返回true
	var serverPwdIsFilled = checkBlank( $("#serverPwd").val() );   //不为空时返回true
	if( !serverLoginIsFilled ){
		$("#msgBoxInfo").html("请输入用户账号名");
		$("#msgBox").modal('show');
		return;
	}
	if( serverLoginIsFilled && ( !checkCharAndNum( $("#serverLogin").val()) ) || !checkLengthBetween($("#serverLogin").val() , 3, 20) ){
		$("#msgBoxInfo").html("用户帐号只允许数字字母,长度3-20位");
		$("#msgBox").modal('show');
		return;
	}
	if( !serverPwdIsFilled) {
		$("#msgBoxInfo").html("请输入用户台密码");
		$("#msgBox").modal('show');
		return;
	}
	if( serverPwdIsFilled && ( !checkCharAndNum( $("#serverPwd").val() ) || !checkLengthBetween($("#serverPwd").val() , 3, 20) )){
		$("#msgBoxInfo").html("用户密码只允许数字字母,长度3-20位");
		$("#msgBox").modal('show');
		return;
	}	
	/*校验控制台账号和密码输入框，非必填项，二者要么都不填，要么都填*/
	var consoleLoginIsFilled = checkBlank( $("#consoleLogin").val() );//为空时返回false
	var consolePwdIsFiled = checkBlank( $("#consolePwd").val() );	 //为空时返回false
	if( consoleLoginIsFilled && ( !checkCharAndNum( $("#consoleLogin").val()) || !checkLengthBetween($("#consoleLogin").val() , 3, 20)) ){
		$("#msgBoxInfo").html("控制台帐号只允许数字字母,长度3-20位");
		$("#msgBox").modal('show');
		return;
	}
	if( consoleLoginIsFilled && !consolePwdIsFiled) {
		$("#msgBoxInfo").html("请输入控制台密码");
		$("#msgBox").modal('show');
		return;
	}
	if(	consolePwdIsFiled && ( !checkCharAndNum( $("#consolePwd").val() ) || !checkLengthBetween($("#consolePwd").val() , 3, 20)) ){
		$("#msgBoxInfo").html("控制台密码只允许数字字母,长度3-20位");
		$("#msgBox").modal('show');
		return;
	}
	if( !consoleLoginIsFilled && consolePwdIsFiled) {
		$("#msgBoxInfo").html("请输入控制台账号");
		$("#msgBox").modal('show');
		return;
	}
		$.ajax({
			url : '${basePath}/pmInfo/updatePM',
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
			 <input type="hidden" id="id" name="id" value="${pm.id }" />	
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label"> <span
					class="up-cq-red-star">*</span>服务器IP
				</label>
				<div class="uup-form-group">
					<div class="up-col-sm-3">
						<select name="ipSegmentId" id="ipSegmentId" class="up-form-control"
							style="width: 200px">
							<c:forEach var="serverIp" items="${ipList }">
								<option value="${serverIp.id}" <c:if test="${pm.ipSegmentId eq serverIp.id}"> selected="selected"</c:if> >
									${serverIp.paramName} 
								</option>
							</c:forEach>	
						</select>
					</div>
					<label for="" class="up-col-sm-2 up-control-label"> <span
						class="up-cq-red-star">*</span>IP末位
					</label>
					<div class="up-col-sm-3">
						<input type="text" class="up-form-control" id="ipNum" name="ipNum"
							placeholder="请输入IP末位" value="${pm.ipNum }">
					</div>
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>控制台地址
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="consoleUrl"
						name="consoleUrl" placeholder="请输入控制台地址" value="${pm.consoleUrl }">
				</div>
				<label for="" class="up-col-sm-2 up-control-label"> <span
					class="up-cq-red-star">*</span>CPU(个)
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="cpu" name="cpu"
						placeholder="请输入CPU个数" value="${pm.cpu }">
				</div>
			</div
			>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>内存(G)
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="memory"
						name="memory" placeholder="请输入内存大小" value="${pm.memory }">
				</div>
				<label for="" class="up-col-sm-2 up-control-label up-control-label2">
					<span class="up-cq-red-star">*</span>硬盘(G)
				</label>
				<div class="up-col-sm-3">
					<input type="text" class="up-form-control" id="disk" name="disk"
						placeholder="请输入磁盘大小" value="${pm.disk }">
				</div>
			</div>
			
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star"></span>控制台账号
				</label>
				<div class="up-col-sm-3">
					<input type="text" value="${pm.consoleLogin }" class="up-form-control" id="consoleLogin" name="consoleLogin" placeholder="请输入管理员账号" >
				</div>
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star"></span>控制台密码
				</label>
				<div class="up-col-sm-3">
					<input type="text"value="${pm.consolePwd }" class="up-form-control" id="consolePwd" name="consolePwd" placeholder="请输入用户账号" >
				</div>
			</div>
			<div class="up-form-group">
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>用户账号
				</label>
				<div class="up-col-sm-3">
					<input type="text" value="${pm.serverLogin }" class="up-form-control" id="serverLogin" name="serverLogin" placeholder="请输入用户密码" >
				</div>
				<label for="" class="up-col-sm-2 up-control-label">
					<span class="up-cq-red-star">*</span>用户密码
				</label>
				<div class="up-col-sm-3">
					<input type="text" value="${pm.serverPwd }" class="up-form-control" id="serverPwd" name="serverPwd" placeholder="请输入用户密码" >
				</div>
			</div>
		</form>
	</div>
	<div class="up-modal-footer up-modal-footer1">
		<button type="button" class="up-btn up-btn-primary" onClick="updatePM()">更新</button>
		<button type="button" class="up-btn up-btn-default" onClick="parent.window.hideDialog()">返回</button>
	</div>
	
	<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->
</body>
</html>