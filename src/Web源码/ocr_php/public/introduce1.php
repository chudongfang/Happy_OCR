<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>OCR文本识别系统</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />

<link type="text/css" rel="stylesheet" href="./css/carousel.css">

<script type="text/javascript" src="./js/jquery.js"></script>

<script type="text/javascript" src="./js/carousel.js"></script>

<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>







<style type="text/css">
  .form{
    position:absolute;
    left:600px;
    top:100px 

  }
  .image{
    position:absolute;
    left:10px;
    top:60px 
  }
  .retext{
    position:absolute;
    top:370px 
  }
  .image_show{
margin-left: auto;
margin-right: auto;
    //margin-left:100px; 
 }
  .body{
    background-image: url("");
  }
  .text{
    text-align: center;       
  }
</style>
</head>

<body class = "body">

<?php require'./head_bar.html';?>



<div id="container">
  <p><a href="#">
    OCR演示
  </a></p>
  <div style="text-align:center;clear:both">
    <script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
    <script src="/follow.js" type="text/javascript"></script>
  </div>
</div>
<script src="js/index.js"></script>



<p><font size="5px">点击图片进行识别</font></p>





<div class="J_Poster poster-main image_show" data-setting='{

"width":800,
"height":500,
"posterWidth":700,
"posterHeight":500,
"scale":0.8,
"autoPlay":true,
"delay":2000,
"speed":300
}'>
    <div class="poster-btn poster-prev-btn"></div>
       <ul class="poster-list">
         <li class="poster-item"><a href="<?php $file_name = "./img/eng_1.png" ; $lan_type = "eng"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
         <li class="poster-item"><a href="<?php $file_name = "./img/eng_2.png" ; $lan_type = "eng"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
         <li class="poster-item"><a href="<?php $file_name = "./img/eng_3.png" ; $lan_type = "eng"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="<?php $file_name = "./img/chi_sim1.png" ; $lan_type = "chi_sim"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="<?php $file_name = "./img/chi_sim_small1.jpg" ; $lan_type = "chi_sim"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="<?php $file_name = "./img/chi_sim3.png" ; $lan_type = "chi_sim"; echo "./intro_deal.php?file_name=".$file_name."&lan_type=". $lan_type;?>"><img src="<?php echo $file_name?>" width="100%" height="100%"></a></li>
       </ul>
    <div class="poster-btn poster-next-btn"></div>
</div>

<!--<p style="height:50px;"></p>

<div class="J_Poster poster-main" data-setting='{
"width":800,
"height":500,
"posterWidth":640,
"posterHeight":270,
"scale":0.8,
"autoPlay":true,
"delay":5000,
"speed":300,
"vericalAlign":"top"
}'>
<div class="poster-btn poster-prev-btn"></div>
    <ul class="poster-list">
        <li class="poster-item"><a href="#"><img src="./img/1.jpg" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="#"><img src="./img/2.jpg" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="#"><img src="./img/3.jpg" width="100%" height="100%"></a></li>
        <li class="poster-item"><a href="#"><img src="./img/4.jpg" width="100%" height="100%"></a></li>

    </ul>
<div class="poster-btn poster-next-btn"></div>
</div>

-->
<script>
$(function(){
Carousel.init($(".J_Poster"));
});
</script>



</body>
</html>
