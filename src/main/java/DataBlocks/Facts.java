package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Георгий on 24.03.2019.
 */
public class Facts extends AbstractBlock {

    public Facts(JSONObject object, String planssubject_id) {
        super(5, "facts");
        this.fieldName = new String[]{
                "id_planssubject", "period", "sumsubcur", "sumsubrub",
                "outersystem"
        };
        data = new HashMap<>();
        data.put(fieldName[0], planssubject_id);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
