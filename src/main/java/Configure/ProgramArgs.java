/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package Configure;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

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
    final public String[] blocks;
    final public String[] tables;
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

        /* FIXME: @indicatorvalue удален из открытых данных, но не удален из паспорта данных.
                  Как только в паспорте будет удалено упоминание этой структуры, окончательно
                  удалить из кода */
        blocks = new String[]{
                "info", "grbs", "documents", "changes", "receiver", "payments", "plans", "faip",
                "plansSubject", "faipSubject", "marks", "npa", "bo", "construct",
                "subjectNpa", "infosub", "infocost", /*"indicatorvalue",*/ "infoind", "mba",
                "addagreement"
        };

        tables = new String[] {
                "grbs", "documents", "changes", "payments",  "plans", "faip",
                "FaipSubject", "Facts", "PlansSubject", "PlanTransSub", "AddAgreement",
                "receiver", "LocalAddress", "ForeignAddress", "marks", "npa", "bo", "construct",
                "subjectNpa", "infosub", "infocost",/* "indicatorvalue",*/ "infoind", "mba",
                "info"
        };
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
            System.out.println(e.getMessage());
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
}
