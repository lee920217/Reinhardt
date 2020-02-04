#!/bin/bash

set -x

ENV_ARG_NAME=$1
CFG_FILE=$2
CFG_ARG_NAME=$3

ENV_ARG_VAL=`eval echo '$'$ENV_ARG_NAME`
ENV_ARG_VAL=${ENV_ARG_VAL//\//\\/}
echo $ENV_ARG_VAL

if [ ! -n "$ENV_ARG_VAL" ]; then
    echo "get non val from env \$$ENV_ARG_NAME"
else
    echo "get val from env \$$ENV_ARG_NAME = $ENV_ARG_VAL"
    sed -i 's/^'"$CFG_ARG_NAME"'.*$/'"$CFG_ARG_NAME"'='"$ENV_ARG_VAL"'/g' $CFG_FILE
fi

