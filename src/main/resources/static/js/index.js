$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");

    // 获取标题和内容
    let title = $("#recipient-name").val();
    let content = $("#message-text").val();

    console.log(title + "///" + content);

    // 发送异步请求
    $.ajax({
        url: CONTEXT_PATH + "/discuss/publish",
        type: 'POST',
        dataType: 'json',
        data: {'title': title, 'content': content},
        success: function (res) {
            console.log(res);
            // 在提示框当中填写返回的消息
            $("#hintBody").text(res.msg);
            // 显示提示框
            $("#hintModal").modal("show");
            // 2s后自动隐藏
            setTimeout(function () {
                $("#hintModal").modal("hide");
                // 成功则刷新页面
                if (res.code === 0) {
                    window.location.reload();
                }
            }, 2000);
        }
    })
}