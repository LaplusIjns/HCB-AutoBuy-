index = 0;

function AjaxgetDaily(index) {
	return $.ajax({
		type: "post",
		url: "/dailyprice/" + index,
		contentType: 'application/json;charset=utf-8',
		async: !1,
	})
}
function parsegetDaily() {
	dailydata = AjaxgetDaily(index)
	if (dailydata["status"] == 404) {
		tiletime()
		
		$template = `<li class="SelectProduct" ><div class="text-secondary mb-sm-1 mb-md-3 mb-5">無資料</div></li>`
		test = $($template)
		test.appendTo("#result")
	
		console.log("無資料")
		
	} else if (dailydata["status"] == 200) {
		tiletime()
		console.log(dailydata["responseJSON"])
		data = dailydata["responseJSON"]
		tiledata(data)
		
	}
	index++;
}
function tiletime(){
	var d = new Date();
	var getMillions = d.getTime();
	const dayMillionsSecond = 86400000;
	beforeDay = getMillions - dayMillionsSecond*index;
	var b = new Date(beforeDay);
	var getYear = b.getFullYear();
	var getMonth = b.getMonth()+1;
	var getDay = b.getDate();
	$template = `<li class="SelectProduct" ><div class="text-secondary mb-sm-1 mb-md-3 mb-5">${getYear} 年 ${getMonth} 月 ${getDay} 日</div></li>`
	test = $($template)
	test.appendTo("#result")
	
}

function tiledata(data){
	for(var i =0;i<data.length;i++){
			if(data[i]["diff"]>0){
			$template = `<a href="./Product?prodid=${data[i]["fk_prod_id"]}"><li class="SelectProduct  border border-secondary border-3 my-2" id=${data[i]["fk_prod_id"]} ><div class="text-danger mb-sm-1 mb-md-3 mb-5">${data[i]['prodname']} 上漲 ${data[i]['diff']} 元 現價 ${data[i]['price']} 元</div></li></a>`;
			test = $($template)
			test.appendTo("#result")	
			}else{
			$template = `<a href="./Product?prodid=${data[i]["fk_prod_id"]}"><li class="SelectProduct  border border-secondary border-3 my-2" id=${data[i]["fk_prod_id"]} ><div class="text-success mb-sm-1 mb-md-3 mb-5">${data[i]['prodname']} 降價 ${Math.abs(data[i]['diff'])} 元 現價 ${data[i]['price']} 元</div></li></a>`;
			test = $($template)
			test.appendTo("#result")	
			}
		}
	}

function init() {
	var timer
//	$(window).scroll(function() {
//		if (document.documentElement.scrollTop + 10 >= document.documentElement.scrollHeight - document.documentElement.clientHeight) {
//			window.clearTimeout(timer);
//			timer = window.setTimeout(function() {
//				console.log("scroll!");
//				parsegetDaily()
//				//			
//			}, 500)
//		}
//	});
	window.addEventListener('scroll',function() {
		if (document.documentElement.scrollTop + 10 >= document.documentElement.scrollHeight - document.documentElement.clientHeight) {
			window.clearTimeout(timer);
			timer = window.setTimeout(function() {
				console.log("scroll!");
				parsegetDaily()
				//			
			}, 500)
		}
	})
	window.addEventListener('touchmove',function() {
			if($(window).scrollTop()+window.innerHeight>=document.body.scrollHeight-30){
			window.clearTimeout(timer);
			timer = window.setTimeout(function() {
				console.log("scroll!");
				parsegetDaily()
				//			
			}, 300)}
			})
}
function initdata(flag) {
	if(flag){
	console.log("hello! "+index);
	parsegetDaily()
	setTimeout(function() {
//		alert();
//		加載資料		
		var flag = document.documentElement.scrollTop + 10 >= document.documentElement.scrollHeight - document.documentElement.clientHeight
		initdata(flag);
	}, 5000);
	return;}
	console.log("end hello!");
}	
$(function() {
	console.log("hello world");
	var flag = document.documentElement.scrollTop + 10 >= document.documentElement.scrollHeight - document.documentElement.clientHeight
	initdata(flag)
	init();
});