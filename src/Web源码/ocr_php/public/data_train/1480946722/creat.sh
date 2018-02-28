#!/bin/bash 
homepath=/home/wwwroot/default/ocr_php/public/data_train/
tesseract ${homepath}$3xi.ha.exp0.tif ${homepath}$3xi.ha.exp0 -l $1 box.train
unicharset_extractor -D ${homepath}$3 ${homepath}$3xi.ha.exp0.box
echo ha 0 0 0 0 0 >> ${homepath}$3font_properties
shapeclustering -F ${homepath}$3font_properties -U ${homepath}$3unicharset -D ${homepath}$3 ${homepath}$3xi.ha.exp0.tr
mftraining -F ${homepath}$3font_properties -U ${homepath}$3unicharset -O ${homepath}$3xi.unicharset -D ${homepath}$3 ${homepath}$3xi.ha.exp0.tr 
cntraining -D ${homepath}$3 ${homepath}$3xi.ha.exp0.tr
mv ${homepath}$3inttemp ${homepath}$3xi.inttemp 
mv ${homepath}$3pffmtable ${homepath}$3xi.pffmtable
mv ${homepath}$3normproto ${homepath}$3xi.normproto
mv ${homepath}$3shapetable ${homepath}$3xi.shapetable
combine_tessdata ${homepath}$3xi.
rm ${homepath}$3xi.inttemp
rm ${homepath}$3xi.unicharset
rm ${homepath}$3xi.pffmtable
rm ${homepath}$3xi.shapetable
rm ${homepath}$3xi.normproto
rm ${homepath}$3font_properties
rm ${homepath}$3unicharset
mv ${homepath}$3xi.traineddata  ${homepath}$3$2.traineddata
cp ${homepath}$3$2.traineddata /usr/local/share/tessdata/
cp ${homepath}$3$2.traineddata /home/wwwroot/default/ocr_php/public/tessdata/
