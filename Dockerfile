FROM openjdk:17-jdk-slim

WORKDIR /app

COPY GCStressTest.java ./
RUN javac GCStressTest.java

CMD ["sh", "-c", "java $JAVA_OPTS GCStressTest"]
