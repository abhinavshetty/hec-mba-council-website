#!bin/bash
echo
echo
echo --------------- Running bot warehouse deploy script -------------------
echo
echo
# echo current working directory
echo ---------- ECHOING CURRENT WORKING DIRECTORY ------------------
echo
echo
CURRENT_WD="dao-interface-service"
echo $ROOT_PATH/$CURRENT_WD
echo
echo

# copy package to target
cp $ROOT_PATH/$CURRENT_WD/target/$CURRENT_WD.war /opt/erudite-labs/$CURRENT_WD/
chmod 777 $DEPLOY_PATH/$CURRENT_WD/*
chmod 777 $DEPLOY_PATH/$CURRENT_WD/logs/*
chmod 777 $DEPLOY_PATH/$CURRENT_WD/pid/*

echo Copy of dao interface package success
echo
echo

sh /$DEPLOY_PATH/$CURRENT_WD/start.sh
return 0