/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Plans extends AbstractBlock {

    public Plans(JSONObject object, String id_info) {
        super(17, "plans");
        this.fieldName = new String[]{
                "id_info", "purpose", "kbk", "sumTotal", "sumMonth", "code",
                "sumYear", "sumFtYear","sumSkYear", "sumTrYear",
                "sumFrYear", "sumOtherYear", "date", "analyticalCode",
                "sumLastYrExec", "sumLastYrNexec", "note"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
