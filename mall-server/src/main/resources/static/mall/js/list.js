$(document).ready(function(){						  
	//小图左右按键效果
	$("#sLeftBtnB").mouseover(function(){
		if($(this).attr("class")=="sLeftBtnB") {
			$(this).attr("class","sLeftBtnBSel");
		}
	});
	
	$("#sLeftBtnB").mouseout(function(){
		if($(this).attr("class")=="sLeftBtnBSel") {
			$(this).attr("class","sLeftBtnB");
		}
	});
	
	$("#sLeftBtnB").click(function(){
		if($(this).attr("class")=="sLeftBtnBSel") {
			var leftPosition=$(".ulSmallPic").css("left");
			var leftPositionNum=Number(leftPosition.substring(0,(leftPosition.length-2)));
			leftPosition=leftPositionNum+248+"px";
			if(leftPosition=="0px") {if($(this).attr("class") != "sLeftBtnBBan") {$(this).attr("class","sLeftBtnBBan");}}
			var bestLeftNum=-($(".ulSmallPic li").length-6)*248;
			if((leftPositionNum+248) > bestLeftNum && $("sRightBtnB").attr("class") != "sRightBtnB") {
				$("#sRightBtnB").attr("class","sRightBtnB")
			}
			if($(".ulSmallPic").attr("rel")=="stop"){
				$(".ulSmallPic").attr("rel","moving");
				$(".ulSmallPic").stop();
				$(".ulSmallPic").animate({left:leftPosition},214,function(){$(".ulSmallPic").attr("rel","stop");});
			}
		}
	});
	
	$("#sRightBtnB").mouseover(function(){
		if($(this).attr("class")=="sRightBtnB") {
			$(this).attr("class","sRightBtnBSel");
		}
	});
	
	$("#sRightBtnB").mouseout(function(){
		if($(this).attr("class")=="sRightBtnBSel") {
			$(this).attr("class","sRightBtnB");
		}
	});
	
	$("#sRightBtnB").click(function(){
		if($(this).attr("class")=="sRightBtnBSel"){
			var leftPosition=$(".ulSmallPic").css("left");
			var leftPositionNum=Number(leftPosition.substring(0,(leftPosition.length-2)));
			leftPosition=leftPositionNum-248+"px";
			var bestLeftNum=-($(".ulSmallPic li").length-6)*248;
			if((leftPositionNum-248)==bestLeftNum) {$(this).attr("class","sRightBtnBBan");}
			if(leftPositionNum==0 && $("#sLeftBtnB").attr("class")=="sLeftBtnBBan") {$("#sLeftBtnB").attr("class","sLeftBtnB");}
			if($(".ulSmallPic").attr("rel")=="stop") {
				$(".ulSmallPic").attr("rel","moving");
				$(".ulSmallPic").stop();
				$(".ulSmallPic").animate({left:leftPosition},248,function(){$(".ulSmallPic").attr("rel","stop");});
			}
		}
	});

	var length=$(".ulSmallPic").children().length;
	if(length<6){
		$(".ulSmallPic").width(5 * 248)
	}else{
		$(".ulSmallPic").width(length * 248);
	}
});