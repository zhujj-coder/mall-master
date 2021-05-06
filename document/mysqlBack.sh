#!/bin/bash
date_now=$(date "+%Y%m%d-%H%M%S")
backUpFolder=/home/db/backup/mysql8
username="root"
password="I7dYazi$wSyCNgML"
db_name="db_common"
#定义备份文件名
fileName="${db_name}_${date_now}.sql"
#定义备份文件目录
backUpFileName="${backUpFolder}/${fileName}"
echo "starting backup mysql ${db_name} at ${date_now}."
/usr/bin/mysqldump -u${username} -h 127.0.0.1 -p${password}  --lock-all-tables --flush-logs ${db_name} > ${backUpFileName}

db_name_game="game"
#定义备份文件名
fileName_game="${db_name_game}_${date_now}.sql"
#定义备份文件目录
backUpFileName_game="${backUpFolder}/${fileName_game}"
echo "starting backup mysql ${db_name_game} at ${date_now}."
/usr/bin/mysqldump -u${username} -h 127.0.0.1 -p${password}  --lock-all-tables --flush-logs ${db_name_game} > ${backUpFileName_game}

db_name_mall="mall"
#定义备份文件名
fileName_mall="${db_name_mall}_${date_now}.sql"
#定义备份文件目录
backUpFileName_mall="${backUpFolder}/${fileName_mall}"
echo "starting backup mysql ${db_name_mall} at ${date_now}."
/usr/bin/mysqldump -u${username} -h 127.0.0.1 -p${password}  --lock-all-tables --flush-logs ${db_name_mall} > ${backUpFileName_mall}

# 删除7天之前的就备份文件
find $backUpFolder/* -mtime +7 -exec rm {} \;

date_end=$(date "+%Y%m%d-%H%M%S")
echo "finish backup mysql database ${db_name} at ${date_end}."




