package microboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.DEFAULT_CHARSET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by stefanbaychev on 3/24/17.
 */

@RunWith(SpringRunner.class)
@RestClientTest(CalculationServicesImpl.class)
public class CalculationServicesImplTests {

    @Autowired
    private CalculationServicesImpl calculationServices;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void calculationTest() {

        this.server.expect(requestTo("http://localhost:8090/doCalculate?numInputOne=" + Double.valueOf(2d) + "&numInputTwo=" + Double.valueOf(2d) + "&computationType=multiply"))
                .andRespond(withSuccess(BigDecimal.valueOf(4d).toString(), new MediaType("application", "json", DEFAULT_CHARSET)));

        assertThat(calculationServices.calculate(Double.valueOf(2d), Double.valueOf(2d), "multiply")).toString().compareTo(BigDecimal.valueOf(Double.valueOf(4d)).toString());

    }


    @Test
    public void reliable() {

        assertThat(calculationServices.reliableCalc(Double.valueOf(2d), Double.valueOf(1d), "multiply")).isEqualTo(BigDecimal.valueOf(2d).setScale(2));

    }
}
