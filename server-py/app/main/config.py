"""
@author Jacob Xie
@time 9/3/2020
"""

from enum import Enum
from os import getenv, path
from pathlib import Path
from dotenv import load_dotenv

base_dir = Path(__file__).parent
resources_dir = Path(__file__).parents[3]

config_name = "resources/secret.env"
config_template_name = "resources/secret.template.env"


def read_config():
    try:
        config_path = path.join(resources_dir, config_name)
        load_dotenv(dotenv_path=config_path)
    except FileNotFoundError:
        config_path = path.join(resources_dir, config_template_name)
        load_dotenv(dotenv_path=config_path)
    except Exception as e:
        err = (
            f"Please check if {config_name} or {config_template_name} exists! \n\n {e}"
        )
        raise Exception(err)


# read config
read_config()


class Config(object):
    SECRET_KEY = getenv("SECRET_KEY", "secret_key")
    DEBUG = False
    conn = {
        "type": getenv("GALLERY_TYPE"),
        "host": getenv("GALLERY_HOST"),
        "port": getenv("GALLERY_PORT"),
        "database": getenv("GALLERY_DATABASE"),
        "username": getenv("GALLERY_USERNAME"),
        "password": getenv("GALLERY_PASSWORD"),
    }
    server_port = getenv("SERVER_PY_PORT")


class DevelopmentConfig(Config):
    DEBUG = True


class ProductionConfig(Config):
    DEBUG = False


class AppConfig(Enum):
    dev = DevelopmentConfig
    prod = ProductionConfig


key = Config.SECRET_KEY

if __name__ == "__main__":
    x1 = DevelopmentConfig().conn
    print(x1)
    x2 = ProductionConfig().conn
    print(x2)
