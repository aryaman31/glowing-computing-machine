worker: cd frontend && npm install && npm run build && 
        rm -rf ../src/main/resources/static/* && 
        mv build/* ../src/main/resources/static/ &&
        rm -r /build/
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/gp_scheduling-0.0.1-SNAPSHOT.jar