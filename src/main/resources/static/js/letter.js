$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});
function send_letter() {
	$("#sendModal").modal("hide");
	$("#hintModal").modal("show");

	let toName = $("#recipient-name").val();
	let content = $("#message-text").val();

	$.ajax({
		url: CONTEXT_PATH + "/letter/send",
		type: 'POST',
		dataType: 'json',
		data: {"toName":toName, "content":content},
		success: function (res){
			console.log(res.code);
			if(res.code===0)
				$("#hintBody").text("发送成功！");
			else
				$("#hintBody").text(res.msg);
			setTimeout(function(){
				$("#hintModal").modal("hide");
				location.reload();
			}, 2000);
		}
	})
}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}