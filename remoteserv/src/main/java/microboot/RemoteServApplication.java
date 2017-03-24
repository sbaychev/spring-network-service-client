package microboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by stefanbaychev on 3/24/17.
 */

@RestController
@SpringBootApplication
public class RemoteServApplication {

    private final Logger LOG = LoggerFactory.getLogger(RemoteServApplication.class);


    @Autowired
    ComputatorServiceImpl computatorService;

    @RequestMapping(value = "/doCalculate")
    public Double doCalculate(Double numInputOne, Double numInputTwo, String computationType) throws Exception {

        LOG.info("Calculation started....");

        try {
            return computatorService.calculate(numInputOne, numInputTwo, computationType);
        } catch (Exception e) {

            LOG.warn("Error when performing calculation: " + e);

            throw new Exception(e);
        }
    }

    /*
    * Handle invalid payload search error.
    *
    *   @param e the e
    *   @return the search error
    */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    private @ResponseBody SearchError handleInvalidPayload(Exception e) {

        LOG.error("Data Integrity Violation", e);

        SearchError error = new SearchError();

        error.setCode("0001");
        error.setMessage("Data Integrity Violation! Check Log for exception information");

        return error;
    }


    public static void main(String... args) {
        SpringApplication.run(RemoteServApplication.class, args);
    }
}
