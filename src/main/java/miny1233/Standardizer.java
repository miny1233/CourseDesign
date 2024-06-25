package miny1233;

public class Standardizer {
    static public String standardize(String input) {
        StringBuilder result = new StringBuilder();
        int status = 0;

        for (int idx = 0; idx < input.length(); idx++) {

            if (input.charAt(idx) == ' ') {
                status = 0;
                continue;
            }

            if (status == 0)
            {
                if (input.charAt(idx) == '\n') {
                    status = 0;
                }else if (input.charAt(idx) == '-')
                    status = 2;
                else
                    status = 1;

                result.append(input.charAt(idx));
                continue;
            }

            if (status == 1)
            {
                status = 0;
                if (input.charAt(idx) != '\'') {
                   result.append(' ');
                   idx--;
                   continue;
                }

                result.append(input.charAt(idx));
                result.append(' ');
                continue;

            }

            if (status == 2)
            {
                status = 0;
                if (input.charAt(idx) != '>') {
                    result.append(' ');
                    idx--;
                    continue;
                }
                result.append(input.charAt(idx));
            }

        }

        return result.toString();
    }
}
