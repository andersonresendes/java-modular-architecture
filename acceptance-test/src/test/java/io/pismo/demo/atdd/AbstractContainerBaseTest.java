package io.pismo.demo.atdd;

import io.restassured.RestAssured;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

abstract class AbstractContainerBaseTest {

  private static final GenericContainer APP;
  private static final MariaDBContainer MY_SQL_CONTAINER;

  static {
    final var network = Network.newNetwork();

    MY_SQL_CONTAINER = (MariaDBContainer) new MariaDBContainer("mariadb:latest")
        .withNetwork(network)
        .withNetworkAliases("testdb");

    APP = new GenericContainer("demo-test:integration")
        .dependsOn(MY_SQL_CONTAINER)
        .withNetwork(network)
        .withEnv("MYSQL_USER", "test")
        .withEnv("MYSQL_PASSWORD", "test")
        .withEnv("MYSQL_URL", "jdbc:mariadb://testdb:" + MySQLContainer.MYSQL_PORT + "/test?autoReconnect=true&useSSL=false")
        .withExposedPorts(8080);

    MY_SQL_CONTAINER.start();
    APP.start();
    init();
  }


  private static void init() {
    RestAssured.baseURI = "http://" + APP.getHost();
    RestAssured.port = APP.getFirstMappedPort();
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }


}
