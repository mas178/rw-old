import logging
import os
from logging.handlers import RotatingFileHandler

from flask import Flask


def init_app(app: Flask) -> None:
    log_dir = '/var/log/rw-portal'

    if not os.path.exists(log_dir):
        return

    formatter = logging.Formatter('%(asctime)s %(levelname)s [%(pathname)s:%(lineno)d]: %(message)s')

    debug_log = os.path.join(log_dir, 'debug.log')
    debug_file_handler = RotatingFileHandler(debug_log, maxBytes=100000, backupCount=10)
    debug_file_handler.setLevel(logging.DEBUG)
    debug_file_handler.setFormatter(formatter)
    app.logger.addHandler(debug_file_handler)

    error_log = os.path.join(log_dir, 'error.log')
    error_file_handler = RotatingFileHandler(error_log, maxBytes=100000, backupCount=10)
    error_file_handler.setLevel(logging.ERROR)
    error_file_handler.setFormatter(formatter)
    app.logger.addHandler(error_file_handler)

    app.logger.setLevel(logging.DEBUG)
