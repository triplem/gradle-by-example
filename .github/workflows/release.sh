#!/usr/bin/env sh
cd ../..
./gradlew -Pversion=$1 -PGITHUB_TOKEN+$GITHUB_TOKEN publish
export revnumber=$1
echo "revnumber=$1" >> $GITHUB_ENV