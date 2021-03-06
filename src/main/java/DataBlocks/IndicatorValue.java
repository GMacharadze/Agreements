/**
 * Author: Georgy Macharadze
 * Date: 10.02.2019
 */
package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Класс, определяющий структуру блока indicatorvalue
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class IndicatorValue extends AbstractBlock {

    public IndicatorValue(JSONObject object, String id_info) {
        super(7, "indicatorvalue");
        this.fieldName = new String[]{
                "id_info", "value", "reportdate",
                "okei", "name", "indnum", "loaddate"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id_info);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
