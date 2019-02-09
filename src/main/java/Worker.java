/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
import Configure.ProgramArgs;
import DataBlocks.AbstractBlock;
import DataBlocks.Info;
import SqlConnection.SqlConnect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Worker {
    /**
     * Обрбаотка фрагмента данных с загрузкой в БД
     * @param jsonText - фрагмент данных в формате JSON
     * @param args - аргументы программы, считанные из ini-файла
     */
    public void work(final String jsonText, final ProgramArgs args)
    {
        JSONParser json = new JSONParser();
        JSONObject jsonObject;
        JSONArray arrayData;
        try {
            jsonObject =  (JSONObject) json.parse(jsonText);
            arrayData = (JSONArray) jsonObject.get("data");

        } catch(ParseException e) {
            System.out.println("Ошибка в формате JSON данных " + e.getMessage());
            return;
        }
        ArrayList<AbstractBlock> blockData = new ArrayList<>();
        SqlConnect sql = new SqlConnect();
        for (Object singleData: arrayData) {
            JSONObject data = (JSONObject) singleData;
            String id = data.get("id").toString();

            blockData.add(new Info((JSONObject) data.get("info"), id));
        }

        try {
            sql.multiplyQuery(blockData, args);
        } catch (SQLException e) {
            System.out.println("Не удалось добавить записи в таблицу " + "info" + " " + e.getMessage());
            return;
        }

        arrayData.clear();
    }
}
