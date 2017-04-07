package microboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@Service
public class ComputatorServiceImpl {

    private final Logger LOG = LoggerFactory.getLogger(ComputatorServiceImpl.class);

    private static Map<String, ComputatorService> COMPUTATOR_MAP;

    public ComputatorServiceImpl() {

        COMPUTATOR_MAP = new TreeMap<String, ComputatorService>();
        COMPUTATOR_MAP.put("multiply", new Multiplier());

    }

    public Object calculate(Double valToCalcOne, Double valToCalcTwo, String typeOfComputation) throws RemoteServException {

        Object computationResult = null;

        if (COMPUTATOR_MAP.containsKey(typeOfComputation)) {

            computationResult = COMPUTATOR_MAP.get(typeOfComputation).compute(valToCalcOne, valToCalcTwo);

            LOG.info("Calculated for inputValueOne: " + valToCalcOne + " and inputValueTwo: " + valToCalcTwo
                    + " operation: " + typeOfComputation + " result: " + computationResult);
        } else {

            LOG.error(new RemoteServException("Unsupported Operation: " + typeOfComputation).getMessage());

            throw new RemoteServException("Unsupported Operation: " + typeOfComputation);
        }

        return computationResult.toString();

    }

}
