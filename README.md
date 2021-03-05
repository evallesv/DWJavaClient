# DWJavaClient

1. Get adoptopenjdk-8: https://adoptopenjdk.net/
2. Get maven 3.6.x: https://maven.apache.org/
3. Add above to your path if neccessary.
4. `git clone git@github.com:evallesv/DWJavaClient.git`
5. Renombrar el archivo **src/main/java/config.properties.sample** a **src/main/java/config.properties** y colocar el host de la API de docuware, el usuario y contraseña
5. `mvn clean package`
6. `java -jar target/test-1.0.0-SNAPSHOT.jar`