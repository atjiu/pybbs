@echo off
:doStart
set CFG_DIR=%~dp0%..
echo on
java  -jar ../pybbs.jar
:end
