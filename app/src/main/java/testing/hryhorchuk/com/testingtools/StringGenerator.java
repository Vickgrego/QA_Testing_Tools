package testing.hryhorchuk.com.testingtools;


import java.util.Random;

public class StringGenerator {
    protected static String errorMessage = "Empty string";
    private int max = 123;
    private int min = 97;
    private Random rand = new Random();
    private StringBuilder sb = new StringBuilder();

    public enum OPTIONS {
        STR_NO_SPACE,
        STR_WITH_SPACES,
        EMAIL,
        INT_STR
    }

    protected String generateString(int length, OPTIONS option) {
        if (length == 0) {
            return errorMessage;
        }
        this.sb.setLength(0);
        int iterate;
        switch (option) {
            case STR_NO_SPACE:
                this.sb.append(getGeneratedString(length));
                break;
            case STR_WITH_SPACES:
                iterate = 0;
                while (iterate < length) {
                    this.sb.append((char) (this.rand.nextInt(this.max - this.min) + this.min));
                    if (length > 6) {
                        if (iterate % 5 == 0 && iterate != 0) {
                            this.sb.replace(iterate, iterate + 1, " ");
                        }
                    } else if (iterate == length / 2 && iterate != 0) {
                        this.sb.replace(iterate, iterate + 1, " ");
                    }
                    iterate++;
                }
                break;
            case INT_STR:
                for (iterate = 0; iterate < length; iterate++) {
                    this.sb.append(this.rand.nextInt(10));
                }
                break;
            case EMAIL:
                if (length > 6) {
                    for (iterate = 0; iterate < length; iterate++) {
                        this.sb.append((char) (this.rand.nextInt(this.max - this.min) + this.min));
                        if (length > 10) {
                            if (iterate == 5) {
                                this.sb.replace(iterate, iterate + 1, "@");
                            }
                            if (iterate == 8) {
                                this.sb.replace(iterate, iterate + 1, ".");
                            }
                        } else {
                            if (iterate == (length / 2) - 1) {
                                this.sb.replace(iterate, iterate + 1, "@");
                            }
                            if (iterate == (length / 2) + 1) {
                                this.sb.replace(iterate, iterate + 1, ".");
                            }
                        }
                    }
                    break;
                }
                return "t@st.qa";
            default:
                return errorMessage;
        }
        return this.sb.toString();
    }

    protected int StringToInt(String s) {
        if (s.equals("")) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    private StringBuilder getGeneratedString(int length) {
        StringBuilder _sb = new StringBuilder();
        for (int iterate = 0; iterate < length; iterate++) {
            _sb.append((char) (this.rand.nextInt(this.max - this.min) + this.min));
        }
        return _sb;
    }
}