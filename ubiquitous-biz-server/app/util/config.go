package util

import (
	"os"

	"github.com/joho/godotenv"
)

type Config struct {
	AppEnv     string
	ApiPrefix  string
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

	return &Config{
		AppEnv:     os.Getenv("APP_ENV"),
		ApiPrefix:  os.Getenv("APP_PREFIX"),
		ApiPort:    os.Getenv("API_PORT"),
		DbDriver:   os.Getenv("DB_DRIVER"),
		DbHost:     os.Getenv("DB_HOST"),
		DbPassword: os.Getenv("DB_PASSWORD"),
		DbUser:     os.Getenv("DB_USER"),
		DbName:     os.Getenv("DB_NAME"),
		DbPort:     os.Getenv("DB_PORT"),
	}, nil
}
