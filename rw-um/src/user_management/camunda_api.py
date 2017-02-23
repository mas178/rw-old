from requests.models import Response
from requests.sessions import Session

from user_management import app
from user_management.models import User, Tenant

URL_API = 'http://192.168.11.178:8080/camunda/api'
URL_ADMIN_API = URL_API + '/admin'
URL_ENGINE_API = URL_API + '/engine/engine/default'
ADMIN_USER_DICT = {'username': 'jonny1', 'password': 'jonny1'}


def login(session: Session) -> Response:
    return session.post(url=URL_ADMIN_API + '/auth/user/default/login/admin', data=ADMIN_USER_DICT)


class UserApi:
    @staticmethod
    def create_user(session: Session, user: User) -> Response:
        app.logger.debug("UserApi#create_user: {} {}".format(user.last_name, user.first_name))
        user_dict = {
            'profile': {
                'id': user.email,
                'firstName': user.first_name,
                'lastName': user.last_name,
                'email': user.email
            },
            'credentials': {
                'password': '12345678'
            }
        }

        return session.post(url=URL_ENGINE_API + '/user/create', json=user_dict)

    @staticmethod
    def get_one(session: Session, user: User) -> Response:
        app.logger.debug("UserApi#get_one: {} {}".format(user.last_name, user.first_name))

        return session.get(url=URL_ENGINE_API + '/user/' + user.email + '/profile')

    @staticmethod
    def get_list(session: Session, **params) -> Response:
        url = URL_ENGINE_API + '/user'
        param_member_of_tenant = params.get('memberOfTenant')
        if param_member_of_tenant is not None:
            url = url + '?memberOfTenant=' + param_member_of_tenant

        app.logger.debug("UserApi#get_list: {}".format(url))

        return session.get(url=url)

    @staticmethod
    def get_count(session: Session) -> int:
        app.logger.debug('UserApi#get_count')

        return len(UserApi.get_list(session).json())

    @staticmethod
    def update(session: Session, user: User) -> Response:
        app.logger.debug("UserApi#update_user: {} {}".format(user.last_name, user.first_name))
        user_dict = {
            'id': user.email,
            'firstName': user.first_name,
            'lastName': user.last_name
        }

        return session.put(url=URL_ENGINE_API + '/user/' + user.email + '/profile', json=user_dict)

    @staticmethod
    def delete(session: Session, user: User) -> Response:
        app.logger.debug("UserApi#delete_user: {} {}".format(user.last_name, user.first_name))

        return session.delete(url=URL_ENGINE_API + '/user/' + user.email)


class TenantApi:
    @staticmethod
    def create(session: Session, tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#create: {}".format(tenant.name))
        # ToDo: organize the relations between id of UM, id of Camunda and name
        tenant_dict = {'id': tenant.name, 'name': tenant.name}

        return session.post(url=URL_ENGINE_API + '/tenant/create', json=tenant_dict)

    @staticmethod
    def get_one(session: Session, tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#get_one: {}".format(tenant.name))

        return session.get(url=URL_ENGINE_API + '/tenant/' + tenant.name)

    @staticmethod
    def get_list(session: Session, **params) -> Response:
        url = URL_ENGINE_API + '/tenant'
        param_user_member = params.get('userMember')
        if param_user_member is not None:
            url = url + '?userMember=' + param_user_member

        app.logger.debug("TenantApi#get_list: {}".format(url))

        return session.get(url=url)

    @staticmethod
    def get_count(session: Session) -> int:
        app.logger.debug('TenantApi#get_count')

        return len(TenantApi.get_list(session).json())

    @staticmethod
    def create_user_member(session: Session, user: User) -> Response:
        app.logger.debug("TenantApi#create_user_member: {} {} {}".format(user.last_name, user.first_name, user.tenant.name))

        return session.put(url="{base}/tenant/{tenant_id}/user-members/{user_id}".format(base=URL_ENGINE_API, tenant_id=user.tenant.name, user_id=user.email))

    @staticmethod
    def delete(session: Session, tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#delete: {}".format(tenant.name))
        # ToDo: organize the relations between id of UM, id of Camunda and name

        return session.delete(url=URL_ENGINE_API + '/tenant/' + tenant.name)
