#!/bin/bash
unzip /home/wwwroot/default/ocr_php/public/data_train/$1.zip -d /home/wwwroot/default/ocr_php/public/data_train/ 
mv /home/wwwroot/default/ocr_php/public/data_train/img_train /home/wwwroot/default/ocr_php/public/data_train/$2

chmod 777 /home/wwwroot/default/ocr_php/public/data_train/$2

cp /home/wwwroot/default/ocr_php/public/data_train/creat.sh /home/wwwroot/default/ocr_php/public/data_train/$2

chmod +x /home/wwwroot/default/ocr_php/public/$2/creat.sh
