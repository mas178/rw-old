# --------------------------------------
# MongoDB Config
# --------------------------------------
MONGODB_DB = 'test1'
MONGODB_HOST = 'localhost'
MONGODB_PORT = 27017

# --------------------------------------
# Flask-Security Configurations (https://pythonhosted.org/Flask-Security/configuration.html)
# --------------------------------------
# Core
SECURITY_PASSWORD_HASH = 'sha512_crypt'
SECURITY_PASSWORD_SALT = "This isn't common."

# Feature Flags
SECURITY_CONFIRMABLE = True
SECURITY_REGISTERABLE = True
SECURITY_RECOVERABLE = False
SECURITY_TRACKABLE = True
SECURITY_PASSWORDLESS = False
SECURITY_CHANGEABLE = True

# --------------------------------------
# Flask-Mail Configurations
# --------------------------------------
MAIL_SERVER = 'smtp.gmail.com'
MAIL_PORT = 465
MAIL_USE_TLS = False
MAIL_USE_SSL = True
MAIL_USERNAME = 'inaba@rational-works.jp'
MAIL_PASSWORD = 'ujafscqjudlqokrg'  # Application password (https://support.google.com/accounts/answer/185833)

# --------------------------------------
# Miscellaneous Configurations
# --------------------------------------
DEBUG = True
SECRET_KEY = b'\r/\xebD\xff\xa7\xc7\x9f\xdcj/\x84\xb4\x1e\x11\xd5\xa3\x03\xa2zl\xf9\xa5\xc6'  # for session encryption
