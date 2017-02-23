from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.ext import declarative
from sqlalchemy.orm import scoped_session, sessionmaker

from portal import app

db = SQLAlchemy(app)
Base = declarative.declarative_base()
Base.session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=db.engine))
Base.query = Base.session.query_property()
Base.metadata.bind = db.engine


def create_all():
    app.logger.debug('create_all()')
    Base.metadata.create_all(bind=db.engine)


def drop_all():
    app.logger.debug('drop_all()')
    Base.metadata.drop_all(bind=db.engine)
