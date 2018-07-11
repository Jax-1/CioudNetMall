$(function(){
	//鼠标hover
	$(".csbox").hover(function(){
		$(this).children('.boxhover').show();
	},function(){
		$(this).children('.boxhover').hide();
	})

	//tab切换
	$(".side-left-ulbox>li").click(function(){
		 var index=$(this).index();
		 $(this).addClass('active').siblings().removeClass('active');
		 $(".side-right-ulbox>li").eq(index).show().siblings().hide();
	})

	 var href=window.location.href;
	 var byid=href.split("#")[1];
	 
	 switch	(byid){
	 	case 'bb':
	 		$(".side-left-ulbox>li").eq(1).addClass('active').siblings().removeClass('active');
	 		$(".side-right-ulbox>li").eq(1).show().siblings().hide();
	 		break;
	 	case 'cc':
	 		$(".side-left-ulbox>li").eq(2).addClass('active').siblings().removeClass('active');
	 		$(".side-right-ulbox>li").eq(2).show().siblings().hide();
	 		break;
	 	default:
	 		$(".side-left-ulbox>li").eq(0).addClass('active').siblings().removeClass('active');
	 		$(".side-right-ulbox>li").eq(0).show().siblings().hide();
	 }		
})