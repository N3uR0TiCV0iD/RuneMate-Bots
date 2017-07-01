@echo off
setlocal EnableDelayedExpansion
set myBotsFolder="%userprofile%\Runemate\bots\com\loldie"
if EXIST !myBotsFolder! (
	RD /S /Q !myBotsFolder!
)
robocopy ".\bin\com\loldie" !myBotsFolder! /E>nul
