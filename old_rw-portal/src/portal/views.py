from flask import render_template, request, redirect, url_for
from flask_security import current_user

from portal import app
from portal.camunda_api import ProcessApi, AuthorizationApi
from portal.models import Category


@app.route('/', methods=['GET'])
def index() -> str:
    app.logger.debug(request)
    return render_template('index.html', processes=ProcessApi.get_list(latestVersion="true", withoutTenantId="true").json(), categories=Category.query.all())


@app.route('/process/<string:process_id>', methods=['GET'])
def process(process_id: str) -> str:
    app.logger.debug(request)
    return render_template('process.html', process=ProcessApi.get_one(process_id).json(), categories=Category.query.all())


@app.route('/after_register', methods=['GET'])
def after_register() -> str:
    app.logger.debug(request.get_data())
    return render_template('after_register.html')


@app.route('/process/purchase/<string:process_id>', methods=['GET'])
def purchase_process(process_id: str) -> str:
    app.logger.debug(request)
    AuthorizationApi.create(user=current_user, resource_type=AuthorizationApi.PROCESS_DEFINITION, resource_id=process_id)
    return redirect(url_for('process', process_id=process_id))
