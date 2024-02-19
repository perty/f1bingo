#!/usr/bin/env bash

keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore springboot.p12 -validity 3650

mv springboot.p12 ../src/main/resources/springboot.p12
