$(function() {
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

	/***************************************************************************
	 * 表单验证开始
	 */
	$('#permform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			permissionName : {
				message : '权限名验证失败',
				validators : {
					notEmpty : {
						message : '权限名不能为空'
					}
				}
			},
			permissionDescribe : {
				message : '权限描述验证失败',
				validators : {
					notEmpty : {
						message : '权限描述不能为空'
					}
				}
			},
			url : {
				message : 'url验证失败',
				validators : {
					notEmpty : {
						message : 'url不能为空'
					}
				}
			},
			type : {
				message : '类型验证失败',
				validators : {
					notEmpty : {
						message : '类型不能为空'
					}
				}
			},
			perms : {
				message : '系统认证权限验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^perms\[[a-zA-Z0-9:._-]+\]$/,
						message : '只能是perms[xxxx]格式,xxx部仅支持特殊符号（:._-）'
					},
					perms:{
						 field: 'userPerm',
						 type:2,
						 message:"括号中的内容请与用户所需权限括保持一致"
					},
					notEmpty : {
						message : '系统认证权限不能为空'
					}
				}
			},
			userPerm : {
				message : '权限描述证失败',
				validators : {
					perms:{
						 field: 'perms',
						 type:1,
						 message:"请与系统认证权限括号中内容保持一致"
					},
					notEmpty : {
						message : '权限描述不能为空'
					}
				}
			},
			sort : {
				message : '权限描述证失败',
				validators : {
					notEmpty : {
						message : '权限描述不能为空'
					}
				}
			},
			parentId : {
				message : '权限描述证失败',
				validators : {
					notEmpty : {
						message : '权限描述不能为空'
					}
				}
			}
		}
	});

	$("#permClosed").click(function() {
		$('#permform').data('bootstrapValidator').resetForm(true);
	})

	/***************************************************************************
	 * 表单验证结束
	 */

	var lodingPermList = function(data, trage, b, prefix) {
		var $_newOption = $("<option>")
		$_newOption.val(data.id);
		$_newOption.text(prefix + data.permissionName);
		$_newOption.append('<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>');
		if (b) {
			trage.append($_newOption);
		} else {
			trage.after($_newOption);
		}
		if (data.sunPerm.length > 0) {
			prefix += "--";
			for (var i = 0; i < data.sunPerm.length; i++) {
				lodingPermList(data.sunPerm[i], $_newOption, false, prefix)
			}
		}
	}

	$.ajax({
		type : "post",
		url : "findFoldPermList",
		success : function(data) {
			if (data.code == 0) {
				console.log(data);
				data = data.data;
				for (var i = 0; i < data.length; i++) {
					lodingPermList(data[i], $("#parentId"), true, "")
				}
			}
		}
	});

	$("#btn_add").click(function() {
		$("#permModal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加权限");
		// $("#userName").removeAttr("readonly");
		reset();
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delPerm",
			data : {
				id : $("#delpermId").val()
			},
			success : function(data) {
				$('#permListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			$("#permModal").modal('show');
			$("#clicktype").val("2");
			$("#myModalLabel").text("修改权限");

			$("#permid").val(row.id);
			$("#permissionName").val(row.permissionName);
			$("#permissionDescribe").val(row.permissionDescribe);
			$("#url").val(row.url);
			$("#type").val(row.type);
			$("#perms").val(row.perms);
			$("#userPerm").val(row.userPerm);
			$("#sort").val(row.sort);
			$("#parentId").val(row.parentId);
		},
		'click .btn_delete' : function(e, value, row, index) {
			$('#sure').modal('show');
			$("#delpermId").val(row.id);
		}

	};

	$("#permListTable").bootstrapTable({
		url : "getAllPermByPage",
		toolbar : "#toolbar",
		sidePagination : "server",// 服务器分页
		// sortName: "rkey",//排序列
		striped : true,// 是否显示行间隔色
		search : true,// 搜索功能
		responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			if (res.code != 0) {
				return res;
			} else {
				return res.data;
			}
		},
		// minimumCountColumns: 2, //最少允许的列数
		showRefresh : true,// 刷新功能
		clickToSelect : true,// 选择行即选择checkbox
		singleSelect : true,// 仅允许单选
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
		}, {
			field : '',
			title : '操作',
			width : '20%',
			align : 'center',
			events : operateEvents,
			formatter : operateFormatter
		} ],
		onPageChange : function(number, size) {
			// currPageIndex = number;
			// currLimit = size
		},
		onLoadSuccess : function() {
			// $("#searchBtn").button('reset');
		}
	});

	function operateFormatter(value, row, index) {
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>', '<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
	}

	function centerModals() {
		$('#permModal').each(function(i) {
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
	}
	// $('#permModal').on('show.bs.modal', centerModals);
	centerModals();

	$('#permform').on('submit', function(e) {
		var $that = $(this);
		e.preventDefault();
		if ($that.data('bootstrapValidator').isValid()) {
			var url = "createPerm";
			if ($("#clicktype").val() != 1) {
				url = "updatePerm";
			}
			$.ajax({
				type : "post",
				url : url,
				data : $("#permform").serializeObject(),
				success : function(data) {
					$('#permListTable').bootstrapTable('refresh');
					$that.data('bootstrapValidator').resetForm(true);
					$('#permModal').modal('hide');
					message(data);
					reset();
				}
			});
		}
	})
	$("#usersubmit").click(function() {
		$("#permform").submit();
	})

	var reset = function() {
		$("#permissionName").val("");
		$("#permissionDescribe").val("");
		$("#url").val("");
		$("#type").val("");
		$("#perms").val("");
		$("#userPerm").val("");
		$("#sort").val("");
		$("#parentId").val("");
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

})