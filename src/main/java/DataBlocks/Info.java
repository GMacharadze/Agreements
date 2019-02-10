/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package DataBlocks;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Класс, определяющий структуру блока info
 * загружаемых данных. Класс реализует абстрактный
 * класс AbstractBlock. Кроме того, класс определяет
 * структуру таблицы в БД, в которую будет осуществляться
 * запись данных экземпляров этого класса.
 */
public class Info extends AbstractBlock {

    public Info(JSONObject info, String id) {
        super(27, "info");
        this.fieldName = new String[]{
                "id", "name", "regNum", "startDate", "endDate", "sum",
                "currencySum", "currencyName", "currencyCode", "code",
                "numAgreem", "dateAgreem", "numberNpa", "dateReg",
                "nameNpa", "rate", "dateUpdate", "sumMba", "sumMbamo",
                "regnumRgz", "mfCode", "mfName", "npaKind", "sumSubFzFb",
                "outerSystem", "internaldocnum", "loaddate"
        };
        data = new HashMap<>();
        data.put(fieldName[0], id);
        for (int i = 1; i < fieldCount; ++i)
            data.put(fieldName[i], info.get(fieldName[i]).toString());
    }
}
