import unittest

import requests

from user_management import app
from user_management.camunda_api import login, UserApi, TenantApi
from user_management.models import User, Tenant
from user_management.signal_callbacks import user_confirmed_callback


class SignalCallbacksTestCase(unittest.TestCase):
    def setUp(self):
        self.session = requests.session()
        self.app = app.test_client()
        login(self.session)

    def tearDown(self):
        self.session.close()

    def test_user_confirmed_callback(self):
        tenant = Tenant()
        tenant.name = 'test_tenant_name_SignalCallbacksTestCase'

        user = User()
        user.email = 'test_SignalCallbacksTestCase@test.test'
        user.first_name = 'test_first_name_SignalCallbacksTestCase'
        user.last_name = 'test_last_name_SignalCallbacksTestCase'
        user.tenant = tenant

        user_confirmed_callback(self.app, user)

        response_json = UserApi.get_one(self.session, user).json()
        self.assertEqual(response_json['id'], user.email)
        self.assertEqual(response_json['firstName'], user.first_name)
        self.assertEqual(response_json['lastName'], user.last_name)
        self.assertEqual(response_json['email'], user.email)

        response_json = TenantApi.get_one(self.session, user.tenant).json()
        self.assertEqual(response_json['id'], user.tenant.name)
        self.assertEqual(response_json['name'], user.tenant.name)

        UserApi.delete(self.session, user)
        TenantApi.delete(self.session, tenant)


if __name__ == '__main__':
    unittest.main()
