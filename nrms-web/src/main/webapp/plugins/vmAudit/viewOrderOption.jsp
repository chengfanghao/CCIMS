<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<head>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<div class='up-tab-content'>
		<div class="up-tab-pane up-active" id="tabs1">
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
											<table
												class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
												<thead>
													<tr class="up-active">
														<th>操作人</th>
														<th>操作时间</th>
														<th>操作类型</th>
														<th>备注</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="opt" items="${orderOpList }">
														<tr>
															<td>${opt.operUserName }</td>
															<td>${opt.operDate }</td>
															<c:if test="${opt.operType le 198 and opt.operType gt 1}">
																<!-- 审批操作 -->
																<c:if test="${opt.operResult ==0 }">
																<!-- 成功 -->
																	<td>${opt.operUserName }审批通过</td>
																</c:if><c:if test="${opt.operResult ==1 }">
																<!-- 驳回 -->
																	<td>${opt.operUserName }驳回申请</td>
																</c:if>
															</c:if>
															<c:if test="${opt.operType == 1 }">
																<td>${opt.operUserName }提交申请</td>
															</c:if>
															<c:if test="${opt.operType == 200 }">
																<td>${opt.operUserName }完成部署</td>
															</c:if>
															<td>${opt.operFeedBack }</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<div class="up-modal-footer up-modal-footer1">
												<button type="button" class="up-btn up-btn-default"
													onClick="parent.window.hideDialog()">关闭</button>
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
</body>
</html>