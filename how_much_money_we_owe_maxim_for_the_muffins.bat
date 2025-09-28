@echo off
setlocal enabledelayedexpansion

REM Set initial values
set "initialAmount = 2760"
set "startDate=2024-12-28"
set "weeklyInterestRate = 8"

REM Get the current date
for /f "tokens=2 delims==." %%A in ('"wmic os get localdatetime /value"') do set currentDate=%%A
set "currentYear=!currentDate:~0,4!"
set "currentMonth=!currentDate:~4,2!"
set "currentDay=!currentDate:~6,2!"

REM Parse the start date
for /f "tokens=1,2,3 delims=-" %%A in ("%startDate%") do (
	set "startYear=%%A"
	set "startMonth=%%B"
	set "startDay=%%C"
)

REM Calcualte the nyumber of weeks elapsed
call :DateToDays %startYear% %startMonth% %startDay% startDays
call :DateToDays %currentYear% %currentMonth% %currentDay% currentDays
set /a weeksElapsed = (currentDays-startDays)/7

set /a finalAmount=2760+221*weeksElapsed

echo Initial Amount (12 muffins at $230 a muffin): $2760
echo Date of Muffin Receival: %startDate%
echo Current Date: %currentYear%-%currentMonth%-%currentDay%
echo Weeks Elapsed: %weeksElapsed%
echo Total Amount Owed (at 8 percent weekly interest): %finalAmount%

pause
goto :eof

:DateToDays
REM Convert year, month, day to days since a reference date
set /a "a=(14-%2)/12, y=%1+4800-a, m=%2+12*a-3"
set /a "%~4=y*365+y/4-y/100+y/400+(153*m+2)/5+%3-32045"
goto :eof