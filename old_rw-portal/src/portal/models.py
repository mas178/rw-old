import uuid

from flask import request
from flask_security import RoleMixin, UserMixin, SQLAlchemyUserDatastore
from sqlalchemy import Table, Column, String, Integer, Boolean, DateTime, ForeignKey
from sqlalchemy.orm import relationship, backref

from portal import app
from portal.database import Base

# Many-to-Many Relationship Table
process_author = Table(
    'Processes_Authors',
    Base.metadata,
    Column('process_id', Integer, ForeignKey('Processes.id')),
    Column('author_id', Integer, ForeignKey('Authors.id'))
)

# Many-to-Many Relationship Table
process_category = Table(
    'Processes_Categories',
    Base.metadata,
    Column('process_id', Integer, ForeignKey('Processes.id')),
    Column('category_id', Integer, ForeignKey('Categories.id'))
)


class Process(Base):
    __tablename__ = 'Processes'

    id = Column(Integer, primary_key=True)
    name = Column(String(50))
    title = Column(String(100))
    description = Column(String(2000))
    image_url = Column(String(250))
    authors = relationship('Author', secondary=process_author, backref="process")  # Many-to-Many Relationship
    categories = relationship('Category', secondary=process_category, backref="process")  # Many-to-Many Relationship

    def __init__(self, name: str = None, title: str = None, description: str = None, image_url: str = None, authors: list = [], categories: list = []) -> None:
        self.name = name
        self.title = title
        self.description = description
        self.image_url = image_url
        self.authors = authors
        self.categories = categories

    def __repr__(self) -> str:
        return "Process\n  name: {}\n  title: {}\n  description: {}\n  image_url: {}\n".format(self.name, self.title, self.description, self.image_url)


class Author(Base):
    __tablename__ = 'Authors'

    id = Column(Integer, primary_key=True)
    name = Column(String(50))

    def __init__(self, name=None):
        self.name = name

    def __repr__(self):
        return "Author\n  name: {}\n".format(self.name)


class Category(Base):
    __tablename__ = 'Categories'

    id = Column(Integer, primary_key=True)
    name = Column(String(50))

    def __init__(self, name=None):
        self.name = name

    def __repr__(self):
        return "Category\n  name: {}\n".format(self.name)


role_user = Table(
    'Roles_Users',
    Base.metadata,
    Column('user_id', Integer(), ForeignKey('Users.id')),
    Column('role_id', Integer(), ForeignKey('Roles.id'))
)


class Role(Base, RoleMixin):
    __tablename__ = 'Roles'

    id = Column(Integer, primary_key=True)
    name = Column(String(80), unique=True)
    description = Column(String(255))


class Tenant(Base):
    __tablename__ = 'Tenants'

    id = Column(String(80), primary_key=True)
    name = Column(String(80), unique=True)
    description = Column(String(255))

    def __init__(self, id: str, name: str, description: str = ''):
        self.id = id
        self.name = name
        self.description = description


class User(Base, UserMixin):
    __tablename__ = 'Users'

    id = Column(Integer, primary_key=True)
    email = Column(String(255), unique=True)
    password = Column(String(255))
    active = Column(Boolean())
    confirmed_at = Column(DateTime())
    roles = relationship('Role', secondary=role_user, backref=backref('users', lazy='dynamic'))  # Many-to-Many

    # Extended Field
    first_name = Column(String(255))
    last_name = Column(String(255))
    tenant_id = Column(String(80), ForeignKey('Tenants.id'))  # One tenant has many users
    tenant = relationship('Tenant')

    # User Tracking Fields
    last_login_at = Column(DateTime())
    current_login_at = Column(DateTime())
    last_login_ip = Column(String(255))
    current_login_ip = Column(String(255))
    login_count = Column(Integer)


class RWUserDatastore(SQLAlchemyUserDatastore):
    # user_registered_callbackに書いちゃうと、userは既にDBに登録された後だからNG。Mongoでやったように、Save処理自体を上書きしたい。しかし、SQLAlchemyの場合、Save処理は、
    # /Users/inaba/Python35/lib/python3.5/site-packages/flask_security/datastore.pyのUserDatastoreクラスのcreate_userメソッドに書かれており、
    # どうやって上書きしたらよいか分からない。いや分かるだろ？普通に上書きすれば良いんだよ。どこのファイルに書くのが妥当か？
    # portal/__init__.pyのuser_datastoreを宣言する前だな。いやModelの中で、RWUserDatastoreってクラスを作るか。
    def create_user(self, **kwargs):
        tenant_name = request.form.get('tenant_name', None)

        if tenant_name:
            # lengthy?
            existing_tenant = Base.session.query(Tenant).filter(Tenant.name.like(tenant_name))

            if existing_tenant.count():
                kwargs['tenant_id'] = existing_tenant.id
                kwargs['tenant'] = existing_tenant
            else:
                tenant = Tenant(str(uuid.uuid1()), tenant_name)
                # Base.session.add(tenant)
                kwargs['tenant_id'] = tenant.id
                kwargs['tenant'] = tenant

        return SQLAlchemyUserDatastore.create_user(self, **kwargs)


def delete_all():
    tables = [
        process_author,
        process_category,
        Category.__table__,
        Author.__table__,
        Process.__table__,
        role_user,
        Role.__table__,
        User.__table__,
        Tenant.__table__,
    ]

    for table in tables:
        app.logger.debug('Delete {}'.format(table))
        hoge = Base.metadata.bind.has_table(table.name)
        app.logger.debug(hoge)
        Base.session.execute(table.delete())

    Base.session.commit()
