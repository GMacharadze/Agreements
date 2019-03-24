/**
 * Author: Georgy Macharadze
 * Date: 10.02.2019
 */
package DataBlocks;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс, определяющий структуру блока addagreement
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class AddAgreement extends AbstractBlock{

    public AddAgreement(JSONObject object, String id_info) {
        super(7, "addagreement");
        this.fieldName = new String[]{
                "id", "id_info", "numadditagreem", "dateagreem",
                "entrydate", "sumsubcur", "sumsubrub"
        };

        type = BLOCK_TYPE.BLOCK_TYPE_WITH_LINKS_FROM;
        nested_tables = new String[] {"plantranssub"};

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
            return new PlanTransSub(obj, data.get(fieldName[0]));
        return null;
    }
}
