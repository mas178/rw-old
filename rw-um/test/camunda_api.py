import unittest

import requests

from user_management.camunda_api import login, UserApi, TenantApi
from user_management.models import User, Tenant


class CamundaApiTestCase(unittest.TestCase):
    def setUp(self):
        self.session = requests.session()
        login(self.session)

    def tearDown(self):
        self.session.close()

    def test_user_crud(self):
        """
        Test following 5 methods
        - camunda_api.create_user
        - camunda_api.delete_user
        - camunda_api.get_user
        - camunda_api.get_user_list
        - camunda_api.get_user_count
        """
        user = User()
        user.email = 'test1@test.test'
        user.first_name = 'test_first_name'
        user.last_name = 'test_last_name'

        user_count_initial = UserApi.get_count(self.session)

        # Test camunda_api.create_user
        response = UserApi.create_user(self.session, user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check user count / Test camunda_api.user_list and camunda_api.user_count
        user_count_after_creation = UserApi.get_count(self.session)
        self.assertEqual(user_count_initial + 1, user_count_after_creation)

        # Test camunda_api.get_user
        response = UserApi.get_one(self.session, user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))
        response_json = response.json()
        self.assertEqual(response_json['id'], user.email)
        self.assertEqual(response_json['firstName'], user.first_name)
        self.assertEqual(response_json['lastName'], user.last_name)
        self.assertEqual(response_json['email'], user.email)

        # Test camunda_api.delete_user
        response = UserApi.delete(self.session, user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check user count / Test camunda_api.user_list and camunda_api.user_count
        user_count_after_deletion = UserApi.get_count(self.session)
        self.assertEqual(user_count_after_creation - 1, user_count_after_deletion)

    def test_tenant_crud(self):
        """
        Test following 5 methods
        - camunda_api.TenantApi.create
        - camunda_api.TenantApi.delete
        - camunda_api.TenantApi.get_one
        - camunda_api.TenantApi.get_list
        - camunda_api.TenantApi.get_count
        """
        tenant = Tenant()
        tenant.name = 'test_tenant_name_1'

        tenant_count_initial = TenantApi.get_count(self.session)

        # Test camunda_api.create_tenant
        response = TenantApi.create(self.session, tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check tenant count / Test camunda_api.tenant_list and camunda_api.tenant_count
        tenant_count_after_creation = TenantApi.get_count(self.session)
        self.assertEqual(tenant_count_initial + 1, tenant_count_after_creation)

        # Test camunda_api.get_tenant
        response = TenantApi.get_one(self.session, tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))
        response_json = response.json()
        self.assertEqual(response_json['id'], tenant.name)  # ToDo: organize the relations between id of UM, id of Camunda and name
        self.assertEqual(response_json['name'], tenant.name)

        # Test camunda_api.delete_tenant
        response = TenantApi.delete(self.session, tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check tenant count / Test camunda_api.tenant_list and camunda_api.tenant_count
        tenant_count_after_deletion = TenantApi.get_count(self.session)
        self.assertEqual(tenant_count_after_creation - 1, tenant_count_after_deletion)

    def test_create_user_member(self):
        """
        Test following 3 methods
        - camunda_api.TenantApi.create_user_member
        - camunda_api.TenantApi.get_list
        - camunda_api.UserApi.get_list
        """
        tenant = Tenant()
        tenant.name = 'test_create_user_member_tenant_name'

        user = User()
        user.email = 'test_create_user_member@test.test'
        user.first_name = 'test_create_user_member_first_name'
        user.last_name = 'test_create_user_member_last_name'
        user.tenant = tenant

        UserApi.create_user(self.session, user)
        TenantApi.create(self.session, tenant)
        TenantApi.create_user_member(self.session, user)

        tenant_list = TenantApi.get_list(self.session, userMember=user.email).json()
        user_list = UserApi.get_list(self.session, memberOfTenant=tenant.name).json()

        self.assertEqual(tenant.name, tenant_list[0]['id'])
        self.assertEqual(tenant.name, tenant_list[0]['name'])
        self.assertEqual(user.email, user_list[0]['id'])
        self.assertEqual(user.first_name, user_list[0]['firstName'])
        self.assertEqual(user.last_name, user_list[0]['lastName'])
        self.assertEqual(user.email, user_list[0]['email'])

        UserApi.delete(self.session, user)
        TenantApi.delete(self.session, tenant)


if __name__ == '__main__':
    unittest.main()
