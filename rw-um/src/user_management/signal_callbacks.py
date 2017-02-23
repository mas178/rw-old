import requests
from flask import Flask

from user_management.camunda_api import login, UserApi, TenantApi
from user_management.models import User


def user_confirmed_callback(app: Flask, user: User):
    session = requests.session()
    login(session)
    UserApi.create_user(session, user)
    TenantApi.create(session, user.tenant)
    TenantApi.create_user_member(session, user)
    session.close()
