function parseTime(inputTime){
	var tmp = new Date(inputTime)
	var month = tmp.getMonth()+1;
	var Day = tmp.getDate();
	value = month+"/"+Day
	// console.log(value)
	return value

}

function getURLParam(){
	var urlstring = location.href
    var url = new URL(urlstring)
    return url.searchParams.get('prodid')
}

function AjexSinyaProduct(prod_id){
    return $.ajax({
		type: "post",
		url: "/SinyaProduct/"+prod_id,
		contentType:'charset=utf-8',
		// data:datajson,
		async: !1,
	})
}

function drawtag(tagsheet){

	$("#tagsection").empty();
	for(var i =0;i<tagsheet.length;i++){
		template = `<a href="./Sinyatag?tagid=${tagsheet[i].fk_tag}"><div value='${tagsheet[i].fk_tag}' class='tagbar m-1 btn btn-primary'>${tagsheet[i].tag_zhtw}</div></a>`;
		$(template).appendTo("#tagsection")
	}
	// $(".tagbar").click(function () {
	// 	// localStorage.setItem("prod_id", $(this).attr("id"));
	// 	// localStorage.setItem("prod_name", $(this).children().text());
	// 	// console.log( $(this).children().text())
	// 	location.href = "./tag"+"?tagid="+$(this).attr("value");
	// })
}

function drawchart(datasheet){
	// var xyValues=[]
	var xValues = []
	var yValues = []
	var historyhigh = datasheet[0]["price"];
	var historylow = datasheet[0]["price"];
	var lastprice = datasheet[datasheet.length-1]["price"];
	var timetag = parseTime(datasheet[datasheet.length-1]["upload_date"]);
	for(var i=0;i<datasheet.length;i++){
		// tmp = {x:datasheet[i]["upload_date"],y:datasheet[i]["fk_prod_id"]};
		// xyValues.push(tmp);
		xValues.push(   parseTime(datasheet[i]["upload_date"]) )
		yValues.push(datasheet[i]["price"])
		if(datasheet[i]["price"]>historyhigh){
			historyhigh = datasheet[i]["price"]
		}
		if(historylow>datasheet[i]["price"]){
			historylow = datasheet[i]["price"]
		}
	}
	Chart.defaults.global.defaultFontColor = "#fff";
	try{
	new Chart("myChart", {
		type: "line",
		data: {
		  labels: xValues,
		  datasets: [{ 
			data: yValues,
			borderColor: "red",
			fill: false,
			backgroundColor: "white",
		  }]
		},
		options: {
		  legend: {display: false},
		  scales: {
			xAxes: [{
			  display: true,
			  gridLines: {
				display: true,
				color: "rgba(0, 0, 0, 0.573)"
			  },
			  scaleLabel: {
				display: true,
				labelString: '日期'
			  }
			}],
			yAxes: [{
			  display: true,
			  gridLines: {
				display: true,
				color: "rgba(0, 0, 0, 0.573)"
			  },
			  scaleLabel: {
				display: true,
				labelString: '價格'
			  }
			}]}
		},

	  })
	  }catch(e){
		  console.log(e)
	  }
	  originalhigh = $("#historyhigh").text()
	  console.log(originalhigh)
	  originallow = $("#historylow").text()
	  console.log(originallow)
	  originallastupdate = $("#lastupdate").text()
	  $("#historylow").text(originallow + "$"+historylow)
	  $("#historyhigh").text(originalhigh + "$"+historyhigh)
	  $("#lastupdate").text(originallastupdate + timetag +" $"+lastprice)
}

$(function(){
	prod_id = getURLParam();
	console.log(prod_id)
	data = AjexSinyaProduct(prod_id)["responseJSON"]
	console.log(data)
	
	var hellohowareyou = data["productname"]["prodname"];
	$("#hellohowareyou").text(hellohowareyou)


    datasheet= data["priceanddate"];
	console.log(datasheet)
	drawchart(datasheet)

	tagsheet = data["taginfo"];
	if(tagsheet !=null){
		drawtag(tagsheet)
	}

});