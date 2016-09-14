<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="box">
    <div class="title">某某系统</div>
    <div class="account">
        <span>账号:</span><input type="text" id="account" value="liyuan">
    </div>
    <div class="password">
        <span>密码:</span><input type="text" id="password" value="1234">
    </div>
    <a href="javascript:;" class="login" onclick="login()">登录</a>
</div>
</body>
<style>
    .box{
        width:300px;
        height: 200px;
        border:1px solid #ccc;
        position: relative;
        left:50%;
        top:50%;
        margin-left:-150px;
        margin-top:-100px;
        padding: 0px 20px 0px 20px ;
    }
    .box .title{
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        height: 60px;
        line-height: 60px;
    }
    .account,.password{
        margin-bottom: 20px;
    }

    .account span,.password span{
        display: inline-block;
        width: 50px;
    }
    .login{
        display: block;
        text-align: center;
        width: 60px;
        height:30px;
        line-height: 30px;
        text-decoration: none;
        border: 1px solid #666;
        background-color: #666;
        color: white;
        border-radius: 3px;
        margin-left:100px;
    }

</style>
<script src="/js/jquery-2.1.4.min.js"></script>
<script>
    $(function(){
        $('#login').click(login);
    });
    function login(){
        $.ajax({
            url: '/login.json',
            data: {
                account: $('#account').val(),
                password: $('#password').val()
            },
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if(data.success){
                    alert("操作成功");
                    //location.href = "/demo/";
                }else{
                    alert(data.msg);
                }

            }
        });
    }
</script>
</html>