import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvReaderTester {
    @Test
    public void testCsvReader() {
        String csvData =
                "- Wiskunde\n" +
                        "  -- Algebra\n" +
                        "    --- Lineaire vergelijkingen\n" +
                        "      ---- Inleiding tot het oplossen van lineaire vergelijkingen.\n" +
                        "    --- Kwadratische vergelijkingen\n" +
                        "      ---- Studie van vergelijkingen van de tweede graad en hun oplossingen.\n" +
                        "  -- Meetkunde\n" +
                        "    --- Cirkels\n" +
                        "      ---- Eigenschappen en formules met betrekking tot cirkels in meetkunde.\n" +
                        "- Natuurkunde\n" +
                        "  -- Mechanica\n" +
                        "    --- Bewegingsleer\n" +
                        "      ---- Studie van beweging, krachten en traagheid.\n";

        BufferedReader br = new BufferedReader(new StringReader(csvData));
        CsvReader csvReader = new CsvReader();
        List<String> result = csvReader.readCsv(br);

        List<String> expected = new ArrayList<>();
        expected.add("-- Algebra");
        expected.add("-- Meetkunde");
        expected.add("-- Mechanica");

        assertEquals(expected, result);
    }
}
