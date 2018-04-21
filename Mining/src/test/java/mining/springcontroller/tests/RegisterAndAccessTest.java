package mining.springcontroller.tests;

import mining.model.TestParameters;
import mining.springcontroller.MinerController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


// 注意，这个测试是让矿机去对主机发请求（逻辑看MinerController），而不是直接给主机发！
// 是单元测试controller逻辑，不是集成测试

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterAndAccessTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MinerController minerController;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void accessWithoutRegister() {
        assertEquals("invalid",minerController.test());

    }

    @Test
    public void accessAfterRegister() {
        minerController.register();
        assertEquals("miner request", minerController.test());
    }

    private String getRoute(String route) {
        return "http://localhost:" + port + "/" + route;
    }
}