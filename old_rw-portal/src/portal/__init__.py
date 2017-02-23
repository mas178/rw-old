from flask import Flask
from flask_mail import Mail
from flask_security import Security, user_confirmed

# Create app
app = Flask(__name__)
app.config.from_pyfile('configs.py')

from portal import log, database, models, forms, signal_callbacks

# Setup Logging
log.init_app(app)

# Setup Flask-Mail
Mail(app)

# Setup Database
database.create_all()

# Setup Flask-Security
user_datastore = models.RWUserDatastore(database.Base, models.User, models.Role)
security = Security(app, user_datastore, confirm_register_form=forms.ExtendedRegisterForm)

user_confirmed.connect(signal_callbacks.user_confirmed_callback, app)

import portal.views
