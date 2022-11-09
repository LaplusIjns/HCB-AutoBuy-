function AutoComplete() {
	$("#searchbar").autocomplete({
		source: function (request, response) {
			console.log(request);
			$.ajax({
				type: "post",
				url: "/findprod/" + request.term,
				async: !1,
				success: function (data) {
					resval = []
					for (var i = 0; i < data.length; i++) {
						resval.push(data[i].prodname)
					}
					response(resval);
				}
			})
		}
	});
}
function AjaxgetProd(datajson, pageparam123) {
	console.log(pageparam123)
	return $.ajax({
		type: "post",
		url: "/findprod2/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}
function AjaxgetTotal(datajson, pageparam123) {
	console.log(pageparam123)
	return $.ajax({
		type: "post",
		url: "/total/" + pageparam123,
		contentType: 'application/json;charset=utf-8',
		data: datajson,
		async: !1,
	})
}
function setHistorysearch(x) {
	if (localStorage.getItem("historySearch") != null) {
		var latestSearch = []
		latestSearch = JSON.parse(localStorage.getItem("historySearch"))
		var alreadyin = false;
		for (var i = 0; i < latestSearch.length; i++) {
			if (latestSearch[i].toString().toUpperCase() === x.toString().toUpperCase()) {
				alreadyin = true;
				console.log("曾經有過")
			}
		}
		if (alreadyin == false) {
			console.log("不曾有過")
			latestSearch.push(x)
		}
		if (latestSearch.length <= 10) {
			savelocalstorge = JSON.stringify(latestSearch)
			localStorage.setItem("historySearch", savelocalstorge)
		}
	} else {
		var tmp = []
		tmp.push(x)
		localStorage.setItem("historySearch", JSON.stringify(tmp))
	}
	initHistory();
}


function searchProd() {
	pageparam123 = 0;
	var gettotal
	var keyword
	document.getElementById("prodsearch").onclick = (event) => {
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
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
		console.log(gettotal)

		// ["responseJSON"]
		console.log(getprod["status"])
		if (getprod["status"] == 200) {
			setHistorysearch(x)
			getprod = getprod["responseJSON"]
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");

		}
		$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}	
	document.getElementById("nextpage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		var x = keyword
		if(x==""|| x==undefined)return
		if (pageparam123 >= 0 && (pageparam123+1)<gettotal[0]) {
			pageparam123 += 1;
		}
		// console.log(pageparam123);
		y = { "prodname": x };
		datajson = JSON.stringify(y)
		console.log(datajson)
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
		$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
	document.getElementById("prepage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		var x = keyword
		if(x==""|| x==undefined)return
		if (pageparam123 > 0) {
			pageparam123 -= 1;
		}

		console.log(x)
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
		$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		event.preventDefault()
	}
}
function handle(e){
	pageparam123 = 0;
	var gettotal
	var keyword
	if(e.keyCode === 13){
		document.activeElement.blur();
		var x = document.querySelector("#searchbar").value
		keyword = document.querySelector("#searchbar").value
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		y = { "prodname": x };
		datajson = JSON.stringify(y)
		// console.log(datajson)
		var getprod = AjaxgetProd(datajson, pageparam123)
		gettotal = AjaxgetTotal(datajson, pageparam123)["responseJSON"]
		console.log(gettotal)

		// ["responseJSON"]
		console.log(getprod["status"])
		if (getprod["status"] == 200) {
			setHistorysearch(x)
			getprod = getprod["responseJSON"]
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");

		}
		$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		document.getElementById("nextpage").onclick = (event) => {
			// var x = document.querySelector("#searchbar").value
			var x = keyword
			if(x==""|| x==undefined)return
			if (pageparam123 >= 0 && (pageparam123+1)<gettotal[0]) {
				pageparam123 += 1;
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
			$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
			event.preventDefault()
		}
		document.getElementById("prepage").onclick = (event) => {
			// var x = document.querySelector("#searchbar").value
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
			$("#totalpage").text("當前搜尋"+keyword+" 總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
			event.preventDefault()
		}
	}
}
function showresult(getprod) {
	$("#result").empty();
	console.log(getprod)
	for (var i = 0; i < getprod.length; i++) {
		$template = `<a href="./Product?prodid=${getprod[i]["prod_id"]}"><li class="SelectProduct" id=${getprod[i]["prod_id"]} ><div class="text-white mb-sm-1 mb-md-3 mb-5">${getprod[i]['prodname']}</div></li></a>`;
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
function initHistory(){
	$("#historySearch").empty();
	var latestSearch = []
		latestSearch = JSON.parse(localStorage.getItem("historySearch"))
	if(latestSearch!=null){
		for(var i =0;i<latestSearch.length;i++){
		$historytemplate = `<span class="m-2"><span class="historyfind text-white p-2 rounded rounded-2">${latestSearch[i]}</span><button class="removethisbar btn btn-secondary">Ｘ</button></span>`;
		test = $($historytemplate)
		test.appendTo("#historySearch")
		}}
		$(".historyfind").click(function(event){
			document.querySelector("#searchbar").value = $(this).text()
		})
		$(".removethisbar").click(function(){
			var tmp =JSON.parse( localStorage.getItem("historySearch") )
			console.log(tmp)
			var tmp2 = $(this).parent("span").children("span").text()
			// console.log($(this).parent("span").children("span").text())
			for(var i=0;i<tmp.length;i++){
				if(tmp[i]===tmp2){
					console.log("im here")
					tmp.splice(i,i);
					if(i==0){
						tmp.splice(0,1);
					}
				}
			}
			console.log(tmp)
			localStorage.setItem("historySearch",JSON.stringify(tmp))
			$(this).parent("span").remove()
		})
	
}
$(function () {
	console.log("hello world");
	AutoComplete()
	searchProd();
	initHistory();
});