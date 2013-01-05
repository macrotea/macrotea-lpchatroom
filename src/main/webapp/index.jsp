<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>长轮询聊天室-DEMO</title>

<script src="jquery.js"></script>

<script>
function chat() {
    $.ajax({
        type : "POST",
        url : "chat.do",
        data : {
            message : $("#content").val()
        },
        success : function(r) {
            $("#content").val("");
        }
    });
}

var messageOffset = 0;

function longpoll() {
    $.ajax({
        type : "POST",
        url : "longpoll.do",
        data : {
            messageOffset : messageOffset
        },
        success : function(r) {
            messageOffset += r.length;
            for ( var i = 0; i < r.length; ++i) {
                var p = document.createElement("p");
                $(p).text(r[i].from + ": " + r[i].content);
                $("#messages")[0].appendChild(p);
            }
            
            //若成功则继续轮询
            longpoll();
        }
    });
}

$(function(){
	$("#submit").click(function(){
		if($("#content").val().trim().length==0){
			alert("请输入内容");
		}else{
			chat();
		}
	});
})
</script>
</head>
<body onload="longpoll()">
	<input id="content" />
	<input id="submit" type="submit" value="我要发言"/>
	<div id="messages"></div>
</body>
</html>