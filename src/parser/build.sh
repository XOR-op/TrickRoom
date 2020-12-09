#!/bin/sh
antlr MxStar.g4 -no-listener -visitor
ls|grep java|xargs -I {} sed -i '' "1s/^/package parser;/" {}
