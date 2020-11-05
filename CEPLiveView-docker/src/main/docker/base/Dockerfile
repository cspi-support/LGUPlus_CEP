#
# Dockerfile for StreamBase generic base image
#
# Copyright (c) 2018 TIBCO Software Inc.
#
FROM centos:7

LABEL description="StreamBase Base"

#
# Set environment
#
ENV PRODUCT_HOME     /opt/tibco/streambase
ENV RUNTIME_HOME     /var/opt/tibco/streambase
ENV JAVA_HOME        /etc/alternatives/jre
ENV PATH             /bin:/usr/sbin:${PRODUCT_HOME}/distrib/tibco/bin
ENV USER_NAME        tibco

#
# Add required additional packages
#
# systat    required for runtime statistcs
# gdb       required for crash dumps
# java      required for applications
# zip       required for snapshots
# unzip     required to unpack runtime on windows
# net-tools useful to diagnose network issues
# iptables  useful to simulate network issues
# tc        useful to simulate network issues
#
RUN yum --assumeyes install \
    sysstat \
    gdb \
    java-1.8.0-openjdk-headless \
    zip \
    unzip \
    net-tools \
    iptables \
    tc \
    && yum clean all

#
# Create a user to run nodes
#
RUN /usr/sbin/useradd \
    --comment "TIBCO StreamBase Guest User" \
    --create-home \
    --user-group \
    --password ${USER_NAME} \
    --shell /bin/bash \
    ${USER_NAME}

#
# Install runtime
#
RUN mkdir -p ${PRODUCT_HOME}
COPY maven ${PRODUCT_HOME}
RUN chmod a+x ${PRODUCT_HOME}/start-node
RUN ${docker.run.unpack}

#
# Allow user access to nodes and applications
#
RUN mkdir -p ${RUNTIME_HOME}
RUN chown ${USER_NAME}:${USER_NAME} ${RUNTIME_HOME}

#
# Change to guest user
#
USER ${USER_NAME}
