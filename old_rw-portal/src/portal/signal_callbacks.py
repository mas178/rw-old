from flask import Flask

from portal.camunda_api import UserApi, TenantApi
from portal.models import User


def user_confirmed_callback(app: Flask, user: User):
    app.logger.debug('Start user_confirmed_callback')
    app.logger.debug(user)

    UserApi.create(user)
    TenantApi.create(user.tenant)
    TenantApi.create_user_member(user)

    app.logger.debug('End user_confirmed_callback')
