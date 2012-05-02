#! /bin/bash

version=$1

if [ -z $version ];
then
  echo "A version must be specified"
  exit
fi
mvn release:prepare release:perform -DdevelopmentVersion=trunk-SNAPSHOT -DreleaseVersion=${version}