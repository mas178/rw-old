import unittest

from test import camunda_api, models, signal_callbacks, camunda_dao

suite = unittest.TestSuite([
    unittest.TestLoader().loadTestsFromTestCase(camunda_api.CamundaApiTestCase),
    unittest.TestLoader().loadTestsFromTestCase(camunda_dao.CamundaDaoTestCase),
    unittest.TestLoader().loadTestsFromTestCase(models.TestProcess),
    unittest.TestLoader().loadTestsFromTestCase(signal_callbacks.TestUserConfirmedCallback),
])

print(suite.countTestCases())

runner = unittest.TextTestRunner()
runner.run(suite)
