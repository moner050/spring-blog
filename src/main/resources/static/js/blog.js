// blogObject 객체 선언 
let blogObject = {

	// init() 함수 선언 
	init: function() {
		let _this = this;

		// "#btn-insert" 버튼에 "click" 이벤트가 발생하면 insertBlog() 함수를 호출한다. 
		$("#btn-insert").on("click", () => {
			_this.insertBlog();
		});

		$("#btn-settingChange").on("click", () => {
			_this.settingChangeBlog();
		});

	},

	insertBlog: function() {
		alert("블로그 등록 요청됨");

		var title = $("#title").val();

		// Ajax를 이용한 비동기 호출
		$.ajax({
			async: false,
			type: "POST", // 요청 방식
			url: "/blog/insert", // 요청 path
			data: { title },
		})
		.done(function(res) {
			console.log(res);
			alert(res);
		})
		.always(function() {
			location.href = "/";
		});
	},

	settingChangeBlog: function() {
		alert("수정중입니다.")

		let blog = {
			title: $("#title").val(),
			tag: $("#tag").val()
		}

		$.ajax({
			type: "POST",
			url: "/blog/update",
			data: JSON.stringify(blog),
			contentType: "application/json; charset=utf-8",
			success: function(response) {
				alert(response);
				location.reload();
			}
		});
	}
}

// blogObject 객체의 init() 함수 호출. 
blogObject.init();
