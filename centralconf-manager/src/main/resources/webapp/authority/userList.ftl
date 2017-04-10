<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-table.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger-theme-future.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger.css" />
<!-- <script src="js/libs/jquery-2.1.1.min.js" type="text/javascript"></script> -->
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="js/plugin/messenger/js/messenger.min.js" type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/bootstrapValidator.js" type="text/javascript"></script>
<script src="js/authority/userList.js" type="text/javascript"></script>
</head>
<body>
	<div id="toolbar" class="btn-group">
		<button id="btn_add" type="button" class="btn btn-default">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
		<!-- 				<button id="btn_edit" type="button" class="btn btn-default"> -->
		<!-- 					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改 -->
		<!-- 				</button> -->
		<!-- 				<button id="btn_delete" type="button" class="btn btn-default"> -->
		<!-- 					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除 -->
		<!-- 				</button> -->
	</div>
	<table id="userListTable">
	</table>

	<!-- Button trigger modal -->
	<!-- <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
				  Launch demo modal
				</button> -->

	<!-- Modal -->
	<div class="modal fade" id="usermodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">用户添加</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="userform">
						<div>
							<input type="hidden" class="form-control" id="clicktype">
							<input type="hidden" class="form-control" name="id" id="userId" >
						</div>
						<div class="form-group">
							<label>用户账号</label> <input type="text" class="form-control" id="userName" name="userName" />
						</div>
						<div class="form-group">
							<label>用户名</label> <input type="text" class="form-control" id="realName" name="realName" />
						</div>
						<div class="form-group">
							<label>部门</label> <input type="text" class="form-control" id="department" name="department">
						</div>
						<div class="form-group">
							<label>电话</label> <input type="text" class="form-control" id="mobilePhone" name="mobile">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="usersubmit" class="btn btn-primary">保存</button>
					<button type="button" id="userColsed" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div id="sure" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">用户删除</h4>
					<input type="hidden" class="form-control" id="deluserId">
				</div>
				<div class="modal-footer">
					<button type="button" id="suresubmit" class="btn btn-primary">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade bs-example-modal-lg" id="userPerm" tabindex="-1" role="dialog" aria-labelledby="userPermLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="userPermLabel">用户授权</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" class="form-control" id="saveUserApp">
					<table id="itemTable">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="userPermSubmit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade bs-example-modal-lg" id="VisitPerm" tabindex="-1" role="dialog" aria-labelledby="userVisitPermLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="userVisitPermLabel">用户访问授权</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" class="form-control" id="saveUserPerm">
					
					<table id="permListTable">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="visitPermSubmit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>