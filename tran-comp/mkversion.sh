#!/bin/bash

rm -f src/main/java/org/trncmp/Version.java

ORG=$(head -15 pom.xml | tail -10 | grep -i groupId | cut -d '>' -f 2 | cut -d '<' -f1)
VERSION=$(head -15 pom.xml | tail -10 | grep -v model | grep -i version | cut -d '>' -f 2 | cut -d '-' -f1)
NOW=$(date -u +"%Y-%m-%dT%H:%M:%SZ")


cat<<EOF > src/main/java/org/trncmp/Version.java
package org.trncmp;
public class Version {
  public static final String number = "${VERSION}";
  public static final String date   = "${NOW}";
  public static final String name   = "${ORG}";
  public static final String author = "Stephen W. Soliday";
  public static final String email  = "mailto:stephen.soliday@trncmp.org";
  public static final String www    = "http://research.trncmp.org";
}
EOF
