package microboot;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

/**
 * Created by stefanbaychev on 3/24/17.
 */
public class ComputeTypeIndexBinder extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(ComputeTypeIndex.valueOf(text.toLowerCase(Locale.getDefault())));
    }
}
