FROM openjdk:11

ENV SBT_VERSION 1.6.2

RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zipRUN unzip sbt-$SBT_VERSION.zip -d ops



CMD /ops/sbt/bin/sbt run