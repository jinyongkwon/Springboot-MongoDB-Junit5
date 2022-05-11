package site.metacoding.mongotest.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.metacoding.mongotest.domain.Naver;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 통합테스트 => 메모리에 모든 레이어가 다뜸.
public class NaverApiControllerTest {

    @Autowired // DI
    private TestRestTemplate rt; // Controller를 때리기 위한 http통신을 위한것
    private static HttpHeaders headers; // 다른메서드에서도 헤더가 필요하기 때문에 위에 만듬.

    @BeforeAll // 모든애들이 실행되기 전 최초에 한번만 실행됨, Each는 계속실행되는 차이가있음.
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void save_테스트() throws JsonProcessingException {
        // given => 가짜데이터
        Naver naver = new Naver();
        naver.setTitle("스프링1강");
        naver.setCompany("재밌어요");

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(naver); // Java Object를 Json으로 바꿔줌. => Byte도 Object로 바꾸기 가능

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);

        // when => 실행할것
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.POST, httpEntity, String.class);
        // header를 모르기때문에 객체를 만들어 데이터를 넣어줌

        // then => 결과가 어떤지 검증
        // System.out.println("=====================================================");
        // System.out.println(response.getBody());
        // System.out.println(response.getHeaders());
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getStatusCode().is2xxSuccessful());
        // System.out.println("=====================================================");
        // assertTrue(response.getStatusCode().is2xxSuccessful());
        DocumentContext dc = JsonPath.parse(response.getBody()); // JsonParser로 Test
        // System.out.println(dc.jsonString());
        String title = dc.read("$.title");
        System.out.println(title);
        assertEquals("스프링1강", title);
    }

    @Test
    public void findAll_테스트() {
        // given

        // when
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.GET, null, String.class);

        // then
        // DocumentContext dc = JsonPath.parse(response.getBody()); // JsonParser로 Test
        // System.out.println(dc.jsonString());
        // String title = dc.read("$.[0]title");
        // assertEquals("지방선거 곧 다가온다", title);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
