package com.dpd.utils;

import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.io.fs.FileUtils;

import java.nio.file.Path;
import java.util.Properties;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

public class NeoUtil {

    private static GraphDatabaseService service;
    private static void init() {
        try {
            Properties properties = FileUtil.getProperties("neo4j.properties");
            Path databaseDirectory = Path.of(properties.getProperty("databaseDirectory"));
            FileUtils.deleteDirectory(databaseDirectory);
            DatabaseManagementServiceBuilder db_server_builder = new DatabaseManagementServiceBuilder(databaseDirectory);
            if (Boolean.parseBoolean(properties.getProperty("BoltConnector.enabled"))) {
                db_server_builder
                        .setConfig(BoltConnector.enabled, true)
                        .setConfig(BoltConnector.listen_address,
                                new SocketAddress(properties.getProperty("bolt_host_name"),
                                        Integer.parseInt(properties.getProperty("bolt_port"))));
            }
            DatabaseManagementService managementService = db_server_builder.build();
            service = managementService.database(DEFAULT_DATABASE_NAME);
            Runtime.getRuntime().addShutdownHook(new Thread(managementService::shutdown));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GraphDatabaseService getService() {
        if (service == null) {
            init();
        }
        return service;
    }
}
