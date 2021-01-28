#!bin/bash
echo
echo
echo --------------- Running dao interface build script -------------------
echo
echo
# echo current working directory
echo ---------- ECHOING CURRENT WORKING DIRECTORY ------------------
echo
echo
CURRENT_WD=$ROOT_PATH"/dao-interface-service"
echo $CURRENT_WD
echo
echo

# build project to ./target
mvn clean package --file "$CURRENT_WD/pom.xml"