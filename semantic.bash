set ff=UNIX
set -e
java -cp /ulib/java/antlr-4.8-complete.jar:./bin TrickRoom -fsyntax-only @/dev/stdin