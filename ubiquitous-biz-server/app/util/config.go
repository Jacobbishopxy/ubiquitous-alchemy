package util

import (
	"os"

	"github.com/joho/godotenv"
)

type Config struct {
	AppEnv     string
	ApiPort    string
	DbDriver   string
	DbHost     string
	DbPassword string
	DbUser     string
	DbName     string
	DbPort     string
}

func LoadConfig(path string) (*Config, error) {
	if err := godotenv.Load(path); err != nil {
		return nil, err
	}

	appEnv := os.Getenv("APP_ENV")
	apiPort := os.Getenv("API_PORT")
	dbDriver := os.Getenv("DB_DRIVER")
	host := os.Getenv("DB_HOST")
	password := os.Getenv("DB_PASSWORD")
	user := os.Getenv("DB_USER")
	dbName := os.Getenv("DB_NAME")
	port := os.Getenv("DB_PORT")

	return &Config{
		AppEnv:     appEnv,
		ApiPort:    apiPort,
		DbDriver:   dbDriver,
		DbHost:     host,
		DbPassword: password,
		DbUser:     user,
		DbName:     dbName,
		DbPort:     port,
	}, nil
}
