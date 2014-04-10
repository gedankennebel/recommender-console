#!/bin/sh
export GRADLE_OPTS="-server -d64 -Xmx2048m -XX:+UseParallelGC -XX:+UseParallelOldGC -Xdebug -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=n"
sh gradlew jettyRun --no-daemon -Dorg.gradle.debug=true
