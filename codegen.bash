set ff=UNIX
set -e
cat |java -cp /ulib/java/antlr-4.9.1-complete.jar:./out TrickRoom -O2 -o output.s -debug
