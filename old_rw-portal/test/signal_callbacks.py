import unittest

from portal import app
from portal.camunda.dao.tenant_dao import TenantDAO
from portal.camunda.dao.tenant_member_dao import TenantMemberDAO
from portal.camunda.dao.user_dao import UserDAO
from portal.database import Base
from portal.models import User, Tenant
from portal.signal_callbacks import user_confirmed_callback


class TestUserConfirmedCallback(unittest.TestCase):
    def setUp(self):
        self.tenant = Tenant()
        self.tenant.id = 'hogehoge'
        self.tenant.name = 'hogehoge'

        self.user = User()
        self.user.id = 'hoge@hoge.hoge'
        self.user.email = 'hoge@hoge.hoge'
        self.user.first_name = '稲葉'
        self.user.last_name = '理晃'
        self.user.tenant = self.tenant

    def tearDown(self):
        TenantMemberDAO.delete(self.user, self.tenant)
        TenantDAO.delete(self.tenant)
        UserDAO.delete(self.user)
        Base.session.commit()

    def test_user_confirmed_callback(self):
        self.assertEqual(UserDAO.registered_user(self.user), False)
        user_confirmed_callback(app, self.user)
        self.assertEqual(UserDAO.registered_user(self.user), True)


if __name__ == '__main__':
    unittest.main()
