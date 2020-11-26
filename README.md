# What is it?

Schrack Revision Analyzer is a tool to analyze reports produced by Schrack Seconet "ServiceAssistant" software.
The reports contain list of fire alarm system detectors and other line elements with information about date and time when each detector has been tested.
The purpose of this project is to aggregate all entries from many report files (possibly from many years) and present witch of them could never been tested.

### Report file structure

Briefly:
text files
Header - 6 lines (contains test date, loop number, panel number)
Entries: typically up to 128 entries, one line each
Example entries:
OSD2000A	37	Detector (1)	13/7			to be checked	
OSD2000A	38	Detector (1)	15/9	10:46:25	10:46:25	CHECKED	

### Usage

Project is in early dev phase.

### Author

Jerzy Balwiński
