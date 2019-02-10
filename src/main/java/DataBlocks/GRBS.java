/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Класс, определяющий структуру блока grbs
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class GRBS extends AbstractBlock {

    public GRBS(JSONObject grbs, String id_info) {
        super(28, "grbs");
        this.fieldName = new String[]{
                "id_info", "okopf", "fullName", "shortName", "inn", "kpp",
                "location", "dateAccount", "kbkInput", "grbsAccount",
                "codeReestr", "countryCode", "countryName", "regionCode",
                "regionName", "districtName", "settleName", "postIndex",
                "locationOktmo", "localCode", "localName", "structType",
                "streetType", "objectType", "buildingType", "roomType",
                "tofkcode", "tofkname"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], grbs.get(fieldName[i]).toString());
    }
}
