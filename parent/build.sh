#!bin/bash
echo
echo
echo --------------- Running parent build script -------------------
echo
echo
# echo current working directory
echo ---------- ECHOING CURRENT WORKING DIRECTORY ------------------
echo
echo
CURRENT_WD=$ROOT_PATH"/parent"
echo $CURRENT_WD
echo
echo

# build project to ./target
mvn clean install --file "$CURRENT_WD/pom.xml"