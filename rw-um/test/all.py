import unittest

import camunda_api
import signal_callbacks

# initialize the test suite
loader = unittest.TestLoader()
suite = unittest.TestSuite()

# add tests to the test suite
suite.addTests(loader.loadTestsFromModule(camunda_api))
suite.addTests(loader.loadTestsFromModule(signal_callbacks))

# initialize a runner, pass it your suite and run it
runner = unittest.TextTestRunner(verbosity=3)
result = runner.run(suite)
