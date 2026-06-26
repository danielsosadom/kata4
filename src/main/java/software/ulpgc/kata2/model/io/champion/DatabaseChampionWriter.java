package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Types.NVARCHAR;
import static java.sql.Types.REAL;

public class DatabaseChampionWriter implements ChampionWriter, AutoCloseable{
    private final Connection conn;
    private final PreparedStatement prepInsert;

    private DatabaseChampionWriter(String path) throws SQLException {
        this(DriverManager.getConnection(path));
    }

    public static DatabaseChampionWriter open(File file) throws IOException {
        try {
            return new DatabaseChampionWriter("jdbc:sqlite:" + file.getAbsolutePath());
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
    private DatabaseChampionWriter(Connection conn) throws SQLException {
        this.conn = conn;
        stopAutocommit();
        createTables();
        prepInsert = conn.prepareStatement(insertChampionStatement);
    }

    private static final String insertChampionStatement = """
            INSERT OR REPLACE INTO champions(name, type, role, tier, win_rate, pick_rate, kda)
            VALUES(?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String createTableStatement = """
            CREATE TABLE IF NOT EXISTS champions(
            name TEXT PRIMARY KEY,
            type TEXT NOT NULL,
            role TEXT NOT NULL,
            tier TEXT NOT NULL,
            win_rate REAL NOT NULL,
            pick_rate REAL NOT NULL,
            kda REAL NOT NULL
            );
            """;
    private void createTables() throws SQLException {
        conn.createStatement().executeUpdate("DROP TABLE IF EXISTS champions");
        conn.createStatement().execute(createTableStatement);
    }

    private void stopAutocommit() throws SQLException {
        conn.setAutoCommit(false);
    }

    public record Parameter(
            int index,
            Object value,
            int type
    ){}
    @Override
    public void write(Champion champion) throws IOException {
        try {
            statementFor(champion).execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private PreparedStatement statementFor(Champion champion) throws SQLException {
        prepInsert.clearParameters();
        paramList(champion).forEach(this::define);
        return prepInsert;
    }

    private void define(Parameter parameter) {
        try {
            prepInsert.setObject(parameter.index, parameter.value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Parameter> paramList(Champion champion) {
        return List.of(
                new Parameter(1, champion.getName(), NVARCHAR),
                new Parameter(2, champion.getClassType(), NVARCHAR),
                new Parameter(3, champion.getRole().name(), NVARCHAR),
                new Parameter(4, champion.getTier().name(), NVARCHAR),
                new Parameter(5, champion.getWinRate(), REAL),
                new Parameter(6, champion.getPickRate(), REAL),
                new Parameter(7, champion.getKda(), REAL)
        );
    }

    @Override
    public void close() throws Exception {
        conn.commit();
        conn.close();
    }
}
