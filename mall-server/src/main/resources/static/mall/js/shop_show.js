$(document).ready(function(){
    $(".mark").mouseover(function() {
        $(".float_layer").show();
        $(".big_box").show();
    })

    $(".mark").mouseleave(function(){
        $(".float_layer").hide();
        $(".big_box").hide();
    })

    $(".mark").mousemove(function(e){
        var l = e.pageX - $(".small_box").offset().left - ($(".float_layer").width() / 2)
        var t = e.pageY - $(".small_box").offset().top - ($(".float_layer").height() / 2)
        if (l < 0) {
            l = 0
        }
        if (l > $(this).width() - $(".float_layer").width()) {
            l = $(this).width() - $(".float_layer").width()
        }
        if (t < 0) {
            t = 0
        }

        if (t > $(this).height() - $(".float_layer").height()) {
            t = $(this).height() - $(".float_layer").height()
        }
        $(".float_layer").css({
            "left": l,
            "top": t
        })

        var pX = l;
        var pY = t ;
        $(".big_box img").css({
            "left": -pX,
            "top": -pY
        })
    })

    //加1 减1
    $(".plus").click(function(){
        var amount=parseInt($("#amount").val())
        $("#amount").val(amount+1);
    })

    $(".minus").click(function(){
        var amount=parseInt($("#amount").val())
        if(amount>0){
            $("#amount").val(amount-1);
        }
    })

    // 评价切换
    $("#pingjia_tit>li").click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        $('.f_ul').css('left',-$(this).index()*996+'px');
    })
})