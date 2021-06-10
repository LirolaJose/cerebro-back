Run project from command line:
1.   	Open the command line
2.   	Navigate to the folder where pom.xml is located
3.   	Build the project (write in command line: mvn clean install)
4.   	Run  the project (write in command line:
4.1   mvn spring-boot:run
4.2   java -jar .\target\cerebro-1.0.2-SNAPSHOT.jar (use a jar file))

Run project from Docker:
Command line:
1.   	Build project with Profile “docker”:
mvn clean install -Pdocker

2.   	Build Docker image:
docker build --build-arg JAR_FILE=target/*.jar -t cerebro:3.0 .

3.   	Run created image:
docker run -p 8080:8080 -t cerebro:3.0
