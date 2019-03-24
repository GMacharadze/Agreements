package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Георгий on 24.03.2019.
 */
public class FaipSubject  extends AbstractBlock{

    public FaipSubject(JSONObject object, String id_info) {
        super(11, "FaipSubject");
        this.fieldName = new String[]{
                "id_info", "name", "code", "sumYear",
                "sumFtYear","sumSkYear", "sumTrYear", "sumFrYear",
                "sumOtherYear", "date", "sumMonth",
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
