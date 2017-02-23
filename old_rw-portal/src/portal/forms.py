from flask_security.forms import RegisterForm

from wtforms import StringField
from wtforms.validators import DataRequired


class ExtendedRegisterForm(RegisterForm):
    first_name = StringField(label='First Name', validators=[DataRequired()], _name='first_name')
    last_name = StringField(label='Last Name', validators=[DataRequired()], _name='last_name')
    tenant_name = StringField(label='Company Name', validators=[DataRequired()], _name='tenant_name')
