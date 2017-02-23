FROM python:3.5

MAINTAINER Masaaki Inaba <inaba@rational-works.jp>

#----------------------
# PIP Install
#----------------------
COPY requirements.txt /etc/python/requirements.txt
RUN pip install -r /etc/python/requirements.txt

#----------------------
# Make directory for logs
#----------------------
RUN mkdir /var/log/rw-portal

#----------------------
# Copy & Execute Application
#----------------------
COPY ./src /src
WORKDIR /src
ENTRYPOINT ["python"]
CMD ["runserver.py"]
