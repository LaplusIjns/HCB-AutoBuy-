
function AjaxgetProd(datajson, pageparam123) {
	return $.ajax({
		type: "post",
		url: "/Sinyafindtag/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}

function Ajexgetitemname(tag_id){
    return $.ajax({
		type: "post",
		url: "/Sinyafindtag2/"+tag_id,
		contentType:'charset=utf-8',
		// data:datajson,
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
		url: "/Sinyatagtotal/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}
function searchProd(tagid) {
	pageparam123 = 0;
	var gettotal
	var keyword
	var tagname
	document.getElementById("prodsearch").onclick = (event) => {
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
		// gettotal = x
		// var x = tagid
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		keyword = x
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
		tagname = Ajexgetitemname(tagid)["responseJSON"]["tag_zhtw"]
		// $("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
	document.getElementById("nextpage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		var x = keyword
		if(x==""|| x==undefined)return
		if (pageparam123 >= 0 && (pageparam123+1)<gettotal[0]) {
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
		$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
	document.getElementById("prepage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		// var x = tagid
		var x = keyword
		if(x==""|| x==undefined)return
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
		$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
}
function showresult(getprod) {
	$("#result").empty();
	console.log(getprod)
	for (var i = 0; i < getprod.length; i++) {
		$template = `<a href="./SinyaProduct?prodid=${getprod[i]["prod_id"]}"><li class="SelectProduct" id=${getprod[i]["prod_id"]} ><div class="text-white mb-sm-1 mb-md-3 mb-5">${getprod[i]['prodname']}</div></li></a>`;
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
function handle(e){
	pageparam123 = 0;
	var gettotal
	var keyword
	var tagname
	if(e.keyCode === 13){
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
		// gettotal = x
		// var x = tagid
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		keyword = x
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
		tagname = Ajexgetitemname(tagid)["responseJSON"]["tag_zhtw"]
		// $("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
		document.getElementById("nextpage").onclick = (event) => {
			// var x = document.querySelector("#searchbar").value
			var x = keyword
			if(x==""|| x==undefined)return
			if (pageparam123 >= 0 && (pageparam123+1)<gettotal[0]) {
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
			$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
			event.preventDefault()
		}
		document.getElementById("prepage").onclick = (event) => {
			// var x = document.querySelector("#searchbar").value
			// var x = tagid
			var x = keyword
			if(x==""|| x==undefined)return
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
			$("#totalpage").text("當前搜尋"+tagname+" ("+keyword+") 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
			event.preventDefault()
		}
	}
}

$(function () {
	console.log("hello world");
	tagid = getURLParam();
	document.querySelector("#searchbar").value = tagid
	console.log(tagid)
	searchProd(tagid);
});