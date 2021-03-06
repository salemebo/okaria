package org.ariia.web;

import org.ariia.args.Argument;
import org.ariia.args.TerminalArgument;
import org.ariia.cli.AriiaCli;
import org.ariia.cli.LogCli;
import org.ariia.config.Properties;
import org.ariia.core.api.client.Clients;
import org.ariia.core.api.client.SegmentClient;
import org.ariia.core.api.request.ClientRequest;
import org.ariia.logging.Log;
import org.ariia.mvc.WebServer;
import org.ariia.mvc.WebServer.ResourceType;
import org.ariia.mvc.router.Routes;
import org.ariia.mvc.sse.EventBroadcast;
import org.ariia.web.app.WebDownloadService;
import org.ariia.web.app.WebLoggerPrinter;
import org.ariia.web.controller.ItemController;
import org.ariia.web.controller.LogLevelController;
import org.ariia.web.controller.SettingController;
import org.ariia.web.services.ItemService;
import org.ariia.web.services.SettingService;
import org.terminal.console.log.Level;
import org.terminal.console.log.api.Printer;
import org.terminal.console.log.impl.PrinterImpl;
import org.terminal.strings.AnsiStringBuilder;

import java.io.IOException;

public class WebService {

    public static boolean isRunningFromJar() {
        return WebService.class.getResource("WebService.class").getProtocol().equalsIgnoreCase("jar");
    }

    public static void start(Argument arguments, ClientRequest clientRequest) throws IOException {

        // setup logging service
        EventBroadcast mainBroadcast = new EventBroadcast();
        Printer printer = new PrinterImpl(System.out);
        WebLoggerPrinter loggingPrinter = new WebLoggerPrinter(mainBroadcast, printer);
        LogCli.initLogServicesNoStart(arguments, loggingPrinter, Level.info);
        Properties properties = new Properties(arguments);

        // setup web server
        int port = arguments.isServerPort() ? arguments.getServerPort() : 8080;
        String resourceLocation = arguments.isServerResourceLocation() ? arguments.getServerResourceLocation()
                : "/static/angular";
        WebServer.ResourceType type = arguments.isServerResourceLocation() ? WebServer.ResourceType.FILE :
//        			isRunningFromJar() ?
//        					WebServer.ResourceType.IN_MEMORY :
                WebServer.ResourceType.STREAM;


        Routes homeRoutes = new Routes("home");
        Routes dashboardRoutes = new Routes("dashboard");
        Routes downloadRoutes = new Routes("download", "**");
        Routes networkRoutes = new Routes("network", "monitor", "chart");
        Routes configRoutes = new Routes("config");
        Routes loggerRoutes = new Routes("logger");

        Routes rootRoutes = new Routes(
        	"/",
                homeRoutes,
                dashboardRoutes,
                downloadRoutes,
                networkRoutes,
                configRoutes,
                loggerRoutes
        );

        WebServer server = new WebServer(port, resourceLocation, type, rootRoutes);

        // setup download manager service
        SegmentClient client = Clients.segmentClient(properties, clientRequest);
        WebDownloadService downloadService = new WebDownloadService(mainBroadcast);

        AriiaCli cli = new AriiaCli(downloadService, client);

        SettingController settingController = new SettingController(new SettingService(downloadService, properties));
        server.createControllerContext(settingController);

        ItemController itemController = new ItemController(new ItemService(downloadService));
        server.createControllerContext(itemController);
        LogLevelController logLevelController = new LogLevelController();
        server.createControllerContext(logLevelController);

        server.createServerSideEventContext("/backbone-broadcast", mainBroadcast);

        cli.lunchAsWebApp(arguments, properties);
        server.start();
        LogCli.startLogService();
        AnsiStringBuilder log = new AnsiStringBuilder();
        log.append("start local web server: ");
        log.blueLite().blink();
        log.build();
        log.append("http://127.0.0.1:" + port + "/");
        if (type == ResourceType.FILE) {
            log.defaultColor();
            log.build();
            log.append("\nStreaming Directory: ");
            log.redLite();
            log.append(resourceLocation);
        }

        Log.log(WebService.class, "Running Web Server", log.toString());


    }

}
