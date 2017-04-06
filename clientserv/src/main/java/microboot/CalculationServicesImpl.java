package microboot;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@Service
public class CalculationServicesImpl {

    private final Logger LOG = LoggerFactory.getLogger(CalculationServicesImpl.class);


    private final RestTemplate restTemplate;

    public CalculationServicesImpl(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "reliableCalc", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
    public Object calculate(Double numInputOne, Double numInputTwo, String computType) {

        String remoteCall = "http://localhost:8090/doCalculate" + "?numInputOne=" +
                numInputOne +"&numInputTwo=" + numInputTwo + "&computationType=" + computType;

        LOG.info("Executing remote call with following information: " + remoteCall);

        URI uri = URI.create(remoteCall);

        return this.restTemplate.getForObject(uri, Object.class);
    }

    public Object reliableCalc(Double numInputOne, Double numInputTwo, String computType) {

        LOG.info("Reliable Call had to be executed with following parameters: " +
                "numInputOne=" + numInputOne +" " +
                " numInputTwo=" + numInputTwo + " " +
                " computationType=" + computType);

        return BigDecimal.valueOf(numInputOne).multiply(BigDecimal.valueOf(numInputTwo));
    }

}
