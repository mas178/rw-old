import unittest

from portal.camunda_api import UserApi, TenantApi, ProcessApi
from portal.models import User, Tenant


class CamundaApiTestCase(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

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

        user_count_initial = UserApi.get_count()

        # Test camunda_api.create_user
        response = UserApi.create(user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check user count / Test camunda_api.user_list and camunda_api.user_count
        user_count_after_creation = UserApi.get_count()
        self.assertEqual(user_count_initial + 1, user_count_after_creation)

        # Test camunda_api.get_user
        response = UserApi.get_one(user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))
        response_json = response.json()
        self.assertEqual(response_json['id'], user.email)
        self.assertEqual(response_json['firstName'], user.first_name)
        self.assertEqual(response_json['lastName'], user.last_name)
        self.assertEqual(response_json['email'], user.email)

        # Test camunda_api.delete_user
        response = UserApi.delete(user)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check user count / Test camunda_api.user_list and camunda_api.user_count
        user_count_after_deletion = UserApi.get_count()
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
        tenant = Tenant('2', 'Test_Tenant_2')
        tenant.name = 'test_tenant_name_1'

        tenant_count_initial = TenantApi.get_count()

        # Test camunda_api.create_tenant
        response = TenantApi.create(tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check tenant count / Test camunda_api.tenant_list and camunda_api.tenant_count
        tenant_count_after_creation = TenantApi.get_count()
        self.assertEqual(tenant_count_initial + 1, tenant_count_after_creation)

        # Test camunda_api.get_tenant
        response = TenantApi.get_one(tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))
        response_json = response.json()
        self.assertEqual(response_json['id'], tenant.name)  # ToDo: organize the relations between id of UM, id of Camunda and name
        self.assertEqual(response_json['name'], tenant.name)

        # Test camunda_api.delete_tenant
        response = TenantApi.delete(tenant)
        self.assertTrue(response.ok, "Response should be OK. (status_code = {})\n{}".format(response.status_code, response.text))

        # Check tenant count / Test camunda_api.tenant_list and camunda_api.tenant_count
        tenant_count_after_deletion = TenantApi.get_count()
        self.assertEqual(tenant_count_after_creation - 1, tenant_count_after_deletion)

    def test_create_user_member(self):
        """
        Test following 3 methods
        - camunda_api.TenantApi.create_user_member
        - camunda_api.TenantApi.get_list
        - camunda_api.UserApi.get_list
        """
        tenant = Tenant('1', 'Test_Tenant_1')
        tenant.name = 'test_create_user_member_tenant_name'

        user = User()
        user.email = 'test_create_user_member@test.test'
        user.first_name = 'test_create_user_member_first_name'
        user.last_name = 'test_create_user_member_last_name'
        user.tenant = tenant

        UserApi.create(user)
        TenantApi.create(tenant)
        TenantApi.create_user_member(user)

        tenant_list = TenantApi.get_list(userMember=user.email).json()
        user_list = UserApi.get_list(memberOfTenant=tenant.name).json()

        self.assertEqual(tenant.name, tenant_list[0]['id'])
        self.assertEqual(tenant.name, tenant_list[0]['name'])
        self.assertEqual(user.email, user_list[0]['id'])
        self.assertEqual(user.first_name, user_list[0]['firstName'])
        self.assertEqual(user.last_name, user_list[0]['lastName'])
        self.assertEqual(user.email, user_list[0]['email'])

        UserApi.delete(user)
        TenantApi.delete(tenant)


class ProcessApiTestCase(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_user_crud(self):
        process_list = ProcessApi.get_list().json()
        print(process_list[0]['id'])
        self.assertEquals(process_list[0]['id'], 'Process_06yj4to:1:3c5b94bc-8de9-11e6-92b2-0242ac110003')


if __name__ == '__main__':
    unittest.main()
