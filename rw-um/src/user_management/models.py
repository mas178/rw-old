from flask import request
from flask_mongoengine import MongoEngine
from flask_security import RoleMixin, UserMixin

db = MongoEngine()


class Role(db.Document, RoleMixin):
    name = db.StringField(max_length=80, unique=True)
    description = db.StringField(max_length=255)


class Tenant(db.Document):
    name = db.StringField(max_length=63, required=True)
    description = db.StringField(max_length=255)


class User(db.Document, UserMixin):
    email = db.StringField(max_length=255, unique=True)
    password = db.StringField(max_length=255)
    active = db.BooleanField(default=True)
    confirmed_at = db.DateTimeField()
    roles = db.ListField(db.ReferenceField(Role), default=[])

    # Extended Field
    first_name = db.StringField(max_length=255)
    last_name = db.StringField(max_length=255)
    tenant = db.ReferenceField('Tenant')

    # User Tracking Fields
    last_login_at = db.DateTimeField()
    current_login_at = db.DateTimeField()
    last_login_ip = db.StringField()
    current_login_ip = db.StringField()
    login_count = db.IntField()

    def save(self, *args, **kwargs):
        tenant_name = request.form.get('tenant_name', None)

        if tenant_name is not None:
            tenant = Tenant(name=tenant_name)
            tenant.save()
            self.tenant = tenant

        return super(User, self).save(*args, **kwargs)
