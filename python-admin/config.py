import os
from dotenv import load_dotenv

load_dotenv()

class Config:
    SECRET_KEY = os.getenv('SECRET_KEY', 'dev-secret-key-default')
    SQLALCHEMY_DATABASE_URI = os.getenv('DATABASE_URL', 'sqlite:///muzej.db')
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    FLASK_ENV = os.getenv('FLASK_ENV', 'development')
