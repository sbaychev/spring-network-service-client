package microboot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.DEFAULT_CHARSET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by stefanbaychev on 3/24/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientServApplicationTests {

    private MockRestServiceServer server;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplate rest;

    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(rest);
    }

    @After
    public void teardown() {
        this.server = null;
    }

    @Test
    public void toCalcTest() {

        this.server.expect(requestTo("http://localhost:8090/doCalculate?numInputOne="+Double.valueOf(2d)+"&numInputTwo="+Double.valueOf(3d)+"&computationType=multiply"))
                .andExpect(method(HttpMethod.GET)).
                andRespond(withSuccess(BigDecimal.valueOf(6d).toString(), new MediaType("application", "json", DEFAULT_CHARSET)));

        Object result = testRestTemplate.getForObject("/calculate?numInputOne="+Double.valueOf(2d)+"&numInputTwo="+Double.valueOf(3d)+"&computType=multiply", String.class);

        assertThat(result.toString()).isEqualTo(BigDecimal.valueOf(6d).toString());
    }

    @Test
    public void toCalcFailureTest() {

        this.server.expect(requestTo("http://localhost:8090/doCalculate?numInputOne="+Double.valueOf(1d)+"&numInputTwo="+Double.valueOf(2d)+"&computType=multiply"))
                .andExpect(method(HttpMethod.GET)).andRespond(withServerError());

        Object result = testRestTemplate.getForObject("/calculate?numInputOne="+Double.valueOf(1d)+"&numInputTwo="+Double.valueOf(2d)+"&computType=multiply", Object.class);

        assertThat(BigDecimal.valueOf(Double.parseDouble(result.toString()))).isEqualTo(BigDecimal.valueOf(2d));
    }
}
