package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class LocalAddress extends AbstractBlock {

    public LocalAddress(JSONObject object) {
        super(16, "LocalAddress");
        this.fieldName = new String[]{
                "countryCode", "countryName", "regionCode",
                "regionName", "postIndex", "localCode", "localName", "oktmo",
                "struct", "street", "streetType", "object", "districtName",
                "settleName", "buildingType", "roomType"
        };
        data = new HashMap<>();
        for (int i = 0; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
