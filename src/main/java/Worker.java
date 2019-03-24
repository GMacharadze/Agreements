/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;
import DataBlocks.*;
import SqlConnection.SqlConnect;
import Web.WebManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Worker {
    private boolean flag_complete = false;
    private LinkedList<String> buffers = new LinkedList<>();
    final private int capacity = 1;


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
            case "receiver":
                return new Receiver(data, id);
            case "payments":
                return new Payments(data, id);
            case "plans":
                return new Plans(data, id);
            case "faip":
                return new Faip(data, id);
            case "plansSubject":
                return new PlanSubject(data, id);
            case "faipSubject":
                return new FaipSubject(data, id);
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
            case "indicatorvalue":
                return new IndicatorValue(data, id);
            case "infoind":
                return new InfoInd(data, id);
            case "addagreement":
                return new AddAgreement(data, id);
            default:
                return null;
        }
    }

    public void clearAll(ProgramArgs args) {
        SqlConnect sql = new SqlConnect();
        sql.connect(args);
        for (int i = 0; i < args.tables.length; ++i)
            try {
                sql.deleteTable(args.tables[i]);
            } catch (SQLException e) {
                System.out.println("Не удалось удалить содержимое таблицы " + args.tables[i]);
                System.out.println(e.getMessage());
            }
        sql.disconnect();
    }

    public void produce(ProgramArgs args) throws InterruptedException
    {
        String jsonText; int i = 0;
        WebManager web = new WebManager();
        int total_records = web.getTotalRecords(args);
        int offset = 0;
        String url;
        while (offset < total_records) {
            url = web.getUrlByOffset(args, offset);
            while(buffers.size() == capacity)
                Thread.sleep(100);

            System.out.println("Загрузка пошла " + ++i + " ...");

            jsonText = web.getContentByURL(url);
            buffers.add(jsonText);

            System.out.println("Загрузка окончена " + i + " ...\n");

            offset += args.pageSize;
        }
        flag_complete = true;
    }

    public void consume(ProgramArgs args) throws InterruptedException, ParseException {
        int i = 0;
        while (true) {
            if (flag_complete && buffers.isEmpty())
                return;

            while (buffers.size() == 0)
                Thread.sleep(100);

            System.out.println("Обработка пошла " + ++i + " ...");

            String jsonText = buffers.removeFirst();

            work(jsonText, args);
            System.out.println("Обработка окончена " + i + " ...\n");
        }
    }

    public void work(final String jsonText, final ProgramArgs args) throws ParseException {
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

        ArrayList<AbstractBlock> blockData = new ArrayList<>();
        SqlConnect sql = new SqlConnect();
        sql.connect(args);

        for (Object singleData: arrayData) {
            JSONObject data = (JSONObject) singleData;
            String id = data.get("id").toString();

            blockData.add(new Info((JSONObject) data.get("info"), id));
        }

        try {
            sql.insertRecords(blockData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Object singleData: arrayData) {
            JSONObject data = (JSONObject) singleData;
            String id = data.get("id").toString();

            for (int i = 1; i < args.blocks.length; ++i) {
                String block = args.blocks[i];
                try {
                    JSONArray array = (JSONArray) data.get(block);
                    for (Object item : array)
                        blockData.add(objAlloc(block, (JSONObject) item, id));
                    array.clear();
                } catch (Exception e) {
                    blockData.add(objAlloc(block, (JSONObject) data.get(args.blocks[i]), id));
                }

                try {
                    sql.insertRecords(blockData);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        arrayData.clear();
        sql.disconnect();
    }
}
