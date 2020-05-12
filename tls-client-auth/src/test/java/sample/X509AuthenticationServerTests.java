/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Joe Grandja
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = X509AuthenticationServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class X509AuthenticationServerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testWithClientCert() {
        String response = this.restTemplate.getForObject("https://localhost:" + port + "/user", String.class);
//        String response = this.restTemplate.getForObject("https://localhost:8443/user", String.class);
        System.out.println(response);
    }
}
