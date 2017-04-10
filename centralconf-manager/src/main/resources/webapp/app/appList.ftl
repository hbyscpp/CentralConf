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
<script src="js/plugin/bootstrapvalidator/language/zh_CN.min.js" type="text/javascript"></script>
<script src="js/app/appList.js" type="text/javascript"></script>
<style type="text/css">
body td{
	height: 47px;
	font-size: 14px;
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
	<div class="modal fade" id="appModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">权限添加</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="appform">
						<div>
							<input type="hidden" class="form-control" id="clicktype"> <input type="hidden" class="form-control" id="formerId" name="id" />
						</div>
						<div class="form-group">
							<label>应用名</label> <input type="text" class="form-control" id="appNameForm" name="appName" />
							<label>应用标识号</label> <input type="text" class="form-control" id="appIdentifyIdFrom" name="appIdentifyId" />
							<label>描述</label> <input type="text" class="form-control" id="appDescForm" name="appDesc" />
							<label>负责人</label> <input type="text" class="form-control" id="liableUserIdForm" name="liableUserId" />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="appsubmit" class="btn btn-primary">保存</button>
					<button type="button" id="appClose" class="btn btn-default" data-dismiss="modal">关闭</button>
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
					<h4 class="modal-title" id="mySmallModalLabel">应用删除</h4>
					<input type="hidden" class="form-control" id="appId">
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