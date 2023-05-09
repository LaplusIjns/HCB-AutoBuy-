function AutoComplete() {
	$("#formprodname").autocomplete({
		source: function (request, response) {
			$.ajax({
				type: "post",
				url: "/Sinyafindprod" ,
				async: !1,
				data: request.term,
				contentType: 'application/json;charset=utf-8',
				success: function (data) {
					resval = []
					for (var i = 0; i < data.length; i++) {
						resval.push(data[i].prodname)
					}
					response(resval);
					$(".ui-menu-item").addClass(" my-1  ")
				}
			})
		}
	});
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

function parseDate(date){
	let formatted_date = date.getFullYear() +" / " + (date.getMonth() + 1) +" / " + date.getDate()
 return formatted_date;
}

function ajaxprod(){
	
	$.ajax({
				type: "post",
				url: "/SinyaForm",
				async: !1,
				data: JSON.stringify( FormData ),
				contentType: 'application/json;charset=utf-8',
				success: function (data) {
// 					resval = []
					totalpage = data.totalpage;
					nowpage = data.page;
					$("#result").empty();
 					for (var i = 0; i < data.produts.length; i++) {
						 var tags = "";
						  	for(var j=0; j<data.produts[i].tagnameDTO.length; j++){
								  tags+= `<a href="#${data.produts[i].tagnameDTO[j].tagId}" class="btn carbtncolor mx-1 px-3  border border-1 border-dark rounded-0">${data.produts[i].tagnameDTO[j].tagZhtw}</a>`;
							  }					
 						var tmp = `
 						<li class="mb-3">
							<a href="./SinyaProduct?prodid=${data.produts[i].prodId}">
								<div class="card carheadcolor border border-2 border-dark rounded-0">
					  				<div class="card-header carheadcolor">
					    				${ data.produts[i].prodname }
					  				</div>
					  				<div class="card-body carbodycolor">
						    			<h3 class="card-title"><i class="bi bi-currency-dollar "></i>${ data.produts[i].lastprice }</h3>
					    				${
											tags
										}
						  			</div>
						  			<div class="card-footer carfootcolor">
					      				<small class="text-muted">最後更新時間: ${ parseDate(new Date(  data.produts[i].lastUpdateDate  )) }</small>
					    			</div>
								</div>
							</a>
					</li>
 						`
 					$("#result").append(tmp);
 					}
 					
 					$("#totalpage").text(`當前搜尋 ${ FormData.prodName } 總共 ${ data.totalnumber } 件商品 共 ${data.totalpage} 頁當前為第 ${data.page+1} 頁`);
 					
 					setHistorysearch(FormData.prodName)
// 					response(resval);
					console.log(data)
				}
			})
	freshpagination();
}

function previousnext(){
	$('.previouspage').on("click",function(){
		console.log(FormData)
		console.log("123123")
//		if()
		 if( jQuery.isEmptyObject(FormData) ){
			 return;
		 }
		 if( FormData["page"] > 1 ){
		 	FormData["page"] = FormData["page"] -1;
		 	ajaxprod();
		 }
		});
	$('.nextpage').on("click",function(){
		console.log( jQuery.isEmptyObject(FormData) )
		if( jQuery.isEmptyObject(FormData) ){
			 return;
		 }
		 if(FormData["page"] < (totalpage) ){
		 	FormData["page"] = FormData["page"] +1;
		 	ajaxprod();
		 }
		 
		});
}
function getpagetemplate(pageparam,bool){
	
	if(bool==true){
		return `<li class="page-item mx-2 pagenumber pagebox"><a class="page-link chose" href="#" onclick="event.preventDefault();">${  parseInt(pageparam+1) }</a></li>`;
	}
	
	return `<li class="page-item mx-2 pagenumber pagebox"><a class="page-link" href="#" onclick="event.preventDefault();">${  parseInt(pageparam+1) }</a></li>`;
}

function freshpageclick(){
	
	$(".pagebox").on('click',function(){
		FormData["page"] = parseInt($(this).text() )
		ajaxprod()
	})
	
}

function freshpagination(){
	$(".pagenumber").remove();
	var emptytag = `<li class="page-item mx-2 pagenumber"><span class="page-link">...</span></li>`;
	console.log(nowpage)
	if(totalpage>7){
		if( nowpage==0 ){
			$('.previouspage').after(getpagetemplate(totalpage/2+1));
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(getpagetemplate(totalpage/2-1));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0,true));
			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}
		if( nowpage==1 ){
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(2));
			$('.previouspage').after(getpagetemplate(1,true));
			$('.previouspage').after(getpagetemplate(0));
//			$('.previouspage').after(emptytag);
			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}		
		if( nowpage==2 ){
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(2,true));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0));
//			$('.previouspage').after(emptytag);
			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}	
		if( nowpage==totalpage-1 ){						
			$('.previouspage').after(getpagetemplate(totalpage/2+1));
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(getpagetemplate(totalpage/2-1));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0));
			
			$('.nextpage').before(emptytag);
//			$('.nextpage').before(getpagetemplate(totalpage-2));
			$('.nextpage').before(getpagetemplate(totalpage-1,true));
			freshpageclick()
			return;
			}
		if( nowpage==totalpage-2 ){
			
			
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0));			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-3));
			$('.nextpage').before(getpagetemplate(totalpage-2,true));
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}
		if( nowpage==totalpage-3 ){
			
			
			$('.previouspage').after(getpagetemplate(totalpage/2));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0));			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-3,true));
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}
		if( nowpage>2 ){
			
			
			$('.previouspage').after(getpagetemplate(nowpage+1));
			$('.previouspage').after(getpagetemplate(nowpage,true));
			$('.previouspage').after(getpagetemplate(nowpage-1));
			$('.previouspage').after(emptytag);
			$('.previouspage').after(getpagetemplate(0));
			
			$('.nextpage').before(emptytag);
			$('.nextpage').before(getpagetemplate(totalpage-1));
			freshpageclick()
			return;
			}
	}
	
//	if( nowpage==1 ){
//
//		$('.previouspage').after(getpagetemplate(totalpage/2+1));
//		$('.previouspage').after(getpagetemplate(totalpage/2));
//		$('.previouspage').after(getpagetemplate(totalpage/2-1));
//	
//		$('.previouspage').after(emptytag);
//		$('.previouspage').after(getpagetemplate(1,true));
//		
//		$('.nextpage').before(emptytag);
//		$('.nextpage').before(getpagetemplate(totalpage-1));
//		
//	}else if(nowpage == (totalpage-1) ){
//		$('.previouspage').after(getpagetemplate(totalpage/2+1));
//		$('.previouspage').after(getpagetemplate(totalpage/2));
//		$('.previouspage').after(getpagetemplate(totalpage/2-1));
//	
//		$('.previouspage').after(emptytag);
//		$('.previouspage').after(getpagetemplate(1));
//		
//		$('.nextpage').before(emptytag);
//		$('.nextpage').before(getpagetemplate(totalpage-1,true));
//	}else if(nowpage==2){
//		$('.previouspage').after(getpagetemplate(totalpage/2+1));
//		$('.previouspage').after(getpagetemplate(totalpage/2));
//		$('.previouspage').after(getpagetemplate(totalpage/2-1));
//	
//		$('.previouspage').after(getpagetemplate(2,true));
//		$('.previouspage').after(emptytag);
//		
//		$('.nextpage').before(emptytag);
//		$('.nextpage').before(getpagetemplate(totalpage-1));
//		
//	}else if(nowpage == (totalpage-2)){
//		$('.previouspage').after(getpagetemplate(totalpage/2+1));
//		$('.previouspage').after(getpagetemplate(totalpage/2));
//		$('.previouspage').after(getpagetemplate(totalpage/2-1));
//	
//		$('.previouspage').after(emptytag);
//		$('.previouspage').after(getpagetemplate(1));
//		
//		$('.nextpage').before(emptytag);
//		$('.nextpage').before(getpagetemplate(totalpage-1,true));
//	}
	
//	else{
		
//		$('.previouspage').after(getpagetemplate(totalpage/2+1));
//		$('.previouspage').after(getpagetemplate(totalpage/2));
//		$('.previouspage').after(getpagetemplate(totalpage/2-1));
//	
//		$('.previouspage').after(emptytag);
//		$('.previouspage').after(getpagetemplate(1));
//		
//		$('.nextpage').before(emptytag);
//		$('.nextpage').before(lasttag);
//	}
}

function searchProd() {
	        $('#prodsearch222').on("click",function(){
        	
        	
        	if(  isNaN(Date.parse($("#startDate").val())) == false)
        	FormData["startDate"] = new Date( $("#startDate").val() );
        	
        	if(  isNaN(Date.parse($("#endDate").val())) == false)
        	FormData["endDate"] =new Date(  $("#endDate").val() );
        	
        	FormData["prodName"] = $("#formprodname").val();
        	
        	if(  isNaN(Date.parse($("#startUpdate").val())) == false)
            	FormData["startUpdate"] = new Date( $("#startUpdate").val() );
            	
           	if(  isNaN(Date.parse($("#endUpdate").val())) == false)
           	FormData["endUpdate"] =new Date(  $("#endUpdate").val() );
        	
        	FormData["expiredProd"] = $("#sevenDay").is(":checked");
        	FormData["filterProd"] = $("#multiProd").is(":checked");
        	FormData["minPrice"] = $("#startPrice").val();
        	FormData["maxPrice"] = $("#endPrice").val();
        	FormData["page"] = 1;
        	/*
        	*
        	*/
        	console.log(FormData)
        	ajaxprod();
        	$('.previouspage').removeAttr('hidden');
        	$('.nextpage').removeAttr('hidden');
			
        });
}


function initHistory(){
	$("#historySearch1").empty();
	var latestSearch = []
		latestSearch = JSON.parse(localStorage.getItem("historySearch"))
	if(latestSearch!=null){
		for(var i =0;i<latestSearch.length;i++){
		$historytemplate = `
		<div class="btn-group   border border-secondary border-2 rounded-0 m-1" role="group">
              <span class="historyfind  p-2 ">${latestSearch[i]}</span><button class="removethisbar btn btn-secondary rounded-0">Ｘ</button>
              </div>
		`;
		test = $($historytemplate)
		test.appendTo("#historySearch1")
		}}
		$(".historyfind").click(function(event){
			$("#formprodname").val($(this).text());
		})
		
		$(".removethisbar").click(function(){
			var tmp =JSON.parse( localStorage.getItem("historySearch") )
			console.log(tmp)
			var tmp2 = $(this).parent("div").children("span").text()
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
			initHistory()
		})
	
}
$(function () {
	console.log("hello world");
	FormData = {}
	FormData["page"] = 1;
	var totalpage,nowpage;
	AutoComplete()
	searchProd();
	previousnext();
	initHistory();
});