<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body >
	<div class="up-cq-table-inner" >
		<div class="up-modal-body" >
			<div class="up-form-horizontal"  style = "padding-left:130px">
				<div class="up-form-group">
					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>服务器地址:
					</label>
					<div class="up-col-sm-4">${pm.fullIp }</div>
					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>控制台地址:
					</label>
					<div class="up-col-sm-2">${pm.consoleUrl }</div>
				</div>

				<div class="up-form-group">
					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>控制台账号:
					</label>
					<div class="up-col-sm-4">${pm.consoleLogin }</div>

					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>控制台密码:
					</label>
					<div class="up-col-sm-2">${pm.consolePwd }</div>
				</div>

				<div class="up-form-group">
					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>服务器账号:
					</label>
					<div class="up-col-sm-4">${pm.serverLogin }</div>
					<label for=""
						class="up-col-sm-2 up-control-label up-control-label2"> <span
						class="up-cq-red-star"></span>服务器密码:
					</label>
					<div class="up-col-sm-2">${pm.serverPwd }</div>
				</div>
				
				<div class="up-form-group">
					<div class="up-col-sm-9">
						<table
							class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
							<thead>
								<td>服务器资源</td>
								<td>CPU(个)</td>
								<td>内存(GB)</td>
								<td>硬盘(GB)</td>
							</thead>
							<tbody>
								<tr class="up-active">
									<td>总量</td>
									<td>${pm.cpu}</td>
									<td>${pm.memory}</td>
									<td>${pm.disk}</td>
								</tr>
								<tr class="up-active">
									<td>剩余量</td>
									<td>${pm.cpu - pm.cpuUsed}</td>
									<td>${pm.memory - pm.memoryUsed}</td>
									<td>${pm.disk - pm.diskUsed}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="up-modal-footer up-modal-footer1">
			<button type="button" class="up-btn up-btn-default"
				onClick="parent.window.hideDialog()">关闭</button>
		</div>
	</div>
		<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->
</body>
</html>