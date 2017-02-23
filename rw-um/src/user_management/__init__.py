import logging

from flask import Flask
from flask_mail import Mail
from flask_security import Security, MongoEngineUserDatastore, user_confirmed

# Create app
app = Flask(__name__)
app.config.from_pyfile('configs.py')

from user_management.forms import ExtendedRegisterForm
from user_management.models import db, User, Role, Tenant
from user_management.signal_callbacks import user_confirmed_callback

# Setup Flask-Mail
Mail(app)

# Setup MongoEngine
db.init_app(app)

# Setup Flask-Security
user_datastore = MongoEngineUserDatastore(db, User, Role)
security = Security(app, user_datastore, register_form=ExtendedRegisterForm, confirm_register_form=ExtendedRegisterForm)
user_confirmed.connect(user_confirmed_callback, app)

# Logging
handler = logging.FileHandler('rw-um.log')
handler.setLevel(logging.DEBUG)  # only log errors and above
app.logger.addHandler(handler)  # attach the handler to the app's logger

import user_management.views
