$(function() {
	var selections = [];
	var app_selections = [];
	var app_env = {};
	var $table = $('#userListTable');
	var $permtable = $("#permListTable");
	var perm_selections = [];
	// Messenger().post("Your request has succeded!");
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	var sprintf = function(str) {
		var args = arguments, flag = true, i = 1;

		str = str.replace(/%s/g, function() {
			var arg = args[i++];

			if (typeof arg === 'undefined') {
				flag = false;
				return '';
			}
			return arg;
		});
		return flag ? str : '';
	};

	/***************************************************************************
	 * 表单验证开始
	 */

	$('#userform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			userName : {
				message : '用户账号验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_-]+$/,
						message : '只能是数字和字母及部特殊符号（-_）'
					},
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			},
			realName : {
				message : '用户名验证失败',
				validators : {
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			},
			department : {
				message : '部门验证失败',
				validators : {
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			},
			mobilePhone : {
				message : '电话验证失败',
				regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
					regexp : /^1[34578]\d{9}$/,
					message : '只能是数字和字母及部特殊符号（-_.）'
				},
				validators : {
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			}
		}
	});

	$("#userColsed").click(function() {
		$('#userform').data('bootstrapValidator').resetForm(true);
	})

	/***************************************************************************
	 * 表单验证结束
	 */

	$("#btn_add").click(function() {
		$("#usermodal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加用户");
		$("#userName").removeAttr("readonly");
		reset();
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delUser",
			data : {
				id : $("#deluserId").val()
			},
			success : function(data) {
				if (data.code == 401) {
					location.reload();
					return;
				}
				$('#userListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			e.stopPropagation();
			$("#usermodal").modal('show');
			$("#clicktype").val("2");
			$("#myModalLabel").text("修改用户");
			$("#userName").val(row.userName);
			$("#realName").val(row.realName);
			$("#department").val(row.department);
			$("#mobilePhone").val(row.mobile);
			$("#userName").attr("readonly", "readonly");
			$("#userId").val(row.id);
		},
		'click .btn_delete' : function(e, value, row, index) {
			e.stopPropagation();
			$('#sure').modal('show');
			$("#deluserId").val(row.id);
		},
		'click .btn_perms' : function(e, value, row, index) {
			e.stopPropagation();
			$('#saveUserApp').val(row.userName);
			$.ajax({
				type : "post",
				url : 'getPermApp',
				async : false,
				data : {
					userName : row.userName
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					app_selections = [];
					if (typeof data.data != 'undefined') {
						app_env = data.data;
						$.each(data.data, function(i, val) {
							app_selections.push(i);
						});
					}
					message(data);
					$('#itemTable').bootstrapTable('refresh');
					$("#userPermLabel").text('用户授权(' + row.userName + ')');
					$('#userPerm').modal('show');
				}
			}).then();

		},
		'click .btn_visit' : function(e, value, row, index) {
			e.stopPropagation();
			$.ajax({
				type : "post",
				url : 'getUserPerm',
				async : false,
				data : {
					userId : row.id
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					perm_selections = [];
					if (typeof data.data != 'undefined') {
						perm_selections = data.data;
					}
					$('#saveUserPerm').val(row.id);
					$('#VisitPerm').modal('show');
					$('#permListTable').bootstrapTable('refresh')
					message(data);
				}
			}).then();
		}
	};

	$("#userListTable").bootstrapTable({
		url : "getUserByPage",
		toolbar : "#toolbar",
		sidePagination : "server",// 服务器
		responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			if (res.code != 0) {
				if (res.code == 401) {
					location.reload();
					return;
				}
				message(res);
				return res;
			} else {
				$.each(res.data.rows, function(i, row) {
					row.state = $.inArray(row.userName, selections) !== -1;
				});
				return res.data;
			}
		},
		// sortName: "rkey",//排序列
		striped : true,// 是否显示行间隔色
		search : true,// 搜索功能
		// minimumCountColumns: 2, //最少允许的列数
		showRefresh : true,// 刷新功能
		clickToSelect : true,// 选择行即选择checkbox
		// singleSelect : true,// 仅允许单选
		// searchOnEnterKey : true,// ENTER键搜索
		pagination : true,// 启用分页
		// escape : false,// 过滤危险字符
		showColumns : true,// 是否显示所有的列
		showToggle : true, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		// queryParams: getParams,//携带参数
		pageSize : 1000,// 每页行数
		pageIndex : 0,// 其实页
		method : "get",// 请求格式
		columns : [ {
			title : 'id',
			field : 'id',
			width : '15%',
			align : 'center',
			valign : 'middle'
		}, {
			title : '登录账户',
			field : 'userName',
			align : 'center',
			width : '15%',
			searchable : true,
			valign : 'middle'
		}, {
			title : '用户名',
			field : 'realName',
			align : 'center',
			width : '15%',
			valign : 'middle'
		}, {
			title : '部门',
			field : 'department',
			align : 'center',
			width : '10%',
			valign : 'middle'
		}, {
			title : '电话',
			field : 'mobile',
			align : 'center',
			width : '10%',
			valign : 'middle'
		},  {
			title : '账号级别',
			field : 'level',
			align : 'center',
			width : '10%',
			valign : 'middle'
		}, {
			field : 'operate',
			title : '操作',
			width : '25%',
			align : 'center',
			events : operateEvents,
			formatter : operateFormatter
		} ]
	});

	function operateFormatter(value, row, index) {
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>', '<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>',
				'<button type="button" class="btn_visit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>用户访问授权</button>', '<button type="button" class="btn_perms btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-cog" aria-hidden="true">授权</span></button>' ].join('');
	}

	function centerModals() {
		$('#usermodal').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
		$('#sure').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
		$('#userPerm').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
//		$('#userPerm').each(function(i) {
//			var $clone = $(this).clone().css('display', 'block').appendTo('body');
//			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
//			top = top > 0 ? top : 0;
//			$clone.remove();
//			$(this).find('.modal-content').css("margin-top", top);
//		});
//		$('#VisitPerm').each(function(i) {
//			var $clone = $(this).clone().css('display', 'block').appendTo('body');
//			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
//			top = top > 0 ? top : 0;
//			$clone.remove();
//			$(this).find('.modal-content').css("margin-top", top);
//		});
		

	}
	// $('#usermodal').on('show.bs.modal', centerModals);
	centerModals();

	$('#userform').on('submit', function(e) {
		e.preventDefault();
		if ($(this).data('bootstrapValidator').isValid()) {
			var url = "createUser";
			if ($("#clicktype").val() != 1) {
				url = "updateUser";
			}
			$.ajax({
				type : "post",
				url : url,
				data : $("#userform").serializeObject(),
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					$('#userListTable').bootstrapTable('refresh');
					$('#userform').data('bootstrapValidator').resetForm(true);
					$('#usermodal').modal('hide');
					message(data);
					reset();
				}
			});
		}
	})
	$("#usersubmit").click(function() {
		$("#userform").submit();
	})

	var reset = function() {
		$("#userName").val("");
		$("#realName").val("");
		$("#department").val("");
		$("#mobilePhone").val("");
	}
	Messenger.options = {
		extraClasses : 'messenger-fixed messenger-on-top messenger-on-right',
		theme : 'future'
	}
	var message = function(data) {
		var type = "info";
		if (data.code != 0) {
			tyep = "error";
		}
		Messenger().post({
			message : data.msg,
			type : type,
			showCloseButton : true
		// extraClasses : 'messenger-fixed messenger-on-top messenger-on-right',
		// theme : 'Block'
		});
	}

	$table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
		selections = getIdSelections();
	});
	function getIdSelections() {
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.userName;
		});
	}

	/**
	 * 授权开始
	 */
	/** ************************************************************************ */

	/**
	 * 应用开始
	 */
	initTable();
	function initTable() {
		// alert(JSON.stringify(app_env));
		// alert(JSON.stringify(app_selections));
		$("#userPermSubmit").click(function() {
			$.ajax({
				type : "post",
				url : 'savePermApp',
				data : {
					value : JSON.stringify(app_env),
					userName : $('#saveUserApp').val()
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					message(data);
					$('#userPerm').modal('hide');
				}
			});

		});
		window.appEvents = {
			'click .btn-default' : function(e, value, row, index) {
				e.stopPropagation();
				var $this = $(this);
				var env = $this.text();
				if ($this.hasClass('btn-success')) {
					delete app_env[row.app][env];
					var app_env_flag = true;
					for ( var i in app_env[row.app]) {
						app_env_flag = false;
					}
					if (app_env_flag) {
						// if(!$tr.hasClass('selected')){
						// }
						delete app_env[row.app];
						var $tr = $this.parent().parent();
						$tr.removeClass('selected');
						var $selectItem = $tr.find(sprintf('[name="%s"]', "btSelectItem"));
						if ($selectItem.length) {
							$selectItem[0].click();
						}
					}
					$this.removeClass('btn-success');
				} else {
					if (typeof app_env[row.app] == "undefined") {
						app_env[row.app] = {};
					}
					app_env[row.app][env] = true;
					var $tr = $this.parent().parent();
					if (!$tr.hasClass('selected')) {
						$tr.addClass('selected');
						var $selectItem = $tr.find(sprintf('[name="%s"]', "btSelectItem"));
						if ($selectItem.length) {
							$selectItem[0].click();
						}
					}
					$this.addClass('btn-success');
				}

			}

		};

		$("#itemTable").bootstrapTable({
			url : "getAllIteam",
			sidePagination : "server",// 服务器
			responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
				if (res.code != 0) {
					if (res.code == 401) {
						location.reload();
						return;
					}
					message(res);
					return res;
				} else {
					$.each(res.data.rows, function(i, row) {
						row.state = $.inArray(row.app, app_selections) !== -1;
					});
					return res.data;
				}
			},
			height : 300,
			striped : true,// 是否显示行间隔色
			search : true,// 搜索功能
			// minimumCountColumns: 2, //最少允许的列数
			showRefresh : true,// 刷新功能
			clickToSelect : true,// 选择行即选择checkbox
			pagination : true,// 启用分页
			showColumns : true,// 是否显示所有的列
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			pageSize : 1000,// 每页行数
			pageIndex : 0,// 其实页
			method : "get",// 请求格式
			columns : [ {
				field : 'state',
				checkbox : true,
				align : 'center',
				valign : 'middle'
			}, {
				title : 'app',
				field : 'app',
				width : '35%',
				align : 'center',
				valign : 'middle'
			}, {
				title : '环境',
				field : 'env',
				align : 'center',
				width : '35%',
				searchable : true,
				valign : 'middle',
				events : appEvents,
				formatter : appFormatter
			} ],
			// onClickRow:function(row, $element){
			// // debugger
			// // alert($element);
			// if($element.hasClass('selected')){
			// debugger
			// $element.find('button').removeClass('btn-success');
			// }
			// },
			onUncheck : function(row, args) {
				delete app_env[row.app];
				$(args[0]).closest('tr').find('button').removeClass('btn-success');
			},
			onCheck : function(row, args) {
				// $(args[0]).closest('tr').find('button').removeClass('btn-success');
				// alert(1);
				if (typeof app_env[row.app] == "undefined") {
					app_env[row.app] = {};
				}
			}
		});
		function appFormatter(value, row, index) {
			if (value == null || value == "") {
				return [ '-' ].join('');
			}
			var buttonValue = "";
			for (var i = 0; i < value.length; i++) {
				if (typeof app_env[row.app] != "undefined" && app_env[row.app][value[i]] == true) {
					buttonValue += '<button type="button" class="button' + i + ' btn btn-default btn-success  btn-sm" style="margin-right:10px;">' + value[i] + '</button>';
				} else {
					buttonValue += '<button type="button" class="button' + i + ' btn btn-default   btn-sm" style="margin-right:10px;">' + value[i] + '</button>';
				}
			}
			// return [ '<button type="button" class="btn_edit btn btn-default
			// btn-sm" style="margin-right:10px;">' + value + '</button>'
			// ].join('');
			return [ '' + buttonValue ].join('');
		}

		$("#itemTable").on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
			app_selections = getSelections($("#itemTable"), "app");
		});

		function getSelections($table, cloum) {
			return $.map($table.bootstrapTable('getSelections'), function(row) {
				return row[cloum];
			});
		}
		/**
		 * 应用结束
		 */

		/**
		 * 访问权限开始
		 */
		$("#permListTable").bootstrapTable({
			url : "getAllPermByPage",
			sidePagination : "server",// 服务器分页
			// sortName: "rkey",//排序列
			striped : true,// 是否显示行间隔色
			search : true,// 搜索功能
			responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
				if (res.code != 0) {
					if (res.code == 401) {
						location.reload();
						return;
					}
					message(res);
					return res;
				} else {
					$.each(res.data.rows, function(i, row) {
						row.state = $.inArray(row.id, perm_selections) !== -1;
					});
					return res.data;
				}
			},
			// minimumCountColumns: 2, //最少允许的列数
			showRefresh : true,// 刷新功能
			clickToSelect : true,// 选择行即选择checkbox
			singleSelect : false,// 仅允许单选
			// searchOnEnterKey: true,//ENTER键搜索
			pagination : true,// 启用分页
			escape : false,// 过滤危险字符
			showColumns : true,// 是否显示所有的列
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			// queryParams: getParams,//携带参数
			pageSize : 1000,// 每页行数
			pageIndex : 0,// 其实页
			method : "get",// 请求格式
			columns : [ {
				field : 'state',
				checkbox : true,
				align : 'center',
				width : '5%',
				valign : 'middle'
			}, {
				title : 'id',
				field : 'id',
				width : '10%',
				align : 'center',
				valign : 'middle',
				visible : false

			}, {
				title : '权限名',
				field : 'permissionName',
				align : 'center',
				width : '10%',
				valign : 'middle'
			}, {
				title : '权限描述',
				field : 'permissionDescribe',
				align : 'center',
				width : '15%',
				valign : 'middle'
			}, {
				title : '拦截url',
				field : 'url',
				align : 'center',
				width : '15%',
				valign : 'middle'
			}, {
				title : '类型',
				field : 'type',
				align : 'center',
				width : '5%',
				valign : 'middle',
				formatter : function(value, row, index) {
					if (value == 1) {
						return "菜单";
					} else if (value == 2) {
						return "按钮";
					} else {
						return "接口";
					}
				}

			}, {
				field : 'perms',
				title : '认证权限',
				width : '10%',
				align : 'center',
			}, {
				field : 'userPerm',
				title : '用户所需权限',
				width : '10%',
				align : 'center',
			}, {
				field : 'sort',
				title : '排序',
				width : '5%',
				align : 'center',
			}, {
				field : 'parentId',
				title : '上级菜单',
				width : '10%',
				align : 'center',
			} ],
			onPageChange : function(number, size) {
				// currPageIndex = number;
				// currLimit = size
			},
			onLoadSuccess : function() {
				// $("#searchBtn").button('reset');
			}
		});

		$permtable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
			perm_selections = getSelections($permtable, "id");
		});

		$("#visitPermSubmit").click(function() {
			$.ajax({
//				contentType:"application/json; charset=utf-8",
				type : "post",
				url : 'savePerm',
				data : {
					value : JSON.stringify(perm_selections),
					userId : $('#saveUserPerm').val()
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					message(data);
					$('#VisitPerm').modal('hide');
				}
			});

		});

		/**
		 * 访问权限结束
		 */

	}

	/** ************************************************************************ */
	/**
	 * 授权结束
	 */

})