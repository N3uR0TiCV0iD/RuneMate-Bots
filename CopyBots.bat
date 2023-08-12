@echo off
cd %~dp0
setlocal EnableDelayedExpansion
set myBotsFolder="%userprofile%\Runemate\bots\com\loldie"
robocopy ".\bin\com\loldie" !myBotsFolder! /E /XO>nul
