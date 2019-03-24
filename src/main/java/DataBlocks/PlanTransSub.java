/**
 * Author: Georgy Macharadze
 * Date: 10.02.2019
 */
package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Класс, определяющий структуру блока plantranssub
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class PlanTransSub extends AbstractBlock {

    public PlanTransSub(JSONObject object, String numadditagreem) {
        super(6, "plantranssub");
        this.fieldName = new String[]{
                "id_addagreement", "kbk", "sumcur", "rate",
                "currencycode", "period"
        };
        data = new HashMap<>();
        data.put(fieldName[0], numadditagreem);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], object.get(fieldName[i]).toString());
    }
}
