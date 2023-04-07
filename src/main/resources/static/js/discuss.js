function like(btn, entityType, entityId, entityUserId){
    $.ajax({
        url: CONTEXT_PATH + "/like",
        type: "post",
        dataType: "json",
        data: {"entityType":entityType, "entityId":entityId, "entityUserId":entityUserId},
        success: function (res){
            console.log(res);
            if(res.code === 0){
                $(btn).children("i").text(res.likeCount);
                $(btn).children("b").text(res.likeStatus===1?"已赞":"赞")
            }
            else{
                alert(res.msg);
            }
        }
    })
}