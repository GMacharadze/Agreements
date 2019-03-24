/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Receiver extends AbstractBlock {

    public Receiver(JSONObject object, String id_info) {
        super(22, "receiver");
        this.fieldName = new String[]{
                "id_info", "budgetName", "oktmo", "fullName", "shortName", "fullNameLat", "okopf",
                "inn", "kpp", "dateAccount", "codeRecipient", "shortNameLat", "codeReestr", "accountNum",
                "accountOrgCode", "regCountryCode", "regCountryName", "admelement", "phoneNumber", "email",
                "codeReestrGrbs", "grbsFullName"
        };

        type = BLOCK_TYPE.BLOCK_TYPE_WITH_LINKS_TO;
        nested_tables = new String[] {"localAddress", "foreignAddress"};

        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());

        nested = new AbstractBlock[] {
                new LocalAddress((JSONObject) object.get(nested_tables[0])),
                new ForeignAddress((JSONObject) object.get(nested_tables[1]))
        };
    }

    @Override
    public String getQuery() {
        String sqlQuery = "insert into " + table + " values (";

        for (int i = 0; i < fieldCount + nested.length - 1; ++i)
            sqlQuery += "?,";
        sqlQuery += "?)";
        return sqlQuery;
    }
}
