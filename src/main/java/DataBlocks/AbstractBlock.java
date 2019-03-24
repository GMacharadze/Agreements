/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */
package DataBlocks;

import java.util.ArrayList;
import java.util.HashMap;

import static DataBlocks.AbstractBlock.BLOCK_TYPE.BLOCK_TYPE_SIMPLE;

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

    public String[] nested_tables;
    public AbstractBlock[] nested;
    public ArrayList<ArrayList<AbstractBlock>> nested_array;

    public enum BLOCK_TYPE {
        BLOCK_TYPE_SIMPLE,
        BLOCK_TYPE_WITH_LINKS_TO,
        BLOCK_TYPE_WITH_LINKS_FROM
    }

    protected BLOCK_TYPE type = BLOCK_TYPE_SIMPLE;

    protected AbstractBlock() {
        this.fieldCount = 0;
    }

    public AbstractBlock(int fieldCount, String table) {
        this.fieldCount = fieldCount;
        this.table = table;
    }

    public BLOCK_TYPE getBlockType() {
        return type;
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

    public String getLastIdQueary() {
        return String.format("select TOP(1) id from %s order by id desc", table);
    }

    public boolean isNull() {
        for (int i = 0; i < fieldCount; ++i)
            if (data.get(fieldName[i]).length() > 0)
                return false;
        return true;
    }

}
