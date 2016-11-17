package com.nemo.ul.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;


public class AppNameComparator implements Comparator<AppNameComparator.IAppOrderInfo> {

    public static interface IAppOrderInfo {
        String getOrderInfo1();

        String getOrderInfo2();
    }

    private static final int TYPE_NUMBER = 1;
    private static final int TYPE_LETTER = 2;
    private static final int TYPE_CHINESE = 3;
    private static final int TYPE_OTHER = 4;
    private final Collator collator;

    public static final AppNameComparator COMPARATOR = new AppNameComparator();

    private AppNameComparator() {
        collator = Collator.getInstance(Locale.CHINA);
    }

    @Override
    public int compare(IAppOrderInfo app1, IAppOrderInfo app2) {
        final int type1 = getType(app1);
        final int type2 = getType(app2);
        if (type1 != type2) {
            return type1 - type2;
        } else {
            return compare1(app1, app2);
        }
    }

    private int getType(IAppOrderInfo app1) {
        if (app1 != null) {
            return 1;
        } else {
            return 2;
        }
    }

    public int compare1(IAppOrderInfo app1, IAppOrderInfo app2) {
        final String baseNameWithExt1 = app1.getOrderInfo1();
        final String baseNameWithExt2 = app2.getOrderInfo1();
        if (baseNameWithExt1.isEmpty() || baseNameWithExt2.isEmpty()) {
            return -1;
        } else if (baseNameWithExt1.isEmpty() && baseNameWithExt2.isEmpty()) {
            return 0;
        }
        final char c1 = baseNameWithExt1.charAt(0);
        final char c2 = baseNameWithExt2.charAt(0);
        final int type1 = getLetterType(c1);
        final int type2 = getLetterType(c2);
        if (type1 != type2) {
            return type1 - type2;
            // if these are letter compare them like this: a < A < b <B ...
        } else if ((type1 == type2) && type1 == TYPE_LETTER) {
            char c11 = Character.toLowerCase(c1);
            char c22 = Character.toLowerCase(c2);
            if (c11 == c22 && c1 != c2) {
                return c2 - c1;
            } else if (c11 == c22) {
                return compare2(baseNameWithExt1, baseNameWithExt2);
            } else {
                return c11 - c22;
            }
        }

        return compare2(baseNameWithExt1, baseNameWithExt2);
    }

    private int getLetterType(char c) {
        if (c >= 0x4e00 && c <= 0x9fa5) {
            return TYPE_CHINESE;
        } else if (isNum(c)) {
            return TYPE_NUMBER;
        } else if (isLetter(c)) {
            return TYPE_LETTER;
        } else {
            return TYPE_OTHER;
        }
    }

    private boolean isNum(char c) {
        return c >= 0x0030 && c <= 0x0039;
    }

    private boolean isLetter(char c) {
        return (c >= 0x0041 && c <= 0x005a) || (c >= 0x0061 && c <= 0x007a);
    }

    private int compare2(String s1, String s2) {
        char ca1[] = s1.toCharArray();
        char ca2[] = s2.toCharArray();
        int len1 = ca1.length;
        int len2 = ca2.length;
        int limit = Math.min(len1, len2);
        int i = 0;
        for (; i < limit; i++) {
            char c1 = ca1[i];
            char c2 = ca2[i];
            int type1 = getLetterType(c1);
            int type2 = getLetterType(c2);
            if (type1 == type2) {
                if (type1 == TYPE_CHINESE) {
                    if (c1 == c2) {
                        continue;
                    }
                    return collator.compare(s1, s2);
                } else if (type1 == TYPE_NUMBER) {
                    int n1 = getNumber(s1.substring(i));
                    int n2 = getNumber(s2.substring(i));
                    if (n1 != n2) {
                        if (n1 == -1) {
                            return 1;
                        } else if (n2 == -1) {
                            return -1;
                        } else {
                            return n1 - n2;
                        }
                    } else if (n1 == n2) {
                        if (n1 != -1) {
                            continue;
                        }
                    }
                } else if (type1 == TYPE_LETTER) {
                    char c11 = Character.toLowerCase(c1);
                    char c22 = Character.toLowerCase(c2);
                    if (c11 == c22 && c1 != c2) {
                        return c2 - c1;
                    } else if (c11 == c22) {
                        continue;
                    } else {
                        return c11 - c22;
                    }
                } else {
                    if (c1 != c2) {
                        return c1 - c2;
                    }
                }
            } else {
                return type1 - type2;
            }
        }
        return len1 - len2;

    }

    private int getNumber(String str) {
        int num = Integer.MAX_VALUE;
        int bits = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                bits++;
            } else {
                break;
            }

        }
        // The number is too big.
        if (bits > 8) {
            return -TYPE_CHINESE;
        } else if (bits > 0) {
            num = Integer.parseInt(str.substring(0, bits));
        }
        return num;
    }
}
