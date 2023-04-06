$(function(){
	$("form").submit(check_data);
	$("input").focus(clear_error);
});

function check_data() {
	let pwd1 = $("#password").val();
	let pwd2 = $("#confirm-password");
	if(pwd1 !== pwd2.val()) {
		pwd2.addClass("is-invalid");
		return false;
	}
	return true;
}

function clear_error() {
	$(this).removeClass("is-invalid");
}