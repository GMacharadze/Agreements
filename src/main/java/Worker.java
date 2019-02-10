/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;
import Configure.ProgramArgs.blockType;
import DataBlocks.*;
import SqlConnection.SqlConnect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Worker {

    private AbstractBlock objAlloc(String block, JSONObject data, String id) {
        switch (block) {
            case "info":
                return new Info(data, id);
            case "grbs":
                return new GRBS(data, id);
            case "documents":
                return new Documents(data, id);
            case "changes":
                return new Changes(data, id);
            case "payments":
                return new Payments(data, id);
            case "marks":
                return new Marks(data, id);
            case "npa":
                return new NPA(data, id);
            case "bo":
                return new BO(data, id);
            case "construct":
                return new Construct(data, id);
            case "subjectNpa":
                return new SubjectNPA(data, id);
            case "infosub":
                return new InfoSub(data, id);
            case "infocost":
                return new InfoCost(data, id);
            case "infoind":
                return new InfoInd(data, id);
            case "indicatorvalue":
                return new IndicatorValue(data, id);
            default:
                return null;
        }
    }

    private void block_parse(String block, blockType type, HashMap<String, ArrayList<AbstractBlock>> bd, JSONObject data) {
        String info_id = data.get("id").toString();
        ArrayList<AbstractBlock> tempList;
        switch (type) {

            case OTO:
                tempList = bd.get(block);
                tempList.add(objAlloc(block, (JSONObject) data.get(block), info_id));
                bd.put(block, tempList);
                return;
            case OTM:
                JSONArray docs = (JSONArray) data.get(block);
                for (Object doc : docs) {
                    tempList = bd.get(block);
                    tempList.add(objAlloc(block, (JSONObject) doc, info_id));
                    bd.put(block, tempList);
                }
                return;
            case MTM:
                /**
                 * TODO:
                 */
                return;
            default:
                return;
        }

    }

    /**
     * Обрбаотка фрагмента данных с загрузкой в БД
     *
     * @param jsonText - фрагмент данных в формате JSON
     * @param args     - аргументы программы, считанные из ini-файла
     */
    public void work(final String jsonText, final ProgramArgs args) {
        JSONParser json = new JSONParser();
        JSONObject jsonObject;
        JSONArray arrayData;
        try {
            jsonObject = (JSONObject) json.parse(jsonText);
            arrayData = (JSONArray) jsonObject.get("data");

        } catch (ParseException e) {
            System.out.println("Ошибка в формате JSON данных " + e.getMessage());
            return;
        }

        //список записей для каждого блока
        HashMap<String, ArrayList<AbstractBlock>> bd = new HashMap<>();
        for (HashMap.Entry<String, ProgramArgs.blockType> entry : args.blocks.entrySet())
            bd.put(entry.getKey(), new ArrayList<>());

        SqlConnect sql = new SqlConnect();
        for (Object singleData : arrayData) {
            JSONObject data = (JSONObject) singleData;

            for (HashMap.Entry<String, blockType> entry : args.blocks.entrySet())
                block_parse(entry.getKey(), entry.getValue(), bd, data);
        }

        for (HashMap.Entry<String, ProgramArgs.blockType> entry : args.blocks.entrySet()) {
            try {
                sql.multiplyQuery(bd.get(entry.getKey()), args);
            } catch (SQLException e) {
                System.out.println("Не удалось добавить записи в таблицу " + entry.getKey() + " " + e.getMessage());
                return;
            }
        }

        bd.clear();
        arrayData.clear();
    }
}
