#
# Copyright (c) 2018 TIBCO Software Inc.
# 
ARG docker_base_version
FROM sbrt-base:${docker_base_version}

LABEL description="StreamBase Application"

#
# Install application archive & test configurations
#
COPY maven ${RUNTIME_HOME}

#
# entry point to start node
#
ENTRYPOINT ${PRODUCT_HOME}/start-node
