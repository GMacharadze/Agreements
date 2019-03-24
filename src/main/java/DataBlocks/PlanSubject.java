package DataBlocks;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Георгий on 24.03.2019.
 */


public class PlanSubject extends  AbstractBlock{

    public PlanSubject(JSONObject object, String id_info) {
        super(11, "planssubject");
        this.fieldName = new String[]{
                "id", "id_info", "sumLastYrNexec", "note",
                "sumLastYrExec", "conditsign", "analyticalCode",
                "kbk", "rate", "purpose", "outersystem"
        };

        type = BLOCK_TYPE.BLOCK_TYPE_WITH_LINKS_FROM;
        nested_tables = new String[] {"facts"};

        data = new HashMap<>();
        data.put(fieldName[1], id_info);
        for (int i = 2; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());

        nested_array = new ArrayList<>(nested_tables.length);

        for (int i = 0; i < nested_tables.length; ++i) {
            nested_array.add(new ArrayList<>());
            JSONArray array = (JSONArray) object.get(nested_tables[i]);
            for (Object obj : array)
                nested_array.get(i).add(getObject(i, (JSONObject) obj));
        }
    }

    private AbstractBlock getObject(int nested_id, JSONObject obj) {
        if (nested_id == 0)
            return new Facts(obj, data.get(fieldName[0]));
        return null;
    }
}
