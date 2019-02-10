/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

package SqlConnection;

import Configure.ProgramArgs;
import DataBlocks.AbstractBlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlConnect {

    Connection sql;

    private String getConnectionString(final ProgramArgs args) {
        return String.format(
                "jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s",
                args.address, args.dbName, args.userName, args.userPass
        );
    }

    public int connect(final ProgramArgs args) {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка при подключении драйверу jdbc " + e.getMessage());
            return 1;
        }

        try {
            if (sql == null || sql.isClosed())
                sql = DriverManager.getConnection(getConnectionString(args));
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных " + e.getMessage());
            return 1;
        }
        return 0;
    }

    public void disconnect() {
        try {
            if (!sql.isClosed())
                sql.close();
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть SQL-соедниение " + e.getMessage());
        }
    }

    public int multiplyQuery(ArrayList<AbstractBlock> blocks, ProgramArgs args) throws SQLException {
        if (connect(args) != 0)
            return 1;
        if (blocks.isEmpty())
            return 0;

        PreparedStatement ps = sql.prepareStatement(blocks.get(0).getQuery());
        for (AbstractBlock block : blocks) {
            for (int i = 0; i < block.fieldCount; ++i)
                ps.setString(i + 1, block.data.get(block.fieldName[i]));
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        blocks.clear();
        disconnect();
        return 0;
    }
}
