<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	$(function() {
		var paramType = $("#paramType").val();
		if (paramType != 0 && paramType != 7) {
			$("#btnAdd").attr("disabled", false);
		} else {
			$("#btnAdd").attr("disabled", true);
		}
		$('#paramType').change(function() {
			var paramType = $("#paramType").val();
			if (paramType != 0 && paramType != 7) {
				$("#btnAdd").attr("disabled", false);
			} else {
				$("#btnAdd").attr("disabled", true);
			}
			window.location.href = '${basePath }/paramManage/showList?pageNum=1&paramType='+paramType;
		})
	})
	function deleteParam(paramId) {
		$("#msgBoxConfirmInfo").html("确定要删除该参数吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click', function() {
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/paramManage/deleteParam',
				data : {
					'paramId' : paramId
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click', function() {
							window.location.href = '${basePath }/paramManage/showList?pageNum=1';
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
		});

	}
	
	function alterParam(paramId) {
			$.ajax({
				type : 'POST',
				url : '${basePath}/paramManage/alterParam',
				data : {
					'paramId' : paramId
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click', function() {
							window.location.reload();
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
	
	function addParam() {
		var paramType = $("#paramType").val();
		if(paramType <=3){
			showDialog('新增参数', '${basePath}/paramManage/addParam?paramTypeIndex='
					+ paramType, '350px');
		}else{
			showDialog('新增参数', '${basePath}/paramManage/addParam?paramTypeIndex='
					+ paramType, '230px');
		}
	}
	function showDialog(title, url, height) {
		$("#modalDialogTitle").html(title);
		$("#modalDialogFrame").attr("height", height);
		$("#modalDialogFrame").attr("src", url);
		$("#modalDialog").modal('show');
	}

	function hideDialog() {
		$("#modalDialog").modal('hide');
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
		<div class="up-tab-content">
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 参数管理
					</h4>
				</div>
				<div class="up_page_con">
					<div class="ex_tab2" role="tabpanel"
						data-example-id="togglable-tabs">
						<div id="" class="up-tab-content">
							<div role="tabpanel" class="up-tab-pane up-active" id="mytab11"
								aria-labelledby="mytab11-tab">
								<div class="up-table-responsive">
									<div class="up-cq-table">
										<div class="up-cq-table-outer">
											<div class="up-cq-table-inner">
												<div class="up-clearfix table_head margin_bottom10">
													<div class="reference_search">
														<form class="up-form-inline" id="searchForm"
															action="${basePath }/paramManage/showList">
															<input type="hidden" id="pageNum" name="pageNum"
																value="1"> <select name="paramType"
																id="paramType" class="up-form-control"
																value="${searchParam.paramType }"
																style="width: 200px; text-align: center;">

																<option value="0">--全部--</option>
																<c:set value="0" var="index" />
																<c:forEach var="type" items="${paramTypeList }">
																	<c:set value="${index + 1}" var="index" />
																	<option value=${index }
																		<c:if test="${searchParam.paramType eq index}">selected</c:if>>${type }</option>
																</c:forEach>
															</select>
															<div class="up-form-group" style="margin-left: 20px">
																<button type="submit" class="up-btn up-btn-primary">搜索</button>
																<button type="button" id="btnAdd" name="btnAdd"
																	class="up-btn up-btn-primary up-btn-primary-red"
																	style="margin-left: 20px" data-toggle="modal"
																	onclick="addParam()">新增</button>
															</div>
														</form>
													</div>
												</div>
												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>参数类型</th>
															<th>参数名称</th>
															<th>参数值</th>
															<th>起始ip</th>
															<th>结束ip</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="paramInfo" items="${page.dataList }">
															<tr>
																<td>${paramInfo.paramType }</td>
																<td>${paramInfo.paramName }</td>
																<td>${paramInfo.paramValue }</td>
																<td>${paramInfo.ipBegin }</td>
																<td>${paramInfo.ipEnd }</td>
																<td>
																	<c:if test="${paramInfo.paramType ne 'audit_state' and paramInfo.paramType ne 'ip_require_msg' and paramInfo.paramType ne 'mail_config'}">
																		<a href="javascript:void(0)"onClick="deleteParam('${paramInfo.id}')"> 删除</a>
																	</c:if>
																	<c:if test="${paramInfo.paramType eq 'user_ip' or paramInfo.paramType eq 'vm_ip' or paramInfo.paramType eq 'server_ip' or paramInfo.paramType eq 'mail_config'}">
																		<a href="javascript:void(0)" onClick="showDialog('编辑系统参数' , '${basePath}/paramManage/alterParam?paramId=${paramInfo.id}' , '320px')">修改 </a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>

												<div class="up-clearfix">
													<div class="up-pull-right">
														<%@include file="/common/page.jsp"%>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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
						<h4 class="up-modal-title" id="modalDialogTitle">新增物理机</h4>
					</div>
					<iframe id="modalDialogFrame" width="100%" height="420px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>

	</div>
</body>

</html>
