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
<script src="js/resource/resourceItemList.js" type="text/javascript"></script>
<style type="text/css">
body td {
	height: 47px;
	font-size: 14px;
}
</style>
</head>
<body>

	<input value="${env.resId}" type="hidden" id="resId">
	<input value="${env.envId}" type="hidden" id="envId">
	<div class="row">

		<!-- col -->
		<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
			<h1 class="page-title txt-color-blueDark">

				<!-- PAGE HEADER -->
				<i class="fa-fw fa fa-table"></i> APPS <span id="app_span">> <a href="#toResouce">应用（${env.resName}）</a>
				 > <a href="#toResourceEnv?resId=${env.resId}">环境（${env.envName}）</a></span> <span>> 配置项 </span>
			</h1>
		</div>

	</div>
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
					<h4 class="modal-title" id="myModalLabel">配置项添加</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="itemform">
						<div>
							<input type="hidden" class="form-control" id="clicktype"> <input type="hidden" class="form-control" id="itemIdForm" name="itemId" />
						</div>
						<div class="form-group">
							<label>配置项名</label> <input type="text" class="form-control" id="itemNameForm" name="itemName" />
						</div>
						<div class="form-group">
							<label>配置项值</label> <input type="text" class="form-control" id="itemValueForm" name="itemValue" />
						</div>
						<div class="form-group">
							<label>描述</label> <input type="text" class="form-control" id="itemDescForm" name="itemDesc" />
						</div>
						<div class="form-group">
							<label>描述</label> <input type="text" class="form-control" id="isDefaultForm" name="isDefault" />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="itemsubmit" class="btn btn-primary">保存</button>
					<button type="button" id="itemClosed" class="btn btn-default" data-dismiss="modal">关闭</button>
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
					<input type="hidden" class="form-control" id="itemId">
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