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

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

/**
 * @author Joe Grandja
 */
@Configuration
public class RestTemplateConfiguration {

    private char[] allPassword = "privatekey1234".toCharArray();

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.jks"), allPassword, allPassword)
//                .loadTrustMaterial(ResourceUtils.getFile("classpath:truststore.jks"), allPassword)
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
//        HttpClient client = HttpClients.custom()
//                .build();
//
//        return builder
//                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
//                .build();
//    }
}