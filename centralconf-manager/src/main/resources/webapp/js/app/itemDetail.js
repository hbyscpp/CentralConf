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
	$('#itemform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			itemName : {
				message : '配置项名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '配置项名不能为空'
					}
				}
			},
			itemValue : {
				message : '配置项值验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '配置项值不能为空'
					}
				}
			},
			itemDesc : {
				message : '配置项描述验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '配置项值不能为空'
					}
				}
			}

		}
	});
	$("#itemClosed").click(function() {
		$('#itemform').data('bootstrapValidator').resetForm(true);
	})

	$('#comitemform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			key : {
				message : '公共配置项名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '公共配置项名不能为空'
					}
				}
			},
			value : {
				message : '公共配置项值验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '公共配置项值不能为空'
					}
				}
			}

		}
	});
	$("#comItemClosed").click(function() {
		$('#comitemform').data('bootstrapValidator').resetForm(true);
	})

	/***************************************************************************
	 * 表单验证结束
	 */

	$("#btn_add").click(function() {
		$("#appModal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加配置项");
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delItem",
			data : {
				id : $("#itemId").val()
			},
			success : function(data) {
				$('#itemListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			$("#appModal").modal('show');
			$("#clicktype").val("2");
			$('#itemIdForm').val(row.id);
			$('#itemNameForm').val(row.itemName);
			$('#itemValueForm').val(row.itemValue);
			$('#itemDescForm').val(row.itemDesc);
			$("#myModalLabel").text("修改配置项");
			// window.location.href = "index#toResourceEnv?resource=" + row.id;
			// var $a = $("<a>"); toResourceEnv
			// $a.attr('href', "index#toEnv?app=" + row.id);
			// $a[0].click();
		},
		'click .btn_delete' : function(e, value, row, index) {
			$("#mySmallModalLabel").text("配置项删除(" + row.itemName + ")")
			$('#sure').modal('show');
			$("#itemId").val(row.id);
		}

	};

	$("#itemListTable").bootstrapTable({
		url : "getEnvItem",
		toolbar : "#toolbar",
		sidePagination : "server",// 服务器分页
		contentType : 'application/x-www-form-urlencoded',
		// sortName: "rkey",//排序列
		striped : true,// 是否显示行间隔色
		search : true,// 搜索功能
		responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			if (res.code != 0) {
				return res;
			} else {
				console.log(res.data)
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
		queryParams : function(params) {
			params.appId = $("#appId").val();
			params.envId = $("#envId").val();
			return params;
		},
		pageSize : 10,// 每页行数
		pageNumber : 1,// 其实页
		method : "post",// 请求格式
		columns : [ {
			title : '属性名',
			field : 'itemName',
			width : '20%',
			align : 'center'
		}, {
			field : 'itemValue',
			title : '属性值',
			width : '20%',
			align : 'center'

		}, {
			field : 'itemDesc',
			title : '描述',
			width : '20%',
			align : 'center'
		}, {
			field : '',
			title : '操作',
			width : '30%',
			align : 'center',
			events : operateEvents,
			formatter : operateFormatter
		} ]
	});

	function operateFormatter(value, row, index) {
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>',
				'<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
	}

	function centerModals($model) {
		$model.each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
	}
	centerModals($('#appModal'));
	centerModals($('#sure'));
	centerModals($('#comItemModal'));
	centerModals($('#comSure'));

	$('#itemform').on('submit', function(e) {
		if ($(this).data('bootstrapValidator').isValid()) {
			e.preventDefault();
			var url = "addItem";
			if ($("#clicktype").val() != 1) {
				url = "updateItem";
			}
			$.ajax({
				type : "post",
				url : url,
				data : {
					envId : $("#envId").val(),
					id : $("#itemIdForm").val(),
					itemName : $('#itemNameForm').val(),
					itemValue : $('#itemValueForm').val(),
					itemDesc : $('#itemDescForm').val(),
					type : 1
				},
				success : function(data) {
					$('#itemListTable').bootstrapTable('refresh');
					$('#itemform').data('bootstrapValidator').resetForm(true);
					$('#appModal').modal('hide');
					message(data);
				}
			});
		}
	})
	$("#itemSubmit").click(function() {
		$("#itemform").submit();
	})

	/**
	 * 公共配置项
	 */

	window.comOperateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			$("#comItemModal").modal('show');
			$("#comclicktype").val("2");
			$('#comitemNameForm').val(row.itemName);
			$('#comitemValueForm').val(row.itemValue);
			$('#comitemDescForm').val(row.itemDesc);
			$("#comitemIdForm").val(row.id);
			$("#comItemLabel").text("修改公共配置项");
		},
		'click .btn_delete' : function(e, value, row, index) {
			$("#comSmallModalLabel").text("公共配置项删除(" + row.itemName + ")")
			$('#comSure').modal('show');
			$("#comitemId").val(row.id);
		}

	};
	$("#comitemListTable").bootstrapTable({
		url : "getCommonItem",
		toolbar : "#comtoolbar",
		sidePagination : "server",// 服务器分页
		contentType : 'application/x-www-form-urlencoded',
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
		queryParams : function(params) {
			params.appId = $("#appId").val();
			return params;
		},// 携带参数
		pageSize : 1000,// 每页行数
		pageNumber : 1,// 其实页
		method : "post",// 请求格式
		columns : [ {
			title : '属性名',
			field : 'itemName',
			width : '20%',
			align : 'center'
		}, {
			field : 'itemValue',
			title : '属性值',
			width : '20%',
			align : 'center'

		}, {
			field : 'itemDesc',
			title : '描述',
			width : '20%',
			align : 'center'
		}, {
			field : '',
			title : '操作',
			width : '30%',
			align : 'center',
			events : comOperateEvents,
			formatter : comOperateFormatter
		} ]
	});

	function comOperateFormatter(value, row, index) {
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>',
				'<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
	}

	$("#btn_comadd").click(function() {
		$("#comItemModal").modal('show');
		$("#comclicktype").val("1");
		$("#comItemLabel").text("公共配置项添加");
	})

	$("#comitemform").on("submit", function() {
		if ($(this).data('bootstrapValidator').isValid()) {
			var url = "addCommonItem";
			if ($("#comclicktype").val() != 1) {
				url = "updateCommonItem";
			}
			$.ajax({
				type : "post",
				url : url,
				data : {
					appId : $("#appId").val(),
					id : $("#comitemIdForm").val(),
					itemName : $('#comitemNameForm').val(),
					itemValue : $('#comitemValueForm').val(),
					itemDesc : $('#comitemDescForm').val()
				},
				success : function(data) {
					$('#comitemListTable').bootstrapTable('refresh');
					$('#comitemform').data('bootstrapValidator').resetForm(true);
					$('#comItemModal').modal('hide');
					message(data);
				}
			});
		}
	})

	$("#comItemSubmit").click(function() {
		$("#comitemform").submit();
	})

	$("#comsuresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delCommonItem",
			data : {
				id : $("#comitemId").val()
			},
			success : function(data) {
				$('#comitemListTable').bootstrapTable('refresh');
				$('#comSure').modal('hide');
				message(data);
			}
		});
	})

	/**
	 * 资源列表
	 */
	$("#resourceItemListTable").bootstrapTable({
		url : "getRelationResourceItem",
		sidePagination : "server",// 服务器分页
		contentType : 'application/x-www-form-urlencoded',
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
		queryParams : function(params) {
			params.appId = $("#appId").val();
			params.envId = $("#envId").val();
			return params;
		},// 携带参数
		pageSize : 1000,// 每页行数
		pageNumber : 1,// 其实页
		method : "post",// 请求格式
		columns : [ {
			title : '资源名',
			field : 'resName',
			width : '20%',
			align : 'center'
		}, {
			title : '资源环境名',
			field : 'resEnvName',
			width : '20%',
			align : 'center'
		}, {
			title : '属性名',
			field : 'itemName',
			width : '20%',
			align : 'center'
		}, {
			field : 'itemValue',
			title : '属性值',
			width : '20%',
			align : 'center'

		}, {
			field : 'itemDesc',
			title : '描述',
			width : '20%',
			align : 'center'
		} ]
	});

	/**
	 * 提示信息
	 */
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
		});
	}

})