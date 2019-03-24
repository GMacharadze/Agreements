package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Георгий on 24.03.2019.
 */
public class Faip extends AbstractBlock{

    public Faip(JSONObject object, String id_info) {
        super(12, "faip");
        this.fieldName = new String[]{
                "id_info", "sumTotal", "sumMonth", "code",
                "sumYear", "sumFtYear","sumSkYear", "sumTrYear",
                "sumFrYear", "sumOtherYear", "date", "name"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
