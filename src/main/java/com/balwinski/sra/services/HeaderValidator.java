package com.balwinski.sra.services;

import com.balwinski.sra.InvalidDataException;

/*
TODO Methods moved from IOService - to reprocess or made all from scratch
 */
public class HeaderValidator {

    private String[] header = new String[6];

    private static final String invalidHeaderLineMsg = "Invalid header line ";

    private boolean isHeaderLine(int lineNum) {
        return (lineNum >= 1 && lineNum <= 6);
    }

    public void parseHeaderLine(String currentLine, int lineNum) throws InvalidDataException {
        switch (lineNum) {
            case 1: {
                if (isValidHeaderLine1(currentLine)) {
                    header[0] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            case 2: {
                if (isValidHeaderLine2(currentLine)) {
                    header[1] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            case 3: {
                if (isValidHeaderLine3(currentLine)) {
                    header[2] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            case 4: {
                if (isValidHeaderLine4(currentLine)) {
                    header[3] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            case 5: {
                if (isValidHeaderLine5(currentLine)) {
                    header[4] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            case 6: {
                if (isValidHeaderLine6(currentLine)) {
                    header[5] = currentLine;
                } else {
                    throw new InvalidDataException(invalidHeaderLineMsg + lineNum + "; Readed: " + currentLine);
                }
                break;
            }
            default: {

            }
        }
    }

    private boolean isValidHeaderLine1(String currentLine) {
        return currentLine.equals("Loop Assistant - Revision Report");
    }

    private boolean isValidHeaderLine2(String currentLine) {
        return currentLine.matches("-{32}");
    }

    private boolean isValidHeaderLine3(String currentLine) {
        //dddd-dd-dd dd:dd:dd - date time line
        return currentLine.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");
    }

    private boolean isValidHeaderLine4(String currentLine) {
        return currentLine.matches("SCU:\\s+\\d+\\s+\\/\\s+Loop:\\s+\\d+");
    }

    private boolean isValidHeaderLine5(String currentLine) {
        return currentLine.matches("={32}");
    }

    private boolean isValidHeaderLine6(String currentLine) {
        return currentLine.isEmpty();
    }
}
