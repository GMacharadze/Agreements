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

    /**
     * Информация о странице с openData:
     * url - базовый адрес страницы с данными в формате JSON
     * pageSize - количество записей на одной загружаемой страницы.
     * Увеличение этого параметра приводит к увеличению скорости
     * работы программы и к увеличению потребляемой
     * оперативной памяти. Максимальное значение - 1000.
     */
    final public String url = "http://budget.gov.ru/epbs/registry/grants/data?";
    final public int pageSize = 500;

    /**
     * iniFileName - имя ini-файла
     */
    final private String iniFileName = "config.ini";

    /**
     * Чтение аргументов из конфигурационного файла iniFileName.
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
}
