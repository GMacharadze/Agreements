/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package Configure;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ProgramArgs {
    /**
     * url - базовый адрес страницы с данными в формате JSON
     * pageSize - количество записей на одной загружаемой страницы.
     * Увеличение этого параметра приводит к увеличению скорости
     * работы программы и к увеличению потребляемой
     * оперативной памяти. Максимальное значение - 1000.
     * blockSingleName - названия блоков данных (1<->1)
     * blockMultiplyName - названия блоков данных (1<->много)
     */
    final public String url = "http://budget.gov.ru/epbs/registry/grants/data?";
    final public int pageSize = 1000;
    final public LinkedHashMap<String, blockType> blocks;
    /**
     * iniFileName - имя ini-файла
     */
    final private String iniFileName = "config.ini";

    /**
     * Информация о странице с openData:
     */
    /**
     * Информация о подключаемой БД:
     * address - адрес сервера. Формат: serverName[\instanceName][:portNumber]
     * dbName - название БД
     * userName - имя пользователя, от которого осуществляется подключение к БД
     * password - используемый пароль при подключении
     */
    public String address;
    public String dbName;
    public String userName;
    public String userPass;

    public ProgramArgs() {
        blocks = new LinkedHashMap<>();
        blocks.put("info", blockType.OTO);
        blocks.put("grbs", blockType.OTO);
        blocks.put("documents", blockType.OTM);
        blocks.put("changes", blockType.OTM);
        blocks.put("payments", blockType.OTM);
        blocks.put("marks", blockType.OTM);
        blocks.put("npa", blockType.OTM);
        blocks.put("bo", blockType.OTM);
        blocks.put("construct", blockType.OTM);
        blocks.put("subjectNpa", blockType.OTM);
        blocks.put("infoind", blockType.OTM);
        blocks.put("infocost", blockType.OTM);
        blocks.put("infosub", blockType.OTM);
        blocks.put("infoind", blockType.OTM);
        blocks.put("indicatorvalue", blockType.OTM);
    }

    /**
     * Чтение аргументов из конфигурационного файла iniFileName.
     *
     * @return - 0 в случае успеха. В противном случае, 1.
     */
    public int parseArgs() {
        Ini ini;
        try {
            ini = new Ini(new File(iniFileName));
        } catch (IOException e) {
            System.out.println("Ошибка при открытии файла " + iniFileName);
            return 1;
        }
        address = ini.get("database", "address");
        dbName = ini.get("database", "databaseName");
        userName = ini.get("database", "userName");
        userPass = ini.get("database", "userPass");

        if (address == null || dbName == null || userName == null || userPass == null) {
            System.out.println("Неверная структура ini-файла");
            return 1;
        }
        return 0;
    }

    /**
     * Тип блока данных:
     * OTO - каждой записи соответстует единственный блок;
     * OTM - каждой записи соответствует несколько блоков;
     * MTM - каждой записи соответствует несколько блоков и
     * в каждом блоке возможно внутренняя иерархия блоков
     */
    public enum blockType {
        OTO,
        OTM,
        MTM
    }
}
