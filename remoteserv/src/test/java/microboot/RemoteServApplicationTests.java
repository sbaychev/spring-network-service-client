package microboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
        Object resp = rest.getForObject("/doCalculate?numInputOne="+Double.valueOf(1)+"&numInputTwo="+Double.valueOf(2)+"&computationType=multiply", Object.class );
        assertThat(resp).isEqualTo(Double.valueOf(2));
    }

    @Test
    public void calculateWrongCommandTest() {
        Object resp = rest.getForObject("/doCalculate?numInputOne="+Double.valueOf(1)+"&numInputTwo="+Double.valueOf(2)+"&computationType=add", Object.class );
        assertThat(resp.toString()).contains(("Unsupported Operation: add"));
    }
}
