import os

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

# URLs and Views
SECURITY_POST_REGISTER_VIEW = '/after_register'

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
SQLALCHEMY_TRACK_MODIFICATIONS = True
is_test_env = 'RW_DB_PORT_3306_TCP_ADDR' not in os.environ or 'RW_CAMUNDA_PORT_8080_TCP_ADDR' not in os.environ
SQLALCHEMY_DATABASE_URI = "{driver_name}://{user}:{password}@{ip}/{db_name}?charset=utf8".format(
    driver_name='mysql+mysqldb',
    user='test' if is_test_env else 'portal',
    password='test' if is_test_env else 'portal',
    ip='0.0.0.0' if is_test_env else os.environ['RW_DB_PORT_3306_TCP_ADDR'],
    db_name='portal')

CAMUNDA_API_DOMAIN = 'http://' \
                     + ('0.0.0.0' if is_test_env else os.environ['RW_CAMUNDA_PORT_8080_TCP_ADDR']) \
                     + ':' \
                     + ('8080' if is_test_env else os.environ['RW_CAMUNDA_PORT_8080_TCP_PORT'])
