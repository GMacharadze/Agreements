package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by Георгий on 18.04.2020.
 */
public class MBA extends AbstractBlock {
    public MBA(JSONObject object, String id_info) {
        super(10, "mba");
        this.fieldName = new String[]{
                "id_info", "target", "kbk", "yearpay",
                "sumsubcur", "summba", "levelfin",
                "analyticalcode", "projectname", "codefaip"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
