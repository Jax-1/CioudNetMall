$(function(){
	//修改收货人地址
	$("#edit_reciver").click(function(){
		var address=[[${address}]];
		console.log(address);
		//变量addlist此处为请求后台的数据，需替换
		var addlist=[{
			name:'李先生',
			address:'广东  深圳市  宝安区&nbsp;水口花园小区8巷6号',
			phone:'13823595211',
			id:'ID001'
		},{
			name:'李先生',
			address:'广东  深圳市  宝安区&nbsp;水口花园小区8巷7号',
			phone:'13823595212',
			id:'ID002'
		},{
			name:'李先生',
			address:'广东  深圳市  南山区&nbsp;赤湾石油大厦',
			phone:'13823595212',
			id:'ID003'
		}];

		var items='';

		for(var i=0;i<addlist.length;i++){
			items+='<li><input type="radio" id="'+addlist[i].id+'" name="addr" value="'+addlist[i].id+'">'+
			'<label for="'+addlist[i].id+'">'+
			        '<span class="true-name">'+addlist[i].name+'</span>'+
			        '<span class="address">'+addlist[i].address+'</span>'+
			        '<span class="phone">'+addlist[i].phone+'</span>'+
			'</label><a href="javascript:void(0);" onclick="del(\''+addlist[i].id+'\')" class="del">[ 删除 ]</a></li>';

		}

		items+='<li><input type="radio" id="new_add" name="addr" value="new">'+
	           '<label for="new_add"><span class="true-name">'+
	                        '使用新地址</span></label></li>';
		$("#address_list").html(items)

		$("#save_add").show();
		$("#address_box").addClass('current_box');

		$(":input[name=addr]").change(function(value,a){
			if($(this).val()=='new'){
				$("#add_addr_box").show();
			}else{
				$("#add_addr_box").hide();
			}
		})
	})

})

function del(id){
	console.log(id);
}