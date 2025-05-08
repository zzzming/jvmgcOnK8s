FROM openjdk:17-jdk-slim

WORKDIR /app

COPY GCStressTest.java ./
RUN javac GCStressTest.java

# Set JVM options to enable GC logging and optimize for container environment
ENV JAVA_OPTS="-Xms256m -Xmx512m \
    -XX:+UseParallelGC \
    -XX:+PrintGCDetails \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCTimeStamps \
    -Xlog:gc*:stdout:time,tags \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/heapdump.hprof"

CMD ["java", "${JAVA_OPTS}", "GCStressTest"]
