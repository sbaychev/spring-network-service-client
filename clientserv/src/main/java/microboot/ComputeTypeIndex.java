package microboot;

/**
 * Created by stefanbaychev on 3/24/17.
 */
public enum ComputeTypeIndex {

    multiply(Multiply.class);


    final Class entityClass;

    ComputeTypeIndex(Class entityClass) {
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }
}
