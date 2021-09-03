package main

import (
	"flag"
	"log"
	"os"

	flexibleconfig "github.com/devopsfaith/krakend-flexibleconfig"
	"github.com/joho/godotenv"
	"github.com/luraproject/lura/config"
	"github.com/luraproject/lura/logging"
	"github.com/luraproject/lura/proxy"
	"github.com/luraproject/lura/router/gin"
)

const (
	fcPartials  = "FC_PARTIALS"
	fcTemplates = "FC_TEMPLATES"
	fcSettings  = "FC_SETTINGS"
	fcPath      = "FC_OUT"
	fcEnable    = "FC_ENABLE"
)

// lura config (any API mutation goes here)
const configFile = "./config/lura.json"

// load env file
func loadEnv(path string) {
	err := godotenv.Load(path)

	if err != nil {
		log.Print(err)
		log.Fatalf("Error loading " + path + " file\n")
	}
}

func main() {
	// get arguments from command line
	envFile := flag.String("c", "lura.env", "Path to the configuration filename")
	port := flag.Int("p", 0, "Port of the service")
	logLevel := flag.String("l", "ERROR", "Logging level")
	debug := flag.Bool("d", false, "Enable the debug")
	flag.Parse()

	// lura flexible config
	loadEnv(*envFile)
	var cfg config.Parser
	cfg = config.NewParser()
	if os.Getenv(fcEnable) != "" {
		cfg = flexibleconfig.NewTemplateParser(flexibleconfig.Config{
			Parser:    cfg,
			Partials:  os.Getenv(fcPartials),
			Settings:  os.Getenv(fcSettings),
			Path:      os.Getenv(fcPath),
			Templates: os.Getenv(fcTemplates),
		})
	}

	// lura service config
	serviceConfig, err := cfg.Parse(configFile)
	if err != nil {
		log.Fatal("ERROR", err.Error())
	}
	serviceConfig.Debug = serviceConfig.Debug || *debug
	if *port != 0 {
		serviceConfig.Port = *port
	}

	logger, _ := logging.NewLogger(*logLevel, os.Stdout, "[LURA]")

	routerFactory := gin.DefaultFactory(proxy.DefaultFactory(logger), logger)

	// start API gateway server
	routerFactory.New().Run(serviceConfig)
}
