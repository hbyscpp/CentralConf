<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>权限</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-table.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger-theme-future.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger.css" />
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="js/plugin/messenger/js/messenger.min.js" type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/bootstrapValidator.js" type="text/javascript"></script>
<script src="js/authority/permList.js" type="text/javascript"></script>
<style type="text/css">
.selectpicker {
	height: 32px;
}
</style>
</head>
<body>


	<div id="toolbar" class="btn-group">
		<button id="btn_add" type="button" class="btn btn-default">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
	</div>
	<table id="permListTable">
	</table>

	<!-- Button trigger modal -->
	<!-- <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
				  Launch demo modal
				</button> -->

	<!-- Modal -->
	<div class="modal fade" id="permModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">权限添加</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="permform">
						<div>
							<input type="hidden" class="form-control" id="clicktype"> <input type="hidden" class="form-control" id="permid" name="id" />
						</div>
						<div class="form-group">
							<label>权限名</label> <input type="text" class="form-control" id="permissionName" name="permissionName" />
						</div>
						<div class="form-group">
							<label>权限描述</label> <input type="text" class="form-control" id="permissionDescribe" name="permissionDescribe" />
						</div>
						<div class="form-group">
							<label>拦截url</label> <input type="text" class="form-control" id="url" name="url">
						</div>
						<div class="form-group">
							<label>类型</label> <select id="type" name="type" class="form-control">
								<option value="1">菜单</option>
								<option value="2">按钮</option>
								<option value="3">接口</option>
							</select>
						</div>
						<div class="form-group">
							<label>认证权限</label> <input type="text" class="form-control" id="perms" name="perms">
						</div>
						<div class="form-group">
							<label>用户所需权限</label> <input type="text" class="form-control" id="userPerm" name="userPerm">
						</div>
						<div class="form-group">
							<label>排序</label> <input type="text" class="form-control" id="sort" name="sort">
						</div>
						<div class="form-group">
							<label>上级菜单</label> <select id="parentId" name="parentId" class="form-control selectpicker">
								<option value="-1">无</option>
							</select>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="usersubmit" class="btn btn-primary">保存</button>
					<button type="button" id="permClosed" class="btn btn-default" data-dismiss="modal">关闭</button>
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
					<h4 class="modal-title" id="myModalLabel">权限删除</h4>
					<input type="hidden" class="form-control" id="delpermId">
				</div>
				<div class="modal-footer">
					<button type="button" id="suresubmit" class="btn btn-primary">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>