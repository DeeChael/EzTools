package org.eztools.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eztools.EzT;
import org.eztools.api.storage.Storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MysqlStorage implements Storage {

    private final HikariConfig hikariConfig = new HikariConfig();

    private final HikariDataSource hikariDataSource;

    private final String host;
    private final int port;
    private final String database;
    private final String table;
    private final String username;
    private final String password;

    private Connection connection;
    private Statement statement;

    private final Map<String,String> cache = new HashMap<>();

    /*
     * I suggest that add a sub user to mysql, and your database account won't be got by another plugins
     */
    public MysqlStorage(String host, int port, String databaseName, String tableName, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = databaseName;
        this.table = tableName;
        this.username = username;
        this.password = password;
        this.hikariConfig.setMaximumPoolSize(100);
        this.hikariConfig.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.hikariConfig.addDataSourceProperty("serverName", this.host);
        this.hikariConfig.addDataSourceProperty("port", this.port);
        this.hikariConfig.addDataSourceProperty("databaseName", this.database);
        this.hikariConfig.addDataSourceProperty("user", this.username);
        this.hikariConfig.addDataSourceProperty("password", this.password);
        this.hikariDataSource = new HikariDataSource(this.hikariConfig);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = this.hikariDataSource.getConnection();
            this.statement = this.connection.createStatement();
            String createTable = "CREATE TABLE If Not Exists `{database}`.`{table}` ( `Name` TEXT , `Context` TEXT ) ENGINE = InnoDB;".replace("{database}", this.database).replace("{table}", this.table);
            this.statement.executeUpdate(createTable);
            String selectFrom = "SELECT * FROM `{database}`.`{table}`;".replace("{database}", this.database).replace("{table}", this.table);
            ResultSet resultSet = this.statement.executeQuery(selectFrom);
            while (resultSet.next()) {
                cache.put(resultSet.getString("Name"), resultSet.getString("Context"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            EzT.getInstance().getLogger().severe("Failed to connect to mysql database");
        }
    }

    public void close() throws SQLException {
        this.connection.close();
        this.statement.close();
        this.hikariDataSource.close();
        this.connection = null;
        this.statement = null;
    }

    @Override
    public boolean has(String key) {
        return cache.containsKey(key);
    }

    @Override
    public String remove(String key) {
        if (this.cache.containsKey(key)) {
            String value = this.cache.remove(key);
            String deleteFrom = "DELETE FROM `{database}`.`{table}` WHERE Name='{name}';".replace("{database}", this.database).replace("{table}", this.table).replace("{name}", key);
            try {
                this.statement.executeUpdate(deleteFrom);
            } catch (SQLException ignored) {
                EzT.getInstance().getLogger().severe("Failed to execute mysql command");
            }
            return value;
        }
        return null;
    }

    @Override
    public void removeAll() {
        this.cache.clear();
        String dropTable = "DROP TABLE `{database}`.`{table}`;".replace("{database}", this.database).replace("{table}", this.table);
        String createTable = "CREATE TABLE If Not Exists `{database}`.`{table}` ( `Name` TEXT , `Context` TEXT ) ENGINE = InnoDB;".replace("{database}", this.database).replace("{table}", this.table);
        try {
            this.statement.executeUpdate(dropTable);
            this.statement.executeUpdate(createTable);
        } catch (SQLException ignored) {
            EzT.getInstance().getLogger().severe("Failed to clear mysql");
        }
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public void set(String key, String value) {
        if (this.has(key)) {
            this.remove(key);
        }
        String insert = "INSERT INTO `{database}`.`{table}` (`Name`, `Context`) VALUES ('{name}', '{context}');".replace("{database}", this.database).replace("{table}", this.table).replace("{name}", key).replace("{context}", value);
        try {
            this.statement.executeUpdate(insert);
        } catch (SQLException ignored) {
            EzT.getInstance().getLogger().severe("Failed to add new item to mysql");
        }
        this.cache.put(key, value);
    }

    @Override
    public List<String> keys() {
        return new ArrayList<>(this.cache.keySet());
    }

    @Override
    public List<String> values() {
        return new ArrayList<>(this.values());
    }

}
