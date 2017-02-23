import requests
from flask import render_template, request
from flask_security import login_required, current_user

from user_management import app
from user_management.camunda_api import login, UserApi, TenantApi


@app.route('/')
@login_required
def index():
    app.logger.debug("call views#index (method: {})".format(request.method))
    return render_template('index.html')


@app.route('/edit', methods=['GET', 'POST'])
@login_required
def edit():
    app.logger.debug("call views#edit (method: {})".format(request.method))
    if request.method == 'GET':
        return render_template('edit.html')
    elif request.method == 'POST':
        _update_current_user_by_form()
        return render_template('index.html')


def _update_current_user_by_form() -> None:
    # Flags for update
    to_be_updated_user = current_user.first_name != request.form.get('first_name') or current_user.last_name != request.form.get('last_name')
    to_be_updated_tenant = current_user.tenant.name != request.form.get('tenant_name')

    if to_be_updated_user or to_be_updated_tenant:
        # Update UM
        current_user.first_name = request.form.get('first_name')
        current_user.last_name = request.form.get('last_name')
        current_user.tenant.name = request.form.get('tenant_name')
        current_user.save()

        # Update Camunda
        session = requests.session()
        login(session)
        if to_be_updated_user:
            UserApi.update(session, current_user)
        if to_be_updated_tenant:
            tenant_name = TenantApi.get_one(session, current_user.tenant).json().get('name')
            if tenant_name is None:
                TenantApi.create(session, current_user.tenant)
        session.close()
