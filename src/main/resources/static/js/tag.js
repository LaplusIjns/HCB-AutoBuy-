function getURLParam(){
	var urlstring = location.href
    var url = new URL(urlstring)
    return url.searchParams.get('tagid')
}
function AjaxgetTag(datajson) {
	return $.ajax({
		type: "post",
		url: "/AutobuyTag",
		contentType: 'application/json;charset=utf-8',
		data: JSON.stringify( datajson ),
		async: !1,
	})
}
function refreshgrid(data){
		 getprod = data["tagproduct"]
		 gettotal = data["totalpage"]
		 element = data["totalelement"]
		
		console.log("總頁數!!"+gettotal)
		// ["responseJSON"]
		
		if ( getprod != null ) {
			showresult(getprod)
		} else {
			console.log("im here")
			$("#result").empty();
			$("#result").text("查無結果");
		}
		tagname = data["prodtagname"]["tag_zhtw"]
		// $("#totalpage").text("總共 "+gettotal[1]+"件商品 共"+gettotal[0]+"頁"+"當前為第"+(pageparam123+1)+"頁")
		$("#totalpage").text("當前搜尋"+tagname+" ("+document.querySelector("#searchbar").value+") 總共 "+element+"件商品 共"+gettotal+"頁"+"當前為第"+(pageparam123+1)+"頁")
	}

function searchProd(tagid) {
	
	console.log("im here111")
	pageparam123 = 0;
//	var gettotal
//	var keyword
//	var tagname
	document.getElementById("prodsearch").onclick = (event) => {
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
		// gettotal = x
		// var x = tagid
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		console.log("im here")
		x = tagid
		keyword = x
		y = { "prodName": x ,"page":pageparam123};
		datajson = JSON.stringify(y)
		// console.log(datajson)
		
		var data = AjaxgetTag(y)["responseJSON"];
		console.log(data)
		
		refreshgrid(data)
		
		event.preventDefault()
	}
	
	document.getElementById("nextpage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		var x = document.querySelector("#searchbar").value
		if(x==""|| x==undefined)return
		if (pageparam123 >= 0 && (pageparam123+1)<gettotal) {
			pageparam123 += 1;
		}
		console.log(pageparam123);
		y = { "prodName": x ,"page":pageparam123};
		var data = AjaxgetTag(y)["responseJSON"];
		console.log(data)
		
		refreshgrid(data)
		
		event.preventDefault()
	}
	document.getElementById("prepage").onclick = (event) => {
		// var x = document.querySelector("#searchbar").value
		// var x = tagid
		var x = document.querySelector("#searchbar").value
		if(x==""|| x==undefined)return
		if (pageparam123 > 0) {
			pageparam123 -= 1;
		}
		y = { "prodName": x ,"page":pageparam123};
		var data = AjaxgetTag(y)["responseJSON"];
		console.log(data)
		
		refreshgrid(data)
		
		event.preventDefault()
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
}
function handle(e){
	console.log(e.keyCode)
	if(e.keyCode === 13){
		pageparam123 = 0;
		var x = document.querySelector("#searchbar").value
		// gettotal = x
		// var x = tagid
		if(x==""){
			alert("輸入點什麼");
			return;
		}
		console.log("im here")
//		x = tagid
//		keyword = x
		y = { "prodName": x ,"page":pageparam123};
		datajson = JSON.stringify(y)
		// console.log(datajson)
		
		var data = AjaxgetTag(y)["responseJSON"];
		console.log(data)
		
		refreshgrid(data)
		
		
		
		event.preventDefault()

	}else{
		console.log("123123")
	}
}

$(function () {
	console.log("hello world");
	tagid = getURLParam();
	document.querySelector("#searchbar").value = tagid
	console.log(tagid)
	searchProd(tagid);
});