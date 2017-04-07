package microboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RemoteServApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void calculateTest() {

        Object resp = rest.getForObject("/doCalculate?numInputOne="+Double.valueOf(1d)+"&numInputTwo="+Double.valueOf(2d)+"&computationType=multiply", String.class);

        assertThat(BigDecimal.valueOf(Double.parseDouble(resp.toString())).compareTo(BigDecimal.valueOf(2d)));
    }

    @Test
    public void calculateWrongCommandTest() {

        Object resp = rest.getForObject("/doCalculate?numInputOne="+Double.valueOf(1d)+"&numInputTwo="+Double.valueOf(2d)+"&computationType=add", Object.class);

        assertThat(resp.toString()).contains(("Unsupported Operation: add"));
    }
}
