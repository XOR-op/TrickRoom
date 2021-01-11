set ff=UNIX
set -e
cat |java -cp /ulib/java/antlr-4.8-complete.jar:./out TrickRoom -fsyntax-only
