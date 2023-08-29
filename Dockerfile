FROM openjdk:17-jdk
EXPOSE 8189
ADD /target/UsersAndRewards.jar UsersAndRewards.jar
ENTRYPOINT ["java", "-jar", "UsersAndRewards.jar"]