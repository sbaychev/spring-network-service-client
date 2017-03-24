package microboot;

import org.springframework.stereotype.Component;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@Component
public class Multiplier implements ComputatorService {

    public Multiplier(){}

    @Override
    public Double compute(Double numValueOne, Double numValueTwo) {
        return numValueOne * numValueTwo;
    }
}
