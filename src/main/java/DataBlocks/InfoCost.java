/**
 * Author: Georgy Macharadze
 * Date: 10.02.2019
 */
package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Класс, определяющий структуру блока infocost
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class InfoCost extends AbstractBlock {

    public InfoCost(JSONObject object, String id_info) {
        super(5, "infocost");
        this.fieldName = new String[]{
                "id_info", "dateinfo", "volimplemcost",
                "currencycode", "loaddate"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
