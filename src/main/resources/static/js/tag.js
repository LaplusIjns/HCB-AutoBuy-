
function AjaxgetProd(datajson, pageparam123) {
	return $.ajax({
		type: "post",
		url: "/findtag/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}

function getURLParam(){
	var urlstring = location.href
    var url = new URL(urlstring)
    return url.searchParams.get('tagid')
}
function AjaxgetTotal(datajson, pageparam123) {
	return $.ajax({
		type: "post",
		url: "/tagtotal/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}
function searchProd(tagid) {
	pageparam123 = 0;
	var gettotal
	document.getElementById("prodsearch").onclick = (event) => {
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
		// var x = tagid
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		y = { "prodname": x };
		datajson = JSON.stringify(y)
		// console.log(datajson)
		var getprod = AjaxgetProd(datajson, pageparam123)
		gettotal = AjaxgetTotal(datajson, pageparam123)["responseJSON"]
		console.log("總頁數!!"+gettotal)
		// ["responseJSON"]
		console.log(getprod["status"])
		if (getprod["status"] == 200) {
			getprod = getprod["responseJSON"]
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");

		}
		$("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
	document.getElementById("nextpage").onclick = (event) => {
		var x = document.querySelector("#searchbar").value
		if (pageparam123 >= 0) {
			pageparam123 += 1;
		}
		console.log(pageparam123);
		y = { "prodname": x };
		datajson = JSON.stringify(y)
		// console.log(datajson)
		var getprod = AjaxgetProd(datajson, pageparam123)
		// ["responseJSON"]
		console.log(getprod["status"])
		if (getprod["status"] == 200) {
			getprod = getprod["responseJSON"]
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");

		}
		$("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
	document.getElementById("prepage").onclick = (event) => {
		var x = document.querySelector("#searchbar").value
		// var x = tagid
		if (pageparam123 > 0) {
			pageparam123 -= 1;
		}
		y = { "prodname": x };
		datajson = JSON.stringify(y)
		// console.log(datajson)
		var getprod = AjaxgetProd(datajson, pageparam123)
		// ["responseJSON"]
		console.log(getprod["status"])
		if (getprod["status"] == 200) {
			getprod = getprod["responseJSON"]
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");

		}
		$("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
}
function showresult(getprod) {
	$("#result").empty();
	console.log(getprod)
	for (var i = 0; i < getprod.length; i++) {
		$template = `<a href="./Product?prodid=${getprod[i]["prod_id"]}"><li class="SelectProduct" id=${getprod[i]["prod_id"]} ><div class="text-white">${getprod[i]['prodname']}</div></li></a>`;
		// console.log($template)
		test = $($template)
		test.appendTo("#result")
	}
	// $(".SelectProduct").click(function () {
	// 	localStorage.setItem("prod_id", $(this).attr("id"));
	// 	localStorage.setItem("prod_name", $(this).children().text());
	// 	// console.log( $(this).children().text())
	// 	location.href = "./Product"+"?prodid="+$(this).attr("id");
	// })
	
}


$(function () {
	console.log("hello world");
	tagid = getURLParam();
	document.querySelector("#searchbar").value = tagid
	console.log(tagid)
	searchProd(tagid);
});