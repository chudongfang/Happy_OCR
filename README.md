# Happy_OCR
一个基于tessrect，可以在Android，Linux，Windows进行图片文本识别和文本训练的跨平台系统。

---

## 项目介绍
    研发本项目主要目的是向用户提供可训练的智能高精度的文本识别功能，包括对各种语言文字、手写体、数字等。而本项目可以较好的满足用户需求，提供可训练的智能文本识别功能。本项目提供两种版本：Android版和web网页。
    
    网页入口：[http://115.159.205.168/ocr_php/public/index.php](http://115.159.205.168/ocr_php/public/index.php) 
  
### 项目功能
- 1.对多国语言进行识别。用户上传图片或pdf，返回识别结果。共有约40种语言库，包括汉语、英语、日语、法语、韩文等。
- 2.为用户提供训练功能。由于文本识别率在某种程度上还不够高，用户可以根据识别率，训练对应的文字，提高识别率。
- 3.训练新语言。可以针对某个人手写体，训练出对应的库，实现识别手写体功能。也可针对某种字体，如行书 进行训练。这样保证了灵活、多变性。
- 4.图片自动优化功能：由于用户上传的图片，可能存在杂乱底色或一些影响识别的杂色，该系统可以针对图片进行处理、优化，提高图片的清晰度与对比度，提供识别精度。
- 5.分账户管理：在手机安卓app端，用户可以建立自己的账户，存储自己识别过的文件。
- 6.N-grem的文本处理算法：G-grep算法根据词语出现的频率对识别结果进行矫正。如“太阳”被识别成“大阳”，N-grem算法可以对其进行矫正。

### 项目实现
本产品创建了一个可训练可学习的智能图像文本识别平台，提供对图片和PDF的文本识别服务。并借助平台，提供多种服务，以提高识别率、满足客户需求。具体服务包括：

（1）训练功能:由用户提供素材对文字进行训练，提高文本识别精度。

（2）新语言	训练:由用户提供素材对新语言进行训练。

（3）图像处理：对用户的图片进行处理，如灰度处理、二值化、去噪、旋转等。

（4）用户管理；对用户识别过的素材和结构进行保存

提供三个平台的使用：

1.Windows 平台

2.Linux   平台

3.Android 
