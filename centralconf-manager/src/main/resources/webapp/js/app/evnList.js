$(function() {
	var $sourceTable = $("#sourceListTable");
	// var source_selections = [];
	// var source_item_selections = [];
//	var source_item_judge = {};
	var source_item_original = {};
	var source_env = {};
	var source_item = {};
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

	window.checkBoxOnclick = function(id, e) {
		var e = e || event;
		var $tr = $(e.target).parent();
		var $parentTr = $tr.parent().closest('tr').prev();
		var $input = $tr.find('input');
		var $buttion = $parentTr.find('.btn-success');
		var iid = $buttion.prop('id');
		// alert(JSON.stringify(source_item));
		if ($input.prop('checked')) {
			delete source_item[iid][id];
		} else {
			if (typeof source_item[iid] == 'undefined') {
				source_item[iid] = {};
			}
			source_item[iid][id] = true;
		}
		$input.prop('checked', !$input.prop('checked'));
		// alert(JSON.stringify(source_item));
	}

	/***************************************************************************
	 * 表单验证开始
	 */

	$('#evnform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				message : '应用名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			}
		}
	});

	$("#evnClosed").click(function() {
		$('#evnform').data('bootstrapValidator').resetForm(true);
	})

	$('#cloneForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			tarName : {
				message : '应用名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			},
			radio : {
				validators : {
					notEmpty : {
						message : '请选择覆盖方式'
					}
				}
			}
		}
	});

	$("#cloneClosed").click(function() {
		$('#cloneForm').data('bootstrapValidator').resetForm(true);
	})

	/***************************************************************************
	 * 表单验证结束
	 */

	$("#btn_add").click(function() {
		$("#evnModal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加环境");
		// $("#userName").removeAttr("readonly");
	})

	$("#btn_common").click(function() {
		// $a = $("<a>");
		// $a.attr('href', "index#toConfItem?env=_common&app=" +
		// $("#appName").val());
		// $a[0].click();
		window.location.href = "index#toCommonItemDetail?appId=" + $("#appId").val();
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delEnv",
			data : {
				envId : $("#envId").val(),
				appId : $("#appId").val()
			},
			success : function(data) {
				$('#evnListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			// $a = $("<a>");
			// $a.attr('href', "index#toConfItemDetail?env=" + row.id + "&app="
			// +
			// $("#appName").val());
			// $a[0].click();
			window.location.href = "index#toItemDetail?env=" + row.id + "&app=" + $("#appId").val();
		},
		'click .btn_delete' : function(e, value, row, index) {
			$("#mySmallModalLabel").text("应用删除环境(" + row.envName + ")")
			$('#sure').modal('show');
			$("#envId").val(row.id);
		},
		'click .btn_clone' : function(e, value, row, index) {
			$("#clone").modal('show');
			$("#cloneEvnName").val(row.id);
			// $("#userName").removeAttr("readonly");
			cloneRest();
		},
		'click .btn_common' : function(e, value, row, index) {
			e.stopPropagation();
			$('#envRelationId').val(row.id);
			$.ajax({
				type : "post",
				url : 'getRelativeResource',
				async : false,
				data : {
					appId : $("#appId").val(),
					envId : row.id
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					// source_selections = [];
					if (typeof data.data != 'undefined') {
						source_env = data.data;
						// $.each(data.data, function(i, val) {
						// source_selections.push(i);
						// });
					}
					message(data);
					$('#sourceListTable').bootstrapTable('refresh');
					$('#source').modal('show');
				}
			}).then(
					$.ajax({
						type : "post",
						url : 'getBindItem',
						async : false,
						data : {
							appId : $("#appId").val(),
							envId : row.id
						},
						success : function(data) {
							if (data.code == 401) {
								location.reload();
								return;
							}
							if (typeof data.data != 'undefined') {
								source_item = data.data;
							}
//							alert(JSON.stringify(source_item_original));
						}
					})
			);
		}

	};

	$("#evnListTable").bootstrapTable({
		url : "getEnv",
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
		pageSize : 10,// 每页行数
		pageNumber : 1,// 其实页
		method : "get",// 请求格式
		columns : [ {
			title : '环境名',
			field : 'envName',
			width : '30%',
			align : 'center',
		}, {
			title : '描述',
			field : 'envDesc',
			width : '30%',
			align : 'center',
		}, {
			field : '',
			title : '操作',
			width : '30%',
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
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置项</button>',
				'<button type="button" class="btn_common btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关联资源</button>',
				'<button type="button" class="btn_clone btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>复制</button>',
				'<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
	}

	function centerModals() {
		$('#evnModal').each(function(i) {
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
		$('#clone').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});

	}
	// $('#permModal').on('show.bs.modal', centerModals);
	centerModals();

	$('#evnform').on('submit', function(e) {
		e.preventDefault();
		if ($(this).data('bootstrapValidator').isValid()) {
			var url = "addEnv";
			if ($("#clicktype").val() != 1) {
				url = "updatePerm";
			}
			$.ajax({
				type : "post",
				url : url,
				data : {
					appId : $("#appId").val(),
					envName : $('#evnNameForm').val(),
					envDesc : $('#envDescForm').val()
				},
				success : function(data) {
					$('#evnListTable').bootstrapTable('refresh');
					$('#evnform').data('bootstrapValidator').resetForm(true);
					$('#evnModal').modal('hide');
					message(data);
				}
			});
		}

	})

	$("#evnsubmit").click(function() {
		$("#evnform").submit();
	})

	var cloneRest = function() {
		$("#dstEnv").val("");
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

	$("#cloneForm").on("submit", function(e) {
		e.preventDefault();
		if ($(this).data('bootstrapValidator').isValid()) {
			$.ajax({
				type : "post",
				url : "copyEnv",
				data : {
					app : $("#appName").val(),
					srcEnv : $("#cloneEvnName").val(),
					dstEnv : $("#dstEnv").val(),
					isOverrie : $("input[name='radio']:checked").val()
				},
				success : function(data) {
					$('#evnListTable').bootstrapTable('refresh');
					$('#clone').modal('hide');
					message(data);
					cloneRest();
				}
			});
		}
	})

	$('#clonesubmit').on('click', function() {
		$("#cloneForm").submit();
	})

	window.itemEvents = {
		'click .btn_save' : function(e, value, row, index) {
			e.stopPropagation();
			var resEnvId = $(this).parent().parent().find('.btn-success').attr('id');
			if (!resEnvId) {
				Messenger().post({
					message : '请选择环境！',
					type : 'info',
					showCloseButton : true
				});
				return;
			}
			$.ajax({
				type : "post",
				url : 'addRelativeResource',
				data : {
					resId : row.id,
					resEnvId : resEnvId,
					// value : JSON.stringify(source_env[row.id]),
					itemValue : JSON.stringify(source_item[resEnvId]),
					appId : $("#appId").val(),
					envId : $("#envRelationId").val()
				},
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					message(data);
//					$('#source').modal('hide');
				}
			});
		}
	};
	window.sourceEvents = {
		'click .btn-default' : function(e, value, row, index) {
			e.stopPropagation();
			var $this = $(this);
			var envId = $this.attr('id'); // ===resEnvId
			var $tr = $this.parent().parent();
			if ($this.hasClass('btn-success')) {
				$this.removeClass('btn-success');
			} else {
				$this.siblings().removeClass('btn-success');
				$this.addClass('btn-success');
			}
			if ($tr.find('> td > .btn-success').length > 0) {
				$tr.find('> td > .detail-icon1').click();
			} else {
				$tr.find('> td > .detail-icon')[0].click();
			}
		}

	};

	var sourceListTable = $("#sourceListTable").bootstrapTable({
		url : "getAllResource",
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

				return res.data;
			}
		},
		// detailView:true,
		detailFormatter : function(index, row) {
			var $button = $('#source').find('[data-index=' + index + ']').find('.btn-success');
			var envId = $button.attr("id");
			var html = [];
			$.ajax({
				type : "post",
				url : 'getResourceEnvItem',
				data : {
					id : $("#appId").val(),
					envId : envId
				},
				async : false,
				success : function(data) {
					if (data.code == 401) {
						location.reload();
						return;
					}
					$table = $("<table></table>");
					$.each(data.data.rows, function(n, o) {
						var $tr = $("<tr onclick='checkBoxOnclick(\"" + o.id + "\")'>");
						var $check = $("<input type='checkbox' style='pointer-events:none;'>");
						if (o.isDefault == "0") {
							$tr = $("<tr>");
							$check = $("<input type='checkbox' checked='checked' disabled=''>");
						}
						if (source_item[envId][o.id]) {
							$check = $("<input type='checkbox' checked='checked'>");
						}
						var $td0 = $("<td>").text(o.id);
						var $td1 = $("<td>").append($check);
						var $td2 = $("<td>").text(o.itemName);
						var $td3 = $("<td>").text(o.itemValue);
						var $td4 = $("<td>").text(o.itemDesc);
						$tr.append($td0).append($td1).append($td2).append($td3).append($td4);
						$table.append($tr);
					});
					// alert($table.html());
					html.push("<table>" + $table.html() + "</table>");
					// alert($table.html());
				}
			});
			return html.join('');

			// $.each(row, function(key, value) {
			// html.push('<p><b>' + key + ':</b> ' + value + '</p>');
			// });
		},
		height : 400,
		// minimumCountColumns: 2, //最少允许的列数
		showRefresh : true,// 刷新功能
		clickToSelect : false,// 选择行即选择checkbox
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
			title : '资源名',
			field : 'resName',
			width : '25%',
			align : 'center',
			valign : 'middle'
		}, {
			title : '环境',
			field : 'envs',
			align : 'center',
			width : '55%',
			searchable : true,
			valign : 'middle',
			events : sourceEvents,
			formatter : sourceFormatter
		}, {
			title : '操作',
			field : '',
			align : 'center',
			width : '55%',
			searchable : true,
			valign : 'middle',
			events : itemEvents,
			formatter : itemDetails
		} ]
	});

	// sourceEvents1

	function itemDetails(value, row, index) {
		return [ '<button type="button" class="detail-icon btn_edit btn btn-default  btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置项详情</button>',
				'<button type="button" class="btn_save btn btn-default btn-sm" ><span class="glyphicon glyphicon-pencil" aria-hidden="true">保存</span></button>',
				'<button type="button" class="detail-icon detail-icon1   btn btn-default  btn-sm" style="display:none"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>1</button>' ].join('');
	}

	function sourceFormatter(value, row, index) {
		if (value == null || value == "") {
			return [ '-' ].join('');
		}
		var buttonValue = "";
		for (var i = 0; i < value.length; i++) {
			// buttonValue += '<div class="btn-group dropup"
			// style="margin-right:15px;">';
			if(typeof source_item[value[i].id] == "undefined"){
				source_item[value[i].id] = {};
//				source_item[value[i].id] = source_item_original[value[i].id];
			}
			buttonValue += '<button type="button" id=' + value[i].id + ' class="btn btn-default ';
			if (typeof source_env[row.id] != "undefined" && source_env[row.id][value[i].id] == true) { //
				buttonValue += 'btn-success';
			}
			buttonValue += '">' + value[i].envName + '</button>';
		}
		return [ '' + buttonValue ].join('');
	}
	// $sourceTable.on('check.bs.table uncheck.bs.table check-all.bs.table
	// uncheck-all.bs.table', function() {
	// source_selections = getSelections($sourceTable, "id");
	// });

	// function getSelections($table, cloum) {
	// return $.map($table.bootstrapTable('getSelections'), function(row) {
	// return row[cloum];
	// });
	// }

//	$("#sourceSubmit").click(function() {
//		// alert(JSON.stringify(source_item));
//		return;
//		$.ajax({
//			type : "post",
//			url : 'addRelativeResource',
//			data : {
//				value : JSON.stringify(source_env),
//				itemValue : JSON.stringify(source_item),
//				appId : $("#appId").val(),
//				envId : $("#envRelationId").val()
//			},
//			success : function(data) {
//				if (data.code == 401) {
//					location.reload();
//					return;
//				}
//				message(data);
//				$('#source').modal('hide');
//			}
//		});
//	});

	/**
	 * _____________item配置项单个可选操作开始_________________________________________
	 */

	// $("#itemSourceListTable").bootstrapTable({
	// url : "getResourceEnvItem",
	// sidePagination : "server",// 服务器分页
	// contentType : 'application/x-www-form-urlencoded',
	// // sortName: "rkey",//排序列
	// striped : true,// 是否显示行间隔色
	// search : true,// 搜索功能
	// responseHandler : function(res) {//
	// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
	// if (res.code != 0) {
	// if (res.code == 401) {
	// location.reload();
	// return;
	// }
	// message(res);
	// return res;
	// } else {
	// // alert(JSON.stringify(source_selections));
	// $.each(res.data.rows, function(i, row) {
	// row.state = $.inArray(row.id, source_item_selections) !== -1;
	// });
	// return res.data;
	// }
	// },
	// height : 400,
	// // minimumCountColumns: 2, //最少允许的列数
	// showRefresh : true,// 刷新功能
	// clickToSelect : true,// 选择行即选择checkbox
	// singleSelect : false,// 仅允许单选
	// // searchOnEnterKey: true,//ENTER键搜索
	// pagination : true,// 启用分页
	// escape : false,// 过滤危险字符
	// showColumns : true,// 是否显示所有的列
	// showToggle : true, // 是否显示详细视图和列表视图的切换按钮
	// cardView : false, // 是否显示详细视图
	// // queryParams: getParams,//携带参数
	// queryParams : function(params) {
	// params.id = $("#appId").val();
	// params.envId = $("#itemEnvRelationId").val();
	// return params;
	// },// 携带参数
	// pageSize : 1000,// 每页行数
	// pageIndex : 0,// 其实页
	// method : "post",// 请求格式
	// columns : [ {
	// field : 'state',
	// checkbox : true,
	// // checkboxEnabled : false,
	// width : '25%',
	// align : 'center',
	// valign : 'middle',
	// formatter : function(value, row, index) {
	// // console.log(row.isDefault);
	// if (row.isDefault == "0") {
	// return {
	// disabled : true
	// };
	// }
	// if (row.isDefault == "1") {
	// return {
	// disabled : false
	// };
	// }
	// return value;
	// }
	// }, {
	// title : 'id',
	// field : 'id',
	// width : '25%',
	// align : 'center',
	// valign : 'middle'
	// }, {
	// title : '资源配置项名',
	// field : 'itemName',
	// width : '25%',
	// align : 'center',
	// valign : 'middle'
	// }, {
	// title : '资源环境',
	// field : 'itemValue',
	// align : 'center',
	// width : '25%',
	// valign : 'middle',
	// }, {
	// title : '描述',
	// field : 'itemDesc',
	// align : 'center',
	// width : '25%',
	// valign : 'middle',
	// } ],
	// onUncheck : function(row, args) {
	// var resEnvId = $("#itemEnvRelationId").val();
	// if (typeof source_item[resEnvId] != "undefined") {
	// delete source_item[resEnvId][row.id];
	// source_item_selections.splice($.inArray(row.id, source_item_selections),
	// 1);
	// }
	// },
	// onCheck : function(row, args) {
	// var resEnvId = $("#itemEnvRelationId").val();
	// if (typeof source_item[resEnvId] != "undefined") {
	// source_item[resEnvId][row.id] = true;
	// } else {
	// source_item[resEnvId] = {};
	// source_item[resEnvId][row.id] = true;
	// source_item_judge[resEnvId] = false;
	// }
	// source_item_selections.push(row.id);
	// }
	// });
	/**
	 * _____________item配置项单个可选操作结束_________________________________________
	 */

})