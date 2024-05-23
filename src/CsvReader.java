import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<String> readCsv(BufferedReader br) {
        List<String> filteredLines = new ArrayList<>();
        String line;
        int targetDashCount = -1;

        try {
            while ((line = br.readLine()) != null) {
                int dashCount = countLeadingDashes(line);

                if (targetDashCount == -1 && dashCount == 1) {
                    targetDashCount = dashCount;
                }

                if (dashCount == targetDashCount + 1) {
                    filteredLines.add(line.trim());
                }

                if (dashCount <= targetDashCount && dashCount != 1) {
                    targetDashCount = -1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredLines;
    }

    private int countLeadingDashes(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == '-') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
