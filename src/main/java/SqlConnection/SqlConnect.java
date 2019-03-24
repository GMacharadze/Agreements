/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

package SqlConnection;

import Configure.ProgramArgs;
import DataBlocks.AbstractBlock;

import java.sql.*;
import java.util.ArrayList;

public class SqlConnect {

    public Connection sql;

    private String getConnectionString(final ProgramArgs args) {
        return String.format(
                "jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s",
                args.address, args.dbName, args.userName, args.userPass
        );
    }

    public void connect(final ProgramArgs args) {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка при подключении драйверу jdbc " + e.getMessage());
        }

        sql = null;
        try {
            if (sql == null || sql.isClosed())
                sql = DriverManager.getConnection(getConnectionString(args));
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных " + e.getMessage());
            System.exit(1);
        }
    }

    public void disconnect() {
        try {
            if (!sql.isClosed())
                sql.close();
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть SQL-соедниение " + e.getMessage());
        }
    }

    public void deleteTable(String table) throws SQLException {
        PreparedStatement ps = sql.prepareStatement("delete from " + table);
        ps.execute();
    }

    private void fillPreparedStatement(PreparedStatement ps, AbstractBlock block, int start_id) throws SQLException {
        for (int i = start_id; i < block.fieldCount; ++i)
            ps.setString(i + 1, block.data.get(block.fieldName[i]));
        ps.addBatch();
    }

    private int insertBatch(ArrayList<AbstractBlock> blocks) throws SQLException {
        PreparedStatement ps = sql.prepareStatement(blocks.get(0).getQuery());

        for (AbstractBlock block : blocks)
            fillPreparedStatement(ps, block, 0);

        ps.executeBatch();
        ps.close();
        return 0;
    }

    private int insertBatch2(ArrayList<AbstractBlock> blocks, PreparedStatement ps, int id) throws SQLException {

        if (blocks.size() == 0)
            return 0;

        for (AbstractBlock block : blocks) {
            ps.setInt(1, id);
            fillPreparedStatement(ps, block, 1);
        }

        return 0;
    }

    private int getNextPrimaryKey(Connection sql, AbstractBlock block) {
        try {
            PreparedStatement ps = sql.prepareStatement(block.getLastIdQueary());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id") + 1;
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    private int ins(AbstractBlock block, Connection sql) throws SQLException {
        String[] generatedColumns = { "id" };
        PreparedStatement ps = sql.prepareStatement(block.getQuery(), generatedColumns);
        for (int i = 0; i < block.fieldCount; ++i)
            ps.setString(i + 1, block.data.get(block.fieldName[i]));
        ps.execute();

        ResultSet resultSet = ps.getGeneratedKeys();
        if (resultSet.next())
            return resultSet.getInt("id");
        else {
            System.out.println("Ошибка при вставке данных в таблицу " + block.table);
            System.exit(1);
            return 1;
        }
    }

    private int insertSignle(ArrayList<AbstractBlock> blocks) throws SQLException {
        PreparedStatement ps = sql.prepareStatement(blocks.get(0).getQuery());
        for (AbstractBlock block : blocks) {

            for (int i = 0; i < block.fieldCount; ++i)
                ps.setString(i + 1, block.data.get(block.fieldName[i]));

            for (int i = 0; i < block.nested_tables.length; ++i) {
                if (block.nested[i].isNull())
                    ps.setNull(block.fieldCount + i + 1, java.sql.Types.INTEGER);
                else
                    ps.setInt(block.fieldCount + i + 1, ins(block.nested[i], sql));
            }
            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();
        return 0;
    }

    private int insertSignle2(ArrayList<AbstractBlock> blocks) throws SQLException {
        PreparedStatement ps = sql.prepareStatement(blocks.get(0).getQuery());

        PreparedStatement[] pss = new PreparedStatement[blocks.get(0).nested_tables.length];

        int id = getNextPrimaryKey(sql, blocks.get(0));

        for (AbstractBlock block : blocks) {
            ps.setInt(1, id);
            for (int i = 1; i < block.fieldCount; ++i)
                ps.setString(i + 1, block.data.get(block.fieldName[i]));
            ps.addBatch();

            for (int i = 0; i < block.nested_tables.length; ++i) {
                ArrayList<AbstractBlock> tmp = block.nested_array.get(i);
                if (tmp.isEmpty())
                    continue;
                pss[i] = sql.prepareStatement(tmp.get(0).getQuery());
                insertBatch2(block.nested_array.get(i), pss[i], id++);
            }
        }

        ps.executeBatch();
        ps.close();

        for (int i = 0; i < blocks.get(0).nested_tables.length; ++i) {
            pss[i].executeBatch();
            pss[i].close();
        }

        return 0;
    }

    public void insertRecords(ArrayList<AbstractBlock> blocks) throws SQLException {
        if (blocks.isEmpty())
            return;

        switch(blocks.get(0).getBlockType()) {
            case BLOCK_TYPE_SIMPLE:
                insertBatch(blocks);
                break;
            case BLOCK_TYPE_WITH_LINKS_TO:
                insertSignle(blocks);
                break;
            case BLOCK_TYPE_WITH_LINKS_FROM:
                insertSignle2(blocks);
                break;
        }
        blocks.clear();
    }
}
