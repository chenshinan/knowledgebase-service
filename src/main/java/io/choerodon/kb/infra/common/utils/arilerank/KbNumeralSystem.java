package io.choerodon.kb.infra.common.utils.arilerank;

import io.choerodon.core.exception.CommonException;

/**
 * Created by Zenger on 2019/4/30.
 */
public abstract class KbNumeralSystem {

    private static final String DIGIT_ERROR = "error.rank.notValidDigit";

    public static final KbNumeralSystem BASE_10 = new KbNumeralSystem() {

        @Override
        public int getBase() {
            return 10;
        }

        @Override
        public char getPositiveChar() {
            return '+';
        }

        @Override
        public char getNegativeChar() {
            return '-';
        }

        @Override
        public char getRadixPointChar() {
            return '.';
        }

        @Override
        public int toDigit(char ch) {
            if (ch >= 48 && ch <= 57) {
                return ch - 48;
            } else {
                throw new CommonException(DIGIT_ERROR);
            }
        }

        @Override
        public char toChar(int digit) {
            return (char) (digit + 48);
        }
    };
    public static final KbNumeralSystem BASE_36 = new KbNumeralSystem() {
        private final char[] digits = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

        @Override
        public int getBase() {
            return 36;
        }

        @Override
        public char getPositiveChar() {
            return '+';
        }

        @Override
        public char getNegativeChar() {
            return '-';
        }

        @Override
        public char getRadixPointChar() {
            return ':';
        }

        @Override
        public int toDigit(char ch) {
            if (ch >= 48 && ch <= 57) {
                return ch - 48;
            } else if (ch >= 97 && ch <= 122) {
                return ch - 97 + 10;
            } else {
                throw new CommonException(DIGIT_ERROR);
            }
        }

        @Override
        public char toChar(int digit) {
            return this.digits[digit];
        }
    };
    public static final KbNumeralSystem BASE_64 = new KbNumeralSystem() {
        private final char[] digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ^_abcdefghijklmnopqrstuvwxyz".toCharArray();

        @Override
        public int getBase() {
            return 64;
        }

        @Override
        public char getPositiveChar() {
            return '+';
        }

        @Override
        public char getNegativeChar() {
            return '-';
        }

        @Override
        public char getRadixPointChar() {
            return ':';
        }

        @Override
        public int toDigit(char ch) {
            if (ch >= 48 && ch <= 57) {
                return ch - 48;
            } else if (ch >= 65 && ch <= 90) {
                return ch - 65 + 10;
            } else if (ch == 94) {
                return 36;
            } else if (ch == 95) {
                return 37;
            } else if (ch >= 97 && ch <= 122) {
                return ch - 97 + 38;
            } else {
                throw new CommonException(DIGIT_ERROR);
            }
        }

        @Override
        public char toChar(int digit) {
            return this.digits[digit];
        }
    };

    abstract int getBase();

    abstract char getPositiveChar();

    abstract char getNegativeChar();

    abstract char getRadixPointChar();

    abstract int toDigit(char var1);

    abstract char toChar(int var1);
}
