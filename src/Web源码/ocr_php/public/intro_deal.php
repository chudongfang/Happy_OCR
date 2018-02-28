<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>OCR文本识别系统</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
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
    left:450px;
    top :70px;
  }
  .btn{
    position:absolute;
    left:450px;
    top :330px;
  }

  .body{
    background-image: url("./img/background.jpg");
  }
  .text{
    text-align: center;       
  }
</style>
</head>

<body class = "body">
<?php //$file_path = './img/180.jpg'?>

<?php require'./head_bar.html'; ?>


<?php
    $file_path =  $_GET['file_name'];
    $lan_type1  =  $_GET['lan_type'];
    require '../vendor/autoload.php';

    $lan_type = sprintf(" -l %s",$lan_type1);
    $ocr = new \TesseractOCR($file_path);
    $string = $ocr ->lang($lan_type) ->run();

?>


<div class = "image">
   <p>
    <img src=<?php  echo $file_path;?>
         width="440" height="600" />
   </p>
</div>

<div class = "retext" style ="width :588px">
  <p>识别结果：</p>
  <textarea class="form-control" rows="25"><?php echo $string; ?></textarea>
</div>
<div class = "btn"><a href="./introduce1.php" class="btn btn-primary btn-lg active" role="button">返回</a></div>





</body>
</html>
