#!/bin/bash

scriptPos=`dirname $0`

cd "$scriptPos/../../integration_test/vagrant/ubuntu_memcached" && vagrant halt
