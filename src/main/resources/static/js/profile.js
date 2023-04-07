$(function(){
	$(".follow-btn").click(follow);
});

function follow() {
	let btn = this;
	if($(btn).hasClass("btn-info")) {
		// 关注TA
		$.ajax({
			url: CONTEXT_PATH + "/follow",
			type: 'post',
			dataType: 'json',
			data: {"userId":$(btn).prev().val(), "tag":true},
			success: function (res){
				if(res.code===0)
					window.location.reload();
				else
					alert(res.msg);
			}
		})
		//$(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
	} else {
		// 取消关注
		$.ajax({
			url: CONTEXT_PATH + "/follow",
			type: 'post',
			dataType: 'json',
			data: {"userId":$(btn).prev().val(), "tag":false},
			success: function (res){
				lo
				if(res.code===0)
					window.location.reload();
				else
					alert(res.msg);
			}
		})
		//$(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
	}
}