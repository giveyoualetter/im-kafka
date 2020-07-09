document.write("<script src='https://unpkg.com/axios/dist/axios.min.js'></script>");

//websocket
 function webSocketFunction(){
	if ("WebSocket" in window){	
	   // 打开一个 web socket
	   var ws = new WebSocket("ws://localhost:9000/websocket");	
	   ws.onopen = function(){
		   alert('websocket has open');
		   //var x = document.forms["myForm"]["fname"].value;
		   //console.log(x);
			//发送数据
			ws.send('register:2000');
	   };		
	   ws.onmessage = function (evt){ 
		   //接收数据
		  var received_msg = evt.data;
		  console.log("接收的数据:"+received_msg);
		  document.getElementById('p1').innerHTML=received_msg;		  
	   };		
	   ws.onclose = function(){ 
		  // 关闭 websocket
		  alert("连接已关闭..."); 
	   };
	}else{
	   // 浏览器不支持 WebSocket
	   alert("您的浏览器不支持 WebSocket!");
	}
}