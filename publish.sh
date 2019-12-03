# 前端编译构建
cd /Users/lbx/dev/web/project/doraemon
npm run build
cd dist
rm -rf /Users/lbx/dev/server/project/doraemon/src/main/resources/static/*
cp -r * /Users/lbx/dev/server/project/doraemon/src/main/resources/static

#cd /Users/lbx/dev/server/project/doraemon
#./gradlew bootWar
#scp -P 22 ./build/libs/doraemon-0.0.1-SNAPSHOT.war root@49.235.52.159:/opt/apache-tomcat-8.5.45/webapps/doraemon.war
