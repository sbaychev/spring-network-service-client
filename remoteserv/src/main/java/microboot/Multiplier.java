package microboot;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by stefanbaychev on 3/24/17.
 */
@Component
public class Multiplier implements ComputatorService {

    public Multiplier(){}

    @Override
    public BigDecimal compute (Double numValueOne, Double numValueTwo) {

        return BigDecimal.valueOf(numValueOne).multiply(BigDecimal.valueOf(numValueTwo));

    }
}
