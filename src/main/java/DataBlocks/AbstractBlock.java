/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package DataBlocks;

import java.util.HashMap;

/**
 * Абстрактный класс, описывающий общие свойства
 * для всех классов данных.
 */
public abstract class AbstractBlock {
    /**
     * fieldCount - количество полей в реализуемом классе(
     * учитываются только те поля, которые будут загружаться
     * непосредственно в БД).
     * fieldName - массив с названиями всех полей реализуемого
     * класса.
     * table - имя таблицы, в которую будут загружаться поля
     * реализуемого класса.
     * data - хэш таблица с загружаемыми данными
     */
    public final int fieldCount;
    public String[] fieldName;
    public String table;
    public HashMap<String, String> data;

    public AbstractBlock(int fieldCount, String table) {
        this.fieldCount = fieldCount;
        this.table = table;
    }

    /**
     * Сформировать запрос добавления в таблицу экземпляра
     * реализуемого класса.
     *
     * @return - запрос добавления в таблицу.
     */
    public String getQuery() {
        String sqlQuery = "insert into " + table + " values (";
        for (int i = 0; i < fieldCount - 1; ++i)
            sqlQuery += "?,";
        sqlQuery += "?)";
        return sqlQuery;
    }

}
