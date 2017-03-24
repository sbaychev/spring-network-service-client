package microboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.util.concurrent.Callable;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@RestController
@SpringBootApplication
@EnableCircuitBreaker
public class ClientServApplication {

    private final Logger LOG = LoggerFactory.getLogger(ClientServApplication.class);

    /**
     * Init binder.
     *
     * @param webDataBinder the web data binder
     */
    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ComputeTypeIndex.class, new ComputeTypeIndexBinder());
    }

    @Autowired
    private CalculationServicesImpl calculationServices;

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping("/calculate")
    public Callable<ResponseEntity<?>> calculate(@Valid @RequestParam Double numInputOne, @Valid @RequestParam Double numInputTwo,
                                                @Valid @RequestParam ComputeTypeIndex computType) {

        LOG.info("Received a calculate call with params: " + numInputOne + " " + numInputTwo  + " " +  computType.toString());

        return ()-> ResponseEntity.ok(calculationServices.calculate(numInputOne, numInputTwo, computType.toString()));
    }

    /*
    * Handle invalid payload search error.
    *
    *   @param e the e
    *   @return the search error
    */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class})
    private @ResponseBody SearchError handleInvalidPayload(Exception e) {

        LOG.error("Data Integrity Violation", e);

        SearchError error = new SearchError();

        error.setCode("0001");
        error.setMessage("Data Integrity Violation! Check Log for exception information");

        return error;
    }


    public static void main(String... args) {
        SpringApplication.run(ClientServApplication.class, args);
    }
}
