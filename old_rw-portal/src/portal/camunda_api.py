import requests
from requests.models import Response

from portal import app
from portal.configs import CAMUNDA_API_DOMAIN
from portal.models import User, Tenant

URL_ENGINE_API = CAMUNDA_API_DOMAIN + '/engine-rest'
ADMIN_USER_DICT = {'username': 'demo', 'password': 'demo'}


class UserApi:
    @staticmethod
    def create(user: User) -> Response:
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

        return requests.post(url=URL_ENGINE_API + '/user/create', json=user_dict)

    @staticmethod
    def get_one(user: User) -> Response:
        app.logger.debug("UserApi#get_one: {} {}".format(user.last_name, user.first_name))

        return requests.get(url=URL_ENGINE_API + '/user/' + user.email + '/profile')

    @staticmethod
    def get_list(**params) -> Response:
        url = URL_ENGINE_API + '/user'
        param_member_of_tenant = params.get('memberOfTenant')
        if param_member_of_tenant is not None:
            url = url + '?memberOfTenant=' + param_member_of_tenant

        app.logger.debug("UserApi#get_list: {}".format(url))

        return requests.get(url=url)

    @staticmethod
    def get_count() -> int:
        app.logger.debug('UserApi#get_count')

        return len(UserApi.get_list().json())

    @staticmethod
    def update(user: User) -> Response:
        app.logger.debug("UserApi#update_user: {} {}".format(user.last_name, user.first_name))
        user_dict = {
            'id': user.email,
            'firstName': user.first_name,
            'lastName': user.last_name
        }

        return requests.put(url=URL_ENGINE_API + '/user/' + user.email + '/profile', json=user_dict)

    @staticmethod
    def delete(user: User) -> Response:
        app.logger.debug("UserApi#delete_user: {} {}".format(user.last_name, user.first_name))

        return requests.delete(url=URL_ENGINE_API + '/user/' + user.email)


class TenantApi:
    @staticmethod
    def create(tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#create: {}".format(tenant.name))
        # ToDo: organize the relations between id of UM, id of Camunda and name
        tenant_dict = {'id': tenant.name, 'name': tenant.name}

        return requests.post(url=URL_ENGINE_API + '/tenant/create', json=tenant_dict)

    @staticmethod
    def get_one(tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#get_one: {}".format(tenant.name))

        return requests.get(url=URL_ENGINE_API + '/tenant/' + tenant.name)

    @staticmethod
    def get_list(**params) -> Response:
        url = URL_ENGINE_API + '/tenant'
        param_user_member = params.get('userMember')
        if param_user_member is not None:
            url = url + '?userMember=' + param_user_member

        app.logger.debug("TenantApi#get_list: {}".format(url))

        return requests.get(url=url)

    @staticmethod
    def get_count() -> int:
        app.logger.debug('TenantApi#get_count')

        return len(TenantApi.get_list().json())

    @staticmethod
    def create_user_member(user: User) -> Response:
        app.logger.debug("TenantApi#create_user_member: {} {} {}".format(user.last_name, user.first_name, user.tenant.name))

        return requests.put(url="{base}/tenant/{tenant_id}/user-members/{user_id}".format(
            base=URL_ENGINE_API, tenant_id=user.tenant.name, user_id=user.email))

    @staticmethod
    def delete(tenant: Tenant) -> Response:
        app.logger.debug("TenantApi#delete: {}".format(tenant.name))
        # ToDo: organize the relations between id of UM, id of Camunda and name

        return requests.delete(url=URL_ENGINE_API + '/tenant/' + tenant.name)


class ProcessApi:
    @staticmethod
    def get_list(**params) -> Response:
        url = '{base_url}/process-definition{params}'.format(base_url=URL_ENGINE_API, params=concatenate_params(params))
        app.logger.debug("ProcessApi#get_list: {}".format(url))

        return requests.get(url=url)

    @staticmethod
    def get_one(_id: str) -> Response:
        url = '{base_url}/process-definition/{id}'.format(base_url=URL_ENGINE_API, id=_id)
        app.logger.debug("ProcessApi#get_one: {}".format(url))

        return requests.get(url=url)


class AuthorizationApi:
    PROCESS_DEFINITION = 6
    PROCESS_INSTANCE = 8
    PERMITTED_RESOURCE_TYPES = [PROCESS_DEFINITION, PROCESS_INSTANCE]

    @staticmethod
    def create(user: User, resource_type: int, resource_id: str) -> Response:
        app.logger.debug("ProcessAuthorizationApi#create: user.id={user_id} / resource_type={resource_type} / resource_id={resource_id}".format(
            user_id=user.id, resource_type=resource_type, resource_id=resource_id))

        if resource_type not in AuthorizationApi.PERMITTED_RESOURCE_TYPES:
            return

        params = {'type': 1,
                  'permissions': ["READ", "CREATE_INSTANCE", "READ_INSTANCE", "UPDATE_INSTANCE",
                                  "READ_TASK", "UPDATE_TASK", "TASK_ASSIGN", "TASK_WORK", "READ_HISTORY"],
                  'userId': None,
                  'groupId': 1,  # all group id in same tenant
                  'resourceType': resource_type,
                  'resourceId': resource_id}

        return requests.post(url=URL_ENGINE_API + '/authorization/create', json=params)


def concatenate_params(params: dict) -> str:
    return '?' + '&'.join([key + '=' + params[key] for key in params.keys()]) if len(params) else ''
