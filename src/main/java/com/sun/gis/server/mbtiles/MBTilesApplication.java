package com.sun.gis.server.mbtiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class MBTilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MBTilesApplication.class, args);
    }

}
