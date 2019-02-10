/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package Web;

import Configure.ProgramArgs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebManager {
    /**
     * Чтение содержимого web страницы
     *
     * @param url - сссылка на web страницу
     * @return - при успешном завершении возвращается
     * содержимое запрашиваемой страницы, в противном
     * случае - null.
     */
    public String getContentByURL(final String url) {
        StringBuilder str = null;
        try {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream(), "UTF-8"));

            str = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                str.append(inputLine);
            in.close();
        } catch (IOException e) {
            System.out.println("Не удалось прочитать содержимое страницы " + url);
            System.out.println("Ошибка: " + e.getMessage());
            return null;
        }
        return str.toString();
    }

    /**
     * Получение ссылки на страницу по заданному смещению
     *
     * @param args   - структура с аргументами из ini-файла
     * @param offset - смещение, относительно которого необходимо
     *               сформировать ссылку
     * @return - адрес запрашиваемой ссылки
     */
    public String getUrlByOffset(final ProgramArgs args, final int offset) {
        final String addition = String.format("pageSize=%d&offset=%d", args.pageSize, offset);
        return args.url + addition;
    }

    /**
     * Чтение общего количества записей на странице
     * с открытыми данными
     *
     * @param args - структура с аргументами из ini-файла
     * @return - общее количество записей
     */
    public int getTotalRecords(final ProgramArgs args) {
        String jsonText = getContentByURL(args.url + "pageSize=1");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonText);
        } catch (ParseException e) {
            System.out.println("Ошибка в формате JSON данных " + e.getMessage());
            return 0;
        }
        return Integer.parseInt(jsonObject.get("recordCount").toString());
    }
}
